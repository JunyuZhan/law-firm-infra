-- 案件管理模块表结构
-- 版本: V4001
-- 模块: 案件管理模块 (V4000-V4999)
-- 创建时间: 2023-06-01
-- 说明: 案件管理功能的完整表结构定义
-- 包括：案件基础信息、案件详情、费用管理、团队管理、参与方管理、任务管理、文档管理、事件管理

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 案件核心表 =======================

-- case_info表（案件基本信息表）
DROP TABLE IF EXISTS case_info;
CREATE TABLE case_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '案件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_number VARCHAR(50) NOT NULL COMMENT '案件编号',
    case_name VARCHAR(200) NOT NULL COMMENT '案件名称',
    case_type TINYINT NOT NULL COMMENT '案件类型(1-民事,2-刑事,3-行政,4-仲裁,5-非诉)',
    case_status TINYINT DEFAULT 1 COMMENT '案件状态(1-待受理,2-进行中,3-已结案,4-已关闭)',
    case_stage TINYINT COMMENT '案件阶段(1-立案,2-调查,3-开庭,4-执行,5-结案)',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    difficulty TINYINT DEFAULT 2 COMMENT '难度(1-简单,2-一般,3-复杂,4-疑难)',
    client_id BIGINT COMMENT '委托人ID',
    primary_lawyer_id BIGINT COMMENT '主办律师ID',
    department_id BIGINT COMMENT '承办部门ID',
    case_source TINYINT COMMENT '案件来源(1-新客户,2-老客户,3-转介绍,4-公益)',
    description TEXT COMMENT '案件描述',
    filing_date DATE COMMENT '立案日期',
    expected_close_date DATE COMMENT '预计结案日期',
    actual_close_date DATE COMMENT '实际结案日期',
    result TINYINT COMMENT '案件结果(1-胜诉,2-败诉,3-和解,4-调解,5-撤诉)',
    is_confidential TINYINT DEFAULT 0 COMMENT '是否保密(0-否,1-是)',
    has_conflict TINYINT DEFAULT 0 COMMENT '是否有利益冲突(0-否,1-是)',
    tags JSON COMMENT '案件标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_case_number (tenant_id, case_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_type (case_type),
    INDEX idx_case_status (case_status),
    INDEX idx_case_stage (case_stage),
    INDEX idx_priority (priority),
    INDEX idx_client_id (client_id),
    INDEX idx_primary_lawyer_id (primary_lawyer_id),
    INDEX idx_department_id (department_id),
    INDEX idx_filing_date (filing_date),
    INDEX idx_expected_close_date (expected_close_date),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件基本信息表';

-- case_detail表（案件详细信息表）
DROP TABLE IF EXISTS case_detail;
CREATE TABLE case_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '详情ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    court_name VARCHAR(100) COMMENT '法院/仲裁机构',
    court_case_number VARCHAR(100) COMMENT '案号',
    judge_name VARCHAR(50) COMMENT '法官/仲裁员',
    opposing_party VARCHAR(100) COMMENT '对方当事人',
    case_summary TEXT COMMENT '案件概要',
    dispute_focus TEXT COMMENT '争议焦点',
    legal_basis TEXT COMMENT '法律依据',
    case_analysis TEXT COMMENT '案件分析',
    risk_assessment TEXT COMMENT '风险评估',
    strategy TEXT COMMENT '办案策略',
    evidence_summary TEXT COMMENT '证据总结',
    timeline JSON COMMENT '案件时间线(JSON格式)',
    related_laws JSON COMMENT '相关法条(JSON格式)',
    keywords JSON COMMENT '关键词(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_case_id (case_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_court_name (court_name),
    INDEX idx_court_case_number (court_case_number),
    
    CONSTRAINT fk_case_detail_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件详细信息表';

-- case_fee表（案件费用表）
DROP TABLE IF EXISTS case_fee;
CREATE TABLE case_fee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '费用ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    fee_type TINYINT NOT NULL COMMENT '费用类型(1-律师费,2-诉讼费,3-差旅费,4-其他)',
    fee_category TINYINT COMMENT '收费方式(1-固定费用,2-按小时,3-按比例,4-风险代理)',
    estimated_amount DECIMAL(12,2) COMMENT '预估金额',
    actual_amount DECIMAL(12,2) COMMENT '实际金额',
    paid_amount DECIMAL(12,2) DEFAULT 0 COMMENT '已付金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    fee_description TEXT COMMENT '费用说明',
    billing_cycle TINYINT COMMENT '计费周期(1-一次性,2-按月,3-按季,4-按年)',
    payment_terms TEXT COMMENT '付款条件',
    discount_rate DECIMAL(5,2) DEFAULT 0 COMMENT '折扣率',
    tax_rate DECIMAL(5,2) DEFAULT 0 COMMENT '税率',
    fee_status TINYINT DEFAULT 1 COMMENT '费用状态(1-待收,2-部分收取,3-已收取,4-减免)',
    due_date DATE COMMENT '应收日期',
    payment_date DATE COMMENT '实收日期',
    invoice_required TINYINT DEFAULT 1 COMMENT '是否需要发票(0-否,1-是)',
    invoice_issued TINYINT DEFAULT 0 COMMENT '是否已开票(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_fee_type (fee_type),
    INDEX idx_fee_status (fee_status),
    INDEX idx_due_date (due_date),
    INDEX idx_status (status),
    
    CONSTRAINT fk_case_fee_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件费用表';

-- case_team表（案件团队表）
DROP TABLE IF EXISTS case_team;
CREATE TABLE case_team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '团队ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    employee_id BIGINT NOT NULL COMMENT '律师/员工ID',
    role_type TINYINT DEFAULT 1 COMMENT '角色类型(1-主办律师,2-协办律师,3-律师助理,4-实习生)',
    responsibility TEXT COMMENT '职责描述',
    workload_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '工作量占比',
    profit_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '收益分配比例',
    join_date DATE COMMENT '加入日期',
    exit_date DATE COMMENT '退出日期',
    is_active TINYINT DEFAULT 1 COMMENT '是否活跃(0-否,1-是)',
    permission_level TINYINT DEFAULT 1 COMMENT '权限级别(1-只读,2-编辑,3-管理)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_case_employee (case_id, employee_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_employee_id (employee_id),
    INDEX idx_role_type (role_type),
    INDEX idx_is_active (is_active),
    
    CONSTRAINT fk_case_team_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件团队表';

-- case_participant表（案件参与方表）
DROP TABLE IF EXISTS case_participant;
CREATE TABLE case_participant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '参与方ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    participant_type TINYINT NOT NULL COMMENT '参与方类型(1-原告,2-被告,3-第三人,4-证人,5-鉴定人)',
    participant_name VARCHAR(100) NOT NULL COMMENT '参与方名称',
    is_organization TINYINT DEFAULT 0 COMMENT '是否为组织(0-个人,1-组织)',
    id_type TINYINT COMMENT '证件类型(1-身份证,2-护照,3-营业执照)',
    id_number VARCHAR(50) COMMENT '证件号码',
    legal_representative VARCHAR(50) COMMENT '法定代表人',
    contact_person VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    contact_email VARCHAR(100) COMMENT '联系邮箱',
    contact_address VARCHAR(255) COMMENT '联系地址',
    role_description TEXT COMMENT '角色说明',
    is_primary TINYINT DEFAULT 0 COMMENT '是否主要参与方(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_participant_type (participant_type),
    INDEX idx_participant_name (participant_name),
    INDEX idx_is_primary (is_primary),
    INDEX idx_status (status),
    
    CONSTRAINT fk_case_participant_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件参与方表';

-- case_task表（案件任务表）
DROP TABLE IF EXISTS case_task;
CREATE TABLE case_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    task_name VARCHAR(100) NOT NULL COMMENT '任务名称',
    task_type TINYINT DEFAULT 1 COMMENT '任务类型(1-文件准备,2-法律研究,3-客户沟通,4-证据收集,5-出庭准备)',
    task_description TEXT COMMENT '任务描述',
    assignee_id BIGINT COMMENT '指派人ID',
    assignee_name VARCHAR(50) COMMENT '指派人姓名',
    assigned_to_id BIGINT NOT NULL COMMENT '执行人ID',
    assigned_to_name VARCHAR(50) COMMENT '执行人姓名',
    task_priority TINYINT DEFAULT 2 COMMENT '任务优先级(1-低,2-中,3-高,4-紧急)',
    task_status TINYINT DEFAULT 1 COMMENT '任务状态(1-待处理,2-进行中,3-已完成,4-已取消)',
    start_date DATE COMMENT '开始日期',
    due_date DATE COMMENT '截止日期',
    complete_date DATE COMMENT '完成日期',
    estimated_hours DECIMAL(5,2) COMMENT '预估工时',
    actual_hours DECIMAL(5,2) COMMENT '实际工时',
    completion_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '完成进度',
    completion_notes TEXT COMMENT '完成说明',
    dependencies JSON COMMENT '依赖任务(JSON格式)',
    tags JSON COMMENT '任务标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_task_type (task_type),
    INDEX idx_task_status (task_status),
    INDEX idx_assignee_id (assignee_id),
    INDEX idx_assigned_to_id (assigned_to_id),
    INDEX idx_due_date (due_date),
    INDEX idx_task_priority (task_priority),
    INDEX idx_status (status),
    
    CONSTRAINT fk_case_task_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件任务表';

-- case_document表（案件文档表）
DROP TABLE IF EXISTS case_document;
CREATE TABLE case_document (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文档ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    document_name VARCHAR(200) NOT NULL COMMENT '文档名称',
    document_type TINYINT DEFAULT 1 COMMENT '文档类型(1-起诉书,2-答辩状,3-证据材料,4-判决书,5-法律意见书)',
    document_category VARCHAR(50) COMMENT '文档分类',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_format VARCHAR(20) COMMENT '文件格式',
    upload_by_id BIGINT COMMENT '上传人ID',
    upload_by_name VARCHAR(50) COMMENT '上传人姓名',
    document_description TEXT COMMENT '文档描述',
    is_confidential TINYINT DEFAULT 0 COMMENT '是否保密(0-否,1-是)',
    access_level TINYINT DEFAULT 1 COMMENT '访问级别(1-公开,2-团队,3-指定人员)',
    document_status TINYINT DEFAULT 1 COMMENT '文档状态(1-草稿,2-待审核,3-已发布,4-已归档)',
    version_number VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    parent_document_id BIGINT COMMENT '父文档ID(版本关联)',
    keywords JSON COMMENT '关键词(JSON格式)',
    tags JSON COMMENT '标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_document_type (document_type),
    INDEX idx_document_status (document_status),
    INDEX idx_upload_by_id (upload_by_id),
    INDEX idx_parent_document_id (parent_document_id),
    INDEX idx_is_confidential (is_confidential),
    INDEX idx_status (status),
    
    CONSTRAINT fk_case_document_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件文档表';

-- case_event表（案件事件表）
DROP TABLE IF EXISTS case_event;
CREATE TABLE case_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '事件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    event_name VARCHAR(100) NOT NULL COMMENT '事件名称',
    event_type TINYINT DEFAULT 1 COMMENT '事件类型(1-开庭,2-客户会议,3-团队讨论,4-调解会议,5-证人约见)',
    event_description TEXT COMMENT '事件描述',
    event_date DATETIME NOT NULL COMMENT '事件时间',
    location VARCHAR(200) COMMENT '事件地点',
    organizer_id BIGINT COMMENT '组织者ID',
    organizer_name VARCHAR(50) COMMENT '组织者姓名',
    participants JSON COMMENT '参与人员(JSON格式)',
    event_status TINYINT DEFAULT 1 COMMENT '事件状态(1-计划中,2-进行中,3-已完成,4-已取消)',
    importance TINYINT DEFAULT 2 COMMENT '重要性(1-低,2-中,3-高,4-紧急)',
    reminder_time DATETIME COMMENT '提醒时间',
    result_summary TEXT COMMENT '结果总结',
    follow_up_actions TEXT COMMENT '后续行动',
    related_documents JSON COMMENT '相关文档(JSON格式)',
    tags JSON COMMENT '标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_event_type (event_type),
    INDEX idx_event_status (event_status),
    INDEX idx_event_date (event_date),
    INDEX idx_organizer_id (organizer_id),
    INDEX idx_importance (importance),
    INDEX idx_status (status),
    
    CONSTRAINT fk_case_event_case FOREIGN KEY (case_id) REFERENCES case_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件事件表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 