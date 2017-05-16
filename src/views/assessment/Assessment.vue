<template>
  <div id="assessment">
  
    <div class="alert alert-danger alert-light alert-dismissible" role="alert" v-if="number === -1 && !warn">
      <i class="zmdi zmdi-close-circle"></i> คุณทำแบบทดสอบครบจำนวนในการทำซ้ำ
    </div>
  
    <div class="row">
  
      <div class="form-group" v-if="number === -1">
        <div class="col-md-12">
          {{data.objective}}
        </div>
      </div>
  
      <div class="form-group" v-if="number === -1">
        <div class="col-md-12 text-center">
          <button type="button" id="start-button" class="btn btn-raised btn-success" v-on:click="startAssessment">เริ่มทำแบบประเมิน</button>
        </div>
      </div>
  
      <div class="form-group" v-for="(question,index) in data.questions" v-if="index === number">
        <div class="col-md-12">
          <strong>{{index+1}}. {{question.question}}</strong>
        </div>
        <div class="input-group col-md-11 col-md-offset-1" v-for="(answer,subIndex) in question.answers">
          <span class="input-group-addon"><input type="radio" :name="'answer-radio-'+index" v-on:click="selected(question.id,subIndex,index)"></span>
          <span>{{answer.answer}}</span>
        </div>
        <div class="col-md-12 text-center">
          <button type="button" :id="'next-button-'+index" class="btn btn-raised btn-primary" v-on:click="nextAssessment" v-if="number < (data.questions.length-1)" disabled>ข้อถัดไป</button>
          <button type="button" :id="'next-button-'+index" class="btn btn-raised btn-primary" v-on:click="sendAssessment" v-if="number === (data.questions.length-1)" disabled>สิ้นสุดการทำแบบประเมิน</button>
        </div>
      </div>
  
      <div class="form-group" v-for="(item,index) in answers" v-if="number === data.questions.length">
  
        <div class="alert alert-danger alert-light alert-dismissible" role="alert" v-if="index == 0 && !pass">
          <i class="zmdi zmdi-close-circle"></i> สามารถเรียนรู้เพื่อสร้างความรู้ ความเข้าใจเรื่อง Integrity จาก VDO
          <router-link :to="{path: '/assessment/'+id+'/course'}">เพียงกดปุ่มนี้</router-link>
        </div>
  
        <div class="alert alert-danger alert-dismissible" role="alert" v-if="index == 0 && !pass">
          <i class="zmdi zmdi-close-circle"></i> คุณไม่ผ่านแบบประเมิน
        </div>
  
        <div class="alert alert-success alert-dismissible" role="alert" v-if="index == 0 && pass">
          <i class="zmdi zmdi-check"></i> ยินดีด้วย! คุณผ่านแบบประเมิน
        </div>
  
        <div class="col-md-12">
          <strong>{{index+1}}. {{item.question.question}}</strong>
        </div>
        <div class="input-group col-md-11 col-md-offset-1">
          <div v-for="(answer,subIndex) in item.question.answers">
            <span style="color:red" v-if="!item.checked && subIndex === item.selected">{{answer.answer}}</span>
            <span style="color:green" v-if="item.checked && subIndex === item.selected">{{answer.answer}}</span>
          </div>
        </div>
        <div class="col-md-12 text-center" v-if="answers.length === index+1">
          <button type="button" class="btn btn-raised btn-primary" v-on:click="restartAssessment">เริ่มทำแบบประเมินใหม่</button>
        </div>
  
      </div>
    </div>
  </div>
</template>

<script>
/* global $ */
import http from '@/api/common/http'

export default {
  name: 'assessment',
  data: function () {
    return {
      id: this.$route.params.id,
      number: -1,
      data: {},
      answers: [],
      pass: true,
      warn: true,
      break: false
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
          if (data.assessmentUsers.length >= data.repeatTime && data.repeatTime !== 0) {
            $('#start-button').prop('disabled', true)
            self.$set(self, 'warn', false)
          }
          self.$set(self, 'data', data)
        })
    },
    startAssessment: function () {
      this.$set(this, 'number', 0)
    },
    nextAssessment: function () {
      this.break = false
      this.$set(this, 'number', this.number + 1)
    },
    selected: function (question, select, index) {
      if (!this.break) {
        $('#next-button-' + index).prop('disabled', false)
        this.answers.push({ question: question, selected: select })
        this.break = true
      }
    },
    sendAssessment: function () {
      var self = this
      this.break = false
      this.$set(this, 'number', this.number + 1)
      http.post('/api/question/' + this.id, this.answers)
        .done(function (data) {
          self.$set(self, 'answers', data)
          var score = 0
          for (var i = 0, len = data.length; i < len; i++) {
            if (data[i].checked) {
              score++
            }
          }
          if ((score / data.length) * 100 >= self.data.passScore) {
            self.$set(self, 'pass', true)
          } else {
            self.$set(self, 'pass', false)
          }
        })
    },
    restartAssessment: function () {
      this.$set(this, 'number', -1)
      this.$set(this, 'answers', [])
      this.fetchData()
    }
  }
}
</script>
