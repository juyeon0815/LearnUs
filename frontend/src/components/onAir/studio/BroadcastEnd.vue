<template>
  <div class="popup">
    <div class="popup-box broadcast-end">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>방송 종료</h1>
      <div class="default">
        <div class="award">
          <h3 @click="showRank= !showRank">
            방송 참여 우수 교육생
            <i :class="[showRank ? 'fi-rr-caret-up' : 'fi-rr-caret-down', 'fi']"></i>
          </h3>
          <transition name="fade"
            v-on:before-enter="beforeEnter" v-on:enter="enter"
            v-on:before-leave="beforeLeave" v-on:leave="leave"
          >
            <div v-show="showRank" ref="target" class="student">
              <div class="rank-row">
                <div class="rank-box">
                  <span class="title">Chat Rank</span>
                  <div 
                    v-for="(item, idx) in activeStudents.chat"
                    :key="idx"
                    class="user"
                  >
                    <span class="rank">{{ ranks[idx] }}</span>
                    <span class="nickname">{{ item.user.nickname }}</span>
                    <i 
                      class="fi fi-rr-add"
                      v-if="item.gifticonYn === 'N'" 
                      @click="createGifticon(item.user)"></i>
                  </div>
                </div>
                <div class="rank-box">
                  <span class="title">Quiz Rank</span>
                  <div 
                    v-for="(item, idx) in activeStudents.quiz"
                    :key="idx"
                    class="user"
                  >
                    <span class="rank">{{ ranks[idx] }}</span>
                    <span class="nickname">{{ item.user.nickname }}</span>
                    <i 
                      class="fi fi-rr-add"
                      v-if="item.gifticonYn === 'N'" 
                      @click="createGifticon"></i>
                  </div>
                </div>
              </div>
              <div class="gifticon">
                <span class="title">Gifticon List</span>
                <ControlGifticon/>
              </div>
            </div>
          </transition>
        </div>
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
<<<<<<< HEAD
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
=======
>>>>>>> a6c28a5b99a6a47e6a28401a2d784a3ea23eca32
    </div>
  </div>
</template>

<script>
import broadcastApi from '@/api/broadcast'
import { mapState, mapGetters } from 'vuex'
import ControlGifticon from './ControlGifticon.vue'
export default {
  name: 'BroadcastEnd',
  components: {
    ControlGifticon
  },
  data () {
    return {
      replay: true,
      attendance: false,
      gifticon: false,
      ranks: ['1st', '2nd', '3rd'],
      showRank: false
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
<<<<<<< HEAD
        alert('방송이 종료되었습니다.')
=======
        this.$router.push({ name: 'ManageVideo', params: { id: this.currentBroadcastId}})
>>>>>>> a6c28a5b99a6a47e6a28401a2d784a3ea23eca32
      } catch (err) {
        console.log(err)
      }
    },
    async createGifticon(data) {
      const gifticonData = {
        userId: data.userId,
        broadcastId: this.currentBroadcastId,
      }
      const result = await this.$store.dispatch('gifticon/createGifticon', gifticonData)
      if (result.status === 200) {
        this.$store.dispatch('broadcast/getActiveStudent', this.currentBroadcastId)
      }
    },
    beforeEnter: function(el) {
      el.style.height = '0'
    },
    enter: function(el) {
      console.log(el.scrollHeight)
      el.style.height = el.scrollHeight + 16 + 'px'
    },
    beforeLeave: function(el) {
      el.style.height = el.scrollHeight + 16 + 'px'
    },
    leave: function(el) {
      el.style.height = '0'
    }
  },
  computed: {
    ...mapState('stomp', ['stomp']),
    ...mapState('broadcast', ['activeStudents']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
    replayData () {
      return {
        broadcastId: this.currentBroadcastId,
        autoUploadYn: this.replay ? 'Y' : 'N'
      }
    },
  },
  created () {
    this.$store.dispatch('broadcast/getActiveStudent', this.currentBroadcastId)
  },
}
</script>