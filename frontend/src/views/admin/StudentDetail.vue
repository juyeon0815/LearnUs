<template>
  <div class="admin student-detail">
    <!-- header -->
    <div class="row">
      <header class="header">
        <div class="chapter">ADMIN<span class="t-orange">:</span></div>
        <span class="subtitle">
          교육생 관리
        </span>
      </header>
    </div>
    <StudentInfo :studentInfo="studentInfo" v-if="studentInfo !== null"/>
  </div>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'
import StudentInfo from '@/components/admin/studentDetail/StudentInfo'
import './admin.scss'

export default {
  name: 'StudnetDetail',
  components: {
    StudentInfo
  },
  methods: {
    ...mapActions('admin', ['getSingleStudent']),
    ...mapMutations('admin', ['SET_SINGLE_STUDENT'])
  },
  computed: {
    studentInfo() {
      return this.$store.state.admin.singleStudent
    }
  },
  created() {
    this.SET_SINGLE_STUDENT(null)
    this.getSingleStudent(this.$route.params.id)
  }
}
</script>