import router from '@/router'
import accountApi from '@/api/account'

const state = {
  accessToken: null,
  userInfo: null,
  searchedEmail: null,
  instantUserId: null,
  httpStatus: null,
  photoKey: 0,
  regionList: null,
  tokenExpired: false,
}

const actions = {
  async onLogin({ dispatch, commit }, userData) {
    await accountApi.login(userData)
      .then((res) => {
        dispatch('getUserInfo', res.data.userId)
        commit('SET_HTTP_STATUS', null)
        commit('SET_EXPIRE', false)
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  onLogout ({ commit }) {
    commit('SET_ACCESS_TOKEN', null)
    commit('SET_USER_INFO', null)
    router.push('/account/login')
  },
  async onFindEmail ({ commit }, userData) {
    await accountApi.findEmail(userData)
      .then((res) => {
        commit('SET_HTTP_STATUS', res.status)
        commit('SET_SEARCHED_EMAIL', res.data)
        router.push('/account/login')
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  async onFindPassword ({ commit }, userData) {
    await accountApi.findPassword(userData)
      .then((res) => {
        if (res.status === 200) {
          commit('SET_INSTANT_USER_ID', userData.userId)
          commit('SET_HTTP_STATUS', res.status)
          router.push('/account/reset-password')
        } 
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  async onResetPassword ({ commit, state }, password) {
    const userData = {
      userId: state.instantUserId,
      newPW: password
    }
    await accountApi.resetPassword(userData)
      .then((res) => {
        router.push('/account/login')
        commit('SET_HTTP_STATUS', res.status)
        commit('SET_INSTANT_USER_ID', null)
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  async onChangePassword ({ state, commit }, passwordData) {
    const userData = {
      ...passwordData,
      userId: state.userInfo.userId
    }
    await accountApi.changePassword(userData)
      .then((res) => {
        commit('SET_HTTP_STATUS', res.status)
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  async onChangeUserInfo({ state, commit }, userInfo) {
    const userData = {
      ...state.userInfo,
      ...userInfo
    }
    await accountApi.changeUserInfo(userData)
      .then((res) => {
        if (res.status === 200) {
          commit('CHANGE_USER_INFO', userData)
          commit('SET_HTTP_STATUS', res.status)
        }
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  async onChangeUserPhoto({ state, commit }, newProfileUrl) {
    const userData = state.userInfo
    userData.profileUrl = newProfileUrl
    await accountApi.changeUserInfo(userData)
      .then((res) => {
        if (res.status === 200) {
          commit('CHANGE_USER_PHOTO', newProfileUrl)
          commit('UPDATE_PHOTO_KEY')
          commit('SET_HTTP_STATUS', res.status)
        }
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  getUserInfo({ commit }, userId) {
    accountApi.getUserInfo(userId)
      .then((res) => {
        commit('SET_USER_INFO', res.data)
      })
  },
  async getRegion ({ commit }) {
    const response = await accountApi.getRegionList()
    commit('SET_REGION', response.data)
  },
  async autoLogout({ commit, dispatch }) {
    dispatch('onLogout')
    commit('SET_EXPIRE', true)
    setTimeout(() => {
      commit('SET_EXPIRE', false)
    }, 3000)
  }
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
  },
  CHANGE_USER_INFO (state, userData) {
    state.userInfo = userData
  },
  CHANGE_USER_PHOTO (state, newProfileUrl) {
    state.userInfo.profileUrl = newProfileUrl
  },
  SET_HTTP_STATUS (state, status) {
    state.httpStatus = status
  },
  UPDATE_PHOTO_KEY (state) {
    state.photoKey += 1
  },
  SET_REGION (state, payload) {
    state.regionList = payload
  },
  SET_EXPIRE (state, payload) {
    state.tokenExpired = payload
  }
}

const getters = {
  isAdmin (state) {
    if (state.userInfo) {
      return state.userInfo.statusCode === 'A' ? 1 : 0
    }
    return 0
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
