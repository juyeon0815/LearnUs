<template>
  <div class="add-mm popup">
    <div class="popup-box">
      <i 
        class="exit-btn fi fi-rr-cross-small"
        @click="$emit('close')"  
      ></i>
      <h1>MatterMost<span class="t-orange">:</span></h1>
      <div class="form">
        <div class="half">
          <div class="input-box">
            <input type="text">
            <label>채널명</label>
          </div>
          <div class="input-box">
            <input type="text">
            <label>URL Path</label>
          </div>
        </div>
        <div class="input-box lg">
          <input type="text">
          <label>WebHook</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MMCreate'
}
</script>

<style>

</style>