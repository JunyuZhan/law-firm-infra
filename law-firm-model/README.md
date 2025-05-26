[返回主项目说明](../README.md)

# 数据模型层 (Model Layer)

## 模块说明
数据模型层是系统的数据模型定义层,包含所有业务实体、数据传输对象、视图对象等。该层不包含任何业务逻辑,只负责数据的定义和转换。

## 子模块文档索引

- [ai-model](./ai-model/README.md)
- [analysis-model](./analysis-model/README.md)
- [archive-model](./archive-model/README.md)
- [auth-model](./auth-model/README.md)
- [base-model](./base-model/README.md)
- [case-model](./case-model/README.md)
- [client-model](./client-model/README.md)
- [contract-model](./contract-model/README.md)
- [document-model](./document-model/README.md)
- [evidence-model](./evidence-model/README.md)
- [finance-model](./finance-model/README.md)
- [knowledge-model](./knowledge-model/README.md)
- [log-model](./log-model/README.md)
- [message-model](./message-model/README.md)
- [organization-model](./organization-model/README.md)
- [personnel-model](./personnel-model/README.md)
- [schedule-model](./schedule-model/README.md)
- [search-model](./search-model/README.md)
- [storage-model](./storage-model/README.md)
- [system-model](./system-model/README.md)
- [task-model](./task-model/README.md)
- [workflow-model](./workflow-model/README.md)

## 模块列表

### 1. 基础模块
- **base-model**: 基础模型,提供基础实体类、DTO、VO等,依赖于common-core、common-util、common-data、common-web和common-security
- **system-model**: 系统模型,包含系统配置、系统参数等（已完整实现）
- **log-model**: 日志模型,包含日志记录、审计日志等（已完整实现）

### 2. 业务模块
- **case-model**: 案件相关模型（已完整实现）
  - 案件基本信息管理
  - 案件状态流转
  - 案件团队管理
  - 案件文档管理
  - 案件费用管理
  - 案件进展管理
- **client-model**: 客户相关模型（已完整实现）
  - 客户基本信息管理
  - 客户分类管理
  - 客户关系管理
- **contract-model**: 合同相关模型（已完整实现）
  - 合同基本信息管理
  - 合同模板管理
  - 合同审批流程
- **document-model**: 文档相关模型（已完整实现）
  - 文档基本信息管理
  - 文档版本控制
  - 文档存储管理
- **finance-model**: 财务相关模型（已完整实现）
  - 费用管理
  - 收入管理
  - 财务报表
- **organization-model**: 组织相关模型（已完整实现）
  - 组织架构管理
  - 部门管理
  - 职位管理
  - 团队管理
- **personnel-model**: 人员相关模型（已完整实现）
  - 人员基本信息
  - 员工管理
  - 律师管理
  - 行政人员管理

### 3. 功能模块
- **auth-model**: 认证相关模型（已完整实现）
  - 用户认证
  - 角色权限
  - 安全策略
- **workflow-model**: 工作流相关模型（已完整实现）
  - 流程定义
  - 流程实例
  - 任务管理
- **storage-model**: 存储相关模型（已完整实现）
  - 文件存储
  - 对象存储
  - 存储策略
- **search-model**: 搜索相关模型（已完整实现）
  - 全文检索
  - 高级搜索
  - 搜索结果处理
- **message-model**: 消息相关模型（已完整实现）
  - 消息发送
  - 消息接收
  - 消息模板
- **knowledge-model**: 知识相关模型（已完整实现）
  - 知识库管理
  - 知识分类
  - 知识检索
- **ai-model**: AI相关模型（已完整实现）
  - AI模型管理
  - AI服务集成
  - AI结果处理
- **schedule-model**: 调度相关模型（已完整实现）
  - 调度任务管理
  - 调度规则定义
  - 调度执行控制
- **task-model**: 任务相关模型（已完整实现）
  - 任务定义
  - 任务分配
  - 任务执行

## 开发规范

### 1. 命名规范
- 类名: 使用大驼峰命名法,如`CaseEntity`
- 方法名: 使用小驼峰命名法,如`getCaseInfo`
- 变量名: 使用小驼峰命名法,如`caseId`
- 常量名: 使用全大写下划线分隔,如`CASE_STATUS`
- 包名: 使用全小写点分隔,如`com.lawfirm.case.model`

### 2. 目录规范
每个模块必须包含以下目录:
```
module/
├── entity/          # 实体类
├── dto/             # 数据传输对象
├── vo/              # 视图对象
├── enums/           # 枚举类
├── constants/       # 常量类
├── service/         # 服务接口
├── mapper/          # 数据访问接口
├── event/           # 事件定义
├── config/          # 配置类
├── query/           # 查询对象
└── result/          # 结果对象
```

### 3. 类规范
- 实体类: 以Entity结尾,如`CaseEntity`
- DTO类: 以DTO结尾,如`CaseDTO`
- VO类: 以VO结尾,如`CaseVO`
- 枚举类: 以Enum结尾,如`CaseStatusEnum`
- 常量类: 以Constants结尾,如`CaseConstants`
- 查询类: 以Query结尾,如`CaseQuery`
- 结果类: 以Result结尾,如`CaseResult`
- 服务接口: 以Service结尾,如`CaseService`
- 数据访问接口: 以Mapper结尾,如`CaseMapper`
- 事件类: 以Event结尾,如`CaseEvent`

### 4. 字段规范
- 实体类字段:
  - id: 主键
  - createTime: 创建时间
  - updateTime: 更新时间
  - createBy: 创建人
  - updateBy: 更新人
  - deleted: 是否删除
  - version: 版本号
  - tenantId: 租户ID

- DTO字段:
  - 不包含实体类基础字段
  - 只包含业务字段
  - 字段名与实体类保持一致

- VO字段:
  - 不包含实体类基础字段
  - 只包含展示字段
  - 字段名与实体类保持一致

### 5. 注释规范
- 类注释:
```java
/**
 * 案件实体类
 *
 * @author author
 * @date 2024-03-18
 */
```

- 字段注释:
```java
/**
 * 案件ID
 */
private Long id;

/**
 * 案件名称
 */
private String name;
```

### 6. 依赖规范
- 所有模块依赖于base-model
- 不允许模块间的横向依赖
- 只允许依赖基础设施层的common模块
- 不允许依赖业务实现模块

### 7. 测试规范
- 单元测试覆盖率要求: 80%以上
- 必须包含以下测试:
  - 实体类测试
  - DTO测试
  - VO测试
  - 枚举类测试
  - 常量类测试
  - 查询类测试
  - 结果类测试
  - 服务接口测试
  - 数据访问接口测试
  - 事件处理测试

## 最佳实践
1. 保持模型的纯粹性，不包含业务逻辑
2. 遵循统一的命名和目录规范
3. 合理使用继承和组合
4. 保持接口的稳定性
5. 注意版本兼容性
6. 使用事件驱动模式处理跨模块交互
7. 合理使用缓存提高性能
8. 注意数据一致性和事务处理

## 更新日志
- 2024-03-18: 初始版本发布
- 2024-03-19: 完成案件管理模块实现
- 2024-03-20: 完成认证授权模块实现
- 2024-03-21: 完成人事管理和组织架构模块实现
- 2024-03-22: 完成客户和合同模块实现
- 2024-03-23: 完成文档和财务模块实现
- 2024-03-24: 完成工作流和存储模块实现
- 2024-03-25: 完成搜索和消息模块实现
- 2024-03-26: 完成知识和AI模块实现
- 2024-04-05: 完成调度模块实现
- 2024-04-07: 完成任务模块实现 