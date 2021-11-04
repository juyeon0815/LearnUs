<template>
  <div class="mm">
    <MMCreate v-if="addChannel" @close="addChannel=false"/>
    <div class="title">
      <h3>MM 채널 관리</h3>
      <button @click="addChannel=true">새 채널 추가</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>채널명</th>
          <th>트랙</th>
          <th>WebHook</th>
        </tr>
      </thead>
      <tbody>
        <MMTableRow
          v-for="(channel, idx) in MMList"
          :key="idx"
          :channel="channel"
        />
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import MMTableRow from './MMTableRow.vue'
import MMCreate from './MMCreate.vue'
export default {
  name: 'MMTable',
  components: {
    MMTableRow,
    MMCreate
  },
  data () {
    return {
      addChannel: false
    }
  },
  computed: {
    ...mapState('admin', ['MMList'])
  },
  created () {
    this.$store.dispatch('admin/getMMAll')
  }
}
</script>

<style>

</style>