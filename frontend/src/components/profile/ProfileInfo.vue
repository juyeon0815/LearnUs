<template>
  <div class="profile-info">
    <div class="title-button-box">
      <div class="title">
        <span>My Info</span>
      </div>
      <button class="change-info-btn">
        정보수정
      </button>
    </div>
    <div class="info-inputs">
      <!-- 닉네임 input -->
      <div class="info-input-box">
        <input 
          type="text"
          class="info-input"
        />
        <label>Nickname</label>
      </div>
      <!-- 전화번호 input -->
      <div class="info-input-box">
        <input 
          type="text"
          class="info-input"
        />
        <label>Phone Number</label>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileInfo'
}
</script>
