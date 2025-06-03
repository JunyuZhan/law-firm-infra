-- 档案管理模块表结构
-- 版本: V8001
-- 模块: 档案管理模块 (V8000-V8999)
-- 创建时间: 2023-06-01
-- 说明: 档案管理功能的完整表结构定义
-- 包括：档案基础信息、文件管理、分类标签、业务关联、借阅管理、操作记录

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 档案基础信息表 =======================

-- archive_info表（档案基本信息表）
DROP TABLE IF EXISTS archive_info;
CREATE TABLE archive_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '档案ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_number VARCHAR(50) NOT NULL COMMENT '档案编号',
    archive_title VARCHAR(200) NOT NULL COMMENT '档案标题',
    archive_description TEXT COMMENT '档案描述',
    archive_type TINYINT DEFAULT 1 COMMENT '档案类型(1-案件档案,2-合同档案,3-文档档案,4-行政档案,5-财务档案)',
    category_id BIGINT COMMENT '档案分类ID',
    archive_level TINYINT DEFAULT 1 COMMENT '档案密级(1-普通,2-内部,3-机密,4-绝密)',
    retention_period TINYINT DEFAULT 1 COMMENT '保管期限(1-3年,2-5年,3-10年,4-30年,5-永久)',
    storage_id BIGINT COMMENT '存储位置ID',
    storage_location VARCHAR(100) COMMENT '存储位置编码',
    archive_status TINYINT DEFAULT 1 COMMENT '档案状态(1-在库,2-借出,3-遗失,4-损坏,5-已销毁)',
    creator_id BIGINT COMMENT '创建人ID',
    creator_name VARCHAR(50) COMMENT '创建人姓名',
    archiver_id BIGINT COMMENT '归档人ID',
    archiver_name VARCHAR(50) COMMENT '归档人姓名',
    archive_time DATETIME COMMENT '归档时间',
    department_id BIGINT COMMENT '所属部门ID',
    department_name VARCHAR(100) COMMENT '所属部门名称',
    keywords VARCHAR(500) COMMENT '关键词',
    file_count INT DEFAULT 0 COMMENT '文件数量',
    total_pages INT DEFAULT 0 COMMENT '总页数',
    total_size BIGINT DEFAULT 0 COMMENT '总大小(字节)',
    last_check_time DATETIME COMMENT '最后检查时间',
    last_borrow_time DATETIME COMMENT '最后借阅时间',
    borrow_count INT DEFAULT 0 COMMENT '借阅次数',
    is_borrowable TINYINT DEFAULT 1 COMMENT '是否可借阅(0-否,1-是)',
    is_destroyed TINYINT DEFAULT 0 COMMENT '是否已销毁(0-否,1-是)',
    destroy_time DATETIME COMMENT '销毁时间',
    destroy_reason VARCHAR(500) COMMENT '销毁原因',
    expire_time DATETIME COMMENT '过期时间',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    tags JSON COMMENT '档案标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_archive_number (tenant_id, archive_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_title (archive_title),
    INDEX idx_archive_type (archive_type),
    INDEX idx_category_id (category_id),
    INDEX idx_archive_status (archive_status),
    INDEX idx_storage_id (storage_id),
    INDEX idx_storage_location (storage_location),
    INDEX idx_archiver_id (archiver_id),
    INDEX idx_department_id (department_id),
    INDEX idx_archive_time (archive_time),
    INDEX idx_expire_time (expire_time),
    INDEX idx_retention_period (retention_period),
    INDEX idx_is_destroyed (is_destroyed),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案基本信息表';

-- archive_file表（档案文件表）
DROP TABLE IF EXISTS archive_file;
CREATE TABLE archive_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_type TINYINT DEFAULT 1 COMMENT '文件类型(1-原件,2-复印件,3-扫描件,4-电子件)',
    file_format VARCHAR(50) COMMENT '文件格式',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_pages INT DEFAULT 0 COMMENT '文件页数',
    file_md5 VARCHAR(32) COMMENT '文件MD5',
    is_original TINYINT DEFAULT 0 COMMENT '是否原件(0-否,1-是)',
    is_encrypted TINYINT DEFAULT 0 COMMENT '是否加密(0-否,1-是)',
    encryption_type VARCHAR(20) COMMENT '加密方式',
    scan_resolution VARCHAR(20) COMMENT '扫描分辨率',
    scan_time DATETIME COMMENT '扫描时间',
    scanner_id BIGINT COMMENT '扫描人ID',
    scanner_name VARCHAR(50) COMMENT '扫描人姓名',
    file_condition TINYINT DEFAULT 1 COMMENT '文件状态(1-完好,2-轻微损坏,3-严重损坏,4-无法使用)',
    storage_position VARCHAR(100) COMMENT '存储位置',
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
    INDEX idx_archive_id (archive_id),
    INDEX idx_file_name (file_name),
    INDEX idx_file_type (file_type),
    INDEX idx_is_original (is_original),
    INDEX idx_file_condition (file_condition),
    INDEX idx_scan_time (scan_time),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status),
    
    CONSTRAINT fk_archive_file_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案文件表';

-- archive_storage表（档案存储位置表）
DROP TABLE IF EXISTS archive_storage;
CREATE TABLE archive_storage (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '存储位置ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    location_code VARCHAR(50) NOT NULL COMMENT '位置编码',
    location_name VARCHAR(100) NOT NULL COMMENT '位置名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父级位置ID',
    level TINYINT DEFAULT 1 COMMENT '层级',
    location_path VARCHAR(500) COMMENT '位置路径',
    location_type TINYINT DEFAULT 1 COMMENT '位置类型(1-楼层,2-区域,3-档案柜,4-层板,5-位置)',
    floor_number VARCHAR(10) COMMENT '楼层号',
    area_code VARCHAR(10) COMMENT '区域编码',
    cabinet_number VARCHAR(10) COMMENT '档案柜号',
    shelf_number VARCHAR(10) COMMENT '层板号',
    position_number VARCHAR(10) COMMENT '位置号',
    capacity INT DEFAULT 0 COMMENT '存储容量',
    used_capacity INT DEFAULT 0 COMMENT '已用容量',
    max_weight DECIMAL(10,2) COMMENT '最大承重(公斤)',
    current_weight DECIMAL(10,2) DEFAULT 0 COMMENT '当前重量(公斤)',
    temperature_range VARCHAR(50) COMMENT '温度范围',
    humidity_range VARCHAR(50) COMMENT '湿度范围',
    security_level TINYINT DEFAULT 1 COMMENT '安全级别(1-普通,2-重要,3-机密)',
    access_control TINYINT DEFAULT 0 COMMENT '门禁控制(0-无,1-有)',
    manager_id BIGINT COMMENT '管理员ID',
    manager_name VARCHAR(50) COMMENT '管理员姓名',
    description VARCHAR(500) COMMENT '位置描述',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_location_code (tenant_id, location_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_location_name (location_name),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_location_type (location_type),
    INDEX idx_manager_id (manager_id),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案存储位置表';

-- ======================= 档案分类标签表 =======================

-- archive_category表（档案分类表）
DROP TABLE IF EXISTS archive_category;
CREATE TABLE archive_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level TINYINT DEFAULT 1 COMMENT '分类层级',
    category_path VARCHAR(500) COMMENT '分类路径',
    description VARCHAR(500) COMMENT '分类描述',
    archive_count INT DEFAULT 0 COMMENT '档案数量',
    retention_period TINYINT COMMENT '默认保管期限',
    access_level TINYINT COMMENT '默认访问级别',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案分类表';

-- archive_label表（档案标签表）
DROP TABLE IF EXISTS archive_label;
CREATE TABLE archive_label (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案标签表';

-- archive_label_relation表（档案标签关联表）
DROP TABLE IF EXISTS archive_label_relation;
CREATE TABLE archive_label_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    label_id BIGINT NOT NULL COMMENT '标签ID',
    label_value VARCHAR(200) COMMENT '标签值',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_archive_label (archive_id, label_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_label_id (label_id),
    
    CONSTRAINT fk_archive_label_relation_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_archive_label_relation_label FOREIGN KEY (label_id) REFERENCES archive_label(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案标签关联表';

-- ======================= 档案业务关联表 =======================

-- archive_case_relation表（案件档案关联表）
DROP TABLE IF EXISTS archive_case_relation;
CREATE TABLE archive_case_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    case_number VARCHAR(50) COMMENT '案件编号',
    case_title VARCHAR(200) COMMENT '案件标题',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-案件材料,2-法律文书,3-证据材料,4-庭审记录,5-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_archive TINYINT DEFAULT 0 COMMENT '是否关键档案(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_archive_case (archive_id, case_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_case_id (case_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_archive (is_key_archive),
    
    CONSTRAINT fk_archive_case_relation_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件档案关联表';

-- archive_contract_relation表（合同档案关联表）
DROP TABLE IF EXISTS archive_contract_relation;
CREATE TABLE archive_contract_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    contract_number VARCHAR(50) COMMENT '合同编号',
    contract_title VARCHAR(200) COMMENT '合同标题',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-合同正文,2-补充协议,3-变更协议,4-履行记录,5-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_archive TINYINT DEFAULT 0 COMMENT '是否关键档案(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_archive_contract (archive_id, contract_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_archive (is_key_archive),
    
    CONSTRAINT fk_archive_contract_relation_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同档案关联表';

-- archive_document_relation表（文档档案关联表）
DROP TABLE IF EXISTS archive_document_relation;
CREATE TABLE archive_document_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    document_id BIGINT NOT NULL COMMENT '文档ID',
    document_title VARCHAR(200) COMMENT '文档标题',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-原始文档,2-衍生文档,3-相关文档,4-其他)',
    relation_desc VARCHAR(200) COMMENT '关联描述',
    is_key_archive TINYINT DEFAULT 0 COMMENT '是否关键档案(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    add_by VARCHAR(50) COMMENT '添加人',
    
    UNIQUE KEY uk_archive_document (archive_id, document_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_document_id (document_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_is_key_archive (is_key_archive),
    
    CONSTRAINT fk_archive_document_relation_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档档案关联表';

-- ======================= 档案借阅管理表 =======================

-- archive_borrow表（档案借阅记录表）
DROP TABLE IF EXISTS archive_borrow;
CREATE TABLE archive_borrow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '借阅ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    borrow_number VARCHAR(50) NOT NULL COMMENT '借阅编号',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    archive_title VARCHAR(200) COMMENT '档案标题',
    borrower_id BIGINT NOT NULL COMMENT '借阅人ID',
    borrower_name VARCHAR(50) COMMENT '借阅人姓名',
    borrower_department VARCHAR(100) COMMENT '借阅人部门',
    borrow_purpose TINYINT DEFAULT 1 COMMENT '借阅目的(1-业务办理,2-研究学习,3-审计检查,4-其他)',
    purpose_desc VARCHAR(500) COMMENT '借阅目的说明',
    borrow_type TINYINT DEFAULT 1 COMMENT '借阅类型(1-普通借阅,2-紧急借阅,3-外借)',
    borrow_status TINYINT DEFAULT 1 COMMENT '借阅状态(1-申请中,2-已批准,3-已借出,4-已归还,5-已拒绝,6-已超期)',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    planned_return_time DATETIME COMMENT '计划归还时间',
    actual_borrow_time DATETIME COMMENT '实际借出时间',
    actual_return_time DATETIME COMMENT '实际归还时间',
    borrow_days INT DEFAULT 0 COMMENT '借阅天数',
    overdue_days INT DEFAULT 0 COMMENT '超期天数',
    approver_id BIGINT COMMENT '审批人ID',
    approver_name VARCHAR(50) COMMENT '审批人姓名',
    approval_time DATETIME COMMENT '审批时间',
    approval_opinion TEXT COMMENT '审批意见',
    handler_id BIGINT COMMENT '经办人ID',
    handler_name VARCHAR(50) COMMENT '经办人姓名',
    borrow_reason VARCHAR(500) COMMENT '借阅理由',
    return_condition TINYINT COMMENT '归还状态(1-完好,2-轻微损坏,3-严重损坏)',
    return_desc VARCHAR(500) COMMENT '归还说明',
    reminder_count INT DEFAULT 0 COMMENT '提醒次数',
    last_reminder_time DATETIME COMMENT '最后提醒时间',
    extension_count INT DEFAULT 0 COMMENT '续借次数',
    extension_days INT DEFAULT 0 COMMENT '续借天数',
    is_returned TINYINT DEFAULT 0 COMMENT '是否已归还(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_borrow_number (tenant_id, borrow_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_borrower_id (borrower_id),
    INDEX idx_borrow_status (borrow_status),
    INDEX idx_borrow_type (borrow_type),
    INDEX idx_apply_time (apply_time),
    INDEX idx_planned_return_time (planned_return_time),
    INDEX idx_actual_return_time (actual_return_time),
    INDEX idx_approver_id (approver_id),
    INDEX idx_is_returned (is_returned),
    INDEX idx_status (status),
    
    CONSTRAINT fk_archive_borrow_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案借阅记录表';

-- archive_borrow_approval表（借阅审批记录表）
DROP TABLE IF EXISTS archive_borrow_approval;
CREATE TABLE archive_borrow_approval (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '审批ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    borrow_id BIGINT NOT NULL COMMENT '借阅ID',
    approval_step TINYINT DEFAULT 1 COMMENT '审批步骤',
    approver_id BIGINT NOT NULL COMMENT '审批人ID',
    approver_name VARCHAR(50) COMMENT '审批人姓名',
    approval_result TINYINT DEFAULT 1 COMMENT '审批结果(1-待审批,2-同意,3-拒绝)',
    approval_time DATETIME COMMENT '审批时间',
    approval_opinion TEXT COMMENT '审批意见',
    approval_conditions VARCHAR(500) COMMENT '审批条件',
    next_approver_id BIGINT COMMENT '下一审批人ID',
    process_instance_id VARCHAR(64) COMMENT '流程实例ID',
    task_id VARCHAR(64) COMMENT '任务ID',
    is_final TINYINT DEFAULT 0 COMMENT '是否最终审批(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_borrow_id (borrow_id),
    INDEX idx_approver_id (approver_id),
    INDEX idx_approval_result (approval_result),
    INDEX idx_approval_time (approval_time),
    INDEX idx_next_approver_id (next_approver_id),
    INDEX idx_is_final (is_final),
    INDEX idx_status (status),
    
    CONSTRAINT fk_archive_borrow_approval_borrow FOREIGN KEY (borrow_id) REFERENCES archive_borrow(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='借阅审批记录表';

-- archive_return表（档案归还记录表）
DROP TABLE IF EXISTS archive_return;
CREATE TABLE archive_return (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '归还ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    borrow_id BIGINT NOT NULL COMMENT '借阅ID',
    return_number VARCHAR(50) NOT NULL COMMENT '归还编号',
    returner_id BIGINT NOT NULL COMMENT '归还人ID',
    returner_name VARCHAR(50) COMMENT '归还人姓名',
    return_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '归还时间',
    receiver_id BIGINT COMMENT '接收人ID',
    receiver_name VARCHAR(50) COMMENT '接收人姓名',
    archive_condition TINYINT DEFAULT 1 COMMENT '档案状态(1-完好,2-轻微损坏,3-严重损坏,4-遗失)',
    condition_desc VARCHAR(500) COMMENT '状态说明',
    return_desc VARCHAR(500) COMMENT '归还说明',
    is_overdue TINYINT DEFAULT 0 COMMENT '是否超期(0-否,1-是)',
    overdue_days INT DEFAULT 0 COMMENT '超期天数',
    penalty_amount DECIMAL(10,2) DEFAULT 0 COMMENT '罚款金额',
    penalty_reason VARCHAR(200) COMMENT '罚款原因',
    is_penalty_paid TINYINT DEFAULT 0 COMMENT '是否已缴纳罚款(0-否,1-是)',
    check_result TINYINT DEFAULT 1 COMMENT '检查结果(1-通过,2-不通过)',
    check_opinion VARCHAR(500) COMMENT '检查意见',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_return_number (tenant_id, return_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_borrow_id (borrow_id),
    INDEX idx_returner_id (returner_id),
    INDEX idx_return_time (return_time),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_archive_condition (archive_condition),
    INDEX idx_is_overdue (is_overdue),
    INDEX idx_check_result (check_result),
    INDEX idx_status (status),
    
    CONSTRAINT fk_archive_return_borrow FOREIGN KEY (borrow_id) REFERENCES archive_borrow(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案归还记录表';

-- ======================= 档案操作记录表 =======================

-- archive_operation_log表（档案操作日志表）
DROP TABLE IF EXISTS archive_operation_log;
CREATE TABLE archive_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    archive_id BIGINT COMMENT '档案ID',
    operation_type TINYINT NOT NULL COMMENT '操作类型(1-创建,2-查看,3-编辑,4-删除,5-借阅,6-归还,7-盘点,8-销毁)',
    operation_desc VARCHAR(200) COMMENT '操作描述',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    operator_ip VARCHAR(50) COMMENT '操作IP',
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    operation_result TINYINT DEFAULT 1 COMMENT '操作结果(1-成功,2-失败)',
    error_message VARCHAR(500) COMMENT '错误信息',
    operation_details JSON COMMENT '操作详情(JSON格式)',
    before_data JSON COMMENT '操作前数据(JSON格式)',
    after_data JSON COMMENT '操作后数据(JSON格式)',
    business_id VARCHAR(64) COMMENT '业务ID',
    business_type VARCHAR(50) COMMENT '业务类型',
    trace_id VARCHAR(64) COMMENT '链路追踪ID',
    session_id VARCHAR(64) COMMENT '会话ID',
    user_agent VARCHAR(500) COMMENT '用户代理',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_operation_type (operation_type),
    INDEX idx_operator_id (operator_id),
    INDEX idx_operation_time (operation_time),
    INDEX idx_operation_result (operation_result),
    INDEX idx_business_id (business_id),
    INDEX idx_trace_id (trace_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案操作日志表';

-- archive_inventory表（档案盘点记录表）
DROP TABLE IF EXISTS archive_inventory;
CREATE TABLE archive_inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '盘点ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    inventory_number VARCHAR(50) NOT NULL COMMENT '盘点编号',
    inventory_name VARCHAR(200) NOT NULL COMMENT '盘点名称',
    inventory_type TINYINT DEFAULT 1 COMMENT '盘点类型(1-全面盘点,2-抽样盘点,3-专项盘点)',
    inventory_scope VARCHAR(500) COMMENT '盘点范围',
    inventory_status TINYINT DEFAULT 1 COMMENT '盘点状态(1-进行中,2-已完成,3-已暂停)',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    total_count INT DEFAULT 0 COMMENT '应盘数量',
    actual_count INT DEFAULT 0 COMMENT '实盘数量',
    match_count INT DEFAULT 0 COMMENT '盘点相符数量',
    missing_count INT DEFAULT 0 COMMENT '盘亏数量',
    surplus_count INT DEFAULT 0 COMMENT '盘盈数量',
    damage_count INT DEFAULT 0 COMMENT '损坏数量',
    inventory_result TINYINT COMMENT '盘点结果(1-正常,2-异常)',
    organizer_id BIGINT COMMENT '组织者ID',
    organizer_name VARCHAR(50) COMMENT '组织者姓名',
    participants JSON COMMENT '参与人员(JSON格式)',
    inventory_report TEXT COMMENT '盘点报告',
    summary TEXT COMMENT '盘点总结',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_inventory_number (tenant_id, inventory_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_inventory_type (inventory_type),
    INDEX idx_inventory_status (inventory_status),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_organizer_id (organizer_id),
    INDEX idx_inventory_result (inventory_result),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案盘点记录表';

-- archive_destroy表（档案销毁记录表）
DROP TABLE IF EXISTS archive_destroy;
CREATE TABLE archive_destroy (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '销毁ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    destroy_number VARCHAR(50) NOT NULL COMMENT '销毁编号',
    destroy_batch VARCHAR(50) COMMENT '销毁批次',
    archive_id BIGINT NOT NULL COMMENT '档案ID',
    archive_title VARCHAR(200) COMMENT '档案标题',
    destroy_reason TINYINT DEFAULT 1 COMMENT '销毁原因(1-超期,2-损坏,3-法定要求,4-其他)',
    reason_desc VARCHAR(500) COMMENT '销毁原因说明',
    destroy_method TINYINT DEFAULT 1 COMMENT '销毁方式(1-粉碎,2-焚烧,3-其他)',
    destroy_status TINYINT DEFAULT 1 COMMENT '销毁状态(1-申请中,2-已批准,3-已销毁,4-已拒绝)',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    applicant_id BIGINT COMMENT '申请人ID',
    applicant_name VARCHAR(50) COMMENT '申请人姓名',
    approver_id BIGINT COMMENT '审批人ID',
    approver_name VARCHAR(50) COMMENT '审批人姓名',
    approval_time DATETIME COMMENT '审批时间',
    approval_opinion TEXT COMMENT '审批意见',
    destroy_time DATETIME COMMENT '销毁时间',
    destroyer_id BIGINT COMMENT '销毁人ID',
    destroyer_name VARCHAR(50) COMMENT '销毁人姓名',
    witness_id BIGINT COMMENT '见证人ID',
    witness_name VARCHAR(50) COMMENT '见证人姓名',
    destroy_location VARCHAR(200) COMMENT '销毁地点',
    destroy_desc TEXT COMMENT '销毁描述',
    destroy_photos JSON COMMENT '销毁照片(JSON格式)',
    destroy_video VARCHAR(500) COMMENT '销毁视频',
    certificate_number VARCHAR(50) COMMENT '销毁证明编号',
    is_completed TINYINT DEFAULT 0 COMMENT '是否完成(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_destroy_number (tenant_id, destroy_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_archive_id (archive_id),
    INDEX idx_destroy_status (destroy_status),
    INDEX idx_destroy_reason (destroy_reason),
    INDEX idx_apply_time (apply_time),
    INDEX idx_destroy_time (destroy_time),
    INDEX idx_applicant_id (applicant_id),
    INDEX idx_approver_id (approver_id),
    INDEX idx_is_completed (is_completed),
    INDEX idx_status (status),
    
    CONSTRAINT fk_archive_destroy_archive FOREIGN KEY (archive_id) REFERENCES archive_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='档案销毁记录表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 