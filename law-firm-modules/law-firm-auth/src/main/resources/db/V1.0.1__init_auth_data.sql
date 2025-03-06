-- 初始化管理员用户（密码：admin123，加密后的值）
INSERT INTO auth_user (id, username, password, nickname, real_name, email, mobile, gender, status, user_type, created_time, updated_time)
VALUES (1, 'admin', '$2a$10$VgIUt0f3tSmS.CkYsYQP1eBENXAQDYAhw7y49GbpIGtMaVikO3A/m', '管理员', '系统管理员', 'admin@lawfirm.com', '13800138000', 1, 1, 1, 1, NOW(), NOW());

-- 初始化角色
INSERT INTO auth_role (id, role_name, role_code, description, status, created_time, updated_time)
VALUES
(1, '超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限', 1, NOW(), NOW()),
(2, '管理员', 'ADMIN', '系统管理员，拥有大部分权限', 1, NOW(), NOW()),
(3, '普通用户', 'USER', '普通用户，拥有基本权限', 1, NOW(), NOW());

-- 初始化权限（菜单）
INSERT INTO auth_permission (id, permission_name, permission_code, permission_type, parent_id, path, component, icon, sort_order, status, created_time, updated_time)
VALUES
-- 系统管理
(1, '系统管理', 'system', 1, 0, '/system', 'Layout', 'system', 1, 1, NOW(), NOW()),
-- 系统管理子菜单
(11, '用户管理', 'system:user', 1, 1, '/system/user', 'system/user/index', 'user', 1, 1, NOW(), NOW()),
(12, '角色管理', 'system:role', 1, 1, '/system/role', 'system/role/index', 'peoples', 2, 1, NOW(), NOW()),
(13, '权限管理', 'system:permission', 1, 1, '/system/permission', 'system/permission/index', 'tree-table', 3, 1, NOW(), NOW()),
(14, '部门管理', 'system:department', 1, 1, '/system/department', 'system/department/index', 'tree', 4, 1, NOW(), NOW()),
(15, '职位管理', 'system:position', 1, 1, '/system/position', 'system/position/index', 'post', 5, 1, NOW(), NOW()),
(16, '系统配置', 'system:config', 1, 1, '/system/config', 'system/config/index', 'edit', 6, 1, NOW(), NOW()),
(17, '用户组管理', 'system:userGroup', 1, 1, '/system/user-group', 'system/user-group/index', 'team', 7, 1, NOW(), NOW()),
-- 日志管理
(2, '日志管理', 'log', 1, 0, '/log', 'Layout', 'log', 2, 1, NOW(), NOW()),
-- 日志管理子菜单
(21, '登录日志', 'log:login', 1, 2, '/log/login', 'log/login/index', 'logininfor', 1, 1, NOW(), NOW()),
(22, '操作日志', 'log:operation', 1, 2, '/log/operation', 'log/operation/index', 'form', 2, 1, NOW(), NOW()),

-- 按钮权限
-- 用户管理按钮
(100, '用户查询', 'system:user:list', 2, 11, '', '', '', 1, 1, NOW(), NOW()),
(101, '用户新增', 'system:user:add', 2, 11, '', '', '', 2, 1, NOW(), NOW()),
(102, '用户修改', 'system:user:edit', 2, 11, '', '', '', 3, 1, NOW(), NOW()),
(103, '用户删除', 'system:user:remove', 2, 11, '', '', '', 4, 1, NOW(), NOW()),
(104, '用户导出', 'system:user:export', 2, 11, '', '', '', 5, 1, NOW(), NOW()),
(105, '用户导入', 'system:user:import', 2, 11, '', '', '', 6, 1, NOW(), NOW()),
(106, '重置密码', 'system:user:resetPwd', 2, 11, '', '', '', 7, 1, NOW(), NOW()),
-- 角色管理按钮
(110, '角色查询', 'system:role:list', 2, 12, '', '', '', 1, 1, NOW(), NOW()),
(111, '角色新增', 'system:role:add', 2, 12, '', '', '', 2, 1, NOW(), NOW()),
(112, '角色修改', 'system:role:edit', 2, 12, '', '', '', 3, 1, NOW(), NOW()),
(113, '角色删除', 'system:role:remove', 2, 12, '', '', '', 4, 1, NOW(), NOW()),
(114, '角色导出', 'system:role:export', 2, 12, '', '', '', 5, 1, NOW(), NOW()),
-- 权限管理按钮
(120, '权限查询', 'system:permission:list', 2, 13, '', '', '', 1, 1, NOW(), NOW()),
(121, '权限新增', 'system:permission:add', 2, 13, '', '', '', 2, 1, NOW(), NOW()),
(122, '权限修改', 'system:permission:edit', 2, 13, '', '', '', 3, 1, NOW(), NOW()),
(123, '权限删除', 'system:permission:remove', 2, 13, '', '', '', 4, 1, NOW(), NOW()),
-- 部门管理按钮
(130, '部门查询', 'system:department:list', 2, 14, '', '', '', 1, 1, NOW(), NOW()),
(131, '部门新增', 'system:department:add', 2, 14, '', '', '', 2, 1, NOW(), NOW()),
(132, '部门修改', 'system:department:edit', 2, 14, '', '', '', 3, 1, NOW(), NOW()),
(133, '部门删除', 'system:department:remove', 2, 14, '', '', '', 4, 1, NOW(), NOW()),
-- 职位管理按钮
(140, '职位查询', 'system:position:list', 2, 15, '', '', '', 1, 1, NOW(), NOW()),
(141, '职位新增', 'system:position:add', 2, 15, '', '', '', 2, 1, NOW(), NOW()),
(142, '职位修改', 'system:position:edit', 2, 15, '', '', '', 3, 1, NOW(), NOW()),
(143, '职位删除', 'system:position:remove', 2, 15, '', '', '', 4, 1, NOW(), NOW()),
-- 用户组管理按钮
(150, '用户组查询', 'system:userGroup:list', 2, 17, '', '', '', 1, 1, NOW(), NOW()),
(151, '用户组新增', 'system:userGroup:add', 2, 17, '', '', '', 2, 1, NOW(), NOW()),
(152, '用户组修改', 'system:userGroup:edit', 2, 17, '', '', '', 3, 1, NOW(), NOW()),
(153, '用户组删除', 'system:userGroup:remove', 2, 17, '', '', '', 4, 1, NOW(), NOW()),
(154, '用户组导出', 'system:userGroup:export', 2, 17, '', '', '', 5, 1, NOW(), NOW());

-- 用户与角色关联
INSERT INTO auth_user_role (user_id, role_id, created_time)
VALUES (1, 1, NOW());

-- 角色与权限关联
-- 超级管理员拥有所有权限
INSERT INTO auth_role_permission (role_id, permission_id, created_time)
SELECT 1, id, NOW() FROM auth_permission;

-- 管理员拥有部分权限
INSERT INTO auth_role_permission (role_id, permission_id, created_time)
SELECT 2, id, NOW() FROM auth_permission WHERE permission_code NOT LIKE '%remove%';

-- 普通用户只有查询权限
INSERT INTO auth_role_permission (role_id, permission_id, created_time)
SELECT 3, id, NOW() FROM auth_permission WHERE permission_code LIKE '%list%' OR permission_type = 1; 