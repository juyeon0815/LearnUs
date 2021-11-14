<template>
  <div class="on-air-item">
    <img 
      src="@/assets/image/test/thumbnail.jpg" 
      alt="boradcast thumbnail"
      @click="$router.push({ name:'OnAir', params: { id: broadcast.broadcastId } })">
    <div class="content">
      <img src="@/assets/image/test/ssafy-square.jpg" alt="" class="track">
      <div class="info">
        <div 
          class="title"
          @click="$router.push({ name:'OnAir', params: { id: broadcast.broadcastId } })">
          {{ shorten(broadcast.title) }}
        </div>
        <div class="viewers">
          <i class="fi fi-sr-flame"></i>
          <span>{{ idx }}명이 시청 중</span>
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
  data () {
    return {
      test: {
        title: "AI가 나 대신 코딩을? 깃헙 'Copilot' 을 사용해봤다!",
        viewers: 456
      }
    }
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