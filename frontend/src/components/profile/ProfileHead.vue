<template>
  <div class="profile-head">
    <!-- 사진 관리 -->
    <div class="student-photo">
      <img :key="photoKey" :src="profileUrl" alt="profile image" class="photo-circle">
      <div class="photo-update">
        <span class="text-btn" @click="showCropper">변경</span>
        <span class="dot-space">⬝</span>
        <span class="text-btn">삭제</span>
        </div>
    </div>
    <!-- 이름, 학번 및 소속 -->
    <div class="student-info">
      <div>
        <span class="student-name">{{ userInfo.name }}</span>
        <span class="student-id">0{{ userInfo.userId }}</span>
      </div>
      <div class="department">
        <span class="grade">SSAFY {{ userInfo.ordinalNo }}기 교육생</span>
        <span class="region-class">({{ userInfo.region }} {{ userInfo.classNo }}반)</span>
      </div>
    </div>
    <!--  -->
    <ProfileCrop v-if="isCropperShow" @hideCropper="onHideCropper"/>
  </div>
</template>

<script>
import ProfileCrop from './ProfileCrop'

export default {
  name: 'ProfileHead',
  components: {
    ProfileCrop
  },
  data: () => {
    return {
      userInfo: null,
      isCropperShow: false,
      photoKey: 0,
    }
  },
  methods: {
    showCropper() {
      this.isCropperShow = true
    },
    onHideCropper() {
      this.isCropperShow = false
      const httpStatus = this.$store.state.account.httpStatus
      if (httpStatus !== null) {
        const alertInfo = {
          type: 'fail',
          message: '',
        }
        if (httpStatus === 200) {
          alertInfo.type = 'success'
          alertInfo.message = '사진 변경이 완료되었습니다.'
          this.photoKey += 1
        } else if (httpStatus === 500) {
          alertInfo.message = '서버 오류입니다.'
        } else {
          alertInfo.message = `${httpStatus} 오류입니다.`
        }
        this.$emit('alert', alertInfo)
      }
    }
  },
  computed: {
    profileUrl() {
      return this.$store.state.account.userInfo.profileUrl
    }
  },
  created() {
    this.userInfo = this.$store.state.account.userInfo
  }
}
</script>
