# 律所管理系统业务模块设计文档

## 一、整体架构

### 1.1 业务模块组成
```
law-firm-modules/
├── law-firm-case     // 案件管理模块
├── law-firm-contract // 合同管理模块
├── law-firm-task     // 任务管理模块
├── law-firm-document // 文档管理模块
└── law-firm-finance  // 财务管理模块
```

### 1.2 模块依赖关系
- 所有业务模块都依赖于基础设施层：
```xml
<!-- 核心依赖 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
</dependency>

<!-- 工作流引擎 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-workflow</artifactId>
</dependency>
```

### 1.3 模块间调用关系
```
案件管理(law-firm-case)
  ├── 调用合同管理 - 查询案件相关合同
  ├── 调用任务管理 - 创建案件相关任务
  ├── 调用文档管理 - 管理案件相关文档
  └── 调用财务管理 - 查询案件收费情况

合同管理(law-firm-contract)
  ├── 调用案件管理 - 关联案件信息
  ├── 调用文档管理 - 存储合同文档
  └── 调用财务管理 - 关联收费信息

任务管理(law-firm-task)
  ├── 调用案件管理 - 关联案件信息
  └── 调用文档管理 - 关联任务文档

文档管理(law-firm-document)
  └── 被其他模块调用

财务管理(law-firm-finance)
  ├── 调用案件管理 - 关联案件信息
  └── 调用合同管理 - 关联合同信息
```

## 二、模块功能设计

### 2.1 案件管理模块 (law-firm-case)

#### 2.1.1 核心功能
- 案件基本信息管理
- 案件进度管理
- 案件团队管理
- 案件文档关联
- 案件财务关联

#### 2.1.2 数据模型
```sql
-- 案件基本信息
CREATE TABLE case_info (
    id VARCHAR(32) PRIMARY KEY,
    case_no VARCHAR(50),         -- 案件编号
    case_name VARCHAR(200),      -- 案件名称
    case_type VARCHAR(50),       -- 案件类型
    client_id VARCHAR(32),       -- 委托人ID
    opposing_party VARCHAR(200), -- 对方当事人
    court VARCHAR(100),          -- 经办法院
    filing_date DATE,           -- 立案日期
    status VARCHAR(20),         -- 案件状态
    description TEXT,           -- 案件描述
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);

-- 案件进度
CREATE TABLE case_progress (
    id VARCHAR(32) PRIMARY KEY,
    case_id VARCHAR(32),        -- 案件ID
    stage VARCHAR(50),          -- 阶段
    progress_desc TEXT,         -- 进展描述
    handler_id VARCHAR(32),     -- 处理人
    plan_date DATE,            -- 计划日期
    actual_date DATE,          -- 实际日期
    create_time DATETIME,
    create_user VARCHAR(32)     -- 创建人
);

-- 案件团队
CREATE TABLE case_team (
    id VARCHAR(32) PRIMARY KEY,
    case_id VARCHAR(32),        -- 案件ID
    user_id VARCHAR(32),        -- 成员ID
    role VARCHAR(20),           -- 角色(主办/协办)
    join_time DATETIME,
    status VARCHAR(20),         -- 状态
    create_user VARCHAR(32)     -- 创建人
);
```

#### 2.1.3 核心API
```http
# 案件管理
POST /api/cases/create           # 创建案件
PUT /api/cases/{id}             # 更新案件
GET /api/cases/{id}             # 获取案件详情
GET /api/cases/list             # 查询案件列表

# 案件进度
POST /api/cases/{id}/progress   # 更新案件进度
GET /api/cases/{id}/progress    # 获取案件进度

# 案件团队
POST /api/cases/{id}/team/add   # 添加团队成员
DELETE /api/cases/{id}/team     # 移除团队成员
```

### 2.2 合同管理模块 (law-firm-contract)

#### 2.2.1 核心功能
- 合同信息管理
- 合同审批流程
- 合同模板管理
- 合同档案管理
- 合同到期提醒

#### 2.2.2 数据模型
```sql
-- 合同信息
CREATE TABLE contract_info (
    id VARCHAR(32) PRIMARY KEY,
    contract_no VARCHAR(50),     -- 合同编号
    contract_name VARCHAR(200),  -- 合同名称
    contract_type VARCHAR(50),   -- 合同类型
    case_id VARCHAR(32),         -- 关联案件ID
    client_id VARCHAR(32),       -- 客户ID
    amount DECIMAL(15,2),        -- 合同金额
    sign_date DATE,             -- 签订日期
    expire_date DATE,           -- 到期日期
    status VARCHAR(20),         -- 合同状态
    content TEXT,               -- 合同内容
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);

-- 合同模板
CREATE TABLE contract_template (
    id VARCHAR(32) PRIMARY KEY,
    template_name VARCHAR(100),  -- 模板名称
    template_type VARCHAR(50),   -- 模板类型
    content TEXT,               -- 模板内容
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);
```

#### 2.2.3 核心API
```http
# 合同管理
POST /api/contracts/create      # 创建合同
PUT /api/contracts/{id}        # 更新合同
GET /api/contracts/{id}        # 获取合同详情
GET /api/contracts/list        # 查询合同列表

# 合同模板
POST /api/contracts/templates  # 创建合同模板
GET /api/contracts/templates   # 获取模板列表
```

### 2.3 任务管理模块 (law-firm-task)

#### 2.3.1 核心功能
- 任务创建分配
- 任务进度跟踪
- 任务协作管理
- 任务提醒
- 任务统计分析

#### 2.3.2 数据模型
```sql
-- 任务信息
CREATE TABLE task_info (
    id VARCHAR(32) PRIMARY KEY,
    task_name VARCHAR(200),     -- 任务名称
    task_type VARCHAR(50),      -- 任务类型
    case_id VARCHAR(32),        -- 关联案件ID
    priority INT,               -- 优先级
    assignee_id VARCHAR(32),    -- 负责人ID
    start_time DATETIME,        -- 开始时间
    end_time DATETIME,          -- 截止时间
    status VARCHAR(20),         -- 任务状态
    description TEXT,           -- 任务描述
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);

-- 任务进度
CREATE TABLE task_progress (
    id VARCHAR(32) PRIMARY KEY,
    task_id VARCHAR(32),        -- 任务ID
    progress INT,               -- 进度百分比
    progress_desc TEXT,         -- 进度描述
    update_time DATETIME,
    update_user VARCHAR(32)     -- 更新人
);
```

#### 2.3.3 核心API
```http
# 任务管理
POST /api/tasks/create         # 创建任务
PUT /api/tasks/{id}           # 更新任务
GET /api/tasks/{id}           # 获取任务详情
GET /api/tasks/list           # 查询任务列表

# 任务进度
POST /api/tasks/{id}/progress # 更新任务进度
GET /api/tasks/{id}/progress  # 获取任务进度
```

### 2.4 文档管理模块 (law-firm-document)

#### 2.4.1 核心功能
- 文档上传管理
- 文档分类管理
- 文档版本控制
- 文档权限控制
- 文档检索

#### 2.4.2 数据模型
```sql
-- 文档信息
CREATE TABLE doc_info (
    id VARCHAR(32) PRIMARY KEY,
    doc_name VARCHAR(200),      -- 文档名称
    doc_type VARCHAR(50),       -- 文档类型
    case_id VARCHAR(32),        -- 关联案件ID
    file_path VARCHAR(500),     -- 文件路径
    file_size BIGINT,          -- 文件大小
    version VARCHAR(20),        -- 版本号
    status VARCHAR(20),         -- 状态
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);

-- 文档权限
CREATE TABLE doc_permission (
    id VARCHAR(32) PRIMARY KEY,
    doc_id VARCHAR(32),         -- 文档ID
    user_id VARCHAR(32),        -- 用户ID
    permission_type INT,        -- 权限类型
    create_time DATETIME,
    create_user VARCHAR(32)     -- 创建人
);
```

#### 2.4.3 核心API
```http
# 文档管理
POST /api/documents/upload     # 上传文档
PUT /api/documents/{id}       # 更新文档信息
GET /api/documents/{id}       # 获取文档详情
GET /api/documents/list       # 查询文档列表

# 文档权限
POST /api/documents/{id}/permissions # 设置文档权限
GET /api/documents/{id}/permissions  # 获取文档权限
```

### 2.5 财务管理模块 (law-firm-finance)

#### 2.5.1 核心功能
- 收费管理
- 费用结算
- 发票管理
- 财务统计
- 对账管理

#### 2.5.2 数据模型
```sql
-- 收费记录
CREATE TABLE finance_charge (
    id VARCHAR(32) PRIMARY KEY,
    case_id VARCHAR(32),        -- 关联案件ID
    charge_type VARCHAR(50),    -- 收费类型
    amount DECIMAL(15,2),       -- 金额
    payment_status VARCHAR(20), -- 支付状态
    payment_time DATETIME,      -- 支付时间
    create_time DATETIME,
    update_time DATETIME,
    create_user VARCHAR(32),    -- 创建人
    update_user VARCHAR(32)     -- 更新人
);

-- 发票信息
CREATE TABLE finance_invoice (
    id VARCHAR(32) PRIMARY KEY,
    invoice_no VARCHAR(50),     -- 发票编号
    charge_id VARCHAR(32),      -- 关联收费ID
    invoice_type VARCHAR(50),   -- 发票类型
    amount DECIMAL(15,2),       -- 金额
    status VARCHAR(20),         -- 状态
    create_time DATETIME,
    create_user VARCHAR(32)     -- 创建人
);
```

#### 2.5.3 核心API
```http
# 收费管理
POST /api/finance/charges/create    # 创建收费记录
PUT /api/finance/charges/{id}       # 更新收费记录
GET /api/finance/charges/{id}       # 获取收费详情
GET /api/finance/charges/list       # 查询收费列表

# 发票管理
POST /api/finance/invoices/create   # 创建发票
GET /api/finance/invoices/{id}      # 获取发票详情
GET /api/finance/invoices/list      # 查询发票列表
```

## 三、工作流集成

### 3.1 工作流应用场景

1. 案件管理
   - 立案审批流程
   - 结案审批流程
   - 案件分配流程

2. 合同管理
   - 合同审批流程
   - 合同变更流程
   - 合同终止流程

3. 任务管理
   - 任务分配流程
   - 任务变更流程
   - 任务验收流程

4. 文档管理
   - 文档审批流程
   - 文档归档流程

5. 财务管理
   - 收费审批流程
   - 发票申请流程
   - 费用报销流程

### 3.2 工作流集成方式

所有业务模块通过依赖`core-workflow`来复用工作流引擎的能力：

```java
@Service
public class CaseService {
    @Autowired
    private WorkflowService workflowService;
    
    public void startCaseApproval(String caseId) {
        // 构建流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("caseId", caseId);
        variables.put("caseType", "民事案件");
        
        // 启动流程
        workflowService.startProcess(
            "case_approval",     // 流程定义KEY
            "CASE_" + caseId,    // 业务KEY
            variables           // 流程变量
        );
    }
}
```

## 四、注意事项

1. 数据库设计
   - 所有表都必须包含基础字段：id、create_time、update_time、create_user、update_user
   - 主键统一使用32位UUID
   - 时间类型统一使用DATETIME
   - 金额类型统一使用DECIMAL(15,2)

2. API设计
   - URL路径采用kebab-case命名
   - 请求参数采用camelCase命名
   - 分页参数统一使用current和size
   - 返回结果统一使用统一响应格式

3. 工作流使用
   - 业务模块不直接操作工作流引擎
   - 统一通过core-workflow模块提供的API进行操作
   - 工作流定义文件统一存放在resources/processes目录下

4. 代码规范
   - 遵循阿里巴巴Java开发手册
   - 使用统一的异常处理机制
   - 使用统一的日志记录方式
   - 重要业务代码必须有单元测试 