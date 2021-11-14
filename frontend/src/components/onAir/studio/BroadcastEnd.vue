<template>
  <div class="popup">
    <div class="popup-box broadcast-end">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>방송 종료</h1>
      <div class="default">
        <div class="check-options">
          <div 
            :class="[replay ? 'checked' : '', 'checkbox']"
            @click="replay = !replay"
          >
            다시보기 바로 공개
          </div>
          <div 
            :class="[attendance ? 'checked' : '', 'checkbox']"
            @click="attendance = !attendance"
          >
            강의 미참석 명단<br/>
            MM 프로 채널 공유
          </div>
          <div 
            :class="[gifticon ? 'checked' : '', 'checkbox']"
            @click="gifticon = !gifticon"
          >
            기프티콘 당첨자 명단<br/>
            MM 교육생 채널 공유
          </div>
        </div>
        <button 
          class="btn dark-orange"
          @click="endBroadcast"
        >방송 종료</button>
      </div>
      <!-- <div class="setting-box">
        <h3>방송 참여 정보</h3>
        <div>
          <button>미참석 명단 Download</button>
          <button>기프티콘 당첨자 명단 Download</button>
        </div> -->
        <!-- 미참석 명단 엑셀 다운로드 -->

        <!-- 기프티콘 명단 엑셀 다운로드 -->
        
        
        <!-- 미참석 명단 MM 전송 -->
        <!-- 기프티콘 명단 MM 전송 -->
        <!-- 다시보기 공개 여부 -->
        <!-- 채팅 / 퀴즈왕 정보 -->
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import broadcastApi from '@/api/broadcast'
import { mapState, mapGetters } from 'vuex'
export default {
  name: 'BroadcastEnd',
  data () {
    return {
      replay: true,
      attendance: false,
      gifticon: false,
    }
  },
  computed: {
    ...mapState('stomp', ['stomp']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
    replayData () {
      return {
        broadcastId: this.currentBroadcastId,
        autoUploadYn: this.replay ? 'Y' : 'N'
      }
    }
  },
  methods: {
    async endBroadcast () {
      try {
        await broadcastApi.endBroadcast(this.replayData)
        this.stomp.send(`/pub/broadcast.stop.${this.currentBroadcastId}`, {})
        if (this.attendance) {
          await broadcastApi.sendAttendMM(this.currentBroadcastId)
        }
        if (this.gifticon) {
          await broadcastApi.sendGifticonMM(this.currentBroadcastId)
        }
        alert('방송이 종료되었습니다.')
      } catch (err) {
        console.log(err)
      }
    },
  }
}
</script>