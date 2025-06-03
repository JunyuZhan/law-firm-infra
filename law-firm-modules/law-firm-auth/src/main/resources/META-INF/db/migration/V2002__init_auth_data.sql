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
INSERT INTO auth_user (id, tenant_id, username, password, email, mobile, status, create_time, create_by, remark)
SELECT 1, NULL, 'admin', '$2y$10$x5Swziktu3TehpBImdvV3uJLtt9.zlHyP4qOYY6o8n6gDUhqhTpxy', 'admin@lawfirm.com', '13800000000', 0, NOW(), 'system', '系统内置管理员'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'admin');


-- 创建初始角色
INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 1, NULL, 'ROLE_ADMIN', '系统管理员', '系统内置角色，拥有所有权限', 0, 0, 1, 1, NOW(), 'system', '系统内置角色，拥有所有权限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_ADMIN');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 2, NULL, 'ROLE_DIRECTOR', '主任', '律所主任，业务管理者', 0, 0, 1, 2, NOW(), 'system', '律所主任，业务管理者'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_DIRECTOR');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 3, NULL, 'ROLE_PARTNER', '合伙人', '合伙人律师，高级律师', 0, 0, 1, 3, NOW(), 'system', '合伙人律师，高级律师'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_PARTNER');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 4, NULL, 'ROLE_LAWYER', '律师', '执业律师，办理案件及客户管理', 2, 0, 1, 4, NOW(), 'system', '执业律师，办理案件及客户管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_LAWYER');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 5, NULL, 'ROLE_TRAINEE', '实习律师', '实习律师，权限受限', 2, 0, 1, 5, NOW(), 'system', '实习律师，权限受限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_TRAINEE');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 6, NULL, 'ROLE_CLERK', '行政', '行政人员，负责律所行政管理', 1, 0, 1, 6, NOW(), 'system', '行政人员，负责律所行政管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_CLERK');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 7, NULL, 'ROLE_FINANCE', '财务', '财务人员，负责律所财务管理', 1, 0, 1, 7, NOW(), 'system', '财务人员，负责律所财务管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_FINANCE');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 8, NULL, 'ROLE_USER', '普通用户', '系统普通用户', 3, 0, 1, 8, NOW(), 'system', '系统普通用户'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_USER');

-- 创建初始权限
-- 系统管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, icon, sort, status, create_time, create_by)
SELECT 1, NULL, 0, 'system', '系统管理', 0, '/system', 'system', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system');

-- 用户管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT 2, NULL, 1, 'system:user', '用户管理', 0, 'user', 'system/user/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user');

-- 角色管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT 3, NULL, 1, 'system:role', '角色管理', 0, 'role', 'system/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:role');

-- 权限管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT 4, NULL, 1, 'system:permission', '权限管理', 0, 'permission', 'system/permission/index', 'tree-table', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:permission');

-- 角色用户关联
INSERT INTO auth_user_role (tenant_id, user_id, role_id, status, create_time, create_by)
SELECT NULL, 1, 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user_role WHERE user_id = 1 AND role_id = 1);

-- 角色权限关联（系统管理员拥有所有权限）
INSERT INTO auth_role_permission (tenant_id, role_id, permission_id, status, create_time, create_by)
SELECT NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = permission.id);

-- 认证授权菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, icon, sort, status, create_time, create_by)
SELECT NULL, 0, 'auth', '认证授权', 0, '/auth', 'lock', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth');

-- 用户管理菜单
SET @auth_menu_id = (SELECT id FROM auth_permission WHERE code = 'auth');
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, @auth_menu_id, 'auth:user', '用户管理', 0, 'user', 'auth/user/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:user');

-- 用户管理详细权限
SET @user_menu_id = (SELECT id FROM auth_permission WHERE code = 'auth:user');
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @user_menu_id, 'sys:user:read', '查看用户', 1, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:read');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @user_menu_id, 'sys:user:create', '新增用户', 1, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:create');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @user_menu_id, 'sys:user:update', '修改用户', 1, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:update');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @user_menu_id, 'sys:user:delete', '删除用户', 1, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:user:delete');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @user_menu_id, 'system:user:manage', '用户管理', 1, 'setting', 5, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user:manage');

-- 角色管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, @auth_menu_id, 'auth:role', '角色管理', 0, 'role', 'auth/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:role');

-- 角色管理详细权限
SET @role_menu_id = (SELECT id FROM auth_permission WHERE code = 'auth:role');
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @role_menu_id, 'sys:role:read', '查看角色', 1, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:read');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @role_menu_id, 'sys:role:create', '新增角色', 1, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:create');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @role_menu_id, 'sys:role:update', '修改角色', 1, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:update');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @role_menu_id, 'sys:role:delete', '删除角色', 1, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:role:delete');

-- 权限管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, @auth_menu_id, 'auth:permission', '权限管理', 0, 'permission', 'auth/permission/index', 'tree-table', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:permission');

-- 权限管理详细权限
SET @permission_menu_id = (SELECT id FROM auth_permission WHERE code = 'auth:permission');
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @permission_menu_id, 'sys:permission:read', '查看权限', 1, 'view', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:read');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @permission_menu_id, 'sys:permission:create', '新增权限', 1, 'add', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:create');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @permission_menu_id, 'sys:permission:update', '修改权限', 1, 'edit', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:update');

INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, icon, sort, status, create_time, create_by)
SELECT NULL, @permission_menu_id, 'sys:permission:delete', '删除权限', 1, 'delete', 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'sys:permission:delete');

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 