import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'

import Main from '@/views/Main'
import Login from '@/views/Login'
import Cardboard from '@/views/Cardboard'
import Paper from '@/views/Paper'

Vue.use(Router)
Vue.use(VueResource)

const router = new Router({
  mode: 'history',
  routes: [{
    path: '/',
    component: Main,
    children: [{
      path: '',
      component: Cardboard
    }, {
      path: '/assessment',
      component: Paper
    }]
  }, {
    path: '/login',
    component: Login
  }]
})

router.beforeEach((to, from, next) => {
  let rt = localStorage.getItem('at')
  if (!rt && to.path !== '/login') {
    next('/login')
  } else {
    next()
  }
})

export default router
