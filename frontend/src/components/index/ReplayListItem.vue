<template>
  <div class="replay-item">
    <div class="info">
      <div class="title">[{{ test.track }}] {{ test.title }}</div>
      <div class="date">{{ test.datetime }}</div>
    </div>
    <i class="fi fi-rr-clip"></i>
  </div>
</template>

<script>
export default {
  name: 'ReplayListItem',
  data () {
    return {
      test: {
        track: '인공지능',
        title: 'Project News: Firebase 활용하기',
        datetime: '2021. 10. 17. 09:00 ~ 12:00'
      }
    }
  }
}
</script>