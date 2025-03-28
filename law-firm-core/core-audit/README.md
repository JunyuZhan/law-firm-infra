# 法律事务管理系统 - 审计日志模块

## 模块说明

审计日志模块提供了系统操作审计和日志记录功能，通过AOP方式实现操作日志记录，支持同步和异步记录方式。本模块仅作为服务接口层提供给业务模块使用，不直接暴露REST接口。

## 核心功能

### 1. 操作日志记录
- 同步日志记录
- 异步日志记录
- 操作结果记录
- 操作参数记录
- 操作时间追踪

### 2. 数据变更审计
- 数据变更前后值记录
- 变更字段标识
- 变更操作者记录
- 变更时间追踪

### 3. 审计日志查询
- 多条件组合查询
- 操作分类筛选
- 时间范围筛选
- 操作人筛选
- 审计日志详情查看

### 4. 基于AOP的审计实现
- 自定义注解支持
- 切面拦截处理
- 灵活的过滤规则
- 审计日志格式化

## 技术架构

### 1. 核心服务接口
- AuditService: 审计服务接口
- AuditQueryService: 审计查询服务接口
- OperationLogService: 操作日志服务接口
- DataChangeService: 数据变更服务接口

### 2. AOP组件
- AuditLogAspect: 审计日志切面
- OperationLogAspect: 操作日志切面
- DataChangeAspect: 数据变更切面

### 3. 注解定义
- @AuditLog: 审计日志注解
- @OperationLog: 操作日志注解
- @DataChangeLog: 数据变更注解

### 4. 数据模型
- AuditLog: 审计日志记录
- OperationLog: 操作日志记录
- DataChangeLog: 数据变更记录

## 使用说明

### 1. 配置要求
```yaml
lawfirm:
  audit:
    enabled: true
    async-enabled: true
    log-params: true
    log-result: true
    data-change-enabled: true
```

### 2. 注解使用示例
```java
// 在Controller方法上使用审计注解
@AuditLog(module = "合同管理", operation = "创建合同", detail = "创建新合同记录")
@PostMapping("/contracts")
public Result<ContractVO> createContract(@RequestBody ContractDTO contractDTO) {
    return contractService.createContract(contractDTO);
}

// 在Service方法上使用数据变更注解
@DataChangeLog(module = "合同管理", operation = "修改合同", entity = "Contract", entityId = "#contractDTO.id")
public ContractVO updateContract(ContractDTO contractDTO) {
    // 合同更新逻辑
}
```

### 3. 服务接口调用示例
```java
// 注入服务
@Autowired
@Qualifier("coreAuditServiceImpl")
private AuditService auditService;

@Autowired
@Qualifier("auditQueryServiceImpl")
private AuditQueryService auditQueryService;

// 手动记录审计日志
auditService.recordLog("合同管理", "删除合同", "删除合同记录，合同ID：" + contractId);

// 异步记录审计日志
auditService.recordLogAsync("合同管理", "批量审核", "批量审核合同，审核数量：" + contracts.size());

// 查询审计日志
Page<AuditLogVO> logs = auditQueryService.queryAuditLogs(
    AuditLogQueryDTO.builder()
        .module("合同管理")
        .operation("创建合同")
        .startTime(startTime)
        .endTime(endTime)
        .operatorId(operatorId)
        .pageNum(1)
        .pageSize(10)
        .build()
);
```

## 安全说明

1. 数据安全
   - 敏感信息过滤
   - 审计日志加密存储
   - 访问权限控制

2. 性能考虑
   - 异步日志记录
   - 日志批量处理
   - 合理的日志保留策略

3. 合规保障
   - 满足监管合规要求
   - 支持多种审计规范
   - 审计日志防篡改
