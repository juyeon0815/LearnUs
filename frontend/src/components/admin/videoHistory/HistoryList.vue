<template>
  <div class="schedule-list history-schedule">
    <HistoryListItem
      v-for="(broadcast, idx) in paginatedArea"
      :key="idx"
      :broadcast="broadcast"/>
    <ul class="pagination" v-if="totalPage && totalPage > 1">
      <li
        :class="{ disabled: currentPage === 1 }"
        @click="changePage(currentPage - 1)"
      >&laquo;</li>
      <li
        :class="{ active: page === currentPage }"
        v-for="page in totalPage"
        :key="page"
        @click="changePage(page)"
      >
        {{ page }}
      </li>
      <li
        :class="{ disabled: currentPage === totalPage }"
        @click="changePage(currentPage + 1)"
      >&raquo;</li>
    </ul>
  </div>
</template>

<script>
import HistoryListItem from './HistoryListItem'

export default {
  name: 'HistoryList',
  components: {
    HistoryListItem,
  },
  props: {
    historyList: Array,
  },
  data () {
    return {
      currentPage: 1,
    }
  },
  methods: {
    changePage(page) {
      this.currentPage = page
    },
  },
  computed: {
    paginatedArea () {
      let start = (this.currentPage - 1) * 10
      let end = this.currentPage * 10
      return this.historyList.slice(start, end)
    },
    totalPage () {
      const total = this.historyList.length
      return Math.ceil(total/10)
    },
  }
}
</script>
