<template>
  <div class="search-bar">
      <select 
        class="subject-select"
        v-model="subjectName"
        @change="changeSubject"
      >
        <option 
          class="decorated"
          v-for="subject in subjectList"
          :value="subject.subjectName"
          :key="subject.trackSubjectId"
        >{{ subject.subjectName }}</option>
      </select>
    <!-- <input 
      type="text"
      placeholder=" 검색어를 입력하세요"
      :value="target"
      @input="inputText"
    >
    <i class="search-btn fi fi-rr-search-heart"></i> -->
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
// import adminApi from '@/api/admin'
export default {
  name: 'SearchBar',
  data () {
    return {
      ord: null,
      target: '',
      userInfo: null,
      subjectName: ''
    }
  },
  methods: {
    inputText (event) {
      this.$store.commit('admin/SET_SEARCH_WORD', event.target.value)
    },
  }, 
  computed: {
    ...mapState('admin', ['selectedOrd','subjects', 'searchWord']),
    ...mapGetters('admin', ['ordinal']),
    subjectList () {
      return this.subjects.filter(subject => {
        return subject.trackSetting.ordinalNo === this.userInfo.ordinalNo
      })
    },
  },
  mounted () {
  },
  beforeUnmount () {
  },
  created() {
    this.userInfo = this.$store.state.account.userInfo
    console.log("userInfo", this.userInfo)
    if (this.subjectList.length === 1) {
      this.subjectName = this.subjectList[0].subjectName
    } else if (this.subjectList.length > 1) {
      const current = this.subjectList.find((subject) => {
        return subject.nowSubject === 'Y'
      })
      this.subjectName = current.subjectName
    }
  }
}
</script>