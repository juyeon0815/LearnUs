<template>
  <div class="on-air studio">
    <div class="content">
      <OnAirControl/>
      <OnAirChat/>
    </div>
  </div>
</template>

<script>
import OnAirChat from '@/components/onAir/OnAirChat'
import OnAirControl from '@/components/onAir/OnAirControl'

export default {
  name: 'OnAirStudio',
  components: {
    OnAirChat,
    OnAirControl
  },
  created() {
    this.$store.dispatch('broadcast/getBroadcastDetail', this.$route.params.id)
  },
}
</script>

<style>

</style>