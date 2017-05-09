<template>
  <div>
    <h3>จัดการแบบประเมิน</h3>
    <section class="ms-component-section">
      <div v-for="(question,index) in data.questions" class="panel-group ms-collapse" :id="'accordion-'+index" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
          <div class="panel-heading" role="tab" :id="'heading-'+index">
            <h4 class="panel-title ms-rotate-icon"><a class="collapsed" role="button" data-toggle="collapse" :data-parent="'#accordion-'+index" :href="'#collapse-'+index" aria-expanded="false" aria-controls="collapseOne"><i class="zmdi zmdi-attachment-alt"></i> {{question.question}}</a></h4>
          </div>
          <div :id="'collapse-'+index" class="panel-collapse collapse" role="tabpanel" :aria-labelledby="'heading-'+index" aria-expanded="false" style="height: 0px;">
            <div class="panel-body">
              <form class="form-horizontal" autocomplete="off">
                <div class="form-group">
                  <label class="col-md-1 control-label">คำถาม</label>
                  <div class="col-md-11">
                    <textarea class="form-control" rows="5" v-model="question.question" required></textarea>
                  </div>
                </div>
  
                <div class="form-group" v-for="(answer,subIndex) in question.answers">
                  <label class="col-md-1 control-label"></label>
                  <div class="input-group col-md-11">
                    <span class="input-group-addon"><input type="radio" name="answer-radio" v-model="question.checked"  :value="subIndex"></span>
                    <input type="text" class="form-control" v-model="answer.answer" required>
                    <span class="input-group-btn"><button class="btn btn-raised btn-default" type="button" v-on:click="removeAnswer(index, subIndex)">ลบคำตอบ</button></span>
                  </div>
                </div>
  
                <div class="form-group">
                  <div class="col-md-11 col-md-offset-1">
                    <button type="button" class="btn btn-raised btn-success" v-on:click="addAnswer(index)">เพิ่มคำตอบแบบประเมิน</button>
                    <button type="button" class="btn btn-raised btn-default" v-on:click="removeQuestion(index)">ลบแบบประเมิน</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-12">
          <button type="button" class="btn btn-raised btn-success" v-on:click="addQuestion()">เพิ่มแบบประเมิน</button>
          <button type="button" class="btn btn-raised btn-primary" v-on:click="onSave">บันทึกแบบประเมิน</button>
        </div>
      </div>
      <!-- panel-code -->
    </section>
  </div>
</template>

<script>
import http from '@/api/common/http'

export default {
  name: 'manage',
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
    },
    onSave: function () {
      var self = this
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    addQuestion: function () {
      this.data.questions.push({
        question: 'แบบประเมินใหม่',
        checked: 0,
        answers: [{
          answer: 'คำตอบ'
        }, {
          answer: 'คำตอบ'
        }]
      })
      this.$set(this, 'data', this.data)
    },
    removeQuestion: function (index) {
      this.data.questions.splice(index, 1)
      this.$set(this, 'data', this.data)
    },
    addAnswer: function (index) {
      this.data.questions[index].answers.push({
        answer: 'คำตอบ'
      })
      this.$set(this, 'data', this.data)
    },
    removeAnswer: function (index, subIndex) {
      this.data.questions[index].answers.splice(subIndex, 1)
      this.$set(this, 'data', this.data)
    }
  }
}
</script>
