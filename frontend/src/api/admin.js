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
}