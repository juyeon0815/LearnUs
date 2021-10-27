<template>
  <div class="on-air-list">
    <div class="chapter">ON<span class="t-orange">:</span>AIR</div>
    <div ref="wrapper" class="wrapper">
      <div ref="carousel" class="carousel">
        <div ref="content" class="row">
          <OnAirListItem
            v-for="idx in 7"
            :key="idx"
            :idx="idx"
          />
        </div>
      </div>
      <div v-if="7 > 3">
        <i 
          ref="prev" 
          class="move left fi fi-rr-caret-left"
          @click="move(0)"
        ></i>
        <i 
          ref="next"
          class="move right fi fi-rr-caret-right"
          @click="move(1)"
        ></i>
      </div>
      
    </div>
  </div>
</template>

<script>
import OnAirListItem from './OnAirListItem'
export default {
  name: 'OnAirList',
  components: {
    OnAirListItem
  },
  methods: {
    move(dir) {
      const gap = 32
      const item = 300
      const carousel = this.$refs.carousel
      const content = this.$refs.content
      const prev = this.$refs.prev
      const next = this.$refs.next
      let width = carousel.offsetWidth;

      // next click
      if (dir) {
        // 스크롤 우측 이동
        carousel.scrollBy(item + gap, 0);
        if (carousel.scrollWidth !== 0) {
          prev.style.display = "flex";
        }
        if (content.scrollWidth < carousel.scrollLeft + width) {
          next.style.display = "none";
        }
        // prev click
      } else {
        // 스크롤 좌측 이동
        carousel.scrollBy(-(item + gap), 0);
        if (carousel.scrollLeft - item <= 0) {
          prev.style.display = "none";
        }
        if (!content.scrollWidth - item - gap < carousel.scrollLeft + item) {
          next.style.display = "flex";
        }
      }
    }
  }
}
</script>