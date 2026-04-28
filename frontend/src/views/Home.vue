<template>
  <div class="home-page">
    <div class="home-container">
      <div class="home-layout">
        <!-- 左侧边栏 -->
        <aside class="sidebar-left">
          <div class="sidebar-card">
            <h3 class="sidebar-title">分类</h3>
            <ul class="category-list">
              <li v-for="cat in categories" :key="cat.categoryId || cat.id" class="category-item">
                <router-link :to="`/category/${cat.categoryId || cat.id}`" class="category-link">
                  <el-icon class="category-icon">
                    <component :is="getCategoryIcon(cat.name)" />
                  </el-icon>
                  <span class="category-name">{{ cat.name }}</span>
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
            <!-- 未登录状态 -->
            <template v-if="!userStore.isLoggedIn">
              <div class="user-header">
                <el-avatar :size="60">学</el-avatar>
                <h4>欢迎来到资源分享平台</h4>
                <p>登录后体验完整功能</p>
              </div>
              <div class="user-actions">
                <router-link to="/login" class="action-btn-wrapper">
                  <el-button type="primary" class="action-btn-item">登录</el-button>
                </router-link>
                <router-link to="/register" class="action-btn-wrapper">
                  <el-button class="action-btn-item">注册</el-button>
                </router-link>
              </div>
            </template>

            <!-- 已登录状态 -->
            <template v-else>
              <div class="user-header">
                <el-avatar :size="60" :src="userStore.userInfo?.avatar || ''">
                  {{ userStore.userInfo?.nickname?.charAt(0) || userStore.userInfo?.username?.charAt(0) || '学' }}
                </el-avatar>
                <h4>{{ userStore.userInfo?.nickname || userStore.userInfo?.username }}</h4>
                <p>{{ userStore.userInfo?.school || '暂未填写学校' }}</p>
              </div>
              <div class="user-actions">
                <router-link to="/profile" class="action-btn-wrapper">
                  <el-button type="primary" class="action-btn-item">个人中心</el-button>
                </router-link>
                <div class="action-btn-wrapper">
                  <el-button class="action-btn-item" @click="handleLogout">退出登录</el-button>
                </div>
              </div>
            </template>
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

          <!-- 平台统计 -->
          <div class="sidebar-card">
            <h3 class="sidebar-title">平台统计</h3>
            <div class="platform-stats">
              <div class="stat-row">
                <span>帖子总数</span>
                <span class="stat-value">{{ formatNumber(platformStats.postCount) }}</span>
              </div>
              <div class="stat-row">
                <span>优惠信息</span>
                <span class="stat-value">{{ formatNumber(platformStats.dealCount) }}</span>
              </div>
              <div class="stat-row">
                <span>注册用户</span>
                <span class="stat-value">{{ formatNumber(platformStats.userCount) }}</span>
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
import { ref, onMounted, watch } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'
// 类型定义
interface Category {
  categoryId?: number
  id?: number
  name: string
  icon?: string
  count?: number
}

interface Post {
  id: number
  title: string
  content: string
  authorName: string
  authorAvatar?: string
  viewCount: number
  commentCount: number
  favoriteCount: number
  likeCount: number
  top?: number
  essence?: number
  createdAt: string
}

interface Deal {
  id: number
  title: string
  price: number
}

// 分类图标映射
const categoryIcons: Record<string, string> = {
  '学习资料': 'ReadingLamp',
  '生活分享': 'Star',
  '求职招聘': 'Briefcase',
  '二手交易': 'Refresh',
  '活动信息': 'Celebration',
  '技术问题': 'Monitor',
  '优惠': 'ShoppingBag',
  '帖子': 'Document',
}



const userStore = useUserStore()
const router = useRouter()

const activeTab = ref('latest')
const loading = ref(false)
const showCreateDialog = ref(false)
const showDealDialog = ref(false)

const categories = ref<Category[]>([])
const posts = ref<Post[]>([])
const hotDeals = ref<Deal[]>([])

// 平台统计数据
const platformStats = ref({
  postCount: 0,
  dealCount: 0,
  userCount: 0,
})

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

function handleLogout() {
  userStore.logout()
  ElMessage.success('退出登录成功')
  router.push('/')
}

function getCategoryIcon(categoryName: string): string {
  // 根据分类名称匹配图标
  for (const [key, iconName] of Object.entries(categoryIcons)) {
    if (categoryName.includes(key)) {
      return iconName
    }
  }
  // 默认返回 Document 图标
  return 'Document'
}

function formatNumber(num: number): string {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + 'w'
  }
  return num.toLocaleString()
}

async function loadPlatformStats() {
  try {
    // 通过统计帖子和优惠数量来获取平台统计数据
    const [postRes, dealRes] = await Promise.all([
      request.get('/api/post/page', { params: { page: 1, size: 1 } }),
      request.get('/api/deal/page', { params: { page: 1, size: 1 } })
    ])
    
    platformStats.value = {
      postCount: postRes.data.data?.total || 0,
      dealCount: dealRes.data.data?.total || 0,
      userCount: 0, // 暂时无法获取用户总数
    }
  } catch (error) {
    console.error('加载平台统计失败:', error)
    platformStats.value = {
      postCount: 0,
      dealCount: 0,
      userCount: 0,
    }
  }
}

async function loadCategories() {
  try {
    console.log('请求分类数据...')
    const response = await request.get('/api/category/list')
    console.log('分类响应数据:', response.data)
    if (response.data && Array.isArray(response.data.data)) {
      categories.value = response.data.data
      console.log('分类数据加载成功:', categories.value.length, '个分类')
    } else {
      // 加载失败时使用默认分类
      console.warn('分类响应数据结构异常，使用默认分类')
      categories.value = [
        { categoryId: 1, name: '学习资料' },
        { categoryId: 2, name: '生活分享' },
        { categoryId: 3, name: '求职招聘' },
        { categoryId: 4, name: '二手交易' },
        { categoryId: 5, name: '活动信息' },
        { categoryId: 6, name: '技术问题' },
      ]
    }
  } catch (error) {
    console.error('加载分类失败:', error)
    categories.value = []
  }
}

async function loadPosts() {
  try {
    loading.value = true
    let response
    let url = '/api/post/page'
    let params: any = {}
    
    if (activeTab.value === 'latest') {
      // 最新帖子：按创建时间倒序
      params = { page: 1, size: 20 }
    } else if (activeTab.value === 'hot') {
      // 热门帖子
      url = '/api/post/hot'
      params = { limit: 20 }
    } else if (activeTab.value === 'essence') {
      // 精华帖子
      url = '/api/post/essence'
      params = { limit: 20 }
    } else {
      params = { page: 1, size: 20 }
    }
    
    console.log('请求帖子数据，URL:', url, '参数:', params)
    response = await request.get(url, { params })
    console.log('帖子响应数据:', response.data)
    
    if (response?.data?.data) {
      let dataList = []
      const responseData = response.data.data
      
      // 判断响应数据结构
      if (Array.isArray(responseData)) {
        // 直接返回数组
        dataList = responseData
      } else if (responseData.records && Array.isArray(responseData.records)) {
        // MyBatis-Plus 分页响应，取 records 字段
        dataList = responseData.records
        console.log('检测到分页响应，records 数量:', dataList.length)
      } else if (responseData.list && Array.isArray(responseData.list)) {
        // 其他分页响应，取 list 字段
        dataList = responseData.list
      }
      
      console.log('解析后的帖子列表:', dataList)
      posts.value = dataList.map((post: any) => ({
        id: post.id,
        title: post.title,
        content: post.content?.substring(0, 150) || '',
        authorName: post.authorName || '匿名用户',
        authorAvatar: post.authorAvatar || '',
        createdAt: post.createdAt,
        viewCount: post.viewCount || 0,
        commentCount: post.commentCount || 0,
        favoriteCount: post.favoriteCount || 0,
        likeCount: post.likeCount || 0,
        top: post.top || 0,
        essence: post.essence || 0
      }))
      console.log('帖子数据加载成功:', posts.value.length, '个帖子')
    } else {
      console.warn('帖子响应数据结构异常:', response)
      posts.value = []
    }
  } catch (error) {
    console.error('加载帖子失败:', error)
    posts.value = []
  } finally {
    loading.value = false
  }
}

async function loadHotDeals() {
  try {
    console.log('请求热门优惠数据...')
    const response = await request.get('/api/deal/hot', { params: { limit: 10 } })
    console.log('热门优惠响应数据:', response.data)
    if (response?.data?.data && Array.isArray(response.data.data)) {
      hotDeals.value = response.data.data.map((deal: any) => ({
        id: deal.id,
        title: deal.title,
        price: deal.price || deal.dealPrice || 0
      }))
      console.log('热门优惠加载成功:', hotDeals.value.length, '个优惠')
    } else {
      console.warn('热门优惠响应数据结构异常')
      hotDeals.value = []
    }
  } catch (error) {
    console.error('加载热门优惠失败:', error)
    hotDeals.value = []
  }
}

// 监听选项卡变化，重新加载帖子数据
watch(activeTab, () => {
  loadPosts()
})

onMounted(() => {
  loadCategories()
  loadPosts()
  loadHotDeals()
  loadPlatformStats()
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
  gap: 12px;
  width: 100%;
}

.action-btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 12px;
  height: 48px;
  padding: 0 24px;
  box-sizing: border-box;
  font-size: 15px;
  font-weight: 500;
  border-radius: 8px;
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

.tabs :deep(.el-tabs__item) {
  color: var(--text-secondary);
  font-weight: 500;
}

.tabs :deep(.el-tabs__item.is-active) {
  color: var(--accent-color);
  font-weight: 600;
}

.tabs :deep(.el-tabs__active-bar) {
  background-color: var(--accent-color);
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

.tag-top,
.tag-essence {
  background-color: var(--tag-top-bg);
  color: var(--tag-text);
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.tag-essence {
  background-color: var(--tag-essence-bg);
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
  gap: 12px;
  flex-wrap: wrap;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: var(--text-tertiary);
  white-space: nowrap;
  min-width: fit-content;
}

@media (max-width: 768px) {
  .stat-item {
    font-size: 11px;
    gap: 2px;
  }
  
  .post-stats {
    gap: 8px;
  }
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

.action-btn-wrapper {
  flex: 1;
}

.action-btn-item {
  width: 100%;
  font-weight: 600;
  border: 2px solid var(--border-color);
  transition: all 0.3s ease;
  height: 40px;
  padding: 0 16px;
  box-sizing: border-box;
}

.action-btn-item[type="primary"] {
  background-color: var(--accent-color);
  color: #ffffff;
  border-color: var(--accent-color);
}

.action-btn-item[type="primary"]:hover {
  background-color: var(--accent-hover);
  border-color: var(--accent-hover);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
}

.action-btn-item:not([type="primary"]) {
  background-color: transparent;
  color: var(--text-primary);
  border-color: var(--text-primary);
}

.action-btn-item:not([type="primary"]):hover {
  background-color: var(--text-primary);
  color: var(--bg-primary);
  transform: translateY(-2px);
  box-shadow: var(--shadow-md);
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
