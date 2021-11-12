<template>
  <div class="top-bar">
    <div v-if="showBtns" class="btn-group">
      <button class="btn black" @click="$router.push({ name: 'CreateLive' })">
        <div class="btn-content">
          <i class="fi fi-rr-video-camera"></i>
          <span>라이브 방송 생성</span>
        </div>
      </button>
      <button class="btn black" @click="$router.push({ name: 'LiveSchedule' })">
        <div class="btn-content">
          <i class="fi fi-rr-calendar"></i>
          <span>주간 일정 보기</span>
        </div>
      </button>
    </div>
    <div class="user-menu">
      <div class="profile">
        <img v-if="userInfo" :key="photoKey" :src="userInfo.profileUrl" alt="">
      </div>
      <div class="dropdown">
        <i class="dropdown-btn fi fi-rr-caret-down"></i>
        <ul class="dropdown-content">
          <li @click="$router.push({ name: 'Profile' })">계정 관리</li>
          <li @click="onLogout()">로그아웃</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'TopBar',
  methods: {
    ...mapActions('account', ['onLogout'])
  },
  computed: {
    ...mapState('account', ['userInfo', 'photoKey']),
    showBtns () {
      if (this.$route.name === "CreateLive") {
        return false
      }
      return true
    }
  },
}
</script>

<style>

</style>