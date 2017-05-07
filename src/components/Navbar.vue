<template>
  <nav class="navbar yamm ms-navbar ms-navbar-primary shrink navbar-fixed-top">
    <div class="container container-full">
      <div class="navbar-header">
        <router-link :to="{path: '/'}" class="navbar-brand">
          <!-- <img src="assets/img/demo/logo-navbar.png" alt=""> -->
          <span class="ms-logo ms-logo-sm">M</span>
          <span class="ms-title">Mitrphol <strong>Groups</strong></span>
        </router-link>
      </div>
  
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
  
          <li class="dropdown">
            <a href="#" class="dropdown-toggle animated fadeIn animation-delay-9" data-toggle="dropdown" data-hover="dropdown" role="button" aria-haspopup="true" aria-expanded="false" data-name="ecommerce">
                    {{name}}
                    <i class="zmdi zmdi-chevron-down"></i>
                    <div class="ripple-container"></div>
                  </a>
            <ul class="dropdown-menu">
              <li>
                <router-link :to="{path: '/assessment'}"><i class="zmdi zmdi-view-compact"></i> เพิ่มแบบประเมิน</router-link>
              </li>
              <li>
                <a href=""><i class="zmdi zmdi-view-compact"></i> กำหนดสิทธิ์การใช้งาน</a>
              </li>
              <li class="divider"></li>
              <li>
                <a v-on:click="logout(this)"><i class="zmdi zmdi-view-compact"></i> ออกจากระบบ</a>
              </li>
            </ul>
          </li>
          <!-- <li class="btn-navbar-menu"><a href="javascript:void(0)" class="sb-toggle-left"><i class="zmdi zmdi-menu"></i></a></li> -->
        </ul>
      </div>
    </div>
    <!-- container -->
  </nav>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'navbar',
  methods: {
    logout: function () {
      this.$store.dispatch('logout', this)
    }
  },
  computed: mapGetters({
    authenticated: 'authenticated',
    name: 'name',
    authority: 'authority'
  })
}
</script>
