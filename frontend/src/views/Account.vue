<template>
  <div class="account">
    <div class="user-form">
      <LoginForm/>
    </div>
  </div>
</template>

<script>
import './account.scss'
import LoginForm from '@/components/account/LoginFrom'

export default {
  name: 'Account',
  components: {
    LoginForm,
  }
}
</script>
