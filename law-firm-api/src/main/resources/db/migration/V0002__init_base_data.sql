-- 系统模块初始化数据
-- 版本: V0002
-- 模块: API
-- 创建时间: 2023-06-02
-- 说明: 初始化系统基础数据，包括管理员账户、角色、权限、配置等
-- 根据实体类定义的表结构创建初始化数据

-- 创建初始管理员用户（密码：admin123）
INSERT INTO auth_user (id, tenant_id, tenant_code, username, password, email, mobile, status, create_time, create_by, remark)
SELECT 1, NULL, NULL, 'admin', '$2a$10$x70nSjQ/j5MzV9t.ZBRQAOokoMnLkDsJCnq6HT45mh3ezEh9jzJ7i', 'admin@lawfirm.com', '13800000000', 0, NOW(), 'system', '系统内置管理员'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'admin');

-- 创建测试律师用户（密码：admin123）
INSERT INTO auth_user (id, tenant_id, tenant_code, username, password, email, mobile, status, create_time, create_by, remark)
SELECT 2, NULL, NULL, 'lawyer', '$2a$10$x70nSjQ/j5MzV9t.ZBRQAOokoMnLkDsJCnq6HT45mh3ezEh9jzJ7i', 'lawyer@lawfirm.com', '13800000001', 0, NOW(), 'admin', '测试用律师账号'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_user WHERE username = 'lawyer');

-- 创建初始角色
INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 1, NULL, NULL, '系统管理员', 'ROLE_ADMIN', 0, 0, 'ADMIN', 0, NOW(), 'system', '系统内置角色，拥有所有权限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_ADMIN');

INSERT INTO auth_role (id, tenant_id, tenant_code, name, code, type, data_scope, business_role_type, status, create_time, create_by, remark)
SELECT 2, NULL, NULL, '律师', 'ROLE_LAWYER', 0, 2, 'LAWYER', 0, NOW(), 'system', '系统内置角色，拥有案件管理等权限'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role WHERE code = 'ROLE_LAWYER');

-- 创建初始权限
-- 系统管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, icon, sort, status, create_time, create_by)
SELECT 1, NULL, NULL, '系统管理', 'system', 'menu', 0, '/system', 'system', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system');

-- 用户管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 2, NULL, NULL, '用户管理', 'system:user', 'menu', 1, 'user', 'system/user/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:user');

-- 角色管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 3, NULL, NULL, '角色管理', 'system:role', 'menu', 1, 'role', 'system/role/index', 'peoples', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'system:role');

-- 权限管理权限
INSERT INTO auth_permission (id, tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT 4, NULL, NULL, '权限管理', 'system:permission', 'menu', 1, 'permission', 'system/permission/index', 'tree-table', 3, 0, NOW(), 'system'
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

-- 初始化存储桶
INSERT INTO storage_bucket (tenant_id, tenant_code, bucket_name, storage_type, domain, status, create_time, create_by, remark)
SELECT NULL, NULL, 'default', 'LOCAL', 'http://localhost:8080/api/files', 0, NOW(), 'system', '默认本地存储桶'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM storage_bucket WHERE bucket_name = 'default');

-- 初始化系统配置
INSERT INTO sys_config (tenant_id, tenant_code, config_key, config_value, group_name, status, create_time, create_by)
VALUES 
(NULL, NULL, 'sys.name', '律师事务所管理系统', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.version', '1.0.0', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.copyright', 'Copyright © 2023 Law Firm Management System', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.upload.allowed.ext', 'jpg,jpeg,png,gif,doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar', '上传设置', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化字典
INSERT INTO sys_dict (tenant_id, tenant_code, dict_name, dict_code, dict_type, status, create_time, create_by)
VALUES
(NULL, NULL, '用户性别', 'sys_user_gender', 'sys_user_gender', 0, NOW(), 'system'),
(NULL, NULL, '权限状态', 'sys_permission_status', 'sys_permission_status', 0, NOW(), 'system'),
(NULL, NULL, '系统开关', 'sys_normal_disable', 'sys_normal_disable', 0, NOW(), 'system'),
(NULL, NULL, '任务状态', 'sys_job_status', 'sys_job_status', 0, NOW(), 'system'),
(NULL, NULL, '系统是否', 'sys_yes_no', 'sys_yes_no', 0, NOW(), 'system'),
(NULL, NULL, '案件状态', 'case_status', 'case_status', 0, NOW(), 'system'),
(NULL, NULL, '案件类型', 'case_type', 'case_type', 0, NOW(), 'system'),
(NULL, NULL, '文档类型', 'doc_type', 'doc_type', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化字典项
INSERT INTO sys_dict_item (tenant_id, tenant_code, dict_id, label, value, sort, status, create_time, create_by)
VALUES
-- 用户性别
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '男', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '女', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '未知', '0', 3, 0, NOW(), 'system'),

-- 权限状态
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_permission_status'), '正常', '0', 1, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_permission_status'), '禁用', '1', 2, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW(); 