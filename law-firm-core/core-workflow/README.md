# 工作流模块(core-workflow)

## 简介
工作流模块基于Flowable实现,提供了统一的工作流管理功能,包括流程定义管理、流程实例管理、任务管理、表单管理等功能。

## 主要功能

### 1. 流程定义管理
- 部署流程定义
- 删除流程定义
- 查询流程定义
- 获取流程图
- 获取流程XML

### 2. 流程实例管理
- 启动流程实例
- 挂起/激活流程实例
- 终止流程实例
- 查询流程实例
- 获取流程变量

### 3. 任务管理
- 查询任务
- 签收任务
- 完成任务
- 委托任务
- 转办任务
- 设置任务变量
- 获取任务表单

### 4. 表单管理
- 创建表单
- 更新表单
- 删除表单
- 查询表单
- 提交表单

### 5. 流程监控
- 流程变量监听
- 流程活动监听
- 流程完成监听
- 任务超时监听
- 任务分配监听

### 6. 异常处理
- 全局异常处理
- 自定义业务异常
- 异常信息存储
- 重试机制

### 7. 告警通知
- 邮件通知
- 系统告警
- 业务告警

## 配置说明

### 1. Maven依赖
```xml
<dependency>
    <groupId>com.lawfirm.core</groupId>
    <artifactId>core-workflow</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置文件(application.yml)
```yaml
workflow:
  process:
    definition-location: processes/  # 流程定义文件位置
    auto-deployment: true           # 是否自动部署
    check-version: true            # 是否检查版本
    history-retention-days: 0      # 历史数据保留天数(0表示永久保留)
  
  task:
    timeout: 72                    # 任务超时时间(小时)
    reminder: 24                   # 任务提醒时间(小时)
    auto-complete: false          # 是否自动完成
    auto-claim: false             # 是否自动签收
    lock-time: 10                 # 任务锁定时间(分钟)

spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=3600s
```

## 使用示例

### 1. 启动流程
```java
@Autowired
private WorkflowService workflowService;

// 启动流程
String processInstanceId = workflowService.startProcess(
    "leave-process",           // 流程定义key
    "LEAVE-2024-001",         // 业务key
    variables,                 // 流程变量
    "zhangsan"                // 启动用户
);
```

### 2. 查询任务
```java
@Autowired
private TaskService taskService;

// 查询待办任务
List<Task> tasks = taskService.listTasks(
    null,                     // 流程实例ID
    "approve",                // 任务定义key
    "zhangsan",              // 办理人
    null,                     // 所有者
    "default"                 // 租户ID
);
```

### 3. 完成任务
```java
@Autowired
private TaskService taskService;

// 完成任务
taskService.completeTask(
    "1001",                   // 任务ID
    variables,                // 任务变量
    "同意"                    // 审批意见
);
```

### 4. 获取流程图
```java
@Autowired
private DiagramService diagramService;

// 获取流程实例图(带高亮)
InputStream diagram = diagramService.getProcessInstanceDiagram("1001");
```

## 异常处理
模块提供了统一的异常处理机制:

```java
try {
    workflowService.startProcess(...);
} catch (WorkflowException e) {
    // 处理业务异常
    log.error("业务异常: {}", e.getMessage());
} catch (Exception e) {
    // 处理其他异常
    log.error("系统异常", e);
}
```

## 监听器使用
可以通过继承基类或实现接口来自定义监听器:

```java
@Component
public class CustomTaskListener extends BaseTaskListener {
    
    @Override
    protected void onCreate(DelegateTask delegateTask) {
        // 实现任务创建时的逻辑
    }
}
```

## 缓存配置
模块使用Caffeine实现缓存:

```java
@Cacheable(value = "processDefinitions", key = "#processDefinitionId")
public ProcessDefinition getProcessDefinition(String processDefinitionId) {
    // 实现获取流程定义的逻辑
}
```

## 告警使用
模块提供了多种告警方式:

```java
@Autowired
private AlertUtil alertUtil;

// 发送邮件告警
alertUtil.sendAlertEmail(
    "任务超时提醒",
    "您有一个任务已超时,请尽快处理",
    "zhangsan@company.com"
);

// 发送系统告警
alertUtil.sendSystemAlert(
    "流程异常",
    "流程实例[1001]执行失败",
    "ERROR"
);
```

## 注意事项
1. 确保MongoDB和Redis服务可用
2. 配置邮件服务器信息
3. 合理配置缓存参数
4. 定期清理历史数据
5. 正确处理事务

## 常见问题
1. Q: 如何自定义流程图样式?
   A: 可以通过修改processes目录下的bpmn文件来自定义样式

2. Q: 如何处理并发签收问题?
   A: 使用乐观锁或任务锁定机制

3. Q: 如何实现自定义表单?
   A: 可以继承Form类并实现自定义字段

4. Q: 如何优化查询性能?
   A: 合理使用缓存,创建必要的索引

## 更新日志
### v1.0.0 (2024-01-20)
- 初始版本发布
- 实现基本的工作流功能
- 添加异常处理机制
- 添加缓存支持
- 添加告警通知 