<template>
  <div class="replay-track">
    <multiselect 
      v-model="selectTrackId"
      class= "track-input"
      :allowEmpty="false"
      :closeOnSelect="true"
      :options=trackOptions
    ></multiselect>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import Multiselect from '@vueform/multiselect'
export default {
  name: 'ReplaySelect',
  components: {
    Multiselect
  },
  data () {
    return {
      trackOptions: [],
      selectTrackId: null,
      subjectId: null,
    }
  },
  computed: {
    ...mapState("account", ["userInfo"]),
    ...mapState("admin", ["tracks"]),
  },
  watch: {
    '$route'() {
      if (this.$route.name === 'Replay') {
        // subject 변경
        if (this.subjectId != this.$route.query.subject) {
          this.selectTrackId = Number(this.$route.params.track)
          this.subjectId = Number(this.$route.query.subject)
          if (this.subjectId != '0') {
            this.trackOptions = []
            this.tracks.forEach(track => {
              if (track.trackSubject.trackSubjectId === this.subjectId) {
                const option = {
                  value: track.trackId,
                  label: track.trackName
                }
                this.trackOptions.push(option)
              }
            })
          }
        } else if (this.selectTrackId != this.$route.params.track) {
          this.selectTrackId = this.$route.params.track
        }
      }
    },
    selectTrackId () {
      if (this.selectTrackId || this.selectTrackId != this.$route.params.track) {
        const data = {
          id: this.selectTrackId,
          ordinalNo: this.userInfo.ordinalNo
        }
        this.$store.dispatch('broadcast/getReplayTrackList', data)
        this.$router.push({
          name: 'Replay', 
          params: {
            track: this.selectTrackId,
          },
          query: {
            subject: this.$route.query.subject
          }
        })
      }
    }
  },
  created() {
    this.selectTrackId = Number(this.$route.params.track)
    this.subjectId = Number(this.$route.query.subject)
    if (this.subjectId != '0') {
      this.tracks.forEach(track => {
        if (track.trackSubject.trackSubjectId === this.subjectId) {
          const option = {
            value: track.trackId,
            label: track.trackName
          }
          this.trackOptions.push(option)
        }
      })
    }
  },
};
</script>

<style></style>
