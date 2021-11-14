<template>
  <div class="control-quiz">
    <ControlQuizForm v-if="quizForm" @close="quizForm = false"/>
    <div class="quiz-list">
      <ControlQuizItem
        v-for="(quiz, idx) in quizList"
        :key="idx"
        :quiz="quiz"
        :num="idx"
        @updateQuiz="quizForm = true"
      />
      <div class="add-box">
        <i 
          @click="quizForm = true"
          class="fi fi-rr-add add-btn btn"
        ></i>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import ControlQuizForm from './ControlQuizForm.vue'
import ControlQuizItem from './ControlQuizItem.vue'
export default {
  name: 'ControlQuiz',
  components: {
    ControlQuizForm,
    ControlQuizItem
  },
  data () {
    return {
      quizForm: false,
    }
  },
  computed: {
    ...mapState('stomp', ['quizList'])
  }
}
</script>