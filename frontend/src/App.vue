<template>
  <div :class="[ noSpace ? 'space' : '', isError ? 'light' : 'dark', 'app' ]">
    <div v-if="tokenExpired" class="logout-alert">
      로그인 유효 시간이 만료되어 자동 로그아웃되었습니다.
    </div>
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
import { mapState } from 'vuex'
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
    ...mapState('account', ['tokenExpired']),
    noSpace () {
      if (
        this.$route.name === "Account" ||
        this.$route.name === "Error"
      ) {
        return false
      }
      return true
    },
    isAccount () {
      return this.$route.name === "Account"
    },
    isError () {
      return this.$route.name === "Error"
    },
    needSideBar () {
      if (this.$route.name === "Account" ||
          this.$route.name === "OnAir" ||
          this.$route.name === "OnAirStudio" ||
          this.$route.name === "ReplayDetail" ||
          this.$route.name === "Error"
      ) {
        return false
      }
      return true
    },
    needTopBar () {
      if (this.$route.name === "Account" ||
          this.$route.name === "Error"
      ) {
        return false
      }
      return true
    },
    needTopLogo () {
      if (this.$route.name === "OnAir" ||
          this.$route.name === "OnAirStudio" ||
          this.$route.name === "ReplayDetail"
      ) {
        return true
      }
      return false
    }
  },
}
</script>