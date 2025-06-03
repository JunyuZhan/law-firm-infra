-- Auth模块初始数据
-- 版本: V2002
-- 模块: auth
-- 创建时间: 2023-06-15
-- 说明: 初始化认证模块所有数据，包括用户、角色、权限等
-- 注意：原V0002中的auth数据已移至此处

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建初始管理员用户（密码：admin123）
INSERT INTO auth_user (id, tenant_id, tenant_code, username, password, email, mobile, status, create_time, create_by, remark)
SELECT 1, NULL, NULL, 'admin', '$2y$10$x5Swziktu3TehpBImdvV3uJLtt9.zlHyP4qOYY6o8n6gDUhqhTpxy', 'admin@lawfirm.com', '13800000000', 0, NOW(), 'system', '系统内置管理员'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'admin');


-- 创建初始角色
INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 1, NULL, NULL, '系统管理员', 'ROLE_ADMIN', 0, 0, 'ADMIN', 0, NOW(), 'system', '系统内置角色，拥有所有权限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_ADMIN');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 2, NULL, NULL, '主任', 'ROLE_DIRECTOR', 0, 0, 'DIRECTOR', 0, NOW(), 'system', '律所主任，业务管理者'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_DIRECTOR');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 3, NULL, NULL, '合伙人', 'ROLE_PARTNER', 0, 0, 'PARTNER', 0, NOW(), 'system', '合伙人律师，高级律师'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_PARTNER');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 4, NULL, NULL, '律师', 'ROLE_LAWYER', 0, 2, 'LAWYER', 0, NOW(), 'system', '执业律师，办理案件及客户管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_LAWYER');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 5, NULL, NULL, '实习律师', 'ROLE_TRAINEE', 0, 2, 'TRAINEE', 0, NOW(), 'system', '实习律师，权限受限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_TRAINEE');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 6, NULL, NULL, '行政', 'ROLE_CLERK', 0, 1, 'CLERK', 0, NOW(), 'system', '行政人员，负责律所行政管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_CLERK');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 7, NULL, NULL, '财务', 'ROLE_FINANCE', 0, 1, 'FINANCE', 0, NOW(), 'system', '财务人员，负责律所财务管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_FINANCE');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 8, NULL, NULL, '普通用户', 'ROLE_USER', 0, 3, 'USER', 0, NOW(), 'system', '系统普通用户'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_USER');

-- 创建初始权限
-- 系统管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT 1, NULL, NULL, '系统管理', 'system', 0, 0, '/system', 'system', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system');

-- 用户管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 2, NULL, NULL, '用户管理', 'system:user', 0, 1, 'user', 'system/user/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user');

-- 角色管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 3, NULL, NULL, '角色管理', 'system:role', 0, 1, 'role', 'system/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:role');

-- 权限管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 4, NULL, NULL, '权限管理', 'system:permission', 0, 1, 'permission', 'system/permission/index', 'tree-table', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:permission');

-- 角色用户关联
INSERT INTO auth_user_role (tenant_id, tenant_code, user_id, role_id, status, create_time, create_by)
SELECT NULL, NULL, 1, 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user_role WHERE user_id = 1 AND role_id = 1);

INSERT INTO auth_user_role (tenant_id, tenant_code, user_id, role_id, status, create_time, create_by)
SELECT NULL, NULL, 2, 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user_role WHERE user_id = 2 AND role_id = 2);

-- 角色权限关联（系统管理员拥有所有权限）
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = permission.id);

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

-- 用户管理详细权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '查看用户', 'sys:user:read', 1, (SELECT id FROM auth_permission WHERE code = 'auth:user'), NULL, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:read');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '新增用户', 'sys:user:create', 1, (SELECT id FROM auth_permission WHERE code = 'auth:user'), NULL, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:create');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '修改用户', 'sys:user:update', 1, (SELECT id FROM auth_permission WHERE code = 'auth:user'), NULL, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:update');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '删除用户', 'sys:user:delete', 1, (SELECT id FROM auth_permission WHERE code = 'auth:user'), NULL, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:delete');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '用户管理', 'system:user:manage', 1, (SELECT id FROM auth_permission WHERE code = 'auth:user'), NULL, 'setting', 5, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user:manage');

-- 角色管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '角色管理', 'auth:role', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'role', 'auth/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:role');

-- 角色管理详细权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '查看角色', 'sys:role:read', 1, (SELECT id FROM auth_permission WHERE code = 'auth:role'), NULL, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:read');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '新增角色', 'sys:role:create', 1, (SELECT id FROM auth_permission WHERE code = 'auth:role'), NULL, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:create');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '修改角色', 'sys:role:update', 1, (SELECT id FROM auth_permission WHERE code = 'auth:role'), NULL, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:update');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '删除角色', 'sys:role:delete', 1, (SELECT id FROM auth_permission WHERE code = 'auth:role'), NULL, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:delete');

-- 权限管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '权限管理', 'auth:permission', 0, (SELECT id FROM auth_permission WHERE code = 'auth'), 'permission', 'auth/permission/index', 'tree-table', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:permission');

-- 权限管理详细权限
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '查看权限', 'sys:permission:read', 1, (SELECT id FROM auth_permission WHERE code = 'auth:permission'), NULL, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:read');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '新增权限', 'sys:permission:create', 1, (SELECT id FROM auth_permission WHERE code = 'auth:permission'), NULL, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:create');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '修改权限', 'sys:permission:update', 1, (SELECT id FROM auth_permission WHERE code = 'auth:permission'), NULL, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:update');

INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '删除权限', 'sys:permission:delete', 1, (SELECT id FROM auth_permission WHERE code = 'auth:permission'), NULL, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:delete');

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
WHERE (permission.code LIKE 'auth%' OR permission.code LIKE 'sys:%' OR permission.code LIKE 'system:%')
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );
  
-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 