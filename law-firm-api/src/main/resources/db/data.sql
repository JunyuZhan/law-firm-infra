-- 插入管理员用户 (密码: admin)
INSERT INTO sys_user (id, username, password, real_name, email, status, creator, create_time) 
VALUES (1, 'admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统管理员', 'admin@lawfirm.com', 1, 1, CURRENT_TIMESTAMP);

-- 插入角色
INSERT INTO sys_role (id, name, code, sort, status, creator, create_time)
VALUES (1, '超级管理员', 'SUPER_ADMIN', 1, 1, 1, CURRENT_TIMESTAMP);
INSERT INTO sys_role (id, name, code, sort, status, creator, create_time)
VALUES (2, '普通用户', 'USER', 2, 1, 1, CURRENT_TIMESTAMP);

-- 绑定用户和角色
INSERT INTO sys_user_role (user_id, role_id, creator, create_time)
VALUES (1, 1, 1, CURRENT_TIMESTAMP);

-- 插入基础字典数据
INSERT INTO sys_dict (dict_name, dict_code, description)
VALUES ('用户状态', 'USER_STATUS', '用户状态字典');

-- 插入菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, visible, status, creator, create_time)
VALUES (1, 0, '系统管理', '/system', NULL, NULL, 0, 'system', 1, 1, 1, 1, CURRENT_TIMESTAMP);

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, visible, status, creator, create_time)
VALUES (2, 1, '用户管理', '/system/user', 'system/user/index', 'system:user:list', 1, 'user', 1, 1, 1, 1, CURRENT_TIMESTAMP);

INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, visible, status, creator, create_time)
VALUES (3, 1, '角色管理', '/system/role', 'system/role/index', 'system:role:list', 1, 'role', 2, 1, 1, 1, CURRENT_TIMESTAMP);

-- 角色菜单关联
INSERT INTO sys_role_menu (role_id, menu_id, creator, create_time)
VALUES (1, 1, 1, CURRENT_TIMESTAMP);

INSERT INTO sys_role_menu (role_id, menu_id, creator, create_time)
VALUES (1, 2, 1, CURRENT_TIMESTAMP);

INSERT INTO sys_role_menu (role_id, menu_id, creator, create_time)
VALUES (1, 3, 1, CURRENT_TIMESTAMP);

-- 插入默认存储桶
INSERT INTO storage_bucket (id, bucket_name, storage_type, status, created_time)
VALUES (1, 'default', 'LOCAL', 1, CURRENT_TIMESTAMP);

INSERT INTO storage_bucket (id, bucket_name, storage_type, status, created_time)
VALUES (2, 'documents', 'LOCAL', 1, CURRENT_TIMESTAMP);

INSERT INTO storage_bucket (id, bucket_name, storage_type, status, created_time)
VALUES (3, 'temp', 'LOCAL', 1, CURRENT_TIMESTAMP);

-- 添加超级管理员用户
INSERT INTO auth_user (username, password, email, mobile, status, employee_id, version, sort, deleted, create_time, create_by, update_time, update_by, remark)
VALUES ('admin', '$2a$10$SQtKkAyoIkJg9gWzUxWR5OK4r2yoRhXKEZGNVB.kBnjqrFJ0DC/FG', 'admin@lawfirm.com', '13800138000', 1, NULL, 0, 0, 0, NOW(), 'system', NOW(), 'system', '系统超级管理员')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加默认角色
INSERT INTO auth_role (name, code, status, version, sort, deleted, create_time, create_by, update_time, update_by, remark)
VALUES ('超级管理员', 'ROLE_ADMIN', 1, 0, 0, 0, NOW(), 'system', NOW(), 'system', '系统超级管理员角色')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 关联用户与角色
INSERT INTO auth_user_role (user_id, role_id, create_time, create_by)
SELECT 
    (SELECT id FROM auth_user WHERE username = 'admin'), 
    (SELECT id FROM auth_role WHERE code = 'ROLE_ADMIN'), 
    NOW(), 
    'system'
ON DUPLICATE KEY UPDATE create_time = NOW();

-- 添加系统权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, icon, path, component, sort)
VALUES 
    -- 系统管理
    ('系统管理', 'system:manage', 1, 0, 'setting', '/system', 'Layout', 1),
    ('用户管理', 'system:user', 1, 1, 'user', '/system/user', 'system/user/index', 1),
    ('角色管理', 'system:role', 1, 1, 'team', '/system/role', 'system/role/index', 2),
    ('权限管理', 'system:permission', 1, 1, 'key', '/system/permission', 'system/permission/index', 3),
    
    -- 用户管理权限
    ('用户查询', 'system:user:query', 3, 2, NULL, NULL, NULL, 1),
    ('用户创建', 'system:user:create', 3, 2, NULL, NULL, NULL, 2),
    ('用户编辑', 'system:user:update', 3, 2, NULL, NULL, NULL, 3),
    ('用户删除', 'system:user:delete', 3, 2, NULL, NULL, NULL, 4),
    
    -- 角色管理权限
    ('角色查询', 'system:role:query', 3, 3, NULL, NULL, NULL, 1),
    ('角色创建', 'system:role:create', 3, 3, NULL, NULL, NULL, 2),
    ('角色编辑', 'system:role:update', 3, 3, NULL, NULL, NULL, 3),
    ('角色删除', 'system:role:delete', 3, 3, NULL, NULL, NULL, 4),
    
    -- 权限管理权限
    ('权限查询', 'system:permission:query', 3, 4, NULL, NULL, NULL, 1),
    ('权限创建', 'system:permission:create', 3, 4, NULL, NULL, NULL, 2),
    ('权限编辑', 'system:permission:update', 3, 4, NULL, NULL, NULL, 3),
    ('权限删除', 'system:permission:delete', 3, 4, NULL, NULL, NULL, 4);

-- 为admin角色分配所有权限 (假设角色id为1，权限ids从1-20)
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 3);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 8);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 9);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 12);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 13);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 14);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 15);
INSERT INTO sys_role_permission (role_id, permission_id) VALUES (1, 16);

-- 初始化管理员用户
-- 用户名: admin
-- 密码: admin123 (BCrypt加密)
INSERT INTO auth_user (id, username, password, email, mobile, status, deleted, create_time, create_by)
SELECT 1, 'admin', '$2a$10$vXXfqH1iKJKvhBzUJKQLW.6QKxhOjc7aZWjvBhcj7ADYBRZAiLXvq', 'admin@lawfirm.com', '13800138000', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'admin');

-- 初始化角色
INSERT INTO auth_role (id, name, code, status, deleted, create_time, create_by)
SELECT 1, '超级管理员', 'ADMIN', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ADMIN');

-- 将管理员用户分配超级管理员角色
INSERT INTO auth_user_role (user_id, role_id, create_time)
SELECT 1, 1, NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user_role WHERE user_id = 1 AND role_id = 1); 