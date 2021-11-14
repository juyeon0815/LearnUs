<template>
  <div class="chat-item">
    <div class="profile">
      <img :src="chat.profileUrl" alt="">
    </div>
    <div class="chat-content">
      <span class="user">{{ chat.nickName }}</span>{{ chat.message }}
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