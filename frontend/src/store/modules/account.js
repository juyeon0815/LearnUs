import router from '@/router'
import accountApi from '@/api/account'

const state = {
  accessToken: null,
  userInfo: null,
}

const actions = {
  onLogin({ dispatch }, userData) {
    accountApi.login(userData)
      .then((res) => {
        dispatch('getUserInfo', res.data.userId)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  onLogout ({ commit }) {
    commit('SET_ACCESS_TOKEN', null)
    router.push('/account/login')
  },
  onFindEmail ({ commit }, userData) {
    accountApi.findEmail(userData)
    .then((res) => {
      console.log(res)
      console.log(commit)
    })
  },
  getUserInfo({ commit }, userId) {
    accountApi.getUserInfo(userId)
      .then((res) => {
        commit('SET_USER_INFO', res.data)
      })
  },
}

const mutations = {
  SET_ACCESS_TOKEN (state, payload) {
    state.accessToken = payload
  },
  SET_USER_INFO (state, userData) {
    state.userInfo = userData
    router.push('/')
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
