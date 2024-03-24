<template>
  <div class="history-container">
    <div class="history-wrapper">
      <div v-if="dataList && dataList.length !== 0" class="history-card-main">
        <div class="history-card-wrapper">
          <el-card v-for="item in dataList" :key="item.id" class="history-card-item">
            <EchartsItem :chart-options="item.genChart" />
            <div class="history-desc">
              <div><span class="title" :title="item.name">{{ item.name }}</span></div>
              <!-- <div><span class="goal" :title="goal">{{ item.goal }}</span></div> -->
              <div><span class="genResult" :title="item.genResult">{{ item.genResult }}</span></div>
            </div>
          </el-card>
        </div>
      </div>

      <el-pagination
        background
        layout="prev, pager, next"
        :current-page="form.page"
        :page-size="form.size"
        :total="total"
        style="text-align: center;margin-top: 10px;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { ChartPage } from '@/api/chart'
import EchartsItem from '@/components/EchartsItem/index.vue'

export default {
  components: { EchartsItem },
  data() {
    return {
      form: {
        page: 1,
        size: 6,
        id: 0,
        name: '',
        goal: '',
        chartType: '',
        userId: undefined
      },
      dataList: [],
      total: 10
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const result = await ChartPage(this.form)
      const data = result.data
      this.dataList = data.records || []
      this.total = parseInt(data.total) || 10
    },
    handleSizeChange(val) {
      this.form.size = val
      console.log('this.form.size', this.form.size)
      this.getData()
    },
    handleCurrentChange(val) {
      this.form.page = val
      console.log('this.form.page', this.form.page)
      this.getData()
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
@import "@/styles/mixin.scss";

.history-container{
  padding: 20px;
  .history-wrapper{

    .history-card-main{
      display: flex;
      justify-content: center;

      .history-card-wrapper{
        width: auto;
        flex: 1;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;

        .history-desc{
          span{
            @include mixin-line-clamp(1);
          }

          .title{
            font-size: 18px;
            line-height: 24px;
          }
          .goal{
            font-size: 14px;
            line-height: 24px;
          }
          .genResult{
            font-size: 12px;
            line-height: 24px;
            @include mixin-line-clamp(2);
          }
        }
      }

      .history-card-item{
        // flex: calc(33% - 20px - 20px);
        // flex-basis: calc(33% - 20px - 20px);
        // max-width: calc(33% - 20px - 20px);
        width: 32%;
        margin-right: 20px;
        margin-bottom: 20px;

        &:nth-child(3n){
            margin-right: 0px;
        }
      }

    }

  }
}
</style>

