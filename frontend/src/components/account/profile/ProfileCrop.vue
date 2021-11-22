<template>
  <div class="popup">
    <div class="popup-box">
      <h1>Change Photo</h1>
      <i class="fi fi-rr-cross exit-btn" @click="$emit('hideCropper')"></i>
      <!-- input -->
      <div v-if="jpegImage === null && croppedImage === null" class="input-box">
        <img v-if="profileUrl !== ''" :src="profileUrl" class="original-img">
        <input 
          class="file-input"
          id="file-input"
          ref="inputImage" 
          type="file" 
          accept="image/*"
          autofocus="true"
          @change="getImage">
        <label for="file-input" class="cropper-btn">사진 업로드</label>
      </div>
      <!-- cropper -->
      <div v-if="jpegImage && croppedImage === null" class="cropper-box">
        <Cropper
          class="cropper"
          ref="cropper"
          :src="jpegImage"
          :stencil-props="{
            aspectRatio: 1/1
          }"/>
        <button @click="cropImage" class="cropper-btn">사진 자르기</button>
      </div>
      <!-- cropped Image -->
      <div v-if="croppedImage" class="cropped-image-box">
        <img class="cropped-image" ref="croppedImage" alt="">
        <button class="cropper-btn" @click="submitProfilePhoto">사진 변경하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { Cropper } from 'vue-advanced-cropper'
import 'vue-advanced-cropper/dist/style.css';
import AWS from "aws-sdk"

export default {
  name: 'ProfileCrop',
  components: {
    Cropper
  },
  data() {
    return {
      jpegImage: null,
      croppedImage: null,
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
        this.$emit('wrongExtension',alertInfo)
        return
      }
      const fr = new FileReader()
      fr.onload = () => {
        this.jpegImage = fr.result
      }
      fr.readAsDataURL(inputImage)
    },
    cropImage() {
      const { canvas } = this.$refs.cropper.getResult()
      if (canvas) {
        canvas.toBlob(blob => {
          this.croppedImage = blob
          const fr = new FileReader()
          fr.onload = () => {
          this.$refs.croppedImage.src = fr.result
          }
          fr.readAsDataURL(blob)
        }, 'image/jpeg')
      }
    },
    async submitProfilePhoto() {
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

      const file = this.croppedImage
      const fileName = this.$store.state.account.userInfo.userId + '.jpg'
      const albumPhotosKey = encodeURIComponent('profiles') + "/"
      const photoKey = albumPhotosKey + fileName

      s3.upload({
        Key: photoKey,
        Body: file,
        ACL: 'public-read'
      }).promise()
        .then(async (res) => {
          const newProfileUrl = res.Location
          await this.$store.dispatch('account/onChangeUserPhoto', newProfileUrl)
          this.$emit('hideCropper')
        })
    }
  },
  computed: {
    profileUrl() {
      return this.$store.state.account.userInfo.profileUrl
    }
  },
}
</script>