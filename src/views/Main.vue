<template>
  <div id="app">
    <navbar></navbar>
    <router-view></router-view>
    <footbar></footbar>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'
import Footbar from '@/components/Footbar'

export default {
  name: 'main',
  components: {
    Navbar,
    Footbar
  }
}
</script>
