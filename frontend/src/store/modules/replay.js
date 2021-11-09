import replayApi from '@/api/replay'

const state = {
    
  broadCasts: null,
  broadCastsByTrack: null,
  selectedSubject: null,
    
}

const actions = {
    //다시보기
    async getBroadCasts({ commit }, ordinalNo) {
        await replayApi.getBroadCasts(ordinalNo).then((res) => {
            commit('SET_BROADCASTS', res.data)
            console.log(res.data)
        }).catch((err) => {
            console.log(err);
        })
  },
  async getBroadCastsByTrack({ commit }, searchData) {
    await replayApi.getBroadCastsByTrack(searchData).then((res) => {
      commit('SET_BROADCASTS_TRACK', res.data)
      console.log(res.data)
    }).catch((err) => {
      console.log(err)
    })
  }
}

const mutations = {
  //다시보기
  SET_BROADCASTS(state, payload) {
    state.broadCasts = payload
  },
  SET_SUBJECT(state, payload) {
    state.selectedSubject = payload
  },
  SET_BROADCASTS_TRACK(state, payload) {
    state.broadCastsByTrack = payload
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

//다시보기

// 1.전체보기 ordinalNo
// 2.해당 트랙별 다시보기 trackId, ordinalNo
// 3.방송 1개 다시보기 , broadcastReplayId