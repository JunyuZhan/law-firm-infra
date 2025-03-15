# 客户管理数据模型模块

本模块提供律所客户管理相关的数据模型定义，包括实体类、数据传输对象(DTO)、视图对象(VO)和Mapper接口等，是客户管理功能的基础数据层。

## 模块结构

```
law-firm-model/client-model/
├── src/main/java/com/lawfirm/model/client/
│   ├── dto/                          # 数据传输对象
│   │   ├── client/                   # 客户相关DTO
│   │   │   ├── ClientCreateDTO.java  # 客户创建DTO
│   │   │   ├── ClientQueryDTO.java   # 客户查询DTO
│   │   │   └── ClientUpdateDTO.java  # 客户更新DTO
│   │   │
│   │   └── contact/                  # 联系人相关DTO
│   │       ├── ContactCreateDTO.java # 联系人创建DTO
│   │       └── ContactQueryDTO.java  # 联系人查询DTO
│   │
│   ├── entity/                       # 实体类
│   │   ├── base/                     # 基础实体
│   │   │   ├── Client.java           # 客户基础实体
│   │   │   └── ClientRelation.java   # 客户关系基础实体
│   │   │
│   │   ├── business/                 # 业务实体
│   │   │   ├── CaseParty.java        # 案件相关方实体
│   │   │   └── ContractParty.java    # 合同相关方实体
│   │   │
│   │   ├── common/                   # 通用实体
│   │   │   ├── ClientAddress.java    # 客户地址实体
│   │   │   ├── ClientCategory.java   # 客户分类实体
│   │   │   ├── ClientContact.java    # 客户联系人实体
│   │   │   └── ClientTag.java        # 客户标签实体
│   │   │
│   │   ├── follow/                   # 跟进相关实体
│   │   │   └── ClientFollowUp.java   # 客户跟进记录实体
│   │   │
│   │   └── relation/                 # 关联关系实体
│   │       └── ClientTagRelation.java # 客户标签关联实体
│   │
│   ├── enums/                        # 枚举类
│   │   ├── ClientLevelEnum.java      # 客户等级枚举
│   │   ├── ClientSourceEnum.java     # 客户来源枚举
│   │   └── ClientTypeEnum.java       # 客户类型枚举
│   │
│   ├── mapper/                       # 数据访问接口
│   │   ├── AddressMapper.java        # 地址Mapper接口
│   │   ├── CategoryMapper.java       # 分类Mapper接口
│   │   ├── ClientMapper.java         # 客户Mapper接口
│   │   ├── ContactMapper.java        # 联系人Mapper接口
│   │   ├── FollowUpMapper.java       # 跟进记录Mapper接口
│   │   ├── RelationMapper.java       # 客户关系Mapper接口
│   │   ├── TagMapper.java            # 标签Mapper接口
│   │   └── TagRelationMapper.java    # 标签关联Mapper接口
│   │
│   ├── service/                      # 服务接口
│   │   ├── ClientService.java        # 客户服务接口
│   │   └── ContactService.java       # 联系人服务接口
│   │
│   └── vo/                           # 视图对象
│       ├── ClientVO.java             # 客户视图对象
│       └── ContactVO.java            # 联系人视图对象
```

## 主要实体类

### 基础实体

#### Client
客户基本信息实体，包含客户的基本属性如名称、类型、等级、联系方式等。是客户管理模块的核心实体。

#### ClientRelation
客户关系基础实体，用于描述客户之间的关系或客户与业务之间的关系。是派生关系实体的基类。

### 业务实体

#### CaseParty
案件相关方实体，继承自ClientRelation，用于描述客户与案件的关系，如原告、被告、第三人等。

#### ContractParty
合同相关方实体，继承自ClientRelation，用于描述客户与合同的关系，如甲方、乙方等。

### 通用实体

#### ClientAddress
客户地址实体，存储客户的地址信息，包括地址类型（注册地址、办公地址、通讯地址等）和详细地址。

#### ClientCategory
客户分类实体，用于对客户进行分类管理，支持多级分类结构。

#### ClientContact
客户联系人实体，存储客户的联系人信息，一个客户可以有多个联系人。

#### ClientTag
客户标签实体，用于对客户进行标签化管理，支持按标签类型区分。

### 跟进实体

#### ClientFollowUp
客户跟进记录实体，记录与客户的沟通和跟进情况，支持设置提醒和下次跟进计划。

### 关联实体

#### ClientTagRelation
客户标签关联实体，用于实现客户与标签的多对多关系。

## Mapper接口

本模块提供以下Mapper接口用于数据访问：

### ClientMapper
客户数据访问接口，支持基本的增删改查操作。

### ContactMapper
联系人数据访问接口，支持查询客户的联系人、默认联系人等操作。

### AddressMapper
地址数据访问接口，支持查询客户的地址、默认地址等操作。

### CategoryMapper
分类数据访问接口，支持查询子分类等操作。

### RelationMapper
客户关系数据访问接口，支持查询客户的关联关系、业务相关的客户关系等操作。

### TagMapper
标签数据访问接口，支持按类型查询标签、查询客户拥有的标签等操作。

### TagRelationMapper
标签关联数据访问接口，支持管理客户和标签的多对多关系。

### FollowUpMapper
跟进记录数据访问接口，支持查询客户的跟进记录、提醒等操作。

## 服务接口

本模块定义了以下服务接口：

### ClientService
客户服务接口，定义了客户管理的核心业务方法，如创建客户、更新客户、删除客户、查询客户等。

### ContactService
联系人服务接口，定义了联系人管理的业务方法，如创建联系人、更新联系人、设置默认联系人等。

## 使用示例

### 实体类使用

```java
// 创建一个客户实体
Client client = new Client()
    .setClientName("示例客户")
    .setClientType(ClientTypeEnum.ENTERPRISE.getCode())
    .setClientLevel(ClientLevelEnum.NORMAL.getCode())
    .setClientSource(ClientSourceEnum.REFERRAL.getCode())
    .setPhone("13800138000")
    .setEmail("example@lawfirm.com");

// 创建一个联系人实体
ClientContact contact = new ClientContact()
    .setClientId(client.getId())
    .setName("张三")
    .setPhone("13900139000")
    .setPosition("法务总监")
    .setIsDefault(1);
```

### DTO使用

```java
// 创建客户的DTO
ClientCreateDTO createDTO = new ClientCreateDTO();
createDTO.setClientName("示例客户");
createDTO.setClientType(ClientTypeEnum.ENTERPRISE.getCode());
createDTO.setPhone("13800138000");
// ... 设置其他属性

// 查询客户的DTO
ClientQueryDTO queryDTO = new ClientQueryDTO();
queryDTO.setClientName("示例");
queryDTO.setClientLevel(ClientLevelEnum.VIP.getCode());
```

### 服务接口使用

```java
@Resource
private ClientService clientService;

@Resource
private ContactService contactService;

// 创建客户
Long clientId = clientService.createClient(createDTO);

// 获取客户详情
ClientVO client = clientService.getClient(clientId);

// 获取客户的联系人
List<ContactVO> contacts = contactService.listClientContacts(clientId);
```

## 数据库表结构参考

本模块对应的数据库表结构如下：

### 客户表(client_info)
| 字段名 | 类型 | 描述 |
|--------|------|------|
| id | BIGINT | 主键ID |
| client_no | VARCHAR(30) | 客户编号 |
| client_name | VARCHAR(100) | 客户名称 |
| client_type | INT | 客户类型(1-个人,2-企业) |
| client_level | INT | 客户等级(1-普通,2-VIP,3-核心) |
| client_source | INT | 客户来源 |
| industry | VARCHAR(50) | 所属行业 |
| scale | VARCHAR(50) | 企业规模 |
| phone | VARCHAR(20) | 联系电话 |
| email | VARCHAR(100) | 电子邮箱 |
| manager_id | BIGINT | 客户负责人ID |
| status | INT | 状态(0-正常,1-禁用) |
| id_type | VARCHAR(20) | 证件类型 |
| id_number | VARCHAR(50) | 证件号码 |
| credit_level | VARCHAR(10) | 信用等级 |
| legal_representative | VARCHAR(50) | 法定代表人 |
| unified_social_credit_code | VARCHAR(50) | 统一社会信用代码 |
| create_by | BIGINT | 创建人 |
| create_time | DATETIME | 创建时间 |
| update_by | BIGINT | 更新人 |
| update_time | DATETIME | 更新时间 |
| is_deleted | TINYINT | 是否删除 |

### 其他主要表
- client_contact：客户联系人表
- client_address：客户地址表
- client_category：客户分类表
- client_tag：客户标签表
- client_tag_relation：客户标签关联表
- client_follow_up：客户跟进记录表
- client_relation：客户关系表 