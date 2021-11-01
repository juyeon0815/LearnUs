import router from '@/router'
import accountApi from '@/api/account'

const state = {
  accessToken: null,
  userInfo: null,
  searchedEmail: null,
  instantUserId: null,
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
      commit('SET_SEARCHED_EMAIL', res.data)
      router.push('/account/login')
    })
    .catch((err) => {
      console.log(err)
    })
  },
  onFindPassword ({ commit }, userData) {
    accountApi.findPassword(userData)
    .then((res) => {
      if (res.status === 200) {
        commit('SET_INSTANT_USER_ID', userData.userId)
        router.push('/account/reset-password')
      } else {
        console.log(res)
      }
    })
    .catch((err) => {
      console.log(err)
    })
  },
  onResetPassword ({ commit, state }, password) {
    const userData = {
      userId: state.instantUserId,
      newPW: password
    }
    accountApi.resetPassword(userData)
    .then(() => {
      router.push('/account/login')
      commit('SET_INSTANT_USER_ID', null)
    })
    .catch((err) => {
      console.log(err)
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
  },
  SET_SEARCHED_EMAIL (state, email) {
    state.searchedEmail = email
  },
  SET_INSTANT_USER_ID (state, userId) {
    state.instantUserId = userId
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
