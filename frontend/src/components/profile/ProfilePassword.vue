<template>
  <div class="profile-info margin-end">
    <div class="title-button-box">
      <div class="title">
        <span>Password</span>
      </div>
      <button class="change-info-btn">
        비밀번호 변경
      </button>
    </div>
    <div class="info-inputs">
      <!-- 기존 비밀번호 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input"
          placeholder="기존 비밀번호를 입력하세요"
        />
        <label>Old Password</label>
      </div>
    </div>
    <div class="info-inputs">
      <!-- 새 비밀번호 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input margin-between"
          placeholder="새로운 비밀번호를 입력하세요"
        />
        <label>New Password</label>
      </div>
      <!-- 새 비밀번호 확인 input -->
      <div class="info-input-box">
        <input 
          type="password"
          class="info-input"
          placeholder="비밀번호를 한 번 더 입력하세요"
        />
        <label>New Password Confirm</label>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfilePassword'
}
</script>
