import quizApi from '@/api/quiz'

const state = {
  stomp: null,
  chatList: null,
  quizList: null,
  quizTarget: null,
  quizOptions: [
    { value: 'o', label: 'OX 퀴즈' },
    { value: 'c', label: '객관식 퀴즈' },
    { value: 's', label: '주관식 퀴즈' },
  ]
}

const actions = {
  async getQuizList ({ commit }, id) {
    try {
      const response = await quizApi.getQuizList(id)
      if (response.status === 200) {
        commit('SET_QUIZ_LIST', response.data)
      }
    } catch (err) {
      console.log(err)
    }
  },
  async addQuiz ({ dispatch }, data) {
    try {
      const response = await quizApi.addQuiz(data)
      console.log(response)
      if (response.status === 200) {
        dispatch('getQuizList', data.broadcastId)
        return Promise.resolve(response)
      }
    } catch (err) {
      console.log(err)
      return Promise.reject(err)
    }
  },
  async editQuiz ({ dispatch }, data) {
    try {
      const response = await quizApi.editQuiz(data)
      console.log(response)
      if (response.status === 200) {
        dispatch('getQuizList', data.broadcastId)
        return Promise.resolve(response)
      }
    } catch (err) {
      console.log(err)
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
      console.log(err)
    }
  }
}

const mutations = {
  SET_STOMP (state, payload) {
    state.stomp = payload
  },
  SET_QUIZ_LIST (state, payload) {
    state.quizList = payload
  },
  SET_QUIZ_TARGET (state, payload) {
    state.quizTarget = payload
  },
  SET_CHAT_LIST (state, payload) {
    state.chatList = payload
  },
  ADD_CHAT_LIST (state, payload) {
    state.chatList.push(payload)
  }
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
