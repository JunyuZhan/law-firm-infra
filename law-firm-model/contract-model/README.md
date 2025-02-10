# 合同模型模块 (Contract Model)

## 模块说明
合同模型模块是律师事务所管理系统中的核心业务模型之一，负责定义与合同相关的所有数据结构和业务规则。该模块提供了合同管理所需的实体类、数据传输对象(DTO)、枚举类型等基础组件。

## 功能特性
- 合同基础信息管理
- 合同相关方管理
- 合同条款管理
- 合同审批流程
- 合同付款计划
- 合同附件管理
- 合同状态跟踪
- 多租户支持
- 版本控制

## 模块结构
```
contract-model/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lawfirm/model/contract/
│   │   │       ├── constants/    # 常量定义
│   │   │       ├── dto/          # 数据传输对象
│   │   │       │   ├── request/  # 请求DTO
│   │   │       │   └── response/ # 响应DTO
│   │   │       ├── entity/       # 实体类
│   │   │       └── enums/        # 枚举类型
│   │   └── resources/            # 资源文件
│   └── test/                     # 单元测试
└── pom.xml                       # 项目配置文件
```

## 核心组件

### 实体类
- `Contract`: 合同主实体
- `ContractParty`: 合同相关方
- `ContractClause`: 合同条款
- `ContractApproval`: 合同审批
- `ContractPayment`: 合同付款计划
- `ContractAttachment`: 合同附件

### 枚举类型
- `ContractStatusEnum`: 合同状态
- `ContractTypeEnum`: 合同类型
- `ContractPriorityEnum`: 合同优先级
- `ApprovalStatusEnum`: 审批状态
- `PaymentTypeEnum`: 付款类型

### 数据传输对象
- 请求DTO
  - `ContractCreateRequest`: 创建合同
  - `ContractUpdateRequest`: 更新合同
  - `ContractQueryRequest`: 查询合同
- 响应DTO
  - `ContractDetailResponse`: 合同详情
  - `ContractListResponse`: 合同列表
  - `ContractStatisticsResponse`: 合同统计

## 技术架构

### 依赖关系
- 基础模型依赖
  - `base-model`: 提供基础实体和通用功能
  - `common-core`: 提供核心功能支持
  - `common-data`: 提供数据访问支持

### 继承体系
```
BaseEntity (common-core)
    └── DataBaseEntity (common-data)
        └── ModelBaseEntity (base-model)
            └── Contract, ContractParty, etc.
```

### 框架支持
- MyBatis Plus: 数据访问框架
- Jakarta Validation: 数据校验
- Jakarta Persistence: JPA注解支持
- Lombok: 代码简化工具
- Jackson: JSON处理

## 使用说明

### 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>contract-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 代码示例

1. 创建合同
```java
Contract contract = new Contract()
    .setContractName("法律服务合同")
    .setContractType(ContractTypeEnum.LEGAL_SERVICE)
    .setContractStatus(ContractStatusEnum.DRAFT)
    .setPriority(ContractPriorityEnum.HIGH);
```

2. 添加合同相关方
```java
ContractParty party = new ContractParty()
    .setPartyName("客户公司")
    .setPartyType("甲方")
    .setIsSignatory(true);
```

3. 设置付款计划
```java
ContractPayment payment = new ContractPayment()
    .setPaymentType(PaymentTypeEnum.INSTALLMENT)
    .setAmount(new BigDecimal("100000.00"))
    .setPaymentStage("首付款");
```

## 开发规范

1. 实体类规范
   - 所有实体类必须继承 `ModelBaseEntity`
   - 必须使用 `@Data` 和 `@Accessors(chain = true)` 注解
   - 必须添加完整的字段注释

2. 枚举类规范
   - 必须实现 `@JsonValue` 注解
   - 必须提供 code 和 description 属性
   - 必须添加完整的注释

3. DTO规范
   - 请求DTO必须包含数据校验注解
   - 响应DTO必须考虑序列化效率
   - 必须提供清晰的字段注释

## 测试覆盖

1. 实体测试
   - 基本属性测试
   - 链式调用测试
   - 继承功能测试

2. 枚举测试
   - 枚举值测试
   - 转换功能测试

3. DTO测试
   - 数据校验测试
   - 转换功能测试

## 维护说明

### 版本历史
- v1.0.0: 初始版本
  - 基础实体定义
  - 核心功能实现
  - 基础测试用例

### 待办事项
- [ ] 添加更多单元测试
- [ ] 完善文档注释
- [ ] 优化数据结构
- [ ] 添加性能测试

## 注意事项
1. 所有实体类都必须继承自 `ModelBaseEntity`
2. 注意处理好租户隔离
3. 注意处理好版本控制
4. 遵循阿里巴巴Java开发规范
5. 保持代码注释的完整性
