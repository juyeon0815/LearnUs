<template>
  <div class="create-live-form">
    <!-- 썸네일, 기초정보 인풋 -->
    <div class="row mb-3 flex-start">
      <ThumbnailUploader/>
      <div class="summary-col">
        <div class="input-box mb-2">
          <input 
            type="text"
            class="input"
            placeholder="라이브 제목을 입력하세요">
          <label>Title</label>
        </div>
        <div class="input-box mb-2">
          <input 
            type="text"
            class="input"
            placeholder="날짜를 선택하세요">
          <label>DateTime</label>
        </div>
        <div class="input-box">
          <input 
            type="text"
            class="input"
            placeholder="라이브 진행자를 입력하세요">
          <label>Instructor</label>
        </div>
      </div>
    </div>
    <!-- 소개 인풋 -->
    <div class="row mb-3">
      <div class="input-box">
        <textarea 
          class="input description"
          placeholder="영상에 대한 간략한 소개 글을 적어주세요."
          cols="30" 
          rows="10"></textarea>
        <label>Description</label>
      </div>
    </div>
    <!-- 대상자 설정 -->
    <div class="row">
      <div class="input-box">
        <multiselect
          v-model="trackIds"
          class="input"
          mode="tags"
          placeholder="수강 대상 트랙을 선택하세요."
          :closeOnSelect="false"
          :searchable="true"
          :options=trackOptions>
        </multiselect>
        <label>Target</label>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import ThumbnailUploader from './ThumbnailUploader'
import Multiselect from '@vueform/multiselect'

export default {
  name: 'CreateLiveForm',
  components: {
    ThumbnailUploader,
    Multiselect,
  },
  data() {
    return {
      trackIds: [],
    }
  },
  computed: {
    ...mapGetters('admin',['trackOptions'])
  }
}
</script>