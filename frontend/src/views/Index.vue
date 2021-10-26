<template>
  <div class="index">
    <OnAirList/>
    <div class="row-half">
      <ReplayList/>
      <WinnerList/>
    </div>
  </div>
</template>

<script>
import './index.scss'
import OnAirList from '@/components/index/OnAirList'
import ReplayList from '@/components/index/ReplayList'
import WinnerList from '@/components/index/WinnerList'
export default {
  name: 'Index',
  components: {
    OnAirList,
    ReplayList,
    WinnerList
  }
}
</script>
