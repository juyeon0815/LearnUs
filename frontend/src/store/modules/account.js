import router from '@/router'
import accountApi from '@/api/account'

const state = {
  accessToken: null,
}

const actions = {
  async onLogin({ dispatch }, userData) {
    await accountApi.login(userData)
      .then((res) => {
        console.log(res)
        dispatch('getUserInfo', res.data.userId)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  onLogout ({ commit }) {
    commit('SET_ACCESS_TOKEN', null)
    router.push('/account/login')
  }
}

const mutations = {
  SET_ACCESS_TOKEN (state, payload) {
    state.accessToken = payload
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
