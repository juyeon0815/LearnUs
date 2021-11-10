<template>
  <div class="live-schedule">
    <span class="title">LIVE<span class="colon">:</span>Schedule</span>
    <ScheduleList/>
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
    ...mapGetters('broadcast', ['broadcastAfterToday'])
  },
  created() {
    this.getBroadcastList()
  }
}
</script>
