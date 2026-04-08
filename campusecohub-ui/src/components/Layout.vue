<template>
  <el-container class="layout-container">
    <el-header class="layout-header">
      <div class="header-content">
        <div class="logo" @click="goHome">
          <el-icon><School /></el-icon>
          <span>校园资源分享</span>
        </div>
        <el-menu
          :default-active="activeMenu"
          mode="horizontal"
          :ellipsis="false"
          router
          class="header-menu"
        >
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/activities">活动</el-menu-item>
          <el-menu-item index="/posts">帖子</el-menu-item>
        </el-menu>
        <div class="header-user">
          <template v-if="userStore.isLoggedIn">
            <el-button type="primary" @click="goProfile">
              <el-icon><User /></el-icon>
              {{ userStore.userInfo?.username }}
            </el-button>
            <el-button @click="handleLogout">退出</el-button>
          </template>
          <template v-else>
            <el-button @click="goLogin">登录</el-button>
            <el-button type="primary" @click="goRegister">注册</el-button>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="layout-main">
      <slot />
    </el-main>
    <el-footer class="layout-footer">
      <p>© 2024 校园经济资源分享平台</p>
    </el-footer>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const goHome = () => router.push('/home')
const goLogin = () => router.push('/login')
const goRegister = () => router.push('/register')
const goProfile = () => router.push('/profile')

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    userStore.logout()
    ElMessage.success('退出成功')
    router.push('/home')
  } catch {
  }
}
</script>

<style scoped lang="scss">
.layout-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.layout-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 0;
  height: 60px;

  .header-content {
    max-width: 1200px;
    margin: 0 auto;
    height: 100%;
    display: flex;
    align-items: center;
    padding: 0 20px;

    .logo {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 20px;
      font-weight: bold;
      color: #409eff;
      cursor: pointer;
      margin-right: 40px;
    }

    .header-menu {
      flex: 1;
      border: none;
    }

    .header-user {
      display: flex;
      gap: 12px;
    }
  }
}

.layout-main {
  flex: 1;
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  padding: 20px;
}

.layout-footer {
  text-align: center;
  padding: 20px;
  color: #909399;
  background: #f5f7fa;
}

@media (max-width: 768px) {
  .layout-header {
    .header-content {
      padding: 0 10px;

      .logo span {
        display: none;
      }

      .header-menu {
        display: none;
      }
    }
  }
}
</style>
