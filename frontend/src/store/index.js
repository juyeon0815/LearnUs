import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import admin from './modules/admin'
import chat from './modules/chat'
import onAir from './modules/onAir'
import video from './modules/video'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    admin,
    chat,
    onAir,
    video
  }
})

export default store

