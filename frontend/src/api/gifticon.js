import _axios from "./interceptor"

export default {
    // 기프티콘 생성
    createGifticon (gifticonInfo) {
        return _axios({
            url: 'gifticon',
            method: 'post',
            data: gifticonInfo
        })
    },
    // 기프티콘 수정
    updateGifticon (gifticonInfo) {
        return _axios({
            url: 'gifticon',
            method: 'patch',
            data: gifticonInfo
        })
    },
    // 기프티콘 삭제
    deleteGifticon (broadcastId) {
        return _axios({
            url: `gifticon/${broadcastId}`,
            method: 'delete'
        })
    },
    // 기프티콘 조회
    getGifticonList (broadcastId) {
        return _axios({
            url: `gifticon/all/${broadcastId}`,
            method: 'get'
        })
    }
}