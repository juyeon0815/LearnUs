<template>
  <div class="admin student-detail">
    <!-- header -->
    <div class="row">
      <header class="header">
        <div class="chapter">ADMIN<span class="t-orange">:</span></div>
        <span class="subtitle">
          교육생 관리
        </span>
      </header>
    </div>
    <StudentInfo/>
  </div>
</template>

<script>
import StudentInfo from '@/components/admin/studentDetail/StudentInfo'
import './admin.scss'

export default {
  name: 'StudnetDetail',
  components: {
    StudentInfo
  },
}
</script>