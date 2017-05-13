import { Bar } from 'vue-chartjs'

export default Bar.extend({
  methods: {
    randomColor: function () {
      var backgroundColor = []
      var borderColor = []
      for (var i = 0; i < 100; i++) {
        var num1 = Math.floor(Math.random() * 255) + 0
        var num2 = Math.floor(Math.random() * 255) + 0
        var num3 = Math.floor(Math.random() * 255) + 0
        backgroundColor.push('rgba(' + num1 + ',' + num2 + ',' + num3 + ',0.2)')
        borderColor.push('rgba(' + num1 + ',' + num2 + ',' + num3 + ',1)')
      }
      return { backgroundColor: backgroundColor, borderColor: borderColor }
    }
  },
  mounted() {
    var color = this.randomColor()
    this.renderChart({
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'Aug'],
      datasets: [
        {
          label: 'My First dataset',
          backgroundColor: color.backgroundColor,
          borderColor: color.borderColor,
          borderWidth: 1,
          data: [65, 59, 80, 81, 56, 55, 40, 100]
        }
      ]
    }, {
      scales: {
        xAxes: [{
          barPercentage: 1
        }]
      }
    })
  }
})
