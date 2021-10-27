<template>
  <div class="on-air">
    <div class="content">
      <OnAirVideo/>
      <OnAirChat/>
    </div>
  </div>
</template>

<script>
import './onAir.scss'
import OnAirVideo from '@/components/onAir/OnAirVideo'
import OnAirChat from '@/components/onAir/OnAirChat'
export default {
  name: 'OnAir',
  components: {
    OnAirVideo,
    OnAirChat
  }
}
</script>