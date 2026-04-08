import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(null)
  const isLoggedIn = ref(false)

  const setUserInfo = (info) => {
    userInfo.value = info
    isLoggedIn.value = !!info
    if (info) {
      localStorage.setItem('userInfo', JSON.stringify(info))
    } else {
      localStorage.removeItem('userInfo')
    }
  }

  const logout = () => {
    userInfo.value = null
    isLoggedIn.value = false
    localStorage.removeItem('userInfo')
  }

  const initUser = () => {
    const saved = localStorage.getItem('userInfo')
    if (saved) {
      try {
        userInfo.value = JSON.parse(saved)
        isLoggedIn.value = true
      } catch {
        localStorage.removeItem('userInfo')
      }
    }
  }

  return {
    userInfo,
    isLoggedIn,
    setUserInfo,
    logout,
    initUser
  }
})
