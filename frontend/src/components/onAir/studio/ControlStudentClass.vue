<template>
  <div class="class" @click="$emit('open', className)">
    <span class="name">{{ name }}</span>
    <span class="rate">{{ attend }} / {{ info.length }}</span>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'ControlStudentClass',
  props: {
    className: String
  },
  computed: {
    ...mapState('broadcast', ['studentList']),
    name () {
      return this.className.replace('_', ' ')
    },
    info () {
      return this.studentList[this.className]
    },
    attend () {
      return this.info.reduce((acc, student) => {
        let attend = student.attend === 'Y' ? 1 : 0
        return acc + attend
      }, 0);
    }
  }
}
</script>

<style>

</style>