import adminApi from '@/api/admin'

const state = {
  // 교육생 관리
  students: null,
}

const actions = {
  // 교육생 관리
  async getStudents({ commit }) {
    const response = await adminApi.getStudents()
    console.log(response)
    commit('SET_STUDENTS', response.data)
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
  }
}

const mutations = {
  SET_STUDENTS (state, payload) {
    this.students = payload
  }
}

const getters = {

}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
