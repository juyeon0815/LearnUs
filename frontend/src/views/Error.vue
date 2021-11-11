<template>
  <div class="error">
    <div class="content">
      <img 
        :src="decoImg" 
        :alt="this.code + 'error'"
      >
      <div class="code">{{ code }}</div>
      <div class="val">{{ msg[code] }}</div>
      <div class="description" v-html="description"></div>
      <button 
        class="btn dark-orange"
        @click="$router.push('/')"
      >Go to Home</button>
    </div>
  </div>
</template>

<script>
import './error.scss'
export default {
  name: 'Error',
  data () {
    return {
      code: '',
      msg: {
        404: 'Page Not Found',
        500: 'Server Error'
      },
    }
  },
  computed: {
    decoImg () {
      return require(`@/assets/image/logo/err-${this.code}.svg`)
    },
    description () {
      if (this.code === '404') {
        return '요청하신 페이지를 찾을 수 없습니다.'
      } else {
        return '알 수 없는 오류가 발생했습니다.<br/> 잠시 후에 다시 시도해주세요.'
      }
    }
  },
  created () {
    this.code = this.$route.params.code
  }
}
</script>

<style>

</style>