import Vue from "vue";
import Vuex from "vuex";
import rest from "@/js/httpCommon.js"

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user:{},
  },
  mutations: {
    GET_USER(state, payload) {
      state.user = payload;
      alert(state.user.name);
    }
  },
  actions: {
    get_user(store, payload) {
      rest.axios({
        url:"/user/select/"+payload,
        method:"get",
      }).then((res)=>{
        alert("성공");
        store.commit("GET_USER", res.data);
      }).catch((err)=>{
        alert("에러발생");
        console.log(err);
      })
    }
  },
  modules: {},
});
