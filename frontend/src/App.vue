<template>
  <div id="app" class="dark">
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
    needSideBar () {
      return true
    },
    needTopBar () {
      return true
    }
  }
}
</script>