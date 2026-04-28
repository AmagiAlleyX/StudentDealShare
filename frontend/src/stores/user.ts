import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authService } from '@/api/auth'

// 用户信息接口
export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar?: string
  email?: string
  phone?: string
  gender?: number
  school?: string
  college?: string
}

// 存储键名
const STORAGE_KEYS = {
  TOKEN: 'token',
  TOKEN_EXPIRE_TIME: 'tokenExpireTime',
  USER_INFO: 'userInfo',
}

export const useUserStore = defineStore('user', () => {
  // 状态
  const token = ref<string | null>(null)
  const tokenExpireTime = ref<number | null>(null)
  const userInfo = ref<UserInfo | null>(null)

  // 计算属性：是否已登录
  const isLoggedIn = computed(() => {
    return !!token.value && !isTokenExpired.value
  })

  // 计算属性：token 是否过期
  const isTokenExpired = computed(() => {
    if (!tokenExpireTime.value) return true
    return Date.now() > tokenExpireTime.value
  })

  // 方法：从本地存储加载用户信息
  function loadFromStorage() {
    const storedToken = localStorage.getItem(STORAGE_KEYS.TOKEN)
    const storedExpireTime = localStorage.getItem(STORAGE_KEYS.TOKEN_EXPIRE_TIME)
    const storedUserInfo = localStorage.getItem(STORAGE_KEYS.USER_INFO)

    if (storedToken) {
      token.value = storedToken
    }

    if (storedExpireTime) {
      tokenExpireTime.value = parseInt(storedExpireTime)
    }

    if (storedUserInfo) {
      try {
        userInfo.value = JSON.parse(storedUserInfo)
      } catch (e) {
        console.error('解析用户信息失败:', e)
        localStorage.removeItem(STORAGE_KEYS.USER_INFO)
      }
    }
  }

  // 方法：保存登录信息
  function setLoginInfo(newToken: string, expireTime: number, info: UserInfo) {
    token.value = newToken
    tokenExpireTime.value = expireTime
    userInfo.value = info

    // 持久化到 localStorage
    localStorage.setItem(STORAGE_KEYS.TOKEN, newToken)
    localStorage.setItem(STORAGE_KEYS.TOKEN_EXPIRE_TIME, expireTime.toString())
    localStorage.setItem(STORAGE_KEYS.USER_INFO, JSON.stringify(info))
  }

  // 方法：退出登录
  function logout() {
    token.value = null
    tokenExpireTime.value = null
    userInfo.value = null

    // 清除本地存储
    localStorage.removeItem(STORAGE_KEYS.TOKEN)
    localStorage.removeItem(STORAGE_KEYS.TOKEN_EXPIRE_TIME)
    localStorage.removeItem(STORAGE_KEYS.USER_INFO)
  }

  // 方法：更新用户信息
  function updateUserInfo(info: Partial<UserInfo>) {
    if (userInfo.value) {
      userInfo.value = { ...userInfo.value, ...info }
      // 同步到 localStorage
      localStorage.setItem(STORAGE_KEYS.USER_INFO, JSON.stringify(userInfo.value))
    }
  }

  // 方法：检查登录状态
  function checkLoginStatus() {
    if (!token.value) {
      return false
    }

    if (isTokenExpired.value) {
      logout()
      return false
    }

    return true
  }

  // 初始化：从本地存储加载
  loadFromStorage()

  return {
    // 状态
    token,
    tokenExpireTime,
    userInfo,
    // 计算属性
    isLoggedIn,
    isTokenExpired,
    // 方法
    loadFromStorage,
    setLoginInfo,
    logout,
    updateUserInfo,
    checkLoginStatus,
  }
})
