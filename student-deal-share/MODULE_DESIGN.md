# Maven 分模块化设计说明

## 模块划分原则

基于资深 Java 开发经验，本项目采用以下模块划分原则：

### 1. 单一职责原则
每个模块只负责一个特定领域，保持高内聚、低耦合。

### 2. 分层架构原则
按照标准的分层架构设计模块：
- **数据层**: sd-mapper
- **业务层**: sd-service-api / sd-service-impl
- **控制层**: sd-controller
- **安全层**: sd-security

### 3. 依赖倒置原则
上层模块依赖下层模块的接口，而非实现。

### 4. 可复用原则
公共功能抽取到 sd-common 模块，提高代码复用率。

## 模块详细设计

### sd-common (公共模块)
**职责**: 提供整个项目通用的基础功能

**包含内容**:
- `constant/`: 常量定义
  - 系统常量、业务常量
- `enums/`: 枚举类
  - 通用枚举类型
- `exception/`: 异常类
  - 业务异常、全局异常
- `utils/`: 工具类
  - 通用工具、业务工具

**依赖**: 无内部依赖

**被依赖**: 所有模块

---

### sd-model (数据模型模块)
**职责**: 定义数据结构和数据传输对象

**包含内容**:
- `entity/`: 实体类
  - 数据库表对应的实体
  - MyBatis Plus Entity
- `dto/`: 数据传输对象
  - 请求参数对象
  - 业务传输对象
- `vo/`: 视图对象
  - 响应数据对象
  - 前端展示对象
- `enums/`: 业务枚举
  - 业务相关枚举

**依赖**: sd-common

**被依赖**: sd-mapper, sd-service-api

**设计理由**: 
- 集中管理所有数据模型，便于维护
- 避免循环依赖
- 方便进行对象映射和转换

---

### sd-mapper (数据访问层模块)
**职责**: 数据库访问操作

**包含内容**:
- `mapper/`: Mapper 接口
  - MyBatis Mapper 接口
  - 继承 BaseMapper
- `resources/mapper/`: XML 映射文件
  - SQL 语句定义

**依赖**: sd-model

**被依赖**: sd-service-impl

**设计理由**:
- 独立的数据访问层，便于单元测试
- 集中管理 SQL，便于优化
- 方便进行数据源切换

---

### sd-service-api (服务接口模块)
**职责**: 定义业务服务接口

**包含内容**:
- `service/`: 服务接口
  - 业务接口定义
  - 服务契约

**依赖**: sd-model

**被依赖**: sd-service-impl, sd-controller

**设计理由**:
- 接口与实现分离，符合依赖倒置原则
- 便于模块解耦
- 方便进行服务替换和扩展

---

### sd-service-impl (服务实现模块)
**职责**: 实现业务逻辑

**包含内容**:
- `service/impl/`: 服务实现类
  - 业务逻辑实现
  - 事务控制

**依赖**: sd-service-api, sd-mapper, sd-common

**被依赖**: sd-controller, sd-admin

**设计理由**:
- 与接口模块分离，降低耦合
- 便于独立测试业务逻辑
- 方便进行 AOP 编程

---

### sd-security (安全认证模块)
**职责**: 安全认证和权限控制

**包含内容**:
- `config/`: 安全配置
  - Spring Security 配置
  - JWT 配置
- `filter/`: 过滤器
  - JWT 过滤器
  - 认证过滤器
- `handler/`: 处理器
  - 认证失败处理器
  - 访问 denied 处理器
- `util/`: 安全工具
  - JWT 工具类
  - 加密工具类

**依赖**: sd-common, sd-model

**被依赖**: sd-controller, sd-admin

**设计理由**:
- 安全功能独立，便于维护和升级
- 可插拔设计，方便扩展
- 集中管理安全相关配置

---

### sd-controller (控制器模块)
**职责**: 提供 REST API 接口

**包含内容**:
- `controller/`: 控制器类
  - REST 接口实现
  - 参数校验
  - 响应处理

**依赖**: sd-service-impl, sd-security

**被依赖**: sd-admin

**设计理由**:
- 控制层独立，便于 API 管理
- 集中处理请求和响应
- 方便进行接口文档生成

---

### sd-search (搜索模块)
**职责**: 搜索功能实现

**包含内容**:
- `controller/`: 搜索接口
- `service/`: 搜索服务
- `util/`: 搜索工具

**依赖**: sd-service-impl

**被依赖**: sd-admin

**设计理由**:
- 搜索功能相对独立
- 便于后期扩展搜索引擎（Elasticsearch 等）
- 可独立优化搜索性能

---

### sd-admin (后台管理模块 - 主启动模块)
**职责**: 应用启动和后台管理功能

**包含内容**:
- `controller/`: 后台管理接口
- `AdminApplication.java`: 启动类
- `resources/`: 配置文件

**依赖**: sd-service-impl, sd-security, sd-controller

**被依赖**: 无

**设计理由**:
- 作为主启动模块，整合所有功能
- 后台管理功能独立
- 便于部署和配置

## 模块依赖图

```
                    sd-admin
                      │
         ┌────────────┼────────────┐
         │            │            │
    sd-controller  sd-search   sd-security
         │            │            │
         └────────────┼────────────┘
                      │
              sd-service-impl
                      │
              sd-service-api
                      │
         ┌────────────┼────────────┐
         │            │            │
      sd-mapper    sd-model     sd-common
         │            │            │
         └────────────┘            │
                      │            │
                      └────────────┘
```

## 模块依赖树

```
sd-admin
├── sd-controller
│   ├── sd-service-impl
│   │   ├── sd-service-api
│   │   │   └── sd-model
│   │   │       └── sd-common
│   │   ├── sd-mapper
│   │   │   └── sd-model
│   │   └── sd-common
│   └── sd-security
│       ├── sd-common
│       └── sd-model
├── sd-search
│   └── sd-service-impl
└── sd-security
```

## 为什么这样划分？

### 1. 避免循环依赖
通过将 sd-model 独立，其他模块都依赖它，避免了常见的 Entity 和 Mapper 之间的循环依赖问题。

### 2. 便于测试
- sd-common 和 sd-model 可以独立测试
- sd-service-impl 可以 Mock sd-mapper 进行单元测试
- sd-controller 可以 Mock sd-service 进行集成测试

### 3. 便于扩展
- 新增功能只需在对应模块添加代码
- 可以独立发布和版本化某些模块
- 便于微服务拆分（每个模块可以独立成微服务）

### 4. 职责清晰
- 每个模块职责单一明确
- 新成员快速理解项目结构
- 便于代码审查和维护

### 5. 符合 Spring Boot 最佳实践
- 标准的分层架构
- 便于使用 Spring Boot 的特性
- 便于集成第三方库

## 包命名规范

```
com.student.dealshare
├── common          # 公共包
│   ├── constant    # 常量
│   ├── enums       # 枚举
│   ├── exception   # 异常
│   └── utils       # 工具类
├── model           # 模型包
│   ├── entity      # 实体
│   ├── dto         # 数据传输对象
│   ├── vo          # 视图对象
│   └── enums       # 业务枚举
├── mapper          # 数据访问包
├── service         # 服务包
│   ├── impl        # 服务实现
│   └── ...         # 各业务服务
├── controller      # 控制器包
└── security        # 安全包
    ├── config      # 配置
    ├── filter      # 过滤器
    ├── handler     # 处理器
    └── util        # 工具
```

## 后续扩展建议

### 1. 引入 Elasticsearch
可新增 `sd-elasticsearch` 模块，专门处理搜索功能。

### 2. 引入消息队列
可新增 `sd-mq` 模块，处理异步消息。

### 3. 微服务拆分
当业务发展到一定规模，可以按模块拆分为微服务：
- 用户服务 (sd-user)
- 优惠服务 (sd-deal)
- 社区服务 (sd-community)
- 搜索服务 (sd-search)

### 4. 引入分布式事务
可新增 `sd-transaction` 模块，处理分布式事务。

## 总结

当前模块划分基于以下考虑：
1. **业务复杂度**: 中等复杂度的单体应用
2. **团队规模**: 适合 5-10 人团队开发
3. **技术栈**: Spring Boot 3 + MyBatis Plus
4. **可扩展性**: 便于后期微服务拆分
5. **可维护性**: 职责清晰，易于维护

这是一个平衡了当前需求和未来扩展的方案，既不过度设计，也留有足够的扩展空间。
