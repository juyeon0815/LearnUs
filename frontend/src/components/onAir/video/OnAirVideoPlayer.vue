<template>
  <div class="video-player">
    <video ref="videoPlayer" class="video-js vjs-fluid"></video>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
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
  methods: {
    ...mapActions('broadcast', ['getBroadcastDetail'])
  },
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
    options() {
      return {
				autoplay: true,
				controls: true,
        fluid: true,
        playbackRates: [0.5, 1, 1.5, 2],
				sources: [
					{
						src: `https://d31f0osw72yf0h.cloudfront.net/${this.broadcastDetail.streamingKey}.m3u8`,
						type: "application/x-mpegURL"
					}
				],
			}
    },
  },
  created () {
    this.getBroadcastDetail(this.$route.params.id)
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