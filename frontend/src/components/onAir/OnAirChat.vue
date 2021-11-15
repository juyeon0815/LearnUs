<template>
  <div id="chat-box" class="on-air-chat">
    <AttendCheck v-if="attendCheck"/>
    <AttendResult v-if="attendResult"/>
    <QuizResult v-if="currentQuizResult"/>
    <SolvingQuiz v-if="currentQuiz" @popup="autosize"/>
    <OnAirChatList/>
    <OnAirChatInput @autosize="autosize"/>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
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
    ...mapState('stomp', ['stomp', 'currentQuiz', 'currentQuizResult', 'attendCheck', 'attendResult']),
    ...mapGetters('broadcast', ['currentBroadcastId'])
  },
  async created() {
    await this.$store.dispatch('stomp/getChatList', this.currentBroadcastId)

    await this.stomp.connect(
      "admin",
      "admin",
      async (frame) => {
        console.log(frame);

        await this.stomp.subscribe(
          `/exchange/chat.exchange/chat.${this.currentBroadcastId}`,
          (message) => {
            const payload = JSON.parse(message.body);
            const data = {
              userId: payload.userId,
              nickName: payload.nickName,
              profileUrl: payload.profileUrl,
              message: payload.message,
              regDate: payload.regDate
            }
            this.$store.commit('stomp/ADD_CHAT_LIST', data)
            this.autosize()
          },
          { "auto-delete": true, durable: false, exclusive: false }
        );
        await this.stomp.send(
          `/pub/chat.enter.${this.currentBroadcastId}`,
          {}
        )
        await this.stomp.subscribe(
          `/exchange/quiz.exchange/quiz.${this.currentBroadcastId}`,
          (message) => {
            const payload = JSON.parse(message.body)
            const key = Object.keys(payload)[0]
            if ( key === 'quiz') {
              this.$store.commit('stomp/SET_CURRENT_QUIZ', payload.quiz)
            } else if (key === 'quizRank') {
              this.$store.commit('stomp/SET_CURRENT_QUIZ', null)
              this.$store.commit('stomp/SET_CURRENT_QUIZ_RESULT', payload.quizRank)
            }
            setTimeout(() => {
              this.$store.commit('stomp/SET_CURRENT_QUIZ_RESULT', null)
            }, 5000)
          },
          { "auto-delete": true, durable: false, exclusive: false }
        )
        await this.stomp.subscribe(
          `/exchange/attendance.exchange/attendance.${this.currentBroadcastId}`, 
          (message) => {
            const payload = JSON.parse(message.body)
            console.log(payload);
            if (payload === 'attendance start') {
              this.$store.commit('stomp/SET_ATTEND_CHECK', true)
            } else if (payload === 'attendance stop') {
              this.$store.commit('stomp/SET_ATTEND_CHECK', false)
            }
          },
          {'auto-delete':true, 'durable':false, 'exclusive':false})
        if (this.$route.name === 'OnAirStudio') {
          await this.stomp.subscribe(
            `/exchange/admin.exchange/admin.${this.currentBroadcastId}`,
            (message) => {
              const payload = JSON.parse(message.body)
              console.log(payload)
            },
            { "auto-delete": true, durable: false, exclusive: false }
          )
        }
      },
      onerror,
      "/"
    );
  },
  mounted () {
    this.autosize()
    window.addEventListener('resize', this.autosize())
  },
  unmounted () {
    this.stomp.disconnect()
  }

}
</script>

<style>

</style>