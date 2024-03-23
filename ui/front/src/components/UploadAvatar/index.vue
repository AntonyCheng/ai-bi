<template>
  <div class="upload-container">
    <el-upload
      class="avatar-uploader"
      action="https://jsonplaceholder.typicode.com/posts/"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :http-request="submitUpload"
    >
      <span class="avatar-container avatar">
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
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
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    // 图片上传成功
    handleAvatarSuccess(response) {
      // dialogForm.value.img_url = URL.createObjectURL(uploadFile.raw!);
      const result = response
      const { url } = result
    },
    // 文件上传
    submitUpload(options) {
      // console.log(options);
      const { file } = options
      const formData = new FormData()
      formData.append('file', file)
      // console.log(imageBlob)
      // console.log(imageBase64URL)

      // const range = editor.getSelection()
      // editor.insertEmbed(range.index, 'image', `${imageBase64URL}`, 'user')
      // 图片上传
      // return Upload(formData)
      //   .then(result => {
      //     console.log(result)

      //     return result
      //   })
      //   .catch(error => {
      //     console.error(error)
      //     return error
      //   })
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
