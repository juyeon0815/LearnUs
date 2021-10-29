<template>
  <div class="attend-result">
    <div class="deco">
      <img src="@/assets/image/logo/logo-mountain-only.svg" alt="">
    </div>
    <div class="text">
      <div class="complete">
        <span>{{ test.name }}({{ test.id }})</span>교육생
        <span>{{ today }} 출석</span>이 확인되었습니다.
      </div>
      <div class="fail">
        <span>{{ test.name }}({{ test.id }})</span>교육생
        <span>{{ today }} 출석</span> 확인에 실패했습니다.
      </div>
    </div>
  </div>
</template>

<script>
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
    today() {
      return moment().locale('ko').format("MMMM Do a");
    }
  },
}
</script>

<style>

</style>