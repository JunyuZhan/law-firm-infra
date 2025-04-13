# 律师事务所管理系统 - 核心功能层

## 1. 概述

核心功能层作为系统的基础支撑，提供底层通用功能实现，不直接对外暴露REST接口。业务模块通过依赖注入方式使用这些核心功能，实现关注点分离和代码复用。

核心功能层包含以下六个核心模块：
- AI能力模块 (core-ai)
- 审计日志模块 (core-audit)
- 消息处理模块 (core-message)
- 搜索服务模块 (core-search)
- 存储服务模块 (core-storage)
- 工作流引擎模块 (core-workflow)

## 2. 核心模块说明

### 2.1 AI能力模块 (core-ai)

提供系统的智能化能力支持，包括：
- 多AI引擎支持（OpenAI、百度、本地、Dify）
- 文本处理和对话功能
- 基础的文档分析能力

主要组件：
- provider: AI服务提供者（支持多种AI引擎）
- service: AI核心服务实现
- handler: AI请求处理器
- config: AI配置管理
- utils: AI工具类

### 2.2 审计日志模块 (core-audit)

提供系统操作审计和日志记录功能：
- 操作日志记录（同步/异步）
- 数据变更审计
- 基于AOP的审计实现
- 审计日志查询

### 2.3 消息处理模块 (core-message)

系统消息处理中心，支持：
- 站内消息
- 邮件通知
- 短信通知
- 消息模板管理
- 消息发送记录

### 2.4 搜索服务模块 (core-search)

基于Lucene的搜索服务：
- 全文检索
- 多字段组合搜索
- 基础的聚合分析
- 搜索结果排序

### 2.5 存储服务模块 (core-storage)

文件存储服务，支持：
- 本地存储
- MinIO对象存储
- 阿里云OSS
- 腾讯云COS
- 基础的文件管理
- 存储桶管理

### 2.6 工作流引擎模块 (core-workflow)

基于Flowable的工作流引擎：
- 流程定义管理
- 流程实例管理
- 任务管理
- 流程表单
- 流程监控

## 3. 技术架构

### 3.1 核心依赖
- Spring Boot Starter
- Spring Cloud
- Flowable：工作流引擎
- Lucene：搜索引擎
- MinIO：对象存储
- RocketMQ：消息队列
- OpenAI SDK：AI服务

### 3.2 存储方案
- MySQL：结构化数据
- Lucene：搜索数据
- MinIO/OSS：对象存储
- Redis：缓存

## 4. 开发规范

### 4.1 代码规范
- 遵循阿里巴巴Java开发规范
- 统一的代码格式化模板
- 完整的注释文档
- 单元测试覆盖

### 4.2 接口规范
- 核心层只提供服务接口，不提供REST接口
- 服务接口需要有完整的接口文档
- 接口实现要考虑并发安全
- 异常要统一处理和转换
- 核心接口要有降级和限流措施

### 4.3 安全规范
- 数据加密传输
- 访问权限控制
- 操作日志记录
- 敏感信息脱敏

### 4.4 模块依赖规范
- 核心模块之间尽量减少依赖
- 核心模块不依赖业务模块
- 业务模块通过依赖注入使用核心功能
- 避免循环依赖 

## 5. 业务模块调用方式

核心功能层不直接对外暴露接口，业务模块通过以下方式调用：

### 5.1 添加依赖

在业务模块的`pom.xml`中添加对应核心模块依赖：

```xml
<!-- 添加工作流引擎模块依赖 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-workflow</artifactId>
</dependency>

<!-- 添加存储服务模块依赖 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-storage</artifactId>
</dependency>

<!-- 根据需要添加其他核心模块依赖 -->
```

### 5.2 注入服务接口

使用`@Autowired`配合`@Qualifier`注入核心服务接口：

```java
@Autowired
@Qualifier("coreProcessServiceImpl")
private ProcessService processService;  // 注入工作流服务

@Autowired
@Qualifier("storageFileServiceImpl")
private FileService fileService;  // 注入存储服务

@Autowired
@Qualifier("coreAuditServiceImpl")
private AuditService auditService;  // 注入审计服务

@Autowired
@Qualifier("searchServiceImpl")
private SearchService searchService;  // 注入搜索服务

@Autowired
@Qualifier("aiQAServiceImpl")
private QAService qaService;  // 注入AI问答服务
```

### 5.3 服务调用示例

#### 5.3.1 工作流服务调用

```java
// 启动流程
String processInstanceId = processService.startProcess(
    "contractApproval",
    "CONTRACT-2024-001",
    Map.of("contractType", "服务合同", "priority", "高")
);

// 查询任务
List<Task> tasks = taskService.listUserTodoTasks("lawyer1");

// 完成任务
taskService.completeTask(
    "task1",
    Map.of("approved", true, "comment", "同意")
);
```

#### 5.3.2 存储服务调用

```java
// 上传文件
FileVO file = fileService.uploadFile(bucketId, multipartFile);

// 获取文件访问URL
String fileUrl = fileService.generateFileUrl(fileId, 3600);  // 有效期1小时

// 创建存储桶
StorageBucket bucket = new StorageBucket();
bucket.setBucketName("contracts");
bucket.setStorageType(StorageTypeEnum.MINIO);
Long bucketId = bucketService.createBucket(bucket);
```

## 6. 安全与合规

### 6.1 数据安全
- 敏感信息过滤
- 审计日志加密存储
- 访问权限控制

### 6.2 性能考虑
- 异步日志记录
- 日志批量处理
- 合理的日志保留策略

### 6.3 合规保障
- 满足监管合规要求
- 支持多种审计规范
- 审计日志防篡改

## 7. 更新日志
- 2024-03-18: 初始版本发布
- 2024-03-19: 完成案件管理模块实现
- 2024-03-20: 完成认证授权模块实现
- 2024-03-21: 完成人事管理和组织架构模块实现
- 2024-03-22: 完成客户和合同模块实现
- 2024-03-23: 完成文档和财务模块实现
- 2024-03-24: 完成工作流和存储模块实现
- 2024-03-25: 完成搜索和消息模块实现
- 2024-03-26: 完成知识和AI模块实现
- 2024-04-05: 完成调度模块实现
- 2024-04-07: 完成任务模块实现