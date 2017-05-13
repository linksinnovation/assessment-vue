<template>
  <div id="import-data">
    <h3>นำเข้าข้อมูล</h3>
    <div class="panel panel-default">
      <div class="panel-body">
  
        <div class="alert alert-primary alert-light alert-dismissible" role="alert" v-if="data.organizeFile">
          <i class="zmdi zmdi-notifications"></i> {{data.organizeFile}}
        </div>
        <div class="form-group" v-if="files.file">
          <div class="col-md-12">
            <progressUpload :props="files.file" :url="files.url" :course="data.id"></progressUpload>
          </div>
        </div>
        <div class="form-group" v-if="!data.organizeFile">
          <div class="col-md-12 text-center is-fileinput">
            <input type="file" accept="text/csv" v-on:change="onBrowse('/api/csvupload',$event)">
            <button type="button" class="btn btn-raised btn-success">นำเข้าข้อมูลผังองค์กร</button>
          </div>
        </div>
  
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/api/common/http'
import ProgressUpload from '@/components/ProgressUpload'

export default {
  name: 'importData',
  components: {
    ProgressUpload
  },
  data: function () {
    return {
      id: this.$route.params.id,
      data: {},
      files: {}
    }
  },
  created: function () {
    this.fetchData()
    this.$on('onCompleteUpload', function (index) {
      var obj = {}
      obj[index] = undefined
      this.$set(this, 'files', obj)
      this.fetchData()
    })
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
    },
    onBrowse: function (url, e) {
      var obj = {}
      obj['file'] = e.target.files[0]
      obj['url'] = url
      this.$set(this, 'files', obj)
    }
  }
}
</script>
