<template>
  <div class="admin create-live">
    <!-- header -->
    <div class="row mb-2">
      <header class="header">
        <div class="chapter">Create<span class="t-orange">:</span>LIVE</div>
      </header>
      <!-- <button class="create-btn">라이브 생성</button> -->
    </div>
    <CreateLiveForm/>
  </div>
</template>

<script>
import CreateLiveForm from '@/components/admin/createLive/CreateLiveForm'

export default {
  name: 'CreateLive',
  components: {
    CreateLiveForm,
  },
  created() {
    this.$store.dispatch('admin/setTrackInfo')
  },
}
</script>