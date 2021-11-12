<template>
  <div class="attend-result">
    <div class="deco">
      <img src="@/assets/image/logo/logo-mountain-only.svg" alt="">
    </div>
    <div class="text">
      <div v-if="attendResult === 1" class="complete">
        <span>{{ userInfo.name }}({{ userInfo.userId }})</span>교육생
        <span>{{ today }} 출석</span>이 확인되었습니다.
      </div>
      <div v-else-if="attendResult === 2" class="fail">
        <span>{{ userInfo.name }}({{ userInfo.userId }})</span>교육생
        <span>{{ today }} 출석</span> 확인에 실패했습니다.
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import moment from 'moment'
export default {
  name: 'AttendResult',
  data () {
    return {
      test: {
        name: '김싸피',
        id: '0545052'
      }
    }
  },
  computed: {
    ...mapState('stomp', ['attendResult']),
    ...mapState('account', ['userInfo']),
    today() {
      return moment().locale('ko').format("MMMM Do a");
    }
  },
}
</script>

<style>

</style>