# 案件管理模块 (Case Module)

## 模块说明
案件管理模块是律师事务所管理系统的核心业务模块之一，负责处理律所所有案件相关的业务流程，包括案件登记、案件分配、案件进展、文书管理、费用管理等功能。该模块与多个其他模块紧密集成，确保案件处理的高效性和规范性。

## 核心功能

### 1. 案件管理
- 案件登记立案
- 案件类型管理
- 案件状态跟踪
- 案件进展管理
- 案件归档管理

### 2. 人员管理
- 承办律师分配
- 协办律师管理
- 案件团队管理
- 工作量统计
- 绩效考核支持

### 3. 文书管理
- 法律文书管理
- 证据材料管理
- 文书模板管理
- 文书审批流程
- 文书版本控制

### 4. 费用管理
- 案件收费标准
- 费用预算管理
- 费用结算管理
- 收支记录管理
- 账单生成导出

## 核心组件

### 1. 案件服务
- CaseService：案件服务接口
- CaseManager：案件管理器
- CaseHandler：案件处理器
- CaseValidator：案件验证器
- CaseConverter：案件转换器

### 2. 人员服务
- CaseTeamService：团队服务
- LawyerAssignService：律师分配服务
- WorkloadService：工作量服务
- PerformanceService：绩效服务
- TeamNotifier：团队通知器

### 3. 文书服务
- DocumentService：文书服务
- TemplateService：模板服务
- ApprovalService：审批服务
- VersionService：版本服务
- ExportService：导出服务

### 4. 费用服务
- FeeService：费用服务
- BudgetService：预算服务
- BillingService：账单服务
- PaymentService：支付服务
- InvoiceService：发票服务

## 使用示例

### 1. 创建案件
```java
@Autowired
private CaseService caseService;

public CaseDTO createCase(CaseCreateRequest request) {
    // 创建案件
    Case case = new Case()
        .setName(request.getName())
        .setType(CaseTypeEnum.CIVIL)
        .setPriority(CasePriorityEnum.NORMAL)
        .setClient(request.getClientId())
        .setDescription(request.getDescription());
    
    // 保存案件
    return caseService.createCase(case);
}
```

### 2. 分配律师
```java
@Autowired
private LawyerAssignService lawyerAssignService;

public void assignLawyer(Long caseId, Long lawyerId, AssignTypeEnum type) {
    // 创建分配请求
    LawyerAssignment assignment = new LawyerAssignment()
        .setCaseId(caseId)
        .setLawyerId(lawyerId)
        .setType(type)
        .setAssignTime(LocalDateTime.now());
    
    // 执行分配
    lawyerAssignService.assign(assignment);
}
```

### 3. 更新进展
```java
@Autowired
private CaseProgressService progressService;

public void updateProgress(Long caseId, ProgressUpdateRequest request) {
    // 创建进展记录
    CaseProgress progress = new CaseProgress()
        .setCaseId(caseId)
        .setStage(request.getStage())
        .setContent(request.getContent())
        .setNextPlan(request.getNextPlan())
        .setUpdateTime(LocalDateTime.now());
    
    // 更新进展
    progressService.updateProgress(progress);
}
```

## 配置说明

### 1. 案件配置
```yaml
case:
  # 案件编号规则
  number:
    prefix: CASE
    date-format: yyyyMMdd
    sequence-length: 4
    
  # 案件分配
  assignment:
    auto-assign: true
    max-cases-per-lawyer: 20
    
  # 案件存储
  storage:
    document-path: /cases/documents
    evidence-path: /cases/evidences
```

### 2. 费用配置
```yaml
fee:
  # 收费标准
  standard:
    min-amount: 5000
    hourly-rate: 1000
    
  # 账单配置
  billing:
    payment-terms: 30
    late-fee-rate: 0.05
    currency: CNY
```

## 注意事项
1. 案件信息安全
2. 文书规范管理
3. 费用准确计算
4. 进展及时更新
5. 团队协作机制 