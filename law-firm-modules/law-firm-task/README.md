# 任务管理模块 (Task Module)

## 模块说明
任务管理模块是律师事务所管理系统的任务协作和管理模块，负责管理律所内部各类任务的创建、分配、跟踪和考核。该模块提供了灵活的任务管理机制，支持多维度的任务分类和进度跟踪，提高团队协作效率。

## 核心功能

### 1. 任务管理
- 任务创建分配
- 任务类型管理
- 任务状态管理
- 任务优先级管理
- 任务标签管理

### 2. 进度管理
- 任务进度跟踪
- 里程碑管理
- 时间节点管理
- 延期预警管理
- 进度报告管理

### 3. 协作管理
- 任务团队管理
- 任务评论讨论
- 任务文件共享
- 任务通知提醒
- 任务协作日志

### 4. 统计分析
- 任务完成统计
- 工时统计分析
- 延期情况分析
- 工作量分析
- 绩效评估分析

## 核心组件

### 1. 任务服务
- TaskService：任务服务接口
- TaskTypeService：类型服务
- TaskStatusService：状态服务
- TaskPriorityService：优先级服务
- TaskTagService：标签服务

### 2. 进度服务
- ProgressService：进度服务接口
- MilestoneService：里程碑服务
- TimelineService：时间线服务
- WarningService：预警服务
- ReportService：报告服务

### 3. 协作服务
- CollaborationService：协作服务接口
- TeamService：团队服务
- CommentService：评论服务
- NotificationService：通知服务
- FileService：文件服务

### 4. 统计服务
- StatisticsService：统计服务接口
- CompletionService：完成率服务
- WorkloadService：工作量服务
- PerformanceService：绩效服务
- AnalysisService：分析服务

## 使用示例

### 1. 创建任务
```java
@Autowired
private TaskService taskService;

public TaskDTO createTask(TaskCreateRequest request) {
    // 创建任务
    Task task = new Task()
        .setTitle(request.getTitle())
        .setDescription(request.getDescription())
        .setType(TaskTypeEnum.valueOf(request.getType()))
        .setPriority(PriorityEnum.valueOf(request.getPriority()))
        .setAssignee(request.getAssigneeId())
        .setDeadline(request.getDeadline())
        .setTags(request.getTags());
    
    // 保存任务
    return taskService.createTask(task);
}
```

### 2. 更新进度
```java
@Autowired
private ProgressService progressService;

public void updateProgress(Long taskId, ProgressUpdateRequest request) {
    // 更新进度
    Progress progress = new Progress()
        .setTaskId(taskId)
        .setPercentage(request.getPercentage())
        .setStatus(TaskStatusEnum.valueOf(request.getStatus()))
        .setRemark(request.getRemark())
        .setUpdatedBy(SecurityUtils.getCurrentUser().getId());
    
    // 保存进度
    progressService.updateProgress(progress);
}
```

### 3. 任务统计
```java
@Autowired
private StatisticsService statisticsService;

public StatisticsResult analyzeTaskStatistics(StatisticsRequest request) {
    // 设置统计参数
    StatisticsParameter parameter = new StatisticsParameter()
        .setStartDate(request.getStartDate())
        .setEndDate(request.getEndDate())
        .setDepartment(request.getDepartmentId())
        .setType(request.getType())
        .setDimensions(request.getDimensions());
    
    // 执行统计
    return statisticsService.analyze(parameter);
}
```

## 配置说明

### 1. 任务配置
```yaml
task:
  # 基础配置
  base:
    enable-subtask: true
    max-subtask-level: 3
    enable-template: true
    
  # 通知配置
  notification:
    deadline-remind: true
    remind-days: [7, 3, 1]
    channels: [email, system]
```

### 2. 进度配置
```yaml
progress:
  # 预警配置
  warning:
    enable: true
    threshold: 80
    check-interval: 1h
    
  # 报告配置
  report:
    auto-generate: true
    period: weekly
    template: default
```

## 注意事项
1. 任务分配合理
2. 进度及时更新
3. 团队协作顺畅
4. 工作量均衡
5. 数据分析应用 