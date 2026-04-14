#!/bin/bash

# 大学生资源信息分享平台 - 快速启动脚本

echo "========================================"
echo "大学生资源信息分享平台 - Maven 工程"
echo "========================================"
echo ""

# 检查 Java 版本
echo "检查 Java 版本..."
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "Java 版本：$java_version"

if [[ ! "$java_version" =~ "17" ]]; then
    echo "警告：请使用 JDK 17"
    exit 1
fi

# 检查 Maven 版本
echo "检查 Maven 版本..."
mvn_version=$(mvn -version 2>&1 | grep -E "Apache Maven" | awk '{print $3}')
echo "Maven 版本：$mvn_version"

echo ""
echo "========================================"
echo "开始编译项目..."
echo "========================================"

# 清理并编译
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "编译成功！"
    echo "========================================"
    echo ""
    echo "项目结构说明："
    echo ""
    echo "student-deal-share/"
    echo "├── sd-common/          # 公共模块"
    echo "├── sd-model/           # 数据模型模块"
    echo "├── sd-mapper/          # 数据访问层模块"
    echo "├── sd-service/         # 业务服务层"
    echo "│   ├── sd-service-api/     # 服务接口"
    echo "│   └── sd-service-impl/    # 服务实现"
    echo "├── sd-security/        # 安全认证模块"
    echo "├── sd-controller/      # 控制器模块"
    echo "├── sd-search/          # 搜索模块"
    echo "└── sd-admin/           # 后台管理模块 (主启动模块)"
    echo ""
    echo "========================================"
    echo "下一步操作："
    echo "========================================"
    echo ""
    echo "1. 配置数据库和 Redis (sd-admin/src/main/resources/application-dev.yml)"
    echo "2. 创建数据库：CREATE DATABASE student_deal_share DEFAULT CHARACTER SET utf8mb4;"
    echo "3. 启动应用：cd sd-admin && mvn spring-boot:run"
    echo "4. 访问接口文档：http://localhost:8080/doc.html"
    echo ""
else
    echo ""
    echo "========================================"
    echo "编译失败！请检查错误信息"
    echo "========================================"
    exit 1
fi
