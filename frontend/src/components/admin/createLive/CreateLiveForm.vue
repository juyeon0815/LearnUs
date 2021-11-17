<template>
  <div class="create-live-form">
    <button class="create-btn" @click="onCreate">라이브 생성</button>
    <!-- 썸네일, 기초정보 인풋 -->
    <div class="live-row">
      <ThumbnailUploader 
        @wrongExtension="onWrongExtension" 
        ref="thumbnailUploader"/>
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
          maxlength="255"
          placeholder="영상에 대한 간략한 소개 글을 적어주세요."
          cols="30" 
          rows="10"></textarea>
        <label>Description</label>
      </div>
    </div>
    <!-- Alert -->
    <div :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert']" v-if="showAlert">{{ alertInfo.message }}</div>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions, mapState, mapGetters } from 'vuex'
import ThumbnailUploader from './ThumbnailUploader'
import Multiselect from '@vueform/multiselect'

export default {
  name: 'CreateLiveForm',
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
      showAlert: false,
      alertInfo: {
        type: null,
        message: '',
      }
    }
  },
  methods: {
    ...mapActions('broadcast', ['createBroadcast']),
    insertTitle (event) {
      this.title = event.target.value
    },
    insertInstructor (event) {
      this.instructor = event.target.value
    },
    insertDescription (event) {
      this.description = event.target.value
    },
    async onCreate() {
      if (this.title === '' || this.instructor === '' || !this.trackIds.length) {
        const alertInfo = {
          type: 'fail',
          message: '제목, 진행자, 대상 트랙은 필수 입력 항목입니다.'
        }
        this.onAlert(alertInfo)
        return
      }
      this.thumbnail = await this.$refs.thumbnailUploader.saveThumbnail()
      let data = this.broadcastData
      const tracksObj = this.tracks.filter(track => {
        return data.trackList.includes(track.trackId)
      })
      data.trackList = tracksObj
      this.createBroadcast(data)
    },
    onWrongExtension(alertInfo) {
      this.onAlert(alertInfo)
    },
    onAlert(alertInfo) {
      this.alertInfo = alertInfo
      this.showAlert = true
      setTimeout(() => {
        this.showAlert = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
      }, 2000)
    }
  },
  computed: {
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
  mounted () {
    this.datetime = moment().format('YYYY-MM-DDTHH:mm')
  }
}
</script>