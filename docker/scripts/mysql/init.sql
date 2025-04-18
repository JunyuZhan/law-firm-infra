-- 律师事务所管理系统 - 数据库初始化脚本
-- 该脚本在数据库首次启动时自动执行
-- 注意: 如果使用Flyway进行数据库迁移，这些表结构将被Flyway脚本覆盖
-- 本脚本主要用于Docker环境的初始化和开发测试环境
-- 更新日期: 2023-06-05 (基于实体类定义更新表结构)

-- 确保使用正确的数据库
CREATE DATABASE IF NOT EXISTS law_firm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE law_firm;

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- auth_user表（对应User实体）
CREATE TABLE IF NOT EXISTS auth_user (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  username VARCHAR(50) NOT NULL COMMENT '用户名',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  email VARCHAR(100) COMMENT '邮箱',
  mobile VARCHAR(20) COMMENT '手机号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  employee_id BIGINT COMMENT '关联的员工ID',
  last_login_time DATETIME COMMENT '最后登录时间',
  last_login_ip VARCHAR(50) COMMENT '最后登录IP',
  account_expire_time DATETIME COMMENT '账号过期时间',
  password_expire_time DATETIME COMMENT '密码过期时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证用户表';

-- auth_role表（对应Role实体）
CREATE TABLE IF NOT EXISTS auth_role (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(50) NOT NULL COMMENT '角色名称',
  code VARCHAR(50) NOT NULL COMMENT '角色编码',
  type INTEGER DEFAULT 1 COMMENT '角色类型（0-系统角色，1-自定义角色）',
  data_scope INTEGER DEFAULT 0 COMMENT '数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）',
  business_role_type VARCHAR(50) COMMENT '业务角色类型',
  parent_id BIGINT DEFAULT 0 COMMENT '父级角色ID，用于角色继承',
  sort INTEGER DEFAULT 0 COMMENT '显示顺序',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- auth_permission表（对应Permission实体）
CREATE TABLE IF NOT EXISTS auth_permission (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  name VARCHAR(50) NOT NULL COMMENT '权限名称',
  code VARCHAR(100) NOT NULL COMMENT '权限编码',
  type VARCHAR(20) COMMENT '权限类型（menu-菜单权限，operation-操作权限，data-数据权限）',
  parent_id BIGINT DEFAULT 0 COMMENT '父级权限ID',
  path VARCHAR(200) COMMENT '权限路径',
  icon VARCHAR(100) COMMENT '图标',
  component VARCHAR(255) COMMENT '组件路径',
  visible TINYINT DEFAULT 1 COMMENT '是否可见（0-隐藏，1-显示）',
  redirect VARCHAR(255) COMMENT '重定向路径',
  sort INTEGER DEFAULT 0 COMMENT '显示顺序',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_permission_code (code),
  KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- auth_user_role表（对应UserRole实体）
CREATE TABLE IF NOT EXISTS auth_user_role (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  role_id BIGINT NOT NULL COMMENT '角色ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- auth_role_permission表（对应RolePermission实体）
CREATE TABLE IF NOT EXISTS auth_role_permission (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  role_id BIGINT NOT NULL COMMENT '角色ID',
  permission_id BIGINT NOT NULL COMMENT '权限ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- sys_config表（系统配置表）
CREATE TABLE IF NOT EXISTS sys_config (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  config_key VARCHAR(100) NOT NULL COMMENT '配置键',
  config_value VARCHAR(500) NOT NULL COMMENT '配置值',
  type VARCHAR(50) COMMENT '配置类型',
  group_name VARCHAR(50) COMMENT '分组名称',
  is_system TINYINT DEFAULT 0 COMMENT '是否系统配置（0-否，1-是）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- sys_dict_type表（字典类型表）
CREATE TABLE IF NOT EXISTS sys_dict_type (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  dict_name VARCHAR(100) NOT NULL COMMENT '字典名称',
  dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
  is_system TINYINT DEFAULT 0 COMMENT '是否系统字典（0-否，1-是）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- sys_dict_data表（字典数据表）
CREATE TABLE IF NOT EXISTS sys_dict_data (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
  dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
  dict_value VARCHAR(100) NOT NULL COMMENT '字典值',
  dict_sort INTEGER DEFAULT 0 COMMENT '字典排序',
  is_default TINYINT DEFAULT 0 COMMENT '是否默认（0-否，1-是）',
  css_class VARCHAR(100) COMMENT 'CSS样式',
  list_class VARCHAR(100) COMMENT '表格样式',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- sys_log_operation表（操作日志表）
CREATE TABLE IF NOT EXISTS sys_log_operation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT COMMENT '用户ID',
  username VARCHAR(50) COMMENT '用户名',
  module VARCHAR(50) COMMENT '模块名称',
  operation VARCHAR(100) COMMENT '操作内容',
  method VARCHAR(100) COMMENT '请求方法',
  request_url VARCHAR(255) COMMENT '请求URL',
  request_method VARCHAR(10) COMMENT '请求方式（GET, POST等）',
  request_params TEXT COMMENT '请求参数',
  request_ip VARCHAR(50) COMMENT '请求IP地址',
  request_time BIGINT COMMENT '请求耗时（毫秒）',
  response_code INTEGER COMMENT '返回状态码',
  response_msg VARCHAR(500) COMMENT '返回消息',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-异常）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- sys_log_login表（登录日志表）
CREATE TABLE IF NOT EXISTS sys_log_login (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT COMMENT '用户ID',
  username VARCHAR(50) COMMENT '用户名',
  login_ip VARCHAR(50) COMMENT '登录IP',
  login_location VARCHAR(100) COMMENT '登录地点',
  browser VARCHAR(100) COMMENT '浏览器类型',
  os VARCHAR(100) COMMENT '操作系统',
  device_type VARCHAR(50) COMMENT '设备类型',
  login_status INTEGER DEFAULT 0 COMMENT '登录状态（0-成功，1-失败）',
  login_msg VARCHAR(255) COMMENT '登录消息',
  login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_login_time (login_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- storage_bucket表（存储桶表）
CREATE TABLE IF NOT EXISTS storage_bucket (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  bucket_name VARCHAR(100) NOT NULL COMMENT '存储桶名称',
  storage_type VARCHAR(50) NOT NULL COMMENT '存储类型（LOCAL, MINIO, OSS等）',
  endpoint VARCHAR(255) COMMENT '存储端点',
  access_key VARCHAR(100) COMMENT '访问密钥',
  secret_key VARCHAR(100) COMMENT '密钥',
  domain VARCHAR(255) COMMENT '访问域名',
  region VARCHAR(50) COMMENT '区域',
  config TEXT COMMENT '其他配置（JSON格式）',
  used_size BIGINT DEFAULT 0 COMMENT '已用容量（字节）',
  max_size BIGINT DEFAULT 0 COMMENT '最大容量（字节）',
  file_count BIGINT DEFAULT 0 COMMENT '文件数量',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_bucket_name (bucket_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储桶表';

-- storage_file表（存储文件表）
CREATE TABLE IF NOT EXISTS storage_file (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  bucket_id BIGINT NOT NULL COMMENT '存储桶ID',
  file_name VARCHAR(255) NOT NULL COMMENT '文件名',
  original_name VARCHAR(255) COMMENT '原始文件名',
  file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
  file_url VARCHAR(1000) COMMENT '文件访问URL',
  file_type VARCHAR(50) COMMENT '文件类型',
  content_type VARCHAR(100) COMMENT 'MIME类型',
  file_size BIGINT DEFAULT 0 COMMENT '文件大小（字节）',
  file_hash VARCHAR(64) COMMENT '文件哈希值（MD5）',
  file_ext VARCHAR(20) COMMENT '文件扩展名',
  file_tags VARCHAR(255) COMMENT '文件标签',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_bucket_id (bucket_id),
  KEY idx_file_hash (file_hash)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储文件表';

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
SELECT NULL, NULL, 1, 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = 1);

INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = 2);

INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = 3);

INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_role_permission WHERE role_id = 1 AND permission_id = 4);

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

-- 初始化字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by)
VALUES
(NULL, NULL, '用户性别', 'sys_user_gender', 0, NOW(), 'system'),
(NULL, NULL, '权限状态', 'sys_permission_status', 0, NOW(), 'system'),
(NULL, NULL, '系统开关', 'sys_normal_disable', 0, NOW(), 'system'),
(NULL, NULL, '任务状态', 'sys_job_status', 0, NOW(), 'system'),
(NULL, NULL, '系统是否', 'sys_yes_no', 0, NOW(), 'system'),
(NULL, NULL, '案件状态', 'case_status', 0, NOW(), 'system'),
(NULL, NULL, '案件类型', 'case_type', 0, NOW(), 'system'),
(NULL, NULL, '文档类型', 'doc_type', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by)
VALUES
-- 用户性别
(NULL, NULL, 'sys_user_gender', '男', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, 'sys_user_gender', '女', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, 'sys_user_gender', '未知', '0', 3, 0, NOW(), 'system'),

-- 权限状态
(NULL, NULL, 'sys_permission_status', '正常', '0', 1, 0, NOW(), 'system'),
(NULL, NULL, 'sys_permission_status', '禁用', '1', 2, 0, NOW(), 'system'),

-- 系统开关
(NULL, NULL, 'sys_normal_disable', '正常', '0', 1, 0, NOW(), 'system'),
(NULL, NULL, 'sys_normal_disable', '禁用', '1', 2, 0, NOW(), 'system'),

-- 系统是否
(NULL, NULL, 'sys_yes_no', '是', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, 'sys_yes_no', '否', '0', 2, 0, NOW(), 'system'),

-- 案件状态
(NULL, NULL, 'case_status', '待接收', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, 'case_status', '处理中', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, 'case_status', '已完成', '3', 3, 0, NOW(), 'system'),
(NULL, NULL, 'case_status', '已关闭', '4', 4, 0, NOW(), 'system'),

-- 案件类型
(NULL, NULL, 'case_type', '民事案件', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, 'case_type', '刑事案件', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, 'case_type', '行政案件', '3', 3, 0, NOW(), 'system'),
(NULL, NULL, 'case_type', '知识产权', '4', 4, 0, NOW(), 'system'),
(NULL, NULL, 'case_type', '合同纠纷', '5', 5, 0, NOW(), 'system'),

-- 文档类型
(NULL, NULL, 'doc_type', '合同文档', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, 'doc_type', '案件材料', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, 'doc_type', '客户资料', '3', 3, 0, NOW(), 'system'),
(NULL, NULL, 'doc_type', '法律法规', '4', 4, 0, NOW(), 'system'),
(NULL, NULL, 'doc_type', '内部文件', '5', 5, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 确保数据库选项正确
SET FOREIGN_KEY_CHECKS = 1;

-- 输出完成信息
SELECT '数据库初始化完成' AS '状态'; 