/*
 * ASIF Audio Format Demuxer
 *
 * Authors:
 *      Matthew Johnsen (u1173601) & Isaac Gibson (u1173413)
 */

#include "avformat.h"
#include "internal.h"
#include "pcm.h"
#include "libavutil/log.h"
#include "libavutil/opt.h"
#include "libavutil/avassert.h"

// Returns a high score if the format is ASIF
static int asif_read_probe(const AVProbeData *p)
{
    /* check file header */
    if (p->buf_size <= 32)
        return 0;
    if (memcmp(p->buf, "asif", 4) == 0)
        return AVPROBE_SCORE_MAX;
    return 0;
}

// Initializes needed information for the packet
static int asif_read_header(AVFormatContext *s)
{
    AVStream *st = avformat_new_stream(s, NULL);
    if (!st)
        return AVERROR(ENOMEM);
    st->codecpar->codec_type = AVMEDIA_TYPE_AUDIO;
    st->codecpar->codec_id = s->iformat->raw_codec_id;
    st->need_parsing = AVSTREAM_PARSE_FULL_RAW;
    st->start_time = 0;
    /* the parameters will be extracted from the compressed bitstream */

    return 0;
}

// Reads all the audio data into the packet's data buffer
static int asif_read_packet(AVFormatContext *s, AVPacket *pkt)
{
    int ret, size;

    size = avio_size(s->pb);

    if ((ret = av_new_packet(pkt, size)) < 0)
        return ret;

    pkt->pos= avio_tell(s->pb);
    pkt->stream_index = 0;
    ret = avio_read(s->pb, pkt->data, size);
    if (ret < 0) {
        av_packet_unref(pkt);
        return ret;
    }
    return ret;
}

AVInputFormat ff_asif_demuxer = {
    .name           = "asif",
    .long_name      = NULL_IF_CONFIG_SMALL("ASIF Demuxer"),
    .priv_data_size = 0,
    .read_probe     = asif_read_probe,
    .read_header    = asif_read_header,
    .read_packet    = asif_read_packet,
    .extensions     = "asif",
    .raw_codec_id   = AV_CODEC_ID_ASIF,
};
