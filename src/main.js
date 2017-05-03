import Vue from 'vue'
import { sync } from 'vuex-router-sync'

import App from './App'
import router from './router'
import store from './store'

import '../node_modules/bootstrap/dist/js/bootstrap.js'

Vue.config.productionTip = false

sync(store, router)
store.dispatch('checkAuth', router)

/* eslint-disable no-new */
new Vue(Vue.util.extend({
  router,
  store
}, App)).$mount('#app')
