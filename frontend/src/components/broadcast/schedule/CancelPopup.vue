<template>
  <div class="popup">
    <div class="popup-box live-schedule">
      <h1 class="mb-2">Delete Broadcast</h1>
      <span>"{{ broadcast.title }}" 방송을</span>
      <span>삭제하시겠습니까?</span>
      <div class="popup-btn-box">
        <button class="btn-popup" @click="$emit('confirmDelete')">삭제</button>
        <button class="btn-popup" @click="$emit('hideModal')">취소</button>
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