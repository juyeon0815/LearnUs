<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td>{{ student.user.name }}</td>
    <td>{{ student.user.userId }}</td>
    <td>{{ student.user.region }} {{ student.user.classNo }}반</td>
    <td>{{ student.attend }}</td>
  </tr>
</template>

<script>
export default {
  name: 'ControlStudentTableRow',
  props: {
    student: Object,
    idx: Number
  }
}
</script>

<style>

</style>