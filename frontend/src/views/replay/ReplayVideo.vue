<template>
  <div id="replay" class="replay">
    <div class="replay-video">
      <ReplayVideoPlayer />
      <ReplayVideoInfo />
    </div>
  </div>
</template>

<script>
import './replayVideo.scss'

import ReplayVideoInfo from '@/components/replay/video/ReplayVideoInfo'
import ReplayVideoPlayer from '@/components/replay/video/ReplayVideoPlayer'

export default {
  name: 'ReplayVideo',
  components: {
    ReplayVideoInfo,
    ReplayVideoPlayer
  },
  watch: {
    $route() {
      if (this.$route.name === "ReplayVideo") {
        const id = this.$route.params.id;
        this.$store.dispatch("replay/getBroadCastInfo", id);
      }
    },
  },
  created(){
    if (this.$route.name === "ReplayVideo") {
      const id = this.$route.params.id;
      this.$store.dispatch("replay/getBroadCastInfo", id);
    }
  }
}
</script>