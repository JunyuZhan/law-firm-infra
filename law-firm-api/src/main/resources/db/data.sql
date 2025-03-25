-- 插入默认管理员用户 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, email, status)
VALUES ('admin', '$2a$10$mH0YskQ8.q4V5VNzLDpE6OmKSi78Qp1Ld.H7fF5aKEi0BzShFfWjm', '系统管理员', 'admin@lawfirm.com', 1);

-- 插入默认角色
INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('管理员', 'ROLE_ADMIN', '系统管理员角色');

INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('律师', 'ROLE_LAWYER', '律师角色');

INSERT INTO sys_role (role_name, role_code, description) 
VALUES ('助理', 'ROLE_ASSISTANT', '助理角色');

-- 插入基础字典数据
INSERT INTO sys_dict (dict_name, dict_code, description)
VALUES ('用户状态', 'USER_STATUS', '用户状态字典'); 