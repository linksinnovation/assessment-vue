<template>
  <div id="course">
    <div class="row">
  
      <div class="col-md-12 text-center" v-if="data.sections.length > 0 && (data.course.contentType === 'PDF' || data.course.contentType === 'PPT')">
        <button type="submit" class="btn btn-raised btn-primary" v-on:click="pdfPrev"> &lt;&lt; </button>
        <span>{{pdfOptions.page}} / {{pdfOptions.numPages}} </span>
        <button type="submit" class="btn btn-raised btn-primary" v-on:click="pdfNext"> &gt;&gt; </button>
      </div>
  
      <div class="col-md-12" v-if="data.sections.length > 0 && data.course.contentType === 'VIDEO'">
        <video-player :options="playerOptions" @ready="playerReadied" @ended="onPlayerEnded($event)"></video-player>
      </div>
  
      <div class="col-md-1" v-if="data.sections.length > 0 && (data.course.contentType === 'PDF' || data.course.contentType === 'PPT')"></div>
      <div class="col-md-10" v-if="data.sections.length > 0 && (data.course.contentType === 'PDF' || data.course.contentType === 'PPT')">
        <pdf :page="pdfOptions.page" @numPages="pdfOptions.numPages = $event" :src="pdfOptions.src" v-if=""></pdf>
      </div>
      <div class="col-md-1" v-if="data.sections.length > 0 && (data.course.contentType === 'PDF' || data.course.contentType === 'PPT')"></div>
  
      <div class="col-md-12 text-center" v-if="data.sections.length > 0 && (data.course.contentType === 'PDF' || data.course.contentType === 'PPT')">
        <button type="submit" class="btn btn-raised btn-primary" v-on:click="pdfPrev"> &lt;&lt; </button>
        <span>{{pdfOptions.page}} / {{pdfOptions.numPages}} </span>
        <button type="submit" class="btn btn-raised btn-primary" v-on:click="pdfNext"> &gt;&gt; </button>
      </div>
  
      <div class="col-md-12" style="margin-bottom: 30px">
        <div class="panel-menu collapse" id="collapseMenu">
          <ul class="panel-group ms-collapse-nav" id="components-nav" role="tablist" aria-multiselectable="true">
  
            <li class="panel" role="tab" :id="'e'+index" v-for="(section,index) in data.sections">
              <a role="button" data-toggle="collapse" data-parent="#components-nav" :href="'#c'+index" aria-expanded="true" :aria-controls="'c'+index" class="collapsed withripple">
                <i class="fa fa-book"></i> {{section.name}}
                <div class="ripple-container"></div>
              </a>
              <ul :id="'c'+index" class="panel-collapse collapse" role="tabpanel" :aria-labelledby="'e'+index">
                <li v-for="(course,subIndex) in section.courses">
                  <a class="withripple" v-on:click="changeCourse(course)">
                    <i class="fa fa-book"></i> {{course.name}}</a>
                </li>
              </ul>
            </li>
  
          </ul>
          <!-- ms-collapse-nav -->
        </div>
      </div>
  
    </div>
  </div>
</template>

<script>
import http from '@/api/common/http'
import pdf from 'vue-pdf'

// hls plugin
import 'videojs-contrib-hls/dist/videojs-contrib-hls.min.js'

export default {
  name: 'course',
  data() {
    return {
      id: this.$route.params.id,
      data: {
        sections: [],
        course: {}
      },
      playerOptions: {
        // videojs and plugin options
        sources: [{
          withCredentials: false,
          type: 'application/x-mpegURL',
          src: 'http://mpintegrity.mitrphol.com/vdo/8e47bb95738b090baf28d731fa8e3785/720p.m3u8'
        }],
        controlBar: {
          timeDivider: false,
          durationDisplay: false
        },
        flash: { hls: { withCredentials: false } },
        html5: { hls: { withCredentials: false } }
      },
      pdfOptions: {
        src: '/files/1-ฟังก์ชั่นงานระบบ (ก่อน 21-04-2017).pdf',
        page: 1,
        numPages: 0
      }
    }
  },
  components: {
    pdf
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
          if (data.sections.length > 0) {
            self.changeCourse(data.sections[0].courses[0])
          }
        })
    },
    changeCourse: function (course) {
      if (course.contentType === 'VIDEO') {
        var playerOptions = {
          sources: [{
            withCredentials: false,
            type: 'application/x-mpegURL',
            src: 'http://mpintegrity.mitrphol.com/vdo/' + course.uuid + '/720p.m3u8'
          }],
          controlBar: {
            timeDivider: false,
            durationDisplay: false
          },
          flash: { hls: { withCredentials: false } },
          html5: { hls: { withCredentials: false } }
        }
        this.$set(this, 'playerOptions', playerOptions)
      } else {
        var pdfOptions = {
          src: '/files/' + course.id + '-' + course.content,
          page: 1,
          numPages: 0
        }
        this.$set(this, 'pdfOptions', pdfOptions)
      }
      this.$set(this.data, 'course', course)
    },
    playerReadied(player) {
      player.tech({ IWillNotUseThisInPlugins: true }).hls
      player.tech_.hls.xhr.beforeRequest = function (options) {
        return options
      }
    },
    onPlayerEnded(e) {
      this.complete()
    },
    pdfNext: function () {
      if (this.pdfOptions.page < this.pdfOptions.numPages) {
        this.pdfOptions.page++
        this.$set(this, 'pdfOptions', this.pdfOptions)
      }
      if (this.pdfOptions.page === this.pdfOptions.numPages) {
        this.complete()
      }
    },
    pdfPrev: function () {
      if (this.pdfOptions.page > 1) {
        this.pdfOptions.page--
        this.$set(this, 'pdfOptions', this.pdfOptions)
      }
    },
    complete: function () {
      http.get('/api/course/' + this.id + '/' + this.data.course.id)
        .done(function (data) {
          console.log(data)
        })
    }
  }
}
</script>

<style lang="scss">
#course {
  #collapseMenu {
    display: block
  }
}
</style>
