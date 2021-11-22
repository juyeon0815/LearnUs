import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'
import Index from '@/views/Index.vue'
import OnAir from '@/views/onAir/OnAir.vue'
import OnAirStudio from '@/views/onAir/OnAirStudio.vue'
import ManageStudent from '@/views/admin/ManageStudent.vue'
import ManageSettings from '@/views/admin/ManageSettings.vue'
import ManageVideo from '@/views/admin/ManageVideo.vue'
import VideoHistory from '@/views/admin/VideoHistory.vue'
import Account from '@/views/account/Login.vue'
import Profile from '@/views/account/Profile.vue'
import LiveSchedule from '@/views/broadcast/LiveSchedule.vue'
import CreateLive from '@/views/admin/CreateLive.vue'
import ErrorPage from '@/views/Error.vue'

import Replay from '@/views/replay/Replay.vue'
import ReplayDetail from '@/views/replay/ReplayDetail.vue'

const routes = [
  // 비로그인 사용자
  {
    path: '/account/:page',
    name: 'Account',
    component: Account
  },
  { 
    path: '/:pathMatch(.*)*', 
    redirect: '/error/404'
  },
  {
    path: '/error/:code',
    name: 'Error',
    component: ErrorPage
  },
  // 로그인 사용자
  {
    path: '/',
    name: 'Index',
    component: Index,
    meta: { requireAuth: true }
  },
  {
    path: '/live/:id',
    name: 'OnAir',
    component: OnAir,
    meta: { requireAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    meta: { requireAuth: true }
  },
  {
    path: '/broadcast/schedule',
    name: 'LiveSchedule',
    component: LiveSchedule,
    meta: { requireAuth: true }
  },
  {
    path: '/replay/:track',
    name: 'Replay',
    component: Replay,
    meta: { requireAuth: true }
  },
  {
    path: '/replay/video/:id',
    name: 'ReplayDetail',
    component: ReplayDetail,
    meta: { requireAuth: true }
  },
  // 관리자
  {
    path: '/live/studio/:id',
    name: 'OnAirStudio',
    component: OnAirStudio,
    meta: { requireAuth: true }
  },
  {
    path: '/admin/video/:id',
    name: 'ManageVideo',
    component: ManageVideo,
    meta: { requireAuth: true }
  },
  {
    path: '/admin/create/live',
    name: 'CreateLive',
    component: CreateLive,
    meta: { requireAuth: true }
  },
  {
    path: '/admin/students',
    name: 'ManageStudent',
    component: ManageStudent,
    meta: { requireAuth: true }
  },
  {
    path: '/admin/settings',
    name: 'ManageSettings',
    component: ManageSettings,
    meta: { requireAuth: true }
  },
  {
    path: '/admin/history',
    name: 'VideoHistory',
    component: VideoHistory,
    meta: { requireAuth: true }
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(function (to, from, next) {
  if (to.matched.some(function(routeInfo) {
    return routeInfo.meta.requireAuth
  })) {
    if (!store.state.account.accessToken) {
      next('/account/login')
    } else {
      if (
        to.name === 'OnAirStudio' ||
        to.name === 'ManageVideo' ||
        to.name === 'CreateLive' ||
        to.name === 'ManageStudent' ||
        to.name === 'ManageSettings' ||
        to.name === 'VideoHistory'
      ) {
        if (store.state.account.userInfo.statusCode === 'A') {
          next()
        } else {
          next('/')
        }
      } else {
        next()
      }
    }
  } else {
    if (to.name === 'Account') {
      if (store.state.account.accessToken) {
        next('/')
      } else {
        next()
      }
    } else {
      next()
    }
  }
  
})

export default router
