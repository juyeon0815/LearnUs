import broadcastApi from '@/api/broadcast'
import quizApi from '@/api/quiz'

const state = {
  stomp: null,
  chatList: null,
  viewers: 0,
  attendCheck: false,
  // 성공하면 1, 실패하면 2
  attendResult: null,
  // 시작 전 0, 진행 중 1, 완료 2
  attendProcess: 0,
  quizList: null,
  currentQuiz: null,
  currentQuizResult: null,
  quizTarget: null,
  quizOptions: [
    { value: 'o', label: 'OX 퀴즈' },
    { value: 'c', label: '객관식 퀴즈' },
    { value: 's', label: '주관식 퀴즈' },
  ]
}

const actions = {
  async getChatList({ commit }, id) {
    try {
      const response = await broadcastApi.getBroadcastChat(id)
      if (response.status === 200) {
        commit('SET_CHAT_LIST', response.data)
      }
    } catch (err) {
      commit('SET_CHAT_LIST', [])
    }
  },
  async isAttendCheck ({ commit }, id) {
    const response = await broadcastApi.isAttendCheck(id)
    if (response.status === 200) {
      commit('SET_ATTEND_CHECK', true)
      commit('SET_ATTEND_PROCESS', 1)
    } else if (response.status === 204) {
      commit('SET_ATTEND_CHECK', false)
      commit('SET_ATTEND_PROCESS', 0)
    }
  },
  async checkAttend ({ commit }, data) {
    commit('SET_ATTEND_CHECK', false)
    try {
      const response = await broadcastApi.checkAttendance(data.broadcastId, data.userId)
      if (response.status === 200) {
        commit('SET_CHECK_RESULT', 1)
      }
    } catch (err) {
      commit('SET_CHECK_RESULT', 2)
    }
    await setTimeout(() => {
      commit('SET_CHECK_RESULT', null)
    }, 3000)
  },
  async getQuizList ({ commit }, id) {
    const response = await quizApi.getQuizList(id)
    if (response.status === 200) {
      commit('SET_QUIZ_LIST', response.data)
    }
  },
  async addQuiz ({ dispatch }, data) {
    try {
      const response = await quizApi.addQuiz(data)
      if (response.status === 200) {
        dispatch('getQuizList', data.broadcastId)
        return Promise.resolve(response)
      }
    } catch (err) {
      return Promise.reject(err)
    }
  },
  async editQuiz ({ dispatch }, data) {
    try {
      const response = await quizApi.editQuiz(data)
      if (response.status === 200) {
        dispatch('getQuizList', data.broadcastId)
        return Promise.resolve(response)
      }
    } catch (err) {
      return Promise.reject(err)
    }
  },
  async deleteQuiz({ dispatch }, data) {
    try {
      const response = await quizApi.deleteQuiz(data.quizId)
      if (response.status === 200) {
        dispatch('getQuizList', data.broadcastId)
      }
    } catch (err) {
      dispatch('getQuizList', data.broadcastId)
    }
  },
  resetStompData ({ commit }) {
    commit('SET_CHAT_LIST', null)
    commit('SET_VIEWERS', 0)
    commit('SET_CURRENT_QUIZ', null)
  }
}

const mutations = {
  SET_STOMP (state, payload) {
    state.stomp = payload
  },
  SET_VIEWERS (state, payload) {
    state.viewers = payload
  },
  SET_ATTEND_CHECK (state, payload) {
    state.attendCheck = payload
  },
  SET_ATTEND_PROCESS (state, payload) {
    state.attendProcess = payload
  },
  SET_CHECK_RESULT (state, payload) {
    state.attendResult = payload
  },
  SET_QUIZ_LIST (state, payload) {
    state.quizList = payload
  },
  SET_QUIZ_TARGET (state, payload) {
    state.quizTarget = payload
  },
  SET_CURRENT_QUIZ (state, payload) {
    state.currentQuiz = payload
  },
  SET_CURRENT_QUIZ_RESULT (state, payload) {
    state.currentQuizResult = payload
  },
  SET_CHAT_LIST (state, payload) {
    state.chatList = payload
  },
  ADD_CHAT_LIST (state, payload) {
    state.chatList.push(payload)
  }
}

const getters = {
  chatLength (state) {
    return state.chatList.length
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
