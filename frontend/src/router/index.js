import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import OnAir from '@/views/onAir/OnAir.vue'
import ManageStudent from '@/views/admin/ManageStudent.vue'
import ManageSettings from '@/views/admin/ManageSettings.vue'
import Account from '@/views/Account.vue'
import Profile from '@/views/Profile.vue'
import LiveSchedule from '@/views/admin/LiveSchedule.vue'
import StudentDetail from '@/views/admin/StudentDetail.vue'
import CreateLive from '@/views/admin/CreateLive.vue'

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
  },
  {
    path: '/on-air/:id',
    name: 'OnAir',
    component: OnAir
  },
  {
    path: '/admin/students',
    name: 'ManageStudent',
    component: ManageStudent
  },
  {
    path: '/admin/students/:id',
    name: 'StudentDetail',
    component: StudentDetail
  },
  {
    path: '/admin/settings',
    name: 'ManageSettings',
    component: ManageSettings
  },
  {
    path: '/admin/create-live',
    name: 'CreateLive',
    component: CreateLive
  },
  {
    path: '/account/:page',
    name: 'Account',
    component: Account
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/admin/live-schedule',
    name: 'LiveSchedule',
    component: LiveSchedule
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
