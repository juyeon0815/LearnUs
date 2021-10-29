<template>
  <div id="chat-box" class="on-air-chat">
    <AttendCheck/>
    <OnAirChatList/>
    <OnAirChatInput @autosize="autosizeList"/>
  </div>
</template>

<script>
import './onAirChat.scss'
import AttendCheck from './chat/AttendCheck.vue'
import OnAirChatInput from './chat/OnAirChatInput.vue'
import OnAirChatList from './chat/OnAirChatList.vue'
export default {
  name: 'OnAirChat',
  components: {
    OnAirChatInput,
    OnAirChatList,
    AttendCheck
  },
  methods: {
    autosizeList () {
      const chat = document.getElementById('chat-box')
      const chatList = document.getElementById('chat-list')
      const chatInput = document.getElementById('chat-input')
      console.log(chatInput.offsetHeight)
      chatList.style.height = (chat.offsetHeight - chatInput.offsetHeight) + "px"
    }
  },
  mounted () {
    this.autosizeList()
    window.addEventListener('resize', this.autosizeList())
  }

}
</script>

<style>

</style>