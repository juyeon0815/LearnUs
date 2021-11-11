<template>
  <div class="video-player">
    <video ref="videoPlayer" class="video-js vjs-fluid"></video>
  </div>
  {{url}}
</template>

<script>

import './replayVideo.scss'

import videojs from 'video.js';
import 'video.js/dist/video-js.css'
import { mapState } from "vuex";
export default {
  name: 'OnAirVideoPlayer',
  data () {
    return {
      player: null,
      videoWidth: 0,
      url : '',
    }
  },
  computed: {
    ...mapState("replay",["broadCastInfo"]),
    options() {
      return {
				autoplay: true,
				controls: true,
        fluid: true,
        playsinline : true,
        muted : true, 
        controlBar : { 
          playToggle : false, 
          pictureInPictureToggle : false, 
          remainingTimeDisplay : false, 
          progressControl : true, 
        },
        playbackRates: [0.5, 1, 1.5, 2],
				sources: [
					{
						// src: require('@/assets/image/test/test.mp4'),
						// type: "video/mp4",

            //다시보기
            // src:this.url,
            src:"https://d31f0osw72yf0h.cloudfront.net/qwe.m3u8",
            type:"application/x-mpegURL",
					}
				],
			}
    },
  },
  mounted() {
    this.url = this.broadCastInfo.replayUrl
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

<style type="text/css">
    .video-js .vjs-time-control{display:block;}
</style>