<template>
  <div class="quiz-result">
    <div class="title-box">
      <div class="deco">
        <img src="@/assets/image/deco/trophy.png" alt="">
      </div>
      <span class="title">정답 자리</span>
    </div>
    <div class="winners">
      <div 
        class="rank-box"
        v-for="(rank, idx) in currentQuizResult.quizAnswerList"
        :key="idx"  
      >
        <div class="rank">{{ idx+1 }}</div>
        <span>{{ rank.user.nickname }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'QuizResult',
  computed: {
    ...mapState('stomp', ['currentQuizResult'])
  }
}
</script>

<style>

</style>