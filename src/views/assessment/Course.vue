<template>
  <div id="course">
    <div class="row">
  
      <div class="col-md-12">
        <video-player :options="playerOptions" @ready="playerReadied"></video-player>
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

// hls plugin
require('videojs-contrib-hls/dist/videojs-contrib-hls')
export default {
  name: 'course',
  data() {
    return {
      id: this.$route.params.id,
      data: {},
      playerOptions: {
        // videojs and plugin options
        sources: [{
          withCredentials: false,
          type: 'application/x-mpegURL',
          src: 'http://10.1.2.203/8e47bb95738b090baf28d731fa8e3785/720p.m3u8'
        }],
        controlBar: {
          timeDivider: false,
          durationDisplay: false
        },
        flash: { hls: { withCredentials: false } },
        html5: { hls: { withCredentials: false } }
      }
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
          self.changeCourse(data.sections[0].courses[0])
        })
    },
    changeCourse: function (course) {
      var options = {
        sources: [{
          withCredentials: false,
          type: 'application/x-mpegURL',
          src: 'http://10.1.2.203/' + course.uuid + '/720p.m3u8'
        }],
        controlBar: {
          timeDivider: false,
          durationDisplay: false
        },
        flash: { hls: { withCredentials: false } },
        html5: { hls: { withCredentials: false } }
      }
      this.$set(this, 'playerOptions', options)
    },
    playerReadied(player) {
      player.tech({ IWillNotUseThisInPlugins: true }).hls
      player.tech_.hls.xhr.beforeRequest = function (options) {
        return options
      }
    }
  }
}
</script>
