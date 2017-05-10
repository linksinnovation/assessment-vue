import Vue from 'vue'
import VueVideoPlayer from 'vue-video-player'
import { sync } from 'vuex-router-sync'

import App from './App'
import router from './router'
import store from './store'

import '../node_modules/bootstrap/dist/js/bootstrap.js'
import '../node_modules/bootstrap-select/dist/js/bootstrap-select.js'
import '../node_modules/bootstrap-datepicker/dist/js/bootstrap-datepicker.js'
import '../node_modules/bootstrap-material-design/dist/js/material.js'
import '../node_modules/bootstrap-material-design/dist/js/ripples.js'
import '@/assets/app.js'

Vue.config.productionTip = false

Vue.use(VueVideoPlayer)

sync(store, router)
store.dispatch('checkAuth', router)

/* global $ */
$.material.init()

/* eslint-disable no-new */
new Vue(Vue.util.extend({
  router,
  store
}, App)).$mount('#app')
