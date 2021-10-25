import { createStore } from 'vuex'
import account from './modules/account'
import live from './modules/live'
import createPersistedState from "vuex-persistedstate";

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    live
  }
})

export default store

