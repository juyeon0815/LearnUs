<template>
  <div id="on-air" class="on-air">
    <div class="content">
      <OnAirVideo/>
      <OnAirChat/>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import './onAir.scss'
import OnAirVideo from '@/components/onAir/OnAirVideo'
import OnAirChat from '@/components/onAir/OnAirChat'
export default {
  name: 'OnAir',
  components: {
    OnAirVideo,
    OnAirChat
  },
  methods: {
    ...mapActions('broadcast', ['getBroadcastDetail'])
  },
  created () {
    this.getBroadcastDetail(this.$route.params.id)
  },
  unmounted () {
    this.$store.commit('broadcast/SET_BROADCAST_DETAIL', null)
  }
}
</script>