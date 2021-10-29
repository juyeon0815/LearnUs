import _axios from "./interceptor"

export default {
  login(userData) {
    return _axios({
      url: 'user/login',
      method: 'post',
      data: userData,
    })
  },
  getUserInfo(userId) {
    return _axios({
      url: `user/id/${userId}`,
      method: 'get',
    })
  }
}