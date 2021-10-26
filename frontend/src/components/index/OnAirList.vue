<template>
  <div class="on-air-list">
    <div class="chapter">ON<span class="t-orange">:</span>AIR</div>
    <div ref="wrapper" class="wrapper">
      <div ref="carousel" class="carousel">
        <div ref="content" class="row">
          <OnAirListItem
            v-for="idx in 6"
            :key="idx"
            :idx="idx"
          />
        </div>
      </div>
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
      const carousel = this.$refs.carousel
      const content = this.$refs.content
      const prev = this.$refs.prev
      const next = this.$refs.next
      let width = carousel.offsetWidth;
      console.log(width)

      if (dir) {
        carousel.scrollBy(width + gap, 0);
        if (carousel.scrollWidth !== 0) {
          prev.style.display = "flex";
        }
        if (content.scrollWidth - width - gap <= carousel.scrollLeft + width) {
          next.style.display = "none";
        }
      } else {
        carousel.scrollBy(-(width + gap), 0);
        if (carousel.scrollLeft - width - gap <= 0) {
          prev.style.display = "none";
        }
        if (!content.scrollWidth - width - gap <= carousel.scrollLeft + width) {
          next.style.display = "flex";
        }
      }
    }
  }
}
</script>

<style>

</style>