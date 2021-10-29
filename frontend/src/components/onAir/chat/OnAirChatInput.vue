<template>
  <div id="chat-input" class="chat-input">
    <div class="input-box">
      <div class="profile"></div>
      <div class="input">
        <div class="user-info">우만승[서울_3반_A306]</div>
        <textarea 
          ref="chatInputArea"
          v-model="content"
          @keydown="autosizeInput"
          @keyup="autosizeInput"
          type="text"
          placeholder="메시지를 입력해주세요."
        ></textarea>
        <i class="fi fi-rr-paper-plane"></i>
      </div>
    </div>
  </div>
</template>

<script>
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
    }
  }
}
</script>

<style>

</style>