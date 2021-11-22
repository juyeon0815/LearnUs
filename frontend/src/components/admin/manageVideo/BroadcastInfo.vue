<template>
  <div class="broadcast-info">
    <UploadSpinner 
      v-show="onUploadSpinner" 
      :spinnerStatus="spinnerStatus"
      :failMessage="failMessage"
      @hideSpinner="onHideSpinner"/>
    <UpdateModal v-if="onUpdateModal" @hideModal="onUpdateModal = false"/>
    <DeleteConfirm v-if="onDeleteConfirm" @close="onDeleteConfirm = false"/>
    <div class="default">
      <div class="video">
        <VideoPlayer/>
        <div class="btns">
          <button
            class="btn black"
            @click="onUpdateModal = true"
          >
            <div class="btn-content">
              <i class="fi fi-rr-pencil"></i>
              <span>영상 정보 수정</span>
            </div>
          </button>
          <input 
            id="video-input"
            type="file"
            accept="video/mp4"
            style="display:none;"
            ref="inputVideo"
            @change="onUploadVideo"
          >
          <label 
            for="video-input"
            class="btn black"
          >
            <div class="btn-content">
              <i class="fi fi-rr-resources"></i>
              <span>영상 소스 교체</span>
            </div>
          </label>
          <button
            class="btn black"
            @click="onDeleteConfirm = true"
          >
            <div class="btn-content">
              <i class="fi fi-rr-trash"></i>
              <span>교육 영상 삭제</span>
            </div>
          </button>
        </div>
      </div>
      <VideoInfo/>
    </div>
    <Award/>
  </div>
</template>

<script>
import AWS from "aws-sdk"

import VideoPlayer from './BroadcastVideoPlayer'
import VideoInfo from './BroadcastVideoInfo.vue'
import DeleteConfirm from './BroadcastDeleteConfirm.vue'
import Award from './BroadcastAward.vue'
import UploadSpinner from './UploadSpinner.vue'
import UpdateModal from '@/components/onAir/studio/VideoInfoUpdateModal'
import { mapActions, mapState } from 'vuex'


export default {
  name: 'BroadcastInfo',
  data () {
    return {
      onUpdateModal: false,
      onDeleteConfirm: false,
      onUploadSpinner: false,
      spinnerStatus: 'loading',
      failMessage: ['',''],
    }
  },
  components: {
    VideoInfo,
    Award,
    UpdateModal,
    DeleteConfirm,
    UploadSpinner,
    VideoPlayer
  },
  methods: {
    ...mapActions('broadcast', ['updateReplayInfo']),
    async onUploadVideo () {
      // 빈 파일 분기
      if (!this.$refs.inputVideo.files.length) {
        return
      }
      // 스피너 on
      this.spinnerStatus = 'loading'
      this.onUploadSpinner = true
      // 영상 소스 교체
      const albumBucketName = process.env.VUE_APP_REPLAY_S3_BUCKET
      const region = "ap-northeast-2"
      const accessKeyId = process.env.VUE_APP_REPLAY_S3_ACCESS_KEY_ID
      const secretAccessKey = process.env.VUE_APP_REPLAY_S3_SECRET_ACCESS_KEY

      AWS.config.update({
        region,
        accessKeyId,
        secretAccessKey
      })

      const s3 = new AWS.S3({
        apiVersion:'2012-10-17',
        params: {Bucket: albumBucketName,}
      })

      const file = this.$refs.inputVideo.files[0]
      const fileName = file.name
      const slice = fileName.split(".")
      /* 파일 확장자 실패 분기 */
      if (!slice[1] || slice[1] !== 'mp4') {
        this.spinnerStatus = 'fail'
        this.failMessage= ['.mp4 파일을', '등록해주세요.']
        return
      }
      const albumVideosKey = encodeURIComponent('replay') + "/"
      const videoKey = albumVideosKey + this.replayDetail.broadcastReplayId + "." + slice[1]

      s3.upload({
        Key: videoKey,
        Body: file,
        ACL: 'public-read'
      }).promise()
        .then(async () => {
          const replayInfo = {
            broadcastReplayId: this.replayDetail.broadcastReplayId,
            openYn: this.replayDetail.openYn,
            replayUrl: `https://d31f0osw72yf0h.cloudfront.net/replay/${this.replayDetail.broadcastReplayId}.${slice[1]}`
          }
          const result = await this.updateReplayInfo(replayInfo)
          if (result.status === 200) {
            this.spinnerStatus = 'success'
          }
        })
        .catch(() => {
          this.spinnerStatus = 'fail'
          this.failMessage = ['파일 저장에', '실패했습니다.']
        })
    },
    onHideSpinner() {
      this.onUploadSpinner = false
      this.failMessage = ['', '']
      this.spinnerStatus = 'loading'
    }
  },
  computed: {
    ...mapState('broadcast', ['replayDetail'])
  }
}
</script>

<style>

</style>