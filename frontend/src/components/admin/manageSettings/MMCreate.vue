<template>
  <div class="add-mm popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>MatterMost<span class="t-orange">:</span></h1>
      <div class="form">
        <div class="half">
          <div class="input-box">
            <input
              @input="insertName"
              :value="name"
              class= "mm-input" 
              type="text"
              placeholder="MM 채널 이름"
              required
            >
            <label>채널명</label>
            <div class="error-text" v-if="error.name">{{error.name}}</div>
          </div>
          <div class="input-box">
            <multiselect 
              v-model="ordinalNo"
              class= "mm-input"
              :closeOnSelect="false"
              :options=targetOptions
              placeholder="MM 채널 목적"
              required
            ></multiselect>
            <label>Purpose</label>
            <div class="error-text" v-if="error.ordinalNo">{{error.ordinalNo}}</div>
          </div>
        </div>
        <div class="input-box">
          <input 
            type="url"
            pattern="https?://.*"
            placeholder="MM 채널 URL"
            v-model="channelUrl"
            @input="checkChannelURL"
            class= "mm-input" 
            required
          >
          <label>URL Path</label>
          <div class="error-text" v-if="error.pathName">{{error.pathName}}</div>
        </div>
        <div class="input-box">
          <input 
            v-model="webhook"
            @input="checkWebHook"
            class= "mm-input" 
            type="url"
            pattern="https?://.*"
            placeholder="MM Incoming Webhook"
            required
          >
          <label>WebHook</label>
          <div class="error-text" v-if="error.webhook">{{error.webhook}}</div>
        </div>
        <div class="input-box" v-if="mmData.ordinalNo ===0">
          <multiselect 
            v-model="trackList"
            class= "mm-input"
            mode="tags"
            placeholder="MM에 연결할 Track"
            :closeOnSelect="false"
            :searchable="true"
            :options=trackOptions
          ></multiselect>
          <label>Tracks</label>
          <div class="error-text" v-if="error.trackList">{{error.trackList}}</div>
        </div>
      </div>
      <button 
        class="submit btn dark-orange"
        @click="addMM"
      >MatterMost 연결</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Multiselect from '@vueform/multiselect'
import adminApi from '@/api/admin'

export default {
  name: 'MMCreate',
  components: {
    Multiselect
  },
  data () {
    return {
      trackOptions: [],
      targetOptions: [
        { label: '교육생 공지', value: 0 },
        { label: '5기 관리', value: 5 },
        { label: '6기 관리', value: 6 },
      ],
      channelUrl: '',
      name: '',
      ordinalNo: 0,
      pathName: null,
      trackList: [],
      webhook: '',
      error: {
        name: false,
        ordinalNo: false,
        pathName: false,
        trackList: false,
        webhook: false,
      },
      isSubmit: false
    }
  },
  methods: {
    insertName (event) {
      this.name = event.target.value
    },
    checkChannelURL (event) {
      if (this.channelUrl.length && !event.target.validity.valid) {
        this.error.pathName = 'URL 형식에 맞지 않습니다'
      } else {
        this.error.pathName = false
        this.pathName = this.channelUrl.split('/').pop()
      }
    },
    checkWebHook (event) {
      if (this.webhook.length && !event.target.validity.valid) {
        this.error.webhook = 'URL 형식에 맞지 않습니다'
      } else {
        this.error.webhook = false
      }
    },
    checkForm () {
      if (!this.name.length) {
        this.error.name = '필수 입력 항목입니다'
      } else {
        this.error.name = false
      }
      if (this.ordinalNo === null) {
        this.error.ordinalNo = '필수 입력 항목입니다'
      } else {
        this.error.ordinalNo = false
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.mmData).map(val => {
        if (val) isSubmit = false;
      })
      this.isSubmit = isSubmit;
    },
    async addMM () {
      try {
        const response = await adminApi.addMatterMost(this.mmData)
        if (response.status === 200) {
          this.$store.dispatch('admin/getMMAll')
          this.$emit('close')
        }
      } catch (err) {
        console.log(err.response)
      }
    }
  },
  computed: {
    ...mapState('admin', ['tracks']),
    mmData () {
      return {
        name: this.name,
        ordinalNo: this.ordinalNo,
        pathName: this.channelUrl.split('/').pop(),
        trackList: this.trackList,
        webhook: this.webhook,
      }
    }
  },
  watch: {
    name () {
      this.checkForm()
    },
    ordinalNo () {
      this.checkForm()
    }
  },
  created () {
    this.$store.dispatch('admin/getTrackAll')
    this.trackOptions = this.tracks.map(track => {
      let label = track.trackName + ' | ' + track.trackSubject.trackSetting.ordinalNo + '기 ' + track.trackSubject.subjectName
      return {value: track, label: label}
    })
  }
}
</script>
<style src="@vueform/multiselect/themes/default.css"></style>