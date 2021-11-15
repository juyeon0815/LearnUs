<template>
  <div v-if="studentList" class="control-student">
    <ControlStudentTable 
      v-if="studentTable"
      @close="offStudentTable"
    />
    <ControlStudentClass
      v-for="(name, idx) in classList"
      :key="idx"
      :className="name"
      @open="onStudentTable"
    />
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import ControlStudentClass from './ControlStudentClass.vue'
import ControlStudentTable from './ControlStudentTable.vue'
export default {
  name: 'ControlStudent',
  components: {
    ControlStudentClass,
    ControlStudentTable
  },
  data () {
    return {
      studentTable: false,
    }
  },
  methods: {
    ...mapMutations('broadcast', ['SET_STUDENT_TARGET']),
    onStudentTable (key) {
      this.SET_STUDENT_TARGET(key)
      this.studentTable = true
    },
    offStudentTable () {
      this.SET_STUDENT_TARGET(null)
      this.studentTable = false
    }
  },
  computed: {
    ...mapState('broadcast', ['studentList']),
    classList () {
      return Object.keys(this.studentList).sort()
    }
  }
}
</script>