<template>
  <Layout>
    <div class="home">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="16">
          <el-card class="welcome-card">
            <h2>欢迎来到校园经济资源分享平台</h2>
            <p>发现优惠活动，分享生活资源，共建美好校园</p>
            <div class="welcome-actions">
              <el-button type="primary" size="large" @click="goActivities">
                <el-icon><Present /></el-icon>
                浏览活动
              </el-button>
              <el-button size="large" @click="goPosts">
                <el-icon><ChatDotRound /></el-icon>
                查看帖子
              </el-button>
            </div>
          </el-card>

          <el-card class="section-card">
            <template #header>
              <div class="card-header">
                <span>热门活动</span>
                <el-button link type="primary" @click="goActivities">查看更多</el-button>
              </div>
            </template>
            <el-skeleton :loading="activityLoading" :rows="3" animated>
              <div v-if="activities.length > 0" class="activity-list">
                <div
                  v-for="activity in activities.slice(0, 3)"
                  :key="activity.id"
                  class="activity-item"
                  @click="goActivityDetail(activity.id)"
                >
                  <div class="activity-title">{{ activity.title }}</div>
                  <div class="activity-meta">
                    <el-tag size="small" type="success">{{ activity.category }}</el-tag>
                    <span class="activity-time">
                      <el-icon><Clock /></el-icon>
                      {{ formatDate(activity.startTime) }}
                    </span>
                  </div>
                </div>
              </div>
              <el-empty v-else description="暂无活动" />
            </el-skeleton>
          </el-card>

          <el-card class="section-card">
            <template #header>
              <div class="card-header">
                <span>最新帖子</span>
                <el-button link type="primary" @click="goPosts">查看更多</el-button>
              </div>
            </template>
            <el-skeleton :loading="postLoading" :rows="3" animated>
              <div v-if="posts.length > 0" class="post-list">
                <div
                  v-for="post in posts.slice(0, 3)"
                  :key="post.id"
                  class="post-item"
                  @click="goPostDetail(post.id)"
                >
                  <div class="post-title">{{ post.title }}</div>
                  <div class="post-meta">
                    <span class="post-author">
                      <el-icon><User /></el-icon>
                      {{ post.username || '匿名用户' }}
                    </span>
                    <span class="post-stats">
                      <el-icon><Star /></el-icon>
                      {{ post.likeCount }}
                      <el-icon style="margin-left: 12px"><ChatLineRound /></el-icon>
                      {{ post.commentCount }}
                    </span>
                  </div>
                </div>
              </div>
              <el-empty v-else description="暂无帖子" />
            </el-skeleton>
          </el-card>
        </el-col>

        <el-col :xs="24" :sm="24" :md="8">
          <el-card class="stats-card">
            <h3>平台数据</h3>
            <el-statistic title="活动数量" :value="100" />
            <el-divider />
            <el-statistic title="帖子数量" :value="200" />
            <el-divider />
            <el-statistic title="用户数量" :value="500" />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '@/components/Layout.vue'
import { activityApi, postApi } from '@/api'

const router = useRouter()

const activityLoading = ref(true)
const postLoading = ref(true)
const activities = ref([])
const posts = ref([])

const goActivities = () => router.push('/activities')
const goPosts = () => router.push('/posts')
const goActivityDetail = (id) => router.push(`/activity/${id}`)
const goPostDetail = (id) => router.push(`/post/${id}`)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const loadActivities = async () => {
  try {
    activityLoading.value = true
    const data = await activityApi.getList({ pageNum: 1, pageSize: 10 })
    activities.value = data.activities || []
  } catch (e) {
    console.error(e)
  } finally {
    activityLoading.value = false
  }
}

const loadPosts = async () => {
  try {
    postLoading.value = true
    const data = await postApi.getList({ pageNum: 1, pageSize: 10 })
    posts.value = data.posts || []
  } catch (e) {
    console.error(e)
  } finally {
    postLoading.value = false
  }
}

onMounted(() => {
  loadActivities()
  loadPosts()
})
</script>

<style scoped lang="scss">
.home {
  .welcome-card {
    margin-bottom: 20px;
    text-align: center;
    padding: 40px;

    h2 {
      margin-bottom: 12px;
      color: #303133;
    }

    p {
      color: #909399;
      margin-bottom: 24px;
    }

    .welcome-actions {
      display: flex;
      gap: 16px;
      justify-content: center;
    }
  }

  .section-card {
    margin-bottom: 20px;

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .activity-list, .post-list {
      .activity-item, .post-item {
        padding: 16px 0;
        border-bottom: 1px solid #f0f0f0;
        cursor: pointer;
        transition: background 0.3s;

        &:last-child {
          border-bottom: none;
        }

        &:hover {
          background: #f5f7fa;
          margin: 0 -16px;
          padding: 16px;
        }

        .activity-title, .post-title {
          font-size: 16px;
          font-weight: 500;
          color: #303133;
          margin-bottom: 8px;
        }

        .activity-meta, .post-meta {
          display: flex;
          justify-content: space-between;
          align-items: center;
          color: #909399;
          font-size: 14px;

          .activity-time, .post-author, .post-stats {
            display: flex;
            align-items: center;
            gap: 4px;
          }
        }
      }
    }
  }

  .stats-card {
    text-align: center;
  }
}
</style>
