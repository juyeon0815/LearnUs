<template>
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
          v-model="purpose"
          class= "mm-input"
          :closeOnSelect="false"
          :options=ordinalOptions
          placeholder="MM 채널 목적"
          required
        ></multiselect>
        <label>Purpose</label>
        <div class="error-text" v-if="error.purpose">{{error.purpose}}</div>
      </div>
    </div>
    <div class="input-box">
      <input 
        type="text"
        placeholder="MM 채널 URL 중 ~/channels/ 뒤의 이름)"
        v-model="pathName"
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
        v-model="trackIds"
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
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import Multiselect from '@vueform/multiselect'
export default {
  name: 'MMForm',
  components: {
    Multiselect
  },
  data () {
    return {
      name: '',
      purpose: 0,
      pathName: '',
      trackIds: [],
      webhook: '',
      error: {
        name: false,
        purpose: false,
        pathName: false,
        trackList: false,
        webhook: false,
      },
      invalid: false,
      isSubmit: false
    }
  },
  methods: {
    insertName (event) {
      this.name = event.target.value
    },
    checkWebHook (event) {
      if (this.webhook.length && !event.target.validity.valid) {
        this.invalid = true
        this.error.webhook = 'URL 형식에 맞지 않습니다'
      } else {
        this.invalid = false
        this.error.webhook = false
      }
    },
    checkForm () {
      if (!this.name.trim().length) {
        this.error.name = '필수 입력 항목입니다'
      } else {
        this.error.name = false
      }
      if (!this.pathName.trim().length) {
        this.error.pathName = '필수 입력 항목입니다'
      } else {
        this.error.pathName = false
      }
      if (this.purpose === null) {
        this.error.purpose = '필수 입력 항목입니다'
      } else {
        this.error.purpose = false
      }
      if (this.purpose === 0) {
        if (!this.trackIds.length) {
          this.error.trackList = 'MM에 연결할 트랙을 하나 이상 선택해주세요'
        } else {
          this.error.trackList = false
        }
      } else {
        this.error.trackList = false
      }
      if (!this.webhook.trim().length) {
        this.error.webhook = '필수 입력 항목입니다'
      } else {
        if (this.invalid) {
          this.error.webhook = 'URL 형식에 맞지 않습니다'
        } else {
          this.error.webhook = false
        }
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(val => {
        if (val) isSubmit = false;
      })
      this.isSubmit = isSubmit;
      if (this.isSubmit) {
        this.$store.commit('admin/SET_MM_DATA', this.mmData)
      } else {
        this.$store.commit('admin/SET_MM_DATA', null)
      }
    },
  },
  computed: {
    ...mapState('admin', ['tracks', 'ordinalNo', 'targetChannel']),
    ...mapGetters('admin', ['trackOptions', 'ordinalOptions']),
    mmData () {
      return {
        name: this.name,
        ordinalNo: this.purpose,
        pathName: this.pathName,
        trackList: this.trackIds,
        webhook: this.webhook,
      }
    },
  },
  watch: {
    name () {
      this.checkForm()
    },
    purpose () {
      this.checkForm()
    },
    pathName () {
      this.checkForm()
    },
    trackIds () {
      this.checkForm()
    },
    webhook () {
      this.checkForm()
    },
    isSubmit () {
      if (this.isSubmit) {
        this.$emit('valid', this.mmData)
      } else {
        this.$emit('valid', null)
      }
    }
  },
  mounted () {
    this.$store.dispatch('admin/getOrdinalAll')
    this.$store.dispatch('admin/getTrackAll')
    if (this.targetChannel) {
      this.name = this.targetChannel.name
      this.purpose = this.targetChannel.ordinalNo
      this.pathName = this.targetChannel.pathName
      this.webhook = this.targetChannel.webhook
      this.trackIds = this.targetChannel.trackList.map(track => {
        return track.trackId
      })
    }
  },
}
</script>

<style src="@vueform/multiselect/themes/default.css"></style>