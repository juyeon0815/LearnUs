<template>
  <div class="index">
    <OnAirList/>
  </div>
</template>

<script>
import './index.scss'
import OnAirList from '@/components/index/OnAirList'
export default {
  name: 'Index',
  components: {
    OnAirList
  }
}
</script>
