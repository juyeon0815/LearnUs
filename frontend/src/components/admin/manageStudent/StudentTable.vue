<template>
  <div class="student-table">
    <StudentDetail v-if="onStudentDetail" @close="onStudentDetail = false"/>
    <table>
      <thead>
        <tr>
          <th>No.</th>
          <th>이름</th>
          <th>학번</th>
          <th>분반</th>
          <th>트랙</th>
          <th>연락처</th>
        </tr>
      </thead>
      <tbody>
        <StudentTableRow
          v-for="(student, idx) in paginatedArea"
          :key="idx"
          :student="student"
          :idx="idx + (currentPage - 1) * 10"
          @open-detail="onStudentDetail = true"
        />
      </tbody>
    </table>
    <div v-if="currentResult && !currentResult.length" class="empty">
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
import StudentDetail from './StudentDetail.vue'
import StudentTableRow from './StudentTableRow.vue'
export default {
  name: 'StudentTable',
  components: {
    StudentDetail,
    StudentTableRow
  },
  data () {
    return {
      currentPage: 1,
      onStudentDetail: false,
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
      if (!this.students) { return [] }
      const entire = this.students[this.selectedOrd]
      if (this.searchWord.length) {
        const result = entire.filter(student => {
          let { name, userId, region, classNo, phone } = student
          let track = student.track.trackName
          let data = [
            name, String(userId).padStart(7, '0'),
            region, classNo + '반',
            track, phone
          ]
          return data.join(' ').includes(this.searchWord)
        })
        return result
      }
      return entire
    },
    paginatedArea () {
      let start = (this.currentPage - 1) * 10
      let end = this.currentPage * 10
      if (this.currentResult) {
        return this.currentResult.slice(start, end)
      }
      return null
    },
    totalPage () {
      if (this.currentResult) {
        const total = this.currentResult.length
        return Math.ceil(total/10)
      }
      return null
    },
  },
  watch: {
    searchWord(val) {
      if (val.length) {
        this.currentPage = 1
      }
    }
  }
}
</script>

<style>

</style>