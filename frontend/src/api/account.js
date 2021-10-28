import _axios from "./interceptor"

export default {
  login(userData) {
    return _axios({
      url: 'user/login',
      method: 'post',
      data: userData,
    })
  },
}