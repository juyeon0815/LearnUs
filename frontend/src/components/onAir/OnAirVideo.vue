<template>
  <div class="on-air-video">
    <OnAirVideoPlayer/>
    <OnAirVideoInfo/>
  </div>
</template>

<script>
import OnAirVideoPlayer from './OnAirVideoPlayer.vue'
import OnAirVideoInfo from './OnAirVideoInfo.vue'

export default {
  name: 'OnAirVideo',
  components: {
    OnAirVideoPlayer,
    OnAirVideoInfo
  }
}
</script>