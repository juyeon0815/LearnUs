<template>
  <div id="chat-input" class="chat-input">
    <div v-if="userInfo" class="input-box">
      <div class="profile">
        <img :src="userInfo.profileUrl" alt="">
      </div>
      <div class="input-area">
        <div class="user-info">{{ userInfo.nickname }}</div>
        <textarea 
          ref="chatInputArea"
          v-model="content"
          @keydown="autosizeInput"
          @keyup="autosizeInput"
          @keydown.enter.prevent
          @keyup.enter="send"
          type="text"
          placeholder="메시지를 입력해주세요."
        ></textarea>
        <i class="fi fi-rr-paper-plane" @click="send"></i>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
export default {
  name: 'OnAirChatInput',
  data () {
    return {
      content: '',
    }
  },
  methods: {
    autosizeInput () {
      const textarea = this.$refs.chatInputArea
      if (!this.content.length) {
        textarea.style.height = "28px"
      }
      textarea.style.height = "1px"
      textarea.style.height = (textarea.scrollHeight + 6) + "px"
      this.$emit('autosize', textarea.style.height)
    },
    send() {
      this.stomp.send(
        `/pub/chat.message.${this.currentBroadcastId}`,
        {},
        JSON.stringify({
          message: this.content,
          nickName: this.userInfo.nickname,
          userId: this.userInfo.userId,
          profileUrl: this.userInfo.profileUrl,
          regDate: new Date(),
        })
      )
      this.content = ''
    },
  },
  computed: {
    ...mapState('stomp', ['stomp']),
    ...mapState('account', ['userInfo']),
    ...mapGetters('broadcast', ['currentBroadcastId'])
  }
}
</script>

<style>

</style>