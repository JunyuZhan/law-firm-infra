# 律师事务所管理系统

## 项目简介

律师事务所管理系统是一套完整的律所业务管理解决方案，采用DDD驱动，分层架构设计，提供案件管理、客户管理、合同管理、文档管理、财务管理等全方位的功能支持。系统具有高度的可扩展性和可维护性。

## 系统架构

### 1. 依赖管理层 (law-firm-dependencies)
- 统一的第三方依赖版本管理
- Maven BOM支持
- 构建配置管理

### 2. 通用模块层 (law-firm-common)
- common-core: 核心功能
- common-util: 工具类库
- common-web: Web支持
- common-data: 数据访问
- common-cache: 缓存处理
- common-security: 安全功能
- common-log: 日志处理
- common-message: 消息处理
- common-test: 测试支持

### 3. 数据模型层 (law-firm-model)
- base-model: 基础模型
- auth-model: 认证授权
- system-model: 系统管理
- client-model: 客户管理
- case-model: 案件管理
- contract-model: 合同管理
- document-model: 文档管理
- finance-model: 财务管理
- workflow-model: 工作流
- knowledge-model: 知识库
- message-model: 消息
- storage-model: 存储
- search-model: 搜索
- ai-model: AI能力
- log-model: 日志
- organization-model: 组织架构
- personnel-model: 人事管理

### 4. 核心功能层 (law-firm-core)
- core-ai: AI能力
- core-audit: 审计日志
- core-message: 消息处理
- core-search: 搜索服务
- core-storage: 存储服务
- core-workflow: 工作流引擎

### 5. 业务模块层 (law-firm-modules)
- law-firm-auth: 认证授权
- law-firm-system: 系统管理
- law-firm-client: 客户管理
- law-firm-case: 案件管理
- law-firm-contract: 合同管理
- law-firm-document: 文档管理
- law-firm-finance: 财务管理
- law-firm-knowledge: 知识库
- law-firm-personnel: 人事管理
- law-firm-schedule: 日程管理
- law-firm-task: 任务管理
- law-firm-supplies: 办公用品
- law-firm-asset: 资产管理
- law-firm-seal: 印章管理
- law-firm-conflict: 利益冲突
- law-firm-archive: 档案管理
- law-firm-analysis: 数据分析

### 6. API层 (law-firm-api)
- 统一的API接口定义
- 接口文档管理
- 接口版本控制
- 接口安全控制

## 技术栈

### 1. 基础环境
- JDK 21
- Maven 3.8+
- Git

### 2. 核心框架
- Spring Boot 3.2.x
- Spring Cloud 2023.x
- Spring Security 6.x
- MyBatis Plus 3.5.x

### 3. 中间件
- MySQL 8.0+
- Redis 6.x
- RocketMQ 5.x
- Elasticsearch 8.12+
- MinIO

### 4. 开发工具
- Lombok
- MapStruct
- Hutool
- Knife4j
- SpringDoc OpenAPI

## 开发环境

### 1. 环境要求
```yaml
development:
  # 应用服务器
  server:
    cpu: 4核+
    memory: 16GB+
    disk: 100GB+
    
  # 开发工具
  tools:
    ide: IntelliJ IDEA 2023.3+
    jdk: Oracle JDK 21+
    maven: 3.8+
    git: 2.x+
```

### 2. 快速开始

#### 环境要求

- JDK 21
- Maven 3.9.x
- MySQL 8.0
- Redis 7.0
- Elasticsearch 7.17.x
- RocketMQ 5.1.x
- MinIO

#### 本地开发

1. 克隆代码

```bash
git clone https://github.com/your-org/law-firm-infra.git
cd law-firm-infra
```

2. 编译打包

```bash
mvn clean install -DskipTests
```

3. 启动服务

```bash
cd law-firm-api
mvn spring-boot:run
```

#### Docker部署

本项目支持使用Docker进行部署，提供了完整的Docker配置。

#### 前提条件

- 安装 [Docker](https://www.docker.com/get-started)
- 安装 [Docker Compose](https://docs.docker.com/compose/install/)

#### 使用Docker Compose启动

Windows环境:

```bash
scripts/docker-build.bat
```
-jar target/law-firm-api-1.0.0.jar
java "-Dspring.profiles.active=dev-noredis" 
Linux/Mac环境:

```bash
chmod +x scripts/docker-build.sh
./scripts/docker-build.sh
```

或者手动执行:

```bash
# 构建镜像
docker-compose build

# 启动服务
docker-compose up -d
```

#### 访问地址

- 律所管理系统: http://localhost:8080
- Swagger API文档: http://localhost:8080/swagger-ui.html
- MinIO控制台: http://localhost:9001 (默认用户名/密码: minioadmin/minioadmin)

### 服务启动

## 项目规范

### 1. 开发规范
- 遵循阿里巴巴Java开发规范
- 统一的代码格式化模板
- 完整的注释文档
- 单元测试覆盖
- 代码审查机制

### 2. 版本规范
- 遵循语义化版本规范
- 统一的依赖版本管理
- 完整的更新日志
- 版本发布流程
- 兼容性保证

### 3. 文档规范
- 完整的接口文档
- 详细的设计文档
- 使用手册
- 部署文档
- 常见问题

## 版本说明

当前版本：1.0.0
发布日期：2025-03-04

### 主要特性
- 完整的业务功能支持
- 微服务架构设计
- 统一的接口规范
- 完善的技术文档
- 高度的可扩展性

### 注意事项
1. 遵循开发规范
2. 重视代码质量
3. 注意性能优化
4. 保证数据安全
5. 做好技术文档

## 贡献指南

详见 [CONTRIBUTING.md](CONTRIBUTING.md)

## 更新日志

详见 [CHANGELOG.md](CHANGELOG.md)

## 许可证

本项目采用 [MIT](LICENSE) 许可证 