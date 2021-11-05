<template>
  <div class="profile-box">
    <span class="heading">My<span class="colon">:</span>Profile</span>
    <div class="light-box">
      <ProfileHead @alert="onAlert"/>
      <ProfileInfo @alert="onAlert"/>
      <ProfilePassword @alert="onAlert"/>
      <div :class="[alertInfo.type === 'fail' ? 'yellow' : 'blue' ,'alert']" v-if="showAlert">{{ alertInfo.message }}</div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import ProfileHead from './ProfileHead'
import ProfileInfo from './ProfileInfo'
import ProfilePassword from './ProfilePassword'

export default {
  name: 'ProfileBox',
  components: {
    ProfileHead,
    ProfileInfo,
    ProfilePassword,
  },
  data() {
    return {
      showAlert: false,
      alertInfo: {
        type: null,
        message: '',
      },
    }
  },
  methods: {
    ...mapMutations('account', ['SET_HTTP_STATUS']),
    onAlert(alertInfo) {
      this.alertInfo = alertInfo
      this.showAlert = true
      setTimeout(() => {
        this.showAlert = false
        this.alertInfo.type = null
        this.alertInfo.message = ''
        this.SET_HTTP_STATUS(null)
      }, 2000)
    }
  },
  created() {
    this.SET_HTTP_STATUS(null)
  }
}
</script>
