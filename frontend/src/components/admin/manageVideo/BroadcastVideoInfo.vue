<template>
  <div class="video-info">
    <div class="data">
      <div class="info-row">
        <span class="key">Title</span>
        <span class="val">{{ broadcastDetail.title }}</span>
      </div>
      <div class="info-row">
        <span class="key">DateTime</span>
        <span class="val">{{ broadcastDetail.broadcastDate }}</span>
      </div>
      <div class="info-row">
        <span class="key">Instructor</span>
        <span class="val">{{ broadcastDetail.teacher }}</span>
      </div>
      <div class="info-row">
        <span class="key">Target</span>
        <span 
          v-for="track in broadcastDetail.trackList"
          :key="track.trackId"
          class="val" 
        >{{ track.trackName }}</span>
      </div>
      <div class="info-row">
        <span class="key">Description</span>
        <span class="val">{{ description }}</span>
      </div>
    </div>
    <div class="brief">
      <div class="brief-col">
        <span class="val">156</span>
        <span class="label">Students</span>
      </div>
      <div class="brief-col">
        <span class="val">60</span>
        <span class="label">Chats</span>
      </div>
      <div class="brief-col">
        <span class="val">10</span>
        <span class="label">Quizzes</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'BroadcastVideoInfo',
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
    description () {
      if (this.broadcastDetail.description.length <= 150) {
        return this.broadcastDetail.description
      }
      return this.broadcastDetail.description.slice(0, 150) + '···'
    }
  }
}
</script>