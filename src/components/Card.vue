<template>
  <div class="col-md-4">
    <div class="card animated fadeInUp animation-delay-6">
      <div class="withripple zoom-img">
        <router-link :to="{path: '/assessment/'+props.id+'/assessment'}">
          <img :src="'images/'+props.cover" alt="..." class="img-responsive">
        </router-link>
      </div>
      <div class="card-block">
        <h3 class="color-primary">{{props.title}}</h3>
        <p>{{props.detail}}</p>
        <p class="text-right">
          <router-link :to="{path: 'assessment/'+props.id+'/assessment'}" class="btn btn-primary btn-raised text-right" role="button">
            <i class="zmdi zmdi-collection-image-o"></i> เข้าสู่แบบประเมิน
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'card',
  props: ['props']
}
</script>
