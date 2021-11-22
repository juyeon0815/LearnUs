<template>
  <div class="schedule-list">
    <span class="subtitle">{{ date }}</span>
    <ScheduleListItem 
      v-for="broadcast in scheduleList"
      :key="broadcast.broadcastId"
      :broadcast="broadcast"
      @onCancel="$emit('onCancel', broadcast)"/>
  </div>
</template>

<script>
import ScheduleListItem from './ScheduleListItem'

export default {
  name: 'ScheduleList',
  components: {
    ScheduleListItem,
  },
  props: {
    date: String,
    scheduleList: Array,
  },
}
</script>
