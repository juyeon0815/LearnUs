<template>
  <div class="winner-list">
    <div class="chapter">Today's Ranking<span class="t-orange">:</span></div>
    <div class="content" v-if="awardList">
      <div class="content-item">
        <div class="title">오늘의 퀴즈왕</div>
        <div class="award" v-if="awardList.quiz.length">
          <WinnerListItem
            v-for="(award, idx) in awardList.quiz"
            :award="award"
            :key="idx"
            :idx="idx"
          />
        </div>
        <div class="award" v-if="!awardList.quiz.length">
          <div class="winner-item">
            <span>퀴즈 진행 후 공개됩니다</span>
          </div>
        </div>
      </div>
      <div class="content-item">
        <div class="title">오늘의 참여왕</div>
        <div class="award" v-if="awardList.chat.length">
          <WinnerListItem
            v-for="(award, idx) in awardList.chat"
            :award="award"
            :key="idx"
            :idx="idx"
          />
        </div>
        <div class="award" v-if="!awardList.chat.length">
          <div class="winner-item">
            <span>수업 진행 후 공개됩니다</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex'
import WinnerListItem from './WinnerListItem.vue'

export default {
  name: 'WinnerList',
  components: {
    WinnerListItem
  },
  methods: {
    ...mapActions('gifticon', ['getAwardList']),
    ...mapMutations('gifticon', ['SET_AWARD_LIST']),
  },
  computed: {
    ...mapState('gifticon', ['awardList'])
  },
  created() {
    this.getAwardList()
  },
  unmounted() {
    this.SET_AWARD_LIST(null)
  }
}
</script>

<style>

</style>