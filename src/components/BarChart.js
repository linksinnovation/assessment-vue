import { Bar, mixins } from 'vue-chartjs'

export default Bar.extend({
  props: ['chartData', 'title'],
  mixins: [mixins.reactiveProp],
  methods: {
    fetchData: function () {
      this.renderChart(this.chartData, {
        maintainAspectRatio: false,
        deferred: {
          yOffset: '80%',
          delay: 100
        },
        title: {
          display: !0,
          text: this.title,
          fontSize: 20,
          fontFamily: 'Roboto',
          fontStyle: 'normal',
          padding: 20
        },
        scales: {
          xAxes: [{
            barPercentage: 1
          }]
        },
        tooltips: {
          callbacks: {
            label: function (tooltipItems, data) {
              return data.datasets[tooltipItems.datasetIndex].label + ': ' + tooltipItems.yLabel + ' คน'
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
