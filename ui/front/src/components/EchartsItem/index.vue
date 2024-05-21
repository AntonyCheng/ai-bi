<template>
  <v-chart class="chart" :option="option" :loading="chartLoading" :style="{height: style.height + 'px'}" />
</template>

<script>
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import {
  BarChart,
  BoxplotChart,
  CandlestickChart,
  CustomChart,
  EffectScatterChart,
  FunnelChart,
  GaugeChart,
  GraphChart,
  HeatmapChart,
  LineChart,
  LinesChart,
  MapChart,
  ParallelChart,
  PictorialBarChart,
  PieChart,
  RadarChart,
  SankeyChart,
  ScatterChart,
  SunburstChart,
  ThemeRiverChart,
  TreeChart,
  TreemapChart
} from 'echarts/charts'

import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  ToolboxComponent
  // LineChart
} from 'echarts/components'
import VChart, { THEME_KEY } from 'vue-echarts'
// import { LineChart } from 'echarts/charts'

use([
  // ↓导入所有图表
  BarChart,
  BoxplotChart,
  CandlestickChart,
  CustomChart,
  EffectScatterChart,
  FunnelChart,
  GaugeChart,
  GraphChart,
  HeatmapChart,
  LineChart,
  LinesChart,
  MapChart,
  ParallelChart,
  PictorialBarChart,
  PieChart,
  RadarChart,
  SankeyChart,
  ScatterChart,
  SunburstChart,
  ThemeRiverChart,
  TreeChart,
  TreemapChart,
  // ↑导入所有图表
  CanvasRenderer,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  ToolboxComponent
])

export default {
  name: 'EchartsItem',
  components: {
    VChart
  },
  provide: {
    [THEME_KEY]: 'light'
  },
  props: {
    chartOptions: {
      type: String,
      default: '{}'
    },
    height: {
      type: Number,
      default: 300
    }
  },
  data() {
    return {
      option: {},
      style: {
        height: 300
      }
    }
  },
  computed: {
    // 一个计算属性的 getter
    chartLoading() {
      // `this` 指向当前组件实例
      return !this.option || JSON.stringify(this.option) === '{}'
    }
  },
  watch: {
    chartOptions: {
      handler(newVal, oldVal) {
        console.log(111)
        console.log('watch chartOptions', newVal)
        this.option = this.chartOptions
        console.log(this.height)
        this.updateOptions(this.option)
      },
      immediate: true
    }
  },
  mounted() {
    // this.option = this.chartOptions
    // console.log(this.height)
    if (this.height) {
      this.style.height = this.height
    }
    // this.updateOptions(this.option)
  },
  methods: {
    // json字符串中，包含函数時，使用JSON.parse(strJSON)转换时，定义的字符串函数被识别成普通的字符串。
    funReviver(key, value) {
      if (key === 'formatter' && typeof value === 'string' && value.indexOf('function') === 0) {
        // alert(key);
        return Function('return ' + value)()
      }
      return value
    },
    updateOptions(chartOptions_) {
      if (!chartOptions_) return
      try {
        const chartData = JSON.parse(chartOptions_)
        // function(k, v) {
        //   if (v.indexOf && v.indexOf('function') > -1) {
        //     return eval('(function(){return ' + v + ' })()')
        //   }
        //   return v
        // }

        // console.log('chartData', chartData)
        this.option = chartData
      } catch (error) {
        console.log(error)
      }
    }
  }
}
</script>

  <style scoped>
  /* .chart {
    height: 300px;
  } */
  </style>
