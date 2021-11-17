<template>
  <div class="profile-head">
    <!-- 사진 관리 -->
    <div class="student-photo">
      <img :key="photoKey" :src="profileUrl" alt="profile image" class="photo-circle">
      <div class="photo-update">
        <span class="text-btn" @click="showCropper">변경</span>
        <span class="dot-space">⬝</span>
        <span class="text-btn" @click="deletePhoto">삭제</span>
        </div>
    </div>
    <!-- 이름, 학번 및 소속 -->
    <div class="student-info">
      <div>
        <span class="student-name">{{ userInfo.name }}</span>
        <span v-if="!isAdmin" class="student-id">0{{ userInfo.userId }}</span>
      </div>
      <div class="department">
        <span v-if="isAdmin" class="grade">관리자 계정</span>
        <span v-if="!isAdmin" class="grade">SSAFY {{ userInfo.ordinalNo }}기 교육생</span>
        <span v-if="!isAdmin" class="region-class">
          ({{ userInfo.region }} {{ userInfo.classNo }}반 • {{ userInfo.track.trackSubject.subjectName }}-{{ userInfo.track.trackName }})
        </span>
      </div>
    </div>
    <!--  -->
    <ProfileCrop 
      v-if="isCropperShow" 
      @hideCropper="onHideCropper" 
      @wrongExtension="onWrongExtension"/>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
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
      defaultPhoto: 'https://mann-goofy.s3.ap-northeast-2.amazonaws.com/profiles/default.jpg'
    }
  },
  methods: {
    ...mapActions('account', ['onChangeUserPhoto']),
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
        } else if (httpStatus === 500) {
          alertInfo.message = '서버 오류입니다.'
        } else {
          alertInfo.message = `${httpStatus} 오류입니다.`
        }
        this.$emit('alert', alertInfo)
      }
    },
    async deletePhoto() {
      if (this.userInfo.profileUrl === this.defaultPhoto) {
        const alertInfo = {
          type: 'fail',
          message: '등록된 사진이 없습니다.'
        }
        this.$emit('alert', alertInfo)
      } else {
        await this.onChangeUserPhoto(this.defaultPhoto)
        const alertInfo = {
          type: 'success',
          message: '사진 삭제가 완료되었습니다.'
        }
        this.$emit('alert', alertInfo)
      }
    },
    onWrongExtension(alertInfo) {
      this.isCropperShow = false
      this.$emit('alert', alertInfo)
    }
  },
  computed: {
    profileUrl() {
      return this.$store.state.account.userInfo.profileUrl
    },
    photoKey() {
      return this.$store.state.account.photoKey
    },
    isAdmin() {
      if (this.$store.state.account.userInfo) {
        return this.$store.state.account.userInfo.statusCode === 'A'
      }
      return false
    }
  },
  created() {
    this.userInfo = this.$store.state.account.userInfo
  }
}
</script>
