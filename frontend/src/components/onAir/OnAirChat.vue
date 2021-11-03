<template>
  <div id="chat-box" class="on-air-chat">
    <AttendCheck v-if="attend"/>
    <AttendResult v-if="attendResult"/>
    <QuizResult v-if="quizResult"/>
    <SolvingQuiz v-if="quiz"/>
    <OnAirChatList/>
    <OnAirChatInput @autosize="autosizeList"/>
  </div>
</template>

<script>
import './onAirChat.scss'
import AttendCheck from './chat/AttendCheck.vue'
import AttendResult from './chat/AttendResult.vue'
import OnAirChatInput from './chat/OnAirChatInput.vue'
import OnAirChatList from './chat/OnAirChatList.vue'
import QuizResult from './chat/QuizResult.vue'
import SolvingQuiz from './chat/SolvingQuiz.vue'
export default {
  name: 'OnAirChat',
  components: {
    OnAirChatInput,
    OnAirChatList,
    // popup
    AttendCheck,
    AttendResult,
    QuizResult,
    SolvingQuiz
  },
  data () {
    return {
      attend: false,
      attendResult: false,
      quiz: false,
      quizResult: false,
    }
  },
  methods: {
    autosize () {
      const chat = document.getElementById('chat-box')
      const chatList = document.getElementById('chat-list')
      const chatInput = document.getElementById('chat-input')
      const pb = document.getElementById('popup-b')
      chatList.style.height = (chat.offsetHeight - chatInput.offsetHeight) + "px"
      chatList.scrollTop = chatList.scrollHeight
      if (pb) {
        pb.style.bottom = chatInput.offsetHeight + 'px'
      }
    }
  },
  mounted () {
    this.autosize()
    window.addEventListener('resize', this.autosize())
  }

}
</script>

<style>

</style>