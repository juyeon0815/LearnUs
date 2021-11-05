<template>
  <div class="admin setting">
    <header class="header">
      <div class="chapter">ADMIN<span class="t-orange">:</span></div>
      <span class="subtitle">
        러너스 설정
      </span>
    </header>
    <div class="content">
      <TrackList/>
      <MMTable/>
    </div>
  </div>
</template>

<script>
import TrackList from '@/components/admin/manageSettings/TrackList'
import MMTable from '@/components/admin/manageSettings/MMTable'
export default {
  name: 'ManageSettings',
  components: {
    TrackList,
    MMTable
  },
  created () {
    this.$store.dispatch('admin/setTrackInfo')
  }
}
</script>