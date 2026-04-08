<template>
  <Layout>
    <div class="activity-detail">
      <el-skeleton :loading="loading" animated>
        <template v-if="activity">
          <el-page-header @back="goBack" content="活动详情" />
          
          <el-card class="detail-card">
            <div class="activity-header">
              <div class="header-tags">
                <el-tag size="large" type="success">{{ activity.category }}</el-tag>
                <el-tag v-if="activity.isStudentExclusive" size="large" type="warning">学生专属</el-tag>
                <el-tag size="large" type="info">{{ activity.source }}</el-tag>
              </div>
              <h1 class="activity-title">{{ activity.title }}</h1>
              <div class="activity-meta">
                <span>
                  <el-icon><Calendar /></el-icon>
                  {{ formatDate(activity.startTime) }} - {{ formatDate(activity.endTime) }}
                </span>
                <span v-if="activity.schoolName">
                  <el-icon><Location /></el-icon>
                  {{ activity.schoolName }}
                </span>
              </div>
            </div>

            <el-divider />

            <div class="activity-section">
              <h3>活动介绍</h3>
              <p>{{ activity.description }}</p>
            </div>

            <div class="activity-section">
              <h3>参与方式</h3>
              <p>{{ activity.participateWay }}</p>
            </div>

            <div class="activity-section">
              <el-button type="primary" size="large" @click="openLink">
                <el-icon><Link /></el-icon>
                查看详情链接
              </el-button>
            </div>

            <el-divider />

            <div class="action-section">
              <template v-if="userStore.isLoggedIn">
                <el-button
                  :type="activity.isCollected ? 'warning' : 'primary'"
                  size="large"
                  @click="handleCollect"
                >
                  <el-icon><Star /></el-icon>
                  {{ activity.isCollected ? '已收藏' : '收藏活动' }}
                </el-button>
              </template>
              <template v-else>
                <el-button type="primary" size="large" @click="goLogin">
                  登录后收藏
                </el-button>
              </template>
            </div>
          </el-card>
        </template>
      </el-skeleton>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Layout from '@/components/Layout.vue'
import { activityApi } from '@/api'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const activity = ref(null)

const goBack = () => router.back()
const goLogin = () => router.push('/login')

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const openLink = () => {
  if (activity.value?.link) {
    window.open(activity.value.link, '_blank')
  }
}

const loadActivity = async () => {
  try {
    loading.value = true
    const userId = userStore.isLoggedIn ? userStore.userInfo?.id : null
    activity.value = await activityApi.getDetail(route.params.id, userId)
  } catch (e) {
    console.error(e)
    ElMessage.error('加载活动详情失败')
  } finally {
    loading.value = false
  }
}

const handleCollect = async () => {
  try {
    if (activity.value.isCollected) {
      await activityApi.uncollect(userStore.userInfo.id, activity.value.id)
      activity.value.isCollected = false
      ElMessage.success('取消收藏成功')
    } else {
      await activityApi.collect(userStore.userInfo.id, activity.value.id)
      activity.value.isCollected = true
      ElMessage.success('收藏成功')
    }
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  loadActivity()
})
</script>

<style scoped lang="scss">
.activity-detail {
  .detail-card {
    margin-top: 20px;

    .activity-header {
      .header-tags {
        display: flex;
        gap: 12px;
        margin-bottom: 16px;
      }

      .activity-title {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 16px;
        color: #303133;
      }

      .activity-meta {
        display: flex;
        gap: 24px;
        color: #909399;
        font-size: 14px;

        span {
          display: flex;
          align-items: center;
          gap: 4px;
        }
      }
    }

    .activity-section {
      margin-bottom: 24px;

      h3 {
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 12px;
        color: #303133;
      }

      p {
        color: #606266;
        line-height: 1.8;
      }
    }

    .action-section {
      text-align: center;
    }
  }
}
</style>
