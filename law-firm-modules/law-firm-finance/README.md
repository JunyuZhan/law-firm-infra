# 财务管理模块 (Finance Module)

## 模块说明
财务管理模块是律师事务所管理系统的财务核算和管理模块，负责处理律所所有的财务相关业务，包括收支管理、费用核算、发票管理、财务报表等功能。该模块与案件管理、合同管理等模块紧密集成，确保财务数据的准确性和完整性。

## 核心功能

### 1. 收支管理
- 收入管理
- 支出管理
- 费用报销
- 预算管理
- 资金流水

### 2. 费用核算
- 案件费用核算
- 成本核算管理
- 利润核算分析
- 费用分摊管理
- 账期管理

### 3. 发票管理
- 发票开具
- 发票核验
- 发票归档
- 发票统计
- 发票导出

### 4. 财务报表
- 收支报表
- 利润报表
- 税务报表
- 预算报表
- 绩效报表

## 核心组件

### 1. 财务服务
- FinanceService：财务服务接口
- AccountingService：会计服务
- BudgetService：预算服务
- ExpenseService：费用服务
- RevenueService：收入服务

### 2. 核算服务
- CostAccountingService：成本核算服务
- ProfitAccountingService：利润核算服务
- AllocationService：分摊服务
- SettlementService：结算服务
- ReconciliationService：对账服务

### 3. 发票服务
- InvoiceService：发票服务
- InvoiceVerificationService：发票核验服务
- InvoiceArchiveService：发票归档服务
- InvoiceExportService：发票导出服务
- InvoiceStatisticsService：发票统计服务

### 4. 报表服务
- ReportService：报表服务接口
- FinancialReportService：财务报表服务
- TaxReportService：税务报表服务
- BudgetReportService：预算报表服务
- PerformanceReportService：绩效报表服务

## 使用示例

### 1. 记录收入
```java
@Autowired
private RevenueService revenueService;

public RevenueDTO recordRevenue(RevenueRequest request) {
    // 创建收入记录
    Revenue revenue = new Revenue()
        .setAmount(request.getAmount())
        .setType(RevenueTypeEnum.CASE_FEE)
        .setRelatedId(request.getCaseId())
        .setPaymentMethod(request.getPaymentMethod())
        .setDescription(request.getDescription());
    
    // 保存收入
    return revenueService.recordRevenue(revenue);
}
```

### 2. 开具发票
```java
@Autowired
private InvoiceService invoiceService;

public InvoiceDTO createInvoice(InvoiceRequest request) {
    // 创建发票
    Invoice invoice = new Invoice()
        .setTitle(request.getTitle())
        .setTaxNumber(request.getTaxNumber())
        .setAmount(request.getAmount())
        .setItems(request.getItems())
        .setRemarks(request.getRemarks());
    
    // 开具发票
    return invoiceService.createInvoice(invoice);
}
```

### 3. 生成报表
```java
@Autowired
private ReportService reportService;

public ReportResult generateReport(ReportRequest request) {
    // 设置报表参数
    ReportParameter parameter = new ReportParameter()
        .setReportType(request.getType())
        .setStartDate(request.getStartDate())
        .setEndDate(request.getEndDate())
        .setDimensions(request.getDimensions());
    
    // 生成报表
    return reportService.generateReport(parameter);
}
```

## 配置说明

### 1. 财务配置
```yaml
finance:
  # 会计配置
  accounting:
    currency: CNY
    decimal-places: 2
    
  # 费用配置
  expense:
    approval-threshold: 10000
    require-receipt: true
    
  # 结算配置
  settlement:
    auto-settlement: true
    settlement-day: 25
```

### 2. 发票配置
```yaml
invoice:
  # 发票规则
  rule:
    auto-number: true
    number-prefix: INV
    
  # 开票限制
  limit:
    min-amount: 100
    max-amount: 1000000
    
  # 导出配置
  export:
    format: pdf
    include-details: true
```

## 注意事项
1. 财务数据准确性
2. 发票管理规范
3. 资金安全管理
4. 预算控制执行
5. 及时对账核算 