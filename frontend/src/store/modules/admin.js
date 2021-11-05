import adminApi from '@/api/admin'

const state = {
  // 교육생 관리
  students: null,
  selectedOrd: null,
  searchWord: '',
  singleStudent: null,
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

  getSingleStudent({ commit }, id) {
    adminApi.getSingleStudent(id)
      .then((res) => {
        commit('SET_SINGLE_STUDENT', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  
  // 방송 스케줄
  getBroadcastList({ commit }) {
    adminApi.getBroadcastList()
      .then((res) => {
        console.log(res)
        console.log(commit)
      })
      .catch((err) => {
        console.log(err)
      })
    },
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
  },
  SET_SINGLE_STUDENT(state, payload) {
    state.singleStudent = payload
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
