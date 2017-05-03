/* global $,localStorage */

import router from '../../router'

export default {
  get: function(path) {
    return $.ajax({
      url: path,
      headers: {
        Authorization: 'bearer ' + localStorage.getItem('at')
      }
    }).fail((jqXHR, textStatus, errorThrown) => {
      router.replace('/login')
    })
  }
}
