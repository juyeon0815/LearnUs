import adminApi from '@/api/admin'

const state = {
  // 교육생 관리
  students: null,
  selectedOrd: null,
  searchWord: '',
  singleStudent: null,
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
    try {
      const response = await adminApi.getStudents()
      commit('SET_STUDENTS', response.data)
    } catch (err) {
      console.log(err.response)
    }
  },
  async getSingleStudent({ commit }, id) {
    await adminApi.getSingleStudent(id)
      .then((res) => {
        commit('SET_SINGLE_STUDENT', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  // 트랙 관리
  async setTrackInfo({ dispatch }) {
    try {
      // 기수 정보 저장
      dispatch('getOrdinalAll')
      // 과정 정보 저장
      dispatch('getSubjectAll')
      // 트랙 정보 저장
      dispatch('getTrackAll')
    } catch (err) {
      console.log(err.response)
    }
  },
  async getOrdinalAll ({ commit }) {
    try {
      const response = await adminApi.getOrdinalNo()
      commit('SET_ORDINAL_NO', response.data)
    } catch (err) {
      console.log(err.response)
    }
  },
  async getSubjectAll ({ commit }) {
    try {
      const response = await adminApi.getSubjectAll()
      commit('SET_SUBJECTS', response.data)
    } catch (err) {
      console.log(err.response)
    }
  },
  async getTrackAll({ commit }) {
    try {
      const response = await adminApi.getTrackAll()
      commit('SET_TRACKS', response.data)
    } catch (err) {
      console.log(err.response)
    }
  },
  async getActiveTrack({ commit }) {
    try {
      const response = await adminApi.getActiveTrack()
      commit('SET_ACTIVE_TRACK', response.data)
    } catch (err) {
      console.log(err.response)
    }
  },
  // MM 관리
  async getMMAll({ commit }) {
    try {
      const response = await adminApi.getMMList()
      commit('SET_MM_LIST', response.data)
    } catch (err) {
      console.log(err.response)
    }
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
  SET_SINGLE_STUDENT(state, payload) {
    state.singleStudent = payload
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
  // 교육생 관리
  ordinal (state) {
    return Object.keys(state.students)
  },
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
