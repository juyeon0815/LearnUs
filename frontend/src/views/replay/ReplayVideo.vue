<template>
  <div id="replay" class="replay">
    <div class="replay-video">
      <ReplayVideoPlayer :broadcastId="broadcastId"/> <!-- broadcastId player로 넘겨주기 -->
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
  data() {
    return {
      broadcastId: 0,
    }
  },
  watch: {
    $route() {
      if (this.$route.name === "ReplayVideo") {
        const id = this.$route.params.id;
        console.log("watch 다시보기!! : ", id);
        this.$store.dispatch("replay/getBroadCastInfo", id);
      }
    },
  },
  created(){
    if (this.$route.name === "ReplayVideo") {
      // const id = this.$route.params.id;
      this.broadcastId = this.$route.params.id; // 넘겨주기 위해 broadcastId 저장
      // await this.$store.dispatch("replay/getBroadCastInfo", id);
    }
  }
}
</script>