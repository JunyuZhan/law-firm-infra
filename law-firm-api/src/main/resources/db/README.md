# 律师事务所管理系统 - 数据库脚本开发方案

## 一、总体设计

### 1. 数据库脚本分类

本系统的数据库脚本分为以下几类：

- **Docker初始化脚本**：位于`docker/scripts/mysql/init.sql`，用于Docker环境的首次启动
- **Flyway迁移脚本**：位于`law-firm-api/src/main/resources/db/migration`目录，用于版本化管理数据库结构
- **数据字典脚本**：位于`law-firm-api/src/main/resources/db/dict`目录，用于管理系统中的数据字典
- **示例数据脚本**：位于`law-firm-api/src/main/resources/db/example`目录，用于开发和测试环境初始化示例数据

### 2. 脚本管理策略

- 使用**Flyway**进行数据库版本管理
- 采用**模块化**设计，按业务模块划分迁移脚本
- 遵循**先表结构，后外键约束**的原则
- 跨模块依赖关系在**全局约束脚本**中统一管理

## 二、版本号规范

### 1. 版本号前缀划分

各模块使用的版本号前缀如下：

| 模块名称 | 版本号前缀 | 示例 |
|---------|----------|------|
| system | V1000xxx | V1000__init_system_tables.sql |
| auth | V2000xxx | V2000__init_auth_tables.sql |
| personnel | V3000xxx | V3000__create_personnel_tables.sql |
| client | V4000xxx | V4000__init_client_tables.sql |
| document | V5000xxx | V5000__init_document_tables.sql |
| case | V6000xxx | V6000__init_case_tables.sql |
| contract | V7000xxx | V7000__init_contract_tables.sql |
| finance | V8000xxx | V8000__init_finance_tables.sql |
| knowledge | V9000xxx | V9000__init_knowledge_tables.sql |
| 全局约束 | V9900xxx | V9900__add_cross_module_constraints.sql |
| archive | V11000xxx | V11000__init_archive_tables.sql |
| task | V12000xxx | V12000__init_task_tables.sql |
| schedule | V13000xxx | V13000__init_schedule_tables.sql |
| analysis | V16000xxx | V16000__init_analysis_tables.sql |

### 2. 版本号命名规则

迁移脚本必须按照以下格式命名：
```
V{版本号}__{描述}.sql
```

说明：
- 版本号：采用模块前缀+序号，如`1000`、`1001`等
- 描述：使用下划线连接的英文单词，描述脚本的主要用途

示例：
- `V1000__init_system_tables.sql` - 初始化系统表
- `V1001__add_system_config_data.sql` - 添加系统配置数据

## 三、目录结构

```
law-firm-api
└── src
    └── main
        └── resources
            └── db
                ├── README.md (本文档)
                ├── migration/ (Flyway迁移脚本)
                │   ├── V1000__init_system_tables.sql
                │   ├── V1001__add_system_config_data.sql
                │   ├── V2000__init_auth_tables.sql
                │   └── ...
                ├── dict/ (数据字典脚本)
                │   ├── sys_dict_type.sql
                │   └── sys_dict_data.sql
                └── example/ (示例数据)
                    ├── example_client.sql
                    ├── example_case.sql
                    └── ...
```

## 四、表设计规范

### 1. 命名规范

- **表名**：使用下划线命名法，模块前缀_表名，如：`sys_user`、`case_info`
- **字段名**：使用下划线命名法，如：`user_id`、`create_time`
- **主键**：统一使用`id`作为主键名
- **外键**：使用`表名_id`格式，如：`user_id`、`role_id`
- **索引**：
  - 主键索引：默认
  - 唯一索引：`uk_字段名`，如：`uk_username`
  - 普通索引：`idx_字段名`，如：`idx_create_time`

### 2. 数据类型规范

- **整型**：
  - 主键ID：`BIGINT`
  - 普通整数：`INT`
  - 状态标志：`TINYINT`
- **字符串**：
  - 短文本：`VARCHAR(长度)`
  - 长文本：`TEXT`
- **日期时间**：统一使用`DATETIME`
- **布尔值**：使用`TINYINT(1)`，0表示false，1表示true
- **小数**：使用`DECIMAL(总位数,小数位数)`

### 3. 通用字段

每个表应包含以下通用字段：

```sql
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
`create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`create_by` BIGINT COMMENT '创建人ID',
`update_by` BIGINT COMMENT '更新人ID',
`deleted` TINYINT DEFAULT 0 COMMENT '是否删除：0未删除，1已删除',
```

### 4. 注释规范

- 表必须有注释：`COMMENT='表描述'`
- 字段必须有注释：`COMMENT='字段描述'`
- 脚本文件顶部必须有注释说明脚本的用途、版本、创建时间等

## 五、迁移脚本开发规范

### 1. 基本原则

- **可重复执行**：脚本应当可以重复执行而不产生错误
- **幂等性**：多次执行同一脚本产生的结果应当一致
- **向后兼容**：新版本脚本不应破坏旧版本的功能
- **单一职责**：一个脚本只做一件事情
- **事务一致性**：一个脚本中的所有操作要么全部成功，要么全部失败

### 2. 表创建规范

创建表时应使用`IF NOT EXISTS`：

```sql
CREATE TABLE IF NOT EXISTS table_name (
  ...
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表描述';
```

### 3. 数据插入规范

插入数据时，应使用`INSERT ... SELECT ... WHERE NOT EXISTS`方式确保幂等性：

```sql
INSERT INTO sys_user (username, password, real_name, status)
SELECT 'admin', '$2a$10$encrypted_password', '系统管理员', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'admin');
```

### 4. 外键约束规范

- 模块内部表之间的外键约束，在模块的专门约束脚本中定义
- 跨模块的外键约束，在全局约束脚本中定义
- 添加外键约束时应使用`IF NOT EXISTS`：

```sql
ALTER TABLE child_table 
ADD CONSTRAINT IF NOT EXISTS fk_child_parent 
FOREIGN KEY (parent_id) REFERENCES parent_table(id);
```

## 六、模块依赖关系

### 1. 模块依赖顺序

系统模块间存在以下依赖关系：

```
system → auth → personnel → 其他业务模块
```

脚本执行顺序应遵循此依赖关系。

### 2. 详细依赖关系

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

## 七、测试与验证

### 1. 测试环境

- 在开发环境中使用MySQL 8.0测试所有脚本
- 使用Flyway命令行工具验证脚本的有效性
- 验证脚本是否可以从零开始构建完整的数据库

### 2. 常见问题排查

- 检查SQL语法错误
- 检查表名和字段名是否使用了MySQL保留字
- 检查外键约束关系是否正确
- 检查索引是否合理
- 验证脚本幂等性

## 八、维护与更新

### 1. 添加新表

- 为新表创建新的迁移脚本
- 遵循表设计规范和命名规范
- 确保表具有必要的索引

### 2. 修改现有表

- 创建新的迁移脚本进行表结构修改
- 使用ALTER TABLE语句修改表结构
- 确保修改操作不会导致数据丢失

### 3. 添加新模块

- 为新模块分配版本号前缀
- 确定新模块与已有模块的依赖关系
- 按照依赖关系创建迁移脚本

## 九、Flyway配置参考

```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
    out-of-order: true
    table: flyway_schema_history
    placeholders:
      schema: law_firm
```

参数说明：
- `enabled`: 启用Flyway
- `locations`: 迁移脚本位置
- `baseline-on-migrate`: 首次使用Flyway时自动创建基线
- `validate-on-migrate`: 每次迁移前验证脚本
- `out-of-order`: 允许不按顺序执行迁移脚本（模块化开发必须）
- `table`: Flyway元数据表名
- `placeholders`: 在SQL脚本中使用的占位符 

# 数据库脚本模块化说明

## 现有架构问题
当前数据库脚本存在模块职责不清晰的问题：

- API层的`V0001__init_base_tables.sql`和`V0002__init_base_data.sql`包含了过多不属于基础层的表结构和数据
- 各业务模块的脚本与API层脚本存在职责重叠

## 正确的模块化原则

### API层（law-firm-api）
应仅包含：
- 系统配置表（sys_config）
- 系统字典表（sys_dict, sys_dict_item）
- 基础日志表（sys_log_operation, sys_log_login）
- 存储相关表（storage_bucket, storage_file）

### Auth模块（law-firm-auth）
应负责：
- 用户表（auth_user）
- 角色表（auth_role）
- 权限表（auth_permission）
- 角色权限关联表（auth_role_permission）
- 用户角色关联表（auth_user_role）
- 登录历史表（auth_login_history）
- 权限申请表（sys_permission_request）
- 团队权限关联表（sys_team_permission）

### System模块（law-firm-system）
应负责：
- 系统监控相关表（sys_monitor_xxx）
- 系统升级相关表（sys_upgrade_xxx）

### 其他业务模块
- Personnel模块：人员和组织相关表
- Client模块：客户相关表
- Case模块：案件相关表
- ...

## 重构策略

由于数据库迁移脚本已经部署并执行，我们不能直接修改已有脚本。应采取以下策略：

1. **保留现有脚本**：所有已执行的脚本保持不变
2. **添加职责说明注释**：在每个模块的现有脚本中添加清晰的注释
3. **后续开发遵循职责划分**：新增表和字段严格遵循模块职责
4. **避免跨模块表操作**：模块脚本不应修改其他模块的表

## 未来迁移策略

将来如果需要进行完整的模块化重构，可以考虑：
1. 创建完整的数据库快照
2. 重新按模块组织脚本
3. 重建数据库

但这需要在系统维护窗口进行，并做好完整的备份和回滚计划。 