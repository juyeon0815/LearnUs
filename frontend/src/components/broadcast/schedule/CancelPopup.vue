<template>
  <div class="popup">
    <div class="popup-box live-schedule">
      <span>"{{ broadcast.title }}"</span>
      <span>방송을 삭제하시겠습니까?</span>
      <div class="popup-btn-box">
        <button class="btn orange btn-popup" @click="$emit('confirmDelete')">삭제</button>
        <button class="btn grey btn-popup" @click="$emit('hideModal')">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CancelPopup',
  props: {
    broadcast: Object
  }
}
</script>