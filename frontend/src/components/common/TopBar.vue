<template>
  <div class="top-bar">
    <div class="btn-group">
      <button class="btn black">
        <div class="btn-content">
          <i class="fi fi-rr-video-camera"></i>
          <span>라이브 방송 생성</span>
        </div>
      </button>
      <button class="btn black">
        <div class="btn-content">
          <i class="fi fi-rr-calendar"></i>
          <span>주간 일정 보기</span>
        </div>
      </button>
    </div>
    <div class="user-menu">
      <div class="profile">
        <img :src="this.$store.state.account.userInfo.profileUrl" alt="">
      </div>
      <div class="dropdown">
        <i class="dropdown-btn fi fi-rr-caret-down"></i>
        <ul class="dropdown-content">
          <li @click="$router.push({ name: 'Profile' })">계정 관리</li>
          <li>정보 수정</li>
          <li @click="onLogout()">로그아웃</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'TopBar',
  methods: {
    ...mapActions('account', ['onLogout'])
  }
}
</script>

<style>

</style>