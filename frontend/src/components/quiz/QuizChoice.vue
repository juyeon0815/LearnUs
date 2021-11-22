<template>
  <div class="quiz-choice">
    <button 
      :class="[
        isSolved && answer === idx+1 ? 'selected' : '', 
        isSolved && answer != idx+1 ? 'unselected' : '', 
        'btn'
      ]"
      v-for="(select, idx) in quiz.quizSelectList"
      :key="idx"
      @click="sendAnswer(idx+1)"
    >{{ select }}</button>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
export default {
  name: 'QuizChoice',
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