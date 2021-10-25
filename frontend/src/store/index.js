import { createStore } from 'vuex'
import user from './modules/user'
import live from './modules/live'
import createPersistedState from "vuex-persistedstate";

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    user,
    live
  }
})

export default store

