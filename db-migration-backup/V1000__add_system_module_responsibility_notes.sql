-- System模块职责说明脚本
-- 版本: V1000
-- 模块: System
-- 创建时间: 2023-07-10
-- 说明: 本脚本不执行实际的数据库操作，仅用于记录模块职责说明

-- 注意：这是一个职责划分的说明文件，不对数据库结构进行任何更改

/*
System模块职责说明：

1. System模块负责的表：
   System模块应负责以下系统功能相关表的创建和维护：
   - 系统监控相关表（sys_monitor_xxx）
     * sys_app_monitor：应用监控表
     * sys_db_monitor：数据库监控表
     * sys_server_monitor：服务器监控表
     * sys_monitor_alert：监控告警表
     * sys_monitor_config：监控配置表
     * sys_monitor_data：监控数据表
   
   - 系统升级相关表
     * sys_upgrade：系统升级表
     * sys_patch：系统补丁表

2. 现有脚本说明：
   - V1001__init_system_monitor_tables.sql：创建系统监控相关表
   - V1002__init_system_monitor_data.sql：初始化系统监控相关数据
   - V1003__init_system_upgrade_tables.sql：创建系统升级相关表
   - V1004__init_system_upgrade_data.sql：初始化系统升级相关数据

3. 后续开发规范：
   a) 系统监控相关：
      - 所有新增的监控相关表应遵循"sys_monitor_xxx"命名规范
      - 监控数据统计和清理策略应在System模块内实现
   
   b) 系统升级相关：
      - 所有新增的升级相关表应有明确的命名
      - 版本管理和补丁发布逻辑应在System模块内实现
   
   c) 其他系统管理功能：
      - 未来新增的系统管理功能应在System模块内实现
      - 相关表应遵循"sys_xxx"命名规范，并在System模块创建

4. 开发建议：
   - 系统监控应与业务监控区分，业务监控应在各业务模块内实现
   - 系统模块应提供API供其他模块调用，避免其他模块直接操作System模块表
*/

-- 此脚本不执行任何实际操作 