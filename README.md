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
java -jar target/law-firm-api-1.0.0.jar

java "-Dspring.profiles.active=dev-noredis" -jar target/law-firm-api-1.0.0.jar

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

## 模块文档

详细的模块依赖关系请参阅 [模块文档](docs/modules.md)。


## 模块文档

详细的模块依赖关系请参阅 [模块文档](docs/modules.md)。


## 模块文档

详细的模块依赖关系请参阅 [模块文档](docs/modules.md)。


## 文档链接

- [系统使用教程](docs/user-guide.md) - 详细的系统使用说明
- [模块文档](docs/modules.md) - 模块结构和依赖关系

# 重构服务实现类的修复总结（更新）

## 修复问题概述

在对法律事务管理系统的日程模块进行重构过程中，我们发现并修复了以下问题：

1. **Service实现类继承关系错误**：
   - 原先某些Service实现类错误地从`BaseServiceImpl`继承，导致类型不匹配问题
   - 已经将相关实现类修改回正确的`ServiceImpl`继承，同时手动实现`BaseService`接口方法

2. **方法签名问题**：
   - 解决了实体类getter/setter方法不匹配的问题
   - 修复了`ScheduleEvent`、`ScheduleReminder`等实体类的字段访问方法

3. **类型转换错误**：
   - 解决了泛型类型转换问题，特别是`Page<>`类型的转换
   - 修复了枚举类型与整数类型之间的转换

## 具体修复方法

1. **Services实现类修复**：
   - `ScheduleReminderServiceImpl` - 从`BaseServiceImpl`改为继承`ServiceImpl`
   - `ScheduleEventServiceImpl` - 添加缺失的`checkConflict`方法实现，修复类型问题
   - `ScheduleCalendarServiceImpl` - 添加`unshareCalendar`方法，实现所有必要的接口方法

2. **Mapper接口创建**：
   - 创建`ScheduleCalendarMapper`接口，包含基本的CRUD方法和自定义查询方法

3. **实体类字段补充**：
   - 为`ScheduleEvent`实体类添加`startTime`和`endTime`字段
   - 为统计方法添加枚举类型处理，解决类型转换问题

4. **枚举值处理**：
   - 修复`countByProperty`方法，处理ScheduleType和ScheduleStatus枚举到Integer的转换

## 已修复问题

我们已经成功修复了以下问题：

1. 创建了缺失的 `ScheduleCalendarMapper` 接口
2. 为 `ScheduleEvent` 实体类添加了 `startTime` 和 `endTime` 字段
3. 修复了 `ScheduleEventDTO` 中添加缺失的 `startTime` 和 `endTime` 字段
4. 为 `MeetingRoomBookingDTO` 添加了 `creatorId` 字段
5. 为 `MeetingRoomDTO` 添加了 `facilities` 字段
6. 解决了 `ScheduleCalendarServiceImpl` 中的枚举类型转换问题
7. 修复了 `ScheduleEventServiceImpl` 中的 `checkConflict` 方法
8. 修复了 `reminderService.getPendingReminders` 方法的参数不匹配问题
9. 在 `MeetingRoomConvert` 中添加了 `List<String>` 和 `String` 之间的类型转换方法
10. 修复了 `MeetingRoomController` 中 `IPage` 和 `Page` 类型不匹配问题
11. 优化了 `MeetingRoomController` 使用 `MeetingRoomConvert` 进行DTO和实体转换
12. 在 `MeetingRoomVO` 中添加了缺失的 `facilities` 字段
13. 创建了 `TimeConverter` 工具类以处理 `LocalDateTime` 与 `long` 之间的转换

## 仍需修复的问题

以下问题需要进一步处理：

1. 其他控制器中的 `IPage` 和 `Page` 类型转换问题：
   - 需要检查其他Controller类中对Service返回的IPage类型处理

2. 服务接口方法缺失问题：
   - 一些Service接口缺少必要的方法，导致实现类中的方法无法正确覆盖接口
   - 需要检查每个Service接口和实现类的方法签名

3. 现有代码需使用 `TimeConverter` 工具类：
   - 在需要在 `LocalDateTime` 和 `long` 之间转换的地方使用新增的工具类方法
   - 修复现有代码中的类型转换错误

## 下一步计划

1. 完成编译并进行完整测试
2. 修复其他Controller层对Service层方法的调用问题
3. 修复剩余的`IPage`和`Page`类型不匹配的问题
4. 补充缺失的接口方法定义
5. 使用`TimeConverter`工具类解决日期时间类型转换问题

# 修复进度更新（2023-04-13）

## 已修复问题

我们已经成功修复了以下问题：

1. 创建了缺失的 `ScheduleCalendarMapper` 接口
2. 为 `ScheduleEvent` 实体类添加了 `startTime` 和 `endTime` 字段
3. 修复了 `ScheduleEventDTO` 中添加缺失的 `startTime` 和 `endTime` 字段
4. 为 `MeetingRoomBookingDTO` 添加了 `creatorId` 字段
5. 为 `MeetingRoomDTO` 添加了 `facilities` 字段
6. 解决了 `ScheduleCalendarServiceImpl` 中的枚举类型转换问题
7. 修复了 `ScheduleEventServiceImpl` 中的 `checkConflict` 方法
8. 修复了 `reminderService.getPendingReminders` 方法的参数不匹配问题
9. 在 `MeetingRoomConvert` 中添加了 `List<String>` 和 `String` 之间的类型转换方法
10. 修复了 `MeetingRoomController` 中 `IPage` 和 `Page` 类型不匹配问题
11. 优化了 `MeetingRoomController` 使用 `MeetingRoomConvert` 进行DTO和实体转换
12. 在 `MeetingRoomVO` 中添加了缺失的 `facilities` 字段
13. 创建了 `TimeConverter` 工具类以处理 `LocalDateTime` 与 `long` 之间的转换

## 仍需修复的问题

以下问题需要进一步处理：

1. 其他控制器中的 `IPage` 和 `Page` 类型转换问题：
   - 需要检查其他Controller类中对Service返回的IPage类型处理

2. 服务接口方法缺失问题：
   - 一些Service接口缺少必要的方法，导致实现类中的方法无法正确覆盖接口
   - 需要检查每个Service接口和实现类的方法签名

3. 现有代码需使用 `TimeConverter` 工具类：
   - 在需要在 `LocalDateTime` 和 `long` 之间转换的地方使用新增的工具类方法
   - 修复现有代码中的类型转换错误

## 下一步计划

1. 完成编译并进行完整测试
2. 修复其他Controller层对Service层方法的调用问题
3. 修复剩余的`IPage`和`Page`类型不匹配的问题
4. 补充缺失的接口方法定义
5. 使用`TimeConverter`工具类解决日期时间类型转换问题
