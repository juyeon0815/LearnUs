<template>
  <div class="search-bar">
    <input 
      type="text"
      placeholder="검색"
      @input="inputText"
      :value="target"
    >
    <i class="search-btn fi fi-rr-search-heart"></i>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data () {
    return {
      target: ''
    }
  },
  methods: {
    inputText (event) {
      this.target = event.target.value
    }
  }
}
</script>

<style>

</style>