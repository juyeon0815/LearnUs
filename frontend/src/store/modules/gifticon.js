import gifticonApi from '@/api/gifticon'

const state = {
  gifticonList: null,
  awardList: null,
}

const actions = {
  async createGifticon({ dispatch }, gifticonData) {
    try {
      const response = await gifticonApi.createGifticon(gifticonData)
      if (response.status === 200) {
        dispatch('getGifticonList', gifticonData.broadcastId)
        return Promise.resolve(response)
      }
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
  async deleteGifticon({ dispatch }, data) {
    try {
      const response = await gifticonApi.deleteGifticon(data.gifticonId)
      if (response.status === 200) {
        dispatch('getGifticonList', data.broadcastId)
        return Promise.resolve(response)
      }
    }
    catch(err) {
      console.log(err)
    }
  },
  async getAwardList({ commit }) {
    try {
      const response = await gifticonApi.getAwardList()
      if (response.status === 200) {
        commit('SET_AWARD_LIST', response.data)
      }
    }
    catch(err) {
      console.log(err)
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