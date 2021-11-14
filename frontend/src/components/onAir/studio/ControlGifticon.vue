<template>
  <div class="control-gifticon">
    <StudentSearchModal
      v-if="isSearchModalOn" 
      @close="closeSearchModal"/>
    <i 
      class="fi fi-sr-add gifticon-add-btn"
      @click="openSearchModal"></i>
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
import StudentSearchModal from './StudentSearchModal.vue'

export default {
  name: 'ControlGifticon',
  components: {
    ControlGifticonRow,
    StudentSearchModal,
  },
  data () {
    return {
      currentPage: 1,
      isSearchModalOn: false,
    }
  },
  methods: {
    ...mapActions('gifticon', ['getGifticonList']),
    openSearchModal() {
      this.isSearchModalOn = true
    },
    closeSearchModal() {
      this.isSearchModalOn = false
    },
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