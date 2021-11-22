<template>
  <div class="live-schedule">
    <div 
      :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert top-abs']" 
      v-if="isAlertShow">{{ alertInfo.message }}</div>
    <CancelPopup 
      v-if="isModalOn"
      @hideModal="hideModal"
      @confirmDelete="onConfirmDelete"
      :broadcast="cancelExpected"/>
    <div v-if="broadcastByDate">
      <span class="title">LIVE<span class="colon">:</span>Schedule</span>
      <ScheduleList 
        v-for="(value, name) in broadcastByDate" 
        :key="name"
        :date="name"
        :scheduleList="value"
        @onCancel="onCancel"/>
    </div>
    <div 
      class="empty"
      v-if="broadcastByDate && !Object.keys(broadcastByDate).length">
      <img src="@/assets/image/logo/rocket-w500.svg" alt="">
      <span>예정된 방송이 없습니다.</span>
    </div>
  </div>
</template>

<script>
import "./liveSchedule.scss"
import ScheduleList from '@/components/broadcast/schedule/ScheduleList'
import CancelPopup from '@/components/broadcast/schedule/CancelPopup'
import { mapActions, mapGetters, mapMutations, mapState } from 'vuex'

export default {
  name: 'LiveSchedule',
  components: {
    ScheduleList,
    CancelPopup,
  },
  data() {
    return {
      cancelExpected: null,
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
    onCancel(broadcast) {
      this.cancelExpected = broadcast
      this.isModalOn = true
    },
    hideModal() {
      this.cancelExpected = null
      this.isModalOn = false
    },
    async onConfirmDelete() {
      await this.deleteBroadcast(this.cancelExpected.broadcastId)
      this.isModalOn = false
      this.cancelExpected = null
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
