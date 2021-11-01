import adminApi from '@/api/admin'

const state = {
  // 교육생 관리
  students: null,
  selectedOrd: null,
  searchWord: '',
}

const actions = {
  // 교육생 관리
  async getStudents({ commit }) {
    const response = await adminApi.getStudents()
    // console.log(response)
    commit('SET_STUDENTS', response.data)
  }
}

const mutations = {
  SET_STUDENTS (state, payload) {
    state.students = payload
  },
  SET_ORD (state, payload) {
    state.selectedOrd = payload
  },
  SET_SEARCH_WORD (state, payload) {
    state.searchWord = payload
  }
}

const getters = {
  ordinal (state) {
    return Object.keys(state.students)
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
