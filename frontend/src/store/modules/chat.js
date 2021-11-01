const state = {
  chatList: [
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '안녕하세요! 반갑습니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '다나가[서울_3반]'
      },
      content: '채팅 UI 테스트 중입니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '이것은 테스트 인풋입니다. 이번엔 좀 길게 써야 되는데 할 말이 없습니다. 로렘은 영어인데 한글 로렘이 있으면 얼마나 좋을까요.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '안녕하세요! 반갑습니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '다나가[서울_3반]'
      },
      content: '채팅 UI 테스트 중입니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '이것은 테스트 인풋입니다. 이번엔 좀 길게 써야 되는데 할 말이 없습니다. 로렘은 영어인데 한글 로렘이 있으면 얼마나 좋을까요.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '안녕하세요! 반갑습니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '다나가[서울_3반]'
      },
      content: '채팅 UI 테스트 중입니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '이것은 테스트 인풋입니다. 이번엔 좀 길게 써야 되는데 할 말이 없습니다. 로렘은 영어인데 한글 로렘이 있으면 얼마나 좋을까요.'
    },
    {
      user: {
        id: 549580,
        nickname: '다나가[서울_3반]'
      },
      content: '채팅 UI 테스트 중입니다.'
    },
    {
      user: {
        id: 549580,
        nickname: '가나다[서울_3반]'
      },
      content: '이것은 테스트 인풋입니다. 이번엔 좀 길게 써야 되는데 할 말이 없습니다. 로렘은 영어인데 한글 로렘이 있으면 얼마나 좋을까요.'
    },
  ]
}

const actions = {

}

const mutations = {
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
