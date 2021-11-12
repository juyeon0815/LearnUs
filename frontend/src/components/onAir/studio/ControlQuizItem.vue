<template>
  <div class="quiz-box">
    <div class="quiz-zone">
      <div class="question">
        <span class="number">Q{{ num + 1}}.</span>
        <span>{{ quiz.question }}</span>
      </div>
      <div class="quiz-info">
        <div class="quiz-col">
          <div class="label">TYPE</div>
          <div class="val">{{ type[quiz.type] }}</div>
        </div>
        <div class="quiz-col">
          <div class="label">ANSWER</div>
          <div class="val">{{ answer }}</div>
        </div>
      </div>
    </div>
    <div class="btn-row">
      <button class="btn dark-orange" @click="onDelete">
        <i class="btn fi fi-rr-trash"></i>
        <span>퀴즈 삭제</span>
      </button>
      <button class="btn dark-orange" @click="onUpdate">
        <i class="btn fi fi-rr-pencil"></i>
        <span>퀴즈 수정</span>
      </button>
      <button class="btn dark-orange">
        <i class="btn fi fi-rr-flame"></i>
        <span>퀴즈 출제</span>
      </button>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'SolvingQuiz',
  props: {
    quiz: Object,
    num: Number
  },
  data () {
    return {
      type: {
        'o': 'OX',
        'c': '객관식',
        's': '주관식',
      }
    }
  },
  methods: {
    ...mapActions('stomp', ['deleteQuiz']),
    onDelete () {
      this.deleteQuiz(this.quiz)
    },
    onUpdate () {
      this.$store.commit('stomp/SET_QUIZ_TARGET', this.quiz)
      this.$emit('updateQuiz')
    }
  },
  computed: {
    answer () {
      if (this.quiz.type === 'o') {
        if (this.quiz.answer) {
          return 'O'
        }
        return 'X'
      } else if (this.quiz.type === 'c') {
        return this.quiz.answer + '번 ' + this.quiz.quizSelectList[this.quiz.answer-1]
      }
      return this.quiz.answer
    }
  }
}
</script>

<style>

</style>