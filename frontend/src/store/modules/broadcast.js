import broadcastApi from '@/api/broadcast'

const state = {
  broadcastList: null,
}

const actions = {
  // 방송 스케줄
  getBroadcastList({ commit }) {
    broadcastApi.getBroadcastList()
      .then((res) => {
        commit('SET_BROADCAST_LIST', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
    },
}

const mutations = {
  SET_BROADCAST_LIST(state, payload) {
    state.broadcastList = payload
  }
}

const getters = {
  broadcastAfterToday(state) {
    function leftZero(val) {
      if (val < 10) {
        return `0${val}`
      } else {
        return val
      }
    }
    const dateObject = new Date()
    const year = dateObject.getFullYear()
    const month = leftZero(dateObject.getMonth() + 1)
    const day = leftZero(dateObject.getDate())
    console.log(day)
    const today = [year, month, day].join('-')
    console.log(today)
    console.log(state.broadcastList)
    return state.broadcastList.filter(broadcast => 
      broadcast.broadcastDate.split(' ')[0] >= today
    )
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
