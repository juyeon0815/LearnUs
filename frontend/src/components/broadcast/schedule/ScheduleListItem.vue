<template>
  <div class="schedule-list-item">
    <div class="thumbnail-box">
      <img :src="broadcast.thumbnailUrl" 
        alt="" 
        class="thumbnail-image"
        @click="$router.push({ name: 'OnAirStudio', params: { id: broadcast.broadcastId }})">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span 
          class="info-title" 
          @click="$router.push({ name: 'OnAirStudio', params: { id: broadcast.broadcastId }})">
          {{ broadcast.title }}
        </span>
        <span 
          class="cancle-broadcast" 
          @click="$emit('onCancle')">
          방송 취소
        </span>
      </div>
      <div class="info-instructor">
        <span><strong>진행자</strong> | {{ broadcast.teacher }}</span>
        <span><strong>방송 시간</strong> | {{ broadcastDate }}</span>
      </div>
      <div class="info-student">
        <span><strong>대상 교육생</strong> |</span>
        <span 
          v-for="track in visibleTracks" 
          :key="track"
          class="track-sticker">{{ track }}</span>
        <span v-if="trackNames.length > 2">
          외 {{ trackNames.length - 2 }}개 트랙
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ScheduleListItem',
  props: {
    broadcast: Object,
  },
  computed: {
    trackNames() {
      return this.broadcast.trackList.map(track => {
        return track.trackName + ' | ' + track.trackSubject.trackSetting.ordinalNo + '기 ' + track.trackSubject.subjectName
      })
    },
    visibleTracks() {
      let result = []
      if (this.trackNames.length <= 2) {
        result = this.trackNames
      } else {
        result = [this.trackNames[0], this.trackNames[2]]
      }
      return result
    },
    broadcastDate() {
      const slice = this.broadcast.broadcastDate.split(' ')
      const date = slice[0].split('-')
      const time = slice[1].split(':')
      let result = date.join('.') + ' ' + time[0] + ':' + time[1]
      return result
    }
  }
}
</script>
