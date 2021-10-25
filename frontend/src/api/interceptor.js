import axios from 'axios'

const _axios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 10000,
})

_axios.interceptors.request.use(
  function (config) {
    return config;
  }, 
  function (error) {
    return Promise.reject(error)
  }
)

_axios.interceptors.response.use(
  function (response) {
    return response;
  },

  async function (error) {
    
    // 500 error 처리
    // if (error.response.status >= 500) {
    //   router.push({ name: 'ServerError'})
    // }
    return Promise.reject(error)
  }
)

export default _axios;
