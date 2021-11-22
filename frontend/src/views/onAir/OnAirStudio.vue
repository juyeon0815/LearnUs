<template>
  <div class="on-air studio">
    <div v-if="broadcastDetail" class="btn-group">
      <button 
        v-if="!attendProcess"
        class="btn black" 
        @click="startAttend"
      >
        <div class="btn-content">
          <i class="fi fi-rr-paw"></i>
          <span>출석체크 시작</span>
        </div>
      </button>
      <button 
        v-else-if="attendProcess === 1"
        class="btn black" 
        @click="endAttend"
      >
        <div class="btn-content">
          <i class="fi fi-rr-paw"></i>
          <span>출석체크 종료</span>
        </div>
      </button>
      <button 
        v-else
        class="btn black" 
        @click="startAttend"
      >
        <div class="btn-content">
          <i class="fi fi-rr-paw"></i>
          <span>출석체크 재시작</span>
        </div>
      </button>
      <button 
        v-if="broadcastDetail && broadcastDetail.liveCode === 'C'" 
        class="btn black" 
        @click="startBroadcast"
      >
        <div class="btn-content">
          <i class="fi fi-rr-play-alt"></i>
          <span>방송 시작</span>
        </div>
      </button>
      <button 
        v-if="broadcastDetail && broadcastDetail.liveCode === 'Y'" 
        class="btn black" 
        @click="isEnding = true"
      >
        <div class="btn-content">
          <i class="fi fi-rr-play-alt"></i>
          <span>방송 종료</span>
        </div>
      </button>
    </div>
    <BroadcastEnd 
      v-if="isEnding"
      @close="isEnding=false"
    />
    <div v-if="broadcastDetail" class="content">
      <OnAirControl/>
      <OnAirChat/>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import './onAirStudio.scss'
import broadcastApi from '@/api/broadcast'
import OnAirChat from '@/components/onAir/OnAirChat'
import OnAirControl from '@/components/onAir/OnAirControl'
import BroadcastEnd from '@/components/onAir/studio/BroadcastEnd'

export default {
  name: 'OnAirStudio',
  components: {
    OnAirChat,
    OnAirControl,
    BroadcastEnd
  },
  data () {
    return {
      isEnding: false,
    }
  },
  methods: {
    startAttend() {
      this.stomp.send(`/pub/attendance.start.${this.currentBroadcastId}`, {})
      this.$store.commit('stomp/SET_ATTEND_PROCESS', 1)
    },
    endAttend() {
      this.stomp.send(`/pub/attendance.stop.${this.currentBroadcastId}`, {})
      this.$store.commit('stomp/SET_ATTEND_PROCESS', 2)
      this.$store.dispatch('broadcast/getBroadcastStudents', this.$route.params.id)
    },
    async startBroadcast() {
      const response = await broadcastApi.startBroadcast(this.currentBroadcastId)
      if (response.status === 200) {
        this.stomp.send(`/pub/broadcast.start.${this.currentBroadcastId}`, {})
      }
    }
  },
  computed: {
    ...mapState('stomp', ['stomp', 'attendProcess']),
    ...mapState('broadcast', ['broadcastDetail']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
  },
  created() {
    this.$store.dispatch('broadcast/getBroadcastDetail', this.$route.params.id)
    this.$store.dispatch('broadcast/getBroadcastStudents', this.$route.params.id)
    this.$store.dispatch('stomp/getQuizList', this.$route.params.id)
  },
  unmounted () {
    this.$store.dispatch('broadcast/closeOnAirStudio')
    this.$store.dispatch('stomp/resetStompData')
  }
}
</script>

<style>

</style>