<template>
  <div v-if="userInfo" class="side-bar">
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
        @click="$router.push({ name: 'LiveSchedule' })"
      >
        <i class="fi fi-rr-play-alt"></i>
        <span>Schedule<span class="t-orange">:</span></span>
      </div>
      
      <div class="menu-item">
        <i class="fi fi-rr-film" @click="moveToReplay(0, 0)"></i>
        <span @click="moveToReplay(0, 0)">RE<span class="t-orange">:</span>PLAY</span>
        <div v-if="optionReady" class="category">
          <span v-if="!isAdmin" @click="moveToReplay(0, 0), changeReplayMenu(0)">‣ 전체 보기</span>
          <div v-if="!isAdmin">
            <span @click="changeReplayMenu(1)">‣ 1학기 과정</span>
            <transition name="fade"
              @before-enter="beforeEnter" @enter="enter"
              @before-leave="beforeLeave" @leave="leave"
            >
              <div class="class-category" v-if="showTrack === 1">
                <span
                  v-for="track in track1st"
                  :key="track.trackId"
                  @click="moveToReplay(track.trackId, track.trackSubject.trackSubjectId)"
                >‣ {{ track.trackName }}</span>
              </div>
            </transition>
          </div>
          <div v-if="semester===2">
            <span @click="changeReplayMenu(2)">‣ 2학기 과정</span>
            <transition name="fade"
              @before-enter="beforeEnter" @enter="enter"
              @before-leave="beforeLeave" @leave="leave"
            >
              <div class="class-category" v-if="showTrack === 2">
                <span
                  v-for="subject in subject2nd"
                  :key="subject.trackSubjectId"
                  @click="moveToReplay(track2nd[subject.trackSubjectId], subject.trackSubjectId)"
                >‣ {{ subject.subjectName }}</span>
              </div>
            </transition>
          </div>
        </div>
      </div>
      <div v-if="isAdmin" class="menu-item">
        <i class="fi fi-rr-sunrise"></i>
        <span><span class="t-orange">:</span>ADMIN</span>
        <div class="category">
          <span @click="$router.push({name: 'ManageStudent'})">‣ 교육생 관리</span>
          <span @click="$router.push({name: 'VideoHistory'})">‣ 교육 영상 관리</span>
          <span @click="$router.push({name: 'CreateLive'})">‣ 라이브 생성</span>
          <span @click="$router.push({name: 'ManageSettings'})">‣ 러너스 설정</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
export default {
  name: 'SideBar',
  data () {
    return {
      vw: 0,
      showTrack : 0,
    }
  },
  methods: {
    moveToReplay (track, subject) {
      this.$router.push({
        name: 'Replay', 
        params: {
          track: track
        },
        query: {
          subject: subject
        }
      })
    },
    changeReplayMenu (num) {
      if (this.showTrack === num) {
        this.showTrack = 0
      } else {
        this.showTrack = num
      }
    },
    beforeEnter: function(el) {
      el.style.height = '0'
    },
    enter: function(el) {
      el.style.height = el.scrollHeight + 'px'
    },
    beforeLeave: function(el) {
      el.style.height = el.scrollHeight + 'px'
    },
    leave: function(el) {
      el.style.height = '0'
    }
  },
  computed: {
    ...mapGetters('account', ['isAdmin']),
    ...mapState('account', ['userInfo']),
    ...mapState('admin', ['tracks', 'subjects']),
    semester () {
      return this.userInfo.track.trackSubject.trackSetting.semester
    },
    track1st () {
      return this.tracks.filter(track => {
        return track.trackSubject.trackSetting.semester === 1
      })
    },
    subject2nd () {
      return this.subjects.filter(subject => {
        return subject.trackSetting.semester === 2
      })
    },
    track2nd () {
      const result = {}
      this.subject2nd.forEach(sub => {
        let track = this.tracks.find(track => {
          return track.trackSubject.trackSubjectId === sub.trackSubjectId
        })
        result[sub.trackSubjectId] = track.trackId
      })
      return result
    },
    optionReady () {
      return this.userInfo && this.tracks && this.subjects
    }
  },
  watch: {
    userInfo (val) {
      if (val) {
        this.$store.dispatch('admin/getTrackAll')
        this.$store.dispatch('admin/getSubjectAll')
      }
    }
  },
  mounted () {
    this.vw = window.innerWidth
    window.addEventListener('resize', () => {
      this.vw = window.innerWidth
    })
    if (this.userInfo) {
      this.$store.dispatch('admin/setTrackInfo')
    }
  },
}
</script>

<style>

</style>