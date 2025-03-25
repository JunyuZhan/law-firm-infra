-- 初始化管理员用户 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, email, status) 
VALUES ('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '系统管理员', 'admin@lawfirm.com', 1)
ON DUPLICATE KEY UPDATE password = VALUES(password);

-- 初始化系统角色
INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('超级管理员', 'ROLE_ADMIN', '系统超级管理员角色') 
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('律师', 'ROLE_LAWYER', '律师角色') 
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('助理', 'ROLE_ASSISTANT', '助理角色') 
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('财务', 'ROLE_FINANCE', '财务角色') 
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name);

-- 为admin用户分配管理员角色
INSERT INTO sys_user_role (user_id, role_id)
SELECT 
    (SELECT id FROM sys_user WHERE username = 'admin'),
    (SELECT id FROM sys_role WHERE role_code = 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE user_id = VALUES(user_id);

-- 初始化基础权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, icon, path, component, sort)
VALUES ('系统管理', 'system:view', 1, 0, 'setting', '/system', 'Layout', 1)
ON DUPLICATE KEY UPDATE permission_name = VALUES(permission_name);

INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, icon, path, component, sort)
VALUES ('用户管理', 'system:user:view', 1, 
       (SELECT id FROM (SELECT id FROM sys_permission WHERE permission_code = 'system:view') as p), 
       'user', '/system/user', 'system/user/index', 1)
ON DUPLICATE KEY UPDATE permission_name = VALUES(permission_name);

INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, icon, path, component, sort)
VALUES ('角色管理', 'system:role:view', 1, 
       (SELECT id FROM (SELECT id FROM sys_permission WHERE permission_code = 'system:view') as p), 
       'team', '/system/role', 'system/role/index', 2)
ON DUPLICATE KEY UPDATE permission_name = VALUES(permission_name);

-- 为管理员角色分配所有权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 
    (SELECT id FROM sys_role WHERE role_code = 'ROLE_ADMIN'),
    id
FROM sys_permission
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id); 