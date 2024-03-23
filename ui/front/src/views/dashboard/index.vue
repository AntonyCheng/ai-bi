<template>
  <div class="dashboard-container">
    <el-card style="max-width: 100%">
      <template #header>
        <div class="card-header">
          <span style="font-size: 24px">我的信息</span>
        </div>
      </template>
      <template>
        <div class="userinfo-card">
          <el-row>
            <el-col align="center" :span="24">
              <UploadAvatar />
            </el-col>
          </el-row>
          <div v-if="userInfo" class="userinfo-container">
            <el-descriptions class="margin-top" title="用户信息" :column="1" :size="''" :border="true">
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-user" />
                  用户名
                </template>
                {{ (userInfo.account || userInfo.name) }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-mobile-phone" />
                  ID
                </template>
                {{ userInfo.id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-cpu" />
                  角色
                </template>
                {{ userInfo.role || '--' }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
          <el-row v-if="false" class="userinfo-container">
            <el-col align="center" :span="24">
              <el-divider>用户名</el-divider>
            </el-col>
            <el-col align="center" :span="24">
              <span class="userinfo-item userinfo-name">{{ userInfo.account || userInfo.name }}</span>
            </el-col>
            <el-col align="center" :span="24">
              <el-divider>ID</el-divider>
            </el-col>
            <el-col align="center" :span="24">
              <span class="userinfo-item userinfo-id">{{ userInfo.id || '--' }}</span>
            </el-col>
            <el-col align="center" :span="24">
              <el-divider>角色</el-divider>
            </el-col>
            <el-col align="center" :span="24">
              <span class="userinfo-item userinfo-role">{{ userInfo.role || '--' }}</span>
            </el-col>
            <el-col align="center" :span="24">
              <el-divider />
            </el-col>
          </el-row>
        </div>
      </template>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import UploadAvatar from '@/components/UploadAvatar/index.vue'

export default {
  name: 'Dashboard',
  components: { UploadAvatar },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'userInfo'
    ])
  },
  methods: {
    handleExceed(files) {

    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
}

  .userinfo-container{
    margin-top: 12px;

    .userinfo-item{
      margin-top: 12px;
    }

    .userinfo-name{
      font-size: 24px;
      font-weight: bold;
      margin-top: 20px;
    }
  }

</style>
