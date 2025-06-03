-- 系统管理模块基础数据
-- 版本: V1002
-- 模块: 系统管理模块 (V1000-V1999)
-- 创建时间: 2023-06-01
-- 说明: 系统管理功能的基础数据初始化
-- 包括：系统菜单、默认角色、组织架构、定时任务、监控配置

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统菜单数据 =======================

-- 一级菜单
INSERT INTO sys_menu (menu_name, menu_code, menu_type, path, component, icon, sort, create_by) VALUES
('系统管理', 'system', 1, '/system', NULL, 'system', 1000, 'system'),
('权限管理', 'auth', 1, '/auth', NULL, 'lock', 2000, 'system'),
('人员管理', 'personnel', 1, '/personnel', NULL, 'user', 3000, 'system'),
('案件管理', 'case', 1, '/case', NULL, 'folder', 4000, 'system'),
('监控中心', 'monitor', 1, '/monitor', NULL, 'monitor', 9000, 'system');

-- 获取一级菜单ID并插入子菜单
-- 系统管理子菜单
SET @system_menu_id = (SELECT id FROM sys_menu WHERE menu_code = 'system');
INSERT INTO sys_menu (parent_id, menu_name, menu_code, menu_type, path, component, perms, icon, sort, create_by) VALUES
(@system_menu_id, '菜单管理', 'system:menu', 2, '/system/menu', 'system/menu/index', 'system:menu:list', 'tree-table', 1010, 'system'),
(@system_menu_id, '角色管理', 'system:role', 2, '/system/role', 'system/role/index', 'system:role:list', 'peoples', 1020, 'system'),
(@system_menu_id, '组织管理', 'system:org', 2, '/system/org', 'system/org/index', 'system:org:list', 'tree', 1030, 'system'),
(@system_menu_id, '定时任务', 'system:job', 2, '/system/job', 'system/job/index', 'system:job:list', 'job', 1040, 'system'),
(@system_menu_id, '系统升级', 'system:upgrade', 2, '/system/upgrade', 'system/upgrade/index', 'system:upgrade:list', 'upload', 1050, 'system');

-- 监控管理子菜单
SET @monitor_menu_id = (SELECT id FROM sys_menu WHERE menu_code = 'monitor');
INSERT INTO sys_menu (parent_id, menu_name, menu_code, menu_type, path, component, perms, icon, sort, create_by) VALUES
(@monitor_menu_id, '监控配置', 'monitor:config', 2, '/monitor/config', 'monitor/config/index', 'monitor:config:list', 'monitor', 9010, 'system'),
(@monitor_menu_id, '监控数据', 'monitor:data', 2, '/monitor/data', 'monitor/data/index', 'monitor:data:list', 'chart', 9020, 'system'),
(@monitor_menu_id, '告警管理', 'monitor:alert', 2, '/monitor/alert', 'monitor/alert/index', 'monitor:alert:list', 'message', 9030, 'system');

-- 菜单按钮权限
-- 菜单管理按钮
SET @menu_manage_id = (SELECT id FROM sys_menu WHERE menu_code = 'system:menu');
INSERT INTO sys_menu (parent_id, menu_name, menu_code, menu_type, perms, sort, create_by) VALUES
(@menu_manage_id, '菜单查询', 'system:menu:query', 3, 'system:menu:query', 1, 'system'),
(@menu_manage_id, '菜单新增', 'system:menu:add', 3, 'system:menu:add', 2, 'system'),
(@menu_manage_id, '菜单修改', 'system:menu:edit', 3, 'system:menu:edit', 3, 'system'),
(@menu_manage_id, '菜单删除', 'system:menu:remove', 3, 'system:menu:remove', 4, 'system');

-- 角色管理按钮
SET @role_manage_id = (SELECT id FROM sys_menu WHERE menu_code = 'system:role');
INSERT INTO sys_menu (parent_id, menu_name, menu_code, menu_type, perms, sort, create_by) VALUES
(@role_manage_id, '角色查询', 'system:role:query', 3, 'system:role:query', 1, 'system'),
(@role_manage_id, '角色新增', 'system:role:add', 3, 'system:role:add', 2, 'system'),
(@role_manage_id, '角色修改', 'system:role:edit', 3, 'system:role:edit', 3, 'system'),
(@role_manage_id, '角色删除', 'system:role:remove', 3, 'system:role:remove', 4, 'system'),
(@role_manage_id, '分配权限', 'system:role:auth', 3, 'system:role:auth', 5, 'system');

-- 组织管理按钮
SET @org_manage_id = (SELECT id FROM sys_menu WHERE menu_code = 'system:org');
INSERT INTO sys_menu (parent_id, menu_name, menu_code, menu_type, perms, sort, create_by) VALUES
(@org_manage_id, '组织查询', 'system:org:query', 3, 'system:org:query', 1, 'system'),
(@org_manage_id, '组织新增', 'system:org:add', 3, 'system:org:add', 2, 'system'),
(@org_manage_id, '组织修改', 'system:org:edit', 3, 'system:org:edit', 3, 'system'),
(@org_manage_id, '组织删除', 'system:org:remove', 3, 'system:org:remove', 4, 'system');

-- ======================= 系统角色数据 =======================

INSERT INTO sys_role (role_name, role_code, role_type, data_scope, description, sort, create_by) VALUES
('超级管理员', 'SUPER_ADMIN', 1, 1, '系统超级管理员，拥有所有权限', 1, 'system'),
('系统管理员', 'SYSTEM_ADMIN', 1, 1, '系统管理员，负责系统配置和管理', 2, 'system'),
('监控管理员', 'MONITOR_ADMIN', 1, 2, '监控管理员，负责系统监控', 3, 'system'),
('普通用户', 'COMMON_USER', 2, 5, '普通业务用户', 10, 'system');

-- 超级管理员分配所有菜单权限
SET @super_admin_role_id = (SELECT id FROM sys_role WHERE role_code = 'SUPER_ADMIN');
INSERT INTO sys_role_menu (role_id, menu_id, create_by)
SELECT 
    @super_admin_role_id,
    id,
    'system'
FROM sys_menu;

-- 系统管理员分配系统管理相关权限
SET @system_admin_role_id = (SELECT id FROM sys_role WHERE role_code = 'SYSTEM_ADMIN');
INSERT INTO sys_role_menu (role_id, menu_id, create_by)
SELECT 
    @system_admin_role_id,
    sm.id,
    'system'
FROM sys_menu sm
WHERE sm.menu_code LIKE 'system%' 
   OR sm.parent_id IN (SELECT id FROM (SELECT id FROM sys_menu WHERE menu_code LIKE 'system%') tmp);

-- 监控管理员分配监控相关权限
SET @monitor_admin_role_id = (SELECT id FROM sys_role WHERE role_code = 'MONITOR_ADMIN');
INSERT INTO sys_role_menu (role_id, menu_id, create_by)
SELECT 
    @monitor_admin_role_id,
    sm.id,
    'system'
FROM sys_menu sm
WHERE sm.menu_code LIKE 'monitor%' 
   OR sm.parent_id IN (SELECT id FROM (SELECT id FROM sys_menu WHERE menu_code LIKE 'monitor%') tmp);

-- ======================= 组织架构数据 =======================

INSERT INTO sys_organization (org_name, org_code, org_type, leader_name, phone, email, sort, create_by, remark) VALUES
('律师事务所', 'LAW_FIRM', 1, '总负责人', '400-0000-0000', 'admin@lawfirm.com', 1, 'system', '律师事务所总部'),
('管理部门', 'MANAGEMENT', 2, '管理部长', '', '', 10, 'system', '行政管理部门'),
('业务部门', 'BUSINESS', 2, '业务部长', '', '', 20, 'system', '业务执行部门'),
('技术部门', 'TECHNOLOGY', 2, '技术部长', '', '', 30, 'system', '技术支持部门');

-- 设置部门层级关系
SET @law_firm_org_id = (SELECT id FROM sys_organization WHERE org_code = 'LAW_FIRM');
UPDATE sys_organization SET parent_id = @law_firm_org_id WHERE org_code IN ('MANAGEMENT', 'BUSINESS', 'TECHNOLOGY');

-- ======================= 定时任务数据 =======================

INSERT INTO sys_job (job_name, job_group, job_class, cron_expression, description, create_by) VALUES
('系统监控数据清理', 'SYSTEM', 'com.lawfirm.system.job.MonitorDataCleanJob', '0 0 2 * * ?', '每天凌晨2点清理过期监控数据', 'system'),
('系统日志归档', 'SYSTEM', 'com.lawfirm.system.job.LogArchiveJob', '0 0 3 * * ?', '每天凌晨3点归档系统日志', 'system'),
('定时任务状态检查', 'SYSTEM', 'com.lawfirm.system.job.JobStatusCheckJob', '0 */10 * * * ?', '每10分钟检查定时任务状态', 'system'),
('系统健康检查', 'MONITOR', 'com.lawfirm.system.job.SystemHealthCheckJob', '0 */5 * * * ?', '每5分钟检查系统健康状况', 'system');

-- ======================= 监控配置数据 =======================

INSERT INTO sys_monitor_config (monitor_type, monitor_name, monitor_target, threshold_warning, threshold_critical, remark, create_by) VALUES
-- 服务器监控
(1, 'CPU使用率监控', 'CPU_USAGE', 70.00, 90.00, '服务器CPU使用率监控', 'system'),
(1, '内存使用率监控', 'MEMORY_USAGE', 80.00, 95.00, '服务器内存使用率监控', 'system'),
(1, '磁盘使用率监控', 'DISK_USAGE', 80.00, 95.00, '服务器磁盘使用率监控', 'system'),

-- 应用监控
(2, 'JVM堆内存监控', 'JVM_HEAP_USAGE', 70.00, 85.00, 'JVM堆内存使用率监控', 'system'),
(2, '应用响应时间监控', 'APP_RESPONSE_TIME', 1000.00, 3000.00, '应用接口响应时间监控(毫秒)', 'system'),
(2, '应用错误率监控', 'APP_ERROR_RATE', 5.00, 10.00, '应用错误率监控(%)', 'system'),

-- 数据库监控
(3, '数据库连接数监控', 'DB_CONNECTION_COUNT', 80.00, 95.00, '数据库连接数监控', 'system'),
(3, '数据库慢查询监控', 'DB_SLOW_QUERY_COUNT', 10.00, 50.00, '数据库慢查询数量监控', 'system'),

-- 接口监控
(4, 'API响应时间监控', 'API_RESPONSE_TIME', 500.00, 2000.00, 'API接口响应时间监控(毫秒)', 'system'),
(4, 'API成功率监控', 'API_SUCCESS_RATE', 95.00, 90.00, 'API接口成功率监控(%)', 'system');

-- ======================= 系统升级记录 =======================

INSERT INTO sys_upgrade (version_from, version_to, upgrade_type, upgrade_status, upgrade_description, operator_name, create_by) VALUES
('0.0.0', '1.0.0', 3, 3, '系统初始化安装', '系统管理员', 'system'); 