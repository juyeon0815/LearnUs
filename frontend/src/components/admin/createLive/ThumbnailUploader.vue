<template>
  <div class="thumbnail-uploader">
    <!-- 선택한 이미지 표시 -->
    <img 
      v-if="thumbnailImage !== null" 
      :src="thumbnailImage" 
      alt="thumbnail image"
      class="image-preview">
    <!-- 파일 input -->
    <input ref="inputImage" type="file" class="hide-input" @change="getImage">
    <label>Thumbnail</label>
    <div class="empty-box">
      <i class="fi fi-rr-add plus-icon"></i>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ThumbnailUploader',
  data() {
    return {
      thumbnailImage: null,
    }
  },
  methods: {
    getImage() {
      const inputImage = this.$refs.inputImage.files[0]
      const fr = new FileReader()
      fr.onload = () => {
        this.thumbnailImage = fr.result
      }
      fr.readAsDataURL(inputImage)
    },
  }
}
</script>