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
      <button class="btn dark-yellow btn-attend">Check</button>
      <span>in</span>
      <span class="rest">05:00</span>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'AttendCheck',
  computed: {
    today() {
      return moment().locale('ko').format("MMMM Do a");
    }
  },
}
</script>

<style>

</style>