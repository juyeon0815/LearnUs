<template>
  <div class="search-bar">
    <select 
      class="ordinal-select" 
      v-model="ord"
      @change="changeOrdinal"
    >
      <option 
        v-for="num in ordinalNo"
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
import { mapState } from 'vuex'
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
      this.$store.commit('admin/SET_SEARCH_WORD', event.target.value.trim())
    },
    changeOrdinal(event) {
      this.$store.commit('admin/SET_ORD', event.target.value)
    }
  }, 
  computed: {
    ...mapState('admin', ['selectedOrd', 'searchWord', 'ordinalNo']),
  },
  mounted () {
    if (!this.selectedOrd) {
      this.$store.commit('admin/SET_ORD', this.ordinalNo[0])
      this.ord = this.ordinalNo[0]
    } else {
      if (this.ordinalNo.includes(this.selectedOrd)) {
        this.ord = this.selectedOrd
      } else {
        this.ord = this.ordinalNo[0]
      }
    }
    this.$store.commit('admin/SET_SEARCH_WORD', '')
  },
  beforeUnmount () {
    this.$store.commit('admin/SET_SEARCH_WORD', '')
  }
}
</script>