<template>
  <div class="video-player">
    <video ref="videoPlayer" class="video-js vjs-16-9"></video>
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
				sources: [
					{
						src: require('@/assets/image/test/test.mp4'),
						type: "video/mp4"
					}
				],
        width: this.videoWidth
			}
    },
  },
  methods: {
    calWidth () {
      const fullWidth = document.getElementById('on-air').offsetWidth
      if ( fullWidth * 0.7 < 750) {
        this.videoWidth = 750
      } else {
        this.videoWidth = fullWidth * 0.7
      }
    }
  },
  mounted() {
    this.player = videojs(
      this.$refs.videoPlayer, 
      this.options, 
      function onPlayerReady() {
        console.log('onPlayerReady', this);
      }
    )
    this.calWidth()
    window.addEventListener('resize', () => {
      this.calWidth()
    })
  },
  beforeUnmount() {
    if (this.player) {
        this.player.dispose()
    }
  }
}
</script>