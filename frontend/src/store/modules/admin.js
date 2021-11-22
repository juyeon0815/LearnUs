import adminApi from '@/api/admin'

const state = {
  // 교육생 관리
  students: null,
  selectedOrd: null,
  searchWord: '',
  studentInfo: null,
  targetId: null,
  // 트랙 관리
  ordinalNo: null,
  subjects: null,
  tracks: null,
  activeTrack: null,
  // MM 관리
  MMList: null,
  targetChannel: null,
  mmData: null
}

const actions = {
  // 교육생 관리
  async getStudents({ commit }) {
    const response = await adminApi.getStudents()
    commit('SET_STUDENTS', response.data)
  },
  async getStudentDetail({ commit }, id) {
    const response = await adminApi.getStudentDetail(id)
    commit('SET_STUDENT_DETAIL', response.data)
  },
  // 트랙 관리
  async setTrackInfo({ dispatch }) {
    // 기수 정보 저장
    dispatch('getOrdinalAll')
    // 과정 정보 저장
    dispatch('getSubjectAll')
    // 트랙 정보 저장
    dispatch('getTrackAll')
  },
  async getOrdinalAll ({ commit }) {
    const response = await adminApi.getOrdinalNo()
    commit('SET_ORDINAL_NO', response.data)
  },
  async getSubjectAll ({ commit }) {
    const response = await adminApi.getSubjectAll()
    commit('SET_SUBJECTS', response.data)
  },
  async getTrackAll({ commit }) {
    const response = await adminApi.getTrackAll()
    commit('SET_TRACKS', response.data)
  },
  async getActiveTrack({ commit }) {
    const response = await adminApi.getActiveTrack()
    commit('SET_ACTIVE_TRACK', response.data)
  },
  // MM 관리
  async getMMAll({ commit }) {
    const response = await adminApi.getMMList()
    commit('SET_MM_LIST', response.data)
  },
}

const mutations = {
  // 교육생 관리
  SET_STUDENTS (state, payload) {
    state.students = payload
  },
  SET_ORD (state, payload) {
    state.selectedOrd = payload
  },
  SET_SEARCH_WORD (state, payload) {
    state.searchWord = payload
  },
  SET_TARGET_ID (state, payload) {
    state.targetId = payload
  },
  SET_STUDENT_DETAIL(state, payload) {
    state.studentInfo = payload
  },
  // 트랙 관리
  SET_ORDINAL_NO (state, payload) {
    state.ordinalNo = payload
  },
  SET_SUBJECTS (state, payload) {
    state.subjects = payload
  },
  SET_TRACKS (state, payload) {
    state.tracks = payload
  },
  SET_ACTIVE_TRACK (state, payload) {
    state.activeTrack = payload
  },
  // MM 관리
  SET_MM_LIST (state, payload) {
    state.MMList = payload
  },
  SET_MM_DATA (state, payload) {
    state.mmData = payload
  },
  SET_TARGET_CHANNEL (state, payload) {
    state.targetChannel = payload
  },
  
}

const getters = {
  // MM 관리
  ordinalOptions (state) {
    let op = [
      { label: '교육생 공지', value: 0 },
    ]
    state.ordinalNo.forEach(ordinal => {
      op.push(
        { 
          label: `${ordinal}기 관리`, 
          value: ordinal
        }
      )
    })
    return op
  },
  trackOptions (state) {
    return state.tracks.map(track => {
      let label = track.trackName + ' | ' + track.trackSubject.trackSetting.ordinalNo + '기 ' + track.trackSubject.subjectName
      return { value: track.trackId, label: label }
    })
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
