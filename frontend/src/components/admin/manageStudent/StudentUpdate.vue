<template>
  <div class="update-popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>UPDATE<span class="t-orange">:</span>Students</h1>
      <div class="subtitle">
        <h3>교육생 정보 수정</h3>
        <button 
          class="btn"
          @click="onUpdateInfo"
        >수정하기</button>
      </div>
      <div class="file-box">
        <span v-if="updateFileName">{{ updateFileName }}</span>
        <label for="update-user">파일 등록</label>
        <input 
          id="update-user"
          type="file"
          accept=".xlsx, .xls"
          @change="onUpload"
          style="display: none;"
        >
      </div>
      <div class="subtitle">
        <h3>신규 교육생 등록</h3>
        <button 
          class="btn"
          @click="onRegisterInfo"
        >등록하기</button>
      </div>
      <div class="file-box">
        <span v-if="createFileName">{{ createFileName }}</span>
        <label for="create-user">파일 등록</label>
        <input 
          id="create-user"
          type="file"
          accept=".xlsx, .xls"
          @change="onUpload"
          style="display: none;"
        >
      </div>
    </div>
  </div>
</template>

<script>
import adminApi from '@/api/admin'
export default {
  name: 'StudentUpdate',
  data () {
    return {
      updateFile: null,
      updateFileName: null,
      createFile: null,
      createFileName: null,
    }
  },
  methods: {
    onUpload(event) {
      const file = event.target.files ? event.target.files[0] : null;
      if (event.target.id === 'update-user') {
        this.updateFile = file
        this.updateFileName = file.name
      } else if (event.target.id === 'create-user') {
        this.createFile = file
        this.createFileName = file.name
      }
    },
    async onRegisterInfo() {
      const formData = new FormData()
      formData.append('excelFile', this.createFile)
      try {
        const response = await adminApi.registerStudents(formData)
        console.log(response)
      } catch (err) {
        console.log(err.response)
      }
    }
  }
}
</script>

<style>

</style>