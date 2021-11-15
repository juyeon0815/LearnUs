<template>
  <div class="video-info">
    <button
      class="btn edit-btn"
      @click="toggleUpdateModal"
    >수정</button>
    <VideoInfoUpdateModal v-if="isUpdateModalOn" @hideModal="toggleUpdateModal"/>
    <div class="info">
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
        <span class="key">Stream Server</span>
        <span class="val">rtmp://k5a306.p.ssafy.io/live</span>
      </div>
      <div class="info-row">
        <span class="key">Stream Key</span>
        <span class="val">{{ broadcastDetail.streamingKey }}</span>
      </div>
    </div>
    <div class="status-box">
      <div class="status">
        <div :class="[status === 'ONAIR' ? 'on' : 'off', 'status-circle']"></div>
        <span>{{ status }}</span>
      </div>
      <div>|</div>
      <div class="viewer">
      <span>{{ viewers }}</span> Viewers
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import VideoInfoUpdateModal from './VideoInfoUpdateModal'

export default {
  name: 'VideoInfo',
  components: {
    VideoInfoUpdateModal,
  },
  data() {
    return {
      isUpdateModalOn: false,
    }
  },
  methods: {
    toggleUpdateModal() {
      this.isUpdateModalOn = !this.isUpdateModalOn
    }
  },
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
    ...mapState('stomp', ['viewers']),
    status () {
      if (this.broadcastDetail.liveCode === 'C') {
        return 'Ready'
      } else if (this.broadcastDetail.liveCode === 'Y') {
        return 'ONAIR'
      }
      return 'The END'
    }
  }
}
</script>

<style>

</style>