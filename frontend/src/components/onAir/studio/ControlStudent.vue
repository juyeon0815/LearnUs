<template>
  <div class="control-student">
    <ControlStudentClass
      v-for="idx in 8"
      :key="idx"
    />
  </div>
</template>

<script>
import ControlStudentClass from './ControlStudentClass.vue'
export default {
  name: 'ControlStudent',
  components: {
    ControlStudentClass
  }
}
</script>