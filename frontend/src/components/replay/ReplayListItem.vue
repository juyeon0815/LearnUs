<template>
  <div class="replay-list-item">
    <div class="thumbnail-box">
      <img :src="thumbnailPath" @click="$router.push({ name: 'ReplayVideo', params : { id : broadcastReplayId }})" alt="" class="thumbnail-image">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span class="info-title">{{ replay.broadcast.title }}</span>
      </div>
      <div class="info-description">
        <span><strong>방송 설명</strong> | {{ replay.broadcast.description }}</span>
      </div>
      <div class="info-instructor">
        <span><strong>진행자</strong> | {{ replay.broadcast.teacher }}</span>
      </div>
      <div class="info-time">
        <span><strong>방송 시간</strong> | {{datetime}}</span>
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
  data() {
    return {
      thumbnailPath: require('@/assets/image/test/thumbnail.jpg'),
      datetime : '',
      broadcastReplayId : this.replay.broadcastReplyId
    }
  },
  mounted () {
    this.datetime = moment(this.replay.broadcast.broadcastDate).format('YYYY-MM-DD HH:mm')
  }
}
</script>
