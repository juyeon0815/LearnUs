import { createRouter, createWebHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import Account from '@/views/Account.vue'

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index
  },
  {
    path: '/account',
    name: 'Account',
    component: Account
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
