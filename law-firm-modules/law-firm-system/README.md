# 系统管理模块 (System Module)

## 模块说明
系统管理模块是律师事务所管理系统的基础支撑模块，负责系统的基础配置、运行监控、日志管理、数据字典等功能。该模块为整个系统提供了统一的系统管理和运维支持能力。

## 核心功能

### 1. 系统配置
- 参数配置管理
- 字典数据管理
- 菜单配置管理
- 接口配置管理
- 任务配置管理

### 2. 运行监控
- 系统运行监控
- 性能指标监控
- 接口调用监控
- 在线用户监控
- 系统资源监控

### 3. 日志管理
- 操作日志管理
- 登录日志管理
- 异常日志管理
- 审计日志管理
- 安全日志管理

### 4. 系统维护
- 数据备份恢复
- 缓存管理维护
- 系统通知公告
- 系统版本管理
- 系统诊断工具

## 核心组件

### 1. 配置服务
- ConfigService：配置服务接口
- DictionaryService：字典服务
- MenuService：菜单服务
- ApiConfigService：接口配置服务
- TaskConfigService：任务配置服务

### 2. 监控服务
- MonitorService：监控服务接口
- PerformanceMonitor：性能监控
- ApiMonitor：接口监控
- UserSessionMonitor：会话监控
- ResourceMonitor：资源监控

### 3. 日志服务
- LogService：日志服务接口
- OperationLogService：操作日志服务
- LoginLogService：登录日志服务
- ExceptionLogService：异常日志服务
- AuditLogService：审计日志服务

### 4. 维护服务
- MaintenanceService：维护服务接口
- BackupService：备份服务
- CacheService：缓存服务
- NoticeService：通知服务
- DiagnosticService：诊断服务

## 使用示例

### 1. 系统配置
```java
@Autowired
private ConfigService configService;

public void updateConfig(ConfigUpdateRequest request) {
    // 创建配置
    SystemConfig config = new SystemConfig()
        .setKey(request.getKey())
        .setValue(request.getValue())
        .setDescription(request.getDescription())
        .setModule(request.getModule())
        .setUpdateBy(SecurityUtils.getCurrentUser().getId());
    
    // 更新配置
    configService.updateConfig(config);
}
```

### 2. 记录日志
```java
@Autowired
private OperationLogService logService;

public void recordOperation(OperationLogRequest request) {
    // 创建日志
    OperationLog log = new OperationLog()
        .setModule(request.getModule())
        .setOperation(request.getOperation())
        .setParams(request.getParams())
        .setResult(request.getResult())
        .setOperator(SecurityUtils.getCurrentUser().getId())
        .setOperateTime(LocalDateTime.now());
    
    // 保存日志
    logService.recordLog(log);
}
```

### 3. 系统监控
```java
@Autowired
private MonitorService monitorService;

public MonitorResult getSystemStatus() {
    // 设置监控维度
    MonitorDimension dimension = new MonitorDimension()
        .setSystem(true)
        .setPerformance(true)
        .setResource(true)
        .setApi(true);
    
    // 获取监控数据
    return monitorService.monitor(dimension);
}
```

## 配置说明

### 1. 系统配置
```yaml
system:
  # 基础配置
  base:
    name: 律师事务所管理系统
    version: 1.0.0
    admin-email: admin@lawfirm.com
    
  # 监控配置
  monitor:
    enabled: true
    interval: 60
    retention-days: 30
    
  # 日志配置
  log:
    enabled: true
    level: INFO
    retention-days: 90
```

### 2. 维护配置
```yaml
maintenance:
  # 备份配置
  backup:
    auto-backup: true
    backup-time: "0 0 2 * * ?"
    backup-path: /backup
    
  # 缓存配置
  cache:
    type: redis
    expire-time: 24h
    max-size: 1000
```

## 注意事项
1. 配置变更管理
2. 日志存储容量
3. 监控告警阈值
4. 备份恢复机制
5. 系统性能优化 