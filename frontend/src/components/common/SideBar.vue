<template>
  <div class="side-bar">
    <div class="logo">
      <img class="logo-sm" src="@/assets/image/logo/logo-mountain-only.svg" alt="">
      <span v-if="vw > 576" class="logo-text">LearnUs</span>
    </div>
    <div class="menu">
      <div 
        class="menu-item"
        @click="$router.push('/')"
      >
        <i class="fi fi-rr-home"></i>
        <span>HOME</span>
      </div>
      <div 
        class="menu-item"
        @click="$router.push({ name: 'OnAir' })"
      >
        <i class="fi fi-rr-play-alt"></i>
        <span>ON<span class="t-orange">:</span>AIR</span>
      </div>
      
      <div class="menu-item">
        <i class="fi fi-rr-film"></i>
        <span>RE<span class="t-orange">:</span>PLAY</span>
        <div class="category">
          <span>‣ 전체보기</span>
          <span>‣ 1학기 과정</span>
          <span>‣ 2학기 과정</span>
        </div>
      </div>
      <div class="menu-item">
        <i class="fi fi-rr-sunrise"></i>
        <span><span class="t-orange">:</span>ADMIN</span>
        <div class="category">
          <span @click="$router.push({name: 'ManageStudent'})">‣ 교육생 관리</span>
          <span>‣ 교육 영상 관리</span>
          <span>‣ 라이브 생성</span>
          <span @click="$router.push({name: 'ManageSettings'})">‣ 러너스 설정</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SideBar',
  data () {
    return {
      vw: 0
    }
  },
  created () {
    this.vw = window.innerWidth
    window.addEventListener('resize', () => {
      this.vw = window.innerWidth
    })
  }
}
</script>

<style>

</style>