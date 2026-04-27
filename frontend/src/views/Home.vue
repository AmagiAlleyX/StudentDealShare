<template>
  <div class="home-page">
    <div class="home-container">
      <div class="home-layout">
        <!-- 左侧边栏 -->
        <aside class="sidebar-left">
          <div class="sidebar-card">
            <h3 class="sidebar-title">分类</h3>
            <ul class="category-list">
              <li v-for="cat in categories" :key="cat.id" class="category-item">
                <router-link :to="`/category/${cat.id}`" class="category-link">
                  <span class="category-icon">{{ cat.icon }}</span>
                  <span class="category-name">{{ cat.name }}</span>
                  <span class="category-count">{{ cat.count }}</span>
                </router-link>
              </li>
            </ul>
          </div>

          <div class="sidebar-card">
            <h3 class="sidebar-title">快速操作</h3>
            <div class="quick-actions">
              <el-button type="primary" class="action-btn" @click="showCreateDialog = true">
                <el-icon><Plus /></el-icon>
                发布帖子
              </el-button>
              <el-button class="action-btn" @click="showDealDialog = true">
                <el-icon><ShoppingBag /></el-icon>
                发布优惠
              </el-button>
            </div>
          </div>
        </aside>

        <!-- 主内容区 -->
        <main class="main-content">
          <!-- 顶部选项卡 -->
          <div class="content-tabs">
            <el-tabs v-model="activeTab" class="tabs">
              <el-tab-pane label="最新" name="latest" />
              <el-tab-pane label="热门" name="hot" />
              <el-tab-pane label="精华" name="essence" />
            </el-tabs>
          </div>

          <!-- 帖子列表 -->
          <div class="post-list">
            <div v-for="post in posts" :key="post.id" class="post-card card">
              <div class="post-header">
                <div class="post-author">
                  <el-avatar :size="40" :src="post.authorAvatar">{{ post.authorName?.charAt(0) }}</el-avatar>
                  <div class="author-info">
                    <span class="author-name">{{ post.authorName }}</span>
                    <span class="post-time">{{ formatTime(post.createdAt) }}</span>
                  </div>
                </div>
                <div class="post-tags">
                  <span v-if="post.top === 1" class="tag tag-top">置顶</span>
                  <span v-if="post.essence === 1" class="tag tag-essence">精华</span>
                </div>
              </div>

              <router-link :to="`/post/${post.id}`" class="post-body">
                <h3 class="post-title">{{ post.title }}</h3>
                <p class="post-excerpt">{{ post.content?.substring(0, 150) }}...</p>
              </router-link>

              <div class="post-footer">
                <div class="post-stats">
                  <span class="stat-item">
                    <el-icon><View /></el-icon>
                    {{ post.viewCount || 0 }}
                  </span>
                  <span class="stat-item">
                    <el-icon><ChatLineRound /></el-icon>
                    {{ post.commentCount || 0 }}
                  </span>
                  <span class="stat-item">
                    <el-icon><Star /></el-icon>
                    {{ post.favoriteCount || 0 }}
                  </span>
                  <span class="stat-item">
                    <el-icon><Opportunity /></el-icon>
                    {{ post.likeCount || 0 }}
                  </span>
                </div>
                <div class="post-actions">
                  <el-button text @click="handleLike(post)">
                    <el-icon><Opportunity /></el-icon>
                    点赞
                  </el-button>
                  <el-button text>
                    <el-icon><Star /></el-icon>
                    收藏
                  </el-button>
                  <el-button text>
                    <el-icon><Share /></el-icon>
                    分享
                  </el-button>
                </div>
              </div>
            </div>
          </div>

          <!-- 加载更多 -->
          <div class="load-more">
            <el-button @click="loadMore" :loading="loading">加载更多</el-button>
          </div>
        </main>

        <!-- 右侧边栏 -->
        <aside class="sidebar-right">
          <!-- 用户卡片 -->
          <div class="sidebar-card user-card">
            <div class="user-header">
              <el-avatar :size="60">学</el-avatar>
              <h4>欢迎来到资源分享平台</h4>
              <p>登录后体验完整功能</p>
            </div>
            <div class="user-actions">
              <el-button type="primary" class="login-btn">登录</el-button>
              <el-button class="register-btn">注册</el-button>
            </div>
          </div>

          <!-- 热门优惠 -->
          <div class="sidebar-card">
            <h3 class="sidebar-title">热门优惠</h3>
            <ul class="hot-deals">
              <li v-for="deal in hotDeals" :key="deal.id" class="hot-deal-item">
                <router-link :to="`/deal/${deal.id}`" class="deal-link">
                  <span class="deal-title">{{ deal.title }}</span>
                  <span class="deal-price">¥{{ deal.price }}</span>
                </router-link>
              </li>
            </ul>
          </div>

          <!-- 活跃用户 -->
          <div class="sidebar-card">
            <h3 class="sidebar-title">活跃用户</h3>
            <ul class="active-users">
              <li v-for="user in activeUsers" :key="user.id" class="active-user-item">
                <el-avatar :size="36">{{ user.nickname?.charAt(0) }}</el-avatar>
                <span class="user-nickname">{{ user.nickname }}</span>
              </li>
            </ul>
          </div>

          <!-- 平台统计 -->
          <div class="sidebar-card">
            <h3 class="sidebar-title">平台统计</h3>
            <div class="platform-stats">
              <div class="stat-row">
                <span>帖子总数</span>
                <span class="stat-value">1,234</span>
              </div>
              <div class="stat-row">
                <span>优惠信息</span>
                <span class="stat-value">567</span>
              </div>
              <div class="stat-row">
                <span>注册用户</span>
                <span class="stat-value">2,890</span>
              </div>
            </div>
          </div>
        </aside>
      </div>
    </div>

    <!-- 发布帖子对话框 -->
    <el-dialog v-model="showCreateDialog" title="发布帖子" width="600px">
      <el-form label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="newPost.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="newPost.content" type="textarea" :rows="6" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="newPost.categoryId" placeholder="选择分类">
            <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreatePost">发布</el-button>
      </template>
    </el-dialog>

    <!-- 发布优惠对话框 -->
    <el-dialog v-model="showDealDialog" title="发布优惠" width="600px">
      <el-form label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="newDeal.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="newDeal.description" type="textarea" :rows="4" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input-number v-model="newDeal.price" :min="0" :precision="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDealDialog = false">取消</el-button>
        <el-button type="primary" @click="handleCreateDeal">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

const activeTab = ref('latest')
const loading = ref(false)
const showCreateDialog = ref(false)
const showDealDialog = ref(false)

const categories = ref([
  { id: 1, name: '学习资料', icon: '📖', count: 156 },
  { id: 2, name: '生活分享', icon: '🌟', count: 234 },
  { id: 3, name: '求职招聘', icon: '💼', count: 89 },
  { id: 4, name: '二手交易', icon: '🔄', count: 312 },
  { id: 5, name: '活动信息', icon: '🎉', count: 67 },
  { id: 6, name: '技术问题', icon: '💻', count: 445 },
])

const posts = ref([
  {
    id: 1,
    title: '2024年考研资料分享，包含数学、英语、政治等科目',
    content: '整理了近三年的考研真题和解析，还有一些复习笔记，希望对大家有帮助。包含数学一、数学二、英语一、英语二、政治等科目的复习资料。',
    authorName: '学霸小明',
    authorAvatar: '',
    createdAt: '2024-01-15 10:30:00',
    viewCount: 1234,
    commentCount: 45,
    favoriteCount: 89,
    likeCount: 156,
    top: 1,
    essence: 1,
  },
  {
    id: 2,
    title: '校园周边美食推荐，学生党必看！',
    content: '学校周边开了很多新餐厅，我去尝试了几家，给大家整理了一份美食攻略。包括价格、口味、环境等详细信息。',
    authorName: '吃货小红',
    authorAvatar: '',
    createdAt: '2024-01-15 09:15:00',
    viewCount: 892,
    commentCount: 67,
    favoriteCount: 123,
    likeCount: 234,
    top: 0,
    essence: 0,
  },
  {
    id: 3,
    title: 'Java 后端开发学习路线分享',
    content: '从零基础到能独立开发项目，分享一下我的学习经历。包括需要掌握的技术栈、推荐的学习资源、项目实践建议等。',
    authorName: '程序猿小李',
    authorAvatar: '',
    createdAt: '2024-01-14 20:45:00',
    viewCount: 2345,
    commentCount: 89,
    favoriteCount: 234,
    likeCount: 456,
    top: 0,
    essence: 1,
  },
  {
    id: 4,
    title: '闲置书籍出售，计算机类教材低价出',
    content: '毕业清理宿舍，有一些计算机专业的教材和参考书，包括《算法导论》、《深入理解计算机系统》等，价格优惠。',
    authorName: '毕业生小王',
    authorAvatar: '',
    createdAt: '2024-01-14 16:20:00',
    viewCount: 456,
    commentCount: 23,
    favoriteCount: 34,
    likeCount: 45,
    top: 0,
    essence: 0,
  },
])

const hotDeals = ref([
  { id: 1, title: '亚马逊学生会员半价优惠', price: 49 },
  { id: 2, title: '京东学生专享优惠券', price: 0 },
  { id: 3, title: 'Apple 教育优惠', price: 7999 },
  { id: 4, title: 'Microsoft 365 学生版', price: 298 },
])

const activeUsers = ref([
  { id: 1, nickname: '学霸小明' },
  { id: 2, nickname: '吃货小红' },
  { id: 3, nickname: '程序猿小李' },
  { id: 4, nickname: '设计师小张' },
  { id: 5, nickname: '摄影师小刘' },
])

const newPost = ref({
  title: '',
  content: '',
  categoryId: null,
})

const newDeal = ref({
  title: '',
  description: '',
  price: 0,
})

function formatTime(time: string) {
  const date = new Date(time)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const minutes = Math.floor(diff / 60000)
  const hours = Math.floor(diff / 3600000)
  const days = Math.floor(diff / 86400000)

  if (minutes < 60) return `${minutes}分钟前`
  if (hours < 24) return `${hours}小时前`
  if (days < 7) return `${days}天前`
  return time
}

function handleLike(post: any) {
  post.likeCount++
}

function loadMore() {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 1000)
}

function handleCreatePost() {
  console.log('发布帖子', newPost.value)
  showCreateDialog.value = false
}

function handleCreateDeal() {
  console.log('发布优惠', newDeal.value)
  showDealDialog.value = false
}

onMounted(() => {
  // 加载数据
})
</script>

<style scoped>
.home-page {
  min-height: calc(100vh - 64px);
  background-color: var(--bg-secondary);
  padding: 20px 0;
}

.home-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
}

.home-layout {
  display: grid;
  grid-template-columns: 240px 1fr 280px;
  gap: 20px;
}

@media (max-width: 1200px) {
  .home-layout {
    grid-template-columns: 1fr 280px;
  }
  .sidebar-left {
    display: none;
  }
}

@media (max-width: 900px) {
  .home-layout {
    grid-template-columns: 1fr;
  }
  .sidebar-right {
    display: none;
  }
}

/* 左侧边栏 */
.sidebar-left {
  position: sticky;
  top: 84px;
  height: fit-content;
}

.sidebar-card {
  background: var(--card-bg);
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  border: 1px solid var(--border-color);
}

.sidebar-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px solid var(--border-color);
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.category-item {
  border-radius: 6px;
  transition: background-color 0.2s ease;
}

.category-item:hover {
  background-color: var(--hover-bg);
}

.category-link {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  color: var(--text-primary);
}

.category-icon {
  font-size: 18px;
}

.category-name {
  flex: 1;
  font-size: 14px;
}

.category-count {
  font-size: 12px;
  color: var(--text-tertiary);
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.action-btn {
  width: 100%;
}

/* 主内容区 */
.main-content {
  min-width: 0;
}

.content-tabs {
  background: var(--card-bg);
  border-radius: 8px;
  padding: 0 16px;
  margin-bottom: 16px;
  border: 1px solid var(--border-color);
}

.tabs :deep(.el-tabs__header) {
  margin-bottom: 0;
}

.post-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.post-card {
  padding: 20px;
}

.post-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.post-author {
  display: flex;
  align-items: center;
  gap: 12px;
}

.author-info {
  display: flex;
  flex-direction: column;
}

.author-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
}

.post-time {
  font-size: 12px;
  color: var(--text-tertiary);
}

.post-tags {
  display: flex;
  gap: 6px;
}

.tag-top {
  background-color: var(--error-color);
  color: #ffffff;
}

.tag-essence {
  background-color: var(--success-color);
  color: #ffffff;
}

.post-body {
  display: block;
  margin-bottom: 12px;
}

.post-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
  line-height: 1.4;
}

.post-title:hover {
  color: var(--accent-color);
}

.post-excerpt {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.post-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 12px;
  border-top: 1px solid var(--border-color);
}

.post-stats {
  display: flex;
  gap: 16px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: var(--text-tertiary);
}

.post-actions {
  display: flex;
  gap: 4px;
}

.load-more {
  text-align: center;
  padding: 20px 0;
}

/* 右侧边栏 */
.sidebar-right {
  position: sticky;
  top: 84px;
  height: fit-content;
}

.user-card {
  text-align: center;
}

.user-header {
  margin-bottom: 16px;
}

.user-header h4 {
  font-size: 16px;
  font-weight: 600;
  margin: 12px 0 4px;
  color: var(--text-primary);
}

.user-header p {
  font-size: 13px;
  color: var(--text-tertiary);
}

.user-actions {
  display: flex;
  gap: 8px;
}

.login-btn, .register-btn {
  flex: 1;
}

.hot-deals {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.hot-deal-item {
  border-radius: 6px;
  transition: background-color 0.2s ease;
}

.hot-deal-item:hover {
  background-color: var(--hover-bg);
}

.deal-link {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 8px;
  color: var(--text-primary);
}

.deal-title {
  font-size: 13px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.deal-price {
  font-size: 14px;
  font-weight: 600;
  color: var(--error-color);
}

.active-users {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.active-user-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-nickname {
  font-size: 14px;
  color: var(--text-primary);
}

.platform-stats {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.stat-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: var(--text-secondary);
}

.stat-value {
  font-weight: 600;
  color: var(--text-primary);
}
</style>
