<template>
  <div class="loading">
    <div class="wave-box">
      <span style="color: #15141B;">LOADING···</span>
      <span style="color: #202125;">LOADING···</span>
      <span style="color: #424550;">LOADING···</span>
      <span style="color: #FF6936;">LOADING···</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Loading'
}
</script>

<style lang="scss" scoped>
  @use "sass:math";
  .loading {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
    img {
      width: 60px;
      height: 60px;
    }
    .wave-box {
      position: relative;
      span {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-family: 'Montserrat', sans-serif;
        font-weight: 900;
        font-size: 2rem;
        font-style: italic;
        -webkit-text-stroke-width: 1px;
        -webkit-text-stroke-color: #424550;
        animation-name: wave;
        animation-iteration-count: infinite;
        animation-timing-function: ease-in-out;
        @for $i from 1 through 4 {
          &:nth-of-type(#{$i}) {
            animation-duration: 2s;
            animation-delay: 0.8s - math.div($i, 4);
          }
        }
      }
    }
    @keyframes wave {
      40%, 50% {
        transform: translate(-50%, -65%) scale(1.05);
      }
      0%, 90%, 100% {
        transform: translate(-50%, -45%) scale(0.95);
      }
    }
  }
</style>