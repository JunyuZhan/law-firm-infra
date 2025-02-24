# 案件模型模块 (Case Model)

## 目录结构
```
model/
└── cases/                                     # 案件模型模块
    ├── constants/                             # 常量定义
    │   ├── CaseFieldConstants.java            # 案件字段限制常量
    │   ├── CaseStatusConstants.java           # 案件状态相关常量
    │   ├── CaseMessageConstants.java          # 案件消息提示常量
    │   └── CaseErrorConstants.java            # 案件错误码常量
    ├── dto/                                   # 数据传输对象
    │   ├── base/                             # 基础DTO
    │   │   ├── CaseBaseDTO.java              # 案件基础DTO
    │   │   ├── CaseCreateDTO.java            # 案件创建DTO
    │   │   ├── CaseQueryDTO.java             # 案件查询DTO
    │   │   └── CaseUpdateDTO.java            # 案件更新DTO
    │   ├── team/                             # 团队相关DTO
    │   │   ├── CaseParticipantDTO.java       # 案件参与方DTO
    │   │   └── CaseTeamDTO.java              # 案件团队DTO
    │   └── business/                         # 业务相关DTO
    │       ├── CaseDocumentDTO.java          # 案件文档DTO
    │       └── CaseReminderDTO.java          # 案件提醒DTO
    ├── vo/                                    # 视图对象
    │   ├── base/                             # 基础视图
    │   │   ├── CaseDetailVO.java             # 案件详情VO
    │   │   └── CaseQueryVO.java              # 案件查询VO
    │   ├── team/                             # 团队相关视图
    │   │   ├── CaseParticipantVO.java        # 参与方VO
    │   │   └── CaseTeamVO.java               # 团队成员VO
    │   └── business/                         # 业务相关视图
    │       ├── CaseDocumentVO.java           # 文档VO
    │       ├── CaseEventVO.java              # 事件VO
    │       ├── CaseNoteVO.java               # 笔记VO
    │       ├── CaseReminderVO.java           # 提醒VO
    │       ├── CaseStatusChangeVO.java       # 状态变更VO
    │       ├── CaseStatusEnumVO.java         # 状态枚举VO
    │       ├── CaseStatusVO.java             # 状态VO
    │       ├── CaseTaskVO.java               # 任务VO
    │       └── CaseTimelineVO.java           # 时间线VO
    ├── entity/                                # 实体类
    │   ├── base/                             # 基础实体
    │   │   └── Case.java                     # 案件实体
    │   ├── team/                             # 团队相关实体
    │   │   ├── CaseAssignment.java           # 案件分配实体
    │   │   ├── CaseParticipant.java          # 案件参与方实体
    │   │   └── CaseTeam.java                 # 案件团队实体
    │   └── business/                         # 业务相关实体
    │       ├── CaseDocument.java             # 案件文档实体
    │       ├── CaseFile.java                 # 案件文件实体
    │       ├── CaseReminder.java             # 案件提醒实体
    │       ├── CaseStatusLog.java            # 案件状态日志实体
    │       └── CaseWorkload.java             # 案件工作量实体
    ├── service/                               # 服务接口
    │   ├── base/                             # 基础服务
    │   │   └── CaseService.java              # 案件基础服务接口
    │   ├── team/                             # 团队相关服务
    │   │   ├── CaseParticipantService.java   # 案件参与方服务接口
    │   │   └── CaseAssignmentService.java    # 案件分配服务接口
    │   └── business/                         # 业务相关服务
    │       ├── CaseTaskService.java          # 案件任务服务接口
    │       ├── CaseReminderService.java      # 案件提醒服务接口
    │       ├── CaseWorkloadService.java      # 案件工作量服务接口
    │       ├── CaseEventService.java         # 案件事件服务接口
    │       ├── CaseFileService.java          # 案件文件服务接口
    │       ├── CaseCategoryService.java      # 案件分类服务接口
    │       ├── CaseFinanceService.java       # 案件财务服务接口
    │       ├── CaseContractService.java      # 案件合同服务接口
    │       ├── CaseApprovalService.java      # 案件审批服务接口
    │       └── CaseKnowledgeService.java     # 案件知识库服务接口
    └── enums/                                 # 枚举类
        ├── base/                              # 基本属性枚举
        │   ├── CaseDifficultyEnum.java        # 案件难度枚举
        │   ├── CaseFeeTypeEnum.java           # 收费类型枚举
        │   ├── CaseHandleTypeEnum.java        # 办理方式枚举
        │   ├── CaseImportanceEnum.java        # 案件重要性枚举
        │   ├── CasePriorityEnum.java          # 优先级枚举
        │   ├── CaseProgressEnum.java          # 案件进展枚举
        │   ├── CaseResultEnum.java            # 案件结果枚举
        │   ├── CaseSourceEnum.java            # 案件来源枚举
        │   ├── CaseStageEnum.java             # 案件阶段枚举
        │   ├── CaseStatusEnum.java            # 案件状态枚举
        │   ├── CaseTagEnum.java               # 案件标签枚举
        │   └── CaseTypeEnum.java              # 案件类型枚举
        ├── cause/                             # 案由相关枚举
        │   ├── AdministrativeCauseEnum.java   # 行政案由枚举
        │   ├── CaseCauseEnum.java             # 案由基础枚举
        │   ├── CompanySecuritiesCauseEnum.java # 公司证券案由枚举
        │   ├── ContractCauseEnum.java         # 合同案由枚举
        │   ├── CriminalCauseEnum.java         # 刑事案由枚举
        │   ├── IpCompetitionCauseEnum.java    # 知识产权竞争案由枚举
        │   ├── LaborPersonnelCauseEnum.java   # 劳动人事案由枚举
        │   ├── MaritimeCauseEnum.java         # 海事海商案由枚举
        │   ├── MarriageInheritanceCauseEnum.java # 婚姻继承案由枚举
        │   ├── NonLitigationCauseEnum.java    # 非诉讼案由枚举
        │   ├── PersonalityRightCauseEnum.java # 人格权案由枚举
        │   ├── PropertyRightCauseEnum.java    # 物权案由枚举
        │   ├── SpecialProcedureCauseEnum.java # 特殊程序案由枚举
        │   └── TortCauseEnum.java             # 侵权案由枚举
        ├── doc/                               # 文档相关枚举
        │   ├── CaseDocumentTypeEnum.java      # 案件文档类型枚举
        │   ├── DocumentSecurityLevelEnum.java # 文档安全级别枚举
        │   ├── DocumentStatusEnum.java        # 文档状态枚举
        │   ├── DocumentTypeEnum.java          # 文档类型枚举
        │   └── FileSecurityLevelEnum.java     # 文件安全级别枚举
        ├── event/                             # 事件相关枚举
        │   ├── CaseTimelineEventEnum.java     # 案件时间线事件枚举
        │   ├── EventStatusEnum.java           # 事件状态枚举
        │   ├── EventTypeEnum.java             # 事件类型枚举
        │   └── TimelineEventTypeEnum.java     # 时间线事件类型枚举
        ├── task/                              # 任务相关枚举
        │   ├── TaskPriorityEnum.java          # 任务优先级枚举
        │   ├── TaskStatusEnum.java            # 任务状态枚举
        │   └── TaskTypeEnum.java              # 任务类型枚举
        ├── team/                              # 团队相关枚举
        │   ├── CaseParticipantTypeEnum.java   # 案件参与方类型枚举
        │   ├── ParticipantTypeEnum.java       # 参与者类型枚举
        │   └── TeamMemberRoleEnum.java        # 团队成员角色枚举
        ├── reminder/                          # 提醒相关枚举
        │   ├── ReminderStatusEnum.java        # 提醒状态枚举
        │   ├── ReminderTypeEnum.java          # 提醒类型枚举
        │   └── RepeatTypeEnum.java            # 重复类型枚举
        └── other/                             # 其他枚举
            └── NoteTypeEnum.java              # 笔记类型枚举
```

## 枚举类说明

### 1. 案件基本属性枚举
- `CaseStatusEnum`：案件状态枚举
- `CaseTypeEnum`：案件类型枚举
- `CasePriorityEnum`：优先级枚举
- `CaseFeeTypeEnum`：收费类型枚举
- `CaseSourceEnum`：案件来源枚举
- `CaseProgressEnum`：案件进展枚举
- `CaseHandleTypeEnum`：办理方式枚举
- `CaseDifficultyEnum`：案件难度枚举
- `CaseImportanceEnum`：案件重要性枚举
- `CaseStageEnum`：案件阶段枚举
- `CaseResultEnum`：案件结果枚举
- `CaseTagEnum`：案件标签枚举

### 2. 案由相关枚举
- `CaseCauseEnum`：案由基础枚举
- `AdministrativeCauseEnum`：行政案由枚举
- `CriminalCauseEnum`：刑事案由枚举
- `ContractCauseEnum`：合同案由枚举
- `CompanySecuritiesCauseEnum`：公司证券案由枚举
- `IpCompetitionCauseEnum`：知识产权竞争案由枚举
- `LaborPersonnelCauseEnum`：劳动人事案由枚举
- `MaritimeCauseEnum`：海事海商案由枚举
- `MarriageInheritanceCauseEnum`：婚姻继承案由枚举
- `NonLitigationCauseEnum`：非诉讼案由枚举
- `PersonalityRightCauseEnum`：人格权案由枚举
- `PropertyRightCauseEnum`：物权案由枚举
- `SpecialProcedureCauseEnum`：特殊程序案由枚举
- `TortCauseEnum`：侵权案由枚举

### 3. 文档相关枚举
- `DocumentTypeEnum`：文档类型枚举
- `DocumentStatusEnum`：文档状态枚举
- `DocumentSecurityLevelEnum`：文档安全级别枚举
- `FileSecurityLevelEnum`：文件安全级别枚举
- `CaseDocumentTypeEnum`：案件文档类型枚举

### 4. 任务相关枚举
- `TaskTypeEnum`：任务类型枚举
- `TaskStatusEnum`：任务状态枚举
- `TaskPriorityEnum`：任务优先级枚举

### 5. 团队相关枚举
- `TeamMemberRoleEnum`：团队成员角色枚举
- `CaseParticipantTypeEnum`：案件参与方类型枚举
- `ParticipantTypeEnum`：参与者类型枚举

### 6. 提醒相关枚举
- `ReminderTypeEnum`：提醒类型枚举
- `ReminderStatusEnum`：提醒状态枚举
- `RepeatTypeEnum`：重复类型枚举

### 7. 事件相关枚举
- `EventTypeEnum`：事件类型枚举
- `EventStatusEnum`：事件状态枚举
- `CaseTimelineEventEnum`：案件时间线事件枚举
- `TimelineEventTypeEnum`：时间线事件类型枚举

### 8. 其他枚举
- `NoteTypeEnum`：笔记类型枚举

## 模块说明
案件模型模块是律师事务所管理系统的案件数据结构定义模块，负责定义系统中所有与案件相关的实体类、数据传输对象、视图对象和枚举类型。该模块是案件管理功能的数据基础。

## 常量类说明

### 1. 字段限制常量
- `CaseFieldConstants`：案件字段限制常量
  - 案件编号长度限制和格式规范
  - 案件名称长度限制
  - 案件描述长度限制
  - 金额精度和格式规范
  - 日期时间格式规范
  - 法院相关字段限制
  - 律师相关字段限制
  - 委托人相关字段限制
  - 分页相关限制

### 2. 状态相关常量
- `CaseStatusConstants`：案件状态相关常量
  - 案件状态码定义
  - 状态流转规则
  - 状态权限控制
  - 状态操作限制

### 3. 消息提示常量
- `CaseMessageConstants`：案件消息提示常量
  - 操作成功提示
  - 操作失败提示
  - 状态变更提示
  - 权限相关提示
  - 业务规则提示

### 4. 错误码常量
- `CaseErrorConstants`：案件错误码常量
  - 系统错误码
  - 业务错误码
  - 参数错误码
  - 权限错误码
  - 状态错误码

## 核心功能

### 1. 案件基本信息
- 案件实体（Case）
  - 案件基本信息
  - 案件状态管理
  - 案件类型分类
  - 案件优先级
  - 收费管理
  - 时间管理

### 2. 案件参与方
- 案件参与方实体（CaseParticipant）
  - 当事人信息
  - 代理人信息
  - 法官信息
  - 其他参与方

### 3. 案件属性
- 案件来源
- 案件优先级
- 收费方式
- 案件状态
- 案件类型

### 4. 案件关联
- 律师关联
- 客户关联
- 律所关联
- 部门关联
- 文档关联

## 数据传输对象（DTO）

### 1. 基础DTO
- `CaseBaseDTO`：案件基础数据传输对象
  - 案件编号、名称、描述
  - 案件类型、状态、进展
  - 办理方式、难度、重要性
  - 优先级、收费类型、来源
  - 主办律师、客户信息
  - 律所信息、时间信息
  - 法院信息、冲突检查

### 2. 创建和更新DTO
- `CaseCreateDTO`：案件创建数据传输对象
  - 继承自CaseBaseDTO
  - 案件参与方列表
  - 案件文档列表
  - 团队成员列表
  - 提醒事项列表
  - 特殊要求
  - 审批流程

- `CaseUpdateDTO`：案件更新数据传输对象
  - 继承自CaseBaseDTO
  - 变更原因
  - 参与方添加/移除
  - 文档添加/移除
  - 团队成员添加/移除
  - 提醒事项添加/移除

### 3. 查询DTO
- `CaseQueryDTO`：案件查询数据传输对象
  - 多条件组合查询
  - 时间范围查询
  - 状态列表查询
  - 关键字搜索
  - 标签过滤

### 4. 关联对象DTO
- `CaseParticipantDTO`：案件参与方数据传输对象
  - 参与方类型
  - 基本信息
  - 联系方式
  - 证件信息

- `CaseTeamDTO`：案件团队数据传输对象
  - 团队成员信息
  - 角色职责
  - 工作量分配
  - 收益分配
  - 考核标准

- `CaseDocumentDTO`：案件文档数据传输对象
  - 文档基本信息
  - 安全级别
  - 权限控制
  - 审批信息
  - 版本管理

- `CaseReminderDTO`：案件提醒数据传输对象
  - 提醒类型
  - 时间设置
  - 重复规则
  - 接收人设置
  - 关联项目

## 视图对象（VO）

### 1. 基础视图
- `CaseDetailVO`：案件详情视图对象
  - 案件完整信息
  - 参与方信息
  - 团队信息
  - 文档信息
  - 财务信息
  - 状态历史

- `CaseQueryVO`：案件查询视图对象
  - 列表展示信息
  - 基础案件信息
  - 关联方信息
  - 时间信息

### 2. 状态相关视图
- `CaseStatusVO`：案件状态视图对象
  - 状态变更信息
  - 操作人信息
  - 变更原因
  - 通知设置

- `CaseStatusChangeVO`：状态变更视图对象
  - 变更前后状态
  - 操作人
  - 变更原因

- `CaseStatusEnumVO`：状态枚举视图对象
  - 状态编码
  - 状态名称
  - 状态描述

### 3. 时间线相关视图
- `CaseTimelineVO`：案件时间线视图对象
  - 事件类型
  - 事件描述
  - 操作信息
  - 相关附件

### 4. 事件相关视图
- `CaseEventVO`：案件事件视图对象
  - 事件详情
  - 参与人员
  - 时间信息
  - 审批信息

### 5. 任务相关视图
- `CaseTaskVO`：案件任务视图对象
  - 任务信息
  - 负责人
  - 进度状态
  - 时间节点
  - 相关资源

### 6. 团队相关视图
- `CaseTeamVO`：团队成员视图对象
  - 成员信息
  - 角色职责
  - 工作时间
  - 部门信息
  - 专业领域

### 7. 提醒相关视图
- `CaseReminderVO`：提醒事项视图对象
  - 提醒类型
  - 提醒内容
  - 时间设置
  - 接收人信息
  - 重复规则
  - 确认状态

### 8. 其他视图对象
- `CaseDocumentVO`：文档视图对象
  - 文档信息
  - 安全设置
  - 审批状态
  - 时效管理

- `CaseNoteVO`：案件笔记视图对象
  - 笔记内容
  - 标签管理
  - 共享设置
  - 审批信息

- `CaseParticipantVO`：参与方视图对象
  - 参与方信息
  - 联系方式
  - 角色说明

## 实体类说明

### 1. 核心实体
- `Case.java`：案件实体类
  - 案件基本信息：案号、名称、类型、状态
  - 当事人信息：委托人、对方当事人
  - 时间信息：立案时间、结案时间
  - 案件属性：优先级、难度、重要性
  - 案件描述：案情简介、备注说明
  - 费用信息：收费类型、预估金额
  - 审计字段：创建时间、更新时间、操作人

### 2. 参与方实体
- `CaseParticipant.java`：案件参与方实体
  - 参与方类型：委托人、对方当事人、证人等
  - 基本信息：姓名、联系方式、地址
  - 身份信息：证件类型、证件号码
  - 关联案件ID
  - 参与角色说明

### 3. 文档实体
- `CaseDocument.java`：案件文档实体
  - 文档基本信息：名称、类型、大小
  - 存储信息：路径、格式
  - 安全级别：访问权限、加密状态
  - 版本控制：版本号、修改记录
  - 文档状态：审核状态、使用状态

### 4. 团队实体
- `CaseTeam.java`：案件团队实体
  - 团队成员：律师ID、角色类型
  - 工作分配：负责内容、工作量
  - 时间信息：加入时间、退出时间
  - 权限设置：操作权限、查看权限

### 5. 文件实体
- `CaseFile.java`：案件文件实体
  - 文件信息：名称、路径、大小
  - 文件类型：格式、分类
  - 安全级别：访问权限
  - 使用状态：是否有效、是否归档
  - 文件来源：上传人、来源渠道
  - 文件描述：用途说明、备注
  - 关联信息：关联案件、关联文档
  - 时间信息：创建时间、最后访问时间
  - 版本信息：版本号、修改历史

### 6. 工作量实体
- `CaseWorkload.java`：案件工作量实体
  - 律师信息：律师ID、姓名
  - 工作类型：咨询、文书、出庭等
  - 工作内容：具体描述
  - 时间记录：开始时间、结束时间
  - 工作量统计：小时数、计费

### 7. 状态日志实体
- `CaseStatusLog.java`：案件状态日志实体
  - 状态变更：原状态、新状态
  - 变更信息：操作人、变更时间
  - 变更原因：具体说明
  - 相关审批：审批人、审批意见

### 8. 案件分配实体
- `CaseAssignment.java`：案件分配实体
  - 分配信息：原律师、受理律师
  - 分配类型：转办、协办、临时代理、工作交接
  - 分配状态：待处理、已接受、已拒绝、已完成
  - 时间信息：分配时间、预计交接时间、实际交接时间
  - 审批信息：审批人、审批时间、审批意见
  - 交接信息：交接说明、是否需要交接
  - 通知信息：是否通知当事人、通知时间、通知内容

### 9. 案件提醒实体
- `CaseReminder.java`：案件提醒实体
  - 提醒类型：日程提醒、任务提醒、期限提醒等
  - 提醒内容：标题、内容、重要程度
  - 时间设置：提醒时间、重复规则、提前提醒时间
  - 接收人：主要接收人、抄送人
  - 提醒状态：未提醒、已提醒、已确认
  - 确认信息：确认时间、确认备注
  - 发送记录：发送次数、最后发送时间
  - 关联信息：关联事项类型、关联事项ID

## 使用示例

### 1. 创建案件
```java
CaseCreateDTO createDTO = new CaseCreateDTO()
    .setCaseNumber("CASE-2024-001")
    .setCaseName("某某合同纠纷案件")
    .setCaseType(CaseTypeEnum.CIVIL)
    .setCaseStatus(CaseStatusEnum.PENDING)
    .setCaseProgress(CaseProgressEnum.PREPARATION)
    .setLawyer("张律师")
    .setClientId(1001L)
    .setLawFirmId(2001L);
```

### 2. 更新案件
```java
CaseUpdateDTO updateDTO = new CaseUpdateDTO()
    .setId(1L)
    .setCaseStatus(CaseStatusEnum.IN_PROGRESS)
    .setCaseProgress(CaseProgressEnum.COURT_HEARING)
    .setChangeReason("进入庭审阶段");
```

### 3. 查询案件
```java
CaseQueryDTO queryDTO = new CaseQueryDTO()
    .setCaseType(CaseTypeEnum.CIVIL)
    .setCaseStatus(Arrays.asList(CaseStatusEnum.IN_PROGRESS))
    .setLawyer("张律师")
    .setFilingTimeStart(LocalDateTime.now().minusMonths(1));
```

## 注意事项
1. 案件编号必须唯一
2. 注意数据安全和隐私保护
3. 重要操作需要记录日志
4. 关注案件状态变更
5. 及时更新关联信息