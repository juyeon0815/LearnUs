<template>
  <div class="replay">
    <header class="header">
      <div class="chapter">REPLAY<span class="t-orange">:</span></div>
      <span class="subtitle">
        선택 트랙
      </span>
    </header>

    <!-- <ReplaySelect v-if="this.$route.params.category != 'all'" /> -->
    <ReplayList />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import "./replay.scss";
import ReplayList from "@/components/replay/ReplayList";
// import ReplaySelect from "@/components/replay/ReplaySelect";

export default {
  name: "Replay",
  data: () => {
    return {
      track: "",
    };
  },
  components: {
    ReplayList,
    // ReplaySelect,
  },
  computed: {
    ...mapState('account', ['userInfo'])
  },
  watch: {
    '$route'() {
      const trackId = this.$route.params.track
      if (!trackId || trackId === '0') {
        this.$store.dispatch('broadcast/getReplayList', this.userInfo.ordinalNo)
      } else {
        const data = {
          id: trackId,
          ordinalNo: this.userInfo.ordinalNo
        }
        this.$store.dispatch('broadcast/getReplayTrackList', data)
      }
    },
  },
  created () {
    const trackId = this.$route.params.track
    if (trackId === '0') {
      this.$store.dispatch('broadcast/getReplayList', this.userInfo.ordinalNo)
    } else {
      const data = {
        id: trackId,
        ordinalNo: this.userInfo.ordinalNo
      }
      this.$store.dispatch('broadcast/getReplayTrackList', data)
    }
  }
};
</script>
