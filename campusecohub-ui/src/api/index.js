import request from '@/utils/request'

export const userApi = {
  register(data) {
    return request({
      url: '/user/register',
      method: 'post',
      data
    })
  },
  login(data) {
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  },
  getInfo(userId) {
    return request({
      url: '/user/info',
      method: 'get',
      params: { userId }
    })
  },
  studentAuth(userId, data) {
    return request({
      url: '/user/student/auth',
      method: 'post',
      params: { userId },
      data
    })
  }
}

export const activityApi = {
  create(data) {
    return request({
      url: '/activity/create',
      method: 'post',
      data
    })
  },
  getDetail(id, userId) {
    return request({
      url: `/activity/detail/${id}`,
      method: 'get',
      params: { userId }
    })
  },
  getList(params) {
    return request({
      url: '/activity/list',
      method: 'get',
      params
    })
  },
  collect(userId, activityId) {
    return request({
      url: '/activity/collect',
      method: 'post',
      params: { userId, activityId }
    })
  },
  uncollect(userId, activityId) {
    return request({
      url: '/activity/uncollect',
      method: 'post',
      params: { userId, activityId }
    })
  },
  getCollections(userId, pageNum = 1, pageSize = 10) {
    return request({
      url: '/activity/collections',
      method: 'get',
      params: { userId, pageNum, pageSize }
    })
  }
}

export const postApi = {
  create(data, userId) {
    return request({
      url: '/post/create',
      method: 'post',
      data,
      params: { userId }
    })
  },
  getDetail(id, userId) {
    return request({
      url: `/post/detail/${id}`,
      method: 'get',
      params: { userId }
    })
  },
  getList(params) {
    return request({
      url: '/post/list',
      method: 'get',
      params
    })
  },
  like(userId, postId) {
    return request({
      url: '/post/like',
      method: 'post',
      params: { userId, postId }
    })
  },
  unlike(userId, postId) {
    return request({
      url: '/post/unlike',
      method: 'post',
      params: { userId, postId }
    })
  },
  comment(data, userId) {
    return request({
      url: '/post/comment',
      method: 'post',
      data,
      params: { userId }
    })
  },
  getComments(postId) {
    return request({
      url: `/post/comments/${postId}`,
      method: 'get'
    })
  }
}

export const schoolApi = {
  getList() {
    return request({
      url: '/school/list',
      method: 'get'
    })
  }
}
