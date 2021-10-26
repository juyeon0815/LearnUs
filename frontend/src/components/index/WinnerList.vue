<template>
  <div class="winner-list">
    <div class="chapter">Congratulations<span class="t-orange">:</span></div>
    <div class="content">
      <div class="title">오늘의 퀴즈왕</div>
      <div class="grid-2">
        <WinnerListItem
          v-for="idx in 5"
          :key="idx"
        />
      </div>
      <div class="title">오늘의 참여왕</div>
      <div class="grid-2">
        <WinnerListItem
          v-for="idx in 10"
          :key="idx"
        />
      </div>
    </div>
  </div>
</template>

<script>
import WinnerListItem from './WinnerListItem.vue'
export default {
  name: 'WinnerList',
  components: {
    WinnerListItem
  }
}
</script>

<style>

</style>