import onAirApi from '@/api/onAir'

const state = {
  test: ''
}

const actions = {
  async createBroadcast ({ commit }, data) {
    const response = await onAirApi.createBroadcast(data)
    console.log(response)
    commit('TEMP', 'test')
  }
}

const mutations = {
  TEMP (state, payload) {
    state.test = payload
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
