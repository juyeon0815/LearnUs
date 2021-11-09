<template>
  <div class="track-current">
    <div class="select-box">
      <select 
        class="ordinal-select"
        v-model="trackName"
        @change="changeSubject"
      >
        <option 
          class="decorated"
          v-for="track in trackList"
          :value="track.trackName"
          :key="track.trackId"
        >{{ track.trackName }}</option>
      </select>
  </div>  
    </div>
    {{param}}
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'TrackInfo',
 data: () => {
    return {
      userInfo: null,
      trackName : ''
    }
  },
  methods:{
    changeSubject(event){
      this.$store.commit('replay/SET_SUBJECT', event.target.value)
    },
  },
  computed: {
    ...mapState('admin', ['tracks']),
    trackList () {
      console.log(this.tracks)
     return this.tracks.filter(track => {
        return track.trackSubject.subjectName === this.$route.params.category;
      }) 
    },
  },
  created () {
    this.trackName = this.trackList[0].trackName
  }
}

</script>

<style>

</style>