<template>
  <div class="live-schedule">
    <div v-if="broadcastByDate">
      <span class="title">LIVE<span class="colon">:</span>Schedule</span>
      <ScheduleList 
        v-for="(value, name) in broadcastByDate" 
        :key="name"
        :date="name"
        :scheduleList="value"/>
    </div>
  </div>
</template>

<script>
import "./liveSchedule.scss"
import ScheduleList from '@/components/broadcast/schedule/ScheduleList'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'LiveSchedule',
  components: {
    ScheduleList,
  },
  methods: {
    ...mapActions('broadcast', ['getBroadcastList'])
  },
  computed: {
    ...mapGetters('broadcast', ['broadcastByDate'])
  },
  created() {
    this.getBroadcastList()
  }
}
</script>
