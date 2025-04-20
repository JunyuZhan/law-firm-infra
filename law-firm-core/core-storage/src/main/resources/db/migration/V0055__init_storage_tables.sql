-- Core-Storage模块表结构初始化
-- 版本: V0055
-- 模块: core-storage
-- 创建时间: 2023-07-01
-- 说明: 创建存储相关表，基于storage-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- storage_bucket表（存储桶表）
CREATE TABLE IF NOT EXISTS storage_bucket (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  bucket_name VARCHAR(100) NOT NULL COMMENT '桶名称',
  bucket_type VARCHAR(20) NOT NULL COMMENT '桶类型',
  domain VARCHAR(255) COMMENT '访问域名',
  access_key VARCHAR(100) COMMENT '访问密钥',
  secret_key VARCHAR(255) COMMENT '密钥密文',
  config TEXT COMMENT '桶配置JSON',
  max_size BIGINT COMMENT '最大空间',
  used_size BIGINT DEFAULT 0 COMMENT '已用空间',
  file_count BIGINT DEFAULT 0 COMMENT '文件数量',
  version INTEGER DEFAULT 0 COMMENT '版本号',
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

-- storage_file表（文件对象表）
CREATE TABLE IF NOT EXISTS storage_file (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  file_name VARCHAR(255) NOT NULL COMMENT '文件名',
  file_type VARCHAR(20) COMMENT '文件类型',
  extension VARCHAR(20) COMMENT '文件扩展名',
  md5 VARCHAR(32) COMMENT '文件MD5',
  bucket_id BIGINT NOT NULL COMMENT '存储桶ID',
  storage_path VARCHAR(255) COMMENT '存储路径',
  storage_size BIGINT DEFAULT 0 COMMENT '存储大小（字节）',
  uuid VARCHAR(36) NOT NULL COMMENT 'UUID标识符',
  upload_time BIGINT COMMENT '上传时间',
  content_type VARCHAR(100) COMMENT '内容类型',
  metadata TEXT COMMENT '元数据信息（JSON格式）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_uuid (uuid),
  KEY idx_bucket_id (bucket_id),
  KEY idx_md5 (md5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件对象表';

-- storage_file_info表（文件信息表）
CREATE TABLE IF NOT EXISTS storage_file_info (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  file_id BIGINT NOT NULL COMMENT '文件ID',
  description TEXT COMMENT '文件描述',
  tags VARCHAR(255) COMMENT '标签（逗号分隔）',
  metadata TEXT COMMENT '文件元数据（JSON格式）',
  access_count BIGINT DEFAULT 0 COMMENT '访问次数',
  download_count BIGINT DEFAULT 0 COMMENT '下载次数',
  last_access_time BIGINT COMMENT '最后访问时间',
  last_download_time BIGINT COMMENT '最后下载时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_file_id (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';

-- storage_chunk_info表（文件分片信息表）
CREATE TABLE IF NOT EXISTS storage_chunk_info (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  file_id VARCHAR(100) COMMENT '文件唯一标识',
  filename VARCHAR(255) COMMENT '文件名',
  chunk_index INTEGER NOT NULL COMMENT '分片索引，从0开始',
  total_chunks INTEGER NOT NULL COMMENT '总分片数',
  chunk_size BIGINT NOT NULL COMMENT '分片大小（字节）',
  chunk_path VARCHAR(255) COMMENT '分片文件存储路径',
  md5 VARCHAR(32) COMMENT '分片MD5校验码',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_file_chunk (file_id, chunk_index),
  KEY idx_filename (filename)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件分片信息表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 