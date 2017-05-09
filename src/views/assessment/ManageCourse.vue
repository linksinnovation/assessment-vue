<template>
  <div id="managecourse">
    <h3>จัดการสื่อการสอน</h3>
    <section class="ms-component-section">
  
      <div v-for="(section,index) in data.sections">
  
        <div class="row">
          <div class="col-md-12">
            <div class="panel-group ms-collapse" :id="'accordion-'+index" role="tablist" aria-multiselectable="true">
              <div class="panel panel-default">
                <div class="panel-heading" role="tab" :id="'heading-'+index">
                  <div class="btn-group pull-right" style="z-index: 1000">
                    <button class="btn btn-raised btn-primary btn-xs" v-on:click="addCourse(index)">เพิ่มสื่อการสอน</button>
                    <button class="btn btn-raised btn-warning btn-xs" v-on:click="removeSection(index)">ลบหมวดหมู่</button>
                  </div>
                  <h4 class="panel-title ms-rotate-icon"><a class="collapsed" role="button" data-toggle="collapse" :data-parent="'#accordion-'+index" :href="'#collapse-'+index" aria-expanded="false" :aria-controls="'#collapse-'+index"><i class="zmdi zmdi-attachment-alt"></i> {{section.name}}</a></h4>
                </div>
                <div :id="'collapse-'+index" class="panel-collapse collapse" role="tabpanel" :aria-labelledby="'heading-'+index" aria-expanded="false" style="height: 0px;">
                  <div class="panel-body">
                    <form class="form-horizontal" autocomplete="off" v-on:submit.prevent="onSave">
                      <div class="form-group">
                        <div class="input-group col-md-11  col-md-offset-1">
                          <input type="text" class="form-control" v-model="section.name" required>
                          <span class="input-group-btn"><button class="btn btn-raised btn-primary btn-sm" type="submit">บันทึก</button></span>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
  
        <div v-for="(course,subIndex) in section.courses" class="row">
          <div class="col-md-11 col-md-offset-1">
            <div class="panel-group ms-collapse" :id="'accordion-'+index+'-'+subIndex" role="tablist" aria-multiselectable="true">
              <div class="panel panel-default">
                <div class="panel-heading" role="tab" :id="'heading-'+index+'-'+subIndex">
                  <div class="btn-group pull-right" style="z-index: 1000">
                    <button class="btn btn-raised btn-warning btn-xs" v-on:click="removeCourse(index,subIndex,$event)">ลบสื่อการสอน</button>
                  </div>
                  <h4 class="panel-title ms-rotate-icon"><a class="collapsed" role="button" data-toggle="collapse" :data-parent="'#accordion-'+index+'-'+subIndex" :href="'#collapse-'+index+'-'+subIndex" aria-expanded="false" :aria-controls="'#collapse-'+index+'-'+subIndex"><i class="zmdi zmdi-attachment-alt"></i> {{course.name}}</a></h4>
                </div>
                <div :id="'collapse-'+index+'-'+subIndex" class="panel-collapse collapse" role="tabpanel" :aria-labelledby="'heading-'+index+'-'+subIndex" aria-expanded="false" style="height: 0px;">
                  <div class="panel-body">
                    <form class="form-horizontal" autocomplete="off" v-on:submit.prevent="onSave">
                      <div class="form-group">
                        <div class=" col-md-11  col-md-offset-1">
                          <div class="input-group">
                            <input type="text" class="form-control" v-model="course.name" required>
                            <span class="input-group-btn"><button class="btn btn-raised btn-primary btn-sm" type="submit">บันทึก</button></span>
                          </div>
                        </div>
                      </div>
  
                      <div class="alert alert-primary alert-light alert-dismissible" role="alert" v-if="course.content">
                        <i class="zmdi zmdi-notifications"></i> {{course.content}}
                      </div>
  
                      <div class="form-group" v-if="files['i'+index+'s'+subIndex] === undefined">
                        <div class="col-md-11  col-md-offset-1">
                          <div class="col-md-4 is-fileinput">
                            <input type="file" accept="video/*" v-on:change="onBrowse('/api/videoupload',index,subIndex,$event)">
                            <button type="button" class="btn btn-raised btn-info">VDO</button>
                          </div>
                          <div class="col-md-4 is-fileinput">
                            <input type="file" accept="application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf" v-on:change="onBrowse('/api/pdfupload',index,subIndex,$event)">
                            <button type="button" class="btn btn-raised btn-info">PDF</button>
                          </div>
                          <div class="col-md-4 is-fileinput">
                            <input type="file" accept="application/vnd.openxmlformats-officedocument.presentationml.presentation" v-on:change="onBrowse('/api/pptupload',index,subIndex,$event)">
                            <button type="button" class="btn btn-raised btn-info">PPT</button>
                          </div>
                        </div>
                      </div>
  
                      <div class="form-group" v-if="files['i'+index+'s'+subIndex] !== undefined">
                        <div class="col-md-12">
                          <progressUpload :props="files['i'+index+'s'+subIndex]" :url="files['url'+index+'s'+subIndex]" :course="course.id" :index="'i'+index+'s'+subIndex"></progressUpload>
                        </div>
                      </div>
  
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
  
      </div>
      <div class="form-group">
        <div class="col-md-12">
          <button type="button" class="btn btn-raised btn-success" v-on:click="addSection()">เพิ่มหมวดหมู่สื่อการสอน</button>
        </div>
      </div>
  
      <!-- panel-code -->
    </section>
  </div>
</template>

<script>
import http from '@/api/common/http'
import ProgressUpload from '@/components/ProgressUpload'

export default {
  name: 'managecourse',
  components: {
    ProgressUpload
  },
  data: function () {
    return {
      id: this.$route.params.id,
      data: {},
      files: {
      }
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
    onSave: function () {
      var self = this
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    addSection: function () {
      var self = this
      this.data.sections.push({
        name: 'หมวดหมู่ใหม่',
        courses: []
      })
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    removeSection: function (index) {
      var self = this
      this.data.sections.splice(index, 1)
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    addCourse: function (index) {
      var self = this
      this.data.sections[index].courses.push({
        name: 'สื่อการสอนใหม่'
      })
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    removeCourse: function (index, subIndex) {
      var self = this
      this.data.sections[index].courses.splice(subIndex, 1)
      http.post('/api/assessment', this.data)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    onBrowse: function (url, index, subIndex, e) {
      var obj = {}
      obj['i' + index + 's' + subIndex] = e.target.files[0]
      obj['url' + index + 's' + subIndex] = url
      this.$set(this, 'files', obj)
    }
  }
}
</script>

<style lang="scss">
#managecourse {
  .btn-group,
  .btn-group-vertical {
    margin: 10px 20px;
  }
}
</style>
