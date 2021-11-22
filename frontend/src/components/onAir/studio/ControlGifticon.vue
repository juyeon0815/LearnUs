<template>
  <div class="control-gifticon">
    <div class="student-table" v-if="gifticonList">
        <table>
          <thead>
            <tr>
              <th>No.</th>
              <th>이름</th>
              <th>학번</th>
              <th>분반</th>
            </tr>
          </thead>
          <tbody>
            <ControlGifticonRow
              v-for="(student, idx) in gifticonList"
              :key="idx"
              :student="student"
              :idx="idx"
            />
          </tbody>
        </table>
        <div v-if="!gifticonList.length" class="empty">
          기프티콘 목록이 없습니다.
        </div>
      </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import ControlGifticonRow from './ControlGifticonRow.vue'

export default {
  name: 'ControlGifticon',
  components: {
    ControlGifticonRow,
  },
  data () {
    return {
      currentPage: 1,
    }
  },
  methods: {
    ...mapActions('gifticon', ['getGifticonList']),
  },
  created() {
    this.getGifticonList(this.$route.params.id)
  },
  computed: {
    ...mapState('gifticon', ['gifticonList']),
  }
}
</script>

<style>

</style>