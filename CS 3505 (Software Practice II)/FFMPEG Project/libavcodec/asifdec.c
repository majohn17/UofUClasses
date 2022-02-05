/*
 * ASIF Audio Format Decoder
 *
 * Authors:
 *      Matthew Johnsen (u1173601) & Isaac Gibson (u1173413)
 */

#include "libavutil/channel_layout.h"

#define BITSTREAM_READER_LE
#include "avcodec.h"
#include "bytestream.h"
#include "get_bits.h"
#include "internal.h"
#include "thread.h"
#include "unary.h"
#include "wavpack.h"
#include "dsd.h"

static int asif_decode(AVCodecContext *avctx, void *data,
                       int *got_frame_ptr, AVPacket *avpkt)
{
   const uint8_t *buf;
   int ret, i, j;

   AVFrame *frame;
   frame = data;

   buf = avpkt->data;

   // Initialize frame information and avcodec information
   bytestream_get_le32(&buf);
   avctx->sample_rate = bytestream_get_le32(&buf);
   avctx->channels = bytestream_get_le16(&buf);
   frame->nb_samples = bytestream_get_le32(&buf);
   avctx->sample_fmt = AV_SAMPLE_FMT_U8P;

   // Allocate memory for the data buffer
   if ((ret = ff_get_buffer(avctx, frame, 0)) < 0)
       return ret;

   // Fill each of the planes of data in the frame
   for (i = 0; i < frame->channels; i++)
   {
       uint8_t *samples;
       uint8_t start;
       start = bytestream_get_byte(&buf);
       samples =  frame->extended_data[i];
       samples[0] = start;

       for (j = 1; j < frame->nb_samples; j++)
       {
           uint8_t current;
           current = bytestream_get_byte(&buf);
           start = start + current;
           samples[j] = start;
       }
   }

   *got_frame_ptr = 1;

   return avpkt->size;
}

AVCodec ff_asif_decoder = {
    .name           = "asif",
    .long_name      = NULL_IF_CONFIG_SMALL("ASIF Decoder"),
    .type           = AVMEDIA_TYPE_AUDIO,
    .id             = AV_CODEC_ID_ASIF,
    .decode         = asif_decode,
    .capabilities   = AV_CODEC_CAP_DR1,
};
