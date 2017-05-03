/* global localStorage */

import auth from '../../api/auth'

const state = {
  authenticated: false
}

const getters = {
  authenticated: (state) => {
    return state.authenticated
  }
}

const mutations = {
  OAUTH_AUTHEN: (state, status) => {
    state.authenticated = status
  }
}

const actions = {
  login: (context, vc) => {
    auth.login(vc).then((data) => {
      localStorage.setItem('at', data.body.access_token)
      localStorage.setItem('rt', data.body.refresh_token)
      context.commit('OAUTH_AUTHEN', true)
      vc.$router.push('/')
    }, (data) => {
      context.commit('OAUTH_AUTHEN', false)
    })
  },
  logout: (context, vc) => {
    localStorage.removeItem('at')
    localStorage.removeItem('rt')
    context.commit('OAUTH_AUTHEN', false)
    vc.$router.replace('/login')
  },
  checkAuth: (context, router) => {
    let rt = localStorage.getItem('at')
    if (rt) {
      auth.checkAuth(rt).done((data, textStatus, jqXHR) => {
        context.commit('OAUTH_AUTHEN', true)
      }).fail((jqXHR, textStatus, errorThrown) => {
        context.commit('OAUTH_AUTHEN', false)
        router.replace('/login')
      })
    }
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
