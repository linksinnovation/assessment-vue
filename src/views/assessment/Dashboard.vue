<template>
  <div id="dashboard">
    <barChart></barChart>
    <barChart></barChart>
  </div>
</template>

<script>
import BarChart from '@/components/BarChart'

export default {
  name: 'dashboard',
  components: {
    BarChart
  }
}
</script>
