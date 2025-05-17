-- 系统模块表结构初始化
-- 版本: V0001
-- 模块: API
-- 创建时间: 2023-06-01
-- 说明: 创建系统核心基础表，不包含外键约束
-- 根据实体类定义创建表结构
-- 注意：此脚本只包含API层职责范围内的基础表

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

-- sys_dict表（系统字典表）
CREATE TABLE IF NOT EXISTS sys_dict (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  dict_name VARCHAR(100) NOT NULL COMMENT '字典名称',
  dict_code VARCHAR(100) NOT NULL COMMENT '字典编码',
  dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
  description VARCHAR(255) COMMENT '字典描述',
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
  UNIQUE KEY uk_dict_code (dict_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

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

-- sys_dict_item表（系统字典项表）
CREATE TABLE IF NOT EXISTS sys_dict_item (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  dict_id BIGINT NOT NULL COMMENT '字典ID',
  label VARCHAR(100) NOT NULL COMMENT '字典项标签',
  value VARCHAR(100) NOT NULL COMMENT '字典项值',
  description VARCHAR(255) COMMENT '字典项描述',
  is_default INTEGER DEFAULT 0 COMMENT '是否默认（0-否，1-是）',
  color_type VARCHAR(50) COMMENT '颜色类型',
  css_class VARCHAR(100) COMMENT 'CSS样式',
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
  KEY idx_dict_id (dict_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典项表';

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
  region VARCHAR(50) COMMENT '区域',
  is_default TINYINT DEFAULT 0 COMMENT '是否默认（0-否，1-是）',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
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
  file_url VARCHAR(500) COMMENT '文件访问URL',
  file_type VARCHAR(100) COMMENT '文件类型',
  file_size BIGINT COMMENT '文件大小（字节）',
  file_hash VARCHAR(64) COMMENT '文件哈希值',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
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

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 