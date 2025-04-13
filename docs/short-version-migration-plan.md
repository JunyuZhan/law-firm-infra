# 短版本号统一计划

## 背景

项目中同时存在两种版本号格式：7位长版本号(如V1000001__)和4-5位短版本号(如V5000__)。为提高一致性和可维护性，决定统一采用短版本号格式。

## 转换规则

将7位版本号转换为4-5位版本号的规则如下：
- Vxyyzzz__ → Vxyz__
- 例如：V1000001__ → V1000__，V2000002__ → V2001__

## 需要重命名的文件

### 1. 系统模块

| 当前文件名 | 新文件名 | 
|------------|----------|
| V1000001__init_system_tables.sql | V1000__init_system_tables.sql |
| V1000002__add_system_config_data.sql | V1001__add_system_config_data.sql |
| V1000003__add_monitor_tables.sql | V1002__add_monitor_tables.sql |
| V1000004__add_alert_tables.sql | V1003__add_alert_tables.sql |
| V1000005__init_dict_data.sql | V1004__init_dict_data.sql |
| V1000006__add_is_system_column.sql | V1005__add_is_system_column.sql |

### 2. 认证模块

| 当前文件名 | 新文件名 |
|------------|----------|
| V2000001__init_auth_tables.sql | V2000__init_auth_tables.sql |
| V2000002__init_auth_data.sql | V2001__init_auth_data.sql |
| V2000003__init_business_permissions.sql | V2002__init_business_permissions.sql |
| V2000004__add_employee_constraint.sql | V2003__add_employee_constraint.sql |

### 3. 人事模块

| 当前文件名 | 新文件名 |
|------------|----------|
| V3000001__create_personnel_tables.sql | V3000__create_personnel_tables.sql |
| V3000002__add_employee_event_log.sql | V3001__add_employee_event_log.sql |
| V3000003__create_organization_tables.sql | V3002__create_organization_tables.sql |
| V3000004__add_foreign_key_constraints.sql | V3003__add_foreign_key_constraints.sql |
| V3000005__insert_position_data.sql | V3004__insert_position_data.sql |

### 4. 客户模块

| 当前文件名 | 新文件名 |
|------------|----------|
| V4000001__init_client_tables.sql | V4000__init_client_tables.sql |
| V4000002__init_client_data.sql | V4001__init_client_data.sql |

### 5. 全局约束模块

| 当前文件名 | 新文件名 |
|------------|----------|
| V9900001__add_cross_module_constraints.sql | V9900__add_cross_module_constraints.sql |

## 更新引用

需要更新引用的文件：

1. **auth模块的约束文件注释**
   - 文件：V2000004__add_employee_constraint.sql → V2003__add_employee_constraint.sql
   - 更新注释中的引用：V9900001__ → V9900__

2. **personnel模块的外键约束文件**
   - 文件：V3000004__add_foreign_key_constraints.sql → V3003__add_foreign_key_constraints.sql
   - 更新注释中的引用：V2000004__ → V2003__

## 实施步骤

1. **备份**：在进行任何更改前备份所有迁移脚本
2. **开发环境测试**：先在开发环境进行重命名和测试
3. **批量重命名**：使用脚本批量重命名文件
4. **更新引用**：更新所有相关引用
5. **检查验证**：验证所有文件命名和引用是否正确
6. **数据库测试**：在测试环境中验证迁移脚本执行正确

## 注意事项

1. 如果数据库迁移历史已包含长版本号记录，需要使用Flyway的repair功能
2. 确保开发团队了解版本号规则变更
3. 更新相关文档以反映新的版本号规则
4. 建议在一个专门的分支进行此操作并经过充分测试后再合并

## PowerShell重命名脚本

可以使用以下PowerShell脚本进行批量重命名：

```powershell
# 系统模块
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000001__init_system_tables.sql" "V1000__init_system_tables.sql"
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000002__add_system_config_data.sql" "V1001__add_system_config_data.sql"
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000003__add_monitor_tables.sql" "V1002__add_monitor_tables.sql"
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000004__add_alert_tables.sql" "V1003__add_alert_tables.sql"
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000005__init_dict_data.sql" "V1004__init_dict_data.sql"
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V1000006__add_is_system_column.sql" "V1005__add_is_system_column.sql"

# 认证模块
Rename-Item "law-firm-modules/law-firm-auth/src/main/resources/db/migration/V2000001__init_auth_tables.sql" "V2000__init_auth_tables.sql"
Rename-Item "law-firm-modules/law-firm-auth/src/main/resources/db/migration/V2000002__init_auth_data.sql" "V2001__init_auth_data.sql"
Rename-Item "law-firm-modules/law-firm-auth/src/main/resources/db/migration/V2000003__init_business_permissions.sql" "V2002__init_business_permissions.sql"
Rename-Item "law-firm-modules/law-firm-auth/src/main/resources/db/migration/V2000004__add_employee_constraint.sql" "V2003__add_employee_constraint.sql"

# 人事模块
Rename-Item "law-firm-modules/law-firm-personnel/src/main/resources/db/migration/V3000001__create_personnel_tables.sql" "V3000__create_personnel_tables.sql"
Rename-Item "law-firm-modules/law-firm-personnel/src/main/resources/db/migration/V3000002__add_employee_event_log.sql" "V3001__add_employee_event_log.sql"
Rename-Item "law-firm-modules/law-firm-personnel/src/main/resources/db/migration/V3000003__create_organization_tables.sql" "V3002__create_organization_tables.sql"
Rename-Item "law-firm-modules/law-firm-personnel/src/main/resources/db/migration/V3000004__add_foreign_key_constraints.sql" "V3003__add_foreign_key_constraints.sql"
Rename-Item "law-firm-modules/law-firm-personnel/src/main/resources/db/migration/V3000005__insert_position_data.sql" "V3004__insert_position_data.sql"

# 客户模块
Rename-Item "law-firm-modules/law-firm-client/src/main/resources/db/migration/V4000001__init_client_tables.sql" "V4000__init_client_tables.sql"
Rename-Item "law-firm-modules/law-firm-client/src/main/resources/db/migration/V4000002__init_client_data.sql" "V4001__init_client_data.sql"

# 全局约束模块
Rename-Item "law-firm-modules/law-firm-system/src/main/resources/db/migration/V9900001__add_cross_module_constraints.sql" "V9900__add_cross_module_constraints.sql"
``` 