<template>
  <div class="replay-video" style="padding-top: 40px;">
    <ReplayVideoPlayer/>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ReplayVideoPlayer from '@/components/replay/video/ReplayVideoPlayer'

export default {
  name: 'ReplayDetail',
  components: {
    ReplayVideoPlayer
  },
  computed: {
    ...mapState('broadcast', ['replayDetail'])
  },
  created () {
    this.$store.dispatch('broadcast/getReplayDetail', this.$route.params.id)
  },
  unmounted () {
    this.$store.commit('broadcast/SET_REPLAY_DETAIL', null)
  }
}
</script>