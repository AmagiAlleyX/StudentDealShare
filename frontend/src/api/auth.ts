import request from '@/utils/request'

// 登录参数
export interface LoginParams {
  username: string
  password: string
}

// 注册参数
export interface RegisterParams {
  username: string
  email: string
  phone?: string
  password: string
}

// 登录响应
export interface LoginResponse {
  token: string
  expireTime: number
  userInfo: {
    id: number
    username: string
    nickname: string
    avatar?: string
    email?: string
    phone?: string
  }
}

// 认证服务
export const authService = {
  /**
   * 用户登录
   */
  login(data: LoginParams) {
    return request.post<LoginResponse>('/api/user/login', data)
  },

  /**
   * 用户注册
   */
  register(data: RegisterParams) {
    return request.post('/api/user/register', data)
  },

  /**
   * 退出登录
   */
  logout() {
    return request.post('/api/user/logout')
  },

  /**
   * 获取当前用户信息
   */
  getCurrentUser() {
    return request.get('/api/user/me')
  },

  /**
   * 更新当前用户信息
   */
  updateCurrentUser(data: any) {
    return request.put('/api/user/update', data)
  },
}
