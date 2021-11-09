<template>
  <div class="replay-list-item">
    <div class="thumbnail-box">
      <img :src="thumbnailPath" @click="$router.push({ name: 'ReplayVideo', params : {id : idx }})" alt="" class="thumbnail-image">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span class="info-title">{{ idx.broadcast.title }}</span>
      </div>
      <div class="info-student">
        <span><strong>대상 교육생</strong> | {{ studentGroup }} Track</span>
      </div>
      <div class="info-time">
        <span><strong>방송 시간</strong> | {{ idx.broadcast.broadcastDate }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReplayListItem',
  props: {
    idx: Object
  },
  data() {
    return {
      thumbnailPath: require('@/assets/image/test/thumbnail.jpg'),
      title: '알고리즘 ① Sort(버블, 선택, 삽입)',
      studentGroup: '5기 전공',
      broadcastTime: "2021.10.19 09:00~"
    }
  }
}
</script>
