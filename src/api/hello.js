const HELLO_URL = '/api/hello'

import http from './common/http'

export default {
  get: function () {
    return http.get(HELLO_URL)
  }
}
