<template>
  <div class="update-popup">
    <div class="popup-box">
      <h1>UPDATE<span class="t-orange">:</span>Students</h1>
      <h3>교육생 정보 수정</h3>
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
      <h3>신규 교육생 등록</h3>
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
    }
  }
}
</script>

<style>

</style>