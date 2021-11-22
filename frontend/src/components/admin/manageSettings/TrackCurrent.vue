<template>
  <div class="current-item">
    <span>{{ ordinal }}기</span>
    <div class="select-box">
      <select 
        class="ordinal-select"
        v-model="subjectName"
        @change="changeSubject"
      >
        <option 
          class="decorated"
          v-for="subject in subjectList"
          :value="subject.subjectName"
          :key="subject.trackSubjectId"
        >{{ subject.subjectName }}</option>
      </select>
      <button @click="changeCurrent">변경</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import adminApi from '@/api/admin'
export default {
  name: 'TrackCurrent',
  props: {
    ordinal: Number
  },
  data () {
    return {
      subjectName: ''
    }
  },
  methods: {
    async changeCurrent () {
      try {
        const response = await adminApi.changeCurrentSubject(this.selectedSubject.trackSubjectId)
        if (response.status === 200) {
          this.$store.dispatch('admin/setTrackInfo')
          this.$emit('current', 1)
        }
      } catch (err) {
        this.$emit('current', 0)
      }
    }
  },
  computed: {
    ...mapState(
      'admin', ['subjects']
    ),
    subjectList () {
      return this.subjects.filter(subject => {
        return subject.trackSetting.ordinalNo === this.ordinal
      })
    },
    selectedSubject () {
      return this.subjectList.find(subject => {
        return subject.subjectName === this.subjectName
      })
    }
  },
  created () {
    if (this.subjectList.length === 1) {
      this.subjectName = this.subjectList[0].subjectName
    } else if (this.subjectList.length > 1) {
      const current = this.subjectList.find((subject) => {
        return subject.nowSubject === 'Y'
      })
      this.subjectName = current.subjectName
    }
  }
}
</script>