/*
 * ASIF Audio Format Encoder
 *
 * Authors:
 *      Matthew Johnsen (u1173601) & Isaac Gibson (u1173413)
 */

#define BITSTREAM_WRITER_LE

#include "libavutil/intreadwrite.h"
#include "libavutil/opt.h"
#include "avcodec.h"
#include "internal.h"
#include "put_bits.h"
#include "bytestream.h"

// Node containing frame data
typedef struct asif_node
{
    uint8_t *data;
    int buf_size;
    struct asif_node *next;
} asif_node;

// Private data for encoding
typedef struct asif_priv_data
{
    int num_samples;
    int num_channels;
    int last_frame_sent;
    int check_packet;
    asif_node *first_node, *last_node;

} asif_priv_data;

static av_cold int asif_encode_init(AVCodecContext *avctx)
{
    asif_priv_data *my_data;
    my_data = avctx->priv_data;

    // Initialize the values of the ASIF's private data
    my_data->num_samples = 0;
    my_data->num_channels = 0;
    my_data->last_frame_sent = 0;
    my_data->check_packet = 0;
    my_data->first_node = NULL;
    my_data->last_node = NULL;

    avctx->frame_size = 1000000;

    return 0;
}

static int asif_send_frame(AVCodecContext *avctx, const AVFrame *frame)
{
    asif_priv_data *my_data;
    my_data = avctx->priv_data;

    if (frame == NULL)
    {
        my_data->check_packet = 1;
        my_data->last_frame_sent = 1;
    }
    else
    {
        int i, j;
        int k = 0;
        asif_node *node;

        // Accumlate frame information
        my_data->num_samples += frame->nb_samples;
        my_data->num_channels = frame->channels;

        // Generate a node, allocate the memory, and copy all data from
        // this frame into the current node

        node = av_mallocz(sizeof(asif_node));
        node->next = NULL;
        node->buf_size = frame->nb_samples;
        node->data = av_mallocz(my_data->num_samples * my_data->num_channels);

        // Populate each node with the frame's data in order of channels
        for (i = 0; i < frame->channels; i++)
        {
            for (j = 0; j < frame->nb_samples; j++)
            {
                node->data[k] = frame->extended_data[i][j];
                k++;
            }
        }

        // Add the node in order at the next place in the linked list.
        if (my_data->first_node == NULL)
        {
            my_data->first_node = node;
            my_data->last_node = node;
        }
        else
        {
            my_data->last_node->next = node;
            my_data->last_node = node;
        }
    }
    return 0;
}

static int asif_receive_packet(AVCodecContext *avctx, AVPacket *avpkt)
{
    uint8_t *buf;
    int size, ret, i;
    int k = 0;

    asif_priv_data *my_data;
    my_data = avctx->priv_data;

    if (my_data->last_frame_sent == 0)
        return AVERROR(EAGAIN);

    if (my_data->check_packet == 1)
    {
        av_log(avctx, AV_LOG_INFO, "RECEIVE_PACKET");

        size = (my_data->num_samples * my_data->num_channels) + 14;

        if ((ret = ff_alloc_packet2(avctx, avpkt, size, size)) < 0)
            return ret;

        buf = avpkt->data;

        // Add the header information to the packet's data
        bytestream_put_byte(&buf, 'a');
        bytestream_put_byte(&buf, 's');
        bytestream_put_byte(&buf, 'i');
        bytestream_put_byte(&buf, 'f');

        bytestream_put_le32(&buf, avctx->sample_rate);
        bytestream_put_le16(&buf, my_data->num_channels);
        bytestream_put_le32(&buf, my_data->num_samples);

        //Loop through each channel and add the data in order into the buffer
        for (i = 1; i <= my_data->num_channels; i++)
        {
            asif_node *node;
            uint8_t prev;
            int j, start;
            start = 1;

            node = my_data->first_node;

            while (node != NULL)
            {
                for (j = node->buf_size * (i - 1); j < node->buf_size * i; j++)
                {
                    if (start == 1)
                    {
                        // Put first sample into the paacket
                        buf[k] = node->data[j];
                        prev = node->data[j];
                        start = 0;
                    }
                    else
                    {
                        // Put remaining deltas into the packet
                        uint8_t current;
                        int result;
                        int8_t delta;
                        current = node->data[j];
                        result = current - prev;

                        // Clamp the bounds closer to 0 for the deltas
                        if (result < -128)
                            delta = -128;
                        else if (result > 127)
                            delta = 127;
                        else
                            delta = result;

                        prev = current;
                        buf[k] = delta;
                    }
                    k++;
                }
                node = node->next;
            }
        }

        my_data->check_packet = 0;
        return 0;
    }

    return AVERROR_EOF;
}
static av_cold int asif_encode_close(AVCodecContext *avctx)
{
    asif_priv_data *my_data;
    asif_node *current_node;
    asif_node *temp;

    my_data = avctx->priv_data;
    current_node = my_data->first_node;

    // Deallocate memory for each of the nodes created
    while (current_node->next != NULL)
    {
        temp = current_node->next;
        av_free(current_node);
        current_node = temp;
    }

    return 0;
}

AVCodec ff_asif_encoder = {
    .name           = "asif",
    .long_name      = NULL_IF_CONFIG_SMALL("ASIF Encoder"),
    .type           = AVMEDIA_TYPE_AUDIO,
    .priv_data_size = sizeof(asif_priv_data),
    .id             = AV_CODEC_ID_ASIF,
    .init           = asif_encode_init,
    .encode2        = NULL,
    .send_frame     = asif_send_frame,
    .receive_packet = asif_receive_packet,
    .close          = asif_encode_close,
    .capabilities   = AV_CODEC_CAP_DELAY,
    .sample_fmts    = (const enum AVSampleFormat[]){ AV_SAMPLE_FMT_U8P,
                                                     AV_SAMPLE_FMT_NONE },
};
