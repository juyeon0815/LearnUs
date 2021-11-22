<template>
  <tr @click="getDetail">
    <td>{{ idx + 1 }}</td>
    <td>{{ student.name }}</td>
    <td>{{ userId }}</td>
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
  },
  methods: {
    getDetail() {
      this.$store.commit('admin/SET_TARGET_ID', this.student.userId)
      this.$emit('open-detail')
    }
  },
  computed: {
    userId () {
      return String(this.student.userId).padStart(7, '0')
    }
  }
}
</script>

<style>

</style>