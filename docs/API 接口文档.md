# 大学生资源信息分享平台 - API 接口文档

## 1. 接口规范

### 1.1 基础信息
- **基础路径**: `/api/v1`
- **数据格式**: JSON
- **字符编码**: UTF-8
- **认证方式**: JWT Token

### 1.2 响应格式

#### 1.2.1 成功响应
```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1699999999999
}
```

#### 1.2.2 错误响应
```json
{
  "code": 400,
  "message": "参数错误",
  "data": null,
  "timestamp": 1699999999999
}
```

### 1.3 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权，需要登录 |
| 403 | 禁止访问，权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
| 1001 | 用户名已存在 |
| 1002 | 手机号已存在 |
| 1003 | 邮箱已存在 |
| 1004 | 用户名或密码错误 |
| 1005 | 用户已被禁用 |
| 2001 | 优惠信息不存在 |
| 2002 | 优惠已下架 |
| 3001 | 帖子不存在 |
| 3002 | 评论不存在 |

### 1.4 分页参数

所有列表接口使用统一的分页参数：

```json
{
  "page": 1,
  "size": 10,
  "sort": "created_at",
  "order": "desc"
}
```

响应格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [],
    "total": 100,
    "page": 1,
    "size": 10,
    "pages": 10
  }
}
```

## 2. 用户模块接口

### 2.1 用户注册

**接口**: `POST /api/v1/user/register`

**请求参数**:
```json
{
  "username": "string, 必填，4-20 位字母数字下划线",
  "password": "string, 必填，6-20 位",
  "phone": "string, 必填，11 位手机号",
  "code": "string, 必填，短信验证码",
  "nickname": "string, 可选，昵称"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "userId": 1001,
    "username": "user123",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### 2.2 用户登录

**接口**: `POST /api/v1/user/login`

**请求参数**:
```json
{
  "username": "string, 必填，用户名/手机号/邮箱",
  "password": "string, 必填，密码"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "userId": 1001,
    "username": "user123",
    "nickname": "昵称",
    "avatar": "http://...",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 7200
  }
}
```

### 2.3 验证码登录

**接口**: `POST /api/v1/user/login/code`

**请求参数**:
```json
{
  "phone": "string, 必填，手机号",
  "code": "string, 必填，短信验证码"
}
```

### 2.4 发送短信验证码

**接口**: `POST /api/v1/user/sms/code`

**请求参数**:
```json
{
  "phone": "string, 必填，手机号",
  "type": "int, 必填，1-注册 2-登录 3-找回密码"
}
```

### 2.5 获取用户信息

**接口**: `GET /api/v1/user/info`

**请求头**: `Authorization: Bearer {token}`

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userId": 1001,
    "username": "user123",
    "nickname": "昵称",
    "avatar": "http://...",
    "gender": 1,
    "phone": "138****1234",
    "email": "user@example.com",
    "school": "XX 大学",
    "college": "XX 学院",
    "level": 5,
    "points": 1000,
    "followCount": 50,
    "fansCount": 100,
    "favoriteCount": 200
  }
}
```

### 2.6 更新用户信息

**接口**: `PUT /api/v1/user/info`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "nickname": "string, 可选，昵称",
  "avatar": "string, 可选，头像 URL",
  "gender": "int, 可选，性别",
  "email": "string, 可选，邮箱",
  "school": "string, 可选，学校",
  "college": "string, 可选，学院"
}
```

### 2.7 修改密码

**接口**: `PUT /api/v1/user/password`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "oldPassword": "string, 必填，旧密码",
  "newPassword": "string, 必填，新密码"
}
```

### 2.8 找回密码

**接口**: `POST /api/v1/user/password/reset`

**请求参数**:
```json
{
  "phone": "string, 必填，手机号",
  "code": "string, 必填，短信验证码",
  "newPassword": "string, 必填，新密码"
}
```

### 2.9 用户登出

**接口**: `POST /api/v1/user/logout`

**请求头**: `Authorization: Bearer {token}`

### 2.10 刷新 Token

**接口**: `POST /api/v1/user/refresh/token`

**请求参数**:
```json
{
  "refreshToken": "string, 必填，刷新令牌"
}
```

### 2.11 关注用户

**接口**: `POST /api/v1/user/follow/{userId}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- userId: 被关注用户 ID

### 2.12 取消关注

**接口**: `DELETE /api/v1/user/follow/{userId}`

**请求头**: `Authorization: Bearer {token}`

### 2.13 获取关注列表

**接口**: `GET /api/v1/user/follow/list`

**请求头**: `Authorization: Bearer {token}`

**查询参数**:
- userId: 用户 ID（不传则为当前用户）
- page: 页码
- size: 每页数量

### 2.14 获取粉丝列表

**接口**: `GET /api/v1/user/fans/list`

**请求头**: `Authorization: Bearer {token}`

**查询参数**:
- userId: 用户 ID
- page: 页码
- size: 每页数量

### 2.15 获取用户发布列表

**接口**: `GET /api/v1/user/post/list`

**查询参数**:
- userId: 用户 ID
- page: 页码
- size: 每页数量

## 3. 优惠模块接口

### 3.1 获取优惠列表

**接口**: `GET /api/v1/deal/list`

**查询参数**:
```
categoryId: 分类 ID（可选）
type: 优惠类型（可选）
keyword: 关键词（可选）
tags: 标签（可选）
status: 状态（可选）
sortBy: 排序字段（可选）
order: 排序方式（可选）
page: 页码
size: 每页数量
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": 1001,
        "title": "京东 618 大促",
        "description": "满 199 减 100",
        "imageUrls": ["http://..."],
        "type": 1,
        "categoryName": "电商优惠",
        "userName": "用户 A",
        "userAvatar": "http://...",
        "price": 299.00,
        "dealPrice": 199.00,
        "discount": "6.6 折",
        "startTime": "2024-06-01 00:00:00",
        "endTime": "2024-06-18 23:59:59",
        "viewCount": 1000,
        "favoriteCount": 200,
        "likeCount": 150,
        "top": 0,
        "recommend": 1,
        "createdAt": "2024-05-20 10:00:00"
      }
    ],
    "total": 100,
    "page": 1,
    "size": 10,
    "pages": 10
  }
}
```

### 3.2 获取优惠详情

**接口**: `GET /api/v1/deal/{id}`

**路径参数**:
- id: 优惠 ID

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1001,
    "title": "京东 618 大促",
    "description": "详细描述...",
    "imageUrls": ["http://...", "http://..."],
    "videoUrl": "http://...",
    "type": 1,
    "categoryName": "电商优惠",
    "user": {
      "userId": 1001,
      "username": "user123",
      "nickname": "昵称",
      "avatar": "http://..."
    },
    "price": 299.00,
    "dealPrice": 199.00,
    "discount": "6.6 折",
    "startTime": "2024-06-01 00:00:00",
    "endTime": "2024-06-18 23:59:59",
    "url": "http://...",
    "qrCode": "http://...",
    "tags": ["618", "京东", "满减"],
    "viewCount": 1000,
    "favoriteCount": 200,
    "likeCount": 150,
    "commentCount": 50,
    "shareCount": 30,
    "top": 0,
    "recommend": 1,
    "createdAt": "2024-05-20 10:00:00",
    "favorited": false,
    "liked": false
  }
}
```

### 3.3 发布优惠

**接口**: `POST /api/v1/deal`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "title": "string, 必填，标题",
  "description": "string, 可选，描述",
  "categoryId": "long, 必填，分类 ID",
  "type": "int, 必填，优惠类型",
  "imageUrls": "array, 可选，图片 URL 列表",
  "videoUrl": "string, 可选，视频 URL",
  "price": "decimal, 可选，原价",
  "dealPrice": "decimal, 可选，优惠价",
  "discount": "string, 可选，折扣",
  "startTime": "datetime, 可选，开始时间",
  "endTime": "datetime, 可选，结束时间",
  "url": "string, 可选，活动链接",
  "tags": "array, 可选，标签列表"
}
```

### 3.4 更新优惠

**接口**: `PUT /api/v1/deal/{id}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

**请求参数**: 同发布优惠

### 3.5 删除优惠

**接口**: `DELETE /api/v1/deal/{id}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

### 3.6 收藏优惠

**接口**: `POST /api/v1/deal/{id}/favorite`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

### 3.7 取消收藏

**接口**: `DELETE /api/v1/deal/{id}/favorite`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

### 3.8 点赞优惠

**接口**: `POST /api/v1/deal/{id}/like`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

### 3.9 取消点赞

**接口**: `DELETE /api/v1/deal/{id}/like`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

### 3.10 获取优惠评论列表

**接口**: `GET /api/v1/deal/{id}/comment/list`

**路径参数**:
- id: 优惠 ID

**查询参数**:
- page: 页码
- size: 每页数量

### 3.11 发布优惠评论

**接口**: `POST /api/v1/deal/{id}/comment`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 优惠 ID

**请求参数**:
```json
{
  "content": "string, 必填，评论内容",
  "imageUrls": "array, 可选，图片列表",
  "parentId": "long, 可选，父评论 ID"
}
```

### 3.12 获取分类列表

**接口**: `GET /api/v1/category/list`

**查询参数**:
- parentId: 父分类 ID（可选，不传则获取一级分类）

### 3.13 获取标签列表

**接口**: `GET /api/v1/tag/list`

**查询参数**:
- keyword: 关键词（可选）
- type: 类型（可选）

## 4. 社区模块接口

### 4.1 获取帖子列表

**接口**: `GET /api/v1/post/list`

**查询参数**:
```
categoryId: 分类 ID（可选）
keyword: 关键词（可选）
tags: 标签（可选）
topicId: 话题 ID（可选）
userId: 用户 ID（可选）
sortBy: 排序字段（可选）
order: 排序方式（可选）
page: 页码
size: 每页数量
```

### 4.2 获取帖子详情

**接口**: `GET /api/v1/post/{id}`

**路径参数**:
- id: 帖子 ID

### 4.3 发布帖子

**接口**: `POST /api/v1/post`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "title": "string, 可选，标题",
  "content": "string, 必填，内容",
  "categoryId": "long, 可选，分类 ID",
  "imageUrls": "array, 可选，图片列表",
  "videoUrls": "array, 可选，视频列表",
  "tags": "array, 可选，标签列表",
  "topicIds": "array, 可选，话题 ID 列表"
}
```

### 4.4 更新帖子

**接口**: `PUT /api/v1/post/{id}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 帖子 ID

### 4.5 删除帖子

**接口**: `DELETE /api/v1/post/{id}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 帖子 ID

### 4.6 点赞帖子

**接口**: `POST /api/v1/post/{id}/like`

**请求头**: `Authorization: Bearer {token}`

### 4.7 取消点赞

**接口**: `DELETE /api/v1/post/{id}/like`

**请求头**: `Authorization: Bearer {token}`

### 4.8 收藏帖子

**接口**: `POST /api/v1/post/{id}/favorite`

**请求头**: `Authorization: Bearer {token}`

### 4.9 取消收藏

**接口**: `DELETE /api/v1/post/{id}/favorite`

**请求头**: `Authorization: Bearer {token}`

### 4.10 获取帖子评论列表

**接口**: `GET /api/v1/post/{id}/comment/list`

**路径参数**:
- id: 帖子 ID

### 4.11 发布帖子评论

**接口**: `POST /api/v1/post/{id}/comment`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 帖子 ID

**请求参数**:
```json
{
  "content": "string, 必填，评论内容",
  "imageUrls": "array, 可选，图片列表",
  "parentId": "long, 可选，父评论 ID"
}
```

### 4.12 获取话题列表

**接口**: `GET /api/v1/topic/list`

**查询参数**:
- keyword: 关键词（可选）
- sortBy: 排序字段（可选）
- page: 页码
- size: 每页数量

### 4.13 获取话题详情

**接口**: `GET /api/v1/topic/{id}`

**路径参数**:
- id: 话题 ID

## 5. 消息模块接口

### 5.1 获取消息通知列表

**接口**: `GET /api/v1/notification/list`

**请求头**: `Authorization: Bearer {token}`

**查询参数**:
- type: 消息类型（可选）
- isRead: 是否已读（可选）
- page: 页码
- size: 每页数量

### 5.2 标记消息已读

**接口**: `PUT /api/v1/notification/{id}/read`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 消息 ID

### 5.3 批量标记已读

**接口**: `PUT /api/v1/notification/read/batch`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "ids": "array, 必填，消息 ID 列表"
}
```

### 5.4 删除消息

**接口**: `DELETE /api/v1/notification/{id}`

**请求头**: `Authorization: Bearer {token}`

**路径参数**:
- id: 消息 ID

### 5.5 获取未读消息数

**接口**: `GET /api/v1/notification/unread/count`

**请求头**: `Authorization: Bearer {token}`

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "totalCount": 10,
    "systemCount": 2,
    "likeCount": 3,
    "commentCount": 4,
    "followCount": 1
  }
}
```

### 5.6 发送私信

**接口**: `POST /api/v1/message/private`

**请求头**: `Authorization: Bearer {token}`

**请求参数**:
```json
{
  "receiverId": "long, 必填，接收者 ID",
  "content": "string, 必填，内容",
  "imageUrls": "array, 可选，图片列表"
}
```

### 5.7 获取私信列表

**接口**: `GET /api/v1/message/private/list`

**请求头**: `Authorization: Bearer {token}`

**查询参数**:
- userId: 对话用户 ID
- page: 页码
- size: 每页数量

## 6. 搜索模块接口

### 6.1 全局搜索

**接口**: `GET /api/v1/search`

**查询参数**:
```
keyword: 关键词，必填
type: 搜索类型（可选，1-优惠 2-帖子 3-用户，不传则搜索全部）
page: 页码
size: 每页数量
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "deals": [],
    "posts": [],
    "users": [],
    "total": 100
  }
}
```

### 6.2 搜索建议

**接口**: `GET /api/v1/search/suggest`

**查询参数**:
- keyword: 关键词，必填
- limit: 返回数量（可选，默认 10）

### 6.3 热门搜索

**接口**: `GET /api/v1/search/hot`

**查询参数**:
- limit: 返回数量（可选，默认 10）

### 6.4 搜索历史

**接口**: `GET /api/v1/search/history`

**请求头**: `Authorization: Bearer {token}`

### 6.5 清除搜索历史

**接口**: `DELETE /api/v1/search/history`

**请求头**: `Authorization: Bearer {token}`

## 7. 文件上传接口

### 7.1 上传图片

**接口**: `POST /api/v1/file/upload/image`

**请求头**: `Authorization: Bearer {token}`

**请求类型**: multipart/form-data

**请求参数**:
- file: 图片文件

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "url": "http://...",
    "filename": "xxx.jpg",
    "size": 102400
  }
}
```

### 7.2 上传视频

**接口**: `POST /api/v1/file/upload/video`

**请求头**: `Authorization: Bearer {token}`

**请求类型**: multipart/form-data

**请求参数**:
- file: 视频文件

## 8. 后台管理接口

### 8.1 管理员登录

**接口**: `POST /api/v1/admin/login`

**请求参数**:
```json
{
  "username": "string, 必填，用户名",
  "password": "string, 必填，密码"
}
```

### 8.2 获取用户列表

**接口**: `GET /api/v1/admin/user/list`

**请求头**: `Authorization: Bearer {adminToken}`

**查询参数**:
- keyword: 关键词（可选）
- status: 状态（可选）
- page: 页码
- size: 每页数量

### 8.3 更新用户状态

**接口**: `PUT /api/v1/admin/user/{id}/status`

**请求头**: `Authorization: Bearer {adminToken}`

**路径参数**:
- id: 用户 ID

**请求参数**:
```json
{
  "status": "int, 必填，0-禁用 1-正常"
}
```

### 8.4 获取优惠审核列表

**接口**: `GET /api/v1/admin/deal/audit/list`

**请求头**: `Authorization: Bearer {adminToken}`

### 8.5 审核优惠

**接口**: `PUT /api/v1/admin/deal/{id}/audit`

**请求头**: `Authorization: Bearer {adminToken}`

**路径参数**:
- id: 优惠 ID

**请求参数**:
```json
{
  "status": "int, 必填，1-通过 0-拒绝",
  "reason": "string, 可选，拒绝原因"
}
```

### 8.6 获取帖子审核列表

**接口**: `GET /api/v1/admin/post/audit/list`

**请求头**: `Authorization: Bearer {adminToken}`

### 8.7 审核帖子

**接口**: `PUT /api/v1/admin/post/{id}/audit`

**请求头**: `Authorization: Bearer {adminToken}`

**路径参数**:
- id: 帖子 ID

**请求参数**:
```json
{
  "status": "int, 必填，1-通过 0-拒绝",
  "reason": "string, 可选，拒绝原因"
}
```

### 8.8 获取数据统计

**接口**: `GET /api/v1/admin/statistics`

**请求头**: `Authorization: Bearer {adminToken}`

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "userCount": 10000,
    "dealCount": 5000,
    "postCount": 3000,
    "todayUserCount": 100,
    "todayDealCount": 50,
    "todayPostCount": 30,
    "activeUserCount": 2000
  }
}
```

## 9. WebSocket 接口

### 9.1 连接地址

```
ws://{host}/api/v1/ws/{userId}
```

### 9.2 消息格式

**服务端推送**:
```json
{
  "type": "notification",
  "data": {
    "id": 1001,
    "type": 2,
    "title": "点赞通知",
    "content": "用户 A 点赞了你的帖子",
    "createdAt": "2024-01-01 12:00:00"
  }
}
```

### 9.3 消息类型

- notification: 通知消息
- private_message: 私信消息
- system: 系统消息
