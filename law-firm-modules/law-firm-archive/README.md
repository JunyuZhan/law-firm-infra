# 档案管理模块 (Archive Module)

## 模块说明
档案管理模块是律师事务所管理系统的档案管理和存储模块，负责管理律所各类档案的收集、整理、保管和利用。该模块提供了完整的档案管理生命周期功能，支持电子档案和实物档案的统一管理，确保档案的安全性和可追溯性。

## 核心功能

### 1. 档案采集
- 档案收集
  - 案件档案
  - 合同档案
  - 人事档案
  - 财务档案
  - 行政档案
- 档案整理
  - 分类编目
  - 档案扫描
  - 信息录入
  - 档案校验
  - 关联管理
- 档案入库
  - 电子归档
  - 实物入库
  - 批量导入
  - 质量检查
  - 入库确认

### 2. 档案管理
- 档案分类
  - 分类体系
  - 分类规则
  - 标签管理
  - 关键词管理
  - 分类维护
- 档案存储
  - 存储位置
  - 存储方式
  - 存储周期
  - 存储容量
  - 备份管理
- 档案安全
  - 权限管理
  - 密级管理
  - 访问控制
  - 安全审计
  - 加密管理

### 3. 档案利用
- 档案查询
  - 全文检索
  - 条件筛选
  - 高级搜索
  - 关联查询
  - 结果导出
- 档案借阅
  - 借阅申请
  - 审批流程
  - 借阅记录
  - 归还管理
  - 超期提醒
- 档案共享
  - 共享范围
  - 共享权限
  - 共享方式
  - 共享记录
  - 共享审计

### 4. 档案维护
- 档案统计
  - 档案数量
  - 利用情况
  - 借阅统计
  - 存储统计
  - 分类统计
- 档案鉴定
  - 保管期限
  - 价值鉴定
  - 销毁鉴定
  - 鉴定记录
  - 审批流程
- 档案销毁
  - 销毁申请
  - 销毁审批
  - 销毁执行
  - 销毁记录
  - 记录归档

## 核心组件

### 1. 采集服务
- CollectionService：采集服务接口
- ScanningService：扫描服务
- ImportService：导入服务
- ValidationService：验证服务
- StorageService：入库服务

### 2. 管理服务
- ArchiveService：档案服务接口
- CategoryService：分类服务
- SecurityService：安全服务
- BackupService：备份服务
- AuditService：审计服务

### 3. 利用服务
- QueryService：查询服务接口
- BorrowService：借阅服务
- ShareService：共享服务
- ExportService：导出服务
- NotificationService：通知服务

### 4. 维护服务
- StatisticsService：统计服务
- AppraisalService：鉴定服务
- DestructionService：销毁服务
- MaintenanceService：维护服务
- MonitorService：监控服务

## 使用示例

### 1. 档案入库
```java
@Autowired
private ArchiveService archiveService;

public ArchiveDTO createArchive(ArchiveCreateRequest request) {
    // 创建档案信息
    Archive archive = new Archive()
        .setCode(generateArchiveCode())
        .setName(request.getName())
        .setCategory(ArchiveCategoryEnum.valueOf(request.getCategory()))
        .setSecurityLevel(SecurityLevelEnum.valueOf(request.getSecurityLevel()))
        .setStorageLocation(request.getStorageLocation())
        .setRetentionPeriod(request.getRetentionPeriod())
        .setDescription(request.getDescription())
        .setFiles(request.getFiles());
    
    // 保存档案
    return archiveService.createArchive(archive);
}
```

### 2. 档案借阅
```java
@Autowired
private BorrowService borrowService;

public BorrowDTO borrowArchive(BorrowRequest request) {
    // 创建借阅申请
    BorrowApplication application = new BorrowApplication()
        .setArchiveId(request.getArchiveId())
        .setPurpose(request.getPurpose())
        .setStartDate(request.getStartDate())
        .setEndDate(request.getEndDate())
        .setBorrower(SecurityUtils.getCurrentUser().getId())
        .setUrgency(request.getUrgency());
    
    // 提交借阅
    return borrowService.borrow(application);
}
```

### 3. 档案销毁
```java
@Autowired
private DestructionService destructionService;

public DestructionResult destroyArchives(DestructionRequest request) {
    // 创建销毁任务
    DestructionTask task = new DestructionTask()
        .setArchiveIds(request.getArchiveIds())
        .setReason(request.getReason())
        .setMethod(DestructionMethodEnum.valueOf(request.getMethod()))
        .setPlannedTime(request.getPlannedTime())
        .setOperator(SecurityUtils.getCurrentUser().getId());
    
    // 执行销毁
    return destructionService.destroy(task);
}
```

## 配置说明

### 1. 档案配置
```yaml
archive:
  # 编号规则
  code:
    prefix: ARC
    date-format: yyyyMMdd
    sequence-length: 4
    
  # 存储配置
  storage:
    base-path: /archives
    max-file-size: 100MB
    allowed-types: [pdf, doc, docx, jpg, png]
```

### 2. 安全配置
```yaml
security:
  # 访问控制
  access:
    enable-encryption: true
    key-rotation: 90d
    
  # 审计配置
  audit:
    enable: true
    retention-days: 365
    log-operations: true
```

## 注意事项
1. 档案完整性保护
2. 分类标准统一
3. 安全等级管控
4. 借阅规范管理
5. 定期数据备份 