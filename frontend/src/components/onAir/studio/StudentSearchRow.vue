<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td>{{ student.user.name }} </td>
    <td>{{ student.user.userId }}</td>
    <td>{{ student.user.region }} {{ student.user.classNo }}반 
      <i 
        class="fi fi-rr-add add-btn"
        v-if="gifticonUserIdList && !gifticonUserIdList.includes(student.user.userId)"
        @click="createGifticon"></i>
      <i 
        class="fi fi-rr-trash add-btn"
        v-if="gifticonUserIdList && gifticonUserIdList.includes(student.user.userId)"
        @click="deleteGifticon"></i>
    </td>
  </tr>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
  name: 'StudentSearchRow',
  props: {
    student: Object,
    idx: Number
  },
  methods: {
    createGifticon() {
      const gifticonData = {
        userId: this.student.user.userId,
        broadcastId: this.student.broadcast.broadcastId,
      }
      this.$store.dispatch('gifticon/createGifticon', gifticonData)
    },
    deleteGifticon() {
      const existingGifticon = this.gifticonList.find(gifticon => {
        return gifticon.user.userId === this.student.user.userId
      })
      const gifticonData = {
        gifticonId: existingGifticon.gifticonId,
        broadcastId: existingGifticon.broadcast.broadcastId,
      }
      this.$store.dispatch('gifticon/deleteGifticon', gifticonData)
    },
  },
  computed: {
    ...mapGetters('gifticon', ['gifticonUserIdList']),
    ...mapState('gifticon', ['gifticonList'])
  }
}
</script>

<style>

</style>