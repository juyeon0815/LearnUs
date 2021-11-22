import gifticonApi from '@/api/gifticon'

const state = {
  gifticonList: null,
  awardList: null,
}

const actions = {
  async createGifticon({ dispatch }, gifticonData) {
    const response = await gifticonApi.createGifticon(gifticonData)
    if (response.status === 200) {
      dispatch('getGifticonList', gifticonData.broadcastId)
      return Promise.resolve(response)
    }
  },
  async getGifticonList({ commit }, broadcastId) {
    const response = await gifticonApi.getGifticonList(broadcastId)
    if (response.status === 200) {
      commit('SET_GIFTICON_LIST', response.data)
    }
  },
  async deleteGifticon({ dispatch }, data) {
    const response = await gifticonApi.deleteGifticon(data.gifticonId)
    if (response.status === 200) {
      dispatch('getGifticonList', data.broadcastId)
      return Promise.resolve(response)
    }
  },
  async getAwardList({ commit }) {
    const response = await gifticonApi.getAwardList()
    if (response.status === 200) {
      commit('SET_AWARD_LIST', response.data)
    }
  }
}

const mutations = {
  SET_GIFTICON_LIST(state, payload) {
    state.gifticonList = payload
  },
  SET_AWARD_LIST(state, payload) {
    state.awardList = payload
  }
}

const getters = {
  gifticonUserIdList(state) {
    if (state.gifticonList) {
      return state.gifticonList.map(student => {
        return student.user.userId
      })
    }
    return null
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters,
}