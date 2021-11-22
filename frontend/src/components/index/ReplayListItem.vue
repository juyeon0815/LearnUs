<template>
  <div class="replay-item">
    <div class="info">
      <div class="title" @click="$router.push({ 
        name: 'ReplayDetail', 
        params: { 
          id: replay.broadcastReplayId
        } 
      })">{{ shorten }}</div>
      <div class="date">{{ date }} · by {{ replay.broadcast.teacher }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReplayListItem',
  props: {
    replay: Object
  },
  computed: {
    date() {
      return this.replay.broadcast.broadcastDate.split('T')[0]
    },
    shorten() {
      if (this.replay.broadcast.title.length > 30) {
        return this.replay.broadcast.title.slice(0, 29) + '···'
      } else {
        return this.replay.broadcast.title
      }
    }
  }
}
</script>