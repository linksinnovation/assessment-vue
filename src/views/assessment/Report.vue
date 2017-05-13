<template>
  <div id="report">
  <h3>รายงาน</h3>

  <div class="form-group">
          <div class="col-md-12 text-center">
            <button type="button" class="btn btn-raised btn-success">ดาวน์โหลดข้อมูล</button>
          </div>
        </div>

  <div class="table-responsive">
                      <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th>#</th>
                            <th>Location</th>
                            <th>กลุ่มธุรกิจ/กลุ่มงาน</th>
                            <th>ด้าน</th>
                            <th>ฝ่าย</th>
                            <th>สัดส่วนการมาทำแบบทดสอบ</th>
                            <th>สัดส่วนการผ่านแบบทดสอบ</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="(report,index) in data">
                            <td>{{index+1}}</td>
                            <td>{{report.organizeName}}</td>
                            <td>{{report.businessName}}</td>
                            <td>{{report.areaName}}</td>
                            <td>{{report.partyName}}</td>
                            <td>{{((report.partyDone/report.partyTotal)*100).toFixed(2)}}% ({{report.partyDone}}/{{report.partyTotal}})</td>
                            <td>{{((report.partyPass/report.partyTotal)*100).toFixed(2)}}% ({{report.partyPass}}/{{report.partyTotal}})</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
  </div>
</template>

<script>
import http from '@/api/common/http'

export default {
  name: 'report',
  data: function () {
    return {
      id: this.$route.params.id,
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
        .get('/api/report/' + this.id)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    }
  }
}
</script>

<style lang="scss">
#report {
  .table-responsive {
    width: 100%;
    margin-bottom: 15px;
    overflow-y: hidden;
    -ms-overflow-style: -ms-autohiding-scrollbar;
    border: 1px solid #ddd;
  }
  .table-responsive > .table > thead > tr > th, .table-responsive > .table > tbody > tr > th, .table-responsive > .table > tfoot > tr > th, .table-responsive > .table > thead > tr > td, .table-responsive > .table > tbody > tr > td, .table-responsive > .table > tfoot > tr > td{
    white-space: nowrap;
  }
}
</style>
