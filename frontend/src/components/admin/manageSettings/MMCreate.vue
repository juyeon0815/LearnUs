<template>
  <div class="add-mm popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close-add')"  
      ></i>
      <h1>MatterMost<span class="t-orange">:</span></h1>
      <MMForm/>
      <button 
        :class="[mmData ? 'dark-orange' : 'disabled', 'submit btn']"
        @click="addMM"
      >MatterMost 연결</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import adminApi from '@/api/admin'
import MMForm from './MMForm.vue'

export default {
  name: 'MMCreate',
  components: {
    MMForm
  },
  methods: {
    async addMM () {
      let data = this.mmData
      if (data.ordinalNo === 0) {
        const tracksObj = this.tracks.filter(track => {
          return data.trackList.includes(track.trackId)
        })
        data.trackList = tracksObj
      } else {
        data.trackList = []
      }
      const response = await adminApi.addMatterMost(data)
      if (response.status === 200) {
        this.$store.dispatch('admin/getMMAll')
        this.$emit('close-add')
      }
    }
  },
  computed: {
    ...mapState('admin', ['mmData', 'tracks'])
  }
}
</script>