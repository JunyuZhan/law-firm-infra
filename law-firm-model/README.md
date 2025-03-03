# 数据模型层 (Model Layer)

## 模块说明
数据模型层是律师事务所管理系统的领域模型层，负责定义系统中所有的实体类、接口、服务定义、枚举类、DTO、VO等定义类。采用模块化设计，通过合理的职责划分确保代码的高内聚低耦合。

## 设计原则

### 1. 分层原则
- 基础设施层（common-data）：提供最基础的实体类定义
- 基础模型层（base-model）：提供业务通用的模型定义
- 业务模型层（各业务模块）：提供具体业务领域的模型定义

### 2. 职责分离原则
- 每个模块只负责自己领域内的模型定义
- 通用功能统一放在base-model中
- 避免跨领域的职责混合
- 合理划分模块边界

### 3. 依赖原则
- 遵循依赖倒置原则
- 上层模块不依赖下层模块
- 都依赖抽象而不是具体实现
- 避免循环依赖

### 4. 扩展原则
- 对扩展开放，对修改关闭
- 预留扩展点
- 使用接口定义而不是具体实现
- 支持插件式扩展

### 5. 模块整合原则
- 相同领域的功能应该集中管理
- 避免跨模块的重复定义
- 使用基类抽象共同特征
- 通过继承实现差异化

## 核心概念

### 1. 实体（Entity）
- 继承关系：BaseEntity（common-data）-> ModelBaseEntity（base-model）-> 具体业务实体
- 包含基础字段：id、createTime、createBy、updateTime、updateBy、remark
- 支持多租户：tenantId
- 支持版本控制：version
- 支持状态管理：status
- 支持排序：sort

### 2. 数据传输对象（DTO）
- 用于层间数据传输
- 包含数据验证
- 支持对象转换
- 避免实体直接暴露

### 3. 视图对象（VO）
- 用于前端展示
- 数据脱敏处理
- 按需返回字段
- 优化传输效率

### 4. 查询对象（Query）
- 支持动态查询
- 支持分页查询
- 支持排序查询
- 支持条件组合

### 5. 枚举定义（Enum）
- 统一状态定义
- 统一类型定义
- 支持国际化
- 支持扩展属性

## 模块结构

### 1. 基础模型模块 (base-model)
```
base-model/
├── entity/           # 基础实体类定义
│   ├── ModelBaseEntity.java     # 业务模型基类
│   ├── TreeEntity.java         # 树形实体基类
│   └── TenantEntity.java       # 租户基类
├── tenant/           # 租户定义
│   ├── TenantMetadata.java     # 租户元数据
│   └── TenantConfig.java       # 租户配置
├── support/          # 基础支持
│   ├── tree/        # 树形结构
│   │   ├── TreeNode.java       # 树节点接口
│   │   └── TreeBuilder.java    # 树构建器
│   ├── cache/       # 缓存
│   │   ├── CacheKey.java       # 缓存键
│   │   └── CacheManager.java   # 缓存管理
│   └── validator/   # 验证器
│       ├── ValidatorChain.java # 验证链
│       └── Validators.java     # 验证器集
├── geo/             # 地理位置定义
│   ├── Address.java           # 地址实体
│   └── Region.java            # 区域实体
├── enums/           # 通用枚举定义
├── dto/             # 通用DTO对象
├── vo/              # 通用VO对象
├── query/           # 通用查询对象
├── service/         # 通用服务接口定义
├── result/          # 通用结果对象
└── constants/       # 通用常量定义
```

### 2. 日志模块 (log-model)
```
log-model/
├── entity/           # 日志实体定义
│   ├── base/         # 基础日志
│   │   ├── BaseLog.java       # 日志基类
│   │   └── AuditableLog.java  # 可审计日志
│   ├── system/       # 系统日志
│   │   ├── LoginLog.java      # 登录日志
│   │   └── SystemLog.java     # 系统日志
│   ├── audit/        # 审计日志
│   │   ├── AuditLog.java      # 审计日志
│   │   └── AuditRecord.java   # 审计记录
│   └── business/     # 业务日志
│       ├── OperationLog.java  # 操作日志
│       └── BusinessLog.java   # 业务日志
├── enums/            # 日志枚举定义
│   ├── LogTypeEnum.java    # 日志类型
│   ├── LogLevelEnum.java   # 日志级别
│   ├── OperateTypeEnum.java # 操作类型
│   └── BusinessTypeEnum.java # 业务类型
├── dto/              # 日志传输对象
│   ├── LogCreateDTO.java   # 日志创建
│   ├── LogQueryDTO.java    # 日志查询
│   └── LogExportDTO.java   # 日志导出
├── vo/               # 日志视图对象
│   ├── LogVO.java          # 日志视图
│   └── LogStatVO.java      # 日志统计
├── service/          # 日志服务定义
│   ├── LogService.java     # 日志服务
│   ├── LogAnalysisService.java # 日志分析
│   └── LogExportService.java   # 日志导出
├── aspect/           # 日志切面
│   ├── LogAspect.java      # 日志切面
│   └── LogPointcut.java    # 切点定义
├── event/            # 日志事件
│   ├── LogEvent.java       # 日志事件
│   └── LogEventListener.java # 事件监听
└── config/           # 日志配置
    ├── LogConfig.java      # 日志配置
    └── LogProperties.java  # 配置属性
```

### 3. 认证授权模块 (auth-model)
```
auth-model/
├── entity/       
│   ├── User.java           # 用户实体
│   ├── Role.java          # 角色实体
│   ├── Permission.java    # 权限实体
│   ├── UserRole.java      # 用户角色关联
│   ├── RolePermission.java # 角色权限关联
│   ├── Department.java    # 部门实体
│   ├── Position.java      # 职位实体
│   ├── UserGroup.java     # 用户组实体
│   └── LoginHistory.java  # 登录历史
├── enums/        
│   ├── UserTypeEnum.java   # 用户类型
│   ├── UserStatusEnum.java # 用户状态
│   ├── PermissionTypeEnum.java # 权限类型
│   ├── DepartmentTypeEnum.java # 部门类型
│   └── PositionLevelEnum.java  # 职位级别
├── dto/          
│   ├── auth/
│   │   ├── LoginDTO.java      # 登录请求
│   │   └── TokenDTO.java      # 令牌信息
│   ├── user/
│   │   ├── UserCreateDTO.java # 用户创建
│   │   ├── UserUpdateDTO.java # 用户更新
│   │   └── UserQueryDTO.java  # 用户查询
│   └── role/
│       ├── RoleCreateDTO.java # 角色创建
│       └── RoleUpdateDTO.java # 角色更新
├── vo/           
│   ├── UserVO.java        # 用户视图
│   ├── RoleVO.java        # 角色视图
│   └── PermissionVO.java  # 权限视图
└── service/      
    ├── UserService.java   # 用户服务
    ├── RoleService.java   # 角色服务
    └── PermissionService.java # 权限服务
```

### 4. 系统管理模块 (system-model)
```
system-model/
├── entity/       
│   ├── SysConfig.java     # 系统配置
│   ├── SysDict.java       # 系统字典
│   └── SysDictItem.java   # 系统字典项
├── enums/        
│   ├── ConfigTypeEnum.java # 配置类型
│   └── DictTypeEnum.java   # 字典类型
├── dto/          
│   ├── config/
│   │   ├── ConfigCreateDTO.java # 配置创建
│   │   └── ConfigUpdateDTO.java # 配置更新
│   └── dict/
│       ├── DictCreateDTO.java   # 字典创建
│       ├── DictUpdateDTO.java   # 字典更新
│       ├── DictItemCreateDTO.java # 字典项创建
│       └── DictItemUpdateDTO.java # 字典项更新
├── vo/           
│   ├── ConfigVO.java      # 配置视图
│   ├── DictVO.java       # 字典视图
│   └── DictItemVO.java   # 字典项视图
└── service/      
    ├── ConfigService.java # 配置服务
    └── DictService.java   # 字典服务
```

### 5. 文档管理模块 (document-model)
```
document-model/
├── entity/
│   ├── base/              # 基础文档
│   │   ├── BaseDocument.java     # 文档基类
│   │   └── DocumentInfo.java     # 文档信息
│   ├── business/          # 业务文档
│   │   ├── CaseDocument.java     # 案件文档
│   │   ├── ContractDocument.java # 合同文档
│   │   └── ArticleDocument.java  # 知识文章
│   └── system/           # 系统文档
│       └── TemplateDocument.java # 模板文档
├── storage/             # 存储管理
│   ├── StorageConfig.java       # 存储配置
│   └── StorageProvider.java     # 存储提供者
├── enums/
│   ├── DocumentTypeEnum.java  # 文档类型
│   ├── DocumentStatusEnum.java # 文档状态
│   └── TemplateTypeEnum.java  # 模板类型
├── dto/
│   ├── document/
│   │   ├── DocumentCreateDTO.java # 文档创建
│   │   ├── DocumentUpdateDTO.java # 文档更新
│   │   └── DocumentQueryDTO.java  # 文档查询
│   └── template/
│       ├── TemplateCreateDTO.java # 模板创建
│       └── TemplateUpdateDTO.java # 模板更新
├── vo/
│   ├── DocumentVO.java     # 文档视图
│   └── TemplateVO.java     # 模板视图
└── service/
    ├── DocumentService.java # 文档服务
    └── TemplateService.java # 模板服务
```

### 6. 客户管理模块 (client-model)
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

### 7. 工作流模块 (workflow-model)
```
workflow-model/
├── entity/
│   ├── base/             # 基础流程
│   │   ├── BaseProcess.java      # 流程基类
│   │   └── ProcessTask.java      # 任务基类
│   └── common/           # 通用流程
│       └── CommonProcess.java    # 通用流程
├── enums/
│   ├── ProcessTypeEnum.java   # 流程类型
│   ├── ProcessStatusEnum.java # 流程状态
│   └── TaskTypeEnum.java      # 任务类型
├── dto/
│   ├── process/
│   │   ├── ProcessCreateDTO.java # 流程创建
│   │   ├── ProcessUpdateDTO.java # 流程更新
│   │   └── ProcessQueryDTO.java  # 流程查询
│   └── task/
│       ├── TaskCreateDTO.java    # 任务创建
│       └── TaskQueryDTO.java     # 任务查询
├── vo/
│   ├── ProcessVO.java      # 流程视图
│   └── TaskVO.java         # 任务视图
└── service/
    ├── ProcessService.java  # 流程服务
    └── TaskService.java     # 任务服务
```

### 7. 消息管理模块 (message-model)
```
message-model/
├── entity/
│   ├── base/             # 基础消息
│   │   ├── BaseMessage.java      # 消息基类
│   │   └── BaseNotify.java       # 通知基类
│   ├── system/           # 系统消息
│   │   └── SystemMessage.java    # 系统消息
│   └── business/         # 业务消息
│       ├── CaseMessage.java      # 案件消息
│       └── ContractMessage.java  # 合同消息
├── enums/
│   ├── MessageTypeEnum.java    # 消息类型
│   ├── MessageStatusEnum.java  # 消息状态
│   └── NotifyChannelEnum.java  # 通知渠道
├── dto/
│   ├── message/
│   │   ├── MessageCreateDTO.java # 消息创建
│   │   └── MessageQueryDTO.java  # 消息查询
│   └── notify/
│       ├── NotifyCreateDTO.java  # 通知创建
│       └── NotifyQueryDTO.java   # 通知查询
├── vo/
│   ├── MessageVO.java      # 消息视图
│   └── NotifyVO.java       # 通知视图
└── service/
    ├── MessageService.java  # 消息服务
    └── NotifyService.java   # 通知服务
```

### 8. 存储模块 (storage-model)
```
storage-model/
├── entity/
│   ├── BaseStorage.java     # 存储基类
│   ├── file/              # 文件存储
│   │   ├── FileObject.java    # 文件对象
│   │   └── FileInfo.java      # 文件信息
│   └── bucket/           # 存储桶
│       └── StorageBucket.java # 存储桶
├── enums/
│   ├── StorageTypeEnum.java   # 存储类型
│   ├── FileTypeEnum.java      # 文件类型
│   └── BucketTypeEnum.java    # 存储桶类型
├── dto/
│   ├── file/
│   │   ├── FileUploadDTO.java  # 文件上传
│   │   └── FileQueryDTO.java   # 文件查询
│   └── bucket/
│       ├── BucketCreateDTO.java # 存储桶创建
│       └── BucketQueryDTO.java  # 存储桶查询
├── vo/
│   ├── FileVO.java         # 文件视图
│   └── BucketVO.java       # 存储桶视图
└── service/
    ├── FileService.java    # 文件服务
    └── BucketService.java  # 存储桶服务
```

### 9. 搜索模块 (search-model)
```
search-model/
├── entity/       # 实体类
│   ├── SearchIndex.java   # 索引实体
│   ├── SearchDoc.java     # 文档实体
│   └── SearchField.java   # 字段实体
├── enums/        # 枚举类
│   ├── IndexTypeEnum.java  # 索引类型
│   ├── FieldTypeEnum.java  # 字段类型
│   └── SearchTypeEnum.java # 搜索类型
├── dto/          # 数据传输对象
│   ├── index/    # 索引相关
│   │   ├── IndexCreateDTO.java # 索引创建
│   │   └── IndexQueryDTO.java  # 索引查询
│   └── search/   # 搜索相关
│       ├── SearchRequestDTO.java # 搜索请求
│       └── SearchResultDTO.java  # 搜索结果
├── vo/           # 视图对象
│   ├── IndexVO.java       # 索引视图（包含索引配置和统计信息）
│   ├── FieldVO.java      # 字段视图（描述索引字段的属性）
│   └── SearchVO.java      # 搜索视图（包含搜索结果和统计）
└── service/      # 服务接口
    ├── IndexService.java  # 索引服务（继承BaseService）
    └── SearchService.java # 搜索服务（继承BaseService）
```


### 10. 知识管理模块 (knowledge-model)
```
knowledge-model/
├── entity/       
│   ├── Article.java        # 文章
│   ├── Category.java       # 分类
│   ├── Tag.java           # 标签
│   ├── Comment.java       # 评论
│   └── Attachment.java    # 附件
├── enums/
│   ├── ArticleTypeEnum.java   # 文章类型
│   ├── CategoryTypeEnum.java  # 分类类型
│   └── ContentTypeEnum.java   # 内容类型
├── dto/
│   ├── article/
│   │   ├── ArticleCreateDTO.java # 文章创建
│   │   ├── ArticleUpdateDTO.java # 文章更新
│   │   └── ArticleQueryDTO.java  # 文章查询
│   └── category/
│       ├── CategoryCreateDTO.java # 分类创建
│       └── CategoryQueryDTO.java  # 分类查询
├── vo/
│   ├── ArticleVO.java     # 文章视图
│   └── CategoryVO.java    # 分类视图
└── service/
    ├── ArticleService.java # 文章服务
    └── CategoryService.java # 分类服务
```

## 依赖关系
```
common-data
    ↓
base-model
    ↓
log-model, auth-model, system-model  # 基础服务层
    ↓
client-model, document-model  # 通用业务层
    ↓
case-model, contract-model    # 核心业务层
    ↓
workflow-model, message-model, storage-model, search-model, knowledge-model  # 支撑服务层
```

## 开发规范

### 1. 命名规范
- 实体类：使用名词单数，如User、Role
- DTO类：以DTO结尾，如UserDTO、RoleDTO
- VO类：以VO结尾，如UserVO、RoleVO
- 枚举类：以Enum结尾，如StatusEnum、TypeEnum
- 服务接口：以Service结尾，如UserService、RoleService

### 2. 编码规范
- 遵循阿里巴巴Java开发手册规范
- 使用Lombok简化代码
- 使用JavaDoc注释
- 使用统一的代码格式化
- 保持代码整洁和可读性

### 3. 序列化规范
- 所有可序列化的类（Entity、DTO、VO等）必须定义serialVersionUID字段
```java
private static final long serialVersionUID = 1L;
```
- 对不需要序列化的字段使用transient关键字标记
```java
private transient String tempField;
```
- 确保序列化兼容性，避免在生产环境中出现不可预期的问题
- 注意在继承关系中，子类也需要定义自己的serialVersionUID

### 4. 文档规范
- 每个模块必须有README.md文件，说明模块用途、结构和用法
- 关键类和方法必须有JavaDoc注释
- 复杂的业务逻辑需要有流程说明

### 5. 测试规范
- 所有模型类应有对应的单元测试
- 确保测试覆盖率达到预期标准
- 使用mock框架测试依赖关系

## 版本管理
- 遵循语义化版本规范 (SemVer)
- 主版本号：不兼容的API变更
- 次版本号：向下兼容的功能性新增
- 修订版本号：向下兼容的问题修正
- 所有版本依赖由law-firm-dependencies统一管理

## 数据模型层子模块

### 1. 知识模型 (knowledge-model)
- 负责知识相关的数据模型定义，包括分类和文章的 DTO 和 VO。

### 2. 文档模型 (document-model)
- 负责文档相关的数据模型定义，包括文档的创建、更新和查询。

### 3. 合同模型 (contract-model)
- 负责合同相关的数据模型定义，包括合同的创建、更新和查询。

### 4. 基础模型 (base-model)
- 提供通用的基础数据模型定义，包含基础实体、DTO、VO 和常量。

### 5. 客户模型 (client-model)
- 负责客户相关的数据模型定义，包括客户的创建、更新和查询。

### 6. 案件模型 (case-model)
- 负责案件相关的数据模型定义，包括案件的创建、更新和查询。

### 7. 认证模型 (auth-model)
- 负责用户认证和授权相关的数据模型定义，包括用户、角色和权限。

### 8. 人事模型 (personnel-model)
- 负责员工和人事相关的数据模型定义，包括员工的创建、更新和查询。

### 9. 组织模型 (organization-model)
- 负责组织结构相关的数据模型定义，包括部门和职位的创建、更新和查询。

### 10. 财务模型 (finance-model)
- 负责财务相关的数据模型定义，包括财务记录的创建、更新和查询。

### 11. 系统模型 (system-model)
- 负责系统配置和字典相关的数据模型定义，包括系统配置的创建、更新和查询。

### 12. 日志模型 (log-model)
- 负责日志记录和管理相关的数据模型定义，包括日志的创建、更新和查询。

### 13. 搜索模型 (search-model)
- 负责搜索功能相关的数据模型定义，包括搜索条件和结果的定义。

### 14. 存储模型 (storage-model)
- 负责文件存储和管理相关的数据模型定义，包括存储配置和提供者的定义。

### 15. 消息模型 (message-model)
- 负责消息通知和管理相关的数据模型定义，包括消息的创建、更新和查询。

### 16. 工作流模型 (workflow-model)
- 负责工作流相关的数据模型定义，包括工作流的创建、更新和查询。

### 17. AI模型 (ai-model)
- 负责人工智能相关的数据模型定义，包括AI服务配置、AI模型参数和结果的定义。

## TODO事项列表

### 技术框架迁移

#### JPA迁移到MyBatis Plus
- [x] auth-model模块实体类迁移
  - [x] User
  - [x] Role
  - [x] Permission
  - [x] Department
  - [x] UserRole
  - [x] RolePermission
  - [x] UserGroup
  - [x] Position
  - [x] LoginHistory
- [x] system-model模块实体类迁移
  - [x] SysConfig
  - [x] SysDict
  - [x] SysDictItem
- [x] document-model模块实体类迁移
  - [x] BaseDocument
  - [x] DocumentInfo
  - [x] TemplateDocument
  - [x] CaseDocument
  - [x] ArticleDocument
  - [x] ContractDocument
- [x] storage-model模块实体类迁移
- [x] log-model模块实体类迁移
- [x] base-model模块实体类迁移
  - [x] ModelBaseEntity
  - [x] TenantEntity
  - [x] TreeEntity
- [x] client-model模块实体类迁移 (2024-04-28)
- [x] case-model模块实体类迁移
- [x] personnel-model模块实体类迁移 (2024-04-28)
- [x] organization-model模块实体类迁移 (2024-04-28)
- [x] finance-model模块实体类迁移 (2024-04-28)
- [x] search-model模块实体类迁移 (2024-04-28)
- [x] message-model模块实体类迁移 (2024-04-28)
- [x] workflow-model模块实体类迁移 (2024-04-28)
- [x] knowledge-model模块实体类迁移 (2024-04-28)
- [x] contract-model模块实体类迁移
- [x] ai-model模块实体类迁移 (没有需要迁移的代码)
- [ ] 处理Lombok依赖问题，解决IDE中的"import lombok cannot be resolved"及"Data cannot be resolved to a type"等错误
- [ ] 代码审查和测试，确保迁移后功能正常

#### 迁移原因说明
原系统同时使用了JPA和MyBatis Plus两种ORM框架，导致代码混乱且可能产生冲突。此次迁移目的是统一使用MyBatis Plus作为唯一的ORM框架，提高开发效率和系统性能，降低维护成本。

#### 迁移规则
1. 删除所有JPA相关导入（jakarta.persistence.*）
2. 删除@Entity、@Table等JPA注解
3. 保留@TableName和@TableField等MyBatis Plus注解
4. 保留Lombok相关注解（@Data、@EqualsAndHashCode等）
5. 确保实体类映射关系与数据库表保持一致 