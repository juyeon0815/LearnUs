import _axios from "./interceptor"

export default {
    getBroadCasts(ordinalNo) {
        return _axios({
            url: `broadcastReplay/all/${ordinalNo}`,
            method : 'get',
        })
    },

    getBroadCastsByTrack(searchData) {
        return _axios({
            url: `broadcastReplay/${searchData.trackId}/${searchData.ordinalNo}`,
            method : 'get'
        })
    }
}