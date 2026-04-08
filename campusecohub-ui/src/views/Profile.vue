<template>
  <Layout>
    <div class="profile">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="8">
          <el-card class="info-card">
            <div class="avatar-section">
              <el-avatar :size="100">{{ (userStore.userInfo?.username || 'U')[0] }}</el-avatar>
              <h2>{{ userStore.userInfo?.username }}</h2>
              <el-tag v-if="userStore.userInfo?.studentStatus === 1" type="success">已认证学生</el-tag>
              <el-tag v-else type="info">未认证</el-tag>
            </div>
            <el-divider />
            <div class="info-list">
              <div class="info-item">
                <span class="label">手机号</span>
                <span class="value">{{ userStore.userInfo?.phone }}</span>
              </div>
              <div class="info-item" v-if="userStore.userInfo?.schoolName">
                <span class="label">学校</span>
                <span class="value">{{ userStore.userInfo?.schoolName }}</span>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :xs="24" :sm="24" :md="16">
          <el-card class="tabs-card">
            <el-tabs v-model="activeTab">
              <el-tab-pane label="我的收藏" name="collections">
                <el-skeleton :loading="collectionLoading" :rows="3" animated>
                  <div v-if="collections.length > 0" class="collection-list">
                    <div
                      v-for="item in collections"
                      :key="item.id"
                      class="collection-item"
                      @click="goActivityDetail(item.id)"
                    >
                      <div class="item-title">{{ item.title }}</div>
                      <div class="item-meta">
                        <el-tag size="small" type="success">{{ item.category }}</el-tag>
                        <span>{{ formatDate(item.startTime) }}</span>
                      </div>
                    </div>
                  </div>
                  <el-empty v-else description="暂无收藏" />
                </el-skeleton>
              </el-tab-pane>

              <el-tab-pane label="学生认证" name="auth">
                <el-form :model="authForm" label-width="100px" style="max-width: 500px">
                  <el-form-item label="选择学校">
                    <el-select v-model="authForm.schoolId" placeholder="请选择学校" style="width: 100%">
                      <el-option
                        v-for="school in schools"
                        :key="school.id"
                        :label="school.name"
                        :value="school.id"
                      />
                    </el-select>
                  </el-form-item>
                  <el-form-item>
                    <el-button
                      type="primary"
                      @click="handleAuth"
                      :loading="authLoading"
                      :disabled="userStore.userInfo?.studentStatus === 1"
                    >
                      {{ userStore.userInfo?.studentStatus === 1 ? '已认证' : '认证' }}
                    </el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </Layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Layout from '@/components/Layout.vue'
import { userApi, activityApi, schoolApi } from '@/api'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('collections')
const collectionLoading = ref(false)
const authLoading = ref(false)
const collections = ref([])
const schools = ref([])

const authForm = reactive({
  schoolId: null
})

const goActivityDetail = (id) => router.push(`/activity/${id}`)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const loadCollections = async () => {
  try {
    collectionLoading.value = true
    const data = await activityApi.getCollections(userStore.userInfo.id)
    collections.value = data.activities || []
  } catch (e) {
    console.error(e)
  } finally {
    collectionLoading.value = false
  }
}

const loadSchools = async () => {
  try {
    schools.value = await schoolApi.getList()
  } catch (e) {
    console.error(e)
  }
}

const handleAuth = async () => {
  if (!authForm.schoolId) {
    ElMessage.warning('请选择学校')
    return
  }
  try {
    authLoading.value = true
    await userApi.studentAuth(userStore.userInfo.id, { schoolId: authForm.schoolId })
    ElMessage.success('认证成功')
    userStore.userInfo.studentStatus = 1
    userStore.userInfo.schoolId = authForm.schoolId
    userStore.setUserInfo(userStore.userInfo)
  } catch (e) {
    console.error(e)
  } finally {
    authLoading.value = false
  }
}

onMounted(() => {
  loadCollections()
  loadSchools()
})
</script>

<style scoped lang="scss">
.profile {
  .info-card {
    .avatar-section {
      text-align: center;
      padding: 20px 0;

      h2 {
        margin: 12px 0 8px;
        color: #303133;
      }
    }

    .info-list {
      .info-item {
        display: flex;
        justify-content: space-between;
        padding: 8px 0;

        .label {
          color: #909399;
        }

        .value {
          color: #303133;
        }
      }
    }
  }

  .tabs-card {
    .collection-list {
      .collection-item {
        padding: 16px;
        border: 1px solid #f0f0f0;
        border-radius: 8px;
        margin-bottom: 12px;
        cursor: pointer;
        transition: all 0.3s;

        &:hover {
          border-color: #409eff;
          background: #f5f7fa;
        }

        .item-title {
          font-size: 15px;
          font-weight: 500;
          color: #303133;
          margin-bottom: 8px;
        }

        .item-meta {
          display: flex;
          justify-content: space-between;
          align-items: center;
          color: #909399;
          font-size: 13px;
        }
      }
    }
  }
}
</style>
