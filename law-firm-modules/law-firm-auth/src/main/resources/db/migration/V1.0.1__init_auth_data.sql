-- 初始化默认管理员用户
INSERT INTO `auth_user` (`id`, `username`, `password`, `email`, `mobile`, `status`, `create_time`, `update_time`, `deleted`)
VALUES (1, 'admin', '$2a$10$OeaHnFM/XUZ25bBnCMk9puGlSXmwRk0Y5KF/zXmQdafVY5pKxE0CW', 'admin@lawfirm.com', '13800000000', 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 初始化默认角色
INSERT INTO `auth_role` (`id`, `name`, `code`, `status`, `create_time`, `update_time`, `deleted`)
VALUES
(1, '系统管理员', 'admin', 1, NOW(), NOW(), 0),
(2, '律所主任', 'director', 1, NOW(), NOW(), 0),
(3, '合伙人律师', 'partner', 1, NOW(), NOW(), 0),
(4, '执业律师', 'lawyer', 1, NOW(), NOW(), 0),
(5, '实习律师', 'trainee', 1, NOW(), NOW(), 0),
(6, '行政/财务人员', 'clerk_finance', 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 初始化用户角色关联
INSERT INTO `auth_user_role` (`user_id`, `role_id`, `create_time`)
VALUES (1, 1, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 初始化基础权限
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES
-- 系统管理
(1, '系统管理', 'system', 'menu', 0, '/system', 'Layout', 'system', 1, 1, 1, NOW(), NOW(), 0),
(2, '用户管理', 'system:user', 'menu', 1, '/system/user', 'system/user/index', 'system:user:list', 1, 1, 1, NOW(), NOW(), 0),
(3, '角色管理', 'system:role', 'menu', 1, '/system/role', 'system/role/index', 'system:role:list', 1, 1, 2, NOW(), NOW(), 0),
(4, '权限管理', 'system:permission', 'menu', 1, '/system/permission', 'system/permission/index', 'system:permission:list', 1, 1, 3, NOW(), NOW(), 0),
-- 用户权限
(5, '用户查询', 'system:user:query', 'button', 2, '', '', 'system:user:query', 1, 1, 1, NOW(), NOW(), 0),
(6, '用户新增', 'system:user:add', 'button', 2, '', '', 'system:user:add', 1, 1, 2, NOW(), NOW(), 0),
(7, '用户修改', 'system:user:edit', 'button', 2, '', '', 'system:user:edit', 1, 1, 3, NOW(), NOW(), 0),
(8, '用户删除', 'system:user:remove', 'button', 2, '', '', 'system:user:remove', 1, 1, 4, NOW(), NOW(), 0),
-- 角色权限
(9, '角色查询', 'system:role:query', 'button', 3, '', '', 'system:role:query', 1, 1, 1, NOW(), NOW(), 0),
(10, '角色新增', 'system:role:add', 'button', 3, '', '', 'system:role:add', 1, 1, 2, NOW(), NOW(), 0),
(11, '角色修改', 'system:role:edit', 'button', 3, '', '', 'system:role:edit', 1, 1, 3, NOW(), NOW(), 0),
(12, '角色删除', 'system:role:remove', 'button', 3, '', '', 'system:role:remove', 1, 1, 4, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 初始化管理员角色权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
(1, 1, NOW()), (1, 2, NOW()), (1, 3, NOW()), (1, 4, NOW()), 
(1, 5, NOW()), (1, 6, NOW()), (1, 7, NOW()), (1, 8, NOW()),
(1, 9, NOW()), (1, 10, NOW()), (1, 11, NOW()), (1, 12, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW();