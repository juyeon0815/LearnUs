import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import chat from './modules/chat'
import live from './modules/live'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    chat,
    live
  }
})

export default store

