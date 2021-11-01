<template>
  <div class="admin student">
    <div class="row">
      <header class="header">
        <div class="chapter">ADMIN<span class="t-orange">:</span></div>
        <span class="subtitle">
          교육생 관리
          <button class="btn">+</button>
        </span>
      </header>
      <SearchBar/>
    </div>
    <StudentTable/>
  </div>
</template>

<script>
import './admin.scss'
import StudentTable from '@/components/admin/StudentTable'
import SearchBar from '@/components/admin/SearchBar'
export default {
  name: 'ManageStudent',
  components: {
    SearchBar,
    StudentTable,
  }
}
</script>

<style>

</style>