<template>
  <form class="form-horizontal" autocomplete="off" v-on:submit.prevent="onSave">
    <fieldset>
      <legend>เพิ่มแบบประเมิน</legend>
      <div class="form-group">
        <label for="title" class="col-md-2 control-label">หัวข้อ</label>
        <div class="col-md-10">
          <input type="text" class="form-control" id="title" v-model="data.title" required>
        </div>
      </div>
      <div class="form-group">
        <label for="textArea" class="col-md-2 control-label">รายละเอียด</label>
        <div class="col-md-10">
          <textarea class="form-control" rows="5" id="textArea" v-model="data.detail" required></textarea>
        </div>
      </div>
      <div class="form-group">
        <label for="textArea" class="col-md-2 control-label">วัตถุประสงค์</label>
        <div class="col-md-10">
          <textarea class="form-control" rows="5" id="textArea" v-model="data.objective" required></textarea>
        </div>
      </div>
      <div class="form-group">
        <label for="startDate" class="col-md-2 control-label">วันเริ่มต้น</label>
        <div class="col-md-10">
          <input id="startDate" type="text" class="form-control" v-model="data.startDate"> </div>
      </div>
      <div class="form-group">
        <label for="endDate" class="col-md-2 control-label">วันสิ้นสุด</label>
        <div class="col-md-10">
          <input id="endDate" type="text" class="form-control" v-model="data.endDate"> </div>
      </div>
      <div class="form-group">
        <label for="number" class="col-md-2 control-label">จำนวนครั้ง</label>
        <div class="col-md-10">
          <input type="number" min="0" class="form-control" id="number" v-model="data.repeatTime">
        </div>
      </div>
      <div class="form-group">
        <label for="passscore" class="col-md-2 control-label">เกณฑ์การผ่าน</label>
        <div class="col-md-10">
          <input type="number" min="0" max="100" class="form-control" id="passscore" v-model="data.passScore">
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-10 col-md-offset-2">
          <div class="checkbox">
            <label>
              <input type="checkbox" v-model="data.showResult"><span class="checkbox-material"><span class="check"></span></span> แสดงผลการทำแบบประเมิน </label>
          </div>
        </div>
      </div>
  
      <div class="form-group">
        <div class="col-md-10 col-md-offset-2">
          <button type="submit" class="btn btn-raised btn-primary">บันทึกแบบประเมิน</button>
        </div>
      </div>
    </fieldset>
  </form>
</template>

<script>
/* global $ */

import http from '@/api/common/http'

export default {
  name: 'presetting',
  data: function () {
    return {
      data: {
        title: '',
        detail: '',
        startDate: '',
        endDate: '',
        repeatTime: 0,
        passScore: 0
      }
    }
  },
  methods: {
    onSave: function () {
      var self = this
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$router.push({ path: '/assessment/' + data.id + '/setting' })
        })
    }
  },
  mounted: function () {
    $('#startDate')
      .datepicker({ format: 'dd/mm/yyyy', orientation: 'bottom left', autoclose: !0, todayHighlight: !0 })
      .on('changeDate', () => { this.data.startDate = $('#startDate').val() })
    $('#endDate')
      .datepicker({ format: 'dd/mm/yyyy', orientation: 'bottom left', autoclose: !0, todayHighlight: !0 })
      .on('changeDate', () => { this.data.endDate = $('#endDate').val() })
  }
}
</script>
