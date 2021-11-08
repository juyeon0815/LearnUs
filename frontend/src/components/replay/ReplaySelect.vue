<template>
  <div class="track-current">
    <div class="select-box">
      <select 
        class="ordinal-select"
        v-model="this.trackList[0].trackName"
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
      subjectId: null,
      userInfo: null,
      subjectName: '',
      category : ''
    }
  },
  methods: {
    
  },
  computed: {
    ...mapState(
      'admin', ['tracks']
    ),
    trackList () {
     return this.tracks.filter(track => {
        return track.trackSubject.subjectName === this.$route.params.category;
      }) 
    }
  },
}

</script>

<style>

</style>