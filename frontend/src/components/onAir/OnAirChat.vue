<template>
  <div class="on-air-chat">
    <OnAirChatList/>
    <OnAirChatInput/>
  </div>
</template>

<script>
import OnAirChatInput from './chat/OnAirChatInput.vue'
import OnAirChatList from './chat/OnAirChatList.vue'
export default {
  name: 'OnAirChat',
  components: {
    OnAirChatInput,
    OnAirChatList
  }
}
</script>

<style>

</style>