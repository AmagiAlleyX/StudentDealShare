# DTO 和 VO 开发总结

## 📚 为什么需要 DTO 和 VO？

### 问题背景

如果只使用 Entity，会存在以下严重问题：

#### 1. **安全隐患**
```java
// ❌ 错误做法：直接返回 Entity
@GetMapping("/user/{id}")
public User getUser(@PathVariable Long id) {
    return userService.getById(id);
}
// 返回结果包含：password, phone, email 等敏感信息
```

#### 2. **字段污染**
```java
// ❌ 错误做法：直接用 Entity 接收参数
@PostMapping("/user/register")
public void register(@RequestBody User user) {
    // 前端可以传递 userId, createTime, status 等字段
    // 可能导致恶意注入
}
```

#### 3. **耦合严重**
- 数据库表结构变化直接影响 API 接口
- 不同业务场景无法灵活定制数据视图
- 无法实现接口版本管理

### 正确的分层架构

```
┌─────────────────────────────────────────┐
│            Controller Layer             │
│              (API 接口层)                │
├─────────────────────────────────────────┤
│         VO (View Object)                │
│         ↓ 返回给前端的数据               │
├─────────────────────────────────────────┤
│         Service Layer                   │
│         (业务逻辑层)                     │
├─────────────────────────────────────────┤
│         DTO (Data Transfer Object)      │
│         ↓ 服务间传输的数据               │
├─────────────────────────────────────────┤
│         Mapper Layer                    │
│         (数据访问层)                     │
├─────────────────────────────────────────┤
│         Entity (实体类)                 │
│         ↓ 与数据库映射                   │
└─────────────────────────────────────────┘
```

## 📊 各层对象详解

### 1. Entity（实体类）

**职责**：与数据库表映射，用于持久化操作

**特点**：
- 使用 JPA/MyBatis 注解
- 包含所有数据库字段
- 包含审计字段（createTime, updateTime）
- 支持逻辑删除

**示例**：
```java
@TableName("user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    private String username;
    private String password;      // 敏感字段
    private String phone;         // 敏感字段
    private LocalDateTime createTime;
    private Integer deleted;
    // ...
}
```

### 2. DTO（Data Transfer Object）

**职责**：服务层之间数据传输，接收前端参数

**特点**：
- 不包含敏感字段
- 包含参数校验注解（@NotBlank, @NotNull）
- 按业务场景分类（RegisterDTO, LoginDTO, UpdateDTO）
- 不包含数据库相关注解

**示例**：
```java
public class UserRegisterDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度 3-20 位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度 6-20 位")
    private String password;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
```

### 3. VO（View Object）

**职责**：返回给前端的数据对象

**特点**：
- 过滤敏感字段（不返回 password）
- 可包含关联数据（如 categoryName, authorName）
- 实现 Serializable 接口
- 字段名对前端友好

**示例**：
```java
public class UserVO implements Serializable {
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String school;
    private Integer studentVerified;
    // 不包含：password, phone, email
}
```

## ✅ 已完成的 DTO 和 VO

### 用户模块

#### DTO（4 个）
- **UserRegisterDTO** - 用户注册
- **UserLoginDTO** - 用户登录
- **UserUpdateDTO** - 更新用户信息
- **PasswordChangeDTO** - 修改密码

#### VO（2 个）
- **UserVO** - 用户信息视图
- **LoginVO** - 登录响应（包含 token）

### 优惠模块

#### DTO（3 个）
- **DealCreateDTO** - 创建优惠
- **DealUpdateDTO** - 更新优惠
- **DealQueryDTO** - 查询条件

#### VO（3 个）
- **DealVO** - 优惠信息视图
- **CategoryVO** - 分类视图
- **TagVO** - 标签视图

### 社区模块

#### DTO（3 个）
- **PostCreateDTO** - 发布帖子
- **PostUpdateDTO** - 更新帖子
- **CommentCreateDTO** - 发表评论

#### VO（3 个）
- **PostVO** - 帖子视图
- **CommentVO** - 评论视图
- **TopicVO** - 话题视图

### 消息模块

#### DTO（1 个）
- **PrivateMessageSendDTO** - 发送私信

#### VO（2 个）
- **NotificationVO** - 通知视图
- **PrivateMessageVO** - 私信视图

### 系统模块

#### DTO（3 个）
- **AdminCreateDTO** - 创建管理员
- **SystemConfigDTO** - 系统配置
- **SensitiveWordDTO** - 敏感词

#### VO（3 个）
- **AdminVO** - 管理员视图
- **SystemConfigVO** - 配置视图
- **OperationLogVO** - 日志视图

## 🔧 MapStruct 转换器

### 为什么使用 MapStruct？

**传统做法的问题**：
```java
// ❌ 手动转换，代码冗长
public UserVO toVO(User user) {
    UserVO vo = new UserVO();
    vo.setUserId(user.getUserId());
    vo.setUsername(user.getUsername());
    vo.setNickname(user.getNickname());
    // ... 几十个字段
    return vo;
}
```

**MapStruct 的优势**：
- ✅ 编译时生成代码，性能高于反射
- ✅ 类型安全
- ✅ 减少样板代码
- ✅ 支持复杂映射

### 已创建的 Converter（4 个）

#### 1. UserConverter
```java
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    User toEntity(UserRegisterDTO dto);
    UserVO toVO(User user);
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}
```

#### 2. DealConverter
```java
@Mapper
public interface DealConverter {
    DealConverter INSTANCE = Mappers.getMapper(DealConverter.class);
    
    Deal toEntity(DealCreateDTO dto);
    DealVO toVO(Deal deal);
    void updateDealFromDTO(DealUpdateDTO dto, @MappingTarget Deal deal);
}
```

#### 3. PostConverter
```java
@Mapper
public interface PostConverter {
    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);
    
    Post toEntity(PostCreateDTO dto);
    PostVO toVO(Post post);
}
```

#### 4. CommentConverter
```java
@Mapper
public interface CommentConverter {
    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);
    
    Comment toEntity(CommentCreateDTO dto);
    CommentVO toVO(Comment comment);
}
```

## 📝 使用示例

### Controller 层使用

```java
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping("/register")
    public R<UserVO> register(@Valid @RequestBody UserRegisterDTO dto) {
        // DTO -> Entity
        User user = userConverter.toEntity(dto);
        // 保存
        userService.register(user);
        // Entity -> VO
        UserVO vo = userConverter.toVO(user);
        return R.ok(vo);
    }

    @GetMapping("/{id}")
    public R<UserVO> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        UserVO vo = userConverter.toVO(user);
        return R.ok(vo);
    }

    @PutMapping("/update")
    public R<Void> update(@Valid @RequestBody UserUpdateDTO dto) {
        User user = userService.getById(SecurityUtils.getUserId());
        // 部分更新
        userConverter.updateUserFromDTO(dto, user);
        userService.updateById(user);
        return R.ok();
    }
}
```

### Service 层使用

```java
@Service
public class DealServiceImpl implements DealService {

    @Autowired
    private DealMapper dealMapper;

    @Autowired
    private DealConverter dealConverter;

    @Override
    public DealVO createDeal(DealCreateDTO dto, Long userId) {
        // DTO -> Entity
        Deal deal = dealConverter.toEntity(dto);
        deal.setUserId(userId);
        deal.setStatus(0);  // 待审核
        deal.setViewCount(0L);
        // 保存
        dealMapper.insert(deal);
        // Entity -> VO
        return dealConverter.toVO(deal);
    }

    @Override
    public PageResult<DealVO> queryDeals(DealQueryDTO dto) {
        Page<Deal> page = new Page<>(dto.getPage(), dto.getSize());
        QueryWrapper<Deal> wrapper = new QueryWrapper<>();
        // 构建查询条件
        if (dto.getCategoryId() != null) {
            wrapper.eq("category_id", dto.getCategoryId());
        }
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            wrapper.like("title", dto.getKeyword());
        }
        // 分页查询
        Page<Deal> dealPage = dealMapper.selectPage(page, wrapper);
        // Entity -> VO
        List<DealVO> voList = dealPage.getRecords().stream()
            .map(dealConverter::toVO)
            .collect(Collectors.toList());
        // 返回分页结果
        return PageResult.of(voList, dealPage.getTotal(), dto.getPage(), dto.getSize());
    }
}
```

## 🎯 最佳实践

### 1. DTO 设计原则

✅ **按场景分类**
- UserRegisterDTO - 注册
- UserLoginDTO - 登录
- UserUpdateDTO - 更新
- PasswordChangeDTO - 改密

✅ **参数校验**
```java
@NotBlank(message = "用户名不能为空")
@Size(min = 3, max = 20, message = "用户名长度 3-20 位")
private String username;
```

✅ **不包含**
- 主键 ID（除非是更新场景）
- 审计字段（createTime, updateTime）
- 敏感字段（password 加密传输）

### 2. VO 设计原则

✅ **过滤敏感信息**
- 不返回 password
- 不返回 phone, email（除非必要）
- 不返回 deleted, status 等内部字段

✅ **可包含关联数据**
```java
public class DealVO {
    private Long dealId;
    private String title;
    // 关联查询
    private String categoryName;
    private String authorName;
    private String authorAvatar;
}
```

✅ **实现 Serializable**
```java
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}
```

### 3. Converter 使用原则

✅ **使用 MapStruct**
- 编译时生成代码
- 性能优于 BeanUtils
- 类型安全

✅ **定义更新方法**
```java
@Mapping(target = "userId", ignore = true)
@Mapping(target = "password", ignore = true)
void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
```

✅ **避免在循环中使用**
```java
// ❌ 低效
for (User user : users) {
    UserVO vo = userConverter.toVO(user);
}

// ✅ 高效
List<UserVO> voList = users.stream()
    .map(userConverter::toVO)
    .collect(Collectors.toList());
```

## 📊 对象转换流程

```
前端请求
   ↓
@RequestBody UserRegisterDTO
   ↓ (Controller)
UserConverter.toEntity()
   ↓
User Entity
   ↓ (Service)
userMapper.insert()
   ↓
User Entity (已保存)
   ↓ (Service)
UserConverter.toVO()
   ↓
UserVO
   ↓ (Controller)
R.ok(vo)
   ↓
返回给前端
```

## 🎓 总结

### 本次完成的工作

1. ✅ **DTO 类（17 个）**
   - 用户模块：4 个
   - 优惠模块：3 个
   - 社区模块：3 个
   - 消息模块：1 个
   - 系统模块：3 个

2. ✅ **VO 类（15 个）**
   - 用户模块：2 个
   - 优惠模块：3 个
   - 社区模块：3 个
   - 消息模块：2 个
   - 系统模块：3 个

3. ✅ **MapStruct Converter（4 个）**
   - UserConverter
   - DealConverter
   - PostConverter
   - CommentConverter

### 架构优势

1. **安全性提升**
   - 敏感字段不暴露
   - 参数校验在 DTO 层
   - 防止恶意注入

2. **解耦合**
   - 数据库变化不影响 API
   - 前端变化不影响数据库
   - 便于接口版本管理

3. **可维护性**
   - 职责清晰
   - 代码规范
   - 易于扩展

### 下一步建议

1. **完善 Converter**
   - 为所有模块创建 Converter
   - 处理复杂映射关系

2. **统一响应包装**
   - 分页结果包装
   - 树形结构包装

3. **参数校验增强**
   - 自定义校验注解
   - 分组校验

---

**开发状态**：✅ DTO 和 VO 开发完成  
**下一步**：业务逻辑层（sd-service 模块）
