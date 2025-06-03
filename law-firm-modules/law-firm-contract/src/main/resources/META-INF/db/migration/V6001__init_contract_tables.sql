-- 合同管理模块表结构
-- 版本: V6001
-- 模块: 合同管理模块 (V6000-V6999)
-- 创建时间: 2023-06-01
-- 说明: 合同管理功能的完整表结构定义
-- 包括：合同基础信息、附件管理、条款管理、团队管理、费用管理、审核流程、模板管理、变更管理

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 合同基础信息表 =======================

-- contract_info表（合同基本信息表）
DROP TABLE IF EXISTS contract_info;
CREATE TABLE contract_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '合同ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_number VARCHAR(50) NOT NULL COMMENT '合同编号',
    contract_name VARCHAR(200) NOT NULL COMMENT '合同名称',
    contract_type TINYINT DEFAULT 1 COMMENT '合同类型(1-委托代理,2-法律顾问,3-专项服务,4-其他)',
    contract_status TINYINT DEFAULT 1 COMMENT '合同状态(1-草稿,2-待审核,3-已审核,4-已签署,5-执行中,6-已完成,7-已终止)',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    case_id BIGINT COMMENT '关联案件ID',
    template_id BIGINT COMMENT '合同模板ID',
    lead_attorney_id BIGINT COMMENT '主办律师ID',
    lead_attorney_name VARCHAR(50) COMMENT '主办律师姓名',
    department_id BIGINT COMMENT '承办部门ID',
    department_name VARCHAR(100) COMMENT '承办部门名称',
    contract_amount DECIMAL(15,2) DEFAULT 0 COMMENT '合同总金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    signing_date DATE COMMENT '签署日期',
    effective_date DATE COMMENT '生效日期',
    expiry_date DATE COMMENT '到期日期',
    service_scope TEXT COMMENT '服务范围',
    delegate_power TEXT COMMENT '委托授权范围',
    fee_type TINYINT COMMENT '收费类型(1-固定费用,2-按小时,3-风险代理,4-混合收费)',
    payment_terms TEXT COMMENT '付款条件',
    contract_content LONGTEXT COMMENT '合同正文内容',
    confidentiality_level TINYINT DEFAULT 1 COMMENT '保密级别(1-公开,2-内部,3-机密,4-绝密)',
    is_framework TINYINT DEFAULT 0 COMMENT '是否框架合同(0-否,1-是)',
    parent_contract_id BIGINT COMMENT '父合同ID(框架合同下的子合同)',
    renewal_count INT DEFAULT 0 COMMENT '续签次数',
    auto_renewal TINYINT DEFAULT 0 COMMENT '是否自动续签(0-否,1-是)',
    reminder_days INT DEFAULT 30 COMMENT '到期提醒天数',
    tags JSON COMMENT '合同标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_contract_number (tenant_id, contract_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_name (contract_name),
    INDEX idx_contract_type (contract_type),
    INDEX idx_contract_status (contract_status),
    INDEX idx_client_id (client_id),
    INDEX idx_case_id (case_id),
    INDEX idx_lead_attorney_id (lead_attorney_id),
    INDEX idx_department_id (department_id),
    INDEX idx_signing_date (signing_date),
    INDEX idx_effective_date (effective_date),
    INDEX idx_expiry_date (expiry_date),
    INDEX idx_parent_contract_id (parent_contract_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同基本信息表';

-- contract_attachment表（合同附件表）
DROP TABLE IF EXISTS contract_attachment;
CREATE TABLE contract_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '附件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_size BIGINT COMMENT '文件大小(字节)',
    file_extension VARCHAR(20) COMMENT '文件扩展名',
    attachment_type TINYINT DEFAULT 1 COMMENT '附件类型(1-合同文本,2-签署页,3-补充协议,4-其他)',
    upload_time DATETIME COMMENT '上传时间',
    uploader_id BIGINT COMMENT '上传人ID',
    uploader_name VARCHAR(50) COMMENT '上传人姓名',
    description VARCHAR(500) COMMENT '附件描述',
    confidentiality_level TINYINT DEFAULT 1 COMMENT '保密级别(1-公开,2-内部,3-机密,4-绝密)',
    is_signature_page TINYINT DEFAULT 0 COMMENT '是否签署页(0-否,1-是)',
    signature_status TINYINT DEFAULT 0 COMMENT '签署状态(0-未签署,1-已签署)',
    version_number VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_attachment_type (attachment_type),
    INDEX idx_file_type (file_type),
    INDEX idx_uploader_id (uploader_id),
    INDEX idx_is_signature_page (is_signature_page),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_attachment_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同附件表';

-- contract_clause表（合同条款表）
DROP TABLE IF EXISTS contract_clause;
CREATE TABLE contract_clause (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '条款ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    clause_title VARCHAR(200) COMMENT '条款标题',
    clause_content TEXT NOT NULL COMMENT '条款内容',
    clause_type TINYINT DEFAULT 1 COMMENT '条款类型(1-标准条款,2-特殊条款,3-补充条款)',
    clause_category VARCHAR(50) COMMENT '条款分类',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_required TINYINT DEFAULT 0 COMMENT '是否必填条款(0-否,1-是)',
    is_editable TINYINT DEFAULT 1 COMMENT '是否可编辑(0-否,1-是)',
    parent_clause_id BIGINT COMMENT '父条款ID',
    level TINYINT DEFAULT 1 COMMENT '条款层级',
    variables JSON COMMENT '条款变量(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_clause_type (clause_type),
    INDEX idx_clause_category (clause_category),
    INDEX idx_sort_order (sort_order),
    INDEX idx_parent_clause_id (parent_clause_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_clause_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同条款表';

-- ======================= 合同团队费用表 =======================

-- contract_team表（合同团队表）
DROP TABLE IF EXISTS contract_team;
CREATE TABLE contract_team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '团队ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    attorney_id BIGINT NOT NULL COMMENT '律师ID',
    attorney_name VARCHAR(50) COMMENT '律师姓名',
    role_type TINYINT DEFAULT 1 COMMENT '角色类型(1-主办律师,2-协办律师,3-律师助理,4-实习生)',
    responsibility TEXT COMMENT '职责描述',
    join_date DATE COMMENT '加入日期',
    exit_date DATE COMMENT '退出日期',
    is_active TINYINT DEFAULT 1 COMMENT '是否活跃(0-否,1-是)',
    billable_hours DECIMAL(8,2) DEFAULT 0 COMMENT '计费小时数',
    hourly_rate DECIMAL(10,2) COMMENT '小时费率',
    workload_percent DECIMAL(5,2) DEFAULT 0 COMMENT '工作量占比',
    profit_percent DECIMAL(5,2) DEFAULT 0 COMMENT '收益分配比例',
    performance_score DECIMAL(3,1) COMMENT '绩效评分',
    remarks TEXT COMMENT '备注说明',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_contract_attorney (contract_id, attorney_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_attorney_id (attorney_id),
    INDEX idx_role_type (role_type),
    INDEX idx_is_active (is_active),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_team_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同团队表';

-- contract_fee表（合同费用表）
DROP TABLE IF EXISTS contract_fee;
CREATE TABLE contract_fee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '费用ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    fee_name VARCHAR(100) NOT NULL COMMENT '费用名称',
    fee_type TINYINT DEFAULT 1 COMMENT '费用类型(1-律师费,2-诉讼费,3-差旅费,4-其他费用)',
    fee_category TINYINT DEFAULT 1 COMMENT '收费方式(1-固定费用,2-按小时,3-按比例,4-风险代理)',
    fee_amount DECIMAL(15,2) NOT NULL COMMENT '费用金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    fee_rate DECIMAL(8,4) COMMENT '费率(适用于比例收费)',
    calculation_method TEXT COMMENT '计算方式说明',
    due_date DATE COMMENT '应付日期',
    payment_status TINYINT DEFAULT 1 COMMENT '支付状态(1-未支付,2-部分支付,3-已支付,4-已退款)',
    paid_amount DECIMAL(15,2) DEFAULT 0 COMMENT '已付金额',
    is_taxable TINYINT DEFAULT 1 COMMENT '是否含税(0-否,1-是)',
    tax_rate DECIMAL(5,2) DEFAULT 0 COMMENT '税率',
    tax_amount DECIMAL(12,2) DEFAULT 0 COMMENT '税额',
    invoice_required TINYINT DEFAULT 1 COMMENT '是否需要发票(0-否,1-是)',
    invoice_issued TINYINT DEFAULT 0 COMMENT '是否已开票(0-否,1-是)',
    condition_desc TEXT COMMENT '收费条件说明',
    milestone_id BIGINT COMMENT '关联里程碑ID',
    billing_cycle TINYINT COMMENT '计费周期(1-一次性,2-按月,3-按季,4-按年)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_fee_type (fee_type),
    INDEX idx_fee_category (fee_category),
    INDEX idx_payment_status (payment_status),
    INDEX idx_due_date (due_date),
    INDEX idx_milestone_id (milestone_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_fee_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同费用表';

-- ======================= 合同审核流程表 =======================

-- contract_review表（合同审核表）
DROP TABLE IF EXISTS contract_review;
CREATE TABLE contract_review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '审核ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    review_type TINYINT DEFAULT 1 COMMENT '审核类型(1-内容审核,2-法务审核,3-财务审核,4-管理审核)',
    review_stage TINYINT DEFAULT 1 COMMENT '审核阶段(1-初审,2-复审,3-终审)',
    review_status TINYINT DEFAULT 1 COMMENT '审核状态(1-待审核,2-审核通过,3-审核拒绝,4-需要修改)',
    reviewer_id BIGINT COMMENT '审核人ID',
    reviewer_name VARCHAR(50) COMMENT '审核人姓名',
    review_time DATETIME COMMENT '审核时间',
    review_opinion TEXT COMMENT '审核意见',
    review_result TEXT COMMENT '审核结果',
    next_reviewer_id BIGINT COMMENT '下一审核人ID',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    expected_complete_time DATETIME COMMENT '预期完成时间',
    review_duration INT COMMENT '审核耗时(分钟)',
    attachments JSON COMMENT '审核附件(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_review_type (review_type),
    INDEX idx_review_status (review_status),
    INDEX idx_reviewer_id (reviewer_id),
    INDEX idx_review_time (review_time),
    INDEX idx_next_reviewer_id (next_reviewer_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_review_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同审核表';

-- contract_approval表（合同审批表）
DROP TABLE IF EXISTS contract_approval;
CREATE TABLE contract_approval (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '审批ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    approval_node VARCHAR(50) COMMENT '审批节点',
    approval_level TINYINT DEFAULT 1 COMMENT '审批级别',
    approval_status TINYINT DEFAULT 1 COMMENT '审批状态(1-待审批,2-审批通过,3-审批拒绝,4-已撤回)',
    approver_id BIGINT COMMENT '审批人ID',
    approver_name VARCHAR(50) COMMENT '审批人姓名',
    approval_time DATETIME COMMENT '审批时间',
    approval_opinion TEXT COMMENT '审批意见',
    process_instance_id VARCHAR(64) COMMENT '流程实例ID',
    task_id VARCHAR(64) COMMENT '任务ID',
    next_approver_id BIGINT COMMENT '下一审批人ID',
    approval_duration INT COMMENT '审批耗时(分钟)',
    is_final_approval TINYINT DEFAULT 0 COMMENT '是否最终审批(0-否,1-是)',
    delegate_approver_id BIGINT COMMENT '委托审批人ID',
    approval_deadline DATETIME COMMENT '审批截止时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_approval_status (approval_status),
    INDEX idx_approver_id (approver_id),
    INDEX idx_approval_time (approval_time),
    INDEX idx_process_instance_id (process_instance_id),
    INDEX idx_next_approver_id (next_approver_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_approval_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同审批表';

-- contract_milestone表（合同里程碑表）
DROP TABLE IF EXISTS contract_milestone;
CREATE TABLE contract_milestone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '里程碑ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    milestone_name VARCHAR(100) NOT NULL COMMENT '里程碑名称',
    milestone_type TINYINT DEFAULT 1 COMMENT '里程碑类型(1-开始节点,2-过程节点,3-结束节点)',
    description TEXT COMMENT '里程碑描述',
    plan_date DATE COMMENT '计划完成日期',
    actual_date DATE COMMENT '实际完成日期',
    milestone_status TINYINT DEFAULT 1 COMMENT '状态(1-未开始,2-进行中,3-已完成,4-已延期,5-已跳过)',
    completion_percent DECIMAL(5,2) DEFAULT 0 COMMENT '完成百分比',
    responsible_id BIGINT COMMENT '负责人ID',
    responsible_name VARCHAR(50) COMMENT '负责人姓名',
    linked_fee_id BIGINT COMMENT '关联费用项ID',
    is_key_point TINYINT DEFAULT 0 COMMENT '是否关键节点(0-否,1-是)',
    reminder_days INT DEFAULT 3 COMMENT '提前提醒天数',
    is_reminder_sent TINYINT DEFAULT 0 COMMENT '是否已发送提醒(0-否,1-是)',
    sequence_number INT COMMENT '序列号',
    deliverables TEXT COMMENT '交付物说明',
    acceptance_criteria TEXT COMMENT '验收标准',
    risk_level TINYINT DEFAULT 1 COMMENT '风险级别(1-低,2-中,3-高)',
    dependencies JSON COMMENT '依赖关系(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_milestone_type (milestone_type),
    INDEX idx_milestone_status (milestone_status),
    INDEX idx_plan_date (plan_date),
    INDEX idx_responsible_id (responsible_id),
    INDEX idx_linked_fee_id (linked_fee_id),
    INDEX idx_is_key_point (is_key_point),
    INDEX idx_sequence_number (sequence_number),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_milestone_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同里程碑表';

-- ======================= 合同模板变更表 =======================

-- contract_template表（合同模板表）
DROP TABLE IF EXISTS contract_template;
CREATE TABLE contract_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    template_name VARCHAR(200) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) NOT NULL COMMENT '模板编码',
    template_type TINYINT DEFAULT 1 COMMENT '模板类型(1-委托代理,2-法律顾问,3-专项服务,4-其他)',
    template_category VARCHAR(50) COMMENT '模板分类',
    template_content LONGTEXT COMMENT '模板内容',
    template_version VARCHAR(20) DEFAULT '1.0' COMMENT '模板版本',
    is_active TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认模板(0-否,1-是)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    creator_id BIGINT COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    approval_status TINYINT DEFAULT 1 COMMENT '审批状态(1-待审批,2-已审批,3-已拒绝)',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    variables JSON COMMENT '模板变量(JSON格式)',
    clauses JSON COMMENT '标准条款(JSON格式)',
    business_rules JSON COMMENT '业务规则(JSON格式)',
    applicable_scope TEXT COMMENT '适用范围',
    usage_instructions TEXT COMMENT '使用说明',
    maintenance_notes TEXT COMMENT '维护说明',
    tags JSON COMMENT '标签(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_template_code (tenant_id, template_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_template_name (template_name),
    INDEX idx_template_type (template_type),
    INDEX idx_template_category (template_category),
    INDEX idx_is_active (is_active),
    INDEX idx_is_default (is_default),
    INDEX idx_approval_status (approval_status),
    INDEX idx_creator_id (creator_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同模板表';

-- contract_change表（合同变更表）
DROP TABLE IF EXISTS contract_change;
CREATE TABLE contract_change (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '变更ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    change_number VARCHAR(50) NOT NULL COMMENT '变更单号',
    change_type TINYINT DEFAULT 1 COMMENT '变更类型(1-金额变更,2-期限变更,3-条款变更,4-团队变更,5-其他)',
    change_reason TEXT COMMENT '变更原因',
    change_description TEXT COMMENT '变更描述',
    change_content JSON COMMENT '变更内容(JSON格式)',
    old_values JSON COMMENT '变更前值(JSON格式)',
    new_values JSON COMMENT '变更后值(JSON格式)',
    change_status TINYINT DEFAULT 1 COMMENT '变更状态(1-草稿,2-待审核,3-已审核,4-已生效,5-已拒绝)',
    initiator_id BIGINT COMMENT '发起人ID',
    initiator_name VARCHAR(50) COMMENT '发起人姓名',
    apply_date DATE COMMENT '申请日期',
    effective_date DATE COMMENT '生效日期',
    approval_required TINYINT DEFAULT 1 COMMENT '是否需要审批(0-否,1-是)',
    approval_process_id VARCHAR(64) COMMENT '审批流程ID',
    impact_assessment TEXT COMMENT '影响评估',
    risk_assessment TEXT COMMENT '风险评估',
    rollback_plan TEXT COMMENT '回滚方案',
    change_cost DECIMAL(12,2) DEFAULT 0 COMMENT '变更成本',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    attachments JSON COMMENT '附件信息(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_change_number (tenant_id, change_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_change_type (change_type),
    INDEX idx_change_status (change_status),
    INDEX idx_initiator_id (initiator_id),
    INDEX idx_apply_date (apply_date),
    INDEX idx_effective_date (effective_date),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_change_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同变更表';

-- contract_conflict表（合同冲突表）
DROP TABLE IF EXISTS contract_conflict;
CREATE TABLE contract_conflict (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '冲突ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    conflict_type TINYINT DEFAULT 1 COMMENT '冲突类型(1-利益冲突,2-时间冲突,3-资源冲突,4-条款冲突,5-其他)',
    conflict_description TEXT COMMENT '冲突描述',
    conflict_source TINYINT DEFAULT 1 COMMENT '冲突来源(1-系统检测,2-人工发现,3-客户反馈)',
    conflict_level TINYINT DEFAULT 2 COMMENT '冲突级别(1-低,2-中,3-高,4-严重)',
    conflict_status TINYINT DEFAULT 1 COMMENT '冲突状态(1-待处理,2-处理中,3-已解决,4-已忽略)',
    related_contract_id BIGINT COMMENT '关联冲突合同ID',
    related_client_id BIGINT COMMENT '关联冲突客户ID',
    related_case_id BIGINT COMMENT '关联冲突案件ID',
    detector_id BIGINT COMMENT '发现人ID',
    detector_name VARCHAR(50) COMMENT '发现人姓名',
    detect_time DATETIME COMMENT '发现时间',
    handler_id BIGINT COMMENT '处理人ID',
    handler_name VARCHAR(50) COMMENT '处理人姓名',
    handle_time DATETIME COMMENT '处理时间',
    resolution_plan TEXT COMMENT '解决方案',
    resolution_result TEXT COMMENT '解决结果',
    prevention_measures TEXT COMMENT '预防措施',
    impact_analysis TEXT COMMENT '影响分析',
    review_required TINYINT DEFAULT 1 COMMENT '是否需要审核(0-否,1-是)',
    reviewer_id BIGINT COMMENT '审核人ID',
    review_time DATETIME COMMENT '审核时间',
    review_opinion TEXT COMMENT '审核意见',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_conflict_type (conflict_type),
    INDEX idx_conflict_level (conflict_level),
    INDEX idx_conflict_status (conflict_status),
    INDEX idx_related_contract_id (related_contract_id),
    INDEX idx_related_client_id (related_client_id),
    INDEX idx_detector_id (detector_id),
    INDEX idx_handler_id (handler_id),
    INDEX idx_detect_time (detect_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_contract_conflict_contract FOREIGN KEY (contract_id) REFERENCES contract_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同冲突表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 