-- Auth模块初始数据
-- 版本: V2002
-- 模块: auth
-- 创建时间: 2023-06-15
-- 说明: 初始化认证模块所有数据，包括用户、角色、权限等，适配重构后的表结构

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建初始管理员用户（密码：admin123）
INSERT INTO auth_user (id, tenant_id, username, password, email, mobile, real_name, status, create_time, create_by, remark)
SELECT 1, 0, 'admin', '$2y$10$x5Swziktu3TehpBImdvV3uJLtt9.zlHyP4qOYY6o8n6gDUhqhTpxy', 'admin@lawfirm.com', '13800000000', '系统管理员', 1, NOW(), 'system', '系统内置管理员'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'admin');

-- 创建初始角色
INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 1, 0, 'ROLE_ADMIN', '系统管理员', '系统内置角色，拥有所有权限', 1, 1, 1, 0, NOW(), 'system', '系统内置角色，拥有所有权限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_ADMIN');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 2, 0, 'ROLE_DIRECTOR', '主任', '律所主任，业务管理者', 1, 1, 0, 1, NOW(), 'system', '律所主任，业务管理者'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_DIRECTOR');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 3, 0, 'ROLE_PARTNER', '合伙人', '合伙人律师，高级律师', 1, 1, 0, 2, NOW(), 'system', '合伙人律师，高级律师'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_PARTNER');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 4, 0, 'ROLE_LAWYER', '律师', '执业律师，办理案件及客户管理', 2, 1, 0, 3, NOW(), 'system', '执业律师，办理案件及客户管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_LAWYER');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 5, 0, 'ROLE_TRAINEE', '实习律师', '实习律师，权限受限', 3, 1, 0, 4, NOW(), 'system', '实习律师，权限受限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_TRAINEE');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 6, 0, 'ROLE_CLERK', '行政', '行政人员，负责律所行政管理', 2, 1, 0, 5, NOW(), 'system', '行政人员，负责律所行政管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_CLERK');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 7, 0, 'ROLE_FINANCE', '财务', '财务人员，负责律所财务管理', 2, 1, 0, 6, NOW(), 'system', '财务人员，负责律所财务管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_FINANCE');

INSERT INTO auth_role (id, tenant_id, code, name, description, data_scope, status, is_system, sort, create_time, create_by, remark)
SELECT 8, 0, 'ROLE_USER', '普通用户', '系统普通用户', 3, 1, 0, 7, NOW(), 'system', '系统普通用户'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_USER');

-- 创建基础权限（使用重构后的表结构）
-- 系统管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, icon, sort, status, create_time, create_by, remark)
SELECT 1, 0, 0, 'system', '系统管理', 1, '/system', 'system', 1, 1, NOW(), 'system', '系统管理顶级菜单'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system');

-- 用户管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 2, 0, 1, 'system:user', '用户管理', 1, 'user', 'system/user/index', 'user', 1, 1, NOW(), 'system', '用户管理菜单'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user');

-- 角色管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 3, 0, 1, 'system:role', '角色管理', 1, 'role', 'system/role/index', 'peoples', 2, 1, NOW(), 'system', '角色管理菜单'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:role');

-- 权限管理权限
INSERT INTO auth_permission (id, tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 4, 0, 1, 'system:permission', '权限管理', 1, 'permission', 'system/permission/index', 'tree-table', 3, 1, NOW(), 'system', '权限管理菜单'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:permission');

-- 认证授权菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, icon, sort, status, create_time, create_by, remark)
SELECT 0, 0, 'auth', '认证授权', 1, '/auth', 'lock', 2, 1, NOW(), 'system', '认证授权顶级菜单'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth');

-- 用户管理菜单
SET @auth_menu_id = (SELECT id FROM auth_permission WHERE code = 'auth');
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, @auth_menu_id, 'auth:user', '用户管理', 1, 'user', 'auth/user/index', 'user', 1, 1, NOW(), 'system', '用户管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'auth:user');

-- 用户角色关联
INSERT INTO auth_user_role (tenant_id, user_id, role_id, status, create_time, create_by, remark)
SELECT 0, 1, 1, 1, NOW(), 'system', '管理员默认角色'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user_role WHERE user_id = 1 AND role_id = 1);

-- 角色权限关联（系统管理员拥有所有权限）
INSERT INTO auth_role_permission (tenant_id, role_id, permission_id, status, create_time, create_by, remark)
SELECT 0, 1, permission.id, 1, NOW(), 'system', '管理员默认权限'
FROM auth_permission permission
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = permission.id);

-- 重新设置外键检查
SET FOREIGN_KEY_CHECKS = 1; 