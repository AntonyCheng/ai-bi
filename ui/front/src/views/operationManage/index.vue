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
                    label-width="90px"
                  >
                    <el-form-item label="操作接口">
                      <el-input v-model="queryForm.api" placeholder="请输入操作接口" style="width: 200px" />
                    </el-form-item>
                    <el-form-item label="操作人账号">
                      <el-input v-model="queryForm.userAccount" placeholder="请输入操作人账号" style="width: 200px" />
                    </el-form-item>
                    <el-form-item label="操作描述">
                      <el-input v-model="queryForm.description" placeholder="请输入操作描述" style="width: 200px" />
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
              <el-button type="success" size="small" style="width: 100px" @click="handleExport">导出操作日志</el-button>
            </template>
            <template>
              <el-button type="danger" size="small" style="width: 100px" @click="handleClear">清空操作日志</el-button>
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
          prop="api"
          label="操作接口"
        />
        <el-table-column
          prop="userAccount"
          label="操作人账号"
        />
        <el-table-column
          prop="description"
          label="操作描述"
        />
        <el-table-column
          prop="createTime"
          label="创建时间"
        />
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
import { pageOperation, clearOperation, exportExcel } from '@/api/operation'
import { Loading, Message } from 'element-ui'

export default {
  name: 'OperationManage',
  data() {
    return {
      queryLoading: false,
      pageLoading: false,
      queryResult: [],
      queryForm: {
        api: undefined,
        userAccount: undefined,
        description: undefined,
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
    pageOperation(this.queryForm).then(response => {
      this.queryResult = response.data
      this.pageLoading = false
    })
  },
  methods: {
    handleQuery() {
      if (
        this.queryForm.api === undefined &&
        this.queryForm.userAccount === undefined &&
        this.queryForm.description === undefined
      ) {
        return
      }
      this.queryForm.page = 1
      this.pageLoading = true
      this.queryLoading = true
      pageOperation(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
        this.queryLoading = false
      })
    },
    handleReset() {
      if (
        this.queryForm.api === undefined &&
        this.queryForm.userAccount === undefined &&
        this.queryForm.description === undefined
      ) {
        return
      }
      this.resetQueryForm()
      this.pageLoading = true
      pageOperation(this.queryForm).then(response => {
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
      exportExcel().then(async data => {
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
    handleClear() {
      this.$confirm('此操作将清空所有操作日志，是否继续?', '确认清空', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        clearOperation().then(response => {
          pageOperation(this.queryForm).then(response => {
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
      pageOperation(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
      })
    },
    handleCurrentChange(val) {
      this.queryForm.page = val
      this.pageLoading = true
      pageOperation(this.queryForm).then(response => {
        this.queryResult = response.data
        this.pageLoading = false
      })
    },
    async resetQueryForm() {
      this.queryForm.api = undefined
      this.queryForm.userAccount = undefined
      this.queryForm.description = undefined
      this.queryForm.page = 1
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
