<template>
  <div class="popup control-gifticon">
    <div class="popup-box">
      <div 
        :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert']" 
        v-if="isAlertShow">{{ alertInfo.message }}</div>
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>Search<span class="t-orange">:</span></h1>
      <div class="input-box">
        <input 
          type="text"
          ref="nameInput"
          placeholder="교육생 이름을 입력해주세요."
          @input="insertStudentName"
          @keyup.enter="searchStudent">
        <i 
          class="fi fi-rr-search search-btn"
          @click="searchStudent"></i>
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
              @onAlert="onAlert"
            />
          </tbody>
        </table>
        <div v-if="!searchedList.length" class="empty popup-empty">
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
      alertInfo: {
        type: null,
        message: '',
      },
      isAlertShow: false,
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
      }
    },
    onAlert(info) {
      this.alertInfo = info
      this.isAlertShow = true
      setTimeout(() => {
        this.isAlertShow = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
      }, 2000)
    }
  },
  computed: {
    ...mapGetters('broadcast', ['entireStudentList']),
  }
}
</script>