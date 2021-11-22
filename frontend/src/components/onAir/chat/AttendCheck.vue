<template>
  <div class="attend-check">
    <div class="title-box">
      <div class="deco">
        <img src="@/assets/image/logo/logo-mountain-only.svg" alt="">
      </div>
      <span class="title">출석체크</span>
      <span class="date">{{ today }}</span>
    </div>
    <div class="timer">
      <button 
        class="btn dark-yellow btn-attend"
        @click="checkAttend(attendData)"
      >Check</button>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapState, mapGetters, mapActions } from 'vuex'
export default {
  name: 'AttendCheck',
  methods: {
    ...mapActions('stomp', ['checkAttend'])
  },
  computed: {
    ...mapState('account', ['userInfo']),
    ...mapState('stomp', ['stomp']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
    today() {
      return moment().locale('ko').format("MMMM Do");
    },
    attendData () {
      return {
        broadcastId: this.currentBroadcastId,
        userId: this.userInfo.userId
      }
    }
  },
}
</script>

<style>

</style>