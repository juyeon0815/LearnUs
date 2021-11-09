<template>
  <div class="video-preview">
    <video ref="videoPlayer" class="video-js vjs-fluid"></video>
  </div>
</template>

<script>
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
    options() {
      return {
				autoplay: true,
				controls: true,
        fluid: true,
				sources: [
					{
						src: "https://d31f0osw72yf0h.cloudfront.net/test.m3u8",
						type: "application/x-mpegURL"
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
