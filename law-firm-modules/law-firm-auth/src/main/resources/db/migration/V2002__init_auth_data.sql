-- Auth模块初始数据
-- 版本: V2002
-- 模块: auth
-- 创建时间: 2023-06-15
-- 说明: 初始化认证模块特有的基础数据（基本认证数据已在API层初始化）

-- 特别说明：
-- auth_permission表的type字段已在V2001脚本中修改为INT类型，各值含义如下：
-- 0: 菜单权限（原menu）
-- 1: 按钮权限（原operation）
-- 2: API权限（原data）
-- 所以下面的菜单添加语句中，type值已统一修改为数字类型（0表示菜单）

-- 添加权限相关字典
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '权限申请状态', 'auth_permission_request_status', 0, 1, NOW(), 'system'),
(NULL, NULL, '权限类型', 'auth_permission_type', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 权限申请状态
(NULL, NULL, 'auth_permission_request_status', '待审批', '0', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'auth_permission_request_status', '已批准', '1', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'auth_permission_request_status', '已拒绝', '2', 3, 0, 0, NOW(), 'system'),

-- 权限类型
(NULL, NULL, 'auth_permission_type', '菜单权限', '0', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'auth_permission_type', '按钮权限', '1', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'auth_permission_type', 'API权限', '2', 3, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加权限管理相关菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '认证授权', 'auth', 0, 0, '/auth', 'lock', NULL, 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth');

-- 用户管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '用户管理', 'auth:user', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'user', 'auth/user/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:user');

-- 角色管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '角色管理', 'auth:role', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'role', 'auth/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:role');

-- 权限管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '权限管理', 'auth:permission', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'permission', 'auth/permission/index', 'tree-table', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:permission');

-- 权限申请菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '权限申请', 'auth:permission:request', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'request', 'auth/permission/request', 'form', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:permission:request');

-- 登录历史菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '登录历史', 'auth:login:history', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'login-history', 'auth/login/history', 'time', 5, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:login:history');

-- 为管理员角色分配权限
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'auth%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  ); 