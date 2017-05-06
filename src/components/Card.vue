<template>
  <div class="col-md-4">
    <div class="card animated fadeInUp animation-delay-6">
      <div class="withripple zoom-img">
        <router-link :to="{path: 'assessment'}">
          <img src="https://agmstudio.io/themes/material-style/1.4/assets/img/demo/port4.jpg" alt="..." class="img-responsive">
        </router-link>
      </div>
      <div class="card-block">
        <h3 class="color-primary">Thumbnail label</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Autem, repellat, vitae porro ex expedita cumque nulla.</p>
        <p class="text-right">
          <router-link :to="{path: 'assessment'}" class="btn btn-primary btn-raised text-right" role="button">
            <i class="zmdi zmdi-collection-image-o"></i> View More
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'card'
}
</script>
