<template>
  <div>
    <div class="material-background"></div>
    <div class="container container-full">
      <div class="ms-paper">
        <div class="row">
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
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> แบบประเมิน</a>
                  </li>
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> สื่อการสอน</a>
                  </li>
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> จัดการแบบประเมิน</a>
                  </li>
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> จัดการสือการสอน</a>
                  </li>
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> นำเข้าข้อมูล</a>
                  </li>
                  <li class="panel">
                    <a role="button" class="collapsed"><i class="zmdi zmdi-folder-star-alt"></i> Dashboard</a>
                  </li>
                  <li>
                    <a role="button"><i class="zmdi zmdi-tab"></i> รายงาน</a>
                  </li>
                </ul>
                <!-- ms-collapse-nav -->
              </div>
            </div>
          </div>
          <!-- col-md-3 -->
          <div class="col-md-9 ms-paper-content-container">
            <div class="ms-paper-content">
              <h1>Vertical Tabs</h1>
              <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
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
  name: 'paper'
}
</script>
