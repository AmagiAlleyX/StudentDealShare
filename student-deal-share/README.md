# 大学生资源信息分享平台 - 后端工程

## 项目介绍

大学生资源信息分享平台是一个面向大学生的垂直社交电商平台，聚合各类优惠活动信息，提供快速抢购通道，搭建用户经验分享社区。

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.2.0
- **MyBatis-Plus**: 3.5.5
- **MySQL**: 8.0
- **Redis**: 7.0
- **JWT**: 0.12.3
- **Knife4j**: 4.3.0 (接口文档)

## 模块结构

```
student-deal-share/
├── sd-common/          # 公共模块（工具类、常量、异常等）
├── sd-model/           # 数据模型模块（Entity、DTO、VO 等）
├── sd-mapper/          # 数据访问层模块（MyBatis Mapper）
├── sd-service/         # 业务服务层模块
│   ├── sd-service-api/     # 服务接口定义
│   └── sd-service-impl/    # 服务实现
├── sd-security/        # 安全认证模块（JWT、权限控制）
├── sd-controller/      # 控制器模块（REST API 接口）
├── sd-search/          # 搜索模块
└── sd-admin/           # 后台管理模块（主启动模块）
```

## 模块依赖关系

```
sd-admin (主模块)
    ├── sd-controller
    │       └── sd-service-impl
    │               ├── sd-service-api
    │               │       └── sd-model
    │               └── sd-mapper
    │                       └── sd-model
    └── sd-security
            ├── sd-common
            └── sd-model

sd-common (基础模块，无内部依赖)
sd-model (数据模型，无内部依赖)
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+

### 安装步骤

1. 克隆项目
```bash
git clone https://github.com/your-repo/student-deal-share.git
cd student-deal-share
```

2. 创建数据库
```sql
CREATE DATABASE student_deal_share DEFAULT CHARACTER SET utf8mb4;
```

3. 修改配置文件
编辑 `sd-admin/src/main/resources/application-dev.yml`，配置数据库和 Redis 连接

4. 编译项目
```bash
mvn clean install
```

5. 启动应用
```bash
cd sd-admin
mvn spring-boot:run
```

### 访问地址

- 接口文档：http://localhost:8080/doc.html
- Swagger UI: http://localhost:8080/swagger-ui.html
- Druid 监控：http://localhost:8080/druid/

## 配置说明

### 多环境配置

项目支持多环境配置：

- `application.yml`: 通用配置
- `application-dev.yml`: 开发环境
- `application-prod.yml`: 生产环境

通过 `spring.profiles.active` 切换环境。

### 主要配置项

1. **数据库配置**: 在 `application-dev.yml` 中配置
2. **Redis 配置**: 在 `application.yml` 中配置
3. **JWT 配置**: 在 `sd-security` 模块中配置

## 开发规范

### 代码规范

- 使用 Lombok 简化代码
- 使用 MapStruct 进行对象转换
- 统一使用 ResponseEntity 返回结果
- 异常统一由全局异常处理器处理

### 命名规范

- 模块前缀：`sd-` (student-dealshare 缩写)
- 包名：`com.student.dealshare.模块名`
- 类名：见名知意，使用驼峰命名

### 接口规范

- RESTful 风格
- 统一返回格式：`{code, message, data}`
- 使用 JWT 进行身份认证

## 测试

### 单元测试
```bash
mvn test
```

### 接口测试
使用 Postman 或 Apifox 导入接口文档进行测试

## 部署

详见 [项目部署说明](../docs/项目部署说明.md)

## 相关文档

- [需求分析文档](../docs/需求分析文档.md)
- [概要设计文档](../docs/概要设计文档.md)
- [数据库设计文档](../docs/数据库设计文档.md)
- [API 接口文档](../docs/API 接口文档.md)
- [测试计划与报告](../docs/测试计划与报告.md)
- [项目部署说明](../docs/项目部署说明.md)

## 常见问题

详见 [项目部署说明](../docs/项目部署说明.md#9-常见问题)

## 贡献指南

1. Fork 本仓库
2. 创建特性分支
3. 提交变更
4. 推送到分支
5. 创建 Pull Request

## 许可证

Copyright © 2024 大学生资源信息分享平台

## 联系方式

- 项目邮箱：support@dealshare.com
- 问题反馈：https://github.com/your-repo/student-deal-share/issues
