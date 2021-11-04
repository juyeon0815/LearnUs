import _axios from "./interceptor"

export default {
  // 교육생 관리
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
  editStudents (data) {
    return _axios({
      url: 'user/list',
      method: 'patch',
      data: data,
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    })
  },
  // 트랙 관리
  getOrdinalNo () {
    return _axios({
      url: `trackSubject/ordinalNo`,
      method: 'get',
    })
  },
  getTrackAll () {
    return _axios({
      url: 'track/all',
      method: 'get',
    })
  },
  getSubjectAll () {
    return _axios({
      url: 'trackSubject/all',
      method: 'get',
    })
  },
  addTrack (data) {
    return _axios({
      url: 'track',
      method: 'post',
      data: data
    })
  },
  editTrack (data) {
    return _axios({
      url: 'track',
      method: 'patch',
      data: data,
    })
  },
  deleteTrack (id) {
    return _axios({
      url: `track/${id}`,
      method: 'delete',
    })
  },
  addSubject (data) {
    return _axios({
      url: 'trackSubject',
      method: 'post',
      data: data
    })
  },
  editSubject (data) {
    return _axios({
      url: 'trackSubject',
      method: 'patch',
      data: data,
    })
  },
  deleteSubject (id) {
    return _axios({
      url: `trackSubject/${id}`,
      method: 'delete',
    })
  },
  changeCurrentSubject (name) {
    return _axios({
      url: `trackSubject/current/${name}`,
      method: 'patch',
    })
  },
  getActiveTrack () {
    return _axios({
      url: `track/current`,
      method: 'get',
    })
  }, 
  // MM 관리
  getMMList () {
    return _axios({
      url: 'mattermost/all',
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