<template>
  <div class="history-container">
    <div class="history-wrapper">

      <el-pagination
        background
        layout="prev, pager, next"
        :total="1000"
        :page="form.page"
        :page-size="form.size"
      />
    </div>
  </div>
</template>

<script>
import { ChartPage } from '@/api/chart'

export default {
  data() {
    return {
      form: {
        page: 1,
        size: 10,
        id: 0,
        name: '',
        goal: '',
        chartType: '',
        userId: undefined
      },
      data: []
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const result = await ChartPage(this.form)
      this.data = result
    },
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style lang="scss">
.history-container{
  padding: 20px;
  .history-wrapper{

  }
}
</style>

