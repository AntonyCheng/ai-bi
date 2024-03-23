<template>
  <div class="upload-container">
    <el-upload
      class="avatar-uploader"
      action=""
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :http-request="submitUpload"
    >
      <span v-if="userInfo" class="avatar-container avatar">
        <img v-if="userInfo.avatar || imageUrl" :src="userInfo.avatar || imageUrl" class="avatar">
        <span v-else class="default-avatar">
          <DefaultAvatar
            :avater-name="userInfo ? userInfo.account :'-'"
            :width="'178px'"
            :height="'178px'"
          />
        </span>
      </span>
      <i class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import DefaultAvatar from '@/components/Avatar/index.vue'

export default {
  components: { DefaultAvatar },
  data() {
    return {
      imageUrl: ''
    }
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ])
  },
  methods: {
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg'
      const isLt1M = file.size / 1024 / 1024 < 1 // 最大1M

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!')
      // }

      if (!isLt1M) {
        this.$message.error('上传头像图片大小不能超过 1MB!')
      }
      return isLt1M
    },
    // 图片上传成功
    handleAvatarSuccess(response) {
      // dialogForm.value.img_url = URL.createObjectURL(uploadFile.raw!);
      const result = response
      console.log('response', response)
      const { url } = result
      // 上传图片成功 更新用户信息
      this.getUserInfo()

      this.imageUrl = url
    },
    // 文件上传
    submitUpload(options) {
      // console.log(options);
      const { file } = options
      const formData = new FormData()
      formData.append('file', file)

      // 图片上传 接口
      // return Upload(formData)
      //   .then(result => {
      //     console.log(result)

      //     // 上传图片成功 更新用户信息
      //     this.getUserInfo()
      //     this.imageUrl = result.url

      //     return result
      //   })
      //   .catch(error => {
      //     console.error(error)
      //     return error
      //   })
    },
    async getUserInfo() {
      await this.$store.dispatch('user/getInfo')
    }
  }
}
</script>

<style lang="scss">
    .upload-container{
        width: 178px;
        height: 178px;
        display: block;

        .avatar-uploader{
            .el-upload {
                border: 1px dashed #d9d9d9;
                border-radius: 50%;
                cursor: pointer;
                position: relative;
                overflow: hidden;
            }

            &:hover{
                border-color: #409EFF;
            }

            .avatar-container{
                width: 178px;
                height: 178px;
                display: block;

                .default-avatar{
                    position: absolute;
                    left: 0;
                    top: 0;
                }
            }

            .avatar-uploader-icon{
                font-size: 28px;
                color: #409EFF;
                width: 178px;
                height: 178px;
                opacity: 0;
                position: absolute;
                top: 0;
                left: 0;

                &:before{
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%,-50%);
                }

                &:hover{
                    opacity: 1;
                }
            }
        }
    }

</style>
