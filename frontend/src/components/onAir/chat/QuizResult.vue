<template>
  <div class="quiz-result">
    <div class="title-box">
      <div class="deco">
        <img src="@/assets/image/deco/trophy.png" alt="">
      </div>
      <span class="title">{{ answer }}</span>
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
    ...mapState('stomp', ['currentQuizResult']),
    answer () {
      if (this.currentQuizResult.quiz.type === 'o') {
        if (this.currentQuizResult.quiz.answer === '1') {
          return 'O'
        }
        return 'X'
      } 
      else if (this.currentQuizResult.quiz.type === 'c') {
        let answer = this.currentQuizResult.quiz.answer
        return answer + '번 ' + this.currentQuizResult.quizSelectList[answer-1].view
      }
      return this.currentQuizResult.quiz.answer
    }
  }
}
</script>

<style>

</style>