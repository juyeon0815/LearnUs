<template>
  <div class="quiz-short">
    <input 
      id="short-input"
      type="text" 
      @input="insertAnswer"
      :value="answer" 
      :class="[isSolved ? 'solved': '']"
      @keyup.enter="sendAnswer"
      placeholder="정답을 입력해주세요!"
    >
    <button v-if="!isSolved" @click="sendAnswer">GO</button>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
export default {
  name: 'QuizShort',
  props: {
    quiz: Object
  },
  data () {
    return {
      answer: '',
      isSolved: false,
    }
  },
  computed: {
    ...mapState('stomp', ['stomp']),
    ...mapState('account', ['userInfo']),
    ...mapGetters('broadcast', ['currentBroadcastId'])
  },
  methods: {
    insertAnswer (event) {
      this.answer = event.target.value
    },
    sendAnswer() {
      this.stomp.send(
        `/pub/quiz.answer`,
        {},
        JSON.stringify({
          quizId: this.quiz.quizId,
          userId: this.userInfo.userId,
          broadcastId: this.currentBroadcastId,
          answer: this.answer,
        })
      )
      this.isSolved = true
      let input = document.getElementById('short-input')
      input.disabled = true
    },
  }
}
</script>

<style>

</style>