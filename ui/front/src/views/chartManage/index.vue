<template>
  <div class="user-manage-container">
    <template>
      <el-collapse accordion>
        <el-collapse-item>
          <template slot="title">
            <span style="font-size: 16px"><b>查询条件</b></span>
          </template>
          <el-card style="max-width: 100%;">
            <template #default>
              <el-row>
                <el-col :span="18">
                  <el-form
                    :inline="true"
                    :model="queryForm"
                    class="demo-form-inline"
                    size="small"
                    label-width="80px"
                  >
                    <el-form-item label="图表名称">
                      <el-input v-model="queryForm.name" placeholder="请输入图表名称" style="width: 200px" />
                    </el-form-item>
                    <el-form-item label="分析目标">
                      <el-input v-model="queryForm.goal" placeholder="请输入分析目标" style="width: 200px" />
                    </el-form-item>
                    <el-form-item label="图表类型">
                      <el-input v-model="queryForm.chartType" placeholder="请输入图表类型" style="width: 200px" />
                    </el-form-item>
                    <el-form-item label="用户账号">
                      <el-input v-model="queryForm.userAccount" placeholder="请输入用户账号" style="width: 200px" />
                    </el-form-item>
                  </el-form>
                </el-col>
                <el-col :span="6" style="text-align: center">
                  <template>
                    <el-button
                      :loading="queryLoading"
                      type="primary"
                      size="small"
                      style="width: 80px"
                      @click="handleQuery"
                    >查询
                    </el-button>
                  </template>
                  <template>
                    <el-button :loading="queryLoading" size="small" style="width: 80px" @click="handleReset">重置
                    </el-button>
                  </template>
                </el-col>
              </el-row>
            </template>
          </el-card>
        </el-collapse-item>
        <el-collapse-item>
          <template slot="title">
            <span style="font-size: 16px"><b>更多操作</b></span>
          </template>
          <template>
            <template>
              <el-button type="success" size="small" style="width: 100px" @click="handleExport">导出图表信息</el-button>
            </template>
          </template>
        </el-collapse-item>
      </el-collapse>
    </template>
    <template>
      <el-table
        v-loading="pageLoading"
        :data="queryResult.records"
        stripe
        style="width: 100%"
      >
        <el-table-column
          prop="id"
          label="ID"
        />
        <el-table-column
          prop="name"
          label="图表名称"
        />
        <el-table-column
          prop="goal"
          label="分析目标"
        />
        <el-table-column
          prop="type"
          label="图表类型"
        />
        <el-table-column
          prop="genChart"
          label="生成图表"
        >
          <template #default="scope">
            <el-popover
              placement="left"
              trigger="hover"
              width="400"
              @show="handlePopoverShow"
              @hide="handlePopoverHide"
            >
              <EchartsItem v-if="isPopoverShow" :chart-options="scope.row.genChart" />
              <el-button slot="reference" type="primary">详情</el-button>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          prop="genResult"
          label="分析结论"
        />
        <el-table-column
          prop="userAccount"
          label="用户账号"
        />
        <el-table-column
          prop="createTime"
          label="创建时间"
        />
        <el-table-column
          fixed="right"
          label="操作"
          width="150"
        >
          <template #default="scope">
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <template>
      <el-pagination
        :current-page="Number.parseInt(queryResult.current)"
        :page-sizes="[10, 30, 50, 100, 150, 300, 500]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="Number.parseInt(queryResult.total)"
        style="text-align: right;margin-top: 10px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </template>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { pageChart, exportExcelChart, deleteChart } from '@/api/admin'
import { Loading, Message } from 'element-ui'
import EchartsItem from '@/components/EchartsItem/index.vue'

export default {
  name: 'ChartManage',
  components: { EchartsItem },
  data() {
    return {
      isPopoverOpt: {
        wait: 200,
        show: false
      },
      isPopoverShow: false,
      queryLoading: false,
      pageLoading: false,
      queryResult: [],
      queryForm: {
        name: undefined,
        goal: undefined,
        chartType: undefined,
        userAccount: undefined,
        page: 1,
        size: 10,
        allowDeep: false
      }
    }
  },
  computed: {
    ...mapGetters([
      'role'
    ])
  },
  created() {
    this.pageLoading = true
    pageChart(this.queryForm).then(response => {
      this.queryResult = response.data
      this.pageLoading = false
    })
  },
  methods: {
    handleQuery() {
      if (
        this.queryForm.name === undefined &&
        this.queryForm.goal === undefined &&
        this.queryForm.chartType === undefined &&
        this.queryForm.userAccount === undefined
      ) {
        return
      }
      this.queryForm.page = 1
      this.pageLoading = true
      this.queryLoading = true
      pageChart(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
        this.queryLoading = false
      })
    },
    handleReset() {
      if (
        this.queryForm.name === undefined &&
        this.queryForm.goal === undefined &&
        this.queryForm.chartType === undefined &&
        this.queryForm.userAccount === undefined
      ) {
        return
      }
      this.resetQueryForm()
      this.pageLoading = true
      pageChart(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
      })
    },
    handleExport() {
      const downloadLoadingInstance = Loading.service({
        text: '正在下载数据，请稍候',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      exportExcelChart().then(async data => {
        if (data) {
          const blob = new Blob([data.data])
          const url = URL.createObjectURL(blob)
          const link = document.createElement('a')
          link.href = url
          link.download = decodeURIComponent(data.headers['download-filename'])
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
          URL.revokeObjectURL(url)
        } else {
          Message.error('下载失败')
        }
        downloadLoadingInstance.close()
      }).catch(() => {
        Message.error('下载失败')
        downloadLoadingInstance.close()
      })
    },
    handleDelete(data) {
      this.$confirm('此操作将删除该图表, 是否继续?', '确认删除', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteChart(data.id).then(response => {
          if (this.queryResult.total % this.queryResult.size === 1) {
            this.queryForm.page--
          }
          this.pageLoading = true
          pageChart(this.queryForm).then(response => {
            this.queryResult = response.data
            this.pageLoading = false
          })
          Message.success(response.msg)
        })
      })
    },
    handleSizeChange(val) {
      this.queryForm.size = val
      this.pageLoading = true
      pageChart(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
      })
    },
    handleCurrentChange(val) {
      this.queryForm.page = val
      this.pageLoading = true
      pageChart(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
      })
    },
    async resetQueryForm() {
      this.queryForm.name = undefined
      this.queryForm.goal = undefined
      this.queryForm.chartType = undefined
      this.queryForm.userAccount = undefined
      this.queryForm.page = 1
    },
    handlePopoverShow() {
      this.$nextTick(() => {
        if (this.isPopoverShow) {
          this.isPopoverShow = false
          setTimeout(() => {
            this.isPopoverShow = true
          }, this.isPopoverOpt.wait)
        } else {
          this.isPopoverShow = true
        }
      })
    },
    handlePopoverHide() {
      this.$nextTick(() => {
        this.isPopoverShow = false

        // this.isPopoverShow = false
        // setTimeout(() => {
        //   this.isPopoverShow = true
        // }, this.isPopoverOpt.wait)
      })
    }
  }
}
</script>

<style lang="scss">
.user-manage {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.avatar-class {
  img {
    width: 100%;
    background-size: cover;
  }
}
</style>
