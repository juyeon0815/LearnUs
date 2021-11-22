<template>
  <div 
    class="replay-item"
    @click="moveToDetail"
  >
    <img 
      :src="replay.broadcast.thumbnailUrl" 
      alt="boradcast thumbnail"
    >
    <div class="content">
      <img src="@/assets/image/deco/ssafy-square.jpg" alt="" class="track">
      <div class="info">
        <div class="title">
          {{ title }}
        </div>
        <div class="viewers">
          <span>{{ broadcastDate }}</span>
          <span>by. {{ replay.broadcast.teacher }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'ReplayListItem',
  props: {
    replay: Object,
  },
  methods: {
    moveToDetail () {
      this.$router.push({ 
        name: 'ReplayDetail', 
        params: { 
          id: this.replay.broadcastReplayId
        } 
      })
    }
  },
  computed: {
    title () {
      if (this.replay.broadcast.title.length > 15) {
        return this.replay.broadcast.title.slice(0, 15) + '···'
      }
      return this.replay.broadcast.title
    },
    broadcastDate () {
      return moment(this.replay.broadcast.broadcastDate).locale('ko').fromNow()
    }
  },
}
</script>
