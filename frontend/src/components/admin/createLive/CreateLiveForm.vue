<template>
  <div class="create-live-form">
    <div class="row">
      <ThumbnailUploader/>
      <div class="summary-col">
        
      </div>
    </div>
  </div>
</template>

<script>
import ThumbnailUploader from './ThumbnailUploader'

export default {
  name: 'CreateLiveForm',
  components: {
    ThumbnailUploader,
  }
}
</script>