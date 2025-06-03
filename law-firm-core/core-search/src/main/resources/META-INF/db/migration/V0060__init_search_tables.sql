-- Core-Search模块表结构初始化
-- 版本: V0060
-- 模块: 搜索模块 (V0060-V0064)
-- 创建时间: 2023-07-01
-- 说明: 搜索功能模块的完整表结构定义，基于search-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 搜索模块核心表 =======================

-- search_index表（搜索索引表）
DROP TABLE IF EXISTS search_index;
CREATE TABLE search_index (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '搜索索引ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    index_name VARCHAR(100) NOT NULL COMMENT '索引名称',
    index_type VARCHAR(20) NOT NULL COMMENT '索引类型',
    number_of_shards INT DEFAULT 1 COMMENT '分片数',
    number_of_replicas INT DEFAULT 1 COMMENT '副本数',
    refresh_interval INT DEFAULT 1 COMMENT '刷新间隔',
    alias VARCHAR(100) COMMENT '索引别名',
    description VARCHAR(255) COMMENT '索引描述',
    enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    settings TEXT COMMENT '索引设置(JSON格式)',
    mappings TEXT COMMENT '索引映射(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_index_name (index_name),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_index_type (index_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索索引表';

-- search_field表（搜索字段表）
DROP TABLE IF EXISTS search_field;
CREATE TABLE search_field (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '搜索字段ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    index_id BIGINT NOT NULL COMMENT '索引ID',
    field_name VARCHAR(100) NOT NULL COMMENT '字段名称',
    field_type VARCHAR(20) NOT NULL COMMENT '字段类型',
    analyzer VARCHAR(50) COMMENT '分析器',
    search_analyzer VARCHAR(50) COMMENT '搜索分析器',
    boost FLOAT DEFAULT 1.0 COMMENT '权重',
    index_option VARCHAR(20) DEFAULT 'docs' COMMENT '索引选项',
    enable_term_vectors TINYINT DEFAULT 0 COMMENT '是否启用词向量(0-否,1-是)',
    ignore_above INT DEFAULT 256 COMMENT '忽略超过长度的字符',
    norms TINYINT DEFAULT 1 COMMENT '是否启用norms(0-否,1-是)',
    store TINYINT DEFAULT 0 COMMENT '是否存储(0-否,1-是)',
    doc_values TINYINT DEFAULT 1 COMMENT '是否使用doc_values(0-否,1-是)',
    null_value VARCHAR(50) COMMENT '空值',
    copy_to VARCHAR(255) COMMENT '复制到字段(逗号分隔)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_index_field (index_id, field_name),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_index_id (index_id),
    INDEX idx_field_type (field_type),
    
    CONSTRAINT fk_search_field_index FOREIGN KEY (index_id) REFERENCES search_index(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索字段表';

-- search_document表（搜索文档表）
DROP TABLE IF EXISTS search_document;
CREATE TABLE search_document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '搜索文档ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    index_id BIGINT NOT NULL COMMENT '索引ID',
    doc_id VARCHAR(100) NOT NULL COMMENT '文档ID',
    biz_id VARCHAR(100) COMMENT '业务ID',
    biz_type VARCHAR(50) COMMENT '业务类型',
    content TEXT COMMENT '内容',
    status TINYINT DEFAULT 0 COMMENT '状态(0-待索引,1-索引成功,2-索引失败)',
    error_msg TEXT COMMENT '错误信息',
    retry_count INT DEFAULT 0 COMMENT '重试次数',
    last_retry_time BIGINT COMMENT '最后重试时间',
    index_time BIGINT COMMENT '索引时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_index_doc (index_id, doc_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_index_id (index_id),
    INDEX idx_status (status),
    INDEX idx_biz_id (biz_id),
    INDEX idx_biz_type (biz_type),
    
    CONSTRAINT fk_search_document_index FOREIGN KEY (index_id) REFERENCES search_index(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='搜索文档表';

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 