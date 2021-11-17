import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import admin from './modules/admin'
import broadcast from './modules/broadcast'
import gifticon from './modules/gifticon'
import stomp from './modules/stomp'
import replay from './modules/replay'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    admin,
    broadcast,
    gifticon,
    stomp,
    replay
  }
})

export default store

