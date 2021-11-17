import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate";
import account from './modules/account'
import admin from './modules/admin'
import broadcast from './modules/broadcast'
import gifticon from './modules/gifticon'
import stomp from './modules/stomp'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    account,
    admin,
    broadcast,
    gifticon,
    stomp,
  }
})

export default store

