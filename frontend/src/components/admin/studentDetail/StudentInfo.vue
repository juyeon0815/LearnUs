<template>
  <div class="student-info">
    <div>
      <img :src="studentInfo.profileUrl" alt="profle image" class="profile-photo">
    </div>
    <div class="info-box">
      <div>
        <input type="text">
        <input type="text">
      </div>
      <div>
        <input type="text">
        <input type="text">
        <input type="text">
        <input type="text">
      </div>
      <div>
        <button>정보 수정</button>
        <button>퇴소</button>
      </div>
    </div>
  </div>  
</template>

<script>
export default {
  name: 'StudentInfo',
  props: {
    studentInfo: Object
  }
}
</script>