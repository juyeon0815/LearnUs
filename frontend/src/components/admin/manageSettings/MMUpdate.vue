<template>
  <div class="add-mm popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close-edit')"  
      ></i>
      <h1>MatterMost<span class="t-orange">:</span></h1>
      <MMForm/>
      <div class="btn-row">
        <button 
          class="orange submit btn"
          @click="deleteMM"
        >MM 채널 삭제</button>
        <button 
          :class="[mmData ? 'dark-orange' : 'disabled', 'submit btn']"
          @click="editMM"
        >정보 수정</button>
      </div>
    </div>
  </div>
</template>

<script>
import adminApi from '@/api/admin'
import MMForm from './MMForm.vue'
import { mapState } from 'vuex'

export default {
  name: 'MMUpdate',
  components: {
    MMForm
  },
  methods: {
    async editMM () {
      let data = this.mmData
      if (data.ordinalNo === 0) {
        const tracksObj = this.tracks.filter(track => {
          return data.trackList.includes(track.trackId)
        })
        data.trackList = tracksObj
      } else {
        data.trackList = []
      }
      data.mattermostId = this.targetChannel.mattermostId
      const response = await adminApi.editMatterMost(data)
      if (response.status === 200) {
        this.$store.dispatch('admin/getMMAll')
        this.$emit('close-edit')
      }
    },
    async deleteMM () {
      const response = await adminApi.deleteMatterMost(this.targetChannel.mattermostId)
      if (response.status === 200) {
        this.$store.dispatch('admin/getMMAll')
        this.$emit('close-edit')
      }
    }
  },
  computed: {
    ...mapState('admin', ['targetChannel', 'mmData', 'tracks'])
  }
}
</script>