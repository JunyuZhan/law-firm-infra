-- 模块职责说明脚本
-- 版本: V0003
-- 模块: API
-- 创建时间: 2023-07-10
-- 说明: 本脚本不执行实际的数据库操作，仅用于记录模块职责说明

-- 注意：这是一个职责划分的说明文件，不对数据库结构进行任何更改

/*
模块职责说明：

1. API层（law-firm-api）表职责划分：
   API层应仅包含以下系统基础表：
   - sys_config：系统配置表
   - sys_dict, sys_dict_item：系统字典表
   - sys_log_operation, sys_log_login：基础日志表
   - storage_bucket, storage_file：存储相关表

2. Auth模块（law-firm-auth）表职责划分：
   以下表实际应属于Auth模块，但目前已在API层创建：
   - auth_user：用户表
   - auth_role：角色表
   - auth_permission：权限表
   - auth_role_permission：角色权限关联表
   - auth_user_role：用户角色关联表
   - auth_login_history：登录历史表
   - sys_permission_request：权限申请表
   - sys_team_permission：团队权限关联表

3. System模块（law-firm-system）表职责划分：
   以下表类型应属于System模块，未来相关表的创建应在System模块中进行：
   - sys_monitor_xxx：系统监控相关表
   - sys_upgrade_xxx：系统升级相关表

4. 各业务模块表职责划分：
   - Personnel模块：人员和组织相关表
   - Client模块：客户相关表
   - Case模块：案件相关表
   - 其他业务模块：各自负责相关业务表

后续开发注意事项：
1. 新表创建应严格遵循模块职责边界
2. 避免跨模块表操作
3. 各模块迁移脚本只操作本模块负责的表
4. 如需操作其他模块的表，应通过API或服务调用实现
*/

-- 此脚本不执行任何实际操作 