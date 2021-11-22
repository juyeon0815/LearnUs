<template>
  <div v-if="replayList" class="replay-list">
    <div v-if="!currentResult.length" class="empty">
      <img src="@/assets/image/logo/rocket-w500.svg" alt="">
      <span>해당 트랙에 공개된 영상이 없습니다.</span>
    </div>
    <div class="replay-grid">
      <ReplayListItem
        v-for="(replay, idx) in paginatedArea"
        :key="idx"
        :idx="idx"
        :replay="replay"
      />
    </div>
    <ul class="pagination" v-if="totalPage && totalPage > 1">
      <li :class="{ disabled: currentPage === 1 }" @click="changePage(currentPage - 1)">&laquo;</li>
      <li
        :class="{ active: page === currentPage }"
        v-for="page in totalPage"
        :key="page"
        @click="changePage(page)"
      >
        {{ page }}
      </li>
      <li :class="{ disabled: currentPage === totalPage }" @click="changePage(currentPage + 1)">
        &raquo;
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";
import ReplayListItem from "./ReplayListItem";

export default {
  name: "ReplayList",
  components: {
    ReplayListItem,
  },
  data() {
    return {
      currentPage: 1,
    }
  },
  methods: {
    changePage(page) {
      this.currentPage = page;
      document.getElementById("scroll").scrollTop = 0
    },
  },
  computed: {
    ...mapState('broadcast', ['replayList']),
    currentResult() {
      if (this.replayList) {
        let entire = this.replayList.filter( replay => {
            return replay.openYn === 'Y'
          })
        return entire
      }
      return []
    },
    paginatedArea() {
      let start = (this.currentPage - 1) * 12
      let end = this.currentPage * 12
      return this.currentResult.slice(start, end)
    },
    totalPage() {
      const total = this.currentResult.length
      return Math.ceil(total / 12)
    },
  },
}
</script>
