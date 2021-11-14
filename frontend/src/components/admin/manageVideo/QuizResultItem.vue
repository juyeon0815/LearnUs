<template>
  <div class="quiz-item">
    <div class="title">
      <span class="num">Q{{ num+1 }}.</span>
      <span class="question">{{ result.quiz.question }}</span>
    </div>
    <div class="result">
      <div class="count">
        <span class="val">{{ result.quiz.attendCount }}</span>
        <span class="label">참가자 수</span>
      </div>
      <div class="count">
        <span class="val">{{ result.quiz.answerCount }}</span>
        <span class="label">정답자 수</span>
      </div>
      <div class="rank-box">
        <div 
          v-for="(item, idx) in result.quizAnswerList"
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
export default {
  name: 'QuizResultItem',
  props: {
    result: Object,
    num: Number
  },
  data () {
    return {
      ranks: ['1st', '2nd', '3rd']
    }
  }
}
</script>

<style>

</style>