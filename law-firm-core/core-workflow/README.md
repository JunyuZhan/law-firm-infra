# 工作流核心模块 (core-workflow)

## 模块简介
core-workflow 是律所管理系统的工作流核心模块，基于 Flowable 工作流引擎实现，提供了完整的工作流管理功能。该模块主要用于处理律所内部的各类业务流程，如案件流转、文档审批等。

## 主要功能

### 1. 流程模板管理
- 流程模板的部署与更新
- 流程模板的版本控制
- 流程模板的查询与预览
- 支持BPMN2.0标准的流程定义

### 2. 任务管理
- 任务的创建与分配
- 任务状态的跟踪与更新
- 任务委派与转办
- 任务候选人/组管理
- 历史任务查询

### 3. 业务流程管理
- 业务流程的创建与启动
- 流程实例的状态管理
- 流程变量的存储与获取
- 流程与业务数据的关联

### 4. 权限控制
- 流程节点权限管理
- 任务访问权限控制
- 多租户支持

## 技术栈
- Spring Boot
- Flowable
- Spring Data JPA
- MySQL
- MongoDB (用于存储流程相关文档)
- Caffeine (本地缓存)

## 核心类说明

### 模型类
- `Task`: 任务基础模型
- `HistoricTask`: 历史任务模型
- `BusinessProcess`: 业务流程模型
- `TaskNodePermission`: 任务节点权限模型

### 服务接口
- `TaskService`: 任务相关操作
- `BusinessProcessService`: 业务流程管理
- `ProcessTemplateService`: 流程模板管理
- `ProcessPermissionService`: 流程权限管理

## 使用示例

### 1. 部署流程模板
```java
@Autowired
private ProcessTemplateService processTemplateService;

// 部署流程模板
String deploymentId = processTemplateService.deployTemplate(
    ProcessTemplateEnum.CASE_CREATE, 
    processFile
);
```

### 2. 启动业务流程
```java
@Autowired
private BusinessProcessService businessProcessService;

// 创建业务流程
BusinessProcess process = new BusinessProcess()
    .setBusinessType("case")
    .setBusinessId("CASE-2024-001")
    .setBusinessTitle("测试案件")
    .setStartUserId("user1");
    
businessProcessService.createBusinessProcess(process);
```

### 3. 任务处理
```java
@Autowired
private TaskService taskService;

// 查询任务
Task task = taskService.getTask(taskId);

// 完成任务
Map<String, Object> variables = new HashMap<>();
variables.put("approved", true);
taskService.completeTask(taskId, variables);
```

## 配置说明

### 数据库配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/law_firm_workflow
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Flowable配置
```yaml
flowable:
  database-schema-update: true
  async-executor-activate: true
  process:
    definition-cache-limit: 1
```

## 注意事项
1. 确保数据库中已创建相应的工作流表
2. 流程模板需符合BPMN2.0规范
3. 建议在生产环境中配置适当的缓存策略
4. 注意处理并发任务时的锁定机制

## 开发计划
- [ ] 添加流程监控功能
- [ ] 优化任务分配算法
- [ ] 增加流程统计分析
- [ ] 支持动态表单配置 