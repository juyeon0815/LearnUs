import axios from 'axios'
import store from '@/store/index.js'

const _axios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 10000,
})

_axios.interceptors.request.use(
  function (config) {
    // 요청 헤더에 acToken 추가
    config.headers.accessToken = store.state.account.accessToken
    return config;
  },
  function (error) {
    return Promise.reject(error)
  }
)

_axios.interceptors.response.use(
  function (response) {
    // 응답 헤더에 acToken이 오는지 확인 > 있으면 state 저장
    if (response.headers.accessToken) {
      store.commit('account/SET_ACCESS_TOKEN', response.headers.accessToken)
    }
    // 응답 헤더에 갱신된 acToken이 오는지 확인 > 있으면 state 저장
    if (response.headers.updateAccessToken) {
      store.commit('account/SET_ACCESS_TOKEN', response.headers.updateAccessToken)
    }
    return response;
  },

  async function (error) {
    // rfToken 만료될 경우, 로그아웃 처리
    if (error.response.status === 401 && error.response.data.msg === 'RefreshToken has been expired') {
      store.dispatch('user/onLogout')
    }
    // 500 error 처리
    // if (error.response.status >= 500) {
    //   router.push({ name: 'ServerError'})
    // }
    return Promise.reject(error)
  }
)

export default _axios;
