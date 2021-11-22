<template>
  <div v-if="broadcastDetail" class="video-info">
    <h3 class="title">{{ broadcastDetail.title }}</h3>
    <div class="sub-info">
      <div class="date">{{ broadcastDate }}</div>
      <div class="instructor">
        <span>by. </span>
        {{ broadcastDetail.teacher }}
      </div>
    </div>
    <div class="descript">{{ broadcastDetail.description }}</div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import moment from 'moment'
export default {
  name: 'OnAirVideoInfo',
  computed: {
    ...mapState('broadcast', ['broadcastDetail']),
    broadcastDate () {
      return moment(this.broadcastDetail.broadcastDate).locale('ko').format('LL');
    }
  }
}
</script>
