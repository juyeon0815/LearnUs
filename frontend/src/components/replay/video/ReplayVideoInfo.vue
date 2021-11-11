<template>
  <div class="video-info">
    <span>{{description}}</span>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: 'OnAirVideoInfo',
  data() {
    return {
      description: '',
    };
  },
  computed:{
    ...mapState("replay",["broadCastInfo"]),
  },
  mounted() {
    this.description = this.broadCastInfo.broadcast.description
  },
}
</script>
