<template>
  <div class="live-schedule">
    <div 
      :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert top-abs']" 
      v-if="isAlertShow">{{ alertInfo.message }}</div>
    <CanclePopup 
      v-if="isModalOn"
      @hideModal="hideModal"
      @confirmDelete="onConfirmDelete"
      :broadcast="cancleExpected"/>
    <div v-if="broadcastByDate">
      <span class="title">LIVE<span class="colon">:</span>Schedule</span>
      <ScheduleList 
        v-for="(value, name) in broadcastByDate" 
        :key="name"
        :date="name"
        :scheduleList="value"
        @onCancle="onCancle"/>
    </div>
  </div>
</template>

<script>
import "./liveSchedule.scss"
import ScheduleList from '@/components/broadcast/schedule/ScheduleList'
import CanclePopup from '@/components/broadcast/schedule/CanclePopup'
import { mapActions, mapGetters, mapMutations, mapState } from 'vuex'

export default {
  name: 'LiveSchedule',
  components: {
    ScheduleList,
    CanclePopup,
  },
  data() {
    return {
      cancleExpected: null,
      isModalOn: false,
      alertInfo: {
        type: null,
        message: '',
      },
      isAlertShow: false,
      prevRoute: null,
    }
  },
  methods: {
    ...mapActions('broadcast', ['getBroadcastList', 'deleteBroadcast']),
    ...mapMutations('broadcast', ['SET_HTTP_STATUS']),
    onCancle(broadcast) {
      this.cancleExpected = broadcast
      this.isModalOn = true
    },
    hideModal() {
      this.cancleExpected = null
      this.isModalOn = false
    },
    async onConfirmDelete() {
      await this.deleteBroadcast(this.cancleExpected.broadcastId)
      this.isModalOn = false
      this.cancleExpected = null
      if (this.httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (this.httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '방송 정보가 삭제되었습니다.'
        } else if (this.httpStatus === 500) {
          alertInfo.message = '서버 오류입니다.'
        } else {
          alertInfo.message = `${this.httpStatus} 오류입니다.`
        }
        this.onAlert(alertInfo)
      }
    },
    onAlert(alertInfo) {
      this.alertInfo = alertInfo
      this.isAlertShow = true
      setTimeout(() => {
        this.isAlertShow = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
        this.SET_HTTP_STATUS(null)
      }, 2000)
    }
  },
  computed: {
    ...mapState('broadcast', ['httpStatus']),
    ...mapGetters('broadcast', ['broadcastByDate']),
  },
  created() {
    this.getBroadcastList()
  },
  mounted() {
    if(this.prevRoute && this.prevRoute.name === 'CreateLive' && this.httpStatus === 200) {
      const alertInfo = {
        type: 'success',
        message: '방송이 생성되었습니다.',
      }
      this.onAlert(alertInfo)
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.prevRoute = from
    })
  }
}
</script>
