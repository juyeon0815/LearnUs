<template>
  <div class="live-schedule">
    <span class="title">LIVE<span class="colon">:</span>Schedule</span>
    <ScheduleList/>
  </div>
</template>

<script>
import ScheduleList from '@/components/schedule/ScheduleList'
import { mapActions } from 'vuex'

export default {
  name: 'LiveSchedule',
  components: {
    ScheduleList,
  },
  methods: {
    ...mapActions('admin', ['getBroadcastList'])
  },
  created() {
    this.getBroadcastList()
  }
}
</script>
