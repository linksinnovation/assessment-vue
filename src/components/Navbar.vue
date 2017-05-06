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
    </div>
    <!-- container -->
  </nav>
</template>

<script>
export default {
  name: 'navbar'
}
</script>
