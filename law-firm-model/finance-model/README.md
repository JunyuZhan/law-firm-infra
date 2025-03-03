# 财务模型模块 (Finance Model)

## 模块说明
财务模型模块是律师事务所管理系统的财务数据结构定义模块，负责定义系统中所有与财务相关的实体类、数据传输对象、视图对象和枚举类型。该模块是财务管理功能的数据基础。

## 目录结构
```
constants/
├── FinanceFieldConstants.java    # 财务字段限制常量
├── FinanceStatusConstants.java   # 财务状态相关常量
├── FinanceMessageConstants.java  # 财务消息提示常量
├── FinanceErrorConstants.java    # 财务错误码常量
└── FinanceRuleConstants.java     # 财务规则常量

entity/
├── Account.java                # 账户实体
├── Transaction.java            # 交易记录实体
├── Invoice.java               # 发票实体
├── Fee.java                   # 费用实体
├── Budget.java                # 预算实体
├── Expense.java               # 支出实体
├── Income.java                # 收入实体
├── BillingRecord.java         # 账单记录实体
├── PaymentPlan.java           # 付款计划实体
├── CostCenter.java            # 成本中心实体
└── FinancialReport.java       # 财务报表实体

enums/
├── InvoiceTypeEnum.java       # 发票类型枚举
├── InvoiceStatusEnum.java     # 发票状态枚举
├── PaymentMethodEnum.java     # 支付方式枚举
├── TransactionTypeEnum.java   # 交易类型枚举
├── AccountTypeEnum.java       # 账户类型枚举
├── AccountStatusEnum.java     # 账户状态枚举
├── CurrencyEnum.java         # 币种枚举
├── FeeTypeEnum.java          # 费用类型枚举
├── IncomeTypeEnum.java       # 收入类型枚举
├── ExpenseTypeEnum.java      # 支出类型枚举
├── BudgetTypeEnum.java       # 预算类型枚举
├── BudgetStatusEnum.java     # 预算状态枚举
├── BillingStatusEnum.java    # 账单状态枚举
├── PaymentStatusEnum.java    # 付款状态枚举
└── ReportTypeEnum.java       # 报表类型枚举

dto/
├── account/
│   ├── AccountCreateDTO.java     # 账户创建DTO
│   ├── AccountUpdateDTO.java     # 账户更新DTO
│   └── AccountQueryDTO.java      # 账户查询DTO
├── transaction/
│   ├── TransactionCreateDTO.java # 交易创建DTO
│   ├── TransactionUpdateDTO.java # 交易更新DTO
│   └── TransactionQueryDTO.java  # 交易查询DTO
├── invoice/
│   ├── InvoiceCreateDTO.java     # 发票创建DTO
│   ├── InvoiceUpdateDTO.java     # 发票更新DTO
│   └── InvoiceQueryDTO.java      # 发票查询DTO
├── fee/
│   ├── FeeCreateDTO.java         # 费用创建DTO
│   ├── FeeUpdateDTO.java         # 费用更新DTO
│   └── FeeQueryDTO.java          # 费用查询DTO
├── budget/
│   ├── BudgetCreateDTO.java      # 预算创建DTO
│   ├── BudgetUpdateDTO.java      # 预算更新DTO
│   └── BudgetQueryDTO.java       # 预算查询DTO
├── expense/
│   ├── ExpenseCreateDTO.java     # 支出创建DTO
│   ├── ExpenseUpdateDTO.java     # 支出更新DTO
│   └── ExpenseQueryDTO.java      # 支出查询DTO
├── income/
│   ├── IncomeCreateDTO.java      # 收入创建DTO
│   ├── IncomeUpdateDTO.java      # 收入更新DTO
│   └── IncomeQueryDTO.java       # 收入查询DTO
├── billing/
│   ├── BillingCreateDTO.java     # 账单创建DTO
│   ├── BillingUpdateDTO.java     # 账单更新DTO
│   └── BillingQueryDTO.java      # 账单查询DTO
└── report/
    ├── ReportCreateDTO.java      # 报表创建DTO
    ├── ReportUpdateDTO.java      # 报表更新DTO
    └── ReportQueryDTO.java       # 报表查询DTO

vo/
├── account/
│   ├── AccountDetailVO.java      # 账户详情VO
│   └── AccountListVO.java        # 账户列表VO
├── transaction/
│   ├── TransactionDetailVO.java  # 交易详情VO
│   └── TransactionListVO.java    # 交易列表VO
├── invoice/
│   ├── InvoiceDetailVO.java      # 发票详情VO
│   └── InvoiceListVO.java        # 发票列表VO
├── fee/
│   ├── FeeDetailVO.java          # 费用详情VO
│   └── FeeListVO.java            # 费用列表VO
├── budget/
│   ├── BudgetDetailVO.java       # 预算详情VO
│   ├── BudgetListVO.java         # 预算列表VO
│   └── BudgetStatisticsVO.java   # 预算统计VO
├── expense/
│   ├── ExpenseDetailVO.java      # 支出详情VO
│   ├── ExpenseListVO.java        # 支出列表VO
│   └── ExpenseStatisticsVO.java  # 支出统计VO
├── income/
│   ├── IncomeDetailVO.java       # 收入详情VO
│   ├── IncomeListVO.java         # 收入列表VO
│   └── IncomeStatisticsVO.java   # 收入统计VO
├── billing/
│   ├── BillingDetailVO.java      # 账单详情VO
│   └── BillingListVO.java        # 账单列表VO
└── report/
    ├── FinancialReportVO.java    # 财务报表VO
    ├── ProfitLossReportVO.java   # 损益报表VO
    ├── CashFlowReportVO.java     # 现金流量表VO
    └── BalanceSheetVO.java       # 资产负债表VO
```

---

## 核心功能

### 1. 财务交易
- 交易实体
  - 交易编号
  - 交易类型
  - 交易金额
  - 交易时间
  - 支付方式
  - 交易状态

### 2. 发票管理
- 发票实体
  - 发票编号
  - 发票抬头
  - 发票金额
  - 开票时间
  - 纳税人识别号
  - 发票状态

### 3. 财务属性
- 交易类型（TransactionTypeEnum）
  - 收入
  - 支出
  - 退款
  - 其他

- 支付方式（PaymentMethodEnum）
  - 现金
  - 银行转账
  - 支票
  - 电子支付

### 4. 财务关联
- 案件关联
  - 案件收费
  - 案件支出
  - 案件退款

- 合同关联
  - 合同收款
  - 合同支出
  - 合同退款

- 其他关联
  - 客户关联
  - 律师关联
  - 律所关联

---

## 核心类说明

### 1. 查询对象
- FinanceQueryDTO：财务查询对象
  - 交易编号
  - 交易类型
  - 金额范围
  - 时间范围
  - 支付方式
  - 创建人

- InvoiceQueryDTO：发票查询对象
  - 发票编号
  - 发票抬头
  - 金额范围
  - 开票时间
  - 纳税人编号
  - 开票人

### 2. 数据传输对象
- FinanceDTO：财务数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

- InvoiceDTO：发票数据传输对象
  - 开票请求
  - 更新请求
  - 查询条件
  - 详情展示

### 3. 视图对象
- FinanceVO：财务视图对象
  - 列表展示
  - 详情展示
  - 统计信息
  - 关联信息

- InvoiceVO：发票视图对象
  - 列表展示
  - 详情展示
  - 统计信息
  - 打印信息

---

## 使用示例

### 1. 财务查询
```java
FinanceQueryDTO query = new FinanceQueryDTO()
    .setTransactionNumber("FIN2024001")
    .setTransactionType(TransactionTypeEnum.INCOME)
    .setMinAmount(new BigDecimal("1000"))
    .setMaxAmount(new BigDecimal("10000"))
    .setPaymentMethod(PaymentMethodEnum.BANK_TRANSFER);
```

### 2. 发票查询
```java
InvoiceQueryDTO query = new InvoiceQueryDTO()
    .setInvoiceNumber("INV2024001")
    .setTitle("某某公司")
    .setMinAmount(new BigDecimal("5000"))
    .setMaxAmount(new BigDecimal("50000"))
    .setTaxpayerNumber("91110000XXX");
```

---

## 注意事项
1. 交易编号必须唯一
2. 发票信息需要严格管理
3. 金额计算需要精确
4. 重要操作需要记录
5. 定期对账和审计 

## 迁移记录

### JPA到MyBatis Plus迁移 (2024-04-28)
- 检查实体类，确认已经添加了MyBatis Plus注解（@TableName、@TableField）
- 移除pom.xml中的JPA相关依赖，添加MyBatis Plus依赖
- 确保实体类继承关系正确

已迁移的实体类：
- Account.java
- Transaction.java
- Invoice.java
- Fee.java
- Budget.java
- Expense.java
- Income.java
- BillingRecord.java
- PaymentPlan.java
- CostCenter.java
- FinancialReport.java 