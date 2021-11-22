<template>
  <div class="chat-item">
    <div class="profile">
      <img :src="chat.profileUrl" alt="">
      <i v-if="adminUser" class="fi fi-sr-crown admin-mark"></i>
    </div>
    <div class="chat-content">
      <span :class="[adminUser ? 'admin' : '', 'user']">{{ chat.nickName }}</span>{{ chat.message }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'OnAirChatListItem',
  props: {
    chat: Object
  },
  computed: {
    adminUser () {
      return this.chat.isAdmin
    }
  }
}
</script>

<style>

</style>