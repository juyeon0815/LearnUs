<template>
  <div class="on-air-control">
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
          :class="[control === 1 ? 'active' : '', 'tab-item']" 
          @click="changeControl(1)"
        >퀴즈 관리</span>
        <span 
          :class="[control === 2 ? 'active' : '', 'tab-item']" 
          @click="changeControl(2)"
        >기프티콘</span>
      </div>
      <ControlStudent v-if="!control"/>
      <ControlQuiz v-else-if="control === 1"/>
      <ControlGifticon v-else-if="control === 2"/>
    </div>
  </div>
</template>

<script>
import './onAirControl.scss'
import VideoInfo from './studio/VideoInfo.vue'
import VideoPreview from './studio/VideoPreview.vue'
import ControlStudent from './studio/ControlStudent.vue'
import ControlGifticon from './studio/ControlGifticon.vue'
import ControlQuiz from './studio/ControlQuiz.vue'
export default {
  name: 'OnAirControl',
  components: {
    VideoInfo,
    VideoPreview,
    ControlGifticon,
    ControlStudent,
    ControlQuiz
  },
  data () {
    return {
      control: 0,
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