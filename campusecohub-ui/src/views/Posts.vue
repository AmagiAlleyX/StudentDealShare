<template>
  <Layout>
    <div class="posts">
      <div class="posts-header">
        <el-button type="primary" v-if="userStore.isLoggedIn" @click="showCreateDialog = true">
          <el-icon><Edit /></el-icon>
          发布帖子
        </el-button>
      </div>

      <el-skeleton :loading="loading" :rows="5" animated>
        <div v-if="posts.length > 0" class="post-list">
          <el-card v-for="post in posts" :key="post.id" class="post-card" shadow="hover" @click="goDetail(post.id)">
            <div class="post-header">
              <el-avatar :size="40">{{ (post.username || '匿名')[0] }}</el-avatar>
              <div class="post-user">
                <div class="username">{{ post.username || '匿名用户' }}</div>
                <div class="post-time">{{ formatDate(post.createTime) }}</div>
              </div>
            </div>
            <h3 class="post-title">{{ post.title }}</h3>
            <p class="post-content">{{ post.content }}</p>
            <div class="post-footer">
              <span class="stat">
                <el-icon><Star /></el-icon>
                {{ post.likeCount }}
              </span>
              <span class="stat">
                <el-icon><ChatLineRound /></el-icon>
                {{ post.commentCount }}
              </span>
            </div>
          </el-card>
        </div>
        <el-empty v-else description="暂无帖子" />
      </el-skeleton>

      <div class="pagination" v-if="total > 0">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadPosts"
          @current-change="loadPosts"
        />
      </div>

      <el-dialog v-model="showCreateDialog" title="发布帖子" width="600px">
        <el-form :model="createForm" label-width="80px">
          <el-form-item label="标题">
            <el-input v-model="createForm.title" placeholder="请输入标题" />
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              v-model="createForm.content"
              type="textarea"
              :rows="6"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="showCreateDialog = false">取消</el-button>
          <el-button type="primary" @click="handleCreate" :loading="createLoading">发布</el-button>
        </template>
      </el-dialog>
    </div>
  </Layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Layout from '@/components/Layout.vue'
import { postApi } from '@/api'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const createLoading = ref(false)
const posts = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const showCreateDialog = ref(false)

const createForm = reactive({
  title: '',
  content: ''
})

const goDetail = (id) => router.push(`/post/${id}`)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN')
}

const loadPosts = async () => {
  try {
    loading.value = true
    const params = { pageNum: pageNum.value, pageSize: pageSize.value }
    if (userStore.isLoggedIn) {
      params.userId = userStore.userInfo?.id
    }
    const data = await postApi.getList(params)
    posts.value = data.posts || []
    total.value = data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handleCreate = async () => {
  if (!createForm.title || !createForm.content) {
    ElMessage.warning('请填写完整信息')
    return
  }
  try {
    createLoading.value = true
    await postApi.create(createForm, userStore.userInfo.id)
    ElMessage.success('发布成功')
    showCreateDialog.value = false
    createForm.title = ''
    createForm.content = ''
    loadPosts()
  } catch (e) {
    console.error(e)
  } finally {
    createLoading.value = false
  }
}

onMounted(() => {
  loadPosts()
})
</script>

<style scoped lang="scss">
.posts {
  .posts-header {
    margin-bottom: 20px;
    text-align: right;
  }

  .post-list {
    .post-card {
      margin-bottom: 20px;
      cursor: pointer;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-2px);
      }

      .post-header {
        display: flex;
        align-items: center;
        gap: 12px;
        margin-bottom: 12px;

        .post-user {
          .username {
            font-weight: 500;
            color: #303133;
          }

          .post-time {
            font-size: 12px;
            color: #909399;
          }
        }
      }

      .post-title {
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 8px;
        color: #303133;
      }

      .post-content {
        font-size: 14px;
        color: #606266;
        margin-bottom: 12px;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }

      .post-footer {
        display: flex;
        gap: 24px;
        color: #909399;
        font-size: 14px;

        .stat {
          display: flex;
          align-items: center;
          gap: 4px;
        }
      }
    }
  }

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
}
</style>
