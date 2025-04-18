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
- schedule-model: 日程管理

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
- law-firm-archive: 档案管理
- law-firm-analysis: 数据分析

### 6. API层 (law-firm-api)
- 统一的API接口定义
- 接口文档管理
- 接口版本控制
- 接口安全控制

## 架构依赖关系

### 整体架构图

```
+---------------------------+
|        API层              |
|   (law-firm-api)          |
+------------+--------------+
             |
             v
+------------+--------------+
|      业务模块层            |
|   (law-firm-modules)      |
+------------+--------------+
      |             |
      v             v
+------------+  +------------+
|  核心功能层  |  |  数据模型层  |
| (law-firm- |  | (law-firm- |
|   core)    |  |   model)   |
+------------+  +------------+
      |             |
      v             v
+---------------------------+
|       通用模块层           |
|   (law-firm-common)       |
+------------+--------------+
             |
             v
+---------------------------+
|       依赖管理层           |
| (law-firm-dependencies)   |
+---------------------------+
```

### 各层次依赖关系详解

#### 1. 依赖管理层 (law-firm-dependencies)

- **职责**：统一管理所有第三方依赖的版本号
- **特点**：
  - 使用Maven BOM (Bill of Materials)方式管理依赖
  - 所有第三方库的版本号集中定义在properties中
  - 不包含实际代码，仅作为依赖版本控制
- **依赖关系**：
  - 被其他所有层引用，但自身不依赖任何项目内部模块
  - 通过`<scope>import</scope>`方式被引入其他模块

#### 2. 通用模块层 (law-firm-common)

- **职责**：提供基础设施和通用功能支持
- **依赖关系**：
  - 向下依赖：law-firm-dependencies
  - 向上被依赖：被model、core、modules和api层依赖
- **模块间关系**：
  - common-core作为基础模块，被其他common模块依赖
  - common-web依赖common-core
  - common-security依赖common-core和common-web
  - 其他模块根据功能需要相互依赖

#### 3. 数据模型层 (law-firm-model)

- **职责**：定义领域模型和数据传输对象
- **依赖关系**：
  - 向下依赖：law-firm-dependencies、law-firm-common (尤其是common-core)
  - 向上被依赖：被core层、modules层和api层依赖
- **模块间关系**：
  - base-model为基础模型，被其他model模块依赖
  - 其他模型模块可能相互依赖，如auth-model可能依赖organization-model
  - 模型层只包含数据结构定义，不包含业务逻辑

#### 4. 核心功能层 (law-firm-core)

- **职责**：实现跨业务模块的核心功能
- **依赖关系**：
  - 向下依赖：law-firm-dependencies、law-firm-common、law-firm-model
  - 向上被依赖：被modules层和api层依赖
- **模块间关系**：
  - 核心功能模块之间通常是相对独立的
  - 某些功能模块可能相互依赖，如core-message可能依赖core-storage

#### 5. 业务模块层 (law-firm-modules)

- **职责**：实现具体业务逻辑
- **依赖关系**：
  - 向下依赖：law-firm-dependencies、law-firm-common、law-firm-model、law-firm-core
  - 向上被依赖：被api层依赖
- **模块间关系**：
  - 业务模块可能存在依赖关系，如law-firm-case依赖law-firm-client
  - 基础业务模块（如auth、system）被其他业务模块依赖
  - 尽量减少模块间循环依赖

#### 6. API层 (law-firm-api)

- **职责**：提供统一的对外接口
- **依赖关系**：
  - 向下依赖：依赖所有其他层
  - 是系统的最顶层，不被其他模块依赖
- **特点**：
  - 集成所有业务模块
  - 提供统一的REST API接口
  - 处理权限验证、接口文档、异常处理等跨切面功能

### 版本管理策略

本项目采用统一的版本管理策略：

- 所有内部模块使用`${revision}`变量统一管理版本
- 所有第三方依赖版本在law-firm-dependencies中集中定义
- 严格控制依赖冲突，确保依赖版本一致性

### 数据流向

1. 请求从API层进入系统
2. API层将请求委托给对应的业务模块处理
3. 业务模块调用核心功能层和数据模型层完成业务逻辑
4. 数据持久化操作通过common层的数据访问能力完成

## 技术栈

### 1. 基础环境
- JDK 21
- Maven 3.8+
- Git

### 2. 核心框架
- Spring Boot 3.2.x
- Spring Security 6.x
- MyBatis Plus 3.5.x

### 3. 中间件
- MySQL 8.0+
- Redis 6.x
- RocketMQ 5.x
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

#### 开发环境启动

```bash
# 启动开发环境（包含MySQL、Redis、MinIO等基础设施）
./start-docker.sh

# 启动完成后，可以通过以下地址访问服务：
# API服务：http://localhost:8080
# MinIO控制台：http://localhost:9000
# Redis：localhost:6379
# MySQL：localhost:3306
```

#### 生产环境部署

```bash
# 1. 进入API层目录
cd law-firm-api

# 2. 设置环境变量
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=实际数据库地址
export DB_USERNAME=实际数据库用户名
export DB_PASSWORD=实际数据库密码
export REDIS_HOST=实际Redis地址
export MINIO_ENDPOINT=实际MinIO地址

# 3. 启动应用
./start.sh
```

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

## 案件-合同管理集成

### 1. 功能概述
- 案件与合同的关联管理
- 合同状态跟踪
- 合同审批流程
- 合同文档管理
- 合同提醒功能

### 2. 核心组件
- `CaseContractService`: 案件合同服务接口
- `CaseContractServiceImpl`: 案件合同服务实现
- `ContractService`: 合同服务接口
- `ContractServiceImpl`: 合同服务实现
- `CaseContractRelation`: 案件合同关联实体

### 3. 数据模型
- 案件合同关联表
- 合同基本信息表
- 合同状态表
- 合同文档表
- 合同提醒表

### 4. 业务流程
1. 案件创建时关联合同
2. 合同审批流程
3. 合同状态更新
4. 合同文档管理
5. 合同提醒设置

### 5. 接口规范
- RESTful API设计
- 统一的响应格式
- 完善的错误处理
- 详细的接口文档

### 6. 安全控制
- 权限验证
- 数据加密
- 操作审计
- 敏感信息保护

## 文档链接

- [系统使用教程](docs/user-guide.md) - 详细的系统使用说明
- [模块文档](docs/modules.md) - 模块结构和依赖关系