/* 
 * API模块初始化数据
 * 版本号V0002，避免与认证模块V2xxx冲突
 */

-- 初始化系统用户
INSERT INTO sys_user (id, username, password, nickname, email, phone, status, avatar, deleted)
VALUES (1, 'admin', '$2a$10$UZfU5pKgK6H77NtQ0iJxV.GnCxiHKIOsHEFaH37RaWmVV5Ue9.SJO', '管理员', 'admin@example.com', '13800138000', 1, '', 0);

-- 初始化系统角色
INSERT INTO sys_role (id, role_name, role_code, description, status, business_role_type, data_scope, deleted)
VALUES (1, '超级管理员', 'SUPER_ADMIN', '系统超级管理员，拥有所有权限', 1, 'ADMIN', 0, 0),
       (2, '律所主任', 'DIRECTOR', '律所主任，拥有绝大部分权限', 1, 'DIRECTOR', 0, 0),
       (3, '合伙人', 'PARTNER', '律所合伙人，拥有团队相关权限', 1, 'PARTNER', 1, 0),
       (4, '律师', 'LAWYER', '普通律师', 1, 'LAWYER', 3, 0),
       (5, '实习生', 'TRAINEE', '实习律师', 1, 'TRAINEE', 3, 0),
       (6, '财务', 'FINANCE', '财务人员', 1, 'FINANCE', 1, 0);

-- 初始化用户角色关联
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1); 