-- System模块监控数据初始化
-- 版本: V1002
-- 模块: system/monitor
-- 创建时间: 2023-06-10
-- 说明: 初始化系统监控相关数据，包括监控配置和字典项

-- 添加监控相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '监控类型', 'sys_monitor_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '告警级别', 'sys_alert_level', 0, 1, NOW(), 'system'),
(NULL, NULL, '告警状态', 'sys_alert_status', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加监控类型字典项
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 监控类型
(NULL, NULL, 'sys_monitor_type', 'CPU使用率', 'CPU_USAGE', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_monitor_type', '内存使用率', 'MEMORY_USAGE', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_monitor_type', '磁盘使用率', 'DISK_USAGE', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_monitor_type', '数据库连接数', 'DB_CONNECTIONS', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_monitor_type', 'JVM内存使用', 'JVM_MEMORY', 5, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_monitor_type', '网络流量', 'NETWORK_TRAFFIC', 6, 0, 0, NOW(), 'system'),

-- 告警级别
(NULL, NULL, 'sys_alert_level', '信息', 'INFO', 1, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_alert_level', '警告', 'WARNING', 2, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_alert_level', '错误', 'ERROR', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_alert_level', '致命', 'FATAL', 4, 0, 0, NOW(), 'system'),

-- 告警状态
(NULL, NULL, 'sys_alert_status', '待处理', 'PENDING', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_alert_status', '处理中', 'HANDLING', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_alert_status', '已关闭', 'CLOSED', 3, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化监控配置（默认配置）
INSERT INTO sys_monitor_config (tenant_id, tenant_code, type, name, threshold, interval_seconds, enabled, description, status, create_time, create_by, remark)
VALUES
-- CPU监控
(NULL, NULL, 'CPU_USAGE', 'CPU使用率告警阈值', 80.0, 60, TRUE, 'CPU使用率超过80%触发告警', 0, NOW(), 'system', '默认CPU监控配置'),
-- 内存监控
(NULL, NULL, 'MEMORY_USAGE', '内存使用率告警阈值', 85.0, 60, TRUE, '内存使用率超过85%触发告警', 0, NOW(), 'system', '默认内存监控配置'),
-- 磁盘监控
(NULL, NULL, 'DISK_USAGE', '磁盘使用率告警阈值', 90.0, 300, TRUE, '磁盘使用率超过90%触发告警', 0, NOW(), 'system', '默认磁盘监控配置'),
-- 数据库连接监控
(NULL, NULL, 'DB_CONNECTIONS', '数据库连接使用率告警阈值', 80.0, 300, TRUE, '数据库连接使用率超过80%触发告警', 0, NOW(), 'system', '默认数据库连接监控配置'),
-- JVM内存监控
(NULL, NULL, 'JVM_MEMORY', 'JVM内存使用率告警阈值', 85.0, 60, TRUE, 'JVM内存使用率超过85%触发告警', 0, NOW(), 'system', '默认JVM内存监控配置')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加系统监控相关权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '系统监控', 'system:monitor', 'menu', 1, 'monitor', 'dashboard', 'system/monitor/index', 10, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor');

-- 添加服务器监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '服务器监控', 'system:monitor:server', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'server', 'system/monitor/server', 'server', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:server');

-- 添加应用监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '应用监控', 'system:monitor:app', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'app', 'system/monitor/app', 'app', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:app');

-- 添加数据库监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '数据库监控', 'system:monitor:db', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'db', 'system/monitor/db', 'database', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:db');

-- 添加告警管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '告警管理', 'system:monitor:alert', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'alert', 'system/monitor/alert', 'alert', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:alert');

-- 添加监控配置权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '监控配置', 'system:monitor:config', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'config', 'system/monitor/config', 'setting', 5, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:config');

-- 为管理员角色分配监控权限
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'system:monitor%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  ); 