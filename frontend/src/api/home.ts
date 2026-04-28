import request from '@/utils/request'
import type { AxiosResponse } from 'axios'
import type { ApiResponse } from '@/utils/request'

/**
 * 分类数据类型
 */
export interface Category {
  id: number
  name: string
  icon?: string
  count?: number
  parentId?: number
}

/**
 * 帖子数据类型
 */
export interface Post {
  id: number
  title: string
  content: string
  authorId: number
  authorName: string
  authorAvatar?: string
  categoryId?: number
  categoryName?: string
  viewCount: number
  commentCount: number
  favoriteCount: number
  likeCount: number
  top?: number
  essence?: number
  createdAt: string
}

/**
 * 优惠数据类型
 */
export interface Deal {
  id: number
  title: string
  description?: string
  price: number
  dealPrice?: number
  imageUrls?: string[]
  viewCount?: number
  favoriteCount?: number
  createdAt?: string
}

/**
 * 获取分类列表
 */
export function getCategoryList() {
  return request.get<Category[]>('/api/category/list')
}

/**
 * 获取最新帖子列表
 */
export function getLatestPosts(page = 1, size = 10) {
  return request.get<any>('/api/post/page', {
    params: {
      page,
      size,
      sortBy: 'created_at',
      order: 'desc'
    }
  })
}

/**
 * 获取热门帖子列表
 */
export function getHotPosts(limit = 10) {
  return request.get<Post[]>('/api/post/hot', {
    params: {
      limit
    }
  })
}

/**
 * 获取精华帖子列表
 */
export function getEssencePosts(limit = 10) {
  return request.get<Post[]>('/api/post/essence', {
    params: {
      limit
    }
  })
}

/**
 * 获取热门优惠列表
 */
export function getHotDeals(limit = 10) {
  return request.get<Deal[]>('/api/deal/hot', {
    params: {
      limit
    }
  })
}

export type { Category, Post, Deal }
