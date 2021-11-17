<template>
  <div class="on-air-list" v-if="onairList">
    <div class="chapter">ON<span class="t-orange">:</span>AIR</div>
    <div ref="wrapper" class="wrapper" v-if="onairList && onairList.length >= 1">
      <div ref="carousel" class="carousel">
        <div ref="content" class="row">
          <OnAirListItem
            v-for="(broadcast, idx) in onairList"
            :key="broadcast.broadcasId"
            :idx="idx"
            :broadcast="broadcast"
          />
        </div>
      </div>
      <div v-if="onairList.length > 3">
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
    <div v-if="onairList && !onairList.length" class="empty">
      <img src="@/assets/image/logo/mountain-w500.svg" alt="mountain">
      <span>LIVE 중인 방송이 없습니다</span> 
      <button 
        class="btn dark-orange"
        @click="$router.push({ name: 'LiveSchedule' })"
      >방송 일정 보기</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import OnAirListItem from './OnAirListItem'

export default {
  name: 'OnAirList',
  components: {
    OnAirListItem
  },
  methods: {
    ...mapActions('broadcast', ['getOnairList']),
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
  },
  computed: {
    ...mapState('broadcast', ['onairList'])
  },
  created() {
    this.getOnairList()
  }
}
</script>