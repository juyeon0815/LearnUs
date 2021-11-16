<template>
  <div class="profile">
    <ProfileBox v-if="isLogin"/>
  </div>
</template>

<script>
import './profile.scss'
import ProfileBox from '@/components/account/profile/ProfileBox'

export default {
  name: 'Profile',
  components: {
    ProfileBox,
  },
  computed: {
    isLogin() {
      return this.$store.state.account.userInfo !== null
    }
  }
}
</script>