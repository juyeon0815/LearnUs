<template>
  <div class="video-player">
    <video ref="videoPlayer" class="video-js vjs-fluid"></video>
  </div>
</template>

<script>
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
    options() {
      return {
				autoplay: true,
				controls: true,
        fluid: true,
        playbackRates: [0.5, 1, 1.5, 2],
				sources: [
					{
						src: require('@/assets/image/test/test.mp4'),
						type: "video/mp4"
					}
				],
			}
    },
  },
  mounted() {
    this.player = videojs(
      this.$refs.videoPlayer, 
      this.options, 
      function onPlayerReady() {
        console.log('onPlayerReady', this);
      }
    )
    this.player.removeChild('BigPlayButton');
  },
  beforeUnmount() {
    if (this.player) {
        this.player.dispose()
    }
  }
}
</script>