<template>
  <div class="on-air studio">
    <div class="btn-group">
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
      <button class="btn black" @click="$router.push({ name: 'LiveSchedule' })">
        <div class="btn-content">
          <i class="fi fi-rr-play-alt"></i>
          <span>방송 시작</span>
        </div>
      </button>
    </div>
    <div class="content">
      <OnAirControl/>
      <OnAirChat/>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import './onAirStudio.scss'
// import broadcastApi from '@/api/broadcast'
import OnAirChat from '@/components/onAir/OnAirChat'
import OnAirControl from '@/components/onAir/OnAirControl'

export default {
  name: 'OnAirStudio',
  components: {
    OnAirChat,
    OnAirControl
  },
  methods: {
    startAttend() {
      this.stomp.send(`/pub/attendance.start.${this.currentBroadcastId}`, {})
      this.$store.commit('stomp/SET_ATTEND_PROCESS', 1)
    },
    endAttend() {
      this.stomp.send(`/pub/attendance.stop.${this.currentBroadcastId}`, {})
      this.$store.commit('stomp/SET_ATTEND_PROCESS', 2)
    },
    // async startBroadcast() {

    // }
  },
  computed: {
    ...mapState('stomp', ['stomp', 'attendProcess']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
  },
  created() {
    this.$store.dispatch('broadcast/getBroadcastDetail', this.$route.params.id)
    this.$store.dispatch('broadcast/getBroadcastStudents', this.$route.params.id)
    this.$store.dispatch('stomp/getQuizList', this.$route.params.id)
    this.$store.commit('stomp/SET_ATTEND_PROCESS', 0)
  },
}
</script>

<style>

</style>