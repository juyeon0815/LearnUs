<template>
  <div class="profile-head">
    <!-- 사진 관리 -->
    <div class="student-photo">
      <div class="photo-circle"></div>
      <div class="photo-update">
        <span class="text-btn">변경</span>
        <span class="dot-space">⬝</span>
        <span class="text-btn">삭제</span>
        </div>
    </div>
    <!-- 이름, 학번 및 소속 -->
    <div class="student-info">
      <div>
        <span class="student-name">우만승</span>
        <span class="student-id">0545052</span>
      </div>
      <div class="department">
        <span class="grade">SSAFY 5기 교육생</span>
        <span class="region-class">(서울 3반)</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfileHead'
}
</script>
