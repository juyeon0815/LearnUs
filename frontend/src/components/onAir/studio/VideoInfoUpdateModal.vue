<template>
  <div class="popup">
    <div class="popup-box create-live">
      <i class="fi fi-rr-cross exit-btn" @click="$emit('hideModal')"></i>
      <h1 class="mb-2">Update LIVE</h1>
      <div class="create-live-form">
        <!-- 썸네일, 기초정보 인풋 -->
        <div class="live-row">
          <ThumbnailUploader ref="thumbnailUploader"/>
          <div class="summary-col">
            <div class="input-box mb-2">
              <input 
                @input="insertTitle"
                :value="title"
                type="text"
                class="input"
                placeholder="라이브 제목을 입력하세요">
              <label>Title</label>
            </div>
            <div class="input-box mb-2">
              <input 
                v-model="datetime"
                type="datetime-local" 
                class="input"
                placeholder="날짜를 선택하세요"
              >
              <label>DateTime</label>
            </div>
            <div class="input-box">
              <input 
                @input="insertInstructor"
                :value="instructor"
                type="text"
                class="input"
                placeholder="라이브 진행자를 입력하세요">
              <label>Instructor</label>
            </div>
          </div>
        </div>
        <!-- 대상자 설정 -->
        <div class="live-row">
          <div class="input-box">
            <multiselect
              v-model="trackIds"
              class="input"
              mode="tags"
              placeholder="수강 대상 트랙을 선택하세요."
              :closeOnSelect="false"
              :searchable="true"
              :options=trackOptions
            >
            </multiselect>
            <label>Target</label>
          </div>
        </div>
        <!-- 소개 인풋 -->
        <div class="live-row">
          <div class="input-box">
            <textarea 
              @input="insertDescription"
              :value="description"
              class="input description"
              placeholder="영상에 대한 간략한 소개 글을 적어주세요."
              cols="30" 
              rows="10"></textarea>
            <label>Description</label>
          </div>
        </div>
      </div>
      <button class="btn-update" @click="onUpdate">업데이트</button>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions, mapState, mapGetters } from 'vuex'
import Multiselect from '@vueform/multiselect'
import ThumbnailUploader from '@/components/admin/createLive/ThumbnailUploader'

export default {
  name: 'VideoInfoUpdateModal',
  components: {
    ThumbnailUploader,
    Multiselect,
  },
  data() {
    return {
      title: '',
      datetime: '',
      instructor: '',
      trackIds: [],
      description: '',
      thumbnail: '',
    }
  },
  methods: {
    ...mapActions('broadcast', ['updateBroadcastInfo']),
    insertTitle (event) {
      this.title = event.target.value
    },
    insertInstructor (event) {
      this.instructor = event.target.value
    },
    insertDescription (event) {
      this.description = event.target.value
    },
    async onUpdate() {
      this.thumbnail = await this.$refs.thumbnailUploader.saveThumbnail()
      let data = {
        ...this.broadcastDetail,
        ...this.broadcastData
      }
      const tracksObj = this.tracks.filter(track => {
        return data.trackList.includes(track.trackId)
      })
      data.trackList = tracksObj
      this.updateBroadcastInfo(data)
    }
  },
  computed: {
    ...mapState('broadcast',['broadcastDetail']),
    ...mapState('admin',['tracks']),
    ...mapGetters('admin',['trackOptions']),
    broadcastData () {
      return {
        thumbnailUrl: this.thumbnail, 
        broadcastDate: moment(this.datetime).format('YYYY-MM-DD HH:mm:ss'), 
        title: this.title,
        teacher: this.instructor, 
        description: this.description, 
        textbook: {}, 
        trackList: this.trackIds
      }
    }
  },
  created() {
    this.$store.dispatch('admin/setTrackInfo')
    this.datetime = moment(this.broadcastDetail.broadcastDate).format('YYYY-MM-DDTHH:mm')
    this.title = this.broadcastDetail.title
    this.instructor = this.broadcastDetail.teacher
    this.description = this.broadcastDetail.description
    this.textbook = this.broadcastDetail.textbook
    this.trackIds = this.broadcastDetail.trackList.map(track => {
      return track.trackId
    })
  },
}
</script>