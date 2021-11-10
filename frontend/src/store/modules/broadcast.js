import broadcastApi from '@/api/broadcast'

const state = {
  broadcastList: null,
  broadcastDetail: null,
  studentList: null,
  studentTarget: null,
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
  },
  async getBroadcastStudents ({ commit }, id) {
    try {
      const response = await broadcastApi.getBroadcastStudents(id)
      commit('SET_STUDENT_LIST', response.data)
      // console.log(response)
    } catch (err) {
      console.log(err)
    }
  }
}

const mutations = {
  SET_BROADCAST_LIST(state, payload) {
    state.broadcastList = payload
  },
  SET_BROADCAST_DETAIL(state, payload) {
    state.broadcastDetail = payload
  },
  SET_STUDENT_LIST (state, payload) {
    state.studentList = payload
  },
  SET_STUDENT_TARGET (state, payload) {
    state.studentTarget = payload
  },
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
  },
  studentData (state) {
    if (state.studentTarget) {
      return state.studentList[state.studentTarget]
    } else {
      return null
    }
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
