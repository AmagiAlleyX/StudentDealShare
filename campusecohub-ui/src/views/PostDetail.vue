<template>
  <Layout>
    <div class="post-detail">
      <el-skeleton :loading="loading" animated>
        <template v-if="post">
          <el-page-header @back="goBack" content="帖子详情" />
          
          <el-card class="detail-card">
            <div class="post-header">
              <el-avatar :size="50">{{ (post.username || '匿名')[0] }}</el-avatar>
              <div class="post-user">
                <div class="username">{{ post.username || '匿名用户' }}</div>
                <div class="post-time">{{ formatDate(post.createTime) }}</div>
              </div>
            </div>

            <h1 class="post-title">{{ post.title }}</h1>
            <div class="post-content">{{ post.content }}</div>

            <el-divider />

            <div class="post-actions">
              <el-button
                :type="post.isLiked ? 'danger' : 'default'"
                @click="handleLike"
                v-if="userStore.isLoggedIn"
              >
                <el-icon><Star /></el-icon>
                {{ post.likeCount }} 点赞
              </el-button>
              <el-button type="primary" @click="scrollToComment">
                <el-icon><ChatLineRound /></el-icon>
                {{ post.commentCount }} 评论
              </el-button>
            </div>
          </el-card>

          <el-card class="comment-card" id="comment-section">
            <template #header>
              <span>评论 ({{ comments.length }})</span>
            </template>

            <template v-if="userStore.isLoggedIn">
              <el-input
                v-model="commentContent"
                type="textarea"
                :rows="3"
                placeholder="写下你的评论..."
                class="comment-input"
              />
              <div class="comment-submit">
                <el-button type="primary" @click="handleComment" :loading="commentLoading">发表评论</el-button>
              </div>
              <el-divider />
            </template>

            <div v-if="comments.length > 0" class="comment-list">
              <div v-for="comment in comments" :key="comment.id" class="comment-item">
                <el-avatar :size="36">{{ (comment.username || '匿名')[0] }}</el-avatar>
                <div class="comment-content">
                  <div class="comment-header">
                    <span class="username">{{ comment.username || '匿名用户' }}</span>
                    <span class="time">{{ formatDate(comment.createTime) }}</span>
                  </div>
                  <p class="text">{{ comment.content }}</p>
                </div>
              </div>
            </div>
            <el-empty v-else description="暂无评论" />
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
import { postApi } from '@/api'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const commentLoading = ref(false)
const post = ref(null)
const comments = ref([])
const commentContent = ref('')

const goBack = () => router.back()

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN')
}

const scrollToComment = () => {
  document.getElementById('comment-section')?.scrollIntoView({ behavior: 'smooth' })
}

const loadPost = async () => {
  try {
    loading.value = true
    const userId = userStore.isLoggedIn ? userStore.userInfo?.id : null
    post.value = await postApi.getDetail(route.params.id, userId)
    await loadComments()
  } catch (e) {
    console.error(e)
    ElMessage.error('加载帖子详情失败')
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    comments.value = await postApi.getComments(route.params.id)
  } catch (e) {
    console.error(e)
  }
}

const handleLike = async () => {
  try {
    if (post.value.isLiked) {
      await postApi.unlike(userStore.userInfo.id, post.value.id)
      post.value.isLiked = false
      post.value.likeCount--
    } else {
      await postApi.like(userStore.userInfo.id, post.value.id)
      post.value.isLiked = true
      post.value.likeCount++
    }
  } catch (e) {
    console.error(e)
  }
}

const handleComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  try {
    commentLoading.value = true
    await postApi.comment({ postId: post.value.id, content: commentContent.value }, userStore.userInfo.id)
    ElMessage.success('评论成功')
    commentContent.value = ''
    post.value.commentCount++
    await loadComments()
  } catch (e) {
    console.error(e)
  } finally {
    commentLoading.value = false
  }
}

onMounted(() => {
  loadPost()
})
</script>

<style scoped lang="scss">
.post-detail {
  .detail-card, .comment-card {
    margin-top: 20px;
  }

  .detail-card {
    .post-header {
      display: flex;
      align-items: center;
      gap: 16px;
      margin-bottom: 20px;

      .post-user {
        .username {
          font-weight: 500;
          color: #303133;
          font-size: 16px;
        }

        .post-time {
          font-size: 13px;
          color: #909399;
        }
      }
    }

    .post-title {
      font-size: 22px;
      font-weight: bold;
      margin-bottom: 16px;
      color: #303133;
    }

    .post-content {
      font-size: 15px;
      color: #606266;
      line-height: 1.8;
      margin-bottom: 20px;
    }

    .post-actions {
      display: flex;
      gap: 12px;
    }
  }

  .comment-card {
    .comment-input {
      margin-bottom: 12px;
    }

    .comment-submit {
      text-align: right;
      margin-bottom: 12px;
    }

    .comment-list {
      .comment-item {
        display: flex;
        gap: 12px;
        padding: 16px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }

        .comment-content {
          flex: 1;

          .comment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 8px;

            .username {
              font-weight: 500;
              color: #303133;
            }

            .time {
              font-size: 12px;
              color: #909399;
            }
          }

          .text {
            color: #606266;
            margin: 0;
          }
        }
      }
    }
  }
}
</style>
