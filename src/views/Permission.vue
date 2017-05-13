<template>
  <div id="prepaper">
    <div class="material-background"></div>
    <div class="container container-full">
      <div class="ms-paper">
        <div class="row pre-paper-view">
          <div class="col-md-3 ms-paper-menu-left-container">
            <div class="ms-paper-menu-left">
              <h3 class="ms-paper-menu-title">
                              <i class="zmdi zmdi-apps"></i> เมนูการกำหนดสิทธิ์
                              <a role="button" data-toggle="collapse" href="#collapseMenu" aria-expanded="false" aria-controls="collapseMenu" class="withripple collapsed">
                                <i class="zmdi zmdi-menu"></i>
                                <div class="ripple-container"></div>
                              </a>
                            </h3>
              <div class="panel-menu collapse" id="collapseMenu" aria-expanded="false" style="height: 0px;">
                <ul class="panel-group ms-collapse-nav" role="tablist">
                  <li>
                    <router-link :to="{path: '/permission'}" role="button"><i class="zmdi zmdi-folder-star-alt"></i> กำหนดสิทธิ์การใช้งาน</router-link>
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
export default {
  name: 'permission'
}
</script>

<style lang='scss'>
#prepaper {
  min-height: calc(100vh - 160px);
  .pre-paper-view {
    min-height: 492px;
  }
}
</style>
