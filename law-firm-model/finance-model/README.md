# 财务模型模块 (Finance Model)

## 模块说明
财务模型模块是律师事务所管理系统的财务数据结构定义模块，负责定义系统中所有与财务相关的实体类、数据传输对象、视图对象和枚举类型。该模块是财务管理功能的数据基础。

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

## 注意事项
1. 交易编号必须唯一
2. 发票信息需要严格管理
3. 金额计算需要精确
4. 重要操作需要记录
5. 定期对账和审计 