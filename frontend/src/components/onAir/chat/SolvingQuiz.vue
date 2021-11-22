<template>
  <div id ="popup-b" class="solving-quiz">
    <div class="question">
      <img src="@/assets/image/deco/quiz.png" alt="">
      <span>{{ currentQuiz.question }}</span>
    </div>
    <div class="answer-zone">
      <QuizOX v-if="currentQuiz.type === 'o'" :quiz="currentQuiz"/>
      <QuizChoice v-if="currentQuiz.type === 'c'" :quiz="currentQuiz"/>
      <QuizShort v-if="currentQuiz.type === 's'" :quiz="currentQuiz"/>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import QuizOX from '@/components/quiz/QuizOX'
import QuizChoice from '@/components/quiz/QuizChoice'
import QuizShort from '@/components/quiz/QuizShort'

export default {
  name: 'SolvingQuiz',
  components: {
    QuizOX,
    QuizChoice,
    QuizShort
  },
  computed: {
    ...mapState('stomp', ['stomp', 'currentQuiz']),
  },
  mounted () {
    this.$emit('popup')
  }
}
</script>

<style>

</style>