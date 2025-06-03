-- 证据管理模块表结构
-- 版本: V9001
-- 模块: 证据管理模块 (V9000-V9999)
-- 创建时间: 2023-06-01
-- 说明: 证据管理功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 证据基础管理表 =======================

-- evidence_info表（证据基本信息表）
DROP TABLE IF EXISTS evidence_info;
CREATE TABLE evidence_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '证据ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    evidence_number VARCHAR(50) NOT NULL COMMENT '证据编号',
    evidence_name VARCHAR(200) NOT NULL COMMENT '证据名称',
    evidence_description TEXT COMMENT '证据描述',
    evidence_type TINYINT DEFAULT 1 COMMENT '证据类型(1-书证,2-物证,3-证人证言,4-视听资料,5-电子数据)',
    evidence_nature TINYINT DEFAULT 1 COMMENT '证据性质(1-直接证据,2-间接证据,3-原始证据,4-传来证据)',
    evidence_source TINYINT DEFAULT 1 COMMENT '证据来源(1-当事人提供,2-法院调取,3-律师调查,4-公安移送,5-其他)',
    source_desc VARCHAR(500) COMMENT '来源描述',
    category_id BIGINT COMMENT '证据分类ID',
    case_id BIGINT COMMENT '关联案件ID',
    case_number VARCHAR(50) COMMENT '案件编号',
    collector_id BIGINT COMMENT '收集人ID',
    collector_name VARCHAR(50) COMMENT '收集人姓名',
    collect_time DATETIME COMMENT '收集时间',
    collect_location VARCHAR(200) COMMENT '收集地点',
    collect_method VARCHAR(200) COMMENT '收集方式',
    custodian_id BIGINT COMMENT '保管人ID',
    custodian_name VARCHAR(50) COMMENT '保管人姓名',
    custody_location VARCHAR(200) COMMENT '保管位置',
    proof_matter TEXT COMMENT '证明事项',
    proof_level TINYINT DEFAULT 1 COMMENT '证明力等级(1-强,2-较强,3-一般,4-较弱,5-弱)',
    evidence_status TINYINT DEFAULT 1 COMMENT '证据状态(1-收集中,2-已收集,3-已质证,4-已认定,5-已归档)',
    is_original TINYINT DEFAULT 0 COMMENT '是否原件(0-否,1-是)',
    is_preserved TINYINT DEFAULT 0 COMMENT '是否已保全(0-否,1-是)',
    is_challenged TINYINT DEFAULT 0 COMMENT '是否被质证(0-否,1-是)',
    is_authenticated TINYINT DEFAULT 0 COMMENT '是否已认证(0-否,1-是)',
    authentication_result TINYINT COMMENT '认证结果(1-有效,2-无效,3-部分有效)',
    file_count INT DEFAULT 0 COMMENT '文件数量',
    keywords VARCHAR(500) COMMENT '关键词',
    chain_id VARCHAR(100) COMMENT '证据链ID',
    hash_value VARCHAR(64) COMMENT '哈希值',
    expire_time DATETIME COMMENT '过期时间',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    tags JSON COMMENT '证据标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_evidence_number (tenant_id, evidence_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_evidence_name (evidence_name),
    INDEX idx_evidence_type (evidence_type),
    INDEX idx_evidence_nature (evidence_nature),
    INDEX idx_evidence_source (evidence_source),
    INDEX idx_category_id (category_id),
    INDEX idx_case_id (case_id),
    INDEX idx_collector_id (collector_id),
    INDEX idx_custodian_id (custodian_id),
    INDEX idx_collect_time (collect_time),
    INDEX idx_evidence_status (evidence_status),
    INDEX idx_proof_level (proof_level),
    INDEX idx_is_original (is_original),
    INDEX idx_is_preserved (is_preserved),
    INDEX idx_is_challenged (is_challenged),
    INDEX idx_is_authenticated (is_authenticated),
    INDEX idx_chain_id (chain_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据基本信息表';

-- evidence_file表（证据文件表）
DROP TABLE IF EXISTS evidence_file;
CREATE TABLE evidence_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    evidence_id BIGINT NOT NULL COMMENT '证据ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_type TINYINT DEFAULT 1 COMMENT '文件类型(1-原件,2-复印件,3-扫描件,4-电子件)',
    file_format VARCHAR(50) COMMENT '文件格式',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_pages INT DEFAULT 0 COMMENT '文件页数',
    file_md5 VARCHAR(32) COMMENT '文件MD5',
    file_sha256 VARCHAR(64) COMMENT '文件SHA256',
    is_original TINYINT DEFAULT 0 COMMENT '是否原件(0-否,1-是)',
    is_encrypted TINYINT DEFAULT 0 COMMENT '是否加密(0-否,1-是)',
    encryption_type VARCHAR(20) COMMENT '加密方式',
    timestamp_info JSON COMMENT '时间戳信息(JSON格式)',
    quality_level TINYINT DEFAULT 1 COMMENT '质量等级(1-高,2-中,3-低)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    description VARCHAR(500) COMMENT '文件描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_evidence_id (evidence_id),
    INDEX idx_file_name (file_name),
    INDEX idx_file_type (file_type),
    INDEX idx_is_original (is_original),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status),
    
    CONSTRAINT fk_evidence_file_evidence FOREIGN KEY (evidence_id) REFERENCES evidence_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据文件表';

-- evidence_category表（证据分类表）
DROP TABLE IF EXISTS evidence_category;
CREATE TABLE evidence_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level TINYINT DEFAULT 1 COMMENT '分类层级',
    category_path VARCHAR(500) COMMENT '分类路径',
    description VARCHAR(500) COMMENT '分类描述',
    evidence_count INT DEFAULT 0 COMMENT '证据数量',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统分类(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_category_code (tenant_id, category_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_category_name (category_name),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_sort_order (sort_order),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据分类表';

-- ======================= 证据分类标签表 =======================

-- evidence_label表（证据标签表）
DROP TABLE IF EXISTS evidence_label;
CREATE TABLE evidence_label (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    label_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    label_code VARCHAR(50) NOT NULL COMMENT '标签编码',
    label_type TINYINT DEFAULT 1 COMMENT '标签类型(1-业务标签,2-状态标签,3-优先级标签,4-自定义标签)',
    label_category VARCHAR(50) COMMENT '标签分类',
    color VARCHAR(20) COMMENT '标签颜色',
    icon VARCHAR(100) COMMENT '标签图标',
    description VARCHAR(200) COMMENT '标签描述',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统标签(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    creator_id BIGINT COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_label_code (tenant_id, label_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_label_name (label_name),
    INDEX idx_label_type (label_type),
    INDEX idx_label_category (label_category),
    INDEX idx_sort_order (sort_order),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_creator_id (creator_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据标签表';

-- evidence_label_relation表（证据标签关联表）
DROP TABLE IF EXISTS evidence_label_relation;
CREATE TABLE evidence_label_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    evidence_id BIGINT NOT NULL COMMENT '证据ID',
    label_id BIGINT NOT NULL COMMENT '标签ID',
    label_value VARCHAR(200) COMMENT '标签值',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_evidence_label (evidence_id, label_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_evidence_id (evidence_id),
    INDEX idx_label_id (label_id),
    
    CONSTRAINT fk_evidence_label_relation_evidence FOREIGN KEY (evidence_id) REFERENCES evidence_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_evidence_label_relation_label FOREIGN KEY (label_id) REFERENCES evidence_label(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据标签关联表';

-- ======================= 证据质证管理表 =======================

-- evidence_challenge表（证据质证记录表）
DROP TABLE IF EXISTS evidence_challenge;
CREATE TABLE evidence_challenge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '质证ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    evidence_id BIGINT NOT NULL COMMENT '证据ID',
    challenger_id BIGINT COMMENT '质证人ID',
    challenger_name VARCHAR(50) COMMENT '质证人姓名',
    challenger_type TINYINT DEFAULT 1 COMMENT '质证人类型(1-对方当事人,2-对方律师,3-第三人,4-其他)',
    challenge_time DATETIME COMMENT '质证时间',
    challenge_reason TEXT COMMENT '质证理由',
    challenge_opinion TEXT COMMENT '质证意见',
    challenge_result TINYINT COMMENT '质证结果(1-有效,2-无效,3-部分有效,4-待定)',
    response_opinion TEXT COMMENT '答辩意见',
    judge_opinion TEXT COMMENT '法官意见',
    final_conclusion TEXT COMMENT '最终结论',
    attachment_files JSON COMMENT '附件文件(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_evidence_id (evidence_id),
    INDEX idx_challenger_id (challenger_id),
    INDEX idx_challenge_time (challenge_time),
    INDEX idx_challenge_result (challenge_result),
    INDEX idx_status (status),
    
    CONSTRAINT fk_evidence_challenge_evidence FOREIGN KEY (evidence_id) REFERENCES evidence_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据质证记录表';

-- ======================= 证据保全管理表 =======================

-- evidence_preservation表（证据保全记录表）
DROP TABLE IF EXISTS evidence_preservation;
CREATE TABLE evidence_preservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '保全ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    preservation_number VARCHAR(50) NOT NULL COMMENT '保全编号',
    evidence_id BIGINT NOT NULL COMMENT '证据ID',
    preservation_type TINYINT DEFAULT 1 COMMENT '保全类型(1-诉前保全,2-诉中保全,3-执行保全)',
    preservation_method TINYINT DEFAULT 1 COMMENT '保全方式(1-查封,2-扣押,3-冻结,4-其他)',
    applicant_id BIGINT COMMENT '申请人ID',
    applicant_name VARCHAR(50) COMMENT '申请人姓名',
    apply_time DATETIME COMMENT '申请时间',
    apply_reason TEXT COMMENT '申请理由',
    court_name VARCHAR(100) COMMENT '执行法院',
    judge_name VARCHAR(50) COMMENT '执行法官',
    preservation_time DATETIME COMMENT '保全时间',
    preservation_location VARCHAR(200) COMMENT '保全地点',
    preservation_desc TEXT COMMENT '保全描述',
    preservation_status TINYINT DEFAULT 1 COMMENT '保全状态(1-申请中,2-已保全,3-已解除,4-已失效)',
    expire_time DATETIME COMMENT '失效时间',
    release_time DATETIME COMMENT '解除时间',
    release_reason VARCHAR(500) COMMENT '解除原因',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_preservation_number (tenant_id, preservation_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_evidence_id (evidence_id),
    INDEX idx_preservation_type (preservation_type),
    INDEX idx_applicant_id (applicant_id),
    INDEX idx_apply_time (apply_time),
    INDEX idx_preservation_status (preservation_status),
    INDEX idx_expire_time (expire_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_evidence_preservation_evidence FOREIGN KEY (evidence_id) REFERENCES evidence_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='证据保全记录表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 