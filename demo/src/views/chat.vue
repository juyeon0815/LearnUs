<template>
  <div>
    <h1>내 닉네임</h1>
    <input type="text" v-model="nickName" />
    <hr style="margin-top: 20px" />
    <h1>CHAT ROOM 만들기</h1>
    <form>
      방 이름 : <input type="text" v-model="roomName" />
      <button @click="roomCreate">만들기</button>
    </form>
    <hr style="margin-top: 20px" />
    <h1 style="margin-top: 20px">CHAT ROOM LIST</h1>
    <div v-for="(info, idx) of chatRoomList" :key="idx">
      <div @click="roomEnter(info)">{{ info.name }}</div>
    </div>

    <hr style="margin-top: 20px" />
    <h1>LearnUS</h1>
    방송 아이디 : <input type="text" v-model="broadcastId" />
    <hr style="margin-top: 20px" />
    <button @click="attendStart">관리자 : 출석 체크 시작~</button>
    <hr style="margin-top: 20px" />
    <button @click="attend">교육생 : 출석 체크 해야댑!!!!!</button>
    <hr style="margin-top: 20px" />
    <button @click="chat">채팅 하잣</button>
    <hr style="margin-top: 20px" />
    <button @click="view">시청자 수 확인하잣</button>
    <hr style="margin-top: 20px" />
    <button @click="quizStart">퀴즈 시작하잣</button><br>
    <button @click="quiz">퀴즈 게스트</button>
  </div>
</template>

<style>
.chats {
  display: flex;
  flex-direction: column;

  gap: 10px;
}
.message {
  font-size: 14px;
}
</style>

<script>
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";
import axios from "axios";
const sockJS = new SockJS("http://localhost:8080/stomp/chat");
const stomp = Stomp.over(sockJS);
export default {
  data() {
    return {
      message: "",
      chatRoomId: 1,
      memberId: 1,
      nickName: "",
      quizAnswer: "",
      chatRoomList: [],
      roomName: "",
      broadcastId: 0,
    };
  },
  methods: {
    quizStart(){
      this.$router.push({ path: "/quiz/start/"+this.broadcastId});
    },
    quiz(){
      this.$router.push({ path: "/quiz/"+this.broadcastId});
    },
    view() {
      this.$router.push({ path: "/broadcast/admin/" + this.broadcastId });
    },
    chat() {
      this.$router.push({ path: "/broadcast/" + this.broadcastId });
    },
    attendStart() {
      this.$router.push({ path: "/attend/start" });
    },
    attend() {
      this.$router.push({ path: "/attend/" + this.broadcastId });
    },
    roomCreate() {
      var roomId = this.chatRoomList.length + 1;
      axios({
        method: "post",
        url: "http://localhost:8080/chat/room",
        params: {
          name: this.roomName,
          id: roomId,
        },
      })
        .then((res) => {
          console.log(res);
          // this.$router.push({path:"detail/"+roomId+"/"+this.roomName+"/"+this.nickName});
        })
        .catch((err) => {
          console.log(err);
        });
    },
    roomEnter(chatRoom) {
      console.log("들어갈 방 : ", chatRoom);
      this.$router.push({
        path:
          "detail/" +
          chatRoom.roomId +
          "/" +
          chatRoom.name +
          "/" +
          this.nickName,
      });
    },
    send() {
      console.log("nickname : ", this.nickName);
      stomp.send(
        "/pub/chat.message.2",
        {},
        JSON.stringify({
          message: this.message,
          memberId: 1,
          nickName: this.nickName,
          chatRoomId: 1,
        })
      );
    },
    quizSend() {
      stomp.send(
        "/pub/quiz.answer.2",
        {},
        JSON.stringify({
          answer: this.quizAnswer,
          userId: 1,
          nickName: this.nickName,
          quizId: 1,
        })
      );
    },
  },
};
</script>