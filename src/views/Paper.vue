<template>
  <div>
    <div class="material-background"></div>
    <div class="container container-full">
      <div class="ms-paper">
        <div class="row paper-view">
          <div class="col-md-3 ms-paper-menu-left-container">
            <div class="ms-paper-menu-left">
              <h3 class="ms-paper-menu-title">
                              <i class="zmdi zmdi-apps"></i> เมนูแบบประเมิน
                              <a role="button" data-toggle="collapse" href="#collapseMenu" aria-expanded="false" aria-controls="collapseMenu" class="withripple collapsed">
                                <i class="zmdi zmdi-menu"></i>
                                <div class="ripple-container"></div>
                              </a>
                            </h3>
              <div class="panel-menu collapse" id="collapseMenu" aria-expanded="false" style="height: 0px;">
                <ul class="panel-group ms-collapse-nav" role="tablist">
                  <li>
                    <router-link :to="{path: '/assessment/'+id+'/assessment'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> แบบประเมิน</router-link>
                  </li>
                  <li>
                    <router-link :to="{path: '/assessment/'+id+'/course'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> สื่อการสอน</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/setting'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> ตั้งค่าแบบประเมิน</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/manage'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> จัดการแบบประเมิน</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/managecourse'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> จัดการสือการสอน</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/import'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> นำเข้าข้อมูล</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/report'}" role="button"><i class="zmdi zmdi-tab"></i> รายงาน</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/dashboard'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> Dashboard</router-link>
                  </li>
                  <li v-if="authority == 'Administrator'">
                    <router-link :to="{path: '/assessment/'+id+'/qrcode'}" role="button"><i class="zmdi zmdi-tab"></i> QR Code</router-link>
                  </li>
                </ul>
                <!-- ms-collapse-nav -->
              </div>
            </div>
          </div>
          <!-- col-md-3 -->
          <div class="col-md-9 ms-paper-content-container">
            <div class="ms-paper-content">
              <router-view></router-view>
            </div>
            <!-- ms-paper-content -->
          </div>
          <!-- col-md-9 -->
        </div>
        <!-- row -->
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'paper',
  data: function () {
    return {
      id: this.$route.params.id
    }
  },
  computed: mapGetters({
    authority: 'authority'
  })
}
</script>

<style lang='scss'>
.paper-view {
  min-height: 446px;

  .ms-collapse-nav li a.router-link-active {
    background-color: #03a9f4;
    color: #fff;
  }
}
</style>
