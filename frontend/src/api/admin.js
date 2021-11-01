import _axios from "./interceptor"

export default {
  getStudents () {
    return _axios({
      url: 'user/all',
      method: 'get',
    })
  }
}