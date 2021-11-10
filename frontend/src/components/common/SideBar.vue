<template>
  <div class="side-bar">
    <div class="logo" @click="$router.push('/')">
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
      >
        <!-- @click="$router.push({ name: 'OnAir' })" -->
        <i class="fi fi-rr-play-alt"></i>
        <span>ON<span class="t-orange">:</span>AIR</span>
      </div>
      
      <div class="menu-item">
        <i class="fi fi-rr-film"></i>
        <span>RE<span class="t-orange">:</span>PLAY</span>
        <div class="category">
          <span @click="$router.push({name: 'Replay', params: {category: 'all'}})">‣ 전체보기</span>
          <span @click="semester1st = !semester1st">‣ 1학기 과정</span>
            <div class="class-category" v-if="semester1st">
              <span @click="$router.push({name: 'Replay', params: {category: '코딩집중교육'}})">‣ 코딩집중교육</span>
            </div>
          <div v-if="semester==2">
          <span @click="semester2nd = !semester2nd">‣ 2학기 과정</span>
            <div class="class-category" v-if="semester2nd">
              <span @click="$router.push({name: 'Replay', params: {category: '공통 PJT'}})">‣ 공통 PJT</span>
              <span @click="$router.push({name: 'Replay', params: {category: '특화 PJT'}})">‣ 특화 PJT</span>
              <span @click="$router.push({name: 'Replay', params: {category: '자율 PJT'}})">‣ 자율 PJT</span>
            </div>
          </div>
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
      vw: 0,
      semester: '',
      semester1st : false,
      semester2nd : false,
      userInfo: null,
    }
  },
  methods :{
  },
  created () {
    this.vw = window.innerWidth
    window.addEventListener('resize', () => {
      this.vw = window.innerWidth
    })
  },
  mounted () {
    this.userInfo = this.$store.state.account.userInfo
    if (this.userInfo) {
      this.semester = this.userInfo.track.trackSubject.trackSetting.semester
    }
  }
}
</script>

<style>

</style>