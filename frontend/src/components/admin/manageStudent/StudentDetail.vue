<template>
  <div class="popup student-detail">
    <div v-if="studentInfo" class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <div :class="[result ? 'blue' : 'yellow', 'alert']" v-if="msg">{{ msg }}</div>
      <h1>Student<span class="t-orange">:</span>Info</h1>
      <div class="student-info">
        <div class="profile-box">
          <img :src="studentInfo.profileUrl" alt="profle image" class="profile">
          <div class="info">
            <div class="row-info">
              <div class="name">{{ studentInfo.name }}</div>
              <div class="id">{{ studentId }}</div>
            </div>
            <div class="ordinal">SSAFY {{ studentInfo.ordinalNo }}기 교육생</div>
            <div class="contact">
              <i class="fi fi-sr-envelope"></i>
              <span>{{ studentInfo.email }}</span>
            </div>
            <div class="contact">
              <i class="fi fi-sr-phone-call"></i> 
              <span>{{ studentInfo.phone }}</span>
            </div>
          </div>
        </div>
        <div class="info-box">
          <div class="box-division">
            <div class="info-input-box">
              <multiselect 
                v-model="region"
                class= "info-input"
                :closeOnSelect="false"
                :options=regionList
                required
              ></multiselect>
              <label>지역</label>
              <div class="error-text" v-if="error.region">{{error.region}}</div>
            </div>
            <div class="info-input-box">
              <input 
                v-model="classNo"
                type="number"
                placeholder="숫자만 입력 가능"
                class="info-input">
              <label>분반</label>
              <div class="error-text" v-if="error.classNo">{{error.classNo}}</div>
            </div>
            <div class="info-input-box">
              <multiselect 
                v-model="trackId"
                class= "info-input"
                :closeOnSelect="false"
                :options=trackOptions
                required
              ></multiselect>
              <label>트랙</label>
              <div class="error-text" v-if="error.trackId">{{error.trackId}}</div> 
            </div>
          </div>
        </div>
        <div v-if="studentInfo.statusCode === 'Y'" class="row-info btns">
          <button 
            :class="[isSubmit ? 'orange' : 'disabled', 'submit-btn btn']"
            @click="onEditInfo"
          >정보 수정</button>
          <button 
            class="submit-btn btn dark-orange"
            @click="onQuit"
          >퇴소</button>
        </div>
        <div v-else class="no-btn">
          퇴소한 교육생입니다.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import accountApi from '@/api/account'
import Multiselect from '@vueform/multiselect'
export default {
  name: 'StudentDetail',
  components: {
    Multiselect
  },
  data () {
    return {
      trackOptions: [],
      region: null,
      classNo: null,
      trackId: null,
      msg: false,
      result: 0,
      error: {
        region: false,
        classNo: false,
        trackId: false,
      },
      isSubmit: false
    }
  },
  methods: {
    async onEditInfo() {
      const studentData = this.studentInfo
      studentData.region = this.region
      studentData.classNo = this.classNo
      studentData.track = this.track

      try {
        const response = await accountApi.changeUserInfo(studentData)
        if (response.status === 200) {
          this.result = 1
          this.msg = '회원 정보 수정을 완료했습니다.'
          this.$store.dispatch('admin/getStudents')
          this.$store.dispatch('admin/getStudentDetail', this.targetId)
          setTimeout(() => {
            this.result = 0
            this.msg = false
            this.$emit('close')
          }, 1500)
        }
      } catch (err) {
        this.result = 0
        this.msg = '회원 정보 수정에 실패했습니다.'
        setTimeout(() => {
          this.msg = false
        }, 2000)
      }
    },
    async onQuit () {
      const studentData = this.studentInfo
      studentData.statusCode = "N"

      try {
        const response = await accountApi.changeUserInfo(studentData)
        if (response.status === 200) {
          this.result = 1
          this.msg = `${this.studentInfo.name}님이 퇴소 처리되었습니다.`
          setTimeout(() => {
            this.result = 0
            this.msg = false
            this.$emit('close')
          }, 1500)
        }
      } catch (err) {
        this.result = 0
        this.msg = '퇴소 처리에 실패했습니다.'
        setTimeout(() => {
          this.msg = false
        }, 2000)
      }

    },
    checkForm () {
      if (isNaN(this.classNo) || this.classNo < 1) {
        this.error.classNo = '1 이상 숫자를 입력하세요.'
      } else {
        this.error.classNo = false
      }
      if (!this.region) {
        this.error.region = '지역을 입력해주세요.'
      } else {
        this.error.region = false
      }
      if (!this.trackId) {
        this.error.trackId = '트랙을 선택해주세요.'
      } else {
        this.error.trackId = false
      }
      // submit 가능 여부 확인
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      })
      this.isSubmit = isSubmit;
    }
  },
  computed: {
    ...mapState('admin', ['studentInfo', 'targetId', 'tracks']),
    ...mapState('account', ['regionList']),
    studentId () {
      return String(this.studentInfo.userId).padStart(7, '0')
    },
    track () {
      return this.tracks.find(track => {
        return track.trackId === this.trackId
      })
    }
  },
  watch: {
    classNo () {
      this.checkForm()
    },
    region () {
      this.checkForm()
    },
    trackId () {
      this.checkForm()
    }
  },
  async created () {
    await this.$store.dispatch('admin/getTrackAll')
    await this.$store.dispatch('admin/getStudentDetail', this.targetId)
    this.region = this.studentInfo.region
    this.classNo = this.studentInfo.classNo
    this.trackId = this.studentInfo.track.trackId
    this.tracks.forEach(track => {
      if (track.trackSubject.trackSetting.ordinalNo === this.studentInfo.ordinalNo) {
        this.trackOptions.push({value: track.trackId, label: track.trackName})
      }
    })
    this.$store.dispatch('account/getRegion')
  },
  unmounted () {
    this.$store.commit('admin/SET_TARGET_ID', null)
    this.$store.commit('admin/SET_STUDENT_DETAIL', null)
  }
}
</script>

<style>

</style>