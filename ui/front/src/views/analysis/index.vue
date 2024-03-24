<template>
  <div class="analysis-container">
    <div class="analysis-wrapper">
      <el-form ref="form" class="analysis-form" :model="form" label-width="120px">
        <el-form-item label="图表名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="分析目标">
          <el-input v-model="form.goal" />
        </el-form-item>
        <el-form-item label="图表类型">

          <el-radio-group v-model="form.chartType">
            <el-radio :label="'饼图'">饼图</el-radio>
            <el-radio :label="'折线图'">折线图</el-radio>
            <el-radio :label="'柱状图'">柱状图</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="图表文件">
          <el-upload
            class="upload-excel"
            drag
            action=""
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :http-request="submitUpload"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">只能上传xls和xlsx文件，且不超过2MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">智能分析</el-button>
          <!-- <el-button @click="onCancel">取消</el-button> -->
        </el-form-item>
      </el-form>

      <div class="chart-preview" />
    </div>
  </div>
</template>

<script>
import { ChartGen } from '@/api/chart'

export default {
  data() {
    return {
      form: {
        name: '',
        goal: '',
        chartType: '',
        multipartFile: ''
      },
      submitLoading: false
    }
  },
  methods: {
    onSubmit() {
      this.$message('正在生成中，请稍等')
      ChartGen(this.form).then((res) => {
      })
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    beforeAvatarUpload(file) {
      const isXLS = file.type === 'file/xls'
      const isXLSX = file.type === 'file/xlsx'

      const isLt2M = file.size / 1024 / 1024 < 2 // 最大1M

      if (!isXLS && !isXLSX) {
        this.$message.error('上传文件只能是 xls和xlsx 格式!')
      }

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }

      const flag = (isXLS || isXLSX) && isLt2M
      return flag
    },
    // 图片上传成功
    handleAvatarSuccess(response) {
      // dialogForm.value.img_url = URL.createObjectURL(uploadFile.raw!);
      // const result = response
      // console.log('response', response)
      // const { url } = result
      // 上传图片成功 更新用户信息
      // this.getUserInfo()

      // this.imageUrl = url
    },
    // 文件上传
    submitUpload(options) {
      // console.log(options);
      const { file } = options
      const formData = new FormData()
      formData.append('file', file)

      // 存储
      this.form.multipartFile = formData
    }
  }
}
</script>

<style lang="scss">
.analysis-container{
  padding: 20px;
  .analysis-wrapper{
    .analysis-form{

      .upload-excel{
        .el-upload{
          width: 100%;
          .el-upload-dragger{
            width: 100%;

          }
        }
      }
    }
    .chart-preview{

    }

  }
}
</style>

