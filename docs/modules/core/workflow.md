# Core Workflow Module

## 1. 模块介绍

`core-workflow` 是律所管理系统的核心工作流模块，提供统一的工作流引擎服务。该模块基于Activiti实现，支持自定义工作流定义、流程执行、任务管理等功能。

## 2. 核心功能

### 2.1 流程定义
- 流程设计
- 流程部署
- 流程版本
- 流程模板
- 流程变量

### 2.2 流程执行
- 流程启动
- 流程暂停
- 流程恢复
- 流程终止
- 流程回退

### 2.3 任务管理
- 任务分配
- 任务委托
- 任务转交
- 任务提醒
- 任务超时

### 2.4 流程监控
- 流程追踪
- 流程统计
- 性能监控
- 异常告警
- 历史记录

## 3. 技术架构

### 3.1 核心组件
```
com.lawfirm.core.workflow/
├── config/                 # 配置类
│   ├── WorkflowProperties # 工作流配置属性
│   └── ActivitiConfig    # Activiti配置
├── service/               # 服务层
│   ├── ProcessService    # 流程服务
│   ├── TaskService      # 任务服务
│   └── HistoryService   # 历史服务
├── model/                # 数据模型
│   ├── entity/          # 实体类
│   ├── dto/             # 传输对象
│   └── enums/           # 枚举类型
└── listener/            # 流程监听器
```

### 3.2 数据库设计
```sql
-- 流程定义表
workflow_process
  - id            # 主键
  - code          # 流程编码
  - name          # 流程名称
  - version       # 流程版本
  - content       # 流程定义
  - status        # 流程状态
  - deploy_time   # 部署时间

-- 流程实例表
workflow_instance
  - id            # 主键
  - process_id    # 流程定义ID
  - business_key  # 业务标识
  - variables     # 流程变量
  - start_time    # 开始时间
  - end_time      # 结束时间
  - status        # 实例状态

-- 任务记录表
workflow_task
  - id            # 主键
  - instance_id   # 实例ID
  - name          # 任务名称
  - assignee      # 处理人
  - create_time   # 创建时间
  - claim_time    # 认领时间
  - complete_time # 完成时间
  - status        # 任务状态
```

## 4. 接口设计

### 4.1 流程服务接口
```java
public interface ProcessService {
    void deployProcess(String processKey, String bpmnXml);
    void startProcess(String processKey, String businessKey, Map<String, Object> variables);
    void suspendProcess(String processInstanceId);
    void resumeProcess(String processInstanceId);
    void terminateProcess(String processInstanceId);
}
```

### 4.2 任务服务接口
```java
public interface TaskService {
    void claimTask(String taskId, String userId);
    void completeTask(String taskId, Map<String, Object> variables);
    void delegateTask(String taskId, String userId);
    void transferTask(String taskId, String userId);
    List<Task> getUserTasks(String userId);
}
```

### 4.3 历史服务接口
```java
public interface HistoryService {
    ProcessHistory getProcessHistory(String processInstanceId);
    List<TaskHistory> getTaskHistory(String processInstanceId);
    List<VariableHistory> getVariableHistory(String processInstanceId);
    ProcessStatistics getProcessStatistics(String processKey);
}
```

## 5. 配置说明

### 5.1 Activiti配置
```yaml
spring:
  activiti:
    database-schema-update: true
    history-level: full
    db-history-used: true
    check-process-definitions: true
    process-definition-location-prefix: classpath:/processes/
```

### 5.2 工作流配置
```yaml
workflow:
  task:
    timeout: 72h
    reminder: 24h
  process:
    auto-deploy: true
    version-strategy: increment
```

## 6. 使用示例

### 6.1 部署流程
```java
@Autowired
private ProcessService processService;

public void deployContractProcess() {
    String bpmnXml = loadBpmnXml("contract-approval.bpmn20.xml");
    processService.deployProcess("contract-approval", bpmnXml);
}
```

### 6.2 启动流程
```java
public void startContractApproval() {
    Map<String, Object> variables = new HashMap<>();
    variables.put("contractId", "CT001");
    variables.put("applicant", "张三");
    
    processService.startProcess(
        "contract-approval", 
        "CT001", 
        variables
    );
}
```

### 6.3 处理任务
```java
@Autowired
private TaskService taskService;

public void handleTask() {
    String taskId = "task001";
    Map<String, Object> variables = new HashMap<>();
    variables.put("approved", true);
    variables.put("comment", "同意");
    
    taskService.completeTask(taskId, variables);
}
```

## 7. 测试说明

### 7.1 单元测试
```java
@Test
void deployProcess_Success() {
    String bpmnXml = loadTestBpmn();
    processService.deployProcess("test-process", bpmnXml);
    
    ProcessDefinition definition = repositoryService
        .createProcessDefinitionQuery()
        .processDefinitionKey("test-process")
        .singleResult();
        
    assertNotNull(definition);
}
```

### 7.2 集成测试
```java
@SpringBootTest
class WorkflowIntegrationTest {
    @Autowired
    private ProcessService processService;
    
    @Test
    void testCompleteWorkflow() {
        // 完整工作流测试
    }
}
```

## 8. 性能优化

### 8.1 流程优化
- 合理设计流程
- 减少流程变量
- 优化流程分支
- 避免死循环

### 8.2 数据优化
- 定期清理历史
- 分表分库
- 索引优化
- 缓存使用

### 8.3 执行优化
- 异步执行
- 批量处理
- 任务池化
- 超时控制

## 9. 扩展性设计

### 9.1 流程监听器
```java
public interface ProcessEventListener {
    void onProcessStart(ProcessStartEvent event);
    void onProcessEnd(ProcessEndEvent event);
    void onTaskCreate(TaskCreateEvent event);
    void onTaskComplete(TaskCompleteEvent event);
}
```

### 9.2 任务处理器
```java
public interface TaskHandler {
    boolean support(String taskDefinitionKey);
    void handle(DelegateTask task);
}
```

## 10. 最佳实践

### 10.1 流程设计
- 清晰的流程命名
- 合理的任务分配
- 完善的错误处理
- 灵活的流程变量

### 10.2 高可用设计
- 集群部署
- 任务负载均衡
- 失败重试机制
- 数据备份恢复

### 10.3 安全考虑
- 权限控制
- 数据隔离
- 操作审计
- 敏感数据处理

## 11. 监控告警

### 11.1 监控指标
- 流程实例数
- 任务处理时间
- 流程完成率
- 系统资源使用

### 11.2 告警规则
- 任务超时告警
- 流程异常告警
- 系统负载告警
- 资源使用告警 