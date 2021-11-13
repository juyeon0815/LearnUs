<template>
  <div>
    <button @click="disconnect">연결해제</button>
    <hr />
    닉네임 : <input type="text" v-model="nickName" /> 메시지 :
    <input type="text" v-model="message" />
    <button @click="send">전송</button>
    <hr style="margin-top: 20px" />
    <div id="chats"></div>
  </div>
</template>

<script>
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import axios from "axios";
var sockJS = null;
var stomp = null;
export default {
  data() {
    return {
      nickName: "",
      message: "",
      broadcastId: 0,
    };
  },
  methods: {
    disconnect() {
      console.log("잘봐~ 연결해제 한다~");
      stomp.disconnect(() => {
        console.log("연결해제");
        this.$router.push({ path: "/" });
      });
    },
    send() {
      var now = new Date();
      now.setHours(now.getHours() + 9);

      stomp.send(
        `/pub/chat.message.${this.broadcastId}`,
        {},
        JSON.stringify({
          message: this.message,
          nickName: this.nickName,
          userId: 534567,
          // regDate: new Date().toISOString().substring(0,10),
          // regDate: new Date().toISOString().substring(0,10),
          regDate: new Date(),
        })
      );
    },
  },
  created() {
    this.broadcastId = this.$route.params.broadcastId;
    sockJS = new SockJS("http://localhost:8080/stomp/chat");
    stomp = Stomp.over(sockJS);

    axios({
      method: "get",
      url: "http://localhost:8080/broadcast/chat/" + this.broadcastId,
    })
      .then((res) => {
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });

    stomp.connect(
      "guest",
      "guest",
      (frame) => {
        console.log("frame : ", frame);
        stomp.subscribe(
          `/exchange/chat.exchange/chat.${this.broadcastId}`,
          function (message) {
            console.log("check:" + message.body);
            const payload = JSON.parse(message.body);

            const html = `<div>
                                <div>${payload.nickName} : ${payload.message} (${payload.regDate})</div>
                                </div>`;
            document
              .getElementById("chats")
              .insertAdjacentHTML("beforeend", html);
          },
          { "auto-delete": true, durable: false, exclusive: false }
        );
        stomp.send(`/pub/chat.enter.${this.broadcastId}`, {});
      },
      onerror,
      "/"
    );
  },
};
</script>
