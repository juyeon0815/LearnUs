<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td>{{ student.name }}</td>
    <td>{{ student.id }}</td>
    <td>{{ student.region }} {{ student.class }} ({{ student.track }})</td>
    <td>{{ student.phoneNum }}</td>
  </tr>
</template>

<script>
export default {
  name: 'StudentTableRow',
  props: {
    student: Object,
    idx: Number
  }
}
</script>

<style>

</style>