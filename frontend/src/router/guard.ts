import type { Router } from 'vue-router'
import { ElMessage } from 'element-plus'

// 不需要登录验证的路由（包括首页）
const whiteList = ['/login', '/register', '/']

// 检查 token 是否过期
function isTokenExpired(expireTime: number | null): boolean {
  if (!expireTime) return true
  return Date.now() > expireTime
}

// 从 localStorage 获取登录状态
function getLoginStatus() {
  const token = localStorage.getItem('token')
  const expireTime = localStorage.getItem('tokenExpireTime')
  

  
  if (!token) {
    return { isLoggedIn: false, token: null }
  }
  
  const expireTimeNum = expireTime ? parseInt(expireTime) : null
  const expired = isTokenExpired(expireTimeNum)
  

  
  if (expired) {
    // Token 过期，清除所有缓存
    localStorage.removeItem('token')
    localStorage.removeItem('tokenExpireTime')
    localStorage.removeItem('userInfo')
    return { isLoggedIn: false, token: null }
  }
  
  return {
    isLoggedIn: true,
    token: token,
  }
}

// 设置路由守卫
export function setupRouterGuard(router: Router) {
  // 全局前置路由守卫
  router.beforeEach((to, from, next) => {
    const { isLoggedIn } = getLoginStatus()
    
    // 如果在白名单中（包括首页），直接放行
    if (whiteList.includes(to.path)) {
      // 如果已登录且访问登录/注册页，重定向到首页
      if (isLoggedIn && (to.path === '/login' || to.path === '/register')) {
        next('/')
      } else {
        next()
      }
      return
    }
    
    // 如果不在白名单中，需要验证 token
    if (!isLoggedIn) {
      ElMessage.warning('请先登录')
      next({
        path: '/login',
        query: { redirect: to.fullPath },
      })
      return
    }
    
    // token 有效，放行
    next()
  })

  // 全局后置路由守卫
  router.afterEach((to) => {
    // 设置页面标题
    document.title = (to.meta.title as string) || '学生优惠分享平台'
  })
}
