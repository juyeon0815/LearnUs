import _axios from "./interceptor"

export default {
  getStudents () {
    return _axios({
      url: 'user/all',
      method: 'get',
    })
  },
  registerStudents (data) {
    return _axios({
      url: 'user',
      method: 'post',
      data: data,
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    })
  },

  getSingleStudent (id) {
    return _axios({
      url: `user/id/${id}`,
      method: 'get',
    })
  },
  // 방송 스케줄 관련
  getBroadcastList () {
    return _axios({
      url: 'broadcast/all',
      method: 'get',
    })
  },
}