<template>
  <div class="popup spinner">
    <!-- loading -->
    <div class="spinner-box" v-if="spinnerStatus==='loading'">
      <div class="lds-spinner">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
    <!-- success -->
    <div class="spinner-box" v-if="spinnerStatus==='success'">
      <svg class="spinner-svg" version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 130.2 130.2">
        <circle class="path circle" fill="none" stroke="#73AF55" stroke-width="6" stroke-miterlimit="10" cx="65.1" cy="65.1" r="62.1"/>
        <polyline class="path check" fill="none" stroke="#73AF55" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" points="100.2,40.2 51.5,88.8 29.8,67.5 "/>
      </svg>
      <span class="spinner-span spinner-success">영상 소스가</span>
      <span class="spinner-span spinner-success">교체되었습니다.</span>
    </div>
    <!-- fail -->
    <div class="spinner-box" v-if="spinnerStatus==='fail'">
      <svg class="spinner-svg" version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 130.2 130.2">
        <circle class="path circle" fill="none" stroke="#D06079" stroke-width="6" stroke-miterlimit="10" cx="65.1" cy="65.1" r="62.1"/>
        <line class="path line" fill="none" stroke="#D06079" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" x1="34.4" y1="37.9" x2="95.8" y2="92.3"/>
        <line class="path line" fill="none" stroke="#D06079" stroke-width="6" stroke-linecap="round" stroke-miterlimit="10" x1="95.8" y1="38" x2="34.4" y2="92.2"/>
      </svg>
      <span class="spinner-span spinner-error">{{ failMessage[0] }}</span>
      <span class="spinner-span spinner-error">{{ failMessage[1] }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoadingSpinner',
  props: {
    spinnerStatus: String,
    failMessage: Array,
  },
  watch: {
    spinnerStatus() {
      if (this.spinnerStatus === 'success' || this.spinnerStatus === 'fail') {
        setTimeout(() => {
          this.$emit('hideSpinner')
        }, 1500)
      }
    }
  }
}
</script>