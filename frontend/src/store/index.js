import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import admin from './modules/admin'
import chat from './modules/chat'
import video from './modules/video'
import replay from './modules/replay'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    admin,
    chat,
    video,
    replay
  }
})

export default store

