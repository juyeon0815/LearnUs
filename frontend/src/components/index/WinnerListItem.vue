<template>
  <div class="winner-item">
    <img :src="prize" alt="" class="prize">
    <div class="nickname">{{ nickName }}</div>
  </div>
</template>

<script>
export default {
  name: 'WinnerListItem',
  props: {
    award: Object,
    idx: Number,
  },
  computed: {
    nickName() {
      return `${this.award.user.ordinalNo}기 ${this.award.user.region} ${this.award.user.classNo}반 ${this.award.user.name}`
    },
    prize() {
      if (this.idx === 0) {
        return require('@/assets/image/index/gold.png')
      } else if (this.idx === 1) {
        return require('@/assets/image/index/silver.png')
      } else {
        return require('@/assets/image/index/bronze.png')
      }
    }
  },
}
</script>

<style>

</style>