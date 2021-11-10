<template>
  <div>
    유저 : <input v-model="userId" />
    <hr style="margin-top: 20px" />
    정답 : <input type="text" v-model="answer" />
    <button @click="answerSend">제출</button>
    <div id="chats"></div>
  </div>
</template>

<script>
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
// import axios from "axios";
var sockJS = null;
var stomp = null;
export default {
  data() {
    return {
      nickName: "",
      message: "",
      broadcastId: 0,
      answer: "",
      userId:0,
      quizId:0,
    };
  },
  methods: {
    answerSend() {
        console.log(`제출합시다: ${this.broadcastId}`);
      stomp.send(
        `/pub/quiz.answer`,
        {},
        JSON.stringify({
          quizId: this.quizId,
          userId: this.userId,
          broadcastId: this.broadcastId,
          answer: this.answer,
        })
      );
    },
    
  },
  created() {
    this.broadcastId = this.$route.params.broadcastId;
    sockJS = new SockJS("http://localhost:8080/stomp/chat");
    stomp = Stomp.over(sockJS);

    stomp.connect(
      "guest",
      "guest",
      (frame) => {
        console.log("frame : ", frame);
        stomp.subscribe(
          `/exchange/quiz.exchange/broadcast.${this.broadcastId}`,
          (message) => {
            const payload = JSON.parse(message.body);

            const html = `<div>
                                <div>${payload.quizId} : ${payload.type}, ${payload.question}, ${payload.answer}, ${payload.broadcastId}, ${payload.quizSelectList}</div>
                                </div>`;
            document
              .getElementById("chats")
              .insertAdjacentHTML("beforeend", html);

            this.quizId = payload.quizId;
          },
          { "auto-delete": true, durable: false, exclusive: false }
        );
      },
      onerror,
      "/"
    );
  },
};
</script>