<template>
  <div class="video-info">
    <div v-if="broadcastDetail" class="data">
      <div class="info-row">
        <span class="key">Title</span>
        <span class="val">{{ broadcastDetail.title }}</span>
      </div>
      <div class="info-row">
        <span class="key">DateTime</span>
        <span class="val">{{ broadcastDetail.broadcastDate }}</span>
      </div>
      <div class="info-row">
        <span class="key">Instructor</span>
        <span class="val">{{ broadcastDetail.teacher }}</span>
      </div>
      <div class="info-row">
        <span class="key">Target</span>
        <span 
          v-for="track in broadcastDetail.trackList"
          :key="track.trackId"
          class="val" 
        >{{ track.trackName }}</span>
      </div>
      <div class="info-row">
        <span class="key">Description</span>
        <span class="val">{{ description }}</span>
      </div>
      <div class="btns">
        <button
          class="btn black"
          @click="downloadAbsenceList"
        >
          <div class="btn-content">
            <i class="fi fi-rr-download"></i>
            <span>미출석자 명단</span>
          </div>
        </button>
        <button
          class="btn black"
          @click="downloadGifticonList"
        >
          <div class="btn-content">
            <i class="fi fi-rr-download"></i>
            <span>기프티콘 당첨자 명단</span>
          </div>
        </button>
      </div>
    </div>
    <div v-if="broadcastCnt" class="brief">
      <div class="brief-col">
        <span class="val">{{ broadcastCnt.attend }}</span>
        <span class="label">Students</span>
      </div>
      <div class="brief-col">
        <span class="val">{{ broadcastDetail.chatCount }}</span>
        <span class="label">Chats</span>
      </div>
      <div class="brief-col">
        <span class="val">{{ quizCnt }}</span>
        <span class="label">Quizzes</span>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapState, mapGetters } from 'vuex'
import broadcastApi from '@/api/broadcast'

export default {
  name: 'BroadcastVideoInfo',
  methods: {
    getFileName (contentDisposition) {
      let fileName = contentDisposition.split("=");
      return fileName[1];
    },
    async downloadAbsenceList () {
      const response = await broadcastApi.downloadAttendance(this.currentBroadcastId)
      if (response.status === 200) {
        const url = window.URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }))
        const fileName = `[${this.broadcastDetail.title}] 미출석자 명단(${this.date}).xlsx`
        
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        link.click()
      }
    },
    async downloadGifticonList () {
      const response = await broadcastApi.downloadGifticon(this.currentBroadcastId)
      if (response.status === 200) {
        const url = window.URL.createObjectURL(new Blob([response.data], { type: response.headers['content-type'] }))
        const fileName = `[${this.broadcastDetail.title}] 기프티콘 당첨자 명단(${this.date}).xlsx`
        
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        link.click()
      }
    },
    
  },
  computed: {
    ...mapState('broadcast', ['broadcastDetail', 'broadcastCnt']),
    ...mapGetters('broadcast', ['currentBroadcastId', 'quizCnt']),
    description () {
      if (this.broadcastDetail.description.length <= 50) {
        return this.broadcastDetail.description
      }
      return this.broadcastDetail.description.slice(0, 50) + '···'
    },
    date () {
      return moment(this.broadcastDetail.broadcastDate).locale('ko').format('L')
    }
  }
}
</script>