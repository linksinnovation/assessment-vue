/* global $,FormData */

const OAUTH_TOKEN = '/oauth/token'
const OAUTH_CHECK = '/oauth/check_token?token='
const OAUTH_HASH = 'Y2xpZW50OnNlY3JldA=='

export default {
  login: function (context) {
    let form = new FormData()
    form.append('grant_type', 'password')
    form.append('username', context.credentials.username)
    form.append('password', context.credentials.password)

    let config = {
      headers: {
        'Authorization': 'Basic ' + OAUTH_HASH
      }
    }

    return context.$http.post(
      OAUTH_TOKEN,
      form,
      config
    )
  },
  checkAuth: function (at) {
    return $.ajax({
      url: OAUTH_CHECK + at,
      headers: {
        Authorization: 'Basic ' + OAUTH_HASH
      }
    })
  }
}
