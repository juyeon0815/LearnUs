<template>
  <tr class="mm-row">
    <td>{{ channel.name }}</td>
    <td>
      <span 
        v-for="(track, idx) in channel.trackList"
        :key="idx"
      >{{ track }}</span>
    </td>
    <td>{{ channel.webhook }}</td>
  </tr>
</template>

<script>
export default {
  name: 'MMTableRow',
  props: {
    channel: Object
  }
}
</script>