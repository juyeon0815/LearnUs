<template>
  <div class="profile-box">
    <p>ProfileBox</p>
    <ProfileHead/>
    <ProfileInfo/>
    <ProfilePassword/>
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
