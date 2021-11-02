<template>
  <div class="search-bar">
    <select 
      class="ordinal-select" 
      v-model="selectedOrd"
      @change="changeOrdinal"
    >
      <option 
        v-for="num in ordinal"
        :value="num"
        :key="num"
      >{{ num }}기</option>
    </select>
    <input 
      type="text"
      placeholder="검색"
      :value="target"
      @input="inputText"
    >
    <i class="search-btn fi fi-rr-search-heart"></i>
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
export default {
  name: 'SearchBar',
  data () {
    return {
      ord: null,
      target: '',
    }
  },
  methods: {
    inputText (event) {
      this.$store.commit('admin/SET_SEARCH_WORD', event.target.value)
    },
    changeOrdinal(event) {
      this.$store.commit('admin/SET_ORD', event.target.value)
    }
  }, 
  computed: {
    ...mapState('admin', ['selectedOrd', 'searchWord']),
    ...mapGetters('admin', ['ordinal'])
  },
  mounted () {
    if (!this.selectedOrd) {
      this.$store.commit('admin/SET_ORD', this.ordinal[0])
    }
    this.$store.commit('admin/SET_SEARCH_WORD', '')
  },
  beforeUnmount () {
    this.$store.commit('admin/SET_SEARCH_WORD', '')
  }
}
</script>