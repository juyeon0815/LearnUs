<template>
  <div class="mm">
    <MMCreate v-if="addChannel" @close-add="addChannel=false"/>
    <MMUpdate 
      v-if="updateChannel" 
      @close-edit="offupdateChannel"
    />
    <div class="title">
      <h3>MM 채널 관리</h3>
      <button @click="addChannel=true">새 채널 추가</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>채널명</th>
          <th>채널 URL path</th>
          <th>연결 트랙</th>
        </tr>
      </thead>
      <tbody>
        <MMTableRow
          v-for="(channel, idx) in MMList"
          :key="idx"
          :channel="channel"
          @on-edit="onUpdateChannel"
        />
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import MMTableRow from './MMTableRow.vue'
import MMCreate from './MMCreate.vue'
import MMUpdate from './MMUpdate.vue'
export default {
  name: 'MMTable',
  components: {
    MMTableRow,
    MMCreate,
    MMUpdate
  },
  data () {
    return {
      addChannel: false,
      updateChannel: false,
    }
  },
  methods: {
    onUpdateChannel () {
      this.updateChannel = true
    },
    offupdateChannel () {
      this.$store.commit('admin/SET_TARGET_CHANNEL', null)
      this.updateChannel = false
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