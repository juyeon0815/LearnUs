<template>
  <div class="account">
    <Decoration/>
    <div class="user-form">
      <i class="fi fi-rr-arrow-small-left direct-back"
      @click="$router.push({ name: 'Account', params: {page: 'login'}})"
      v-if='showBack'></i>
      <LoginForm v-if="current === 'login'" @alert="onAlert"/>
      <FindEmailForm v-if="current === 'find-email'" @alert="onAlert"/>
      <FindPasswordForm v-if="current === 'find-password'" @alert="onAlert"/>
      <ResetPasswordForm v-if="current === 'reset-password'" @alert="onAlert"/>
      <div :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert']" v-if="showAlert">{{ alertInfo.message }}</div>
    </div>
  </div>
</template>

<script>
import './login.scss'
import Decoration from '@/components/account/login/Decoration'
import LoginForm from '@/components/account/login/LoginForm'
import FindEmailForm from '@/components/account/login/FindEmailForm'
import FindPasswordForm from '@/components/account/login/FindPasswordForm'
import ResetPasswordForm from '@/components/account/login/ResetPasswordForm'

export default {
  name: 'Login',
  components: {
    Decoration,
    LoginForm,
    FindEmailForm,
    FindPasswordForm,
    ResetPasswordForm,
  },
  data () {
    return {
      current: '',
      showBack: false,
      showAlert: false,
      alertInfo: {
        type: null,
        message: '',
      }
    }
  },
  methods: {
    onAlert(alertInfo) {
      this.alertInfo = alertInfo
      this.showAlert = true
      setTimeout(() => {
        this.showAlert = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
      }, 2000)
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
  },
}
</script>
