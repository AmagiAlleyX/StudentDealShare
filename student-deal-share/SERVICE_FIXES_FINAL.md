# Service 层错误修复总结

## ✅ 已修复的错误

### 错误 1：ResultCodeEnum 枚举值不完整

**问题描述**：
- `ALREADY_LIKED(5006,"xxx" )` 使用了占位符 "xxx"
- `USERNAME_ALREADY_EXISTS(5007,"xxxxx" )` 使用了占位符 "xxxxx"
- 缺少多个业务相关的错误码

**修复方案**：
完善所有枚举值，使用正确的错误提示信息：

```java
/**
 * 已点赞
 */
ALREADY_LIKED(5006, "已点赞"),

/**
 * 用户名已存在
 */
USERNAME_ALREADY_EXISTS(5007, "用户名已存在"),

/**
 * 无效的凭证
 */
INVALID_CREDENTIALS(1010, "用户名或密码错误"),

/**
 * 用户不存在
 */
USER_NOT_FOUND(1011, "用户不存在"),

/**
 * 密码错误
 */
INVALID_PASSWORD(1012, "密码错误"),

/**
 * 不能关注自己
 */
CANNOT_FOLLOW_SELF(1013, "不能关注自己"),

/**
 * 已关注
 */
ALREADY_FOLLOWED(1014, "已关注"),

/**
 * 已收藏
 */
ALREADY_FAVORITED(2006, "已收藏");
```

**修复文件**：
- `/sd-common/src/main/java/com/student/dealshare/common/result/ResultCodeEnum.java`

---

### 错误 2：JwtTokenProvider 类不存在

**问题描述**：
- UserServiceImpl 中使用了 `JwtTokenProvider`
- 该类未创建，导致编译错误

**修复方案**：
创建完整的 JWT Token 提供者类：

```java
package com.student.dealshare.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:student-deal-share-secret-key-2024}")
    private String secret;

    @Value("${jwt.expire:86400000}")
    private Long expire;

    public String createToken(Long userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expire);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("验证 Token 失败：{}", e.getMessage());
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getExpireTime() {
        return expire;
    }
}
```

**修复文件**：
- `/sd-security/src/main/java/com/student/dealshare/security/JwtTokenProvider.java`

---

### 错误 3：OperationLog 导入缺失

**问题描述**：
- OperationLogAspect 中使用 OperationLog 但未导入

**修复方案**：
添加导入语句：

```java
import com.student.dealshare.model.entity.OperationLog;
```

**修复文件**：
- `/sd-service/sd-service-impl/src/main/java/com/student/dealshare/aspect/OperationLogAspect.java`

---

### 错误 4：sd-service-impl 缺少 sd-security 依赖

**问题描述**：
- Service 实现类无法识别 SecurityUtils
- JwtTokenProvider 也无法注入

**根本原因**：
- `sd-service-impl/pom.xml` 缺少 sd-security 依赖

**修复方案**：
添加依赖：

```xml
<dependency>
    <groupId>com.student.dealshare</groupId>
    <artifactId>sd-security</artifactId>
</dependency>
```

**修复文件**：
- `/sd-service/sd-service-impl/pom.xml`

---

### 错误 5：UserServiceImpl 缺少 pageUsers 方法实现

**问题描述**：
- UserService 接口定义了 `pageUsers` 方法
- UserServiceImpl 未实现该方法

**修复方案**：
添加实现：

```java
@Override
public Page<UserVO> pageUsers(int page, int size) {
    Page<User> userPage = new Page<>(page, size);
    Page<User> result = userMapper.selectPage(userPage, null);
    
    return result.convert(userConverter::toVO);
}
```

**修复文件**：
- `/sd-service/sd-service-impl/src/main/java/com/student/dealshare/service/impl/UserServiceImpl.java`

---

## 📊 错误码完整列表

### 通用错误码（200-599）

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 参数验证失败 |
| 401 | 未授权，请先登录 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 405 | 方法不支持 |
| 500 | 服务器内部错误 |

### 用户相关错误码（1000-1999）

| 错误码 | 说明 |
|--------|------|
| 1001 | 用户名已存在 |
| 1002 | 手机号已存在 |
| 1003 | 邮箱已存在 |
| 1004 | 用户名或密码错误 |
| 1005 | 用户已被禁用 |
| 1006 | 验证码错误 |
| 1007 | 验证码已过期 |
| 1008 | 旧密码错误 |
| 1009 | 用户未登录 |
| 1010 | 用户名或密码错误 |
| 1011 | 用户不存在 |
| 1012 | 密码错误 |
| 1013 | 不能关注自己 |
| 1014 | 已关注 |

### 优惠相关错误码（2000-2999）

| 错误码 | 说明 |
|--------|------|
| 2001 | 优惠信息不存在 |
| 2002 | 优惠已下架 |
| 2003 | 优惠活动未开始 |
| 2004 | 优惠活动已结束 |
| 2005 | 分类不存在 |
| 2006 | 已收藏 |

### 社区相关错误码（3000-3999）

| 错误码 | 说明 |
|--------|------|
| 3001 | 帖子不存在 |
| 3002 | 评论不存在 |
| 3003 | 话题不存在 |
| 3004 | 帖子已被删除 |

### 消息相关错误码（4000-4999）

| 错误码 | 说明 |
|--------|------|
| 4001 | 消息不存在 |

### 通用业务错误码（5000-5999）

| 错误码 | 说明 |
|--------|------|
| 5001 | 数据不存在 |
| 5002 | 数据已存在 |
| 5003 | 操作被拒绝 |
| 5004 | 无权限操作 |
| 5005 | 超过限制 |
| 5006 | 已点赞 |

---

## 🔧 JWT 相关配置

### 依赖（父 pom.xml）

```xml
<properties>
    <jwt.version>0.12.3</jwt.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jwt.version}</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 模块依赖（sd-security/pom.xml）

```xml
<dependencies>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### 配置文件（application.yml）

```yaml
jwt:
  secret: student-deal-share-secret-key-2024-this-is-a-very-long-secret-key
  expire: 86400000  # 24 小时
```

---

## 📝 使用示例

### 1. 用户注册

```java
@PostMapping("/register")
public R<LoginVO> register(@Valid @RequestBody UserRegisterDTO dto) {
    LoginVO result = userService.register(dto);
    return R.ok(result);
}

// Service 层
@Override
@Transactional(rollbackFor = Exception.class)
public LoginVO register(UserRegisterDTO dto) {
    // 检查用户名是否存在
    if (existUser != null) {
        throw new BusinessException(ResultCodeEnum.USERNAME_ALREADY_EXISTS);
    }
    
    // 创建用户并生成 Token
    return buildLoginVO(user);
}
```

### 2. 用户登录

```java
@PostMapping("/login")
public R<LoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
    LoginVO result = userService.login(dto);
    return R.ok(result);
}

// Service 层
@Override
public LoginVO login(UserLoginDTO dto) {
    if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        throw new BusinessException(ResultCodeEnum.INVALID_CREDENTIALS);
    }
    
    return buildLoginVO(user);
}
```

### 3. 关注用户

```java
@PostMapping("/follow/{followeeId}")
public R<Void> followUser(@PathVariable Long followeeId) {
    userService.followUser(followeeId);
    return R.ok();
}

// Service 层
@Override
public void followUser(Long followeeId) {
    Long followerId = SecurityUtils.getCurrentUserId();
    
    if (followerId.equals(followeeId)) {
        throw new BusinessException(ResultCodeEnum.CANNOT_FOLLOW_SELF);
    }
    
    // 检查是否已关注
    if (exist != null) {
        throw new BusinessException(ResultCodeEnum.ALREADY_FOLLOWED);
    }
    
    // 创建关注记录
}
```

### 4. 点赞功能

```java
@PostMapping("/like/{id}")
public R<Void> likePost(@PathVariable Long id) {
    postService.likePost(id);
    return R.ok();
}

// Service 层
@Override
public void likePost(Long postId) {
    Long userId = SecurityUtils.getCurrentUserId();
    
    // 检查是否已点赞
    if (exist != null) {
        throw new BusinessException(ResultCodeEnum.ALREADY_LIKED);
    }
    
    // 创建点赞记录
}
```

---

## 🔍 验证步骤

1. **重新加载 Maven 项目**
   ```
   右键项目 -> Maven -> Reload Project
   ```

2. **清理并编译**
   ```bash
   mvn clean install -DskipTests
   ```

3. **检查错误**
   - ResultCodeEnum 应该有完整的错误码
   - JwtTokenProvider 应该可以正常注入
   - SecurityUtils 应该可以正常使用
   - 所有 Service 方法都应该有实现

---

**修复完成时间**：2024-01-XX  
**修复状态**：✅ 已完成
