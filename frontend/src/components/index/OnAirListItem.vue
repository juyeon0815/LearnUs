<template>
  <div class="on-air-item">
    <img 
      :src="broadcast.thumbnailUrl" 
      alt="boradcast thumbnail"
      @click="$router.push({ name:'OnAir', params: { id: broadcast.broadcastId } })">
    <div class="content">
      <img src="@/assets/image/deco/ssafy-square.jpg" alt="" class="track">
      <div class="info">
        <div 
          class="title"
          @click="$router.push({ name:'OnAir', params: { id: broadcast.broadcastId } })">
          {{ shorten(broadcast.title) }}
        </div>
        <div class="viewers">
          <span>{{ broadcast.teacher }} 교수</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OnAirListItem',
  props: {
    idx: Number,
    broadcast: Object,
  },
  methods: {
    shorten (text) {
      if (text.length >= 20) {
        return text.slice(0, 20) + '···'
      }
      return text
    }
  },
}
</script>