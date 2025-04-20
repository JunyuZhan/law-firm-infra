-- System模块升级管理权限初始化
-- 版本: V2004
-- 模块: system/upgrade
-- 创建时间: 2023-06-16
-- 说明: 初始化系统升级相关的权限，从V1004脚本中移出
-- 依赖: V2001, V2002, V2003

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
-- 注意：下面的语句在Flyway执行时是有效的，如果在SQL编辑器中报错可以忽略
-- SET FOREIGN_KEY_CHECKS=0;

-- 添加系统升级相关权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '系统升级', 'system:upgrade', 0, 1, 'upgrade', 'cloud-upload', 'system/upgrade/index', 11, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:upgrade');

-- 添加版本管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '版本管理', 'system:upgrade:version', 0, (SELECT id FROM auth_permission WHERE code = 'system:upgrade'), 'version', 'system/upgrade/version', 'tag', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:upgrade:version');

-- 添加补丁管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '补丁管理', 'system:upgrade:patch', 0, (SELECT id FROM auth_permission WHERE code = 'system:upgrade'), 'patch', 'system/upgrade/patch', 'code', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:upgrade:patch');

-- 为管理员角色分配升级权限
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'system:upgrade%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );
  
-- 恢复外键约束
-- 注意：下面的语句在Flyway执行时是有效的，如果在SQL编辑器中报错可以忽略
-- SET FOREIGN_KEY_CHECKS=1; 