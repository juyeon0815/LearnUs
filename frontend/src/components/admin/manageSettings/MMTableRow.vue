<template>
  <tr class="mm-row">
    <td class="name" @click="setTarget">{{ channel.name }}</td>
    <td>{{ channel.pathName }}</td>
    <td v-if="!channel.ordinalNo" class="track-list">
      <span 
        class="track-item"
        v-for="(track, idx) in channel.trackList"
        :key="idx"
      >{{ track.trackSubject.trackSetting.ordinalNo}}기 | {{ track.trackName }}</span>
    </td>
    <td v-else class="track-list">
       <span 
        class="track-item admin"
      >{{ channel.ordinalNo }}기 | 담당 프로 채널</span>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'MMTableRow',
  props: {
    channel: Object
  },
  methods: {
    setTarget () {
      this.$store.commit('admin/SET_TARGET_CHANNEL', this.channel)
      this.$emit('on-edit')
    }
  }
}
</script>