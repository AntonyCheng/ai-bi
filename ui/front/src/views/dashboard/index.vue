<template>
  <div class="dashboard-container">
    <el-card style="max-width: 100%">
      <template #header>
        <div class="card-header">
          <span style="font-size: xx-large">我的信息</span>
        </div>
      </template>
      <template>
        <el-row>
          <el-col align="center" :span="24">
            <el-avatar :size="80" :src="avatar" />
            <el-upload
              ref="upload"
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
              action=""
            >
              <template>
                <el-button col size="small" type="default">更换头像</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </template>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'avatar'
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

</style>
