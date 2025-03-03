# 合同模型模块 (Contract Model)

## 模块说明
合同模型模块是律师事务所管理系统的合同数据结构定义模块，负责定义系统中所有与合同相关的实体类、数据传输对象、视图对象和枚举类型。该模块是合同管理功能的数据基础。

## 目录结构

```
contract-model/
├── constants/        # 常量类
│   ├── ContractStatusConstants.java  # 合同状态常量类，定义合同状态的常量
│   ├── PaymentTypeConstants.java     # 支付类型常量类，定义支付方式的常量
│   └── ContractTypeConstants.java    # 合同类型常量类，定义合同类型的常量
├── enums/            # 枚举类型
│   ├── ContractTypeEnum.java        # 合同类型枚举，定义合同的类型
│   ├── ContractStatusEnum.java      # 合同状态枚举，定义合同的状态
│   ├── ContractPriorityEnum.java     # 合同优先级枚举，定义合同的优先级
│   └── PaymentTypeEnum.java         # 支付类型枚举，定义合同的支付方式
├── entity/           # 合同实体类
│   ├── Contract.java                # 合同实体类，包含合同的基本信息和状态管理
│   ├── ContractClause.java          # 合同条款实体类，定义合同的条款内容
│   ├── ContractReview.java           # 合同审核实体类，管理合同的审核信息
│   ├── ContractChange.java           # 合同变更实体类，记录合同的变更信息
│   └── ContractAttachment.java       # 合同附件实体类，管理合同相关的附件信息
├── dto/              # 数据传输对象
│   ├── ContractCreateDTO.java       # 创建合同的请求数据传输对象
│   ├── ContractUpdateDTO.java       # 更新合同的请求数据传输对象
│   ├── ContractQueryDTO.java        # 查询合同的请求数据传输对象
│   └── ContractReviewDTO.java       # 合同审核的请求数据传输对象
├── vo/               # 视图对象
│   ├── ContractVO.java              # 合同视图对象，展示合同的基本信息
│   ├── ContractDetailVO.java        # 合同详情视图对象，展示合同的详细信息
│   ├── ContractListVO.java          # 合同列表视图对象，展示合同的简要信息，包括合同编号、名称、状态、客户名称和签约日期。
│   └── ContractSummaryVO.java       # 合同汇总视图对象，展示合同的汇总信息，包括合同总数、总金额、活跃合同数量和已到期合同数量。
├── service/          # 服务接口
│   ├── ContractService.java          # 合同服务接口，提供合同相关的业务逻辑
│   └── ContractReviewService.java    # 合同审核服务接口，提供合同审核的业务逻辑
```

## 核心功能

### 1. 合同基本信息
- 合同实体（Contract）
  - 合同编号
  - 合同名称
  - 合同类型
  - 合同状态
  - 合同金额
  - 签约时间
  - 客户信息（关联客户实体）
  - 合同条款（可选条款列表）

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

## 重新设计

### 1. 合同条款（ContractClause）
- 条款编号
- 条款内容
- 条款类型（可选、强制）

### 2. 合同审核（ContractReview）
- 审核状态
- 审核意见
- 审核人信息
- 审核时间

### 3. 合同变更（ContractChange）
- 变更编号
- 变更内容
- 变更时间
- 变更原因

### 4. 合同附件（ContractAttachment）
- 附件编号
- 附件类型
- 附件路径
- 上传时间

### 5. 合同管理功能
- 合同创建
- 合同更新
- 合同删除
- 合同查询
- 合同审核
- 合同变更记录管理

### 6. 数据传输对象（DTO）
- ContractCreateDTO
- ContractUpdateDTO
- ContractQueryDTO
- ContractReviewDTO

### 7. 视图对象（VO）
- ContractVO
- ContractDetailVO
- ContractListVO
- ContractSummaryVO

### 8. 枚举类型
- ContractStatusEnum
- ContractTypeEnum

### 9. 依赖关系
- 依赖于客户模块（Client）
- 依赖于基础模型模块（BaseModel）

### 10. 设计原则
- 遵循单一职责原则
- 采用模块化设计，确保高内聚低耦合。

## 设计说明
本模块主要用于定义合同相关的枚举、数据传输对象（DTO）、视图对象（VO）和服务接口，并不涉及具体的实现。

## 常量定义

### 1. 合同状态常量
```java
public class ContractStatusConstants {
    public static final String DRAFT = "草稿";
    public static final String UNDER_REVIEW = "审核中";
    public static final String EFFECTIVE = "已生效";
    public static final String TERMINATED = "已终止";
    public static final String EXPIRED = "已到期";
}
```

### 2. 支付类型常量
```java
public class PaymentTypeConstants {
    public static final String ONE_TIME = "一次性付款";
    public static final String INSTALLMENT = "分期付款";
    public static final String PROGRESS_PAYMENT = "按进度付款";
}
```

### 3. 合同类型常量
```java
public class ContractTypeConstants {
    public static final String LEGAL_SERVICE = "法律服务合同";
    public static final String CONSULTING_SERVICE = "咨询服务合同";
    public static final String AGENCY = "代理合同";
    public static final String OTHER = "其他合同类型";
}
```

## 迁移记录

### JPA到MyBatis Plus迁移 (2024-04-28)
- 添加MyBatis Plus注解（@TableName、@TableField）
- 移除JPA相关注解和导入
- 修改pom.xml，移除JPA依赖，添加MyBatis Plus依赖
- 更新实体类继承关系，使用ModelBaseEntity

已迁移的实体类：
- Contract.java
- ContractAttachment.java
- ContractChange.java
- ContractClause.java
- ContractReview.java
