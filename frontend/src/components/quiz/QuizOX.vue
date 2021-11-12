<template>
  <div class="quiz-ox">
    <span 
      @click="sendAnswer(1)"
      :class="[
        isSolved && answer === 1 ? 'selected' : '', 
        isSolved && answer === 0 ? 'unselected' : '', 
      ]"
    >O</span>
    <span 
      @click="sendAnswer(0)"
      :class="[
        isSolved && answer === 0 ? 'selected' : '', 
        isSolved && answer === 1 ? 'unselected' : '', 
      ]"
    >X</span>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import './quiz.scss'
export default {
  name: 'QuizOX',
  props: {
    quiz: Object
  },
  data () {
    return {
      answer: null,
      isSolved: false,
    }
  },
  computed: {
    ...mapState('stomp', ['stomp']),
    ...mapState('account', ['userInfo']),
    ...mapGetters('broadcast', ['currentBroadcastId'])
  },
  methods: {
    sendAnswer(answer) {
      this.stomp.send(
        `/pub/quiz.answer`,
        {},
        JSON.stringify({
          quizId: this.quiz.quizId,
          userId: this.userInfo.userId,
          broadcastId: this.currentBroadcastId,
          answer: answer,
        })
      )
      this.isSolved = true
      this.answer = answer
    },
  }
}
</script>

<style>

</style>