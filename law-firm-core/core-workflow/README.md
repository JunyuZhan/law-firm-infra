# 工作流引擎模块

## 模块说明

工作流引擎模块(`core-workflow`)是核心功能层的关键组成部分，基于Flowable工作流引擎封装，提供流程定义、流程实例、任务管理等功能。本模块**仅作为功能封装层**提供工作流能力支持，不直接对外暴露REST接口，不负责数据持久化和配置管理。

## 功能特性

### 1. 流程引擎封装
- Flowable工作流引擎集成和配置
- 流程定义管理接口
- 流程实例管理接口
- 任务管理接口

### 2. 表单管理
- 表单定义接口
- 表单数据处理
- 表单验证支持

### 3. 流程监控
- 流程实例状态查询
- 任务执行监控
- 流程性能监控

### 4. 流程权限
- 流程发起权限控制接口
- 任务处理权限控制接口

## 使用方式

### 1. 添加依赖
在业务模块的pom.xml中添加：
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-workflow</artifactId>
</dependency>
```

### 2. 定义数据存储和配置
- **重要**：业务模块负责定义和管理工作流相关的数据库表
- **重要**：业务模块负责提供工作流引擎所需的配置

业务模块应在自己的资源目录中定义数据库表：
```sql
-- 在业务模块中定义工作流相关表(示例)
CREATE TABLE business_process (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_type VARCHAR(64) NOT NULL,
    business_id VARCHAR(64) NOT NULL,
    process_instance_id VARCHAR(64) NOT NULL,
    -- 其他业务字段
);
```

### 3. 服务注入
使用`@Autowired`和`@Qualifier`注入服务：
```java
@Autowired
@Qualifier("coreProcessServiceImpl")
private ProcessService processService;

@Autowired
@Qualifier("coreTaskServiceImpl")
private TaskService taskService;

@Autowired
@Qualifier("coreFormServiceImpl")
private FormService formService;
```

### 4. 服务调用示例
```java
// 启动流程
String processInstanceId = processService.startProcess(
    "contractApproval",  // 流程定义Key
    "CONTRACT-2024-001", // 业务标识
    Map.of("contractType", "服务合同", "priority", "高")  // 流程变量
);

// 查询任务
List<TaskVO> tasks = taskService.listUserTasks("lawyer1");

// 完成任务
taskService.completeTask(
    "task1",  // 任务ID
    Map.of("approved", true, "comment", "同意")  // 任务变量
);
```

### 5. 业务层封装建议
建议在业务层对工作流服务进行封装，增加业务逻辑：
```java
@Service
public class ContractWorkflowService {
    
    @Autowired
    private ProcessService processService;
    
    /**
     * 启动合同审批流程
     */
    public String startContractApprovalProcess(Contract contract) {
        // 构建业务参数
        Map<String, Object> variables = new HashMap<>();
        variables.put("contractId", contract.getId());
        variables.put("contractType", contract.getType());
        variables.put("amount", contract.getAmount());
        
        // 调用流程服务
        return processService.startProcess(
            "contract_approval",
            "CONTRACT-" + contract.getId(),
            variables
        );
    }
}
```

## 配置说明

本模块作为功能封装层，**不直接管理配置**。业务模块负责提供以下配置：

1. 数据源配置
2. Flowable引擎配置
3. 流程定义文件位置
4. 流程历史记录配置

业务模块提供配置示例：
```yaml
# 在业务模块的application.yml中配置
flowable:
  database-schema-update: true
  history-level: full
  async-executor-activate: true
  process-definition-location-prefix: classpath:/processes/
```

## 注意事项

1. 本模块仅提供功能封装，不负责数据持久化
2. 流程定义文件(.bpmn20.xml)应放在业务模块资源目录下
3. 工作流相关的数据表应由业务模块定义和管理
4. 业务模块应处理好工作流与业务对象的关联关系
5. 所有工作流配置应由业务模块提供
