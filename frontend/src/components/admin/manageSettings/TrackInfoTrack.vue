<template>
  <div style="width: 100%;">
    <div v-if="isEditMode" class="edit-box">
      <input 
        type="text" 
        :value="data.trackName"
        @input="insertTrack"
      >
      <span>
        <button 
          :class="[data.trackName.trim().length ? '' : 'disabled']" 
          @click="editTrack"
        >수정</button>
        <button @click="offEditTrack">취소</button>
      </span>
    </div>
    <div v-else class="info">
      <span 
        class="name"
      >{{ track.trackName }}</span>
      <span>
        <button @click="onEditTrack">수정</button>
        <button @click="deleteTrack">삭제</button>
      </span>
    </div>
  </div>
</template>

<script>
import adminApi from '@/api/admin'
export default {
  name: 'TrackInfoTrack',
  props: {
    track: Object
  },
  data () {
    return {
      isEditMode: false,
      data: {
        subjectId: null,
        trackId: null,
        trackName: ''
      }
    }
  },
  methods: {
    onEditTrack() {
      this.isEditMode = true
      this.data.subjectId = this.track.trackSubject.trackSubjectId
      this.data.trackId = this.track.trackId
      this.data.trackName = this.track.trackName
    },
    offEditTrack() {
      this.isEditMode = false
      this.data.subjectId = null
      this.data.trackId = null
      this.data.trackName = ''
    },
    insertTrack(event) {
      this.data.trackName = event.target.value
    },
    async editTrack() {
      try {
        const response = await adminApi.editTrack(this.data)
        if (response.status === 200) {
          this.offEditTrack()
          this.$store.dispatch('admin/getTrackAll')
        }
      } catch (err) {
        if (err.response.data === 'same_name') {
          this.$emit('at', 1)
        } else {
          this.offEditTrack()
          this.$emit('at', 0)
        }
      }
    },
    async deleteTrack() {
      try {
        const response = await adminApi.deleteTrack(this.track.trackId)
        if (response.status === 200) {
          this.$store.dispatch('admin/getTrackAll')
        }
      } catch (err) {
        this.$store.dispatch('admin/getTrackAll')
      }
    },
  }
}
</script>