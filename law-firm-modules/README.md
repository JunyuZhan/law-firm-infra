[返回主项目说明](../README.md)

# 律师事务所管理系统 (Law Firm Management System)

## 技术架构
**核心组件**：
- 微服务框架：Spring Cloud 2022.0.1
- 安全认证：Spring Security 6.1.0
- 数据访问：MyBatis-Plus 3.5.3.1

**基础设施**：
- 数据库：MySQL 8.0 + MongoDB 6.0
- 缓存：Redis 7.0 集群
- 消息队列：RocketMQ 5.0

## 开发规范
### 1. 代码规范
- 遵循阿里巴巴Java开发规范
- 统一的代码格式化模板
- 规范的注释和文档
- 完整的单元测试
- 统一的异常处理

### 2. 接口规范
- RESTful API设计规范
- 统一的响应格式
- 完整的接口文档
- 标准的错误码
- 接口版本控制

### 3. 安全规范
- 身份认证
- 权限控制
- 数据加密
- 安全审计
- 漏洞防护

## 模块详细分工

### 基础业务模块
#### law-firm-auth（认证授权模块）
- **核心职责**：负责整个系统的认证、授权与安全管理
- **主要功能**：
  - 用户认证（登录、注销、多因素认证）
  - 权限管理（基于角色/权限的访问控制）
  - 安全审计（操作日志、安全日志）
  - 会话管理（令牌生成、验证、刷新）
  - 单点登录集成
- **技术组件**：Spring Security、JWT、OAuth2.0
- **数据管理**：用户、角色、权限、操作日志
- **对外接口**：身份验证API、权限检查API、用户信息API


### law-firm-system（系统管理模块）
- **核心职责**：系统基础设置与运维管理
- **主要功能**：
  - 组织架构管理（部门、职位、团队）
  - 系统参数配置
  - 字典管理（公共代码表）
  - 菜单管理
  - 系统监控与告警
  - 审计日志管理
- **技术组件**：Spring Admin、Actuator
- **数据管理**：组织、配置、字典、菜单、系统日志
- **对外接口**：组织架构API、系统配置API、字典API

### law-firm-document（文档管理模块）
- **核心职责**：系统全局文档管理与存储服务
- **主要功能**：
  - 文档上传与下载
  - 文档版本控制
  - 文档分类与标签
  - 全文检索
  - 文档转换与预览
  - 文档权限控制
- **技术组件**：MinIO/S3、PDF处理库、OCR
- **数据管理**：文档元数据、文档内容、文档版本、文档分类
- **对外接口**：文档CRUD API、文档搜索API、文档预览API

## 核心业务模块

### law-firm-case（案件管理模块）
- **核心职责**：律所案件全生命周期管理
- **主要功能**：
  - 案件登记与分类
  - 案件团队管理
  - 案件阶段管理
  - 案件文档管理
  - 案件任务分配
  - 案件关联管理（关联客户、合同等）
  - 案件时间记录
  - 案件统计分析
- **技术组件**：core-workflow集成
- **数据管理**：案件基本信息、案件进度、案件团队、案件文档
- **对外接口**：案件CRUD API、案件流程API、案件统计API
- **依赖模块**：client、document、task、personnel

### law-firm-client（客户管理模块）
- **核心职责**：客户信息与客户关系管理
- **主要功能**：
  - 客户信息管理（个人/机构）
  - 客户联系人管理
  - 客户分类与标签
  - 客户关系维护记录
  - 客户满意度评估
  - 客户风险评估
  - 潜在客户管理
- **技术组件**：数据加密、数据质量校验
- **数据管理**：客户基本信息、联系人、客户关系记录
- **对外接口**：客户CRUD API、客户关系API、客户搜索API
- **依赖模块**：system、document

### law-firm-contract（合同管理模块）
- **核心职责**：律所内外部合同管理
- **主要功能**：
  - 合同起草与模板管理
  - 合同审批流程
  - 合同执行跟踪
  - 收付款计划管理
  - 合同变更管理
  - 合同到期提醒
  - 律师函管理
- **技术组件**：core-workflow集成、合同模板引擎
- **数据管理**：合同基本信息、合同条款、合同履行状态
- **对外接口**：合同CRUD API、合同模板API、合同审批API
- **依赖模块**：client、document、finance

### law-firm-finance（财务管理模块）
- **核心职责**：律所财务与费用管理
- **主要功能**：
  - 收费项目管理
  - 计费模式管理（固定费用、计时收费等）
  - 账单生成与发送
  - 收付款管理
  - 成本核算
  - 发票管理
  - 账期管理与催款
  - 财务报表
- **技术组件**：报表生成、财务计算
- **数据管理**：收费项目、账单、收付款记录、发票
- **对外接口**：计费API、账单API、收付款API、报表API
- **依赖模块**：client、case、contract

## 支撑业务模块

### law-firm-personnel（人事管理模块）
- **核心职责**：律所人员、绩效与培训管理
- **主要功能**：
  - 人员档案管理
  - 绩效考核与评估
  - 工时登记与统计
  - 项目贡献度统计
  - 培训与发展
  - 专业技能管理
- **技术组件**：考核评估算法
- **数据管理**：人员信息、绩效数据、工时记录、培训记录
- **对外接口**：人员API、绩效API、工时API
- **依赖模块**：system、document

### law-firm-knowledge（知识管理模块）
- **核心职责**：律所知识资产管理与利用
- **主要功能**：
  - 法律知识库管理
  - 案例库管理
  - 文书模板管理
  - 研究成果管理
  - 法规动态跟踪
  - 知识分享与协作
- **技术组件**：全文检索、分类算法
- **数据管理**：知识条目、案例、模板、法规
- **对外接口**：知识库API、案例库API、法规库API
- **依赖模块**：document、case

### law-firm-analysis（数据分析模块）
- **核心职责**：系统数据分析与决策支持
- **主要功能**：
  - 业务数据分析
  - 绩效分析
  - 财务分析
  - 客户分析
  - 案件类型与成功率分析
  - 趋势分析
  - 预测分析
- **技术组件**：数据仓库、OLAP、可视化
- **数据管理**：分析模型、分析结果、报表
- **对外接口**：数据分析API、报表API
- **依赖模块**：几乎所有其他业务模块

### law-firm-archive（档案管理模块）
- **核心职责**：律所电子与纸质档案管理
- **主要功能**：
  - 档案分类与编码
  - 档案入库与借阅
  - 档案移交与销毁
  - 电子档案与实体档案关联
  - 档案清单与目录
  - 档案完整性检查
- **技术组件**：条码/RFID集成
- **数据管理**：档案元数据、档案位置、档案状态
- **对外接口**：档案CRUD API、档案检索API
- **依赖模块**：document、case、contract

## 辅助业务模块

### law-firm-task（任务管理模块）
- **核心职责**：跨模块的统一任务管理
- **主要功能**：
  - 任务创建与分配
  - 任务优先级与截止日期
  - 任务依赖关系
  - 任务协作与沟通
  - 任务提醒与通知
  - 任务完成与延期处理
- **技术组件**：core-workflow集成、通知服务
- **数据管理**：任务基本信息、任务状态、任务关联
- **对外接口**：任务CRUD API、任务分配API、任务状态API
- **依赖模块**：system、personnel

### law-firm-schedule（日程管理模块）
- **核心职责**：律所人员日程与会议管理
- **主要功能**：
  - 个人日程管理
  - 团队日程协调
  - 会议室预约
  - 日程提醒
  - 外部日程集成
  - 庭审安排
- **技术组件**：日历同步、提醒服务
- **数据管理**：日程信息、会议信息、提醒设置
- **对外接口**：日程API、会议室API、提醒API
- **依赖模块**：personnel、case

## 模块间依赖关系
### 层级结构
1. **基础层**：
   - 📦 law-firm-auth
   - 📦 law-firm-system
   - 📦 law-firm-document

2. **核心层**：
   - 🏢 law-firm-case
   - 🤝 law-firm-client
   - 📑 law-firm-contract
   - 💰 law-firm-finance

3. **支撑层**：
   - 👥 law-firm-personnel
   - 🧠 law-firm-knowledge
   - 📊 law-firm-analysis
   - 🗃️ law-firm-archive

4. **辅助层**：
   - ✅ law-firm-task
   - 📅 law-firm-schedule
   - 🏷️ law-firm-asset
   - 🖋️ law-firm-seal

## 核心工作流集成策略
### 流程集成点
| 模块           | 集成场景                 | 业务类型标识       |
|----------------|------------------------|-------------------|
| 案件管理模块    | 案件阶段转换审批        | CASE_PHASE_CHANGE |
| 合同管理模块    | 合同版本变更审批        | CONTRACT_UPDATE   |
| 财务管理模块    | 大额费用报销审批        | FINANCE_REIMBURSE |
| 印章管理模块    | 印章使用申请审批        | SEAL_APPLICATION  |
| 人事管理模块    | 绩效考核结果确认        | PERFORMANCE_REVIEW|

注：各业务模块根据业务需求，使用ProcessCreateDTO中的businessType字段，标识不同业务类型的流程，并在各自模块内实现特定业务逻辑。

## 系统说明
律师事务所管理系统是一套完整的律所业务管理解决方案...

### law-firm-schedule（日程管理模块）
日程管理模块提供个人及团队日程的管理功能，包括日程安排、提醒、冲突检测等。

> **注：利益冲突功能已整合到合同管理模块(law-firm-contract)中实现，以简化架构。**

### law-firm-task（任务管理模块）
任务管理模块提供任务分配、跟踪和提醒功能，支持团队协作和工作流程管理。

## 模块依赖关系

主要模块依赖关系如下：

- 📊 law-firm-api
  - 📚 law-firm-common
  - 📝 law-firm-core
  - 📋 law-firm-model
  - 📁 law-firm-modules
    - 🔐 law-firm-auth
    - 🏢 law-firm-system
    - 👥 law-firm-personnel
    - 💼 law-firm-case
    - 📄 law-firm-contract
    - 👪 law-firm-client
    - 💰 law-firm-finance
    - 📑 law-firm-document
    - 📚 law-firm-knowledge
    - 📅 law-firm-schedule
    - ✅ law-firm-task
    - 📨 law-firm-archive
    - 📊 law-firm-analysis

## 数据库迁移规范

### 版本号分配

各模块的数据库迁移脚本版本号按照以下规则分配：

- **API层**: V0001-V0099
- **核心模块**: V0050-V0099
- **系统模块**: V1000-V1999
- **认证模块**: V2000-V2999
- **客户模块**: V4000-V4999
- **合同模块**: V6000-V6999
- **案例模块**: V7000-V7999
- **档案模块**: V12000-V12999
- 其他业务模块: 按照各自的版本号范围

### 表命名规范

1. **模块前缀原则**: 每个表名应以其所属模块名称作为前缀
   - 例如：案例模块的表应以 `case_` 开头
   - 例如：客户模块的表应以 `client_` 开头
   - 例如：档案模块的表应以 `archive_` 开头

2. **中间关联表**: 应放在主导业务的模块中
   - 例如：案例与客户的关联表应放在案例模块中，命名为 `case_client_relation`
   - 例如：合同与客户的关联表应放在合同模块中，命名为 `contract_client_relation`

### 模块职责划分

1. **职责清晰**: 每个模块只应包含与其核心业务直接相关的表
   - 客户模块: 只包含客户基本信息、联系方式、分类等
   - 案例模块: 包含案例信息及其直接关联的参与方、文档等
   - 合同模块: 包含合同信息及其直接关联的参与方、条款等

2. **避免重复**: 不同模块不应定义功能重叠的表
   - 错误示例: 客户模块中的 `case_party` 与案例模块中的 `case_participant`
   - 正确做法: 统一使用案例模块中的 `case_participant` 表

### 迁移脚本最佳实践

1. **脚本命名**: 使用有意义的名称描述脚本功能
   - 例如: `V7004__reconcile_participant_tables.sql`

2. **脚本注释**: 每个脚本开头应包含以下信息
   ```sql
   -- 模块: [模块名称]
   -- 版本: [版本号]
   -- 创建时间: [YYYY-MM-DD]
   -- 说明: [脚本功能描述]
   -- 依赖: [依赖的其他脚本版本号]
   ```

3. **数据迁移**: 当需要调整表结构或移动表时，确保数据不丢失
   - 使用临时表存储数据
   - 使用INSERT IGNORE避免重复数据
   - 添加审计日志记录变更

4. **错误处理**: 使用存储过程包装复杂操作，提供错误处理机制

5. **文档更新**: 重要的结构变更应同时更新模块文档

### 最近的结构调整

1. **V4003**: 从客户模块中移除 `case_party` 和 `contract_party` 表
2. **V6003**: 将客户模块中的合同当事人数据迁移到合同模块
3. **V7003**: 将客户模块中的案件当事人数据迁移到案例模块
4. **V7004**: 合并案例模块中的 `case_party` 和 `case_participant` 表
5. **V12004**: 将档案模块中的 `case_archive` 表重命名为 `archive_case`


...

## 子模块文档索引

- [law-firm-analysis](./law-firm-analysis/README.md)
- [law-firm-archive](./law-firm-archive/README.md)
- [law-firm-auth](./law-firm-auth/README.md)
- [law-firm-case](./law-firm-case/README.md)
- [law-firm-client](./law-firm-client/README.md)
- [law-firm-contract](./law-firm-contract/README.md)
- [law-firm-document](./law-firm-document/README.md)
- [law-firm-evidence](./law-firm-evidence/README.md)
- [law-firm-finance](./law-firm-finance/README.md)
- [law-firm-knowledge](./law-firm-knowledge/README.md)
- [law-firm-personnel](./law-firm-personnel/README.md)
- [law-firm-schedule](./law-firm-schedule/README.md)
- [law-firm-system](./law-firm-system/README.md)
- [law-firm-task](./law-firm-task/README.md)
