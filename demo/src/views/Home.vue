<template>
  <div class="home">
      카테고리 : <input type="text" v-model="categori">
      내용 : <input type="text" v-model="content">
      파일 : <input type="file" id="file" ref="files" multiple="multiple" @change="upload"/>
      <!-- 유저 아이디 : <input type="text" v-model="searchUser"> 
      <button @click="search">검색</button> -->
      <button @click="send">보내기~</button>
      <hr>
      <input type="text" v-model="email">
      <input type="password" v-model="password">
      <button @click="login">로그인</button>
      <hr>
      <input type="number" v-model="broadcastId">
      <button @click="download">참가 명단 다운</button>
  </div>
</template>

<style>
img {
  width:200px;
  height: 200px;
}
</style>

<script>
// @ is an alias to /src
// import rest from "@/js/httpCommon.js";
import axios from "axios";
// import putFile from "@/api/putFile.js";

export default {
  name: "Home",
  data() {
    return {
      categori:"", // 카테고리 1 : 음식, 2 : 옷, 3 : 화장품
      content:"", // 내용
      searchUser:"", // 유저 아이디로 검색
      filename:"", // 미리보기를 위한 변수
      userId:"", // 게시글 작성하는 유저 아이디
      user:{}, // 게시글 작성하는 유저 객체
      list:[], // 백에서 가져온 게시글 리스트
      sendList:[], // 백에 보내는 이미지 정보를 담은 리스트
      urlList:[],
      picker:"",

      email:"",
      password:"",

      broadcastId:0,
    }
  },
  methods: {
    download() {
      const param = new URLSearchParams();
      param.append("broadcastId", this.broadcastId);
      axios({
        method: "post",
        url: "http://localhost:8080/broadcast/end/gifticon/download",
        params : {
          "broadcastId":this.broadcastId
        },
        responseType: "blob"
      })
      .then((res)=> {
        console.log("res : ", res);
        const url = window.URL.createObjectURL(new Blob([res.data], { type: res.headers['content-type'] }));
        let fileName = this.getFileName(res.headers['content-disposition'])
        
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', fileName);
        link.click();

      }).catch((err)=> {
        console.log(err);
      })
    },
    getFileName (contentDisposition) {
      let fileName = contentDisposition.split("=");
      return fileName[1];
    },
    login() {
      console.log("email : ", this.email);
      console.log("password : ", this.password);

      var body = {
        "email":this.email,
        "password":this.password
      }

      axios.post("http://localhost:8080/user/login", body)
      .then((res)=> {
        console.log(res);
        console.log(res.headers.accesstoken);
      }).catch((err)=> {
        console.log(err);
      })
    },
    dateSelect() {
      console.log(this.picker);
      // var date = {
      //   photoUrl:"url입니다",
      //   scheduleDate: this.picker,
      // }
      // axios.post(`http://localhost:8080/schedule`, date)
      // .then((res)=>{
      //   console.log(res.data);
      // }).catch((err)=> {
      //   console.log(err);
      // })

      axios.get("http://localhost:8080/schedule/"+this.picker)
      .then((res)=> {
        console.log(res.data);
      }).catch((err)=> {
        console.log(err);
      })
    },  
    upload() { // 이미지 업로드 관련 함수
      console.log(this.$refs.files.files);
      console.log(this.$refs.files.files[0]);
      const formDate = new FormData();
      formDate.append("excelFile", this.$refs.files.files[0]);
      axios.patch(`http://localhost:8080/user/list`, formDate, {
        headers: {
          "Content-Type": `multipart/form-data`,
        }
      })
      .then((res)=>{
        console.log(res.data);
        alert("성공");
      }).catch((err)=>{
        alert("실패")
        console.log(err);
      })
    },
    send() {
      // console.log(putFile.upload(this.sendList));
      // console.log("백에 ");
      // console.log("보낼");
      // console.log("게유");

    },
    // senddd() { // 게시글 등록을 위해 백으로 게시글 정보 보내는 함수
    //   const formData = new FormData();
    //   for (var index=0;index<this.sendList.length;index++) {  // 등록할 사진들 저장
    //     formData.append('files', this.sendList[index]);
    //   }
    //   this.sendList=[]; // formData에 append 후 이미지 리스트 비워주기
    //   formData.append('categori', this.categori); // 카테고리 
    //   formData.append('content', this.content); // 내용
    //   formData.append('userId', this.userId); // 유저 아이디
    //   axios.post('http://localhost:8080/post/regist', formData, {
    //     headers: {
    //       'Content-Type': 'multipart/form-data'
    //     }
    //   })
    //   .then((response) => {
    //     alert("보냈슴!");
    //     console.log(response.data);
    //   })
    //   .catch((error) => {
    //     alert("못보냈슴!");
    //     console.log(error);
    //   })
    // }
  },
};
</script>
