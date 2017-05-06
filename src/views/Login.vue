<template>
<div class="login">
  <div class="middle-box text-center animated fadeInDown">
    <div>
      <div>
        <h1 class="logo-name">StockrSubmit</h1>
      </div>
      <h3>Welcome to StockrSubmit</h3>
      <p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.</p>
      <p>Login in. To see it in action.</p>
      <form class="mt" role="form" v-on:submit.prevent="onSubmit">
        <div class="form-group">
          <input type="email" class="form-control" placeholder="Email" required="" v-model="credentials.username">
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="Password" required="" v-model="credentials.password">
        </div>
        <button type="submit" class="btn btn-raised btn-primary block full-width mb">Login</button>

        <router-link to="/"><small>Forgot password?</small></router-link>
        <p class="text-muted text-center"><small>Do not have an account?</small></p>
        <router-link to="/" class="btn btn-sm btn-white btn-block">Create an account</router-link>
      </form>
      <p class="mt"> <small>StockrSubmit Distribute and Sell your Photos &copy; 2016</small> </p>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'wall',
  data: function() {
    return {
      credentials: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    onSubmit: function() {
      this.$store.dispatch('login', this)
    }
  }
}
</script>

<style lang="scss">
.login {
    width: 100%;
    height: 100%;
    min-height: 100vh;
    color: #676a6c;
    background-color: #f3f3f4;

    .middle-box {
        max-width: 300px;
        z-index: 100;
        margin: 0 auto;
        padding-top: 10%;
        h1 {
            &.logo-name {
                color: #e6e6e6;
                font-size: 35px;
                font-weight: 800;
                letter-spacing: -1px;
                margin-bottom: 0;
            }
        }
        h3 {
            margin-top: 5px;
            font-weight: 600;
            font-size: 16px;
        }
        .block {
            display: block;
        }
        .full-width {
            width: 100% !important;
        }
        .btn-white {
            color: inherit;
            background: white;
            border: 1px solid #e7eaec;
            &:focus,
            &:hover {
                color: inherit;
                border: 1px solid #d2d2d2;
            }
        }
        .mt {
            margin-top: 15px;
        }
        .mb {
            margin-bottom: 15px;
        }
    }
}
</style>
