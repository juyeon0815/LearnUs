<template>
  <div class="quiz-info">
    <QuizResultItem
      v-for="(result, idx) in quizResult"
      :key="idx"
      :result="result"
      :num="idx"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import QuizResultItem from './QuizResultItem.vue'

export default {
  name: 'QuizResult',
  components: {
    QuizResultItem
  },
  computed: {
    ...mapState('broadcast', ['quizResult'])
  },
}
</script>
