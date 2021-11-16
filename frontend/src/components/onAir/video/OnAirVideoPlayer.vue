<template>
  <div class="video-player">
    <video 
      ref="videoPlayer" 
      class="video-js vjs-fluid"
    ></video>
    <div v-if="broadcastDetail && broadcastDetail.liveCode === 'C'" class="video-empty">
      <i class="fi fi-rr-rocket"></i>
      <span>라이브 방송 준비 중입니다.</span> 
    </div>
    <div v-else-if="broadcastDetail && broadcastDetail.liveCode === 'N'" class="video-empty">
      <i class="fi fi-rr-rocket"></i>
      <span>라이브 방송이 종료되었습니다.</span> 
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import videojs from 'video.js';
import 'video.js/dist/video-js.css'

export default {
  name: 'OnAirVideoPlayer',
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
				autoplay: 'any',
				controls: true,
        fluid: true,
        liveui: true,
        playbackRates: [0.5, 1, 1.5, 2],
        poster: this.broadcastDetail.thumbnailUrl,
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