<template>
  <div class="student-table">
    <table>
      <thead>
        <tr>
          <th>No.</th>
          <th>이름</th>
          <th>학번</th>
          <th>분반</th>
          <th>연락처</th>
        </tr>
      </thead>
      <tbody>
        <StudentTableRow
          v-for="(student, idx) in paginatedArea"
          :key="idx"
          :student="student"
          :idx="idx + (currentPage - 1) * 10"
        />
      </tbody>
    </table>
    <div v-if="!currentResult.length" class="empty">
      검색 결과가 없습니다.
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
import StudentTableRow from './StudentTableRow.vue'
export default {
  name: 'StudentTable',
  components: {
    StudentTableRow
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
    ...mapState('admin', ['students', 'selectedOrd', 'searchWord']),
    currentResult () {
      return this.students[this.selectedOrd]
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

<style>

</style>