<template>
  <div v-if="activeStudents" class="award">
    <div class="award-row">
      <div class="title">
        <i class="fi fi-rr-comment"></i>
        <span>Chat</span>
      </div>
      <div class="rank-box">
        <div 
          v-for="(item, idx) in activeStudents.chat"
          :key="idx"
          class="user"
        >
          <span class="rank">{{ ranks[idx] }}</span>
          <img class="profile" :src="item.user.profileUrl" alt="">
          <span class="nickname">{{ item.user.nickname }}</span>
        </div>
      </div>
    </div>
    <div class="award-row">
      <div class="title">
        <i class="fi fi-rr-crown"></i>
        <span>Quiz</span>
      </div>
      <div class="rank-box">
        <div 
          v-for="(item, idx) in activeStudents.quiz"
          :key="idx"
          class="user"
        >
          <span class="rank">{{ ranks[idx] }}</span>
          <img class="profile" :src="item.user.profileUrl" alt="">
          <span class="nickname">{{ item.user.nickname }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'BroadcastAward',
  data () {
    return {
      ranks: ['1st', '2nd', '3rd']
    }
  },
  computed: {
    ...mapState('broadcast', ['activeStudents'])
  }
}
</script>

<style>

</style>