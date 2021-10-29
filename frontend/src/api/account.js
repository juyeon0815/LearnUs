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
  },
  findEmail(userData) {
    return _axios({
      url: `user/searchId/${userData.userId}/${userData.phoneNumber}`,
      method: 'get'
    })
  }
}