<template>
  <div class="live-schedule">
    <span class="title">LIVE<span class="colon">:</span>Schedule</span>
    <ScheduleList/>
  </div>
</template>

<script>
import ScheduleList from '@/components/admin/schedule/ScheduleList'

export default {
  name: 'LiveSchedule',
  components: {
    ScheduleList,
  }
}
</script>
