<template>
  <div class="popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1 v-if="quizId">Update<span class="t-orange">:</span>Quiz</h1>
      <h1 v-else>Create<span class="t-orange">:</span>Quiz</h1>
      <div class="quiz-form">
        <div class="input-box">
          <input
            @input="insertQuestion"
            :value="question"
            class= "quiz-input" 
            type="text"
            placeholder="문제를 입력해주세요."
            required
          >
          <label>Question</label>
        </div>
        <div class="half">
          <div class="input-box">
            <multiselect 
              v-model="type"
              class= "quiz-input"
              :closeOnSelect="false"
              :options=quizOptions
              placeholder="퀴즈 유형"
              required
            ></multiselect>
            <label>Type</label>
          </div>
          <div class="input-box">
            <multiselect 
              v-if="answerOptions && selectComplete"
              v-model="answer"
              class= "quiz-input"
              :closeOnSelect="false"
              :options=answerOptions
              placeholder="정답을 입력해주세요."
              required
            ></multiselect>
            <div
              v-else-if="answerOptions" 
              class="wrong-input"
            >보기를 모두 입력해주세요.</div>
            <input
              v-else
              @input="insertAnswer"
              :value="answer"
              class= "quiz-input" 
              type="text"
              placeholder="정답을 입력해주세요."
              required
            >
            <label>Answer</label>
          </div>
        </div>
        <div v-if="type === 'c'" class="half">
          <div class="input-box">
            <input
              @input="insertSelect($event, 1)"
              :value="select1"
              class= "quiz-input" 
              type="text"
              placeholder="문제를 입력해주세요."
              required
            >
            <label>보기 1</label>
          </div>
          <div class="input-box">
            <input
              @input="insertSelect($event, 2)"
              :value="select2"
              class= "quiz-input" 
              type="text"
              placeholder="문제를 입력해주세요."
              required
            >
            <label>보기 2</label>
          </div>
          <div class="input-box">
            <input
              @input="insertSelect($event, 3)"
              :value="select3"
              class= "quiz-input" 
              type="text"
              placeholder="문제를 입력해주세요."
              required
            >
            <label>보기 3</label>
          </div>
          <div class="input-box">
            <input
              @input="insertSelect($event, 4)"
              :value="select4"
              class= "quiz-input" 
              type="text"
              placeholder="문제를 입력해주세요."
              required
            >
            <label>보기 4</label>
          </div>
          
        </div>
        <button 
          v-if="quizId"
          @click="onEditQuiz"
          :class="[isSubmit ? 'dark-orange' : 'disabled', 'btn']"
        >퀴즈 수정</button>
        <button 
          v-else
          @click="onAddQuiz"
          :class="[isSubmit ? 'dark-orange' : 'disabled', 'btn']"
        >퀴즈 생성</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import Multiselect from '@vueform/multiselect'
export default {
  name: 'ControlQuizForm',
  components: {
    Multiselect
  },
  data () {
    return {
      broadcastId: null,
      answer: null,
      question: null,
      type: null,
      select1: null,
      select2: null,
      select3: null,
      select4: null,
      quizId: null
    }
  },
  methods: {
    ...mapActions('stomp', ['addQuiz', 'editQuiz']),
    insertQuestion (event) {
      this.question = event.target.value
    },
    insertAnswer (event) {
      this.answer = event.target.value
    },
    insertSelect (event, num) {
      if (num === 1) {
        this.select1 = event.target.value
      } else if (num === 2) {
        this.select2 = event.target.value
      } else if (num === 3) {
        this.select3 = event.target.value
      } else if (num === 4) {
        this.select4 = event.target.value
      }
    },
    async onAddQuiz () {
      const result = await this.addQuiz(this.quizData)
      if (result.status === 200) {
        this.$emit('close')
      } 
    },
    async onEditQuiz () {
      const updateData = this.quizData
      updateData['quizId'] = this.quizId
      const result = await this.editQuiz(updateData)
      if (result.status === 200) {
        this.$emit('close')
      } 
    },
  },
  computed: {
    ...mapState('stomp', ['quizOptions', 'quizTarget']),
    ...mapState('broadcast', ['broadcastDetail']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
    selectComplete () {
      if (this.type === 'c') {
        return this.quizData.quizSelectList.every(select => {
          return select
        })
      }
      return true
    },
    answerOptions () {
      if (this.type === 'c') {
        return [
          { value: '1', label: '1번 ' + this.select1 },
          { value: '2', label: '2번 ' + this.select2 },
          { value: '3', label: '3번 ' + this.select3 },
          { value: '4', label: '4번 ' + this.select4 },
        ]
      } else if (this.type === 'o') {
        return [
          { value: '1', label: 'O' },
          { value: '0', label: 'X' }
        ]
      }
      return null
    },
    quizData () {
      if (this.type === 'c') {
        return {
          broadcastId: this.broadcastId,
          answer: this.answer,
          question: this.question,
          type: this.type,
          quizSelectList: [
            this.select1,
            this.select2,
            this.select3,
            this.select4,
          ]
        }
      } else {
        return {
          broadcastId: this.broadcastId,
          answer: this.answer,
          question: this.question,
          type: this.type,
        }
      }
    },
    isSubmit () {
      return Object.values(this.quizData).every(data => {
        return data
      })
    },
  },
  watch: {
    type () {
      this.answer = null
    }
  },
  created () {
    this.broadcastId = this.broadcastDetail.broadcastId
    if (this.quizTarget) {
      this.quizId = this.quizTarget.quizId
      this.question = this.quizTarget.question
      this.type = this.quizTarget.type
      this.answer = this.quizTarget.answer
      if (this.type === 'c') {
        this.select1 = this.quizTarget.quizSelectList[0]
        this.select2 = this.quizTarget.quizSelectList[1]
        this.select3 = this.quizTarget.quizSelectList[2]
        this.select4 = this.quizTarget.quizSelectList[3]
      }
    }
  },
  unmounted () {
    this.$store.commit('stomp/SET_QUIZ_TARGET', null)
  }
}
</script>

<style>

</style>