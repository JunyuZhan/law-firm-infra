-- System模块监控权限初始化
-- 版本: V2003
-- 模块: system/monitor
-- 创建时间: 2023-06-16
-- 说明: 初始化系统监控相关权限，从V1002脚本中移出
-- 依赖: V2001, V2002

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
-- 注意：下面的语句在Flyway执行时是有效的，如果在SQL编辑器中报错可以忽略
-- SET FOREIGN_KEY_CHECKS=0;

-- 添加系统监控相关权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '系统监控', 'system:monitor', 0, 1, 'monitor', 'dashboard', 'system/monitor/index', 10, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor');

-- 添加服务器监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '服务器监控', 'system:monitor:server', 0, (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'server', 'system/monitor/server', 'server', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:server');

-- 添加应用监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '应用监控', 'system:monitor:app', 0, (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'app', 'system/monitor/app', 'app', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:app');

-- 添加数据库监控权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '数据库监控', 'system:monitor:db', 0, (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'db', 'system/monitor/db', 'database', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:db');

-- 添加告警管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '告警管理', 'system:monitor:alert', 0, (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'alert', 'system/monitor/alert', 'alert', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:monitor:alert');

-- 添加监控配置权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '监控配置', 'system:monitor:config', 0, (SELECT id FROM auth_permission WHERE code = 'system:monitor'), 'config', 'system/monitor/config', 'setting', 5, 0, NOW(), 'system'
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
  
-- 恢复外键约束
-- 注意：下面的语句在Flyway执行时是有效的，如果在SQL编辑器中报错可以忽略
-- SET FOREIGN_KEY_CHECKS=1;