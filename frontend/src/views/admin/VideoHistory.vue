<template>
  <div class="admin live-schedule history">
    <div class="row">
      <header class="header">
        <div class="chapter">History<span class="t-orange">:</span></div>
        <span class="subtitle">
          교육 영상 관리
        </span>
      </header>
    </div>
    <HistoryList v-if="historyList" :historyList="historyList"/>
    <div
      v-if="historyList && !historyList.length"
      class="empty">
      <img src="@/assets/image/logo/rocket-w500.svg" alt="">
      <span>지난 교육 영상이 존재하지 않습니다.</span>
    </div>
  </div>
</template>

<script>
import './videoHistory.scss'
import { mapActions, mapMutations, mapState } from 'vuex'
import HistoryList from '@/components/admin/videoHistory/HistoryList.vue'

export default {
  name: 'VideoHistory',
  components: {
    HistoryList,
  },
  methods: {
    ...mapActions('broadcast', ['getHistoryList']),
    ...mapMutations('broadcast', ['SET_HISTORY_LIST'])
  },
  computed: {
    ...mapState('broadcast', ['historyList'])
  },
  created() {
    this.getHistoryList()
  },
  unmounted() {
    this.SET_HISTORY_LIST(null)
  },
}
</script>

<style>

</style>