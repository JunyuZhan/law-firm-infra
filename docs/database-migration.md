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
| api | V0001xxx-V0049xxx | V0001__init_base_tables.sql |
| core | V0050xxx-V0099xxx | V0050__init_audit_tables.sql |
| system | V1000xxx | V1000__add_system_module_responsibility_notes.sql |
| auth | V2000xxx | V2000__add_auth_module_responsibility_notes.sql |
| personnel | V3000xxx | V3001__init_personnel_tables.sql |
| client | V4000xxx | V4001__init_client_tables.sql |
| document | V5000xxx | V5001__init_document_tables.sql |
| contract | V6000xxx | V6001__init_contract_tables.sql |
| case | V7000xxx | V7000__add_case_module_responsibility_notes.sql |
| finance | V8000xxx | V8000__add_finance_module_responsibility_notes.sql |
| knowledge | V9000xxx | V9001__init_knowledge_tables.sql |
| schedule | V10000xxx | V10001__init_schedule_tables.sql |
| task | V11000xxx | V11001__init_task_tables.sql |
| archive | V12000xxx | V12001__init_archive_tables.sql |
| analysis | V16000xxx | V16000001__init_analysis_tables.sql |
| 跨模块约束 | V90000xxx | V90000__add_cross_module_constraints.sql |

### 2. 迁移脚本命名约定

迁移脚本必须按照以下格式命名：
```
V{版本号}__{描述}.sql
```

例如：
```
V1000__add_system_module_responsibility_notes.sql
```

### 3. 迁移脚本设计原则

为了避免表之间的循环依赖问题，迁移脚本应遵循以下设计原则：

1. **分阶段创建**：先创建表结构，后添加外键约束
2. **避免循环依赖**：在设计迁移脚本时，应确保没有循环依赖关系
3. **保持单一职责**：每个迁移脚本应该只负责一项特定任务
4. **跨模块约束集中处理**：跨模块的外键约束统一在指定版本中添加，避免散落在各个模块中
5. **模块职责边界清晰**：每个表应该属于其对应的业务模块，不应在其他模块中定义

### 4. 模块职责划分

为确保系统设计的清晰性和可维护性，各模块职责边界必须明确：

1. **表命名规范**：
   - 每个模块的表应使用对应前缀，如case模块使用`case_`前缀
   - 避免在非相关模块中创建表，例如不应在client模块中创建`case_party`表

2. **已发现的职责划分问题**：
   - 客户模块(V4001__init_client_tables.sql)中创建了`case_party`表，应移至案例模块
   - 客户模块(V4001__init_client_tables.sql)中创建了`contract_party`表，应移至合同模块
   - 档案模块(V12001__init_archive_tables.sql)中创建了`case_archive`表，应移至案例模块或明确修改为`archive_case`
   - 案例模块(V7001__init_case_tables.sql)创建的`case_participant`表与client模块中的`case_party`表功能存在重叠，应合并或明确区分职责

3. **表命名不一致问题**：
   - 部分表命名不符合模块前缀约定，例如核心审计模块中的`t_audit_log`和`t_audit_record`应修改为`audit_log`和`audit_record`
   - 系统模块中的表应使用`sys_`前缀，但部分表（例如`auth_permission_request`）使用了错误的前缀
   - 工作流模块中的表命名混合使用了`wf_`和`workflow_`前缀，应统一为一种命名方式

4. **版本号混乱问题**：
   - 系统模块(system)的权限初始化脚本使用了V2003和V2004的版本号，这些版本号应属于认证模块(auth)
   - 部分模块职责说明脚本(如V7000)与实际的表结构脚本(如V7001)存在内容不一致的问题

### 5. 模块依赖关系

以下是各模块之间的主要依赖关系：

```
system → auth → personnel → 其他业务模块
```

各模块细分依赖关系：

1. **系统核心**：
   - api (API层): 提供最基础的系统表如sys_config、sys_dict等
   - core (核心模块): 提供核心功能如审计、存储、搜索、AI等
   - system (系统模块): 提供系统监控、升级等功能
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

### 6. 表依赖关系图

以下是主要表之间的依赖关系：

```
                   +-------------+
                   |  auth_user  |<----+
                   +-------------+     |
                         ^             |
                         |             |
                         |             |
                   +-------------+     |
                   | per_person  |     |
                   |             |     |
                   +-------------+     |
                         ^             |
                         |             |
                         |             |
 +----------------+    +-------------+ |
 | org_department |<---| per_employee| |
 |                |    |             | |
 +----------------+    +-------------+ |
         ^                   ^         |
         |                   |         |
         |                   |         |
 +----------------+    +-------------+ |
 | org_position   |    | per_employee| |
 |                |<---+   _position | |
 +----------------+    +-------------+ |
         ^                             |
         |                             |
         |                             |
 +----------------+    +-------------+ |
 | org_team       |<---| per_employee| |
 |                |    | _organization| |
 +----------------+    +-------------+ |
         ^                   ^         |
         |                   |         |
         |                   |         |
 +----------------+          |         |
 | per_employee_  |----------+---------+
 | organization   |
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
 | fin_account |     | work_task   |     | doc_info    |
 | fin_invoice |     |             |     |             |
 +-------------+     +-------------+     +-------------+
                           ^
                           |
                     +-------------+
                     | schedule_   |
                     | schedule    |
                     +-------------+
```

### 7. 迁移脚本执行顺序

基于上述依赖关系，迁移脚本的执行顺序如下：

1. **V0001xxx-V0049xxx**: 创建API层基础表
2. **V0050xxx-V0099xxx**: 创建核心模块表
3. **V1000xxx**: 创建系统基础表(system模块)
4. **V2000xxx**: 创建认证模块表和约束(auth模块)
5. **V3000xxx**: 创建人事和组织模块表
6. **V4000xxx-V16000xxx**: 创建其他业务模块表(不含跨模块外键约束)
7. **V90000xx**: 添加所有跨模块外键约束(集中处理所有跨模块依赖)

### 8. 跨模块外键约束处理

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

### 9. 存在的问题和解决方案

1. **模块职责边界问题**：
   - 问题：客户模块中包含了案例和合同模块的表
   - 解决方案：创建V7003__reconcile_case_party_tables.sql和V6003__reconcile_contract_party_tables.sql脚本，将这些表迁移到正确的模块

2. **表结构重复问题**：
   - 问题：`case_party`和`case_participant`表功能重叠
   - 解决方案：合并这两个表，保留功能更完整的`case_participant`表，并创建迁移脚本处理数据转移

3. **版本号错误问题**：
   - 问题：系统模块使用了认证模块的版本号前缀
   - 解决方案：创建新的迁移脚本，将这些权限初始化移至正确的版本号范围内

4. **命名不一致问题**：
   - 问题：表命名前缀不一致（如`t_audit_log`与模块的`audit_`前缀不符）
   - 解决方案：创建表重命名的迁移脚本，确保所有表名符合命名约定

### 10. Flyway 配置

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

### 11. 重置数据库迁移

如需重置数据库迁移历史，请使用以下命令：

```bash
cd scripts
./db-utils.sh clear
```

这将删除 Flyway 历史表，允许重新执行所有迁移脚本。

### 12. 迁移工具

项目提供了迁移工具脚本，位于 `scripts` 目录下：

- `db-utils.sh`: 数据库工具脚本
- `create-migration.sh`: 创建新的迁移脚本
- `validate-migrations.sh`: 验证迁移脚本是否符合规范