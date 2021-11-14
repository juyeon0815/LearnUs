<template>
  <div class="popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>Search<span class="t-orange">:</span></h1>
      <div class="input-box">
        <input 
          type="text"
          @input="insertStudentName"
          @keyup.enter="searchStudent">
        <button
          @click="searchStudent">
          검색</button>
      </div>
      <div class="student-table" v-if="searchedList">
        <table>
          <thead>
            <tr>
              <th>No.</th>
              <th>이름</th>
              <th>학번</th>
              <th>분반</th>
            </tr>
          </thead>
          <tbody class="popup-tbody">
            <StudentSearchRow
              v-for="(student, idx) in searchedList"
              :key="idx"
              :student="student"
              :idx="idx"
            />
          </tbody>
        </table>
        <div v-if="!searchedList.length" class="empty">
          검색 결과가 없습니다.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import StudentSearchRow from './StudentSearchRow.vue'

export default {
  name: 'GifticonSearchModal',
  components: {
    StudentSearchRow
  },
  data() {
    return {
      currentPage: 1,
      studentName: null,
      searchedList: null,
    }
  },
  methods: {
    insertStudentName (event) {
      this.studentName = event.target.value
    },
    searchStudent() {
      if (this.entireStudentList) {
        const searchedList = this.entireStudentList.filter(student => {
          return student.user.name.includes(this.studentName)
        })
        this.searchedList = searchedList
        this.studentName = null
      }
    },
  },
  computed: {
    ...mapGetters('broadcast', ['entireStudentList']),
  }
}
</script>