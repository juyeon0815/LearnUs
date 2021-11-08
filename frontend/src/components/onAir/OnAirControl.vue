<template>
  <div class="on-air-control">
    <ControlStudentTable v-if="studentDetail"/>
    <div class="default">
      <VideoPreview/>
      <VideoInfo/>
    </div>
    <div class="control-box">
      <div class="tabs">
        <span 
          :class="[!control ? 'active' : '', 'tab-item']" 
          @click="changeControl(0)"
        >교육생 관리</span>
        <span 
          :class="[control ? 'active' : '', 'tab-item']" 
          @click="changeControl(1)"
        >퀴즈 관리</span>
      </div>
      <ControlStudent v-if="!control"/>
      <ControlQuiz v-else/>
    </div>
  </div>
</template>

<script>
import './onAirControl.scss'
import VideoInfo from './studio/VideoInfo.vue'
import VideoPreview from './studio/VideoPreview.vue'
import ControlStudent from './studio/ControlStudent.vue'
import ControlStudentTable from './studio/ControlStudentTable.vue'
import ControlQuiz from './studio/ControlQuiz.vue'
export default {
  name: 'OnAirControl',
  components: {
    VideoInfo,
    VideoPreview,
    ControlStudent,
    ControlStudentTable,
    ControlQuiz
  },
  data () {
    return {
      control: 0,
      studentDetail: false,
    }
  },
  methods: {
    changeControl (num) {
      this.control = num
    }
  }
}
</script>

<style>

</style>