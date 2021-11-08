import _axios from "./interceptor"

export default {
  // 실시간 방송 전체
  getOnAirAll () {
    return _axios({
      url: 'broadcast/all',
      method: 'get',
    })
  },
  // 방송 조회
  getOnAirInfo (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'get',
    })
  },
  // 참석 명단
  getOnAirInfo (id) {
    return _axios({
      url: `broadcast/attendance/${id}`,
      method: 'get',
    })
  },
}