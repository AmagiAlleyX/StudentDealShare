<template>
  <Layout>
    <div class="activities">
      <el-card class="filter-card">
        <el-form :inline="true" :model="filters">
          <el-form-item label="分类">
            <el-select v-model="filters.category" placeholder="全部分类" clearable>
              <el-option label="优惠活动" value="优惠活动" />
              <el-option label="兼职信息" value="兼职信息" />
              <el-option label="二手交易" value="二手交易" />
              <el-option label="其他" value="其他" />
            </el-select>
          </el-form-item>
          <el-form-item label="来源">
            <el-select v-model="filters.source" placeholder="全部来源" clearable>
              <el-option label="官方" value="官方" />
              <el-option label="学生分享" value="学生分享" />
            </el-select>
          </el-form-item>
          <el-form-item label="搜索">
            <el-input v-model="filters.keyword" placeholder="搜索活动" clearable style="width: 200px" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="loadActivities">搜索</el-button>
            <el-button @click="resetFilters">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <el-skeleton :loading="loading" :rows="5" animated>
        <div v-if="activities.length > 0" class="activity-list">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="12" :md="8" v-for="activity in activities" :key="activity.id">
              <el-card class="activity-card" shadow="hover" @click="goDetail(activity.id)">
                <div class="card-header">
                  <el-tag size="small" type="success">{{ activity.category }}</el-tag>
                  <el-tag v-if="activity.isStudentExclusive" size="small" type="warning">学生专属</el-tag>
                </div>
                <h3 class="card-title">{{ activity.title }}</h3>
                <p class="card-desc">{{ activity.description }}</p>
                <div class="card-footer">
                  <span class="time">
                    <el-icon><Clock /></el-icon>
                    {{ formatDate(activity.startTime) }}
                  </span>
                  <span class="source">{{ activity.source }}</span>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        <el-empty v-else description="暂无活动" />
      </el-skeleton>

      <div class="pagination" v-if="total > 0">
        <el-pagination
          v-model:current-page="filters.pageNum"
          v-model:page-size="filters.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadActivities"
          @current-change="loadActivities"
        />
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Layout from '@/components/Layout.vue'
import { activityApi } from '@/api'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const activities = ref([])
const total = ref(0)

const filters = reactive({
  category: '',
  source: '',
  keyword: '',
  pageNum: 1,
  pageSize: 10
})

const goDetail = (id) => router.push(`/activity/${id}`)

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const loadActivities = async () => {
  try {
    loading.value = true
    const params = { ...filters }
    if (userStore.isLoggedIn) {
      params.userId = userStore.userInfo?.id
    }
    const data = await activityApi.getList(params)
    activities.value = data.activities || []
    total.value = data.total || 0
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const resetFilters = () => {
  filters.category = ''
  filters.source = ''
  filters.keyword = ''
  filters.pageNum = 1
  loadActivities()
}

onMounted(() => {
  loadActivities()
})
</script>

<style scoped lang="scss">
.activities {
  .filter-card {
    margin-bottom: 20px;
  }

  .activity-list {
    .activity-card {
      margin-bottom: 20px;
      cursor: pointer;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-4px);
      }

      .card-header {
        display: flex;
        gap: 8px;
        margin-bottom: 12px;
      }

      .card-title {
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 8px;
        color: #303133;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .card-desc {
        font-size: 14px;
        color: #909399;
        margin-bottom: 12px;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }

      .card-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #909399;
        font-size: 13px;

        .time {
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
