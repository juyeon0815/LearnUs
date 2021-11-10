<template>
    <div>
        방송 아이디 : <input type="text" v-model="broadcastId">
        <button @click="attendStart">출석 시작</button>
    </div>
</template>

<script>
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
var sockJS = null;
var stomp = null;
export default {
    data() {
        return {
            broadcastId:0,
        }
    },
    methods: {
        attendStart() {
            stomp.send(`/pub/attendance.start.${this.broadcastId}`, {});
        }
    },
    created() {
        sockJS = new SockJS("http://localhost:8080/stomp/chat");
        stomp = Stomp.over(sockJS);
        stomp.connect("guest", "guest", (frame) => {
            console.log("frame : ", frame);
        }, onerror, '/');
    }
}
</script>