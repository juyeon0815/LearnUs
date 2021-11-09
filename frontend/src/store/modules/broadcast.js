import broadcastApi from '@/api/broadcast'

const state = {
  
}

const actions = {
  // 방송 스케줄
  getBroadcastList({ commit }) {
    broadcastApi.getBroadcastList()
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
