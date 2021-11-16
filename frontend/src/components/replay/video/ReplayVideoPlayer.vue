<template>
  <div class="replay-player">
    <video 
      ref="videoPlayer" 
      class="video-js vjs-fluid"
    ></video>
    <div v-if="replayDetail" class="replay-info">
      <h3 class="title">
        <span>[강의 다시보기]</span>
        {{ replayDetail.broadcast.title }}
      </h3>
      <div class="date">{{ broadcastDate }}</div>
      <div class="instructor">
        <span>by. </span>
        {{ replayDetail.broadcast.teacher }}
      </div>
    </div>
  </div>
</template>

<script>
import './replayVideo.scss'
import { mapGetters, mapState } from "vuex";
import moment from 'moment'
import videojs from "video.js";
import "video.js/dist/video-js.css";

export default {
  name: 'ReplayVideoPlayer',
  data() {
    return {
      player: null,
    }
  },
  computed: {
    ...mapState('broadcast', ['replayDetail']),
    ...mapGetters('broadcast', ['hasReplay']),
    options() {
      return {
        autoplay: 'any',
        controls: true,
        fluid: true,
        poster: this.replayDetail.broadcast.thumbnailUrl,
        liveui: this.mediaType.startsWith('a') ? true : false,
        playbackRates: [0.5, 1, 1.5, 2],
        sources: [
          {
            src: this.replayDetail.replayUrl,
            type: this.mediaType,
          },
        ],
      }
    },
    mediaType () {
      const src = this.replayDetail.replayUrl.split('.')
      const exte = src[src.length-1]
      if (exte === 'm3u8') {
        return 'application/x-mpegURL'
      }
      return 'video/mp4'
    },
    broadcastDate () {
      return moment(this.replayDetail.broadcast.broadcastDate).locale('ko').format('LL');
    }
  },
  watch: {
    hasReplay (val) {
      if (val) {
        this.player = videojs(
          this.$refs.videoPlayer, 
          this.options, 
          function ready() {
            this.currentTime(0);
          }
        )
        this.player.removeChild("BigPlayButton")
      } else {
        if (this.player) {
          this.player.dispose()
        }
      }
    },
  },
  mounted() {
    if (this.hasReplay) {
      this.player = videojs(
        this.$refs.videoPlayer, 
        this.options, 
        function ready() {
          this.currentTime(0);
        }
      )
      this.player.removeChild("BigPlayButton")
    }
  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose()
    }
  },
};
</script>

<style></style>
