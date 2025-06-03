-- Auth模块职责说明脚本
-- 版本: V2000
-- 模块: Auth
-- 创建时间: 2023-07-10
-- 说明: 本脚本不执行实际的数据库操作，仅用于记录模块职责说明

-- 注意：这是一个职责划分的说明文件，不对数据库结构进行任何更改

/*
Auth模块职责说明：

1. Auth模块负责的表：
   以下表实际应由Auth模块负责，但目前部分表已在API层创建：
   - auth_user：用户表（已在API层创建）
   - auth_role：角色表（已在API层创建）
   - auth_permission：权限表（已在API层创建）
   - auth_role_permission：角色权限关联表（已在API层创建）
   - auth_user_role：用户角色关联表（已在API层创建）
   - auth_login_history：登录历史表（已在API层创建）
   - sys_permission_request：权限申请表（已在API层创建）
   - sys_team_permission：团队权限关联表（已在API层创建）

2. 现有脚本说明：
   - V2001__init_auth_tables.sql：原计划创建Auth模块表，但因表已在API层创建，现仅修改auth_permission表结构
   - V2002__init_auth_data.sql：初始化Auth模块基础数据

3. 后续开发规范：
   a) 对已存在表的修改：
      - 可以通过Auth模块脚本修改上述Auth相关表的结构和数据
      - 修改时需添加详细注释，说明为何在Auth模块修改这些表
   
   b) 新增Auth相关表：
      - 所有新增的Auth相关表应在Auth模块中创建
      - 遵循"auth_xxx"或明确的Auth相关命名规范

   c) 数据导入导出：
      - Auth模块负责上述表的数据导入导出逻辑
      - 确保数据迁移时Auth相关表的完整性

4. 开发建议：
   - 虽然表在API层创建，但业务逻辑应在Auth模块实现
   - 将来如进行架构重构，应考虑将这些表的创建迁移到Auth模块
*/

-- 此脚本不执行任何实际操作 