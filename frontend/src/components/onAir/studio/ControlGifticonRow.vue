<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td>{{ student.user.name }} </td>
    <td>{{ student.user.userId }}</td>
    <td>{{ student.user.region }} {{ student.user.classNo }}반 
      <i class="fi fi-rr-cross-small cancle-btn" @click="deleteGifticon"></i>
    </td>
  </tr>
</template>

<script>
export default {
  name: 'ControlGifticonRow',
  props: {
    student: Object,
    idx: Number
  },
  methods: {
    deleteGifticon() {
      const gifticonData = {
        gifticonId: this.student.gifticonId,
        broadcastId: this.student.broadcast.broadcastId,
      }
      this.$store.dispatch('gifticon/deleteGifticon', gifticonData)
    },
  },
}
</script>

<style>

</style>