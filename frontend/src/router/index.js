import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import OnAir from '@/views/onAir/OnAir.vue'
import ManageStudent from '@/views/admin/ManageStudent.vue'
import Account from '@/views/Account.vue'
import Profile from '@/views/Profile.vue'
import LiveSchedule from '@/views/admin/LiveSchedule.vue'

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
    path: '/live-schedule',
    name: 'LiveSchedule',
    component: LiveSchedule
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
