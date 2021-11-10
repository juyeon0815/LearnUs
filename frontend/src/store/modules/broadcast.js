import broadcastApi from '@/api/broadcast'

const state = {
  broadcastList: null,
  broadcastDetail: null,
}

const actions = {
  // 방송 스케줄
  getBroadcastList({ commit }) {
    broadcastApi.getBroadcastList()
      .then((res) => {
        console.log(res.data)
        commit('SET_BROADCAST_LIST', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  // 방송 상세 정보 조회 및 수정
  getBroadcastDetail({commit}, id) {
    broadcastApi.getBroadcastDetail(id)
      .then((res) => {
        commit('SET_BROADCAST_DETAIL', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  updateBroadcastInfo({commit}, broadcastInfo) {
    broadcastApi.updateBroadcastInfo(broadcastInfo)
      .then((res) => {
        if (res.status === 200){
          console.log(res)
          commit('SET_BROADCAST_DETAIL', broadcastInfo)
        }
      })
      .catch((err) => {
        console.log(err)
      })
  }
}

const mutations = {
  SET_BROADCAST_LIST(state, payload) {
    state.broadcastList = payload
  },
  SET_BROADCAST_DETAIL(state, payload) {
    state.broadcastDetail = payload
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
    const today = [year, month, day].join('-')
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
