# sd-common 模块开发完成总结

## ✅ 已完成的内容

### 1. 统一响应结果（result 包）

#### R.java - 统一响应类
- 统一的 API 响应格式
- 支持泛型数据返回
- 提供多种构建方法（ok/fail）
- 包含时间戳
- 实现 Serializable 接口

**使用示例**:
```java
// 成功响应
return R.ok(data);

// 失败响应
return R.fail("操作失败");

// 使用枚举
return R.fail(ResultCodeEnum.USER_NOT_LOGIN);
```

#### ResultCodeEnum.java - 状态码枚举
- **2xx**: 成功/失败通用码
- **1xxx**: 用户相关错误码
- **2xxx**: 优惠相关错误码
- **3xxx**: 社区相关错误码
- **4xxx**: 消息相关错误码
- **5xxx**: 通用业务错误码

**错误码规范**:
- 200: 成功
- 400: 参数错误
- 401: 未授权
- 403: 禁止访问
- 404: 资源不存在
- 500: 服务器错误

#### PageResult.java - 分页结果类
- 包含数据列表
- 包含总记录数
- 自动计算总页数
- 提供空结果构造方法

### 2. 异常处理（exception 包）

#### BusinessException.java - 业务异常类
- 继承 RuntimeException
- 支持错误码和消息
- 支持从枚举构造
- 支持自定义错误码

**使用示例**:
```java
// 抛出业务异常
throw new BusinessException("用户名已存在");

// 使用枚举
throw new BusinessException(ResultCodeEnum.USERNAME_EXISTS);

// 自定义错误码
throw new BusinessException(1001, "用户名已存在");
```

#### GlobalExceptionHandler.java - 全局异常处理器
- 使用 `@RestControllerAdvice` 实现全局捕获
- 处理业务异常
- 处理参数验证异常
- 处理空指针异常
- 处理其他未知异常
- 记录详细日志

**处理的异常类型**:
- `BusinessException`: 业务异常
- `MethodArgumentNotValidException`: 参数验证异常
- `BindException`: 参数绑定异常
- `IllegalArgumentException`: 非法参数异常
- `NullPointerException`: 空指针异常
- `Exception`: 其他异常

### 3. 常量类（constant 包）

#### SystemConstant.java - 系统常量
**包含内容**:
- 分页常量（默认大小、最大大小）
- 删除标识（0-未删除，1-已删除）
- 状态标识（0-禁用，1-正常，2-审核中）
- JWT 相关常量
- Redis Key 前缀
- 缓存过期时间

**使用示例**:
```java
// 分页
Integer page = SystemConstant.DEFAULT_PAGE;
Integer size = SystemConstant.DEFAULT_PAGE_SIZE;

// 删除标识
entity.setDeleted(SystemConstant.DELETED_NO);

// Redis Key
String key = SystemConstant.RedisKey.USER_TOKEN + userId;

// 缓存时间
Long expire = SystemConstant.CacheExpire.SMS_CODE;
```

#### BusinessConstant.java - 业务常量
**包含内容**:
- 优惠类型（预售、折扣、优惠券等）
- 目标类型（优惠、帖子、评论）
- 消息类型（系统、点赞、评论等）
- 帖子类型（图文、视频）
- 管理员角色
- 标签类型
- 敏感词类型

**使用示例**:
```java
// 优惠类型
deal.setType(BusinessConstant.DEAL_TYPE_FLASH_SALE);

// 目标类型
favorite.setTargetType(BusinessConstant.TARGET_TYPE_DEAL);

// 消息类型
notification.setType(BusinessConstant.MESSAGE_TYPE_LIKE);
```

### 4. 工具类（util 包）

#### StringUtils.java - 字符串工具类
- 继承 Hutool 的 StrUtil
- 扩展常用字符串方法
- 空值判断
- 大小写转换

**主要方法**:
```java
StringUtils.isEmpty(str);
StringUtils.isNotEmpty(str);
StringUtils.isBlank(str);
StringUtils.isNotBlank(str);
StringUtils.trim(str);
StringUtils.toLowerCase(str);
StringUtils.toUpperCase(str);
```

#### CodeUtil.java - 验证码工具类
- 生成数字验证码
- 生成混合验证码
- 生成图形验证码
- 验证码校验

**使用示例**:
```java
// 生成 6 位数字验证码
String code = CodeUtil.generateNumericCode();

// 生成 4 位图形验证码
String imageCode = CodeUtil.generateImageCode(4);

// 验证验证码
boolean valid = CodeUtil.validateCode(inputCode, rightCode);
```

## 📦 包结构

```
com.student.dealshare.common
├── result/                      # 响应结果相关
│   ├── R.java                  # 统一响应类
│   ├── ResultCodeEnum.java     # 状态码枚举
│   └── PageResult.java         # 分页结果类
├── exception/                   # 异常相关
│   ├── BusinessException.java  # 业务异常类
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── constant/                    # 常量相关
│   ├── SystemConstant.java     # 系统常量
│   └── BusinessConstant.java   # 业务常量
└── util/                        # 工具类
    ├── StringUtils.java        # 字符串工具
    └── CodeUtil.java           # 验证码工具
```

## 🎯 设计亮点

### 1. 统一规范
- 统一的响应格式
- 统一的错误码规范
- 统一的异常处理

### 2. 易于扩展
- 错误码枚举便于新增
- 常量分类清晰
- 工具类基于 Hutool 扩展

### 3. 类型安全
- 使用枚举代替魔法数字
- 泛型支持
- 强类型检查

### 4. 开发友好
- 丰富的构造方法
- 清晰的命名
- 详细的注释

### 5. 生产就绪
- 实现 Serializable
- 完整的异常处理
- 日志记录

## 📝 使用示例

### Controller 中使用

```java
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public R<UserVO> register(@RequestBody @Validated RegisterDTO dto) {
        UserVO userVO = userService.register(dto);
        return R.ok(userVO);
    }

    @GetMapping("/{id}")
    public R<UserVO> getUser(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return R.ok(userVO);
    }

    @GetMapping("/list")
    public R<PageResult<UserVO>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<UserVO> pageResult = userService.list(page, size);
        return R.ok(pageResult);
    }
}
```

### Service 中使用

```java
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserVO register(RegisterDTO dto) {
        // 检查用户名是否存在
        if (userMapper.selectByUsername(dto.getUsername()) != null) {
            throw new BusinessException(ResultCodeEnum.USERNAME_EXISTS);
        }

        // 检查手机号是否存在
        if (userMapper.selectByPhone(dto.getPhone()) != null) {
            throw new BusinessException(ResultCodeEnum.PHONE_EXISTS);
        }

        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setStatus(SystemConstant.STATUS_NORMAL);
        user.setDeleted(SystemConstant.DELETED_NO);

        userMapper.insert(user);

        return convertToVO(user);
    }
}
```

### 常量使用

```java
// Redis 操作
String key = SystemConstant.RedisKey.USER_TOKEN + userId;
redisTemplate.opsForValue().set(key, token, 
    SystemConstant.CacheExpire.USER_TOKEN, TimeUnit.SECONDS);

// 业务判断
if (deal.getType().equals(BusinessConstant.DEAL_TYPE_FLASH_SALE)) {
    // 限时抢购逻辑
}

// 状态判断
if (user.getStatus().equals(SystemConstant.STATUS_NORMAL)) {
    // 用户正常逻辑
}
```

## 🔧 后续开发建议

### 下一步：sd-model 模块
创建 Entity、DTO、VO 等数据模型类

### 再下一步：sd-mapper 模块
创建 Mapper 接口和 XML 映射文件

### 然后：sd-service 模块
创建服务接口和实现

### 最后：sd-controller 模块
创建 REST API 控制器

## 📋 检查清单

- [x] 统一响应结果类 R
- [x] 状态码枚举 ResultCodeEnum
- [x] 分页结果类 PageResult
- [x] 业务异常类 BusinessException
- [x] 全局异常处理器 GlobalExceptionHandler
- [x] 系统常量 SystemConstant
- [x] 业务常量 BusinessConstant
- [x] 字符串工具 StringUtils
- [x] 验证码工具 CodeUtil

---

**sd-common 模块开发完成！** 🎉

现在整个项目的基础设施已经就绪，可以开始开发上层代码了！
