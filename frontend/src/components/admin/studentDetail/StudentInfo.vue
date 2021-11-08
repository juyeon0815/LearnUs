<template>
  <div class="student-info">
    <div v-if="studentInfo !== null">
      <img :src="studentInfo.profileUrl" alt="profle image" class="profile-photo">
    </div>
    <div v-if="studentInfo !== null" class="info-box">
      <div class="box-division">
        <div class="info-input-box">
          <input 
            type="text"
            class="info-input"
            disabled
            v-model="studentInfo.name">
          <label>이름</label>
        </div>
        <div class="info-input-box">
          <input 
            type="text"
            class="info-input"
            disabled
            v-model="studentInfo.userId">
          <label>학번</label>
        </div>
      </div>
      <div class="box-division">
        <div class="info-input-box">
          <input 
            type="text"
            class="info-input small"
            v-model="studentInfo.ordinalNo">
          <label>기수</label>
        </div>
        <div class="info-input-box">
          <input 
            type="text"
            class="info-input small"
            v-model="studentInfo.region">
          <label>지역</label>
        </div>
        <div class="info-input-box">
          <input 
            type="text"
            class="info-input small"
            v-model="studentInfo.classNo">
          <label>분반</label>
        </div>
        <div class="info-input-box">
          <multiselect 
              v-model="studentInfo.track"
              class= "info-input small"
              :closeOnSelect="false"
              :options=trackOptions
              required
            ></multiselect>
          <label>트랙</label> 
        </div>
      </div>
      <div class="box-division">
        <button class="submit-btn update-btn">정보 수정</button>
        <button class="submit-btn expel-btn">퇴소</button>
      </div>
    </div>
  </div>  
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import Multiselect from '@vueform/multiselect'

export default {
  name: 'StudentInfo',
  components: {
    Multiselect
  },
  data() {
    return {
      studentInfo: null,
      trackOptions: [],
    }
  },
  methods: {
    ...mapActions('admin', ['getSingleStudent']),
    ...mapMutations('admin', ['SET_SINGLE_STUDENT'])
  },
  computed: {
    ...mapState('admin', ['tracks']),
  },
  async created() {
    await this.$store.dispatch('admin/getActiveTrack')
    await this.$store.dispatch('admin/getTrackAll')
    this.trackOptions = this.tracks.map(track => {
      let label = track.trackName
      return {value: track, label: label}
    })
    this.SET_SINGLE_STUDENT(null)
    await this.getSingleStudent(this.$route.params.id)
    this.studentInfo = this.$store.state.admin.singleStudent
    console.log(this.studentInfo)
  }
}
</script>
<style src="@vueform/multiselect/themes/default.css"></style>