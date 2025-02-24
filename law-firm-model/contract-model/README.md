# 合同模型模块 (Contract Model)

## 模块说明
合同模型模块是律师事务所管理系统的合同数据结构定义模块，负责定义系统中所有与合同相关的实体类、数据传输对象、视图对象和枚举类型。该模块是合同管理功能的数据基础。

## 核心功能

### 1. 合同基本信息
- 合同实体（Contract）
  - 合同编号
  - 合同名称
  - 合同类型
  - 合同状态
  - 合同金额
  - 签约时间

### 2. 合同分类
- 合同类型（ContractTypeEnum）
  - 法律服务合同
  - 咨询服务合同
  - 代理合同
  - 其他合同类型

### 3. 合同状态
- 合同状态（ContractStatusEnum）
  - 草稿
  - 审核中
  - 已生效
  - 已终止
  - 已到期

### 4. 合同属性
- 合同优先级（ContractPriorityEnum）
  - 高优先级
  - 普通优先级
  - 低优先级

- 支付类型（PaymentTypeEnum）
  - 一次性付款
  - 分期付款
  - 按进度付款

### 5. 合同关联
- 客户关联
- 案件关联
- 律师关联
- 律所关联
- 财务关联

## 核心类说明

### 1. 实体类
- Contract：合同实体
  - 基本信息
  - 合同内容
  - 签约信息
  - 付款信息
  - 关联信息
  - 状态管理

### 2. 数据传输对象
- ContractDTO：合同数据传输对象
  - 创建请求
  - 更新请求
  - 查询条件
  - 详情展示

### 3. 视图对象
- ContractVO：合同视图对象
  - 列表展示
  - 详情展示
  - 统计信息
  - 关联信息

### 4. 枚举类型
- ContractTypeEnum：合同类型
- ContractStatusEnum：合同状态
- ContractPriorityEnum：优先级
- PaymentTypeEnum：付款方式

## 使用示例

### 1. 创建合同
```java
Contract contract = new Contract()
    .setName("法律顾问服务合同")
    .setType(ContractTypeEnum.LEGAL_SERVICE)
    .setStatus(ContractStatusEnum.DRAFT)
    .setPriority(ContractPriorityEnum.NORMAL)
    .setPaymentType(PaymentTypeEnum.INSTALLMENT);
```

### 2. 合同状态更新
```java
contract.setStatus(ContractStatusEnum.EFFECTIVE)
    .setUpdateBy("admin")
    .setRemark("合同已通过审核并生效");
```

## 注意事项
1. 合同编号必须唯一
2. 关注合同到期提醒
3. 重要变更需要记录
4. 注意付款计划管理
5. 保护合同隐私信息
