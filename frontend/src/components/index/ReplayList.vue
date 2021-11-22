<template>
  <div class="replay-list">
    <div class="chapter">RE<span class="t-orange">:</span>PLAY</div>
    <div class="col" v-if="indexReplayList && indexReplayList.length">
      <ReplayListItem
        v-for="(replay, idx) in indexReplayList"
        :key="idx"
        :replay="replay"
      />
      <div class="more" @click="$router.push({
        name: 'Replay', 
        params: {
          track: 0
        },
        query: {
          subject: 0
        }
      })">
        + View More 👀
      </div>
    </div>
    <div class="col" v-if="indexReplayList && !indexReplayList.length">
      <div class="replay-item">등록된 다시보기 영상이 없습니다</div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import ReplayListItem from './ReplayListItem.vue'

export default {
  name: 'ReplayList',
  components: {
    ReplayListItem
  },
  methods: {
    ...mapActions('broadcast', ['getIndexReplayList']),
    ...mapMutations('broadcast', ['SET_INDEX_REPLAY_LIST']),
  },
  computed: {
    ...mapState('broadcast', ['indexReplayList'])
  },
  created() {
    this.getIndexReplayList(this.$store.state.account.userInfo.userId)
  },
  unmounted() {
    this.SET_INDEX_REPLAY_LIST(null)
  }
}
</script>