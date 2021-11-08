<template>
  <div id="on-air" class="on-air">
    <div class="content">
      <OnAirVideo/>
    </div>
  </div>
</template>

<script>
import OnAirVideo from '@/components/onAir/OnAirVideo'
export default {
  name: 'ReplayVideo',
  components: {
    OnAirVideo,
  }
}
</script>