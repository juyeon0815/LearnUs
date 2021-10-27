<template>
  <div class="profile-box">
    <span class="heading">My<span class="colon">:</span>Profile</span>
    <div class="light-box">
      <ProfileHead/>
      <ProfileInfo/>
      <ProfilePassword/>
    </div>
  </div>
</template>

<script>
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
}
</script>
