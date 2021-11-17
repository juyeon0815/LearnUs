<template>
  <div class="update-popup">
    <div class="popup-box">
      <Loading v-if="isLoading"/>
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <div :class="[result ? 'blue' : 'yellow', 'alert']" v-if="msg">{{ msg }}</div>
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
import Loading from '@/components/common/Loading'
export default {
  name: 'StudentUpdate',
  components: {
    Loading
  },
  data () {
    return {
      updateFile: null,
      updateFileName: null,
      createFile: null,
      createFileName: null,
      msg: false,
      result: 0,
      isLoading: false,
    }
  },
  methods: {
    onUpload(event) {
      const file = event.target.files ? event.target.files[0] : null
      const ext = file ? file.name.split('.').pop().toLowerCase() : null
      if (ext === 'xlsx' || ext === 'xls') {
        if (event.target.id === 'update-user') {
          this.updateFile = file
          this.updateFileName = file.name
        } else if (event.target.id === 'create-user') {
          this.createFile = file
          this.createFileName = file.name
        }
      } else {
        if (event.target.id === 'update-user') {
          this.updateFile = null
          this.updateFileName = null
        } else if (event.target.id === 'create-user') {
          this.createFile = null
          this.createFileName = null
        }
        event.target.value = ''
        this.result = 0
        this.msg = '엑셀 파일만 등록이 가능합니다.'
        setTimeout(() => {
          this.msg = false
        }, 2000)
      }
    },
    async onRegisterInfo() {
      this.isLoading = true
      const formData = new FormData()
      formData.append('excelFile', this.createFile)
      try {
        const response = await adminApi.registerStudents(formData)
        if (response.status === 200) {
          this.isLoading = false
          this.createFile = null
          this.createFileName = null
          this.result = 1
          this.msg = '신규 교육생 등록이 완료되었습니다.'
          setTimeout(() => {
            this.msg = false
          }, 2000)
          this.$store.dispatch('admin/getStudents')
        }
      } catch (err) {
        this.isLoading = false
        this.result = 0
        this.msg = '신규 교육생 등록에 실패했습니다.'
        setTimeout(() => {
          this.msg = false
        }, 2000)
      }
    },
    async onUpdateInfo () {
      this.isLoading = true
      const formData = new FormData()
      formData.append('excelFile', this.updateFile)
      try {
        const response = await adminApi.editStudents(formData)
        if (response.status === 200) {
          this.isLoading = false
          this.updateFile = null
          this.updateFileName = null
          this.result = 1
          this.msg = '교육생 정보 수정이 완료되었습니다.'
          setTimeout(() => {
            this.msg = false
          }, 2000)
          this.$store.dispatch('admin/getStudents')
        }
      } catch (err) {
        this.isLoading = false
        this.result = 0
        this.msg = '교육생 정보 수정에 실패했습니다.'
        setTimeout(() => {
          this.msg = false
        }, 2000)
      }
    }
  }
}
</script>

<style>

</style>