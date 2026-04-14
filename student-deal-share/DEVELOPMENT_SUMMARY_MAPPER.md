# 数据访问层开发总结

## 📦 模块概述

**sd-mapper 模块** 完成了数据访问层的所有 Entity 实体类和 Mapper 接口的开发，基于 MyBatis Plus 实现高效的数据持久化操作。

## 📂 模块结构

```
sd-mapper/
└── src/main/java/com/student/dealshare/
    ├── config/
    │   └── MybatisPlusConfig.java          # MyBatis Plus 配置
    ├── mapper/                              # Mapper 接口目录
    │   ├── UserMapper.java
    │   ├── UserFollowMapper.java
    │   ├── UserFavoriteMapper.java
    │   ├── UserHistoryMapper.java
    │   ├── DealMapper.java
    │   ├── CategoryMapper.java
    │   ├── TagMapper.java
    │   ├── PostMapper.java
    │   ├── CommentMapper.java
    │   ├── LikeRecordMapper.java
    │   ├── TopicMapper.java
    │   ├── PostTopicMapper.java
    │   ├── NotificationMapper.java
    │   ├── PrivateMessageMapper.java
    │   ├── AdminMapper.java
    │   ├── SystemConfigMapper.java
    │   ├── OperationLogMapper.java
    │   └── SensitiveWordMapper.java
    └── model/                               # Entity 实体类目录（与 sd-model 共享）
        └── entity/
            ├── User.java
            ├── UserFollow.java
            ├── UserFavorite.java
            ├── UserHistory.java
            ├── Deal.java
            ├── Category.java
            ├── Tag.java
            ├── Post.java
            ├── Comment.java
            ├── LikeRecord.java
            ├── Topic.java
            ├── PostTopic.java
            ├── Notification.java
            ├── PrivateMessage.java
            ├── Admin.java
            ├── SystemConfig.java
            ├── OperationLog.java
            └── SensitiveWord.java
```

## ✅ 完成内容

### 1. Entity 实体类（18 个）

#### 用户模块（4 个）
- **User** - 用户表
- **UserFollow** - 用户关注表
- **UserFavorite** - 用户收藏表
- **UserHistory** - 用户浏览历史表

#### 优惠模块（3 个）
- **Deal** - 优惠信息表
- **Category** - 分类表
- **Tag** - 标签表

#### 社区模块（5 个）
- **Post** - 帖子表
- **Comment** - 评论表
- **LikeRecord** - 点赞记录表
- **Topic** - 话题表
- **PostTopic** - 帖子话题关联表

#### 消息模块（2 个）
- **Notification** - 系统通知表
- **PrivateMessage** - 私信表

#### 系统模块（4 个）
- **Admin** - 管理员表
- **SystemConfig** - 系统配置表
- **OperationLog** - 操作日志表
- **SensitiveWord** - 敏感词表

### 2. Entity 设计特点

#### 2.1 注解使用
```java
@TableName("user")                    // 指定表名
@TableId(value = "user_id", type = IdType.ASSIGN_ID)  // 主键策略：雪花算法
@TableField(fill = FieldFill.INSERT)  // 自动填充字段
@TableLogic                           // 逻辑删除
```

#### 2.2 主键策略
- 使用 `IdType.ASSIGN_ID`（雪花算法）生成唯一 ID
- 支持分布式系统，避免 ID 冲突

#### 2.3 自动填充
- `createTime` - 插入时自动填充
- `updateTime` - 插入和更新时自动填充
- 通过 `MybatisPlusConfig` 实现

#### 2.4 逻辑删除
- 所有表都支持逻辑删除
- 使用 `@TableLogic` 注解
- 删除时设置 `deleted=1`，查询时自动过滤

### 3. Mapper 接口（18 个）

#### 3.1 继承 BaseMapper
所有 Mapper 接口都继承 `com.baomidou.mybatisplus.core.mapper.BaseMapper<T>`，自动获得以下能力：

```java
public interface UserMapper extends BaseMapper<User> {
    // 自动获得 17 个基础 CRUD 方法：
    // insert, deleteById, deleteByMap, delete, updateById, update, 
    // selectById, selectBatchIds, selectByMap, selectList, selectPage,
    // selectMaps, selectMapsPage, selectObjs, selectCount, selectOne, selectList
}
```

#### 3.2 @Mapper 注解
- 每个 Mapper 接口都标注 `@Mapper`
- Spring Boot 启动时自动扫描并创建代理对象

### 4. MyBatis Plus 配置

#### 4.1 分页插件
```java
@Bean
public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MARIADB));
    return interceptor;
}
```

#### 4.2 自动填充处理器
```java
@Override
public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
}

@Override
public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
}
```

## 🔧 技术要点

### 1. 序列化版本 ID
所有 Entity 都实现了 `Serializable` 接口，并定义了 `serialVersionUID`：
```java
@Serial
private static final long serialVersionUID = 1L;
```

### 2. 数据类型映射

| Java 类型 | 数据库类型 | 说明 |
|-----------|-----------|------|
| Long | BIGINT | 主键、外键 |
| String | VARCHAR | 文本字段 |
| Integer | INT | 状态、类型、计数 |
| BigDecimal | DECIMAL | 价格 |
| LocalDateTime | DATETIME | 时间戳 |
| String[] | JSON/VARCHAR | 图片数组 |

### 3. 索引优化预留
虽然 Entity 中没有直接定义索引，但数据库设计文档中已经规划了索引：
- 外键字段索引（如 `user_id`, `post_id`）
- 查询条件索引（如 `status`, `category_id`）
- 联合索引（如 `(user_id, create_time)`）

## 📊 使用示例

### 1. 注入 Mapper
```java
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
}
```

### 2. 基础 CRUD 操作
```java
// 插入
User user = new User();
user.setUsername("test");
userMapper.insert(user);

// 查询
User user = userMapper.selectById(1L);
List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("status", 1));

// 分页
Page<User> page = new Page<>(1, 10);
userMapper.selectPage(page, new QueryWrapper<User>());

// 更新
user.setNickname("新昵称");
userMapper.updateById(user);

// 删除
userMapper.deleteById(1L);  // 逻辑删除
```

### 3. 条件构造器
```java
// LambdaQueryWrapper（类型安全）
LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
    .eq(User::getStatus, 1)
    .like(User::getNickname, "张三")
    .orderByDesc(User::getCreateTime);
List<User> users = userMapper.selectList(wrapper);
```

### 4. 分页查询
```java
// 方式 1：使用 Page 对象
Page<User> page = new Page<>(1, 10);
userMapper.selectPage(page, new QueryWrapper<User>().eq("status", 1));

// 方式 2：Service 层封装
IPage<User> userPage = userService.page(new Page<>(1, 10), 
    new QueryWrapper<User>().eq("status", 1));
```

## 🎯 最佳实践

### 1. Entity 设计原则
- ✅ 一个类对应一张表
- ✅ 使用包装类型而非基本类型（支持 null）
- ✅ 实现 Serializable 接口
- ✅ 使用 Lombok 简化代码
- ✅ 字段名与数据库列名一致

### 2. Mapper 使用原则
- ✅ 优先使用 BaseMapper 提供的方法
- ✅ 复杂查询使用 Wrapper 构造条件
- ✅ 避免在 Mapper 层编写简单 SQL
- ✅ 多表联查使用 XML 或@Select 注解

### 3. 性能优化建议
- ✅ 使用分页查询避免全表扫描
- ✅ 合理设计索引
- ✅ 批量操作使用 `insertBatch`
- ✅ 缓存热点数据（Redis）

## 📝 后续工作建议

### 1. XML 映射文件（可选）
对于复杂的多表联查，可以创建 XML 映射文件：
```xml
<!-- resources/mapper/UserMapper.xml -->
<mapper namespace="com.student.dealshare.mapper.UserMapper">
    <resultMap id="UserDetailMap" type="User">
        <!-- 关联查询映射 -->
    </resultMap>
</mapper>
```

### 2. 自定义查询方法
在 Mapper 接口中添加自定义方法：
```java
public interface DealMapper extends BaseMapper<Deal> {
    List<Deal> selectHotDeals(@Param("limit") Integer limit);
    Page<Deal> searchDeals(Page<Deal> page, @Param("keyword") String keyword);
}
```

### 3. 数据权限
结合 MyBatis Plus 的数据权限插件，实现行级数据权限控制。

### 4. 审计字段
添加创建人、更新人等审计字段：
```java
@TableField(fill = FieldFill.INSERT)
private Long createBy;

@TableField(fill = FieldFill.INSERT_UPDATE)
private Long updateBy;
```

## 🎓 学习资源

- [MyBatis Plus 官方文档](https://baomidou.com/)
- [MyBatis Plus 代码生成器](https://baomidou.com/pages/223848/)
- [MyBatis Plus 插件扩展](https://baomidou.com/pages/825a6d/)

---

**开发状态**：✅ 数据访问层开发完成  
**下一步**：业务逻辑层（sd-service 模块）
