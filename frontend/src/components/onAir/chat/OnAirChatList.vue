<template>
  <div id="chat-list" class="chat-list">
    <OnAirChatListItem
      v-for="(chat, idx) in chatList"
      :key="idx"
      :chat=chat
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import OnAirChatListItem from './OnAirChatListItem'

export default {
  name: 'OnAirChatList',
  components: {
    OnAirChatListItem,
  },
  computed: {
    ...mapState('stomp', ['chatList'])
  },
}
</script>

<style>

</style>