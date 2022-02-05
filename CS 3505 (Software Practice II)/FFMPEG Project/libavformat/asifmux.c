/*
 * ASIF Audio Format Muxer
 *
 * Authors:
 *      Matthew Johnsen (u1173601) & Isaac Gibson (u1173413)
 */

#include "avformat.h"
#include "rawenc.h"

static int asif_write_packet(AVFormatContext *s, AVPacket *pkt)
{
    avio_write(s->pb, pkt->data, pkt->size);

    return 0;
}

AVOutputFormat ff_asif_muxer = {
    .name         = "asif",
    .long_name    = NULL_IF_CONFIG_SMALL("ASIF Muxer"),
    .extensions   = "asif",
    .audio_codec  = AV_CODEC_ID_ASIF,
    .video_codec  = AV_CODEC_ID_NONE,
    .write_packet = asif_write_packet,
    .flags        = AVFMT_NOTIMESTAMPS,
};
