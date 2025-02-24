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
- 实体类：XxxEntity
- DTO：XxxDTO
- VO：XxxVO
- 枚举：XxxEnum
- 服务接口：XxxService
- 查询对象：XxxQuery
- 结果对象：XxxResult

### 2. 包结构规范
- 实体类放在entity包
- DTO放在dto包
- VO放在vo包
- 枚举放在enums包
- 服务接口放在service包
- 查询对象放在query包
- 结果对象放在result包

### 3. 定义规范
- 所有实体类都要继承common-data中的BaseEntity或base-model中的ModelBaseEntity
- 所有枚举类都要实现相应的接口
- 所有服务接口都要有清晰的注释
- 所有DTO、VO都要有字段说明
- 所有查询对象都要实现分页接口
- 所有结果对象都要实现序列化接口

### 4. 注释规范
- 类注释：说明类的用途、作者、版本
- 方法注释：说明方法的功能、参数、返回值
- 字段注释：说明字段的含义、格式、约束

### 5. 代码规范
- 遵循阿里巴巴Java开发手册
- 使用Lombok简化代码
- 合理使用设计模式
- 保持代码简洁清晰

## 版本说明

### 1.0.0 (2024-02-21)
- 初始版本发布
- 完整的模块化架构
- 基础定义支持
- 完整的文档支持

### 1.1.0 (计划中)
- 增强多租户支持
- 完善审计功能
- 优化数据权限
- 支持更多业务场景
- 实施模块整合重构
  - 统一日志管理
  - 统一文档中心
  - 统一支付体系
  - 统一消息系统
  - 统一工作流
  - 统一存储管理
- 新增客户管理模块
- 新增知识管理模块
- 完善案件管理核心功能
- 增强合同管理模块
- 优化财务管理模块
- 基础设施增强
  - 统一树形结构支持
  - 统一缓存管理
  - 统一验证框架
  - 服务接口标准化
  - 跨模块通信规范
  - 数据权限模型优化
- 性能优化
  - 优化日志性能
  - 优化缓存策略
  - 优化查询性能 