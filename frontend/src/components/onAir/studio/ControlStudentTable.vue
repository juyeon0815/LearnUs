<template>
  <div class="popup">
    <div class="popup-box">
      <div class="student-table">
        <table>
          <thead>
            <tr>
              <th>No.</th>
              <th>이름</th>
              <th>학번</th>
              <th>분반</th>
              <th>출석 시간</th>
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
    </div>
  </div>
</template>

<script>
import ControlStudentTableRow from './ControlStudentRow.vue'
export default {
  name: 'ControlStudentTable',
  components: {
    ControlStudentTableRow
  }
}
</script>

<style>

</style>