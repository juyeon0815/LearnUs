<template>
  <div class="replay">
    <header class="header">
      <div class="chapter">REPLAY<span class="t-orange">:</span></div>
      <ReplaySelect 
        v-if="this.$route.query.subject != '0' || this.isAdmin" />
    </header>
    <ReplayList/>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import "./replay.scss";
import ReplayList from "@/components/replay/ReplayList";
import ReplaySelect from "@/components/replay/ReplaySelect";

export default {
  name: "Replay",
  data: () => {
    return {
      track: "",
    };
  },
  components: {
    ReplayList,
    ReplaySelect,
  },
  computed: {
    ...mapState('account', ['userInfo']),
    ...mapGetters('account', ['isAdmin']),
    ...mapState('admin', ['ordinalNo'])
  },
  watch: {
    '$route'() {
      if (this.userInfo) {
        if (this.isAdmin) {
          this.$store.dispatch('broadcast/getReplayList', this.ordinalNo[0])
        } else {
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
        }
      }
    },
  },
  created () {
    if (this.userInfo) {
      if (this.isAdmin) {
        this.$store.dispatch('broadcast/getReplayList', this.ordinalNo[0])
      } else {
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
      }
    }
  },
  unmounted () {
    this.$store.commit('broadcast/SET_REPLAY_LIST', null)
  }
};
</script>
