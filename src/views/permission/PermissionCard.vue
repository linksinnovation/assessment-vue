<template>
  <div id="permission-card">
    <h3>กำหนดสิทธิ์การใช้งาน</h3>
  
    <div class="form-group label-floating">
      <form autocomplete="off" v-on:submit.prevent="search">
        <label class="control-label" for="addon">ค้นหาผู้ใช้งาน</label>
        <div class="input-group">
          <input type="text" id="addon" class="form-control" v-model="keyword" required>
          <span class="input-group-btn">
            <button type="submit" class="btn btn-raised btn-primary">
              ค้นหา
            </button>
          </span>
        </div>
      </form>
    </div>
  
    <div class="card">
      <div class="row" v-for="(user,index) in data">
        <div class="col-lg-6">
          <img :src="user.photo" alt="" class="img-responsive" style="min-width:150px"> </div>
        <div class="col-lg-6">
          <div class="card-block text-center">
            <h4 class="color-primary">{{user.nameTh}}</h4>
            <a v-on:click="toggleAuthority(user,$event)" class="btn btn-raised btn-primary btn-raised" v-if="user.authorities[0].authority == 'Administrator'">
              <i class="zmdi zmdi-wrench"></i> ปรับสิทธิ์เป็น User</a>
            <a v-on:click="toggleAuthority(user,$event)" href="javascript:void(0)" class="btn btn-raised btn-primary btn-raised" v-if="user.authorities[0].authority == 'User'">
              <i class="zmdi zmdi-wrench"></i> ปรับสิทธิ์เป็น Admin</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from '@/api/common/http'

export default {
  name: 'permissionCard',
  data: function () {
    return {
      data: [],
      keyword: ''
    }
  },
  methods: {
    search: function () {
      var self = this
      http
        .post('/api/user/search', this.keyword)
        .done(function (data) {
          self.$set(self, 'data', data)
        })
    },
    toggleAuthority: function (userdetail, e) {
      var self = this
      http
        .post('/api/user/update', userdetail)
        .done(function (data) {
          self.search()
        })
    }
  }
}
</script>
