import _axios from "./interceptor"

export default {
  getBroadCasts(ordinalNo) {
    return _axios({
      url: `broadcastReplay/all/${ordinalNo}`,
      method: 'get',
    })
  },

  getBroadCastsByTrack(searchData) {
    return _axios({
      url: `broadcastReplay/track/${searchData.trackId}/${searchData.ordinalNo}`,
      method: 'get'
    })
  },

  getBroadCastInfo(broadcastReplayId) {
    return _axios({
      url: `broadcastReplay/${broadcastReplayId}`,
      method: 'get'
    })
  },

  getIndexReplayList(userId) {
    return _axios({
      url: `broadcastReplay/index/${userId}`,
      method: 'get'
    })
  },
}