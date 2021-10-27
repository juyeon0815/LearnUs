<template>
  <div class="winner-item">
    <div class="profile"></div>
    <div class="nickname">{{ nickname }}</div>
  </div>
</template>

<script>
export default {
  name: 'WinnerListItem',
  data () {
    return {
      nickname: '서울 3반 가나다'
    }
  }
}
</script>

<style>

</style>