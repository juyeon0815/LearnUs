<template>
  <div class="track-info popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <div :class="[result ? 'blue' : 'yellow', 'alert']" v-if="msg">{{ msg }}</div>
      <h1>Tracks<span class="t-orange">:</span></h1>
      <div class="track-current">
        <TrackCurrent
          v-for="(ordinal, idx) in ordinalNo"
          :key="idx"
          :ordinal="ordinal"
          @current="alertCurrent"
        />
      </div>
      <div class="track-box">
        <div class="ordinal">
          <span
            v-for="(ordinal, idx) in ordinalNo"
            :key="idx"
            @click="changeOrd(ordinal)"
            :class="[ordinal == ord ? 'selected' : '']"
          >{{ ordinal }}기</span>
        </div>
        <div class="subject">
          <TrackInfoSubject
            v-for="subject in subjectList"
            :key="subject.trackSubjectId"
            :subject="subject"
            @selected="changeSubject"
            :class="[subjectId == subject.trackSubjectId ? 'selected' : '']"
          />
          <div class="add-subject">
            <button class="add-btn" @click="onAdd('subject')">+</button>
            <div class="add-box">
              <input 
                type="text"
                :value="subjectData.subjectName"
                @input="insertSubject"
                placeholder="과정 이름"
              >
              <span>
                <button 
                  :class="[subjectData.subjectName.trim().length ? '' : 'disabled']" 
                  @click="addSubject"
                >추가</button>
                <button @click="offAdd('subject')">취소</button>
              </span>
            </div>
          </div>
        </div>
        <div v-if="subjectId" class="track-name" >
          <TrackInfoTrack
            v-for="track in trackList"
            :key="track.trackId"
            :track="track"
            @at="alertTrack"
          />
          <div class="add-track">
            <button class="add-btn" @click="onAdd('track')">+</button>
            <div class="add-box">
              <input 
                type="text"
                :value="trackData.trackName"
                @input="insertTrack"
                placeholder="트랙 이름"
              >
              <span>
                <button 
                  :class="[trackData.trackName.trim().length ? '' : 'disabled']" 
                  @click="addTrack"
                >추가</button>
                <button @click="offAdd('track')">취소</button>
              </span>
            </div>
          </div>
        </div>
        <div v-else class="track-empty">
          <span>교육 과정을 선택해주세요.</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import adminApi from '@/api/admin'
import TrackCurrent from './TrackCurrent.vue'
import TrackInfoSubject from './TrackInfoSubject.vue'
import TrackInfoTrack from './TrackInfoTrack.vue'
export default {
  name: 'TrackInfo',
  components: {
    TrackCurrent,
    TrackInfoSubject,
    TrackInfoTrack
  },
  data () {
    return {
      ord: null,
      subjectId: null,
      subjectName: null,
      subjectData: {
        ordinalNo: null,
        subjectName: '',
      },
      trackData: {
        subjectId: null,
        trackName: ''
      },
      msg: false,
      result: 0
    }
  },
  methods: {
    changeOrd(num) {
      this.ord = num
      this.subjectId = null
      this.subjectName = null
    },
    changeSubject(id, name) {
      this.subjectId = id
      this.subjectName = name
    },
    alertCurrent (success) {
      if (success) {
        this.result = 1
        this.msg = '현재 진행 중인 교육 과정이 변경되었습니다.'
      } else {
        this.result = 0
        this.msg = '교육 과정 변경에 실패했습니다.'
      }
      setTimeout(() => {
        this.msg = false
      }, 2000)
    },
    onAdd(target) {
      const input = document.querySelector(`.add-${target} > .add-box`)
      const btn = document.querySelector(`.add-${target} > .add-btn`)
      input.style.display = 'flex'
      btn.style.display = 'none'
      if (target === 'subject') {
        this.subjectData.ordinalNo = this.ord
      } else if (target === 'track') {
        this.trackData.subjectId = this.subjectId
      }
    },
    offAdd(target) {
      const input = document.querySelector(`.add-${target} > .add-box`)
      const btn = document.querySelector(`.add-${target} > .add-btn`)
      input.style.display = 'none'
      btn.style.display = 'flex'
      if (target === 'subject') {
        this.subjectData.ordinalNo = null
        this.subjectData.subjectName = ''
      } else if (target === 'track') {
        this.trackData.subjectName = null
        this.trackData.trackName = ''
      }
    },
    insertSubject(event) {
      this.subjectData.subjectName = event.target.value
    },
    insertTrack(event) {
      this.trackData.trackName = event.target.value
    },
    async addSubject() {
      const response = await adminApi.addSubject(this.subjectData)
      if (response.status === 200) {
        this.offAdd('subject')
        this.$store.dispatch('admin/getSubjectAll')
      }
    },
    async addTrack() {
      try {
        const response = await adminApi.addTrack(this.trackData)
        if (response.status === 200) {
          this.offAdd('track')
          this.$store.dispatch('admin/getTrackAll')
        }
      } catch (err) {
        if (err.response.data === 'same_name') {
          this.alertTrack(1)
        } else {
          this.offAdd('track')
          this.alertTrack(0)
        }
      }
    },
    alertTrack (val) {
      this.result = 0
      if (val) {
        this.msg = '같은 이름의 트랙은 등록할 수 없습니다.'
      } else {
        this.msg = '트랙 추가에 실패했습니다.'
      }
      setTimeout(() => {
        this.msg = false
      }, 2000)
    }
  },
  computed: {
    ...mapState(
      'admin', ['ordinalNo', 'subjects', 'tracks']
    ),
    subjectList () {
      return this.subjects.filter(subject => {
        return subject.trackSetting.ordinalNo === this.ord
      })
    },
    trackList () {
      return this.tracks.filter(track => {
        return track.trackSubject.trackSubjectId === this.subjectId
      }) 
    }
  },
  created () {
    this.ord = this.ordinalNo[0]
  }
}
</script>

<style>

</style>