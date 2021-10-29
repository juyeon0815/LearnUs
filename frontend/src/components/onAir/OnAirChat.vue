<template>
  <div id="chat-box" class="on-air-chat">
    <AttendCheck v-if="attend"/>
    <AttendResult v-if="attendResult"/>
    <SolvingQuiz v-if="quiz"/>
    <OnAirChatList/>
    <OnAirChatInput @autosize="autosizeList"/>
  </div>
</template>

<script>
import './onAirChat.scss'
import AttendCheck from './chat/AttendCheck.vue'
import AttendResult from './chat/AttendResult.vue'
import SolvingQuiz from './chat/SolvingQuiz.vue'
import OnAirChatInput from './chat/OnAirChatInput.vue'
import OnAirChatList from './chat/OnAirChatList.vue'
export default {
  name: 'OnAirChat',
  components: {
    OnAirChatInput,
    OnAirChatList,
    AttendCheck,
    AttendResult,
    SolvingQuiz
  },
  data () {
    return {
      attend: false,
      attendResult: true,
      quiz: false,
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