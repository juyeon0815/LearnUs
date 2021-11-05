<template>
  <tr class="mm-row">
    <td>{{ channel.name }}</td>
    <td class="track-list">
      <span 
        class="track-item"
        v-for="(track, idx) in channel.trackList"
        :key="idx"
      >{{ track.trackName }}</span>
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