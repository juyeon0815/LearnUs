<template>
  <div class="schedule-list-item">
    <div :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert']" v-if="showAlert">{{ alertInfo.message }}</div>
    <div class="thumbnail-box">
      <img :src="history.broadcast.thumbnailUrl" 
        alt="" 
        class="thumbnail-image"
        @click="$router.push({ name: 'ManageVideo', params: { id: history.broadcast.broadcastId }})">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span 
          class="info-title"
          @click="$router.push({ name: 'ManageVideo', params: { id: history.broadcast.broadcastId }})">
          {{ shorten }}
        </span>
      </div>
      <div class="info-open">
        <span class="info-open-text">
          <strong>공개 상태</strong> | 
          <span :class="[ history.openYn === 'Y' ? 'open' : 'close', 'open-sticker']">{{ isOpen }}</span>
          <span> <i class="fi fi-rr-refresh alter" @click="alterOpen"></i></span>
        </span>
      </div>
      <div class="info-instructor">
        <span><strong>진행자</strong> | {{ history.broadcast.teacher }}</span>
        <span><strong>방송 시간</strong> | {{ broadcastDate }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'HistoryListItem',
  props: {
    history: Object,
    idx: Number,
  },
  data() {
    return {
      showAlert: false,
      alertInfo: {
        type: null,
        message: '',
      },
    }
  },
  methods: {
    ...mapActions('broadcast', ['alterOpenStatus']),
    async alterOpen() {
      const replayInfo = this.history
      if (replayInfo.openYn === 'Y') {
        replayInfo.openYn = 'N'
      } else {
        replayInfo.openYn = 'Y'
      }
      const result = await this.alterOpenStatus(replayInfo)
      if (result.status === 200) {
        const alertInfo = {
          type: 'success',
          message: '공개 상태가 변경되었습니다.'
        }
        this.onAlert(alertInfo)
      }
    },
    onAlert(alertInfo) {
      this.alertInfo = alertInfo
      this.showAlert = true
      setTimeout(() => {
        this.showAlert = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
        this.SET_HTTP_STATUS(null)
      }, 2000)
    }
  },
  computed: {
    shorten() {
      if (this.history.broadcast.title.length > 20) {
        return this.history.broadcast.title.slice(0,19) + '···'
      } else {
        return this.history.broadcast.title
      }
    },
    broadcastDate() {
      const slice = this.history.broadcast.broadcastDate.split('T')
      const date = slice[0].split('-')
      const time = slice[1].split(':')
      let result = date.join('.') + ' ' + time[0] + ':' + time[1]
      return result
    },
    isOpen() {
      if (this.history.openYn === 'Y') {
        return 'OPEN'
      } else {
        return 'CLOSE'
      }
    }
  }
}
</script>
