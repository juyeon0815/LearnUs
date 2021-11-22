<template>
  <div class="video-preview">
    <video 
      ref="videoPlayer" 
      class="video-js vjs-fluid"
    ></video>
    <div v-if="!isLive" class="video-empty">
      <i class="fi fi-rr-rocket"></i>
      <span>진행 중인 실시간 방송이 없습니다.</span> 
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import videojs from 'video.js';
import 'video.js/dist/video-js.css'

export default {
  name: 'VideoPreview',
  data () {
    return {
      player: null,
      videoWidth: 0,
    }
  },
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
    ...mapGetters('broadcast', ['isLive']),
    options() {
      return {
				autoplay: false,
				controls: true,
        // poster: this.broadcastDetail.thumbnailUrl,
        fluid: true,
        liveui: true,
				sources: [
					{
						src: `https://d31f0osw72yf0h.cloudfront.net/${this.broadcastDetail.streamingKey}.m3u8`,
						type: "application/x-mpegURL"
					}
				],
			}
    },
  },
  watch: {
    isLive (val) {
      if (val) {
        this.player = videojs(
          this.$refs.videoPlayer, 
          this.options
        )
        this.player.removeChild('BigPlayButton');
      } else {
        if (this.player) {
          this.player.dispose()
        }
      }
    },
  },
  mounted() {
    if (this.isLive && this.broadcastDetail) {
      this.player = videojs(
          this.$refs.videoPlayer, 
          this.options
        )
        this.player.removeChild('BigPlayButton');
    }
  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose()
    }
  }
}
</script>