-- System模块升级管理数据初始化
-- 版本: V1004
-- 模块: system/upgrade
-- 创建时间: 2023-06-11
-- 说明: 初始化系统升级相关的字典数据和权限

-- 添加升级相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '升级类型', 'sys_upgrade_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '升级状态', 'sys_upgrade_status', 0, 1, NOW(), 'system'),
(NULL, NULL, '补丁类型', 'sys_patch_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '补丁状态', 'sys_patch_status', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加升级类型字典项
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 升级类型
(NULL, NULL, 'sys_upgrade_type', '补丁升级', 'PATCH', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_type', '小版本升级', 'MINOR', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_type', '大版本升级', 'MAJOR', 3, 0, 0, NOW(), 'system'),

-- 升级状态
(NULL, NULL, 'sys_upgrade_status', '待升级', 'PENDING', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_status', '升级中', 'UPGRADING', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_status', '升级成功', 'SUCCESS', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_status', '升级失败', 'FAILED', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_upgrade_status', '已回滚', 'ROLLBACK', 5, 0, 0, NOW(), 'system'),

-- 补丁类型
(NULL, NULL, 'sys_patch_type', 'SQL补丁', 'SQL', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_patch_type', '脚本补丁', 'SCRIPT', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_patch_type', '文件补丁', 'FILE', 3, 0, 0, NOW(), 'system'),

-- 补丁状态
(NULL, NULL, 'sys_patch_status', '待执行', 'PENDING', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'sys_patch_status', '执行中', 'EXECUTING', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_patch_status', '执行成功', 'SUCCESS', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_patch_status', '执行失败', 'FAILED', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'sys_patch_status', '已回滚', 'ROLLBACK', 5, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加系统升级相关权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '系统升级', 'system:upgrade', 'menu', 1, 'upgrade', 'cloud-upload', 'system/upgrade/index', 11, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:upgrade');

-- 添加版本管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '版本管理', 'system:upgrade:version', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:upgrade'), 'version', 'system/upgrade/version', 'tag', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:upgrade:version');

-- 添加补丁管理权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '补丁管理', 'system:upgrade:patch', 'menu', (SELECT id FROM auth_permission WHERE code = 'system:upgrade'), 'patch', 'system/upgrade/patch', 'code', 2, 0, NOW(), 'system'
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