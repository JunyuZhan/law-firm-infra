# 律师事务所管理系统 - 数据库迁移指南

## 数据库环境要求

本项目数据库迁移脚本基于MySQL 8.0开发和测试，建议使用MySQL 8.0或更高版本。某些脚本使用了MySQL 8.0特有的语法，如：
- `CREATE INDEX IF NOT EXISTS` 
- `ADD CONSTRAINT IF NOT EXISTS`

使用较低版本的MySQL（如5.7）可能会导致迁移脚本执行失败。

## 数据库迁移规则

本项目使用Flyway进行数据库版本管理和迁移。为确保各模块迁移脚本顺利执行，请遵循以下规则：

### 1. 版本号规范

各模块使用的版本号前缀如下：

| 模块名称 | 版本号前缀 | 示例 |
|---------|----------|------|
| system | V1000xxx | V1000001__init_system_tables.sql |
| auth | V2000xxx | V2000001__init_auth_tables.sql |
| personnel | V3000xxx | V3000001__create_personnel_tables.sql |
| client | V4000xxx | V4000001__init_client_tables.sql |
| document | V5000xxx | V5000001__init_document_tables.sql |
| case | V6000xxx | V6000001__init_case_tables.sql |
| contract | V7000xxx | V7000001__init_contract_tables.sql |
| finance | V8000xxx | V8000001__init_finance_tables.sql |
| knowledge | V9000xxx | V9000001__init_knowledge_tables.sql |
| archive | V11000xxx | V11000001__init_archive_tables.sql |
| task | V12000xxx | V12000001__init_task_tables.sql |
| schedule | V13000xxx | V13000001__init_schedule_tables.sql |
| analysis | V16000xxx | V16000001__init_analysis_tables.sql |

### 2. 迁移脚本命名约定

迁移脚本必须按照以下格式命名：
```
V{版本号}__{描述}.sql
```

例如：
```
V1000001__init_system_tables.sql
```

### 3. 迁移脚本设计原则

为了避免表之间的循环依赖问题，迁移脚本应遵循以下设计原则：

1. **分阶段创建**：先创建表结构，后添加外键约束
2. **避免循环依赖**：在设计迁移脚本时，应确保没有循环依赖关系
3. **保持单一职责**：每个迁移脚本应该只负责一项特定任务
4. **跨模块约束集中处理**：跨模块的外键约束统一在指定版本中添加，避免散落在各个模块中

### 4. 模块依赖关系

以下是各模块之间的主要依赖关系：

```
system → auth → personnel → 其他业务模块
```

各模块细分依赖关系：

1. **系统核心**：
   - system (系统模块): 提供基础表如sys_user、sys_role等
   - auth (认证模块): 依赖system模块，提供认证相关功能

2. **人事组织**：
   - personnel (人事模块): 依赖auth和system模块
   - personnel包含两部分: 人员信息和组织结构

3. **业务模块**：
   - client (客户模块): 依赖personnel模块
   - case (案件模块): 依赖client和personnel模块
   - contract (合同模块): 依赖case、client和personnel模块
   - document (文档模块): 依赖case、contract和personnel模块
   - finance (财务模块): 依赖case、client和personnel模块
   - knowledge (知识库模块): 依赖personnel模块
   - task (任务模块): 依赖case和personnel模块
   - schedule (日程模块): 依赖task、case和personnel模块
   - archive (档案模块): 依赖case、client和personnel模块
   - analysis (分析模块): 依赖finance、case、task等模块

### 5. 表依赖关系图

以下是主要表之间的依赖关系：

```
                   +-------------+
                   |  sys_user   |<----+
                   +-------------+     |
                         ^             |
                         |             |
                         |             |
                   +-------------+     |
                   | personnel_  |     |
                   |   person    |     |
                   +-------------+     |
                         ^             |
                         |             |
                         |             |
 +----------------+    +-------------+ |
 | organization_  |<---| personnel_ | |
 | department     |    |  employee  | |
 +----------------+    +-------------+ |
         ^                   ^         |
         |                   |         |
         |                   |         |
 +----------------+    +-------------+ |
 | organization_  |    | personnel_ | |
 |   position     |<---+   lawyer   | |
 +----------------+    +-------------+ |
         ^                             |
         |                             |
         |                             |
 +----------------+    +-------------+ |
 | organization_  |<---| personnel_ | |
 |     team       |    |   staff    | |
 +----------------+    +-------------+ |
         ^                   ^         |
         |                   |         |
         |                   |         |
 +----------------+          |         |
 | organization_  |----------+---------+
 | team_member    |
 +----------------+

 业务模块表依赖:
 
 +-------------+     +-------------+     +-------------+
 |  client_    |<----| case_info   |<----| contract_   |
 |  info       |     |             |     | info        |
 +-------------+     +-------------+     +-------------+
        ^                  ^                   ^
        |                  |                   |
        |                  |                   |
 +-------------+     +-------------+     +-------------+
 | finance_    |     | task        |     | document_   |
 | records     |     |             |     | info        |
 +-------------+     +-------------+     +-------------+
                           ^
                           |
                     +-------------+
                     | schedule    |
                     |             |
                     +-------------+
```

### 6. 迁移脚本执行顺序

基于上述依赖关系，迁移脚本的执行顺序如下：

1. **V1000xxx**: 创建系统基础表(system模块)
2. **V2000xxx**: 创建认证模块表和约束(auth模块)
3. **V3000001**: 创建人事模块基础表(personnel模块，不包含外键约束)
4. **V3000003**: 创建组织模块基础表(personnel模块的organization部分，不包含外键约束)
5. **V3000004**: 添加人事和组织表之间的所有外键约束
6. **V4000xxx-V16000xxx**: 创建其他业务模块表(不含跨模块外键约束)
7. **V90000xx**: 添加所有跨模块外键约束(集中处理所有跨模块依赖)

### 7. 跨模块外键约束处理

为解决跨模块外键约束问题，我们采用以下策略：

1. **移除原有散落的跨模块约束**：
   - 移除finance模块中对personnel_employee的外键约束
   - 移除knowledge模块中对personnel_employee的外键约束
   - 移除auth模块中对personnel_person的外键约束

2. **创建统一的跨模块约束脚本**：
   - 创建V90000xx系列迁移脚本专门处理跨模块约束
   - 确保所有依赖表已经创建完成后再添加约束

3. **处理跨模块外键约束的执行策略**：
   - 如果启用外键约束会导致循环依赖，则使用触发器或应用层约束替代
   - 对于必须的外键约束，确保按正确顺序执行
   - 使用Flyway的out-of-order功能确保约束按照版本号顺序执行

### 8. Flyway 配置

所有模块使用统一的 Flyway 配置：

```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
    out-of-order: true
    table: flyway_schema_history
```

其中`out-of-order: true`是关键配置，允许Flyway按照版本号顺序而非创建时间顺序执行迁移脚本，这对于多模块项目尤为重要。

### 9. 重置数据库迁移

如需重置数据库迁移历史，请使用以下命令：

```bash
cd scripts
./db-utils.sh clear
```

这将删除 Flyway 历史表，允许重新执行所有迁移脚本。

### 10. 迁移工具

项目提供了迁移工具脚本，位于 `scripts/migration-utils.py`，可以用于：

- 重置迁移版本号
- 重命名迁移脚本
- 交换模块迁移版本

使用方法：

```bash
python migration-utils.py reset                        # 重置所有模块版本号
python migration-utils.py rename auth V3000 V2000      # 重命名auth模块版本号
python migration-utils.py swap auth personnel          # 交换两个模块的版本号
```

## 全局外键约束脚本

为了管理跨模块外键约束，我们需要创建一个全局外键约束脚本。建议创建一个新的模块或在现有模块中添加如下脚本：

```sql
-- V9000001__add_cross_module_constraints.sql

-- auth模块与personnel模块的外键约束
ALTER TABLE personnel_person 
ADD CONSTRAINT fk_person_user_id 
FOREIGN KEY (user_id) REFERENCES sys_user(id);

-- finance模块与personnel模块的外键约束
ALTER TABLE finance_expense_record 
ADD CONSTRAINT fk_expense_applicant 
FOREIGN KEY (applicant_id) REFERENCES personnel_employee(id);

ALTER TABLE finance_expense_record 
ADD CONSTRAINT fk_expense_approver 
FOREIGN KEY (approver_id) REFERENCES personnel_employee(id);

-- knowledge模块与personnel模块的外键约束
ALTER TABLE knowledge_document 
ADD CONSTRAINT fk_knowledge_author 
FOREIGN KEY (author_id) REFERENCES personnel_employee(id);

-- 添加其他跨模块外键约束...
```

## 注意事项

1. 修改迁移脚本前，请确保已备份数据库
2. 迁移脚本一旦提交到版本控制系统，禁止修改内容
3. 如需修改已执行的迁移脚本，请创建新的迁移脚本
4. 检查MySQL版本是否为8.0或更高版本：`./db-utils.sh check-version`
5. 建议在每次开发环境重置后，先执行`./db-utils.sh clear`清理Flyway历史记录
6. 避免将同一个外键约束添加到多个迁移脚本中，以防止冲突
7. 对于需要跨模块引用的表，考虑在应用层进行完整性约束，而不是数据库外键约束 