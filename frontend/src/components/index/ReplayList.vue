<template>
  <div class="replay-list">
    <div class="chapter">RE<span class="t-orange">:</span>PLAY</div>
    <div class="col">
      <ReplayListItem
        v-for="idx in 3"
        :key="idx"
      />
      <div class="more">
        + View More 👀
      </div>
    </div>
  </div>
</template>

<script>
import ReplayListItem from './ReplayListItem.vue'
export default {
  name: 'ReplayList',
  components: {
    ReplayListItem
  }
}
</script>