<template>
  <div class="account">
    <div class="user-form">
      <i class="fi fi-rr-arrow-small-left direct-back"
      @click="$router.push({ name: 'Account', params: {page: 'login'}})"
      v-if='showBack'></i>
      <LoginForm v-if="current === 'login'"/>
      <FindEmailForm v-if="current === 'find-email'"/>
      <FindPasswordForm v-if="current === 'find-password'"/>
      <ResetPasswordForm v-if="current === 'reset-password'"/>
    </div>
  </div>
</template>

<script>
import './account.scss'
import LoginForm from '@/components/account/LoginFrom'
import FindEmailForm from '@/components/account/FindEmailForm'
import FindPasswordForm from '@/components/account/FindPasswordForm'
import ResetPasswordForm from '@/components/account/ResetPasswordForm'

export default {
  name: 'Account',
  components: {
    LoginForm,
    FindEmailForm,
    FindPasswordForm,
    ResetPasswordForm,
  },
  data () {
    return {
      current: '',
      showBack: false,
    }
  },
  watch: {
    '$route' () {
      if (this.$route.name === 'Account') {
        const page = this.$route.params.page
        if (page === 'login' || page === 'find-email' || page === 'find-password' || page === 'reset-password') {
          this.current = page
        } else {
          this.$router.push('/404')
        }
        if (page === 'find-email' || page === 'find-password' || page === 'reset-password') {
          this.showBack = true;
        } else {
          this.showBack = false;
    }
      }
    }
  },
  created() {
    const page = this.$route.params.page
    if (page === 'login' || page === 'find-email' || page === 'find-password' || page === 'reset-password') {
      this.current = page
    } else {
      this.$router.push('/404')
    }
    if (page === 'find-email' || page === 'find-password' || page === 'reset-password') {
      this.showBack = true;
    } else {
      this.showBack = false;
    }
  }
}
</script>
