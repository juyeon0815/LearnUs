<template>
  <div class="on-air-item">
    <img src="@/assets/image/test/thumbnail.jpg" alt="">
    <div class="content">
      <div class="track"></div>
      <div class="info">
        <div class="title">{{ shorten(test.title) }}</div>
        <div class="viewers">
          <i class="fi fi-sr-flame"></i>
          <span>{{ test.viewers }}명이 시청 중</span>
        </div>
      </div>
      <div class="dropdown">
        <i class="fi fi-rr-menu-dots-vertical"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OnAirListItem',
  props: {
    idx: Number
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
  }
}
</script>