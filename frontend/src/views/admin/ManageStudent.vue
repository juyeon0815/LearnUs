<template>
  <div class="admin student">
    <div class="row">
      <header class="header">
        <div class="chapter">ADMIN<span class="t-orange">:</span></div>
        <span class="subtitle">
          교육생 관리
          <button 
            class="btn"
            @click="onUpdate = true"
          >+</button>
        </span>
      </header>
      <SearchBar/>
    </div>
    <StudentTable/>
    <StudentUpdate
      v-if="onUpdate"
      @close="onUpdate=false"
    />
  </div>
</template>

<script>
import { mapState } from 'vuex'
import './admin.scss'
import StudentTable from '@/components/admin/StudentTable'
import StudentUpdate from '@/components/admin/StudentUpdate'
import SearchBar from '@/components/admin/SearchBar'
export default {
  name: 'ManageStudent',
  components: {
    SearchBar,
    StudentTable,
    StudentUpdate
  },
  data () {
    return {
      onUpdate: false,
    }
  },
  computed: {
    ...mapState('admin', ['students']),
  },
  created () {
    this.$store.dispatch('admin/getStudents')
  }
}
</script>