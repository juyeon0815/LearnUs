<template>
  <div>
    <h1>퀴즈</h1>
    <hr style="margin-top: 20px" />
    퀴즈번호 : <input type="text" v-model="quizId" />
    <button @click="quizStart">퀴즈 시작</button><br>
    <button @click="quizEnd">퀴즈 끝내기</button>
    <div id="check"></div>
  </div>
</template>

<script>
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";

var sockJS = null;
var stomp = null;
export default {
  date() {
    return {
      quizId: 0,
      broadcastId: 0,
    };
  },
  methods: {
    quizStart() {
        console.log(`퀴즈스타트 ${this.quizId}`);

      stomp.send(
        `/pub/quiz.start.${this.broadcastId}`,
        {},
        //quizId 하나만 보내기때문에 객체가 아닌 단일 값으로 전송
        this.quizId
        
        // JSON.stringify({
        //   quizId: this.quizId,
        // })
      );
    },
    quizEnd() {
      stomp.send(`/pub/quiz.stop.${this.broadcastId}`, {} , this.quizId);
    }
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
          `/exchange/admin.exchange/admin.${this.broadcastId}`,
          function (message) {
            const payload = JSON.parse(message.body);

            const html = `<div>
                                <div>${payload}</div>
                                </div>`;
            document
              .getElementById("check")
              .insertAdjacentHTML("beforeend", html);
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