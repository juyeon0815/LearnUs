import _axios from "./interceptor"

export default {
  // 기프티콘 생성
  createGifticon(gifticonData) {
    return _axios({
      url: 'gifticon',
      method: 'post',
      data: gifticonData
    })
  },
  // 기프티콘 수정
  updateGifticon(gifticonInfo) {
    return _axios({
      url: 'gifticon',
      method: 'patch',
      data: gifticonInfo
    })
  },
  // 기프티콘 삭제
  deleteGifticon(gifticonId) {
    return _axios({
      url: `gifticon/${gifticonId}`,
      method: 'delete'
    })
  },
  // 기프티콘 조회
  getGifticonList(broadcastId) {
    return _axios({
      url: `gifticon/all/${broadcastId}`,
      method: 'get'
    })
  },
  // award 조회
  getAwardList() {
    return _axios({
      url: 'award',
      method: 'get'
    })
  }
}