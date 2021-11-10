<template>
  <div class="schedule-list-item">
    <div class="thumbnail-box">
      <img :src="broadcast.thumbnailUrl" alt="" class="thumbnail-image">
    </div>
    <div class="broadcast-info">
      <div class="info-first-box">
        <span 
          class="info-title" 
          @click="$router.push({ name: 'OnAirStudio', params: { id: broadcast.broadcastId }})">
        {{ broadcast.title }}
        </span>
        <span class="cancle-broadcast">방송 취소</span>
      </div>
      <div class="info-instructor">
        <span><strong>진행자</strong> | {{ broadcast.teacher }}</span>
      </div>
      <div class="info-student">
        <span><strong>대상 교육생</strong> | {{ trackNames }}</span>
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
    }
  }
}
</script>
