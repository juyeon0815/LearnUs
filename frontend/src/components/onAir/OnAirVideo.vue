<template>
  <div class="on-air-video">
    <OnAirVideoPlayer/>
    <OnAirVideoInfo/>
  </div>
</template>

<script>
import './onAirVideo.scss'
import OnAirVideoPlayer from './video/OnAirVideoPlayer.vue'
import OnAirVideoInfo from './video/OnAirVideoInfo.vue'

export default {
  name: 'OnAirVideo',
  components: {
    OnAirVideoPlayer,
    OnAirVideoInfo
  }
}
</script>