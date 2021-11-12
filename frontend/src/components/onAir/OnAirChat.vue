<template>
  <div id="chat-box" class="on-air-chat">
    <AttendCheck v-if="attend"/>
    <AttendResult v-if="attendResult"/>
    <QuizResult v-if="quizResult"/>
    <SolvingQuiz v-if="quiz"/>
    <OnAirChatList/>
    <OnAirChatInput @autosize="autosize"/>
  </div>
</template>

<script>
// import SockJS from 'sockjs-client';
// import Stomp from '@stomp/stompjs';
import { mapState } from 'vuex'
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
  computed: {
    ...mapState('account', ['userInfo']),
    ...mapState('broadcast', ['broadcastDetail']),
    ...mapState('stomp', ['stomp']),
  },
  // async created() {
  //   let roomId = this.broadcastDetail.broadcastId
  //   let nickName = this.userInfo.nickname

  //   this.stomp.connect(
  //     "guest",
  //     "guest",
  //     (frame) => {
  //       console.log("frame : ", frame);
  //       console.log("STOMP CONNECT");
  //       console.log("params : ", roomId);
  //       this.stomp.subscribe(
  //         `/exchange/chat.exchange/chat.${roomId}`,
  //         function (message) {
  //           const payload = JSON.parse(message.body);

  //           const html = `<div>
  //                               <div>${payload.nickName} : ${payload.message}</div>
  //                               </div>`;
  //           document
  //             .getElementById("chats")
  //             .insertAdjacentHTML("beforeend", html);
  //         },
  //         { "auto-delete": true, durable: false, exclusive: false }
  //       );
  //       this.stomp.send(
  //         `/pub/chat.enter.${roomId}`,
  //         {},
  //         JSON.stringify({
  //           nickName: nickName,
  //           chatRoomId: roomId,
  //         })
  //       );
  //     },
  //     onerror,
  //     "/"
  //   );
  // },
  mounted () {
    this.autosize()
    window.addEventListener('resize', this.autosize())
  }

}
</script>

<style>

</style>