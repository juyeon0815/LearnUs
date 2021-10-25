<template>
  <div class="account">
    <LoginForm/>
  </div>
</template>

<script>
import LoginForm from '@/components/account/LoginFrom'

export default {
  name: 'Account',
  components: {
    LoginForm,
  }
}
</script>
