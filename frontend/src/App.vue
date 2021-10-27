<template>
  <div id="app" :class="[ !isAccount ? 'space' : '', 'dark' ]">
    <SideBar
      v-if="needSideBar"
    />
    <TopBar
      v-if="needTopBar"
    />
    <router-view
      :class="{ 'space-left': needSideBar, 'space-top': needTopBar }"
    />
  </div>
</template>

<script>
import "@/assets/style/index.scss";
import SideBar from '@/components/common/SideBar'
import TopBar from '@/components/common/TopBar'

export default {
  name: 'App',
  components: {
    SideBar,
    TopBar,
  },
  computed: {
    isAccount () {
      return this.$route.name === "Account"
    },
    needSideBar () {
      if (this.$route.name === "Account") {
        return false
      }
      return true
    },
    needTopBar () {
      if (this.$route.name === "Account") {
        return false
      }
      return true
    }
  }
}
</script>