# 案件模型模块 (Case Model)

## 模块说明
案件模型模块是律师事务所管理系统的案件数据结构定义模块，负责定义系统中所有与案件相关的实体类、数据传输对象、视图对象和枚举类型。该模块是案件管理功能的数据基础。

## 目录结构
```
src/main/java/com/lawfirm/model/cases/
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
│   │   ├── CaseTeamDTO.java              # 案件团队DTO
│   │   └── CaseAssignmentDTO.java        # 案件分配DTO
│   └── business/                         # 业务相关DTO
│       ├── CaseDocumentDTO.java          # 案件文档DTO
│       ├── CaseFeeDTO.java               # 案件费用DTO
│       ├── CaseReminderDTO.java          # 案件提醒DTO
│       ├── CaseTaskDTO.java              # 案件任务DTO
│       ├── CaseEventDTO.java             # 案件事件DTO
│       ├── CaseWorkloadDTO.java          # 案件工作量DTO
│       ├── CaseCategoryDTO.java          # 案件分类DTO
│       ├── CaseFinanceDTO.java           # 案件财务DTO
│       ├── CaseContractDTO.java          # 案件合同DTO
│       ├── CaseApprovalDTO.java          # 案件审批DTO
│       └── CaseKnowledgeDTO.java         # 案件知识库DTO
├── entity/                                # 实体类
│   ├── base/                             # 基础实体
│   │   └── Case.java                     # 案件实体
│   ├── team/                             # 团队相关实体
│   │   ├── CaseAssignment.java           # 案件分配实体
│   │   ├── CaseParticipant.java          # 案件参与方实体
│   │   └── CaseTeam.java                 # 案件团队实体
│   └── business/                         # 业务相关实体
│       ├── CaseDocument.java             # 案件文档实体
│       ├── CaseFee.java                  # 案件费用实体
│       ├── CaseFile.java                 # 案件文件实体
│       ├── CaseReminder.java             # 案件提醒实体
│       ├── CaseStatusLog.java            # 案件状态日志实体
│       └── CaseWorkload.java             # 案件工作量实体
├── enums/                                 # 枚举类
│   ├── base/                             # 基本属性枚举
│   │   ├── CaseDifficultyEnum.java       # 案件难度枚举
│   │   ├── CaseFeeTypeEnum.java          # 收费类型枚举
│   │   ├── CaseHandleTypeEnum.java       # 办理方式枚举
│   │   ├── CaseImportanceEnum.java       # 案件重要性枚举
│   │   ├── CasePriorityEnum.java         # 优先级枚举
│   │   ├── CaseProgressEnum.java         # 案件进展枚举
│   │   ├── CaseResultEnum.java           # 案件结果枚举
│   │   ├── CaseSourceEnum.java           # 案件来源枚举
│   │   ├── CaseStageEnum.java            # 案件阶段枚举
│   │   ├── CaseStatusEnum.java           # 案件状态枚举
│   │   └── CaseTypeEnum.java             # 案件类型枚举
│   ├── civilcause/                       # 民事案由枚举
│   ├── admincause/                       # 行政案由枚举
│   ├── charge/                           # 刑事罪名枚举
│   ├── doc/                              # 文档相关枚举
│   ├── event/                            # 事件相关枚举
│   ├── task/                             # 任务相关枚举
│   ├── team/                             # 团队相关枚举
│   ├── reminder/                         # 提醒相关枚举
│   ├── note/                             # 笔记相关枚举
│   ├── other/                            # 其他枚举
│   └── base/                             # 基本属性枚举
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
│       ├── CaseDocumentService.java      # 案件文件服务接口
│       ├── CaseCategoryService.java      # 案件分类服务接口
│       └── CaseFinanceService.java       # 案件财务服务接口
└── vo/                                    # 视图对象
    ├── base/                             # 基础视图
    │   ├── CaseDetailVO.java             # 案件详情VO
    │   └── CaseQueryVO.java              # 案件查询VO
    ├── team/                             # 团队相关视图
    │   ├── CaseParticipantVO.java        # 参与方VO
    │   ├── CaseTeamVO.java               # 团队成员VO
    │   └── CaseAssignmentVO.java         # 案件分配VO
    └── business/                         # 业务相关视图
        ├── CaseDocumentVO.java           # 文档VO
        ├── CaseFeeVO.java                # 费用VO
        ├── CaseEventVO.java              # 事件VO
        ├── CaseNoteVO.java               # 笔记VO
        ├── CaseReminderVO.java           # 提醒VO
        ├── CaseStatusChangeVO.java       # 状态变更VO
        ├── CaseStatusEnumVO.java         # 状态枚举VO
        ├── CaseStatusVO.java             # 状态VO
        ├── CaseTaskVO.java               # 任务VO
        └── CaseProgressVO.java           # 时间线VO
```

## 主要功能

### 1. 案件基本信息管理
- 案件基本信息的增删改查
- 案件状态流转管理
- 案件类型分类管理
- 案件优先级管理
- 案件收费管理

### 2. 案件参与方管理
- 当事人信息管理
- 代理人信息管理
- 法官信息管理
- 其他参与方管理

### 3. 案件团队管理
- 团队成员管理
- 工作分配管理
- 权限控制管理
- 工作量统计

### 4. 案件文档管理
- 文档信息管理
- 文档版本控制
- 文档权限管理
- 文档安全管理

### 5. 案件费用管理
- 费用信息管理
- 收付款管理
- 发票管理
- 费用审核管理

### 6. 案件提醒管理
- 提醒事项管理
- 日程安排管理
- 期限管理
- 通知管理

## 依赖关系
- 依赖 `base-model` 模块
- 依赖 `common-core` 模块
- 依赖 `common-util` 模块
- 依赖 `common-web` 模块
- 依赖 `common-data` 模块

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

## 技术栈
- Java 21
- Spring Boot 3.2.2
- Lombok
- Jakarta Validation
- JUnit 5
- Mockito

## 迁移记录

### JPA到MyBatis Plus迁移 (2024-04-28)
- 检查实体类，确认已经添加了MyBatis Plus注解（@TableName、@TableField）
- 移除pom.xml中的JPA依赖，添加MyBatis Plus依赖
- 确保实体类继承关系正确

已迁移的实体类：
- Case.java
- CaseAssignment.java
- CaseParticipant.java
- CaseTeam.java
- CaseDocument.java
- CaseTask.java
- CaseEvent.java
- CaseFee.java
- CaseNote.java