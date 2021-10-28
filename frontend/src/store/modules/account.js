import router from '@/router'

const state = {
  accessToken: null,
}

const actions = {
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
