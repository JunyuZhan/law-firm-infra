-- System模块升级管理数据初始化
-- 版本: V1004
-- 模块: system/upgrade
-- 创建时间: 2023-06-11
-- 说明: 初始化系统升级相关的字典数据
-- 注意: 与auth_permission相关的权限初始化已移至V2004脚本

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