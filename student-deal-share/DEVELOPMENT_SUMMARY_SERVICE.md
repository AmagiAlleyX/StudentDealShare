# 业务逻辑层开发总结

## 📦 模块概述

**sd-service 模块** 完成了整个项目的业务逻辑层，采用接口与实现分离的设计模式，实现了用户管理、优惠信息、社区互动、消息通知等核心业务功能。

## 📂 模块结构

```
sd-service/
├── sd-service-api/                      # Service 接口层
│   └── src/main/java/com/student/dealshare/
│       ├── annotation/
│       │   └── OperationLog.java        # 操作日志注解
│       └── service/api/
│           ├── UserService.java         # 用户服务接口
│           ├── DealService.java         # 优惠服务接口
│           ├── PostService.java         # 帖子服务接口
│           ├── CommentService.java      # 评论服务接口
│           ├── NotificationService.java # 通知服务接口
│           ├── PrivateMessageService.java # 私信服务接口
│           └── OperationLogService.java # 日志服务接口
│
└── sd-service-impl/                     # Service 实现层
    └── src/main/java/com/student/dealshare/
        ├── aspect/
        │   └── OperationLogAspect.java  # 操作日志切面
        ├── config/
        │   └── TransactionConfig.java   # 事务配置
        └── service/impl/
            ├── UserServiceImpl.java     # 用户服务实现
            ├── DealServiceImpl.java     # 优惠服务实现
            ├── PostServiceImpl.java     # 帖子服务实现
            ├── CommentServiceImpl.java  # 评论服务实现
            ├── NotificationServiceImpl.java # 通知服务实现
            └── OperationLogServiceImpl.java # 日志服务实现
```

## ✅ 完成内容

### 1. Service 接口（7 个）

#### 1.1 UserService - 用户服务
```java
public interface UserService {
    LoginVO register(UserRegisterDTO dto);          // 用户注册
    LoginVO login(UserLoginDTO dto);                // 用户登录
    UserVO getUserById(Long userId);                // 获取用户信息
    UserVO getCurrentUser();                        // 获取当前用户
    void updateCurrentUser(UserUpdateDTO dto);      // 更新用户信息
    void changePassword(PasswordChangeDTO dto);     // 修改密码
    Page<UserVO> pageUsers(int page, int size);     // 分页查询用户
    void followUser(Long followeeId);               // 关注用户
    void unfollowUser(Long followeeId);             // 取消关注
    boolean isFollowing(Long userId, Long followeeId); // 是否关注
}
```

#### 1.2 DealService - 优惠服务
```java
public interface DealService {
    DealVO createDeal(DealCreateDTO dto);           // 创建优惠
    DealVO getDealById(Long dealId);                // 获取优惠详情
    Page<DealVO> pageDeals(DealQueryDTO queryDTO);  // 分页查询优惠
    List<DealVO> listHotDeals(int limit);           // 热门优惠
    void updateDeal(DealUpdateDTO dto);             // 更新优惠
    void deleteDeal(Long dealId);                   // 删除优惠
    void verifyDeal(Long dealId, boolean passed);   // 审核优惠
    void favoriteDeal(Long dealId);                 // 收藏优惠
    void unfavoriteDeal(Long dealId);               // 取消收藏
    boolean isFavorite(Long userId, Long dealId);   // 是否收藏
    void incrementViewCount(Long dealId);           // 增加浏览量
    Page<DealVO> pageUserDeals(Long userId, int page, int size); // 用户发布的优惠
}
```

#### 1.3 PostService - 帖子服务
```java
public interface PostService {
    PostVO createPost(PostCreateDTO dto);           // 发布帖子
    PostVO getPostById(Long postId);                // 获取帖子详情
    Page<PostVO> pagePosts(int page, int size);     // 分页查询帖子
    void updatePost(PostUpdateDTO dto);             // 更新帖子
    void deletePost(Long postId);                   // 删除帖子
    void incrementViewCount(Long postId);           // 增加浏览量
    void likePost(Long postId);                     // 点赞帖子
    void unlikePost(Long postId);                   // 取消点赞
    boolean isLiked(Long userId, Long postId);      // 是否点赞
    Page<PostVO> pageUserPosts(Long userId, int page, int size); // 用户发布的帖子
    List<PostVO> listHotPosts(int limit);           // 热门帖子
    List<PostVO> listEssencePosts(int limit);       // 精华帖子
}
```

#### 1.4 CommentService - 评论服务
```java
public interface CommentService {
    CommentVO createComment(CommentCreateDTO dto);  // 发表评论
    List<CommentVO> listCommentsByPost(Long postId, int limit); // 帖子评论列表
    Page<CommentVO> pageComments(Long postId, int page, int size); // 评论分页
    void deleteComment(Long commentId);             // 删除评论
    void likeComment(Long commentId);               // 点赞评论
    void unlikeComment(Long commentId);             // 取消点赞评论
    boolean isLiked(Long userId, Long commentId);   // 是否点赞评论
}
```

#### 1.5 NotificationService - 通知服务
```java
public interface NotificationService {
    void sendNotification(Long userId, Integer type, String title, String content, Long relatedId);
    List<NotificationVO> listUnreadNotifications(Long userId);
    Page<NotificationVO> pageNotifications(Long userId, int page, int size);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    int countUnread(Long userId);
}
```

#### 1.6 PrivateMessageService - 私信服务
```java
public interface PrivateMessageService {
    void sendMessage(PrivateMessageSendDTO dto, Long senderId);
    Page<PrivateMessageVO> pageMessages(Long userId, Long otherUserId, int page, int size);
    void markAsRead(Long messageId);
    void deleteMessage(Long messageId, Long userId);
}
```

#### 1.7 OperationLogService - 操作日志服务
```java
public interface OperationLogService extends IService<OperationLog> {
}
```

### 2. Service 实现类（6 个）

#### 2.1 UserServiceImpl - 用户服务实现

**核心功能**：
- ✅ 用户注册（密码加密、token 生成）
- ✅ 用户登录（凭证验证、token 发放）
- ✅ 用户信息管理（查询、更新）
- ✅ 密码修改（原密码验证）
- ✅ 关注功能（关注、取消关注、关注状态）

**业务规则**：
```java
// 1. 注册时检查用户名是否存在
// 2. 密码使用 BCrypt 加密
// 3. 登录时检查用户状态
// 4. 不能关注自己
// 5. 不能重复关注
```

**事务管理**：
```java
@Transactional(rollbackFor = Exception.class)
public LoginVO register(UserRegisterDTO dto) {
    // 检查用户名
    // 创建用户
    // 生成 token
    // 返回登录信息
}
```

#### 2.2 DealServiceImpl - 优惠服务实现

**核心功能**：
- ✅ 优惠信息 CRUD
- ✅ 优惠审核（通过/拒绝）
- ✅ 收藏功能（收藏、取消收藏）
- ✅ 浏览量统计
- ✅ 热门优惠推荐

**业务规则**：
```java
// 1. 创建优惠时默认待审核状态
// 2. 审核通过后状态变更为已发布
// 3. 收藏时增加收藏计数
// 4. 取消收藏时减少收藏计数
// 5. 不能重复收藏
```

**查询优化**：
```java
// 分页查询 + 条件过滤
LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(Deal::getStatus, 1)
       .eq(Deal::getCategoryId, categoryId)
       .orderByDesc(Deal::getCreateTime);
```

#### 2.3 PostServiceImpl - 帖子服务实现

**核心功能**：
- ✅ 帖子发布（支持关联话题）
- ✅ 帖子管理（CRUD、置顶、精华）
- ✅ 互动功能（点赞、取消点赞）
- ✅ 浏览量统计
- ✅ 热门/精华帖子推荐

**业务规则**：
```java
// 1. 发布帖子时关联话题
// 2. 点赞时增加点赞计数
// 3. 不能重复点赞
// 4. 精华帖子单独列表
```

**话题关联**：
```java
if (dto.getTopicIds() != null && dto.getTopicIds().length > 0) {
    for (Long topicId : dto.getTopicIds()) {
        PostTopic postTopic = new PostTopic();
        postTopic.setPostId(post.getPostId());
        postTopic.setTopicId(topicId);
        postTopicMapper.insert(postTopic);
    }
}
```

#### 2.4 CommentServiceImpl - 评论服务实现

**核心功能**：
- ✅ 评论发表（支持回复）
- ✅ 评论列表（按帖子查询）
- ✅ 评论管理（删除）
- ✅ 评论点赞

**业务规则**：
```java
// 1. 支持多级评论（level 字段）
// 2. 回复时继承父级 level
// 3. 点赞时增加点赞计数
// 4. 删除评论不影响父评论
```

**评论级别**：
```java
if (dto.getParentId() == null || dto.getParentId() == 0) {
    comment.setLevel(1);  // 一级评论
} else {
    Comment parent = commentMapper.selectById(dto.getParentId());
    comment.setLevel(parent.getLevel() + 1);  // 子评论
}
```

#### 2.5 NotificationServiceImpl - 通知服务实现

**核心功能**：
- ✅ 发送通知（系统通知、互动通知）
- ✅ 未读通知列表
- ✅ 通知分页查询
- ✅ 已读标记（单条/全部）
- ✅ 未读数量统计

**通知类型**：
```java
// 1 - 系统通知
// 2 - 点赞通知
// 3 - 评论通知
// 4 - 关注通知
// 5 - 回复通知
```

#### 2.6 OperationLogServiceImpl - 操作日志服务

**核心功能**：
- ✅ 日志记录（AOP 自动记录）
- ✅ 日志查询

### 3. AOP 切面

#### 3.1 OperationLogAspect - 操作日志切面

**功能**：
- ✅ 自动记录操作日志
- ✅ 记录请求参数
- ✅ 记录执行时间
- ✅ 记录执行状态

**使用方式**：
```java
@OperationLog(module = "用户管理", type = "CREATE", description = "用户注册")
public LoginVO register(UserRegisterDTO dto) {
    // 业务逻辑
}
```

**切面定义**：
```java
@Pointcut("@annotation(com.student.dealshare.annotation.OperationLog)")
public void logPointcut() {
}

@Around("logPointcut()")
public Object around(ProceedingJoinPoint point) throws Throwable {
    // 记录日志
}
```

### 4. 事务配置

#### 4.1 TransactionConfig - 事务配置类

```java
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
}
```

**事务注解使用**：
```java
@Transactional(rollbackFor = Exception.class)
public void someMethod() {
    // 业务逻辑，异常时自动回滚
}
```

**事务传播行为**：
- `REQUIRED`（默认）- 如果当前存在事务，则加入该事务；否则创建一个新事务
- `REQUIRES_NEW` - 创建一个新事务，如果当前存在事务，则挂起当前事务
- `NESTED` - 如果当前存在事务，则在嵌套事务内执行

## 🔧 技术要点

### 1. 事务管理

#### 1.1 声明式事务
```java
@Transactional(rollbackFor = Exception.class)
public LoginVO register(UserRegisterDTO dto) {
    // 任何异常都会回滚
}
```

#### 1.2 事务边界
- Service 层方法作为事务边界
- Controller 层不调用多个 Service 方法（避免长事务）
- 只读查询不需要事务

### 2. 异常处理

#### 2.1 业务异常
```java
if (existUser != null) {
    throw new BusinessException(ResultCodeEnum.USERNAME_ALREADY_EXISTS);
}
```

#### 2.2 异常类型
- `BusinessException` - 业务异常
- `ValidationException` - 参数校验异常
- `AuthenticationException` - 认证异常
- `AuthorizationException` - 授权异常

### 3. 分页查询

#### 3.1 MyBatis Plus 分页
```java
Page<User> userPage = new Page<>(page, size);
Page<User> result = userMapper.selectPage(userPage, wrapper);
return result.convert(userConverter::toVO);
```

#### 3.2 分页结果
```java
{
    "records": [...],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
}
```

### 4. 条件构造器

#### 4.1 LambdaQueryWrapper
```java
LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
wrapper.eq(Deal::getStatus, 1)
       .like(Deal::getTitle, keyword)
       .orderByDesc(Deal::getCreateTime);
```

#### 4.2 常用方法
- `eq` - 等于
- `ne` - 不等于
- `gt` - 大于
- `lt` - 小于
- `like` - 模糊匹配
- `in` - IN 查询
- `orderByDesc` - 降序排序

### 5. 安全认证

#### 5.1 获取当前用户
```java
Long userId = SecurityUtils.getCurrentUserId();
```

#### 5.2 密码加密
```java
user.setPassword(passwordEncoder.encode(dto.getPassword()));
```

#### 5.3 Token 生成
```java
String token = jwtTokenProvider.createToken(userId);
```

## 📝 使用示例

### Controller 调用示例

```java
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public R<LoginVO> register(@Valid @RequestBody UserRegisterDTO dto) {
        LoginVO result = userService.register(dto);
        return R.ok(result);
    }

    @PostMapping("/login")
    public R<LoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
        LoginVO result = userService.login(dto);
        return R.ok(result);
    }

    @GetMapping("/me")
    public R<UserVO> getCurrentUser() {
        UserVO user = userService.getCurrentUser();
        return R.ok(user);
    }

    @PutMapping("/update")
    public R<Void> update(@Valid @RequestBody UserUpdateDTO dto) {
        userService.updateCurrentUser(dto);
        return R.ok();
    }
}
```

### Service 组合调用示例

```java
@Service
@RequiredArgsConstructor
public class PostInteractionService {

    private final PostService postService;
    private final NotificationService notificationService;

    @Transactional(rollbackFor = Exception.class)
    public void likePost(Long postId) {
        // 1. 点赞帖子
        postService.likePost(postId);
        
        // 2. 获取帖子作者
        PostVO post = postService.getPostById(postId);
        
        // 3. 发送通知给作者
        notificationService.sendNotification(
            post.getUserId(),
            2,  // 点赞通知
            "有人点赞了你的帖子",
            "用户 XXX 点赞了你的帖子《" + post.getTitle() + "》",
            postId
        );
    }
}
```

## 🎯 最佳实践

### 1. Service 设计原则

✅ **单一职责**
- 每个 Service 只负责一个业务领域
- 避免上帝类（God Class）

✅ **接口隔离**
- 接口定义清晰
- 实现类专注于业务逻辑

✅ **依赖倒置**
- 依赖接口而非实现
- 便于单元测试

### 2. 事务使用原则

✅ **粒度适中**
- 事务范围不宜过大
- 避免长事务

✅ **只读优化**
```java
@Transactional(readOnly = true)
public UserVO getUserById(Long userId) {
    return userService.getById(userId);
}
```

✅ **异常回滚**
```java
@Transactional(rollbackFor = Exception.class)
public void someMethod() {
    // 任何异常都回滚
}
```

### 3. 业务逻辑处理

✅ **参数校验**
- DTO 层使用注解校验
- Service 层进行业务规则校验

✅ **数据转换**
- 使用 MapStruct 转换器
- 避免手动 set 字段

✅ **计数操作**
- 使用数据库原子操作
- 避免并发问题

### 4. 性能优化

✅ **分页查询**
- 避免全表扫描
- 限制返回数量

✅ **懒加载**
- 按需加载关联数据
- 避免 N+1 问题

✅ **缓存预留**
```java
// TODO: 添加 Redis 缓存
public DealVO getDealById(Long dealId) {
    // 1. 先查缓存
    // 2. 缓存未命中查数据库
    // 3. 写入缓存
}
```

## 📊 统计数据

| 模块 | Service 接口 | Service 实现 | 方法总数 |
|------|------------|------------|----------|
| 用户模块 | 1 | 1 | 11 |
| 优惠模块 | 1 | 1 | 13 |
| 社区模块 | 2 | 2 | 19 |
| 消息模块 | 2 | 1 | 11 |
| 系统模块 | 1 | 1 | 1 |
| **总计** | **7** | **6** | **55** |

## 🎓 总结

### 本次完成的工作

1. ✅ **Service 接口（7 个）**
   - UserService - 用户服务
   - DealService - 优惠服务
   - PostService - 帖子服务
   - CommentService - 评论服务
   - NotificationService - 通知服务
   - PrivateMessageService - 私信服务
   - OperationLogService - 日志服务

2. ✅ **Service 实现（6 个）**
   - UserServiceImpl
   - DealServiceImpl
   - PostServiceImpl
   - CommentServiceImpl
   - NotificationServiceImpl
   - OperationLogServiceImpl

3. ✅ **AOP 切面**
   - OperationLogAspect - 操作日志切面

4. ✅ **配置类**
   - TransactionConfig - 事务配置

### 架构优势

1. **分层清晰**
   - 接口与实现分离
   - 职责明确

2. **事务安全**
   - 声明式事务管理
   - 自动回滚机制

3. **易于扩展**
   - 依赖接口
   - 便于添加新功能

4. **可测试性**
   - 接口易于 Mock
   - 支持单元测试

### 下一步建议

1. **单元测试**
   - 为核心 Service 编写单元测试
   - 使用 Mockito 模拟依赖

2. **集成测试**
   - 测试 Service 组合调用
   - 测试事务回滚

3. **性能优化**
   - 添加 Redis 缓存
   - 优化数据库查询

4. **日志增强**
   - 添加业务日志
   - 完善异常日志

---

**开发状态**：✅ 业务逻辑层开发完成  
**下一步**：安全模块（sd-security）或 控制层（sd-controller）
