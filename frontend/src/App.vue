<template>
  <div class="app">
    <HeaderNav />
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useThemeStore } from '@/stores/theme'
import HeaderNav from '@/components/layout/HeaderNav.vue'

const themeStore = useThemeStore()

onMounted(() => {
  themeStore.initTheme()
})
</script>

<style scoped>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  padding-top: 64px;
}
</style>
