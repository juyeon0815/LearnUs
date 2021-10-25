<template>
  <div class="on-air-list">
    <div class="chapter">ON<span class="t-orange">:</span>AIR</div>
    <OnAirListItem/>
  </div>
</template>

<script>
import OnAirListItem from './OnAirListItem'
export default {
  name: 'OnAirList',
  components: {
    OnAirListItem
  }
}
</script>

<style>

</style>