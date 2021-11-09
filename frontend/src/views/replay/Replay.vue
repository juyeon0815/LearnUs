<template>
  <div class="replay">
    <div class="row">
      <span class="title">RE<span class="colon">:</span>PLAY</span>
    </div>
    <TrackInfo v-if="this.$route.params.category != 'all'" />
    <ReplayList />
  </div>
</template>

<script>
import "./replay.scss";

import ReplayList from "@/components/replay/ReplayList";
import TrackInfo from "@/components/replay/ReplaySelect";

export default {
  name: "Replay",
  data: () => {
    return {
      track: "",
    };
  },
  components: {
    ReplayList,
    TrackInfo,
  },
  watch: {
    $route() {
      if (this.$route.name === "Replay") {
        const category = this.$route.params.category;
        if (category === "all") {
          this.$store.commit("replay/SET_BROADCASTS_TRACK", null);
        }
        this.$store.dispatch("replay/getBroadCasts", this.$store.state.account.userInfo.ordinalNo);
      }
    },
  },
  created(){
    if (this.$route.name === "Replay") {
      const category = this.$route.params.category;
        if (category === "all") {
          this.$store.commit("replay/SET_BROADCASTS_TRACK", null);
        }
        this.$store.dispatch("replay/getBroadCasts", this.$store.state.account.userInfo.ordinalNo);
      }
  }
};
</script>
