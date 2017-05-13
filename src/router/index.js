import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'

import Main from '@/views/Main'
import Login from '@/views/Login'
import Cardboard from '@/views/Cardboard'
import Paper from '@/views/Paper'
import PrePaper from '@/views/PrePaper'
import Assessment from '@/views/assessment/Assessment'
import Setting from '@/views/assessment/Setting'
import PreSetting from '@/views/assessment/PreSetting'
import Manage from '@/views/assessment/Manage'
import ManageCourse from '@/views/assessment/ManageCourse'
import ImportData from '@/views/assessment/ImportData'
import Course from '@/views/assessment/Course'
import Cover from '@/views/assessment/Cover'
import QrCode from '@/views/assessment/QrCode'
import Report from '@/views/assessment/Report'
import Dashboard from '@/views/assessment/Dashboard'

import Permission from '@/views/Permission'
import PermissionCard from '@/views/permission/PermissionCard.vue'

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
      path: '/permission',
      component: Permission,
      children: [{
        path: '',
        component: PermissionCard
      }]
    }, {
      path: '/assessment',
      component: PrePaper,
      children: [{
        path: '',
        component: PreSetting
      }]
    }, {
      path: '/assessment/:id(\\d+)',
      component: Paper,
      children: [{
        path: 'assessment',
        component: Assessment
      }, {
        path: 'course',
        component: Course
      }, {
        path: 'setting',
        component: Setting
      }, {
        path: 'manage',
        component: Manage
      }, {
        path: 'managecourse',
        component: ManageCourse
      }, {
        path: 'cover',
        component: Cover
      }, {
        path: 'import',
        component: ImportData
      }, {
        path: 'report',
        component: Report
      }, {
        path: 'dashboard',
        component: Dashboard
      }, {
        path: 'qrcode',
        component: QrCode
      }]
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
