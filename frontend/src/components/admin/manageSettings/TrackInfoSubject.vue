<template>
  <div 
    style="width: 100%;"
    :id="'subject' + subject.trackSubjectId"
  >
    <div v-if="isEditMode" class="edit-box">
      <input 
        type="text" 
        :value="data.subjectName"
        @input="insertSubject"
      >
      <span>
        <button 
          :class="[data.subjectName.trim().length ? '' : 'disabled']" 
          @click="editSubject"
        >수정</button>
        <button @click="offEditSubject">취소</button>
      </span>
    </div>
    <div v-else class="info">
      <span 
        class="name"
        @click="$emit('selected', subject.trackSubjectId, subject.subjectName)"
      >{{ subject.subjectName }}</span>
      <span>
        <button @click="onEditSubject">수정</button>
        <button @click="deleteSubject">삭제</button>
      </span>
    </div>
  </div>
</template>

<script>
import adminApi from '@/api/admin'

export default {
  name: 'TrackInfoSubject',
  props: {
    subject: Object
  },
  data () {
    return {
      isEditMode: false,
      data: {
        ordinalNo: null,
        subjectName: '',
        trackSubjectId: null
      },
    }
  },
  methods: {
    onEditSubject() {
      this.isEditMode = true
      this.data.ordinalNo = this.subject.trackSetting.ordinalNo
      this.data.subjectName = this.subject.subjectName
      this.data.trackSubjectId = this.subject.trackSubjectId
    },
    offEditSubject() {
      this.isEditMode = false
      this.data.ordinalNo = null
      this.data.subjectName = ''
      this.data.trackSubjectId = null
    },
    insertSubject(event) {
      this.data.subjectName = event.target.value
    },
    async editSubject() {
      const response = await adminApi.editSubject(this.data)
      if (response.status === 200) {
        this.offEditSubject()
        this.$store.dispatch('admin/getSubjectAll')
      }
    },
    async deleteSubject() {
      const response = await adminApi.deleteSubject(this.subject.trackSubjectId)
      if (response.status === 200) {
        this.$store.dispatch('admin/getSubjectAll')
      }
    },
  }
}
</script>