<template>
  <div class="track">
    <TrackInfo v-if="editTrack" @close="editTrack=false"/>
    <div class="title">
      <h3>Track 관리</h3>
      <button @click="editTrack=true">수정</button>
    </div>
    <div class="track-list">
      <TrackListItem
        v-for="(ordinal, idx) in ordinalNo"
        :key="idx"
        :ordinal=ordinal
      />
    </div>
  </div>
</template>

<script>
import TrackListItem from './TrackListItem.vue'
import TrackInfo from './TrackInfo.vue'
import { mapState } from 'vuex'

export default {
  name: 'ManageTrack',
  data() {
    return {
      editTrack: false
    }
  },
  components: {
    TrackListItem,
    TrackInfo
  },
  computed: {
    ...mapState(
      'admin', ['ordinalNo', 'subjects', 'tracks']
    ),
  },
  created () {
    this.$store.dispatch('admin/getActiveTrack')
  }
}
</script>