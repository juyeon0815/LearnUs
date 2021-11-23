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
import StompJs from 'stompjs';
import SockJS from 'sockjs-client';
import { mapState, mapActions, mapGetters } from 'vuex'

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
    AttendCheck,
    AttendResult,
    QuizResult,
    SolvingQuiz
  },
  methods: {
    ...mapActions('broadcast', ['getBroadcastDetail']),
    autosize () {
      const chat = document.getElementById('chat-box')
      const chatList = document.getElementById('chat-list')
      const chatInput = document.getElementById('chat-input')
      const pb = document.getElementById('popup-b')
      chatList.style.height = (chat.offsetHeight - chatInput.offsetHeight) + "px"
      this.autoscroll()
      if (pb) {
        pb.style.bottom = chatInput.offsetHeight + 'px'
      }
    },
    autoscroll () {
      const chatList = document.getElementById('chat-list')
      chatList.scrollTo({ top: chatList.scrollHeight, behavior: 'smooth' })
    },
    connect () {
      const sockJS = new SockJS(process.env.VUE_APP_STOMP_SERVER);
      const stomp = StompJs.over(sockJS);
      this.$store.commit('stomp/SET_STOMP', stomp)

      this.stomp.connect(
        "admin", "admin",
        () => {
          const id = this.$route.params.id
          this.stomp.subscribe(
            `/exchange/chat.exchange/chat.${id}`,
            (message) => {
              const payload = JSON.parse(message.body)
              this.$store.commit('stomp/ADD_CHAT_LIST', payload)
            },
            { "auto-delete": true, durable: false, exclusive: false }
          );
          this.stomp.send(
            `/pub/chat.enter.${id}`,
            {}
          )
          this.stomp.subscribe(
            `/exchange/quiz.exchange/quiz.${id}`,
            (message) => {
              const payload = JSON.parse(message.body)
              const key = Object.keys(payload)[0]
              if ( key === 'quiz') {
                this.$store.commit('stomp/SET_CURRENT_QUIZ', payload.quiz)
              } else if (key === 'quizRank') {
                this.$store.dispatch('stomp/getQuizList', this.currentBroadcastId)
                this.$store.commit('stomp/SET_CURRENT_QUIZ', null)
                this.$store.commit('stomp/SET_CURRENT_QUIZ_RESULT', payload.quizRank)
              }
              setTimeout(() => {
                this.$store.commit('stomp/SET_CURRENT_QUIZ_RESULT', null)
              }, 5000)
            },
            { "auto-delete": true, durable: false, exclusive: false }
          )
          this.stomp.subscribe(
            `/exchange/attendance.exchange/attendance.${id}`, 
            (message) => {
              const payload = JSON.parse(message.body)
              if (payload === 'attendance start') {
                this.$store.commit('stomp/SET_ATTEND_CHECK', true)
              } else if (payload === 'attendance stop') {
                this.$store.commit('stomp/SET_ATTEND_CHECK', false)
              } else if (payload === 'broadcast start') {
                this.getBroadcastDetail(id)
              } else if (payload === 'broadcast stop') {
                this.getBroadcastDetail(id)
              }
            },
            {'auto-delete':true, 'durable':false, 'exclusive':false})
          if (this.$route.name === 'OnAirStudio') {
            this.stomp.subscribe(
              `/exchange/admin.exchange/admin.${id}`,
              (message) => {
                const payload = JSON.parse(message.body)
                this.$store.commit('stomp/SET_VIEWERS', payload.viewer)
              },
              { "auto-delete": true, durable: false, exclusive: false }
            )
          }
        },
        onerror,
        "/"
      )
      this.stomp.debug = null
    }
  },
  computed: {
    ...mapState('account', ['userInfo']),
    ...mapState('stomp', ['stomp', 'currentQuiz', 'currentQuizResult', 'attendCheck', 'attendResult']),
    ...mapGetters('broadcast', ['currentBroadcastId']),
    ...mapGetters('stomp', ['chatLength'])
  },
  watch: {
    chatLength () {
      this.$nextTick(() => {
        this.autosize()
      })
    }
  },
  async created() {
    this.$store.dispatch('stomp/getChatList', this.$route.params.id)
    this.$store.dispatch('stomp/isAttendCheck', this.$route.params.id)
    this.connect()
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
