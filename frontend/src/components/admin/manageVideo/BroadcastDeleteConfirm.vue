<template>
  <div class="popup delete">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>Delete<span class="t-orange">:</span>Broadcast</h1>
      <h3>해당 교육 영상과 정보를 완전히 삭제하시겠습니까?</h3>
      <div class="video-box">
        <img :src="broadcastDetail.thumbnailUrl" alt="">
        <div class="info">
          <span class="title">{{ broadcastDetail.title }}</span>
          <span>방송 일자: {{ broadcastDetail.broadcastDate }}</span>
          <span>수강 대상: 
            <span 
              v-for="track in broadcastDetail.trackList"
              :key="track.trackId"
              class="val" 
            >{{ track.trackSubject.trackSetting.ordinalNo }}기 {{ track.trackName }}</span>
          </span>
        </div>
      </div>
      <div>
        <input 
          v-model="confirm"
          type="checkbox" 
          id="confirm" 
          name="confirm"
          style="margin-right: .2rem;"
        >
        <label for="confirm">삭제된 동영상은 되돌릴 수 없음을 알고 있습니다.</label>
      </div>
      <button 
        :class="[confirm ? 'orange' : 'disabled', 'btn']" 
        @click="onDelete"
      >교육 영상 삭제</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import broadcastApi from '@/api/broadcast'
export default {
  name: 'BroadcastDeleteConfirm',
  data () {
    return {
      confirm: false,
    }
  },
  methods: {
    async onDelete () {
      const response = await broadcastApi.deleteBroadcast(this.broadcastDetail.broadcastId)
      if (response.status === 200) {
        this.$emit('close')
        this.$router.push({ name: 'VideoHistory' })
      }
    }
  },
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
  }
}
</script>

<style>

</style>