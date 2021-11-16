<template>
  <div class="attend-stats">
    <span class="key">출석 결과</span>
    <span class="val">
      {{ broadcastCnt.attend }}명 / {{ broadcastCnt.totalAttend }}명
      ({{ attendRatio }}%)
    </span>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'BroadcastAttendance',
  computed: {
    ...mapState('broadcast', ['broadcastCnt']),
    attendRatio () {
      return Math.ceil(this.broadcastCnt.attend / this.broadcastCnt.totalAttend * 100)
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "@/assets/style/_color.scss";
  .attend-stats {
    display: flex;
    width: fit-content;
    gap: .5rem;
    background: $black;
    padding: .2rem 1rem;
    border-radius: 2rem;
    margin: 2rem auto 0;
    .key {
      color: $orange;
      font-weight: 700;
    }
  }
</style>