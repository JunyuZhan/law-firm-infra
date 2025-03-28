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

基于Elasticsearch的搜索服务：
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
- Elasticsearch：搜索引擎
- MinIO：对象存储
- RocketMQ：消息队列
- OpenAI SDK：AI服务

### 3.2 存储方案
- MySQL：结构化数据
- Elasticsearch：搜索数据
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

#### 5.3.3 审计服务调用

```java
// 记录审计日志
auditService.recordLog("合同管理", "删除合同", "删除合同ID: " + contractId);

// 异步记录审计日志
auditService.recordLogAsync("批量操作", "导入客户", "导入客户数量: " + customers.size());

// 查询审计日志
Page<AuditLogVO> logs = auditQueryService.queryAuditLogs(
    AuditLogQueryDTO.builder()
        .module("合同管理")
        .startTime(startTime)
        .endTime(endTime)
        .pageNum(1)
        .pageSize(10)
        .build()
);
```

#### 5.3.4 搜索服务调用

```java
// 基础搜索
SearchResult<DocVO> result = searchService.search(
    "合同违约",     // 关键词
    "contract",    // 搜索范围
    PageRequest.of(0, 10)  // 分页
);

// 索引文档
indexService.indexDocument(document);
```

#### 5.3.5 消息服务调用

```java
// 发送系统消息
messageSender.sendSystemMessage(
    "任务提醒", 
    "您有新的合同审核任务需要处理", 
    Arrays.asList("user1", "user2")
);

// 发送邮件
messageSender.sendTemplateEmail(
    "case_assignment",  // 模板代码
    templateParams,     // 模板参数
    "案件分配通知",      // 邮件主题
    Collections.singletonList("lawyer@example.com")
);
```

### 5.4 业务模块封装最佳实践

业务模块应该对核心服务进行二次封装，以适应具体业务场景：

```java
@Service
@RequiredArgsConstructor
public class ContractWorkflowService {
    
    @Autowired
    @Qualifier("coreProcessServiceImpl")
    private ProcessService processService;
    
    /**
     * 启动合同审批流程
     */
    public String startContractApprovalProcess(Contract contract) {
        // 构建流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("contractId", contract.getId());
        variables.put("contractType", contract.getType());
        variables.put("amount", contract.getAmount());
        variables.put("initiator", SecurityUtils.getCurrentUsername());
        
        // 调用核心层服务启动流程
        return processService.startProcess(
            "contract_approval_process",
            "CONTRACT-" + contract.getId(),
            variables
        );
    }
}
```

通过这种方式，业务模块可以使用核心层提供的底层功能，同时增加业务特定的逻辑，实现关注点分离和代码复用。 

### 5.5 配置管理规范
- 核心模块不直接管理配置文件
- 配置项由业务层提供
- 核心模块应设计接收配置的接口
- 核心模块不应硬编码配置值
- 敏感配置（如API密钥）由业务层负责安全存储和管理

### 5.6 数据存储规范
- 核心模块不直接创建或管理数据库表
- 数据存储相关表结构由业务模块定义
- 核心模块应通过接口获取业务层提供的数据存储服务
- 核心模块不应包含数据库迁移脚本
- 业务层负责定义数据库表之间的关联关系

## 6. 核心层重构建议

针对当前核心层中存在的直接管理数据库和配置的问题，提出以下重构建议：

### 6.1 清理数据库脚本
- 将核心层中的数据库迁移脚本（如`V1.0.1__init_workflow_tables.sql`）移至相应的业务模块
- 移除核心层中的数据库表创建逻辑
- 调整核心层服务实现，通过接口获取业务层提供的数据访问服务

### 6.2 调整配置管理
- 移除核心层中的具体配置文件（如`application-workflow.yml`）
- 设计配置接口，供业务层注入配置
- 使用条件装配方式适应不同业务场景的配置

### 6.3 重新设计存储抽象
- 将存储策略与存储结构分离
- 业务层负责提供数据存储实现
- 核心层仅处理功能逻辑，不涉及存储细节

### 6.4 统一异常处理
- 定义核心层统一的异常体系
- 屏蔽底层技术实现的异常
- 提供清晰的错误信息和状态码

## 7. 业务模块调用方式