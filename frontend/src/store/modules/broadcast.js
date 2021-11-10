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
        const broadcastList = res.data
        broadcastList.sort(function (a, b) {
          if (a.broadcastDate > b.broadcastDate) {
            return 1
          }
          if (a.broadcastDate < b.broadcastDate) {
            return -1
          }
          return 0;
        })
        commit('SET_BROADCAST_DETAIL', broadcastList)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  // 방송 생성 
  async createBroadcast ({ commit }, data) {
    try {
      const response = await broadcastApi.createBroadcast(data)
      console.log(response)
    } catch (err) {
      commit('TEMP', 'test')
    }
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
  broadcastByDate(state) {
    /* function leftZero(val) {
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
    const tomorrow = [year, month, day+1].join('-') */
    /* return state.broadcastList.filter(broadcast => 
      broadcast.broadcastDate.split(' ')[0] >= today
    ) */
    const dates = state.broadcastList.map(broadcast => {
      return broadcast.broadcastDate.split(' ')[0]
    })
    const uniqueDates = [...new Set(dates)]
    uniqueDates.sort()
    const schedule = {}
    for (var i = 0; i < uniqueDates.length; i++) {
      schedule[`${uniqueDates[i]}`] = []
    }
    for (var k = 0; k < state.broadcastList.length; k++) {
      schedule[`${state.broadcastList[k].broadcastDate.split(' ')[0]}`].push(state.broadcastList[k])
    }
    /* if (uniqueDates.includes(today)) {
      schedule['today'] = schedule[today]
      delete schedule[today]
    }
    if (uniqueDates.includes(tomorrow)) {
      schedule['tomorrow'] = schedule[tomorrow]
      delete schedule[tomorrow]
    } */
    /* const result = []
    for (const element in schedule) {
      const newElement = {
        date: element,
        schedule: schedule[element]
      }
      result.push(newElement)
    } */
    return schedule
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
