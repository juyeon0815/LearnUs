<template>
  <div class="chat-item">
    <div class="profile"></div>
    <div class="chat-content">
      <span class="user">{{ chat.user.nickname }}</span>{{ chat.content }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'OnAirChatListItem',
  props: {
    chat: Object
  }
}
</script>

<style>

</style>