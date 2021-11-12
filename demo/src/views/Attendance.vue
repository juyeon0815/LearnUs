<template>
    <div>
        <div id="attend">

        </div>
    </div>
</template>

<script>
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
var sockJS = null;
var stomp = null;
export default {
    date() {
        return {
            broadcastId:0,
        }
    },
    created() {
        this.broadcastId = this.$route.params.broadcastId;
        sockJS = new SockJS("http://localhost:8080/stomp/chat");
        stomp = Stomp.over(sockJS);
        stomp.connect("guest", "guest", (frame) => {
            console.log("frame : ", frame);
            stomp.subscribe(`/exchange/attendance.exchange/attendance.${this.broadcastId}`, function(message) {
                const payload = JSON.parse(message.body);
                console.log(payload);

                const html = `<div>
                                ${payload}
                                </div>`
                document.getElementById("attend").insertAdjacentHTML('beforeend', html);
            }, {'auto-delete':true, 'durable':false, 'exclusive':false})
        }, onerror, '/');
    }
}
</script>