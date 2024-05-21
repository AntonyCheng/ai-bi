<template>
  <div class="analysis-container">
    <div class="analysis-wrapper">
      <el-form ref="formRef" class="analysis-form" :model="form" :rules="formRules" label-width="120px">
        <el-form-item label="图表名称" prop="name">
          <el-input placeholder="请输入图表名称" v-model="form.name" />
        </el-form-item>
        <el-form-item label="分析目标" prop="goal">
          <el-input placeholder="请输入分析目标" v-model="form.goal" />
        </el-form-item>
        <el-form-item label="图表类型" prop="chartType">
          <el-select v-model="form.chartType" filterable placeholder="请选择图表类型">
            <el-option
              v-for="item in typeList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
          <!-- <el-radio-group>
            <el-radio v-for="item in typeList" :key="item" :label="item">{{ item }}</el-radio>
          </el-radio-group> -->
        </el-form-item>
        <el-form-item label="表格文件">
          <el-upload
            class="upload-excel"
            drag
            action=""
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :http-request="submitUpload"
            :limit="1"
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
      <div v-loading="submitLoading" class="chart-preview">
        <div class="chart-preview-wrapper">
          <span v-if="!previewData">预览区</span>
          <div v-else style="padding: 20px;">
            <!-- <el-card> -->
            <EchartsItem :chart-options="previewData" />
            <div class="history-desc">
              <!-- <div><span class="title" :title="item.createTime">{{ item.createTime }}</span></div> -->
              <!-- <div><span class="goal" :title="goal">{{ item.goal }}</span></div> -->
              <div><span class="genResult" :title="previewText">{{ previewText }}</span></div>
            </div>
            <!-- </el-card> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ChartGen } from '@/api/chart'
import EchartsItem from '@/components/EchartsItem/index.vue'

export default {
  components: { EchartsItem },
  data() {
    return {
      form: {
        name: '',
        goal: '',
        chartType: '',
        file: {}
      },
      submitLoading: false,
      previewText: '',
      previewData: undefined,
      // '散点图','地理坐标/地图','K 线图','盒须图','路径图', '平行坐标系', '桑基图',
      typeList: ['折线图', '柱状图', '饼图', '雷达图', '热力图', '关系图', '树图', '矩形树图', '旭日图', '漏斗图'],
      formRules: {
        'name': [{ required: true, trigger: 'blur', message: '图表名称是必填项' }],
        'goal': [{ required: true, trigger: 'blur', message: '分析目标是必填项' }],
        'chartType': [{ required: true, trigger: 'blur', message: '图表类型是必填项' }]
      }
    }
  },
  methods: {
    onSubmit() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.previewData = null
          this.submitLoading = true
          this.$message('正在生成中，请注意观察右侧预览区')
          const body = new FormData()
          console.log('body', body)
          for (const key in this.form) {
            body.append(key, this.form[key])
          }
          // body.append('file', this.form['file'])

          ChartGen(body).then((res) => {
            this.submitLoading = false
            const data = res.data
            console.log('res', res)

            this.previewText = data.genResult
            this.previewData = data.genChart
            this.$message.success('生成成功')
          }).catch(() => {
            this.submitLoading = false
            this.$message.error('生成失败')
          })
        }
      })
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    beforeAvatarUpload(file) {
      console.log(file.name.split('.').reverse()[0])
      const suffix = file.name.split('.').reverse()[0]

      const suffixFlag = suffix !== 'xls' && suffix !== 'xlsx'

      const isLt2M = file.size / 1024 / 1024 < 2 // 最大2M

      if (suffixFlag) {
        this.$message.error('上传文件只能是 xls和xlsx 格式!')
      }

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }

      const flag = !suffixFlag && isLt2M
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
      // const formData =
      console.log('file', file)

      // 存储
      this.form.file = file
    }
  }
}
</script>

<style lang="scss">
@import "@/styles/mixin.scss";

.analysis-container{
  padding: 20px;

  .analysis-wrapper{
    display: flex;
    flex-wrap: nowrap;
    width: 100%;

    .analysis-form{
      flex: 1;
      padding-right: 20px;

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
      flex: 1;

      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;

      &:hover{
        border-color: #409EFF;
      }

      .chart-preview-wrapper{
        // height: 300px;
        height: 100%;
        display: flex;
        // height: 20px;
        justify-content: center;
        align-items: center;

        .history-desc{

          .title{
            font-size: 18px;
            line-height: 24px;
          }
          .goal{
            font-size: 14px;
            line-height: 24px;
          }
          .genResult{
            font-size: 20px;
            line-height: 24px;
          }
        }
      }

    }

  }
}
</style>

