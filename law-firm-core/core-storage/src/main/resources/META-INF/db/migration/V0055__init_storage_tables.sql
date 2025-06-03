-- Core-Storage模块表结构初始化
-- 版本: V0055
-- 模块: 存储模块 (V0055-V0059)
-- 创建时间: 2023-07-01
-- 说明: 存储功能模块的完整表结构定义，基于storage-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 存储模块核心表 =======================

-- storage_bucket表（存储桶表）
DROP TABLE IF EXISTS storage_bucket;
CREATE TABLE storage_bucket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '存储桶ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
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
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_bucket_name (bucket_name),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_bucket_type (bucket_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储桶表';

-- storage_file表（存储文件表）
DROP TABLE IF EXISTS storage_file;
CREATE TABLE storage_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '存储文件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    file_type VARCHAR(20) COMMENT '文件类型',
    extension VARCHAR(20) COMMENT '文件扩展名',
    md5 VARCHAR(32) COMMENT '文件MD5',
    bucket_id BIGINT NOT NULL COMMENT '存储桶ID',
    storage_path VARCHAR(255) COMMENT '存储路径',
    storage_size BIGINT DEFAULT 0 COMMENT '存储大小(字节)',
    uuid VARCHAR(36) NOT NULL COMMENT 'UUID标识符',
    upload_time BIGINT COMMENT '上传时间',
    content_type VARCHAR(100) COMMENT '内容类型',
    metadata TEXT COMMENT '元数据信息(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_uuid (uuid),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_bucket_id (bucket_id),
    INDEX idx_md5 (md5),
    INDEX idx_file_type (file_type),
    
    CONSTRAINT fk_storage_file_bucket FOREIGN KEY (bucket_id) REFERENCES storage_bucket(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储文件表';

-- storage_file_info表（文件详细信息表）
DROP TABLE IF EXISTS storage_file_info;
CREATE TABLE storage_file_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文件详细信息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    file_id BIGINT NOT NULL COMMENT '文件ID',
    description TEXT COMMENT '文件描述',
    tags VARCHAR(255) COMMENT '标签(逗号分隔)',
    metadata TEXT COMMENT '文件元数据(JSON格式)',
    access_count BIGINT DEFAULT 0 COMMENT '访问次数',
    download_count BIGINT DEFAULT 0 COMMENT '下载次数',
    last_access_time BIGINT COMMENT '最后访问时间',
    last_download_time BIGINT COMMENT '最后下载时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_file_id (file_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_access_count (access_count),
    INDEX idx_download_count (download_count),
    
    CONSTRAINT fk_storage_file_info_file FOREIGN KEY (file_id) REFERENCES storage_file(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件详细信息表';

-- storage_chunk_info表（文件分片信息表）
DROP TABLE IF EXISTS storage_chunk_info;
CREATE TABLE storage_chunk_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文件分片信息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    file_id VARCHAR(100) COMMENT '文件唯一标识',
    filename VARCHAR(255) COMMENT '文件名',
    chunk_index INT NOT NULL COMMENT '分片索引，从0开始',
    total_chunks INT NOT NULL COMMENT '总分片数',
    chunk_size BIGINT NOT NULL COMMENT '分片大小(字节)',
    chunk_path VARCHAR(255) COMMENT '分片文件存储路径',
    md5 VARCHAR(32) COMMENT '分片MD5校验码',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_file_chunk (file_id, chunk_index),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_filename (filename),
    INDEX idx_file_id (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件分片信息表';

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 