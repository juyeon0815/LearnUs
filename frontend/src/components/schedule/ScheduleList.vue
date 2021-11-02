<template>
  <div class="schedule-list">
    <span class="subtitle">TODAY</span>
    <ScheduleListItem/>
  </div>
</template>

<script>
import ScheduleListItem from './ScheduleListItem'

export default {
  name: 'ScheduleList',
  components: {
    ScheduleListItem,
  }
}
</script>
