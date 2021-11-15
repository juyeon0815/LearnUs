import router from '@/router'
import broadcastApi from '@/api/broadcast'
import quizApi from '@/api/quiz'

const state = {
  broadcastList: null,
  broadcastDetail: null,
  broadcastCnt: null,
  studentList: null,
  studentTarget: null,
  httpStatus: null,
  onairList: null,
  quizResult: null,
  replayList: null,
  replayListByTrack: null,
  replayDetail: null,
  selectedSubject: null,
  activeStudents: null,
}

const actions = {
  // 방송 스케줄
  async getBroadcastList({ commit }) {
    try {
      const firstResponse = await broadcastApi.getBroadcastList()
      const secondResponse = await broadcastApi.getOnAirList()
      const broadcastList = firstResponse.data.concat(secondResponse.data)
      broadcastList.sort(function (a, b) {
        if (a.broadcastDate > b.broadcastDate) {
          return 1
        }
        if (a.broadcastDate < b.broadcastDate) {
          return -1
        }
        return 0;
      })
      commit('SET_BROADCAST_LIST', broadcastList)
    }
    catch (err) {
      console.log(err)
    }
  },
  // 방송 상세 정보 조회 및 수정
  async getBroadcastDetail({ commit }, id) {
    try {
      const response = await broadcastApi.getBroadcastDetail(id)
      if (response.status === 200) {
        commit('SET_BROADCAST_DETAIL', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  // 방송 생성 
  async createBroadcast ({ commit }, data) {
    try {
      const response = await broadcastApi.createBroadcast(data)
      commit('SET_HTTP_STATUS', response.status)
      router.push({ name: 'LiveSchedule' })
    } catch (err) {
      commit('TEMP', 'test')
    }
  },
  async updateBroadcastInfo({commit}, broadcastInfo) {
    await broadcastApi.updateBroadcastInfo(broadcastInfo)
      .then((res) => {
        if (res.status === 200){
          commit('SET_HTTP_STATUS', res.status)
          commit('SET_BROADCAST_DETAIL', broadcastInfo)
        }
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
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
  },
  async deleteBroadcast({ commit }, id) {
    await broadcastApi.deleteBroadcast(id)
      .then((res) => {
        commit('DELETE_BROADCAST', id)
        commit('SET_HTTP_STATUS', res.status)
      })
      .catch((err) => {
        commit('SET_HTTP_STATUS', err.response.status)
      })
  },
  getOnairList({ commit }) {
    broadcastApi.getOnairList()
      .then((res) => {
        commit('SET_ONAIR_LIST', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  async getQuizResult ({ commit }, id) {
    try {
      const response = await quizApi.getQuizResult(id)
      if (response.status === 200) {
        commit('SET_QUIZ_RESULT', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  async getActiveStudent ({ commit }, id) {
    try {
      const response = await broadcastApi.getActiveStudent(id)
      if (response.status === 200) {
        commit('SET_ACTIVE_STUDENTS', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  async getBroadcastAfter ({ commit }, id) {
    try {
      const response = await broadcastApi.getBroadcastAfter(id)
      if (response.status === 200) {
        commit('SET_REPLAY_DETAIL', response.data.broadcastReplay)
      }
      const cnt = await broadcastApi.getBroadcastStudentCnt(id)
      if (cnt.status === 200) {
        commit('SET_BROADCAST_CNT', cnt.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  async getReplayDetail ({ commit }, id) {
    try {
      const response = await broadcastApi.getReplayInfo(id)
      if (response.status === 200) {
        commit('SET_REPLAY_DETAIL', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  closeManageVideo ({ commit }) {
    commit('SET_BROADCAST_DETAIL', null)
    commit('SET_BROADCAST_CNT', null)
    commit('SET_STUDENT_LIST', null)
    commit('SET_STUDENT_TARGET', null)
    commit('SET_QUIZ_RESULT', null)
    commit('SET_ACTIVE_STUDENTS', null)
    commit('SET_REPLAY_DETAIL', null)
  },
  closeOnAirStudio ({ commit }) {
    commit('SET_BROADCAST_DETAIL', null)
    commit('SET_STUDENT_LIST', null)
    commit('SET_STUDENT_TARGET', null)
    commit('SET_REPLAY_DETAIL', null)
  },
}

const mutations = {
  SET_BROADCAST_LIST(state, payload) {
    state.broadcastList = payload
  },
  SET_BROADCAST_DETAIL(state, payload) {
    state.broadcastDetail = payload
  },
  SET_BROADCAST_CNT(state, payload) {
    state.broadcastCnt = payload
  },
  SET_STUDENT_LIST (state, payload) {
    state.studentList = payload
  },
  SET_STUDENT_TARGET (state, payload) {
    state.studentTarget = payload
  },
  SET_HTTP_STATUS (state, payload) {
    state.httpStatus = payload
  },
  DELETE_BROADCAST (state, id) {
    state.broadcastList = state.broadcastList.filter(broadcast => {
      return broadcast.broadcastId != id
    })
  },
  SET_ONAIR_LIST (state, payload) {
    state.onairList = payload
  },
  SET_QUIZ_RESULT (state, payload) {
    state.quizResult = payload
  },
  SET_ACTIVE_STUDENTS (state, payload) {
    state.activeStudents = payload
  },
  SET_REPLAY_DETAIL (state, payload) {
    state.replayDetail = payload
  }
}

const getters = {
  currentBroadcastId (state) {
    if (state.broadcastDetail) {
      return state.broadcastDetail.broadcastId
    }
    return null
  },
  isLive (state) {
    if (state.broadcastDetail) {
      return state.broadcastDetail.liveCode === 'Y'
    }
    return null
  },
  studentData (state) {
    if (state.studentTarget) {
      return state.studentList[state.studentTarget]
    }
    return null
  },
  quizCnt (state) {
    if (state.quizResult) {
      return state.quizResult.length
    }
    return 0
  },
  broadcastByDate(state) {
    if (state.broadcastList) {
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
      return schedule
    }
    return null
  },
  entireStudentList(state) {
    if (state.studentList) {
      let result = []
      for (const studentList of Object.values(state.studentList)) {
        result = result.concat(studentList)
      }
      return result
    }
    return null
  },
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
