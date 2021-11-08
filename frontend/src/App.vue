<template>
  <div id="app" :class="[ !isAccount ? 'space' : '', 'dark' ]">
    <SideBar
      v-if="needSideBar"
    />
    <TopBar
      v-if="needTopBar"
    />
    <TopLogo
      v-if="needTopLogo"
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
import TopLogo from '@/components/common/TopLogo'

export default {
  name: 'App',
  components: {
    SideBar,
    TopBar,
    TopLogo
  },
  computed: {
    isAccount () {
      return this.$route.name === "Account"
    },
    needSideBar () {
      if (this.$route.name === "Account" ||
          this.$route.name === "OnAir" ||
          this.$route.name === "OnAirStudio"
      ) {
        return false
      }
      return true
    },
    needTopBar () {
      if (this.$route.name === "Account") {
        return false
      }
      return true
    },
    needTopLogo () {
      if (this.$route.name === "OnAir" ||
          this.$route.name === "OnAirStudio"
      ) {
        return true
      }
      return false
    }
  }
}
</script>