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
import AWS from "aws-sdk"
export default {
  name: 'ThumbnailUploader',
  data() {
    return {
      thumbnailImage: null,
      thumbnailPath: ''
    }
  },
  methods: {
    getImage() {
      const inputImage = this.$refs.inputImage.files[0]
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

      AWS.config.update({
        region,
        accessKeyId,
        secretAccessKey
      })

      const s3 = new AWS.S3({
        apiVersion:'2012-10-17',
        params: {Bucket: albumBucketName,}
      })

      const file = this.thumbnailImage
      const fileName = 'tempThumbnail.jpg'
      const albumPhotosKey = encodeURIComponent('profiles') + "/"
      const photoKey = albumPhotosKey + fileName

      s3.upload({
        Key: photoKey,
        Body: file,
        ACL: 'public-read'
      }).promise()
        .then((res) => {
          this.thumbnailPath = res.Location
          // const newProfileUrl = res.Location
          // await this.$store.dispatch('account/onChangeUserPhoto', newProfileUrl)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>