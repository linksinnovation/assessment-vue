/* global localStorage */

import auth from '../../api/auth'
import jwt from 'jwt-decode'

const state = {
  authenticated: false,
  name: '',
  authority: ''
}

const getters = {
  authenticated: (state) => {
    return state.authenticated
  },
  name: (state) => {
    return state.name
  },
  authority: (state) => {
    return state.authority
  }
}

const mutations = {
  OAUTH_AUTHEN: (state, payload) => {
    state.authenticated = payload.status
    state.name = payload.name
    state.authority = payload.authority
  }
}

const actions = {
  login: (context, vc) => {
    auth.login(vc).then((data) => {
      localStorage.setItem('at', data.body.access_token)
      localStorage.setItem('rt', data.body.refresh_token)
      context.commit('OAUTH_AUTHEN', { status: true, name: jwt(data.body.access_token).name, authority: jwt(data.body.access_token).authorities[0] })
      vc.$router.push('/')
    }, (data) => {
      context.commit('OAUTH_AUTHEN', { status: false })
    })
  },
  logout: (context, vc) => {
    localStorage.removeItem('at')
    localStorage.removeItem('rt')
    context.commit('OAUTH_AUTHEN', { status: false })
    vc.$router.replace('/login')
  },
  checkAuth: (context, router) => {
    let rt = localStorage.getItem('at')
    if (rt) {
      auth.checkAuth(rt).done((data, textStatus, jqXHR) => {
        context.commit('OAUTH_AUTHEN', { status: true, name: jwt(rt).name, authority: jwt(rt).authorities[0] })
      }).fail((jqXHR, textStatus, errorThrown) => {
        context.commit('OAUTH_AUTHEN', { status: false })
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
