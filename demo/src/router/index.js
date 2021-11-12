import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import recipe from "../views/recipe.vue";
import chat from "../views/chat.vue";
import About from "../views/About.vue";
import chatDetail from "../views/chatDetail.vue";
import AttendanceStart from "../views/AttendanceStart.vue";
import Attendance from "../views/Attendance.vue";
import Broadcast from "../views/broadcast.vue";
import BroadcastAdmin from "../views/broadcastAdmin.vue";
import QuizAdmin from "../views/QuizAdmin.vue";
import Quiz from "../views/Quiz.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/home",
    name: "Home",
    component: Home,
  },
  {
    path: "/about/:postId",
    name: "About",
    component: About,
  },
  {
    path: "/recipe",
    component: recipe,
  },
  {
    path:"/",
    component: chat,
  },
  {
    path:"/detail/:roomId/:roomName/:nickName",
    component: chatDetail,
  },
  {
    path:"/attend/start",
    component: AttendanceStart
  },
  {
    path:"/attend/:broadcastId",
    component: Attendance
  },
  {
    path:"/broadcast/:broadcastId",
    component: Broadcast
  },
  {
    path:"/broadcast/admin/:broadcastId",
    component: BroadcastAdmin
  },
  {
    path:"/quiz/start/:broadcastId",
    component: QuizAdmin
  },
  {
    path:"/quiz/:broadcastId",
    component: Quiz
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
