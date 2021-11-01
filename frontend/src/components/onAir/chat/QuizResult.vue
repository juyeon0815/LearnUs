<template>
  <div class="quiz-result">
    <div class="title-box">
      <div class="deco">
        <img src="@/assets/image/deco/trophy.png" alt="">
      </div>
      <span class="title">정답 자리</span>
    </div>
    <div class="winners">
      <div 
        class="rank-box"
        v-for="idx in 3"
        :key="idx"  
      >
        <div class="rank">{{ idx }}</div>
        <span>김싸피 [서울_3반]</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuizResult'
}
</script>

<style>

</style>