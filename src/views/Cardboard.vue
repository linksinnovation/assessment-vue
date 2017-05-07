<template>
  <div class="container mt-2">
  
    <card v-for="item of data" :props="item" :key="item.id"></card>
  
  </div>
</template>

<script>
import http from '@/api/common/http'
import Card from '@/components/Card'

export default {
  name: 'cardboard',
  components: {
    Card
  },
  data: function () {
    return {
      data: []
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
        .get('/api/assessment')
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    }
  }
}
</script>
