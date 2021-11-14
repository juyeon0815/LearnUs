<template>
  <div class="broadcast-info">
    <UpdateModal v-if="onUpdateModal" @hideModal="onUpdateModal = false"/>
    <DeleteConfirm v-if="onDeleteConfirm" @close="onDeleteConfirm = false"/>
    <div class="default">
      <div class="video">
        <div class="player"></div>
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
          <button
            class="btn black"
          >
            <div class="btn-content">
              <i class="fi fi-rr-resources"></i>
              <span>영상 소스 교체</span>
            </div>
          </button>
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
// import VideoPlayer from '@/components/replay/video/ReplayVideoPlayer'
import VideoInfo from './BroadcastVideoInfo.vue'
import DeleteConfirm from './BroadcastDeleteConfirm.vue'
import Award from './BroadcastAward.vue'
import UpdateModal from '@/components/onAir/studio/VideoInfoUpdateModal'
export default {
  name: 'BroadcastInfo',
  data () {
    return {
      onUpdateModal: false,
      onDeleteConfirm: false
    }
  },
  components: {
    VideoInfo,
    Award,
    UpdateModal,
    DeleteConfirm
    // VideoPlayer
  }
}
</script>

<style>

</style>