# 工作流核心模块 (Core Workflow)

## 模块定位

工作流核心模块是律师事务所管理系统的核心流程引擎，基于Flowable工作流引擎进行扩展，负责实现工作流模型模块(`workflow-model`)中定义的服务接口，提供了流程定义、执行、监控和管理的完整实现。本模块处于业务层与数据层之间，为上层业务提供统一的工作流能力支持。

## 架构设计

### 整体架构

```
                    +------------------+
                    |  业务应用模块    |
                    +--------+---------+
                             |
                             v
+------------+     +--------------------+    +--------------+
|            |     |                    |    |              |
| 表单服务   +---->+   工作流核心模块   +<---+ 权限服务     |
|            |     |  (core-workflow)   |    |              |
+------------+     +----------+---------+    +--------------+
                             |
                             v
                  +--------------------+    +--------------+
                  |                    |    |              |
                  |    Flowable引擎    +<-->+ MySQL数据库  |
                  |                    |    | (流程数据)   |
                  +--------------------+    +--------------+
```

### 分层架构

```
+---------------------------------------------------+
|                    Controller层                    |
| - 流程管理API                                      |
| - 任务管理API                                      |
| - 流程模板管理API                                  |
+---------------------------------------------------+
|                    Service层                       |
| - 流程服务(ProcessServiceImpl)                     |
| - 任务服务(TaskServiceImpl)                        |
| - 权限服务(ProcessPermissionServiceImpl)           |
| - 任务分配服务(TaskAssignmentServiceImpl)          |
| - 任务通知服务(TaskNotificationServiceImpl)        |
+---------------------------------------------------+
|                   适配器层                         |
| - Flowable流程适配器(FlowableProcessAdapterImpl)   |
| - Flowable任务适配器(FlowableTaskAdapterImpl)      |
| - 数据转换器(ProcessConverter, TaskConverter)      |
+---------------------------------------------------+
|                    数据访问层                      |
| - MyBatis映射文件                                  |
| - 流程数据仓库                                     |
| - 任务数据仓库                                     |
+---------------------------------------------------+
```

## 核心组件说明

### 1. 服务实现

实现了`workflow-model`模块中定义的服务接口：

- **ProcessServiceImpl**: 实现流程管理的核心业务功能
- **TaskServiceImpl**: 实现任务管理的核心业务功能
- **ProcessPermissionServiceImpl**: 实现流程权限管理
- **TaskNotificationServiceImpl**: 实现任务通知功能
- **TaskAssignmentServiceImpl**: 实现任务分配功能

### 2. 适配器层

封装Flowable引擎API，提供更高层次的抽象：

- **FlowableProcessAdapter/Impl**: 封装流程相关的Flowable API
- **FlowableTaskAdapter/Impl**: 封装任务相关的Flowable API

### 3. 数据转换器

处理不同数据模型之间的转换：

- **ProcessConverter**: 流程实体、DTO和VO之间的转换
- **TaskConverter**: 任务实体、DTO和VO之间的转换

### 4. 配置类

- **FlowableConfig**: 配置Flowable流程引擎
- **CacheConfig**: 配置缓存机制
- **WorkflowConfig**: 工作流模块的全局配置

### 5. 控制器层

提供RESTful API：

- **ProcessController**: 流程管理API
- **TaskController**: 任务管理API
- **ProcessTemplateController**: 流程模板管理API

## 核心功能

### 1. 流程管理

- 流程定义部署
- 流程实例创建
- 流程实例控制（挂起、激活、终止）
- 流程变量管理
- 流程权限控制

### 2. 任务管理

- 任务创建与分配
- 任务查询与过滤
- 任务处理（完成、转交、委派）
- 任务通知

### 3. 流程监控

- 流程实例监控
- 任务执行监控
- 流程性能分析

## 代码目录结构

```
core-workflow/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.lawfirm.core.workflow/
│   │   │       ├── adapter/                     # Flowable适配层
│   │   │       │   ├── converter/              # 数据转换器
│   │   │       │   │   ├── ProcessConverter.java
│   │   │       │   │   └── TaskConverter.java
│   │   │       │   │
│   │   │       │   └── flowable/               # Flowable核心适配器
│   │   │       │       ├── FlowableProcessAdapter.java
│   │   │       │       ├── FlowableProcessAdapterImpl.java
│   │   │       │       ├── FlowableTaskAdapter.java
│   │   │       │       └── FlowableTaskAdapterImpl.java
│   │   │       │
│   │   │       ├── config/                      # 配置类
│   │   │       │   ├── CacheConfig.java
│   │   │       │   ├── FlowableConfig.java
│   │   │       │   └── WorkflowConfig.java
│   │   │       │
│   │   │       ├── controller/                  # API接口实现
│   │   │       │   ├── ProcessController.java
│   │   │       │   ├── ProcessTemplateController.java
│   │   │       │   └── TaskController.java
│   │   │       │
│   │   │       ├── entity/                      # 实体类
│   │   │       │
│   │   │       ├── exception/                   # 异常处理类
│   │   │       │
│   │   │       ├── listener/                    # 事件监听器
│   │   │       │
│   │   │       ├── mapper/                      # MyBatis Mapper接口
│   │   │       │
│   │   │       ├── repository/                  # 数据访问层
│   │   │       │
│   │   │       ├── service/                     # 服务实现
│   │   │       │   │   ├── ProcessServiceImpl.java
│   │   │       │       ├── TaskServiceImpl.java
│   │   │       │
│   │   │       └── vo/                         # 视图对象
│   │   │
│   │   └── resources/
│   │       ├── mapper/                         # MyBatis映射文件
│   │       │   ├── CommonProcessMapper.xml
│   │       │   ├── ProcessMapper.xml
│   │       │   └── TaskMapper.xml
│   │       │
│   │       ├── db/                            # 数据库相关配置
│   │       │
│   │       └── application.yml                 # 应用配置文件
└── pom.xml                                      # 项目依赖
```

## 与workflow-model集成

core-workflow模块作为workflow-model接口的实现层，通过以下方式与workflow-model集成：

1. **引入依赖**：在pom.xml中引入workflow-model依赖
2. **实现接口**：实现workflow-model中定义的服务接口
3. **适配数据模型**：通过转换器转换workflow-model的DTO和VO
4. **遵循接口语义**：严格按照接口定义的语义实现功能

## 开发规范

1. **命名规范**：
   - 接口实现类统一使用"接口名+Impl"形式
   - 适配器类使用"Flowable+功能+Adapter"形式
   - 转换器类使用"实体名+Converter"形式

2. **代码组织**：
   - 流程相关的服务放在service.process包下
   - 任务相关的服务放在service.task包下
   - 适配器相关的类放在adapter包下

3. **异常处理**：
   - 业务异常继承自RuntimeException
   - 所有异常需要有明确的错误码和错误消息
   - 在服务层处理异常，不在适配器层抛出

4. **日志规范**：
   - 使用SLF4J日志框架
   - 每个方法入口记录info级别日志
   - 异常情况记录error级别日志

## 重构与优化计划

### 1. 架构优化

- **进一步完善适配器模式**：隔离Flowable引擎，减少直接依赖
- **增强事件机制**：完善事件监听器，支持更灵活的业务集成
- **统一异常处理**：建立统一的异常处理机制

### 2. 功能增强

- **表单处理**：增强表单渲染和数据处理能力
- **任务分配**：优化任务分配算法，支持更复杂的分配规则
- **流程监控**：增加更多监控指标，提供性能分析功能

### 3. 性能优化

- **缓存机制**：优化缓存策略，减少数据库访问
- **批量处理**：实现批量任务处理，提高性能
- **异步处理**：将非关键操作异步化，提高响应速度

### 4. 安全增强

- **权限控制**：细化权限控制，实现更精细的访问控制
- **数据加密**：敏感数据加密存储
- **操作审计**：记录所有关键操作，便于审计

## 技术栈

- **基础框架**：Spring Boot 3.2.x
- **工作流引擎**：Flowable 6.8.x
- **数据库**：MySQL 8.0+
- **缓存**：Caffeine(本地缓存), Redis(分布式缓存)
- **持久层**：MyBatis Plus 3.5.x
- **Web层**：Spring MVC
- **消息队列**：RocketMQ 5.0.x (用于异步事件处理)
- **API文档**：SpringDoc OpenAPI 2.x
- **安全框架**：Spring Security 6.x
- **测试框架**：JUnit 5.x, Mockito, Testcontainers
- **日志框架**：SLF4J, Logback
- **存储服务**：可选集成Minio/阿里云OSS/腾讯云COS(流程相关文件存储)
