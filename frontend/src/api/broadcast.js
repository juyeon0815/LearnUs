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
  // 방송 중인 영상 리스트
  getOnAirList () {
    return _axios({
      url: 'broadcast/all/Y',
      method: 'get',
    })
  },
  //방송 종료된 영상 리스트
  getHistoryList() {
    return _axios({
      url: 'broadcastReplay/all',
      method: 'get'
    })
  },
  // 방송 정보 관련
  getBroadcastDetail (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'get'
    })
  },
  // 채팅 기록 가져오기
  getBroadcastChat (id) {
    return _axios({
      url: `broadcast/chat/${id}`,
      method: 'get',
    })
  },
  // 출석체크
  checkAttendance (broadcastId, userId) {
    return _axios({
      url: `broadcast/${broadcastId}/${userId}`,
      method: 'patch',
    })
  },
  // 방송 생성
  createBroadcast (data) {
    return _axios({
      url: 'broadcast',
      method: 'post',
      data: data
    })
  },
  // 방송 수정
  updateBroadcastInfo (broadcastInfo) {
    return _axios({
      url: 'broadcast',
      method: 'patch',
      data: broadcastInfo
    })
  },
  // 방송 삭제
  deleteBroadcast (id) {
    return _axios({
      url: `broadcast/${id}`,
      method: 'delete',
    })
  },
  // 참석 명단
  getBroadcastStudents (id) {
    return _axios({
      url: `broadcast/attendance/${id}`,
      method: 'get',
    })
  },
  // 현재 방송중인 방송 리스트
  getOnairList() {
    return _axios({
      url: 'broadcast/all/Y',
      method: 'get',
    })
  },
  startBroadcast(id) {
    return _axios({
      url: 'broadcast/start',
      method: 'post',
      params: {
        'broadcastId': id
      }
    })
  },
  endBroadcast(data) {
    return _axios({
      url: 'broadcast/end/replay',
      method: 'post',
      params: data
    })
  },
  sendAttendMM (id) {
    return _axios({
      url: 'broadcast/end/attendance',
      method: 'post',
      params: {
        'broadcastId': id
      }
    })
  },
  downloadAttendance (id) {
    return _axios({
      url: 'broadcast/end/attendance/download',
      method: 'post',
      params: {
        'broadcastId': id
      },
      responseType: 'blob'
    })
  },
  sendGifticonMM (id) {
    return _axios({
      url: 'broadcast/end/gifticon',
      method: 'post',
      params: {
        'broadcastId': id
      }
    })
  },
  downloadGifticon (id) {
    return _axios({
      url: 'broadcast/end/gifticon/download',
      method: 'post',
      params: {
        'broadcastId': id
      },
      responseType: 'blob'
    })
  },
  getBroadcastAfter (id) {
    return _axios({
      url: `/broadcast/after/${id}`,
      method : 'get',
    })
  },
  getBroadcastStudentCnt (id) {
    return _axios({
      url: `/broadcast/attendance/after/${id}`,
      method : 'get',
    })
  },
  getActiveStudent (id, type) {
    return _axios({
      url: `/broadcast/end/king/${id}/${type}`,
      method : 'get',
    })
  },
  getReplayList (ordinalNo) {
    return _axios({
      url: `broadcastReplay/all/${ordinalNo}`,
      method : 'get',
    })
  },
  getReplayByTrack (id, ordinalNo) {
    return _axios({
      url: `broadcastReplay/track/${id}/${ordinalNo}`,
      method : 'get'
    })
  },
  getReplayId (id) {
    return _axios({
      url: `broadcastReplay/getId/${id}`,
      method : 'get'
    })
  },
  getReplayInfo (id) {
    return _axios({
      url: `broadcastReplay/${id}`,
      method : 'get'
    })
  },
  isAttendCheck (id) {
    return _axios({
      url: `/broadcast/start/isAttend/${id}`,
      method : 'get'
    })
  },
  updateReplayInfo(replayInfo) {
    return _axios({
      url: 'broadcastReplay',
      method: 'patch',
      data: replayInfo
    })
  },
  getIndexReplayList(userId) {
    return _axios({
      url: `broadcastReplay/index/${userId}`,
      method: 'get'
    })
  },
}