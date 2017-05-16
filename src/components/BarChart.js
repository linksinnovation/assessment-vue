import { Bar, mixins } from 'vue-chartjs'

export default Bar.extend({
  props: ['chartData'],
  mixins: [mixins.reactiveProp],
  methods: {
    fetchData: function () {
      this.renderChart(this.chartData, {
        scales: {
          xAxes: [{
            barPercentage: 1
          }]
        },
        tooltips: {
          callbacks: {
            label: function (tooltipItems, data) {
              return data.datasets[tooltipItems.datasetIndex].label + ': ' + tooltipItems.yLabel + ' %'
            }
          }
        }
      })
    }
  },
  mounted() {
    this.fetchData()
  }
})
