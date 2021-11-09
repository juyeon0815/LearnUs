<template>
  <div class="profile">
    <ProfileBox/>
  </div>
</template>

<script>
import './profile.scss'
import ProfileBox from '@/components/account/profile/ProfileBox'

export default {
  name: 'Profile',
  components: {
    ProfileBox,
  }
}
</script>