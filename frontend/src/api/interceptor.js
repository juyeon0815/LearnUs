import axios from 'axios'
import router from '@/router'
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
    if (response.headers.accesstoken) {
      store.commit('account/SET_ACCESS_TOKEN', response.headers.accesstoken)
    }
    return response;
  },

  async function (error) {
    // rfToken 만료될 경우, 로그아웃 처리
    if (error.response.status === 401 && error.response.headers.msg === 'RefreshToken has been expired') {
      store.dispatch('account/autoLogout')
    }
    // 500 error 처리
    if (error.response.status >= 500) {
      router.push('/error/500')
    }
    return Promise.reject(error)
  }
)

export default _axios;
