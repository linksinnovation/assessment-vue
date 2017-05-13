<template>
  <div id="qr-code">
    <h3>QR Code</h3>
    <div class="panel panel-default">
      <div class="panel-body">
  
          <div class="col-md-12 text-center" v-if="data.id">
            <img :src="'/images/qrcode-'+data.id+'.jpg'" >
          </div>
  
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/api/common/http'

export default {
  name: 'qrCode',
  data: function () {
    return {
      id: this.$route.params.id,
      data: {}
    }
  },
  created: function () {
    this.fetchData()
  },
  watch: {
    '$route': 'fetchData'
  },
  methods: {
    fetchData: function () {
      var self = this
      http
        .get('/api/assessment/' + this.id)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    }
  }
}
</script>
