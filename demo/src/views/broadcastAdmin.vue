<template>
    <div>
        <div id="check">

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
    methods: {

    },
    created() {
        this.broadcastId = this.$route.params.broadcastId;

        


        sockJS = new SockJS("http://localhost:8080/stomp/chat");
        stomp = Stomp.over(sockJS);
        stomp.connect("guest", "guest", (frame) => {
            console.log("frame : ", frame);
            stomp.subscribe(`/exchange/admin.exchange/admin.${this.broadcastId}`, function(message) {
                const payload = JSON.parse(message.body);

                const html = `<div>
                                <div>${payload}</div>
                                </div>`
                document.getElementById("check").insertAdjacentHTML('beforeend', html);
            }, {'auto-delete':true, 'durable':false, 'exclusive':false})
        }, onerror, '/');
    }
}
</script>