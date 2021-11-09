<template>
  <div class="track-current">
    <div class="select-box">
      <select class="ordinal-select" v-model="selectTrack" @change="changeSubject">
        <!-- //<option disabled value="">전체보기</option> -->
        <option
          class="decorated"
          v-for="track in trackList"
          :value="track.trackName"
          :key="track.trackId"
          >{{ track.trackName }}</option
        >
      </select>
    </div>
  </div>
  {{ test }}
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "TrackInfo",
  data: () => {
    return {
      userInfo: null,
      selectTrack: "",
      selectTrackId: "",
    };
  },
  methods: {
    changeSubject(event) {
      this.selectTrack = event.target.value;
      console.log(this.selectTrack);

      this.selectTrackId = this.trackList.find((track) => {
        return track.trackName === this.selectTrack;
      });
      console.log(this.searchData.trackId, this.searchData.ordinalNo);
      this.$store.dispatch("replay/getBroadCastsByTrack", this.searchData);
    },
  },
  computed: {
    ...mapState("admin", ["tracks"]),
    trackList() {
      console.log(this.tracks);
      return this.tracks.filter((track) => {
        return track.trackSubject.subjectName === this.$route.params.category;
      });
    },
    searchData() {
      return {
        trackId: this.selectTrackId.trackId,
        ordinalNo: this.$store.state.account.userInfo.ordinalNo,
      };
    },
  },
  watch: {
    $route() {
      if (this.$route.params.category !== "all") {
        this.selectTrack = this.trackList[0].trackName;

        this.selectTrackId = this.trackList.find((track) => {
          return track.trackName === this.selectTrack;
        });
        console.log(this.searchData.trackId, this.searchData.ordinalNo);
        this.$store.dispatch("replay/getBroadCastsByTrack", this.searchData);
      }
    },
  },
  created() {
    this.selectTrack = this.trackList[0].trackName;
  },
};
</script>

<style></style>
