<template>
    <div class="replay">
      <div class="row">
      <span class="title">RE<span class="colon">:</span>PLAY</span>
      </div>
      <TrackInfo v-if="this.$route.params.category!='all'"/>
      <ReplayList/>
  </div>
</template>

<script>
import './replay.scss'

import ReplayList from '@/components/replay/ReplayList'
import TrackInfo from '@/components/replay/ReplaySelect'

export default {
  name: 'Replay',
  components: {
     ReplayList, 
     TrackInfo
  }
}
</script>