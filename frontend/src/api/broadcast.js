import _axios from "./interceptor"

export default {
  // 방송 스케줄 관련
  // C(만들었는데 진행 안한 것) -> Y(진행중) -> N(끝)
  getBroadcastList () {
    return _axios({
      url: 'broadcast/all/C',
      method: 'get',
    })
  },
  // 방송 정보 관련
  getBroadcastDetail (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'get'
    })
  },
  updateBroadcastInfo (broadcastInfo) {
    console.log(broadcastInfo)
    return _axios({
      url: 'broadcast',
      method: 'patch',
      data: broadcastInfo
    })
  }
}