<template>
  <div id="dashboard">
  
    <form class="form-horizontal">
      <div class="form-group">
        <label class="col-md-2 control-label">มุมมอง</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.view" v-on:change="selectorChange('view')">
            <option value="">เลือกมุมมอง</option>
            <option value="location">Location</option>
            <option value="overview">ภาพรวม</option>
          </select>
        </div>
      </div>
  
      <div class="form-group" v-if="selector.view != '' && selector.view == 'location'">
        <label class="col-md-2 control-label">Location</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.organize" v-on:change="selectorChange('organize')">
            <option value="">เลือก Location</option>
            <option v-for="(option,index) in selection.organize" :value="option.name">{{option.name}}</option>
          </select>
        </div>
      </div>
  
      <div class="form-group" v-if="selector.view != '' && selector.view == 'overview'">
        <label class="col-md-2 control-label">ภาพรวม</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.organize" v-on:change="selectorChange('organize')">
            <option value="">เลือก ภาพรวม</option>
            <option v-for="(option,index) in selection.organize" :value="option.name">{{option.name}}</option>
          </select>
        </div>
      </div>
  
      <div class="form-group" v-if="selector.organize != ''">
        <label class="col-md-2 control-label">กลุ่มธุรกิจ</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.business" v-on:change="selectorChange('business')">
            <option value="">เลือก กลุ่มธุรกิจ</option>
            <option v-for="(option,index) in selection.business" :value="option.name">{{option.name}}</option>
          </select>
        </div>
      </div>
  
      <div class="form-group" v-if="selector.business != ''">
        <label class="col-md-2 control-label">กลุ่มงาน</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.group" v-on:change="selectorChange('group')">
            <option value="">เลือก กลุ่มงาน</option>
            <option v-for="(option,index) in selection.group" :value="option.name">{{option.name}}</option>
          </select>
        </div>
      </div>
  
      <div class="form-group" v-if="selector.group != ''">
        <label class="col-md-2 control-label">ด้าน</label>
        <div class="col-md-10">
          <select class="form-control" v-model="selector.area" v-on:change="selectorChange('area')">
            <option value="">เลือก ด้าน</option>
            <option v-for="(option,index) in selection.area" :value="option.name">{{option.name}}</option>
          </select>
        </div>
      </div>
  
    </form>
  
    <div class="row">
      <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="panel panel panel-info panel-dark text-center">
          <div class="panel-body">
            <h2 class="counter">{{sumChart.total}}</h2>
            <i class="fa fa-4x fa-group"></i>
            <p class="mt-2">ทั้งหมด</p>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="panel panel panel-warning panel-dark text-center">
          <div class="panel-body">
            <h2 class="counter">{{sumChart.done}}</h2>
            <i class="fa fa-4x fa-file-text"></i>
            <p class="mt-2">ทำแบบทดสอบ</p>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="panel panel panel-success panel-dark text-center">
          <div class="panel-body">
            <h2 class="counter">{{sumChart.pass}}</h2>
            <i class="fa fa-4x fa-check-square-o"></i>
            <p class="mt-2">ผ่านแบบทดสอบ</p>
          </div>
        </div>
      </div>
      <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="panel panel panel-danger panel-dark text-center">
          <div class="panel-body">
            <h2 class="counter">{{sumChart.notpass}}</h2>
            <i class="fa fa-4x fa-times-circle-o"></i>
            <p class="mt-2">ไม่ผ่านแบบทดสอบ</p>
          </div>
        </div>
      </div>
    </div>
  
    <barChart title="จำนวนผู้ทำแบบทดสอบ" :chartData="doneChart" v-if="doneChart != null && selector.view != ''"></barChart>
    <barChart title="จำนวนผู้ผ่านแบบทดสอบ" :chartData="passChart" v-if="passChart != null && selector.view != ''"></barChart>
  </div>
</template>

<script>
import BarChart from '@/components/BarChart'
import http from '@/api/common/http'

export default {
  name: 'dashboard',
  components: {
    BarChart
  },
  data: function () {
    return {
      id: this.$route.params.id,
      doneChart: null,
      passChart: null,
      sumChart: {
        total: 0,
        done: 0,
        pass: 0,
        notpass: 0
      },
      selection: {
        organize: [],
        business: [],
        group: [],
        area: []
      },
      selector: {
        view: '',
        organize: '',
        business: '',
        group: '',
        area: ''
      }
    }
  },
  created: function () {
    this.initData()
  },
  watch: {
    '$route': 'initData'
  },
  methods: {
    initData: function () {
      var self = this
      var selector = {
        view: 'overview',
        organize: '',
        business: '',
        group: '',
        area: ''
      }
      self.$set(self, 'selector', selector)
      http
        .post('/api/dashboard/location/' + this.id, selector)
        .done(function (data) {
          self.$set(self.selection, 'organize', data)
          self.calculateChart(data)
        })
    },
    fetchData: function (select) {
      var self = this
      var selector = {}

      if (select === 'view') {
        selector = {
          view: this.selector.view,
          organize: '',
          business: '',
          group: '',
          area: ''
        }
        self.$set(self, 'selector', selector)
        http
          .post('/api/dashboard/location/' + this.id, this.selector)
          .done(function (data) {
            self.$set(self.selection, 'organize', data)
            self.calculateChart(data)
          })
      } else if (select === 'organize') {
        selector = {
          view: this.selector.view,
          organize: this.selector.organize,
          business: '',
          group: '',
          area: ''
        }
        self.$set(self, 'selector', selector)
        http
          .post('/api/dashboard/location/' + this.id, this.selector)
          .done(function (data) {
            self.$set(self.selection, 'business', data)
            self.calculateChart(data)
          })
      } else if (select === 'business') {
        selector = {
          view: this.selector.view,
          organize: this.selector.organize,
          business: this.selector.business,
          group: '',
          area: ''
        }
        self.$set(self, 'selector', selector)
        http
          .post('/api/dashboard/location/' + this.id, this.selector)
          .done(function (data) {
            self.$set(self.selection, 'group', data)
            self.calculateChart(data)
          })
      } else if (select === 'group') {
        selector = {
          view: this.selector.view,
          organize: this.selector.organize,
          business: this.selector.business,
          group: this.selector.group,
          area: ''
        }
        self.$set(self, 'selector', selector)
        http
          .post('/api/dashboard/location/' + this.id, this.selector)
          .done(function (data) {
            self.$set(self.selection, 'area', data)
            self.calculateChart(data)
          })
      } else if (select === 'area') {
        selector = {
          view: this.selector.view,
          organize: this.selector.organize,
          business: this.selector.business,
          group: this.selector.group,
          area: this.selector.area
        }
        self.$set(self, 'selector', selector)
        http
          .post('/api/dashboard/location/' + this.id, this.selector)
          .done(function (data) {
            self.$set(self.selection, 'party', data)
            self.calculateChart(data)
          })
      }
    },
    selectorChange: function (select) {
      this.fetchData(select)
    },
    calculateChart: function (data) {
      var label = []
      var doneChartScore = []
      var doneChartTotal = []
      var passChartScore = []
      var passChartTotal = []

      this.sumChart = {
        total: 0,
        done: 0,
        pass: 0,
        notpass: 0
      }

      for (var i = 0; i < data.length; i++) {
        label.push(data[i].name)
        // doneChartScore.push(((data[i].done / data[i].total) * 100).toFixed(2))
        // passChartScore.push(((data[i].pass / data[i].total) * 100).toFixed(2))
        doneChartScore.push(data[i].done)
        doneChartTotal.push(data[i].total)
        passChartScore.push(data[i].pass)
        passChartTotal.push(data[i].done)

        this.sumChart.total += data[i].total
        this.sumChart.done += data[i].done
        this.sumChart.pass += data[i].pass
        this.sumChart.notpass += (data[i].done - data[i].pass)
      }

      var doneChart = {
        labels: label,
        datasets: [
          {
            label: 'ผู้เข้ามาทำแบบทดสอบ',
            backgroundColor: '#4CAF50',
            borderWidth: 1,
            data: doneChartScore
          },
          {
            label: 'ทั้งหมด',
            backgroundColor: '#f87979',
            borderWidth: 1,
            data: doneChartTotal
          }
        ]
      }

      var passChart = {
        labels: label,
        datasets: [
          {
            label: 'ผู้ผ่านแบบทดสอบ',
            backgroundColor: '#4CAF50',
            borderWidth: 1,
            data: passChartScore
          },
          {
            label: 'ทั้งหมด',
            backgroundColor: '#f87979',
            borderWidth: 1,
            data: passChartTotal
          }
        ]
      }

      this.$set(this, 'doneChart', doneChart)
      this.$set(this, 'passChart', passChart)
      this.$set(this, 'sumChart', this.sumChart)
    }
  }
}
</script>
