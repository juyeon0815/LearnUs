import router from '@/router'
import gifticonApi from '@/api/gifticon'

const state = {
    gifticonList: null,
    HttpStatus: null,
}

const actions = {
    async createGifticon ({ commit }, data) {
        try {
            const response = await gifticonApi.createGifticon(data)
            commit('SET_HTTP_STATUS', response.status);
            
        } catch (err) {
            console.log(err);
        }
    }
}

const mutations = {
    SET_HTTP_STATUS (state, payload) {
        state.httpStatus = payload
    },
}