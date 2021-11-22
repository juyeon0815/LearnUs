<template>
  <div class="replay-player">
    <video 
      ref="videoPlayer" 
      class="video-js vjs-fluid"
    ></video>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import videojs from "video.js";
import "video.js/dist/video-js.css";

export default {
  name: 'BroadcastVideoPlayer',
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
  }
}
</script>

<style>

</style>