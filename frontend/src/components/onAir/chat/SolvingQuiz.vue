<template>
  <div id ="popup-b" class="solving-quiz">
    <div class="question">
      <img src="@/assets/image/deco/quiz.png" alt="">
      <span>서울 3반 우만승이가 좋아하는 포켓몬의 이름은 무엇일까요?</span>
    </div>
    <div class="answer-zone">
      <QuizOX v-if="type === 'O'"/>
      <QuizChoice v-if="type === 'C'"/>
      <QuizShort v-if="type === 'S'"/>
    </div>
  </div>
</template>

<script>
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
  data () {
    return {
      type: 'S'
    }
  }
}
</script>

<style>

</style>