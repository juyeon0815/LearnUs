import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import Account from '@/views/Account.vue'
import Profile from '@/views/Profile.vue'

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
