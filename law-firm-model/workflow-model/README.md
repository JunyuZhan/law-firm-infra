 # 工作流模型模块

## 模块说明
工作流模型模块提供了律师事务所系统中工作流相关的核心数据模型和服务接口定义。该模块主要包含流程管理、任务管理等功能的基础数据结构和服务定义。

## 目录结构
```
workflow-model/
├── entity/
│   ├── base/             # 基础流程
│   │   ├── BaseProcess.java      # 流程基类
│   │   └── ProcessTask.java      # 任务基类
│   └── common/           # 通用流程
│       └── CommonProcess.java    # 通用流程
├── enums/
│   ├── ProcessTypeEnum.java   # 流程类型
│   ├── ProcessStatusEnum.java # 流程状态
│   └── TaskTypeEnum.java      # 任务类型
├── dto/
│   ├── process/
│   │   ├── ProcessCreateDTO.java # 流程创建
│   │   ├── ProcessUpdateDTO.java # 流程更新
│   │   └── ProcessQueryDTO.java  # 流程查询
│   └── task/
│       ├── TaskCreateDTO.java    # 任务创建
│       └── TaskQueryDTO.java     # 任务查询
├── vo/
│   ├── ProcessVO.java      # 流程视图
│   └── TaskVO.java         # 任务视图
└── service/
    ├── ProcessService.java  # 流程服务
    └── TaskService.java     # 任务服务
```

## 核心功能

### 1. 流程管理
- 流程定义：支持自定义流程模板和流程节点
- 流程实例：管理流程实例的创建、更新、查询等操作
- 流程状态：跟踪流程实例的状态变更
- 流程类型：支持多种业务类型的流程定义

### 2. 任务管理
- 任务分配：支持任务的创建和分配
- 任务状态：跟踪任务的执行状态
- 任务类型：支持多种类型的任务定义
- 任务查询：提供灵活的任务查询功能

## 主要类说明

### 实体类
1. BaseProcess
   - 流程基类，定义流程的基本属性
   - 包含：流程ID、流程名称、流程类型、创建时间等

2. ProcessTask
   - 任务基类，定义任务的基本属性
   - 包含：任务ID、任务名称、任务类型、执行人等

3. CommonProcess
   - 通用流程实现，继承自BaseProcess
   - 适用于常规业务流程

### 枚举类
1. ProcessTypeEnum
   - 定义流程类型：如案件流程、合同流程等

2. ProcessStatusEnum
   - 定义流程状态：如草稿、进行中、已完成等

3. TaskTypeEnum
   - 定义任务类型：如审批任务、处理任务等

### DTO类
1. ProcessCreateDTO
   - 流程创建数据传输对象
   - 包含创建流程所需的必要信息

2. ProcessUpdateDTO
   - 流程更新数据传输对象
   - 包含更新流程所需的信息

3. TaskCreateDTO
   - 任务创建数据传输对象
   - 包含创建任务所需的必要信息

### VO类
1. ProcessVO
   - 流程视图对象
   - 用于展示流程信息

2. TaskVO
   - 任务视图对象
   - 用于展示任务信息

### 服务接口
1. ProcessService
   - 提供流程相关的业务操作接口
   - 包含流程的CRUD操作

2. TaskService
   - 提供任务相关的业务操作接口
   - 包含任务的CRUD操作

## 使用说明
1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>workflow-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

2. 继承基类
```java
public class CustomProcess extends BaseProcess {
    // 自定义流程实现
}
```

3. 使用服务接口
```java
@Autowired
private ProcessService processService;

// 创建流程
ProcessCreateDTO createDTO = new ProcessCreateDTO();
Long processId = processService.createProcess(createDTO);

// 查询流程
ProcessVO process = processService.getProcess(processId);
```

## 注意事项
1. 所有实体类都继承自BaseModel，确保基础字段的一致性
2. DTO类继承自BaseDTO，VO类继承自BaseVO
3. 遵循统一的命名规范和代码风格
4. 确保完整的单元测试覆盖