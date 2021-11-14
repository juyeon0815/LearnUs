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
        class="fi fi-rr-cross-small add-btn"
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
    async createGifticon() {
      const gifticonData = {
        userId: this.student.user.userId,
        broadcastId: this.student.broadcast.broadcastId,
      }
      const result = await this.$store.dispatch('gifticon/createGifticon', gifticonData)
      if (result.status === 200) {
        const info = {
          type: 'success',
          message: '기프티콘 목록이 추가되었습니다.'
        }
        this.$emit('onAlert', info)
      }
    },
    async deleteGifticon() {
      const existingGifticon = this.gifticonList.find(gifticon => {
        return gifticon.user.userId === this.student.user.userId
      })
      const gifticonData = {
        gifticonId: existingGifticon.gifticonId,
        broadcastId: existingGifticon.broadcast.broadcastId,
      }
      const result = await this.$store.dispatch('gifticon/deleteGifticon', gifticonData)
      if (result.status === 200) {
        const info = {
          type: 'success',
          message: '기프티콘 목록에서 삭제되었습니다.'
        }
        this.$emit('onAlert', info)
      }
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