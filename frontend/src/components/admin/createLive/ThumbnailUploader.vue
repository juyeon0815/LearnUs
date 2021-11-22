<template>
  <div class="thumbnail-uploader">
    <!-- 선택한 이미지 표시 -->
    <img 
      v-if="thumbnailImage !== null" 
      :src="thumbnailImage" 
      alt="thumbnail image"
      class="image-preview">
    <!-- 파일 input -->
    <input 
      ref="inputImage" 
      type="file" 
      class="hide-input"
      accept="image/*"
      @change="getImage">
    <label>Thumbnail</label>
    <div class="empty-box">
      <i class="fi fi-rr-add plus-icon" v-if="!thumbnailImage"></i>
    </div>
    <!-- input 삭제 -->
    <button class="btn-delete" v-if="thumbnailImage" @click="deleteThumbnail">
      삭제
    </button>
  </div>
</template>

<script>
import AWS from "aws-sdk"
export default {
  name: 'ThumbnailUploader',
  data() {
    return {
      thumbnailImage: null,
      thumbnailPath: '',
    }
  },
  methods: {
    getImage() {
      const inputImage = this.$refs.inputImage.files[0]
      /* 파일 확장자 실패 분기 */
      const extension = inputImage.name.split(".")[1]
      if (!extension || (extension !== 'jpg' && extension !== 'jpeg' && extension !== 'png')) {
        const alertInfo = {
          type: 'fail',
          message: 'jpg · jpeg · png 파일만 등록할 수 있습니다.'
        }
        if (this.$route.name === 'OnAirStudio' || this.$route.name === 'ManageVideo') {
          this.thumbnailImage = this.originalThumbnailPath
        } else {
          this.thumbnailImage = null
        }
        this.$refs.inputImage.value = ''
        this.$emit('wrongExtension',alertInfo)
        return
      }
      const reader = new FileReader()
      reader.onload = () => {
        this.thumbnailImage = reader.result
      }
      reader.readAsDataURL(inputImage)
    },
    async saveThumbnail() {
      const albumBucketName = process.env.VUE_APP_S3_BUCKET
      const region = "ap-northeast-2"
      const accessKeyId = process.env.VUE_APP_S3_ACCESS_KEY_ID
      const secretAccessKey = process.env.VUE_APP_S3_SECRET_ACCESS_KEY
      if (!this.thumbnailImage) {
        const defaultThumbnailPath = 'https://' + albumBucketName + '.s3.ap-northeast-2.amazonaws.com/thumbnails/default.jpg'
        return defaultThumbnailPath
      } else if ((this.$route.name === "OnAirStudio" || this.$route.name === 'ManageVideo') && this.thumbnailImage === this.originalThumbnailPath) {
        return this.originalThumbnailPath
      }

      AWS.config.update({
        region,
        accessKeyId,
        secretAccessKey
      })

      const s3 = new AWS.S3({
        apiVersion:'2012-10-17',
        params: {Bucket: albumBucketName,}
      })

      const file = this.$refs.inputImage.files[0]
      const fileName = file.name
      const slice = fileName.split(".")
      const albumPhotosKey = encodeURIComponent('thumbnails') + "/"
      const photoKey = albumPhotosKey + slice[0] + "_" + new Date().getTime() + "." + slice[1]

      await s3.upload({
        Key: photoKey,
        Body: file,
        ACL: 'public-read'
      }).promise()
        .then((res) => {
          this.thumbnailPath = res.Location
        })
      return this.thumbnailPath
    },
    deleteThumbnail() {
      this.thumbnailImage = null
      this.$refs.inputImage.value = ''
    },
  },
  computed: {
    originalThumbnailPath() {
      return this.$store.state.broadcast.broadcastDetail.thumbnailUrl
    }
  },
  created() {
    if (this.$route.name === 'OnAirStudio' || this.$route.name === 'ManageVideo') {
      this.thumbnailImage = this.originalThumbnailPath
    }
  }
}
</script>