<template>
  <div class="video-info">
    <button
      class="btn edit-btn"
    >수정</button>
    <div class="info">
      <div class="info-row">
        <span class="key">Title</span>
        <span class="val">Java 기본 및 응용(1)</span>
      </div>
      <div class="info-row">
        <span class="key">DateTime</span>
        <span class="val">2021-10-17 09:00</span>
      </div>
      <div class="info-row">
        <span class="key">Instructor</span>
        <span class="val">최광호</span>
      </div>
      <div class="info-row">
        <span class="key">Target</span>
        <span class="val">6기 코딩(비전공)</span>
      </div>
      <div class="info-row">
        <span class="key">Stream Server</span>
        <span class="val">rtmp://k5a306.p.ssafy.io/live</span>
      </div>
      <div class="info-row">
        <span class="key">Stream Key</span>
        <span class="val">ads45fafsa112</span>
      </div>
    </div>
    <div class="status-box">
      <div class="status">
        <div class="status-circle"></div>
        <span>ONAIR</span>
      </div>
      <div>|</div>
      <div class="viewer">
      <span>356 </span>
      Viewers
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VideoInfo'
}
</script>

<style>

</style>