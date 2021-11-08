<template>
  <tr>
    <td>{{ idx + 1 }}</td>
    <td>{{ student.name }}</td>
    <td>{{ student.userId }}</td>
    <td>{{ student.region }} {{ student.classNo }}반</td>
    <td>{{ student.track.trackName }}</td>
    <td>{{ student.phone }}</td>
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