<template>
  <div class="video-player">
    <video id="my-video" ref="videoPlayer" class="video-js vjs-fluid"></video>
  </div>
  {{ url }}
</template>

<script>
import "./replayVideo.scss";

import videojs from "video.js";
import "video.js/dist/video-js.css";
import { mapState } from "vuex";
export default {
  name: "OnAirVideoPlayer",
  props: {
    broadcastId: String,
  },
  data() {
    return {
      player: null,
      videoWidth: 0,
      url: "",
    };
  },
  computed: {
    ...mapState("replay", ["broadCastInfo"]),
    options() {
      return {
        autoplay: true,
        controls: true,
        fluid: true,
        playbackRates: [0.5, 1, 1.5, 2],
        sources: [
          {
            src: this.url,
            // src: "https://d31f0osw72yf0h.cloudfront.net/qwe.m3u8",
            type: "application/x-mpegURL",
          },
        ],
      };
    },
  },
  async mounted() {
    await this.$store.dispatch("replay/getBroadCastInfo", this.broadcastId); // broadcastId 업데이트 -> async await 걸어서 순서대로 처리되도록!
    this.url = this.broadCastInfo.replayUrl;
    console.log("ReplayVideoPlayer : "+this.url);

    this.player = videojs(this.$refs.videoPlayer, this.options, function ready() {
      this.currentTime(0);
    });
    this.player.removeChild("BigPlayButton");

  },
  beforeUnmount() {
    if (this.player) {
      this.player.dispose();
    }
  },
};
</script>

<style></style>
