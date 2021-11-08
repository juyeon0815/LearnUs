<template>
  <div class="replay-list">
    <div id="scroll" class="content">
    <ReplayListItem
    v-for="idx in 15"
    :key="idx"
    :idx="idx"/>
    </div>
    
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
import { mapState } from 'vuex'
import ReplayListItem from './ReplayListItem'

export default {
  name: 'ReplayList',
  components: {
    ReplayListItem,
  },
  data () {
    return {
      currentPage: 1,
    }
  },
  methods: {
    changePage(page) {
      this.currentPage = page
      document.getElementById('scroll').scrollTop = 0
    },
  },
    computed: {
    ...mapState('admin', ['students', 'selectedOrd', 'searchWord']),
    currentResult () {
      const entire = this.students[this.selectedOrd]
      return entire
    },
    paginatedArea () {
      let start = (this.currentPage - 1) * 10
      let end = this.currentPage * 10
      return this.currentResult.slice(start, end)
    },
    totalPage () {
      const total = this.currentResult.length
      return Math.ceil(total/10)
    },
  }
}
</script>
