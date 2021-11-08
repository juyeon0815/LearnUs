import _axios from "./interceptor"

export default {
  // 방송 스케줄 관련
  getBroadcastList () {
    return _axios({
      url: 'broadcast/all',
      method: 'get',
    })
  },
}
