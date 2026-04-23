# 项目错误修复说明

## ✅ 已修复的错误

### 错误 1：application.yml 有两个 spring 配置块

**问题描述**：
- `application.yml` 文件中存在两个 `spring:` 配置块
- 第二个 spring 配置（Redis）会导致第一个被覆盖

**修复方案**：
- 将 Redis 配置合并到第一个 spring 配置块中
- 删除重复的 spring 配置

**修复文件**：
- `/sd-admin/src/main/resources/application.yml`

### 错误 2、3：dev.yml 无法加载 MariaDB 驱动和 Druid 连接池

**问题描述**：
- IDEA 报错 `driver-class-name: org.mariadb.jdbc.Driver` 无法识别
- IDEA 报错 `type: com.alibaba.druid.pool.DruidDataSource` 无法解析

**根本原因**：
- `sd-admin/pom.xml` 缺少必要的依赖

**修复方案**：
在 `sd-admin/pom.xml` 中添加以下依赖：

```xml
<!-- MariaDB 驱动 -->
<dependency>
    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
</dependency>

<!-- Druid 连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-3-starter</artifactId>
</dependency>

<!-- Spring Boot Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

**修复文件**：
- `/sd-admin/pom.xml`

### 错误 4：prod.yml 同样的驱动和连接池问题

**问题描述**：
- 与错误 2、3 相同
- `prod.yml` 也无法识别 MariaDB 驱动和 Druid 连接池

**修复方案**：
- 与错误 2、3 一起修复，添加相同的依赖

**修复文件**：
- `/sd-admin/pom.xml`

### 错误 5：全局异常处理器无法识别 Spring 注解

**问题描述**：
- `GlobalExceptionHandler.java` 中的 `@Slf4j`、`@RestControllerAdvice`、`@ExceptionHandler` 等注解无法识别
- IDEA 爆红

**根本原因**：
- `sd-common/pom.xml` 缺少 `spring-boot-starter-web` 依赖
- 这些注解来自 Spring Web 模块

**修复方案**：
在 `sd-common/pom.xml` 中添加：

```xml
<!-- Spring Boot Web (提供注解支持) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <scope>provided</scope>
</dependency>
```

**修复文件**：
- `/sd-common/pom.xml`

### 错误 6：R 类的 @Schema 注解无法识别

**问题描述**：
- `R.java` 中的 `@Schema` 注解无法识别
- IDEA 爆红

**根本原因**：
- `sd-common/pom.xml` 缺少 Swagger 注解依赖

**修复方案**：
在 `sd-common/pom.xml` 中添加：

```xml
<!-- Knife4j/Swagger 注解 -->
<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-annotations-jakarta</artifactId>
</dependency>
```

**修复文件**：
- `/sd-common/pom.xml`

## 📝 父 POM 补充

为了支持 Swagger 注解，在父 `pom.xml` 中添加了版本管理：

```xml
<properties>
    <swagger.version>2.2.15</swagger.version>
</properties>

<dependencyManagement>
    <dependencies>
        <!-- Swagger 注解 -->
        <dependency>
            <groupId>io.swagger.core.v3</groupId>
            <artifactId>swagger-annotations-jakarta</artifactId>
            <version>${swagger.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**修复文件**：
- `/pom.xml`

## 🔍 验证修复

执行以下命令验证修复：

```bash
# 1. 清理项目
mvn clean

# 2. 安装依赖
mvn install

# 3. 编译项目
mvn compile

# 4. 运行项目
cd sd-admin
mvn spring-boot:run
```

## 📋 修复总结

| 错误 | 修复文件 | 添加的依赖 |
|------|---------|-----------|
| 错误 1 | application.yml | 无（配置合并） |
| 错误 2、3 | sd-admin/pom.xml | mariadb-java-client, druid-spring-boot-3-starter |
| 错误 4 | sd-admin/pom.xml | 同上 |
| 错误 5 | sd-common/pom.xml | spring-boot-starter-web |
| 错误 6 | sd-common/pom.xml | swagger-annotations-jakarta |
| 版本管理 | pom.xml | swagger.version 属性 |

## ⚠️ 注意事项

1. **MariaDB vs MySQL**
   - 默认使用 MariaDB 驱动
   - MySQL 驱动已注释备用
   - 切换数据库时注释/取消注释对应配置

2. **Druid 版本**
   - 使用 `druid-spring-boot-3-starter`（适配 Spring Boot 3）
   - 不要使用旧版本的 `druid-spring-boot-starter`

3. **Swagger 注解**
   - 使用 Jakarta 版本（`swagger-annotations-jakarta`）
   - 不要使用旧版本的 `swagger-annotations`

4. **依赖作用域**
   - `sd-common` 中的 `spring-boot-starter-web` 使用 `provided` 作用域
   - 避免打包时重复包含

## 🎯 后续建议

1. **IDEA 缓存清理**
   ```
   File -> Invalidate Caches -> Invalidate and Restart
   ```

2. **Maven 重新导入**
   ```
   右键项目 -> Maven -> Reload Project
   ```

3. **检查依赖树**
   ```bash
   mvn dependency:tree
   ```

4. **更新 IDEA 的 Maven 设置**
   - 确保使用项目指定的 Maven 版本
   - 确保使用项目指定的 JDK 17

---

**修复完成时间**：2024-01-XX  
**修复状态**：✅ 已完成
