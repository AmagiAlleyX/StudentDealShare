<template>
  <header class="header" :class="{ 'header-scrolled': isScrolled }">
    <div class="header-container">
      <div class="header-left">
        <router-link to="/" class="logo">
          <span class="logo-icon">📚</span>
          <span class="logo-text">资源分享</span>
        </router-link>
        <nav class="nav-menu" :class="{ 'nav-mobile-open': isMobileMenuOpen }">
          <router-link to="/" class="nav-item" active-class="active">
            <el-icon><HomeFilled /></el-icon>
            <span>首页</span>
          </router-link>
          <router-link to="/deals" class="nav-item" active-class="active">
            <el-icon><ShoppingBag /></el-icon>
            <span>优惠</span>
          </router-link>
          <router-link to="/posts" class="nav-item" active-class="active">
            <el-icon><Document /></el-icon>
            <span>帖子</span>
          </router-link>
        </nav>
      </div>

      <div class="header-right">
        <div class="search-box">
          <el-input
            v-model="searchQuery"
            placeholder="搜索资源..."
            :prefix-icon="Search"
            clearable
            class="search-input"
          />
        </div>

        <div class="header-actions">
          <el-dropdown trigger="click">
            <span class="action-btn">
              <el-icon><Setting /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="toggleTheme">
                  <el-icon>
                    <component :is="themeStore.isDark ? 'Sunny' : 'Moon'" />
                  </el-icon>
                  {{ themeStore.isDark ? '浅色模式' : '深色模式' }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>

        <button class="mobile-menu-btn" @click="toggleMobileMenu">
          <el-icon><component :is="isMobileMenuOpen ? 'Close' : 'Fold'" /></el-icon>
        </button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useThemeStore } from '@/stores/theme'
import { Search } from '@element-plus/icons-vue'

const themeStore = useThemeStore()
const searchQuery = ref('')
const isScrolled = ref(false)
const isMobileMenuOpen = ref(false)

function toggleTheme() {
  themeStore.toggleTheme()
}

function toggleMobileMenu() {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
}

function handleScroll() {
  isScrolled.value = window.scrollY > 10
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 64px;
  background-color: var(--header-bg);
  border-bottom: 1px solid var(--border-color);
  z-index: 1000;
  transition: all 0.3s ease;
}

.header-scrolled {
  box-shadow: var(--shadow-md);
}

.header-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 32px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
}

.logo-icon {
  font-size: 24px;
}

.logo-text {
  display: none;
}

@media (min-width: 768px) {
  .logo-text {
    display: inline;
  }
}

.nav-menu {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: var(--header-bg);
  border-bottom: 1px solid var(--border-color);
  padding: 12px;
  box-shadow: var(--shadow-md);
  flex-direction: column;
  gap: 8px;
}

@media (min-width: 768px) {
  .nav-menu {
    display: flex;
    gap: 4px;
    position: static;
    flex-direction: row;
    background-color: transparent;
    border-bottom: none;
    padding: 0;
    box-shadow: none;
  }
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 12px;
  border-radius: 6px;
  color: var(--text-secondary);
  font-size: 14px;
  transition: all 0.2s ease;
  cursor: pointer;
}

.nav-item:hover {
  background-color: var(--hover-bg);
  color: var(--text-primary);
}

.nav-item.active {
  background-color: var(--active-bg);
  color: var(--accent-color);
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.search-box {
  display: none;
}

@media (min-width: 768px) {
  .search-box {
    display: block;
    width: 240px;
  }
}

@media (min-width: 1024px) {
  .search-box {
    width: 320px;
  }
}

.search-input :deep(.el-input__wrapper) {
  background-color: var(--input-bg);
  box-shadow: none;
  border: 1px solid var(--border-color);
}

.search-input :deep(.el-input__wrapper:hover) {
  border-color: var(--border-hover);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: transparent;
  color: var(--text-secondary);
  transition: all 0.2s ease;
}

.action-btn:hover {
  background-color: var(--hover-bg);
  color: var(--text-primary);
}

.mobile-menu-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 6px;
  background: transparent;
  color: var(--text-secondary);
}

@media (min-width: 768px) {
  .mobile-menu-btn {
    display: none;
  }
}

@media (max-width: 767px) {
  .nav-mobile-open {
    display: flex !important;
  }
}
</style>
