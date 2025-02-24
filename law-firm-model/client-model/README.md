# 客户管理模块 (Client Model)

## 模块说明
客户管理模块是律师事务所管理系统的客户数据模型定义模块，负责定义系统中所有客户相关的实体类、数据传输对象、视图对象和服务接口。该模块是系统客户管理的数据基础。

## 目录结构
```
client-model/
├── entity/       
│   ├── base/             # 基础定义
│   │   ├── Client.java          # 客户基类
│   │   └── ClientRelation.java  # 关系基类
│   ├── business/         # 业务关系
│   │   ├── CaseParty.java       # 案件当事人
│   │   └── ContractParty.java   # 合同当事人
│   └── common/          # 通用定义
│       ├── ClientContact.java    # 联系人
│       ├── ClientAddress.java    # 客户地址
│       └── ClientCategory.java   # 客户分类
├── enums/
│   ├── ClientTypeEnum.java    # 客户类型（个人/企业）
│   ├── ClientLevelEnum.java   # 客户等级
│   ├── ClientSourceEnum.java  # 客户来源
│   └── ContactTypeEnum.java   # 联系人类型
├── dto/
│   ├── client/
│   │   ├── ClientCreateDTO.java  # 客户创建
│   │   ├── ClientUpdateDTO.java  # 客户更新
│   │   └── ClientQueryDTO.java   # 客户查询
│   └── contact/
│       ├── ContactCreateDTO.java  # 联系人创建
│       └── ContactQueryDTO.java   # 联系人查询
├── vo/
│   ├── ClientVO.java       # 客户视图
│   └── ContactVO.java      # 联系人视图
└── service/
    ├── ClientService.java  # 客户服务
    └── ContactService.java # 联系人服务
```

## 核心功能

### 1. 基础客户管理
- 客户基类（Client）
  - 客户编号
  - 客户名称
  - 客户类型
  - 客户等级
  - 客户来源
  - 证件信息
  - 基本信息
  - 联系方式
  - 客户状态
  - 信用等级

- 客户关系（ClientRelation）
  - 关系类型
  - 关联客户
  - 关系描述
  - 生效时间
  - 失效时间
  - 关系状态

### 2. 业务关系管理
- 案件当事人（CaseParty）
  - 案件ID
  - 当事人类型
  - 当事人角色
  - 代理类型
  - 委托时间
  - 代理权限
  - 案件状态

- 合同当事人（ContractParty）
  - 合同ID
  - 当事人类型
  - 签约角色
  - 签约资格
  - 签约时间
  - 履约状态
  - 合同权利
  - 合同义务

### 3. 通用信息管理
- 联系人（ClientContact）
  - 联系人姓名
  - 联系人类型
  - 所属部门
  - 职务职位
  - 联系方式
  - 重要程度
  - 备注说明

- 客户地址（ClientAddress）
  - 地址类型
  - 国家地区
  - 省市区县
  - 详细地址
  - 邮政编码
  - 是否默认
  - 地址标签

- 客户分类（ClientCategory）
  - 分类名称
  - 分类编码
  - 分类层级
  - 上级分类
  - 分类描述
  - 排序权重

## 核心类说明

### 1. 实体类
- Client：客户基类
  - 基本信息
  - 证件信息
  - 联系信息
  - 状态管理

- ClientRelation：关系基类
  - 关系定义
  - 关联管理
  - 状态管理
  - 时效控制

### 2. 数据传输对象
- client/：客户相关DTO
  - ClientCreateDTO：客户创建
  - ClientUpdateDTO：客户更新
  - ClientQueryDTO：客户查询

- contact/：联系人相关DTO
  - ContactCreateDTO：联系人创建
  - ContactQueryDTO：联系人查询

### 3. 视图对象
- ClientVO：客户视图
  - 基本信息
  - 扩展信息
  - 统计信息
  - 关联信息

- ContactVO：联系人视图
  - 基本信息
  - 联系方式
  - 关联信息
  - 状态信息

### 4. 服务接口
- ClientService：客户服务
  - 客户CRUD
  - 客户分类
  - 客户关系
  - 状态管理
  - 信息统计
  - 缓存管理

- ContactService：联系人服务
  - 联系人CRUD
  - 联系人分类
  - 关联管理
  - 状态管理
  - 缓存管理

## 使用示例

### 1. 创建客户
```java
ClientCreateDTO createDTO = new ClientCreateDTO()
    .setClientName("北京某某科技有限公司")
    .setClientType(ClientTypeEnum.ENTERPRISE.getValue())
    .setClientLevel(ClientLevelEnum.VIP.getValue())
    .setClientSource(ClientSourceEnum.RECOMMENDATION.getValue())
    .setLegalRepresentative("张三")
    .setContactPhone("13800138000")
    .setEmail("contact@company.com");

Long clientId = clientService.createClient(createDTO);
```

### 2. 创建联系人
```java
ContactCreateDTO createDTO = new ContactCreateDTO()
    .setClientId(clientId)
    .setContactName("李四")
    .setContactType(ContactTypeEnum.PRIMARY.getValue())
    .setDepartment("法务部")
    .setPosition("法务总监")
    .setMobile("13900139000")
    .setEmail("legal@company.com")
    .setImportance(1);

Long contactId = contactService.createContact(createDTO);
```

## 注意事项
1. 客户编号必须全局唯一
2. 注意客户类型和等级的准确性
3. 及时更新客户状态和关系
4. 重要操作需要记录日志
5. 关注客户信息安全和隐私
6. 确保联系人信息及时更新
7. 注意客户分类的合理性 