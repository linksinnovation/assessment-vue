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
  
    <barChart :chartData="doneChart" v-if="doneChart != null && selector.view != ''"></barChart>
    <barChart :chartData="passChart" v-if="passChart != null && selector.view != ''"></barChart>
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
  methods: {
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
      var passChartScore = []
      for (var i = 0; i < data.length; i++) {
        label.push(data[i].name)
        doneChartScore.push(((data[i].done / data[i].total) * 100).toFixed(2))
        passChartScore.push(((data[i].pass / data[i].total) * 100).toFixed(2))
      }

      var doneChart = {
        labels: label,
        datasets: [
          {
            label: 'สัดส่วนการเข้ามาทำแบบทดสอบ',
            backgroundColor: '#f87979',
            borderWidth: 1,
            data: doneChartScore
          }
        ]
      }

      var passChart = {
        labels: label,
        datasets: [
          {
            label: 'สัดส่วนการทำแบบทดสอบผ่าน',
            backgroundColor: '#f87979',
            borderWidth: 1,
            data: passChartScore
          }
        ]
      }

      this.$set(this, 'doneChart', doneChart)
      this.$set(this, 'passChart', passChart)
    }
  }
}
</script>
