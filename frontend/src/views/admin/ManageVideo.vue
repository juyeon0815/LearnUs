<template>
  <div class="admin video">
    <header class="header">
      <div class="chapter">ADMIN<span class="t-orange">:</span></div>
      <span class="subtitle">
        교육 영상 관리
      </span>
    </header>
    <div class="content">
      <div class="tabs">
        <span 
          :class="[!page ? 'active' : '', 'tab-item']" 
          @click="changePage(0)"
        >방송 정보</span>
        <span 
          :class="[page === 1 ? 'active' : '', 'tab-item']" 
          @click="changePage(1)"
        >출석 결과</span>
        <span 
          :class="[page === 2 ? 'active' : '', 'tab-item']" 
          @click="changePage(2)"
        >퀴즈 결과</span>
      </div>
      <BroadcastInfo v-if="!page"/>
      <div v-else-if="page === 1">
        <BroadcastAttendance/>
        <ControlStudent/>
      </div>
      <QuizResult v-else-if="page === 2"/>
    </div>
  </div>
</template>

<script>
import './manageVideo.scss'
import BroadcastInfo from '@/components/admin/manageVideo/BroadcastInfo'
import BroadcastAttendance from '@/components/admin/manageVideo/BroadcastAttendance'
import QuizResult from '@/components/admin/manageVideo/QuizResult'
import ControlStudent from '@/components/onAir/studio/ControlStudent.vue'
export default {
  name: 'ManageVideo',
  components: {
    BroadcastInfo,
    BroadcastAttendance,
    ControlStudent,
    QuizResult
  },
  data () {
    return {
      page: 0,
    }
  },
  methods: {
    changePage (num) {
      this.page = num
    }
  },
  created() {
    const data = {
      id: this.$route.params.id,
      type: 1
    }
    this.$store.dispatch('broadcast/getBroadcastDetail', this.$route.params.id)
    this.$store.dispatch('broadcast/getBroadcastAfter', this.$route.params.id)
    this.$store.dispatch('broadcast/getActiveStudent', data)
    this.$store.dispatch('broadcast/getQuizResult', this.$route.params.id)
    this.$store.dispatch('broadcast/getBroadcastStudents', this.$route.params.id)
  },
  unmounted () {
    this.$store.dispatch('broadcast/closeManageVideo')
  }
}
</script>