<template>
  <div>
    <h1>방 이름 : {{ this.$route.params.roomName }}</h1>
    <hr style="margin-top: 20px" />
    메시지 : <input type="text" v-model="message" />
    <button @click="send">전송</button>
    <hr style="margin-top: 20px; margin-bottom: 20px" />
    <div id="chats"></div>
  </div>
</template>

<script>
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
var sockJS = null;
var stomp = null;
export default {
  data() {
    return {
      message: "",
      roomId: "",
      roomName: "",
      nickName: "",
      regDate:"",
    };
  },
  methods: {
    send() {
        console.log(`방번호 : ${this.roomId}`);
      stomp.send(
        `/pub/chat.message.${this.roomId}`,
        {},
        JSON.stringify({
          message: this.message,
          nickName: this.nickName,
          chatRoomId: this.roomId,
        })
      );
    },
  },
  async created() {
    sockJS = new SockJS("http://localhost:8080/stomp/chat");
    stomp = Stomp.over(sockJS);
    this.roomId = await this.$route.params.roomId;
    this.roomName = await this.$route.params.roomName;
    this.nickName = await this.$route.params.nickName;
    console.log("roomId : ", this.roomId);
    console.log("roomName : ", this.roomName);
    console.log("nickName : ", this.nickName);

    console.log("stomp : ", this.stomp);

    stomp.connect(
      "guest",
      "guest",
      (frame) => {
        console.log("frame : ", frame);
        console.log("STOMP CONNECT");
        console.log("roomId connect : ", this.roomId);
        console.log("stomp connect : ", stomp);
        console.log("params : ", this.$route.params.roomId);
        stomp.subscribe(
          `/exchange/chat.exchange/room.${this.roomId}`,
          function (message) {
            const payload = JSON.parse(message.body);

            const html = `<div>
                                <div>${payload.nickName} : ${payload.message}</div>
                                </div>`;
            document
              .getElementById("chats")
              .insertAdjacentHTML("beforeend", html);
          },
          { "auto-delete": true, durable: false, exclusive: false }
        );
        stomp.send(
          `/pub/chat.enter.${this.roomId}`,
          {},
          JSON.stringify({
            nickName: this.nickName,
            chatRoomId: this.roomId,
          })
        );
      },
      onerror,
      "/"
    );
  },
};
</script>
