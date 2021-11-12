import gifticonApi from '@/api/gifticon'

const state = {
  gifticonList: null,
}

const actions = {
  async createGifticon({ commit }, data) {
    try {
      const response = await gifticonApi.createGifticon(data)
      console.log(response)
      commit('TEST')
    } catch (err) {
      console.log(err);
    }
  },
  async getGifticonList({ commit }, broadcastId) {
    try {
      const response = await gifticonApi.getGifticonList(broadcastId)
      if (response.status === 200) {
        commit('SET_GIFTICON_LIST', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
}

const mutations = {
  SET_GIFTICON_LIST(state, payload) {
    state.gifticonList = payload
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
}