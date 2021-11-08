import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import admin from './modules/admin'
import broadcast from './modules/broadcast'
import chat from './modules/chat'
import onAir from './modules/onAir'
import video from './modules/video'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    admin,
    broadcast,
    chat,
    onAir,
    video
  }
})

export default store

