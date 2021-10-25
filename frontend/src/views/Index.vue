<template>
  <div class="index">
    <SideBar/>
    <TopBar/>
  </div>
</template>

<script>
import SideBar from '@/components/common/SideBar'
import TopBar from '@/components/common/TopBar'

export default {
  name: 'Index',
  components: {
    SideBar,
    TopBar,
  }
}
</script>
