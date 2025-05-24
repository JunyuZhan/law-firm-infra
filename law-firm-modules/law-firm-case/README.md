# 案件管理模块

本模块是律所管理系统的案件管理实现模块，基于`law-firm-model/case-model`数据模型，提供律所案件管理的完整业务功能实现。本模块负责实现case-model中定义的所有接口。

## 模块功能

- 案件基本信息管理：创建、查询、更新和删除案件信息
- 案件状态管理：案件状态流转和生命周期管理
- 案件团队管理：案件团队组建和任务分配
- 案件文档管理：案件相关文档的管理与存储
- 案件费用管理：案件收费和费用记录
- 案件事件管理：案件日程和事件记录
- 数据统计分析：案件相关数据统计与分析

## 业务模块集成

案件管理模块与系统中的其他业务模块紧密集成，主要包括：

1. **认证授权模块 (law-firm-auth)**
   - 用户身份验证与会话管理
   - 案件访问权限控制（使用@RequiresPermissions等注解）
   - 操作权限判断（通过auth模块提供的权限检查机制）
   - 数据权限过滤（通过auth模块提供的数据权限框架）

2. **文档管理模块 (law-firm-document)**
   - 案件文档存储与调用
   - 文档模板应用
   - 文档在线预览
   - 文档版本管理

3. **客户管理模块 (law-firm-client)**
   - 案件客户信息关联
   - 客户联系方式获取
   - 客户授权代表管理
   - 客户风险信息获取

4. **人事管理模块 (law-firm-personnel)**
   - 案件团队人员选择
   - 律师专业背景查询
   - 工作量统计与评估
   - 人员变动处理

## 核心层集成

案件管理模块集成了系统核心层（law-firm-core）的以下组件：

1. **工作流引擎 (core-workflow)**
   - 案件流程定义和管理
   - 案件状态流转
   - 审批流程管理
   - 任务分配和跟踪

2. **审计日志 (core-audit)**
   - 案件操作审计（使用@Audit等注解记录操作）
   - 数据变更记录（通过审计模块的数据变更跟踪机制）
   - 合规性检查（利用审计模块提供的合规检查功能）

3. **存储服务 (core-storage)**
   - 案件文档存储
   - 文件版本管理
   - 文件访问控制

4. **搜索服务 (core-search)**
   - 案件全文检索
   - 高级搜索功能

5. **消息处理 (core-message)**
   - 案件状态变更通知
   - 案件提醒消息
   - 团队协作消息

## 目录结构

案件管理模块的目录结构按照开发顺序排列如下：

```
src
├── main
│   ├── java
│   │   └── com.lawfirm.cases
│   │       ├── 1️⃣ 基础设施层
│   │       ├── config                      # 配置类（首先开发）
│   │       │   ├── CaseModuleConfiguration.java
│   │       │   └── CaseSecurityConfiguration.java # 安全配置
│   │       ├── constant                    # 业务特定常量定义
│   │       │   └── CaseBusinessConstants.java  # 案件业务相关常量
│   │       ├── exception                   # 异常定义
│   │       │   └── CaseException.java      # 案件异常
│   │       │
│   │       ├── 2️⃣ 核心集成层
│   │       ├── core                        # 核心层集成
│   │       │   ├── workflow                # 工作流集成
│   │       │   │   ├── CaseWorkflowManager.java
│   │       │   │   └── CaseTaskAssigner.java
│   │       │   ├── audit                   # 审计日志集成
│   │       │   │   └── CaseAuditProvider.java  # 提供案件审计信息给审计模块
│   │       │   ├── storage                 # 存储服务集成
│   │       │   │   └── CaseStorageManager.java
│   │       │   ├── search                  # 搜索服务集成
│   │       │   │   └── CaseSearchManager.java
│   │       │   └── message                 # 消息处理集成
│   │       │       └── CaseMessageManager.java
│   │       │
│   │       ├── 3️⃣ 业务实现层
│   │       ├── service                     # 服务实现（实现case-model中定义的服务接口）
│   │       │   └── impl                    # 服务实现类
│   │       │       ├── CaseServiceImpl.java    # 核心服务，优先实现
│   │       │       ├── TeamServiceImpl.java    # 团队服务
│   │       │       ├── ProcessServiceImpl.java # 流程服务
│   │       │       ├── DocumentServiceImpl.java # 文档服务
│   │       │       ├── FeeServiceImpl.java     # 费用服务
│   │       │       └── EventServiceImpl.java   # 事件服务
│   │       ├── convert                     # 对象转换器
│   │       │   ├── CaseConvert.java        # 案件对象转换
│   │       │   └── TeamConvert.java        # 团队对象转换
│   │       │
│   │       ├── 4️⃣ 流程与事件处理层
│   │       ├── process                     # 工作流相关
│   │       │   ├── definition              # 流程定义
│   │       │   │   ├── CaseProcessDefinition.java
│   │       │   │   └── CaseApprovalDefinition.java
│   │       │   ├── listener                # 流程监听器
│   │       │   │   ├── CaseStartListener.java
│   │       │   │   └── CaseCompleteListener.java
│   │       │   └── handler                 # 流程处理器 
│   │       │       ├── CaseCreateHandler.java
│   │       │       └── CaseApprovalHandler.java
│   │       ├── event                       # 事件处理
│   │       │   └── CaseEventHandler.java   # 案件事件处理器
│   │       ├── listener                    # 事件监听器
│   │       │   ├── CaseStatusListener.java # 状态变更监听器
│   │       │   └── CaseDocumentListener.java # 文档变更监听器
│   │       │
│   │       ├── 5️⃣ 业务模块集成层
│   │       ├── integration                 # 系统集成
│   │       │   ├── auth                    # 认证集成
│   │       │   │   ├── CasePermissionProvider.java  # 提供案件权限信息给auth模块
│   │       │   │   └── CaseDataPermissionProvider.java  # 提供数据权限过滤规则
│   │       │   ├── client                  # 客户集成
│   │       │   │   ├── ClientComponent.java
│   │       │   │   └── ClientChangeListener.java
│   │       │   ├── document                # 文档集成
│   │       │   │   ├── DocumentComponent.java
│   │       │   │   └── DocumentPermissionSync.java
│   │       │   └── personnel               # 人事集成
│   │       │       ├── LawyerComponent.java
│   │       │       └── WorkloadReporter.java
│   │       │
│   │       ├── 6️⃣ 控制层
│   │       ├── controller                  # 控制器（实现case-model中定义的API接口）
│   │       │   ├── CaseControllerImpl.java # 案件基本控制器实现
│   │       │   ├── TeamControllerImpl.java # 团队管理控制器实现
│   │       │   ├── ProcessControllerImpl.java # 流程控制器实现
│   │       │   ├── DocumentControllerImpl.java # 文档管理控制器实现
│   │       │   ├── FeeControllerImpl.java  # 费用管理控制器实现
│   │       │   ├── TaskControllerImpl.java # 任务管理控制器实现
│   │       │   └── EventControllerImpl.java # 事件管理控制器实现
│   │       │
│   └── resources                           # 7️⃣ 资源文件
│       ├── db
│       │   └── migration                   # 数据库迁移脚本
│       │       ├── V1__create_case_tables.sql
│       │       └── V2__init_case_data.sql
│       ├── processes                       # 工作流定义文件
│       │   ├── case-process.bpmn20.xml     # 案件流程定义
│       │   └── case-approval.bpmn20.xml    # 案件审批流程
│       ├── application-case.yml            # 模块配置文件
│       └── META-INF
│           └── spring
│               └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
└── test                                    # 8️⃣ 测试层
    └── java
        └── com.lawfirm.cases
            ├── service
            │   └── CaseServiceTest.java    # 服务测试
            ├── integration
            │   ├── AuthIntegrationTest.java # 认证集成测试
            │   ├── ClientIntegrationTest.java # 客户集成测试
            │   └── DocumentIntegrationTest.java # 文档集成测试
            └── controller
                └── CaseControllerTest.java # 控制器测试
```

## 模块依赖

- law-firm-model/case-model：案件数据模型与接口定义（包含事件基类和事件定义）
- law-firm-model/client-model：客户数据模型
- law-firm-model/document-model：文档数据模型
- law-firm-model/log-model：日志数据模型
- law-firm-modules/law-firm-auth：认证授权模块
- law-firm-modules/law-firm-document：文档管理模块
- law-firm-modules/law-firm-client：客户管理模块
- law-firm-modules/law-firm-personnel：人事管理模块
- law-firm-core/core-workflow：工作流引擎
- law-firm-core/core-audit：审计日志
- law-firm-core/core-storage：存储服务
- law-firm-core/core-search：搜索服务
- law-firm-core/core-message：消息处理
- common-core：核心基础组件
- common-web：Web相关组件
- common-data：数据访问组件
- common-log：日志组件（提供日志记录和审计功能）

## 开发计划

开发顺序按照上述目录结构中的编号组织，具体如下：

### 第一阶段：基础设施（初始化）

1. **配置与常量**
   - 模块配置类（CaseModuleConfiguration）
   - 配置文件（application-case.yml）
   - 业务常量定义
   - 异常定义

2. **核心层集成基础**
   - 工作流引擎集成（CaseWorkflowManager）
   - 审计日志集成（CaseAuditProvider）
   - 存储服务集成（CaseStorageManager）
   - 搜索服务集成（CaseSearchManager）
   - 消息处理集成（CaseMessageManager）

### 第二阶段：核心业务功能

3. **基础服务实现**
   - CaseServiceImpl：实现案件基本信息管理
   - TeamServiceImpl：实现团队管理
   - 基本对象转换器（CaseConvert）

4. **流程处理实现**
   - ProcessServiceImpl：实现流程管理
   - 流程定义实现
   - 流程监听器和处理器实现

### 第三阶段：扩展业务功能

5. **扩展服务实现**
   - DocumentServiceImpl：实现文档管理
   - FeeServiceImpl：实现费用管理
   - EventServiceImpl：实现事件管理

6. **事件处理实现**
   - CaseEventHandler实现
   - 事件监听器实现

### 第四阶段：系统集成与接口实现

7. **业务模块集成**
   - 认证模块集成（权限处理）
   - 客户模块集成（客户信息同步）
   - 文档模块集成（文档存储集成）
   - 人事模块集成（律师信息同步）

8. **控制器实现**
   - 基础控制器（CaseControllerImpl）
   - 扩展控制器（团队、流程、文档等）

### 第五阶段：测试与优化

9. **单元测试实现**
   - 服务层测试
   - 控制器测试
   - 集成测试

10. **系统优化**
    - 性能优化
    - 接口调整
    - 问题修复

## 接口实现

本模块实现了case-model中定义的所有接口，不在本模块中定义新的接口。主要包括以下接口实现：

### 案件基本操作接口

实现案件创建、更新、查询、列表获取和删除等基本操作接口。

### 案件状态管理接口

实现案件状态变更、归档和重新激活等接口。

### 案件流程管理接口

实现流程启动、步骤完成和任务获取等接口。

### 团队管理接口

实现团队成员获取、添加和移除等接口。

### 文档管理接口

实现案件文档获取、上传和下载等接口。

### 费用管理接口

实现案件费用获取、添加和更新等接口。

### 事件管理接口

实现案件事件获取、创建和更新等接口。

### 案件搜索接口

实现高级搜索和搜索建议等接口。

> 注：所有接口定义详情请参考`law-firm-model/case-model`模块的API文档

## 案件合同关联架构调整

### 背景与目标

律师事务所的标准业务流程通常是先有委托合同，再创建案件。合同管理属于合同模块的职责，而案件模块应该只负责案件管理以及案件与合同的关联关系。

为了符合单一职责原则并避免功能重复，我们对案件与合同之间的关系进行了架构调整。

### 调整内容

1. **明确模块职责边界**：
   - 合同模块：负责合同的完整生命周期管理（创建、签署、审核等）
   - 案件模块：负责案件管理，并通过关联ID引用合同

2. **重构案件合同关联服务**：
   - 将`CaseContractService`接口简化为只处理关联关系
   - 删除了案件模块中的合同管理功能（如合同签署、审核等）
   - 新增`case_contract_relation`表存储案件与合同的多对多关系

3. **修改接口定义**：
   - 新增关联/解除关联接口
   - 保留合同查询和状态检查接口
   - 移除直接管理合同的接口

### 数据结构

案件合同关联表(`case_contract_relation`)用于存储案件与合同之间的多对多关系：

| 字段名 | 类型 | 描述 |
|--------|------|------|
| id | bigint | 主键ID |
| case_id | bigint | 案件ID |
| contract_id | bigint | 合同ID |
| relation_type | tinyint | 关联类型（1:委托合同, 2:服务合同, 3:其他） |
| relation_desc | varchar | 关联描述 |
| ... | ... | 其他系统字段 |

### 使用方式

```java
// 关联合同到案件
caseContractService.associateContractWithCase(caseId, contractId);

// 解除案件与合同的关联
caseContractService.disassociateContractFromCase(caseId, contractId);

// 获取案件关联的所有合同
List<CaseContractVO> contracts = caseContractService.listCaseContracts(caseId);

// 检查案件是否有关联的有效合同
boolean hasValidContract = caseContractService.checkCaseContractStatus(caseId);

// 获取案件关联的特定合同详情
CaseContractVO contractDetail = caseContractService.getCaseContractDetail(caseId, contractId);
```

### 业务流程

1. 客户咨询并意向委托 → 创建并签署委托合同
2. 基于委托合同 → 创建案件
3. 创建案件时 → 关联已有的委托合同
4. 案件进行中 → 可关联更多相关合同（如服务合同）
5. 查看案件详情 → 可查看所有关联的合同

## 资源文件与数据库迁移脚本

- `resources/application-case.yml`：模块配置文件，已存在。
- `resources/processes/case-process.bpmn20.xml`、`case-approval.bpmn20.xml`：流程定义文件，已存在。
- `resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`：自动配置导入文件，已存在。
- `resources/META-INF/db/migration/`：数据库迁移脚本目录，**已存在**，主要脚本包括：
  - `V7000__add_case_module_responsibility_notes.sql`
  - `V7001__init_case_tables.sql`
  - `V7002__init_case_data.sql`
  - `V7003__reconcile_case_party_tables.sql`
  - `V7004__reconcile_participant_tables.sql`
  - `V7005__add_case_approval_tables.sql`