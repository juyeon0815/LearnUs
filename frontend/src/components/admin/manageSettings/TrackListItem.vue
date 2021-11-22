<template>
  <div class="track-item" v-if="subjects && tracks">
    <header>
      SSAFY <span>{{ ordinal }}</span>th
    </header>
    <div class="course">
      <span>{{ currentSubject.trackSetting.semester }}학기 과정</span>
      <span>•</span>
      <span class="subject">{{ currentSubject.subjectName }}</span>
    </div>
    <div class="names">
      <span
        v-for="item in currentTrack"
        :key="item.trackId"
      >
        {{ item.trackName }}</span>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'ManageTrackItem',
  props: {
    ordinal: Number
  },
  computed: {
    ...mapState(
      'admin', ['subjects', 'tracks']
    ),
    currentSubject () {
      return this.subjects.find(subject => {
        return subject.trackSetting.ordinalNo == this.ordinal && (subject.trackSetting.semester === 1 || subject.nowSubject === 'Y')
      })
    },
    currentTrack () {
      return this.tracks.filter(track => {
        return track.trackSubject.trackSubjectId === this.currentSubject.trackSubjectId
      })
    }
  },
}
</script>