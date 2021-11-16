<template>
  <div class="popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>Students<span class="t-orange">:</span></h1>
      <div class="student-table">
        <table>
          <thead>
            <tr>
              <th>No.</th>
              <th>이름</th>
              <th>학번</th>
              <th>분반</th>
              <th>출석 여부</th>
            </tr>
          </thead>
          <tbody>
            <ControlStudentTableRow
              v-for="(student, idx) in paginatedArea"
              :key="idx"
              :student="student"
              :idx="idx + (currentPage - 1) * 10"
            />
          </tbody>
        </table>
        <div v-if="!studentData.length" class="empty">
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
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ControlStudentTableRow from './ControlStudentRow.vue'
export default {
  name: 'ControlStudentTable',
  components: {
    ControlStudentTableRow
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
    ...mapGetters('broadcast', ['studentData']),
    paginatedArea () {
      let start = (this.currentPage - 1) * 10
      let end = this.currentPage * 10
      return this.studentData.slice(start, end)
    },
    totalPage () {
      const total = this.studentData.length
      return Math.ceil(total/10)
    },
  }
}
</script>

<style>

</style>