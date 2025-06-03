-- 财务管理模块表结构
-- 版本: V10001
-- 模块: 财务管理模块 (V10000-V10999)
-- 创建时间: 2023-06-01
-- 说明: 财务管理功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 账户管理表 =======================

-- finance_account表（账户信息表）
DROP TABLE IF EXISTS finance_account;
CREATE TABLE finance_account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '账户ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    account_number VARCHAR(50) NOT NULL COMMENT '账户编号',
    account_name VARCHAR(100) NOT NULL COMMENT '账户名称',
    account_type TINYINT DEFAULT 1 COMMENT '账户类型(1-现金,2-银行,3-支付宝,4-微信,5-虚拟,6-信用)',
    account_status TINYINT DEFAULT 1 COMMENT '账户状态(1-正常,2-冻结,3-注销,4-锁定)',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    balance DECIMAL(15,2) DEFAULT 0.00 COMMENT '账户余额',
    frozen_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '冻结金额',
    available_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '可用金额',
    credit_limit DECIMAL(15,2) DEFAULT 0.00 COMMENT '信用额度',
    bank_name VARCHAR(100) COMMENT '开户银行',
    bank_account VARCHAR(50) COMMENT '银行账号',
    account_holder VARCHAR(50) COMMENT '开户人',
    client_id BIGINT COMMENT '关联客户ID',
    department_id BIGINT COMMENT '关联部门ID',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认账户(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-否,1-是)',
    last_transaction_time DATETIME COMMENT '最后交易时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_account_number (tenant_id, account_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_account_name (account_name),
    INDEX idx_account_type (account_type),
    INDEX idx_account_status (account_status),
    INDEX idx_client_id (client_id),
    INDEX idx_department_id (department_id),
    INDEX idx_currency (currency),
    INDEX idx_is_default (is_default),
    INDEX idx_is_enabled (is_enabled),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账户信息表';

-- ======================= 交易管理表 =======================

-- finance_transaction表（交易记录表）
DROP TABLE IF EXISTS finance_transaction;
CREATE TABLE finance_transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '交易ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    transaction_number VARCHAR(50) NOT NULL COMMENT '交易编号',
    transaction_type TINYINT NOT NULL COMMENT '交易类型(1-收款,2-付款,3-转账,4-退款,5-预付款,6-押金,7-调整)',
    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    transaction_time DATETIME NOT NULL COMMENT '交易时间',
    from_account_id BIGINT COMMENT '付款账户ID',
    to_account_id BIGINT COMMENT '收款账户ID',
    business_id BIGINT COMMENT '关联业务ID',
    business_type VARCHAR(50) COMMENT '关联业务类型(case-案件,contract-合同,invoice-发票)',
    transaction_status TINYINT DEFAULT 1 COMMENT '交易状态(1-待处理,2-处理中,3-成功,4-失败,5-取消)',
    summary VARCHAR(200) COMMENT '交易摘要',
    voucher_number VARCHAR(50) COMMENT '凭证号',
    payment_method TINYINT COMMENT '付款方式(1-银行转账,2-现金,3-支票,4-支付宝,5-微信,6-抵扣)',
    approval_status TINYINT DEFAULT 1 COMMENT '审批状态(1-无需审批,2-待审批,3-已审批,4-已拒绝)',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    department_id BIGINT COMMENT '部门ID',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_transaction_number (tenant_id, transaction_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_transaction_type (transaction_type),
    INDEX idx_transaction_time (transaction_time),
    INDEX idx_from_account_id (from_account_id),
    INDEX idx_to_account_id (to_account_id),
    INDEX idx_business_id (business_id),
    INDEX idx_business_type (business_type),
    INDEX idx_transaction_status (transaction_status),
    INDEX idx_approval_status (approval_status),
    INDEX idx_operator_id (operator_id),
    INDEX idx_department_id (department_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_finance_transaction_from_account FOREIGN KEY (from_account_id) REFERENCES finance_account(id),
    CONSTRAINT fk_finance_transaction_to_account FOREIGN KEY (to_account_id) REFERENCES finance_account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易记录表';

-- ======================= 发票管理表 =======================

-- finance_invoice表（发票信息表）
DROP TABLE IF EXISTS finance_invoice;
CREATE TABLE finance_invoice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '发票ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    invoice_number VARCHAR(50) NOT NULL COMMENT '发票编号',
    invoice_type TINYINT DEFAULT 1 COMMENT '发票类型(1-增值税专用发票,2-增值税普通发票,3-电子发票,4-普通收据)',
    invoice_status TINYINT DEFAULT 1 COMMENT '发票状态(1-待开票,2-已开票,3-已作废,4-已寄出,5-已签收)',
    amount DECIMAL(15,2) NOT NULL COMMENT '发票金额',
    tax_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '税额',
    total_amount DECIMAL(15,2) NOT NULL COMMENT '价税合计',
    case_id BIGINT COMMENT '关联案件ID',
    contract_id BIGINT COMMENT '关联合同ID',
    client_id BIGINT COMMENT '客户ID',
    invoice_title VARCHAR(200) NOT NULL COMMENT '发票抬头',
    invoice_content VARCHAR(500) COMMENT '发票内容',
    issue_time DATETIME COMMENT '开票时间',
    issued_by VARCHAR(50) COMMENT '开票人',
    taxpayer_number VARCHAR(50) COMMENT '纳税人识别号',
    registered_address VARCHAR(200) COMMENT '注册地址',
    registered_phone VARCHAR(50) COMMENT '注册电话',
    bank_name VARCHAR(100) COMMENT '开户银行',
    bank_account VARCHAR(50) COMMENT '银行账号',
    receiver_name VARCHAR(50) COMMENT '收件人',
    receiver_phone VARCHAR(50) COMMENT '收件电话',
    receiver_address VARCHAR(200) COMMENT '收件地址',
    express_company VARCHAR(50) COMMENT '快递公司',
    express_number VARCHAR(50) COMMENT '快递单号',
    send_time DATETIME COMMENT '寄送时间',
    receive_time DATETIME COMMENT '签收时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_invoice_number (tenant_id, invoice_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_invoice_type (invoice_type),
    INDEX idx_invoice_status (invoice_status),
    INDEX idx_case_id (case_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_client_id (client_id),
    INDEX idx_issue_time (issue_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='发票信息表';

-- ======================= 应收应付管理表 =======================

-- finance_receivable表（应收账款表）
DROP TABLE IF EXISTS finance_receivable;
CREATE TABLE finance_receivable (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '应收ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    receivable_number VARCHAR(50) NOT NULL COMMENT '应收账款编号',
    receivable_name VARCHAR(100) NOT NULL COMMENT '应收账款名称',
    contract_id BIGINT COMMENT '关联合同ID',
    case_id BIGINT COMMENT '关联案件ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    total_amount DECIMAL(15,2) NOT NULL COMMENT '应收总金额',
    received_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '已收金额',
    unreceived_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '未收金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    credit_period INT DEFAULT 0 COMMENT '账期（天）',
    due_date DATETIME COMMENT '到期日期',
    expected_receipt_date DATETIME COMMENT '预计收款日期',
    overdue_days INT DEFAULT 0 COMMENT '逾期天数',
    receivable_status TINYINT DEFAULT 1 COMMENT '应收状态(1-待收款,2-部分收款,3-已收款,4-逾期,5-坏账)',
    lawyer_id BIGINT COMMENT '负责律师ID',
    department_id BIGINT COMMENT '部门ID',
    last_receipt_date DATETIME COMMENT '最近收款日期',
    collection_status TINYINT DEFAULT 1 COMMENT '催收状态(1-无需催收,2-待催收,3-催收中,4-催收完成)',
    bad_debt_provision DECIMAL(15,2) DEFAULT 0.00 COMMENT '坏账准备',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_receivable_number (tenant_id, receivable_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_case_id (case_id),
    INDEX idx_client_id (client_id),
    INDEX idx_receivable_status (receivable_status),
    INDEX idx_due_date (due_date),
    INDEX idx_lawyer_id (lawyer_id),
    INDEX idx_department_id (department_id),
    INDEX idx_collection_status (collection_status),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账款表';

-- ======================= 账单管理表 =======================

-- finance_bill表（账单信息表）
DROP TABLE IF EXISTS finance_bill;
CREATE TABLE finance_bill (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '账单ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    bill_number VARCHAR(50) NOT NULL COMMENT '账单编号',
    bill_type TINYINT DEFAULT 1 COMMENT '账单类型(1-律师费,2-诉讼费,3-顾问费,4-代理费,5-综合账单)',
    bill_status TINYINT DEFAULT 1 COMMENT '账单状态(1-草稿,2-已确认,3-已发送,4-部分付款,5-已付款,6-已取消,7-逾期)',
    case_id BIGINT COMMENT '关联案件ID',
    contract_id BIGINT COMMENT '关联合同ID',
    client_id BIGINT NOT NULL COMMENT '客户ID',
    bill_title VARCHAR(200) NOT NULL COMMENT '账单标题',
    total_amount DECIMAL(15,2) NOT NULL COMMENT '账单总金额',
    paid_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '已付金额',
    unpaid_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '未付金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    bill_date DATETIME NOT NULL COMMENT '账单日期',
    due_date DATETIME COMMENT '到期日期',
    payment_terms VARCHAR(200) COMMENT '付款条件',
    lawyer_id BIGINT COMMENT '负责律师ID',
    department_id BIGINT COMMENT '部门ID',
    is_recurring TINYINT DEFAULT 0 COMMENT '是否循环账单(0-否,1-是)',
    recurring_period TINYINT COMMENT '循环周期(1-月,2-季度,3-半年,4-年)',
    next_bill_date DATETIME COMMENT '下次账单日期',
    discount_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '折扣金额',
    tax_rate DECIMAL(5,4) DEFAULT 0.0000 COMMENT '税率',
    tax_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '税额',
    final_amount DECIMAL(15,2) NOT NULL COMMENT '最终金额',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_bill_number (tenant_id, bill_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_bill_type (bill_type),
    INDEX idx_bill_status (bill_status),
    INDEX idx_case_id (case_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_client_id (client_id),
    INDEX idx_bill_date (bill_date),
    INDEX idx_due_date (due_date),
    INDEX idx_lawyer_id (lawyer_id),
    INDEX idx_department_id (department_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='账单信息表';

-- ======================= 预算管理表 =======================

-- finance_budget表（预算计划表）
DROP TABLE IF EXISTS finance_budget;
CREATE TABLE finance_budget (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预算ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    budget_number VARCHAR(50) NOT NULL COMMENT '预算编号',
    budget_name VARCHAR(100) NOT NULL COMMENT '预算名称',
    budget_type TINYINT DEFAULT 1 COMMENT '预算类型(1-部门预算,2-项目预算,3-年度预算,4-季度预算,5-月度预算)',
    budget_status TINYINT DEFAULT 1 COMMENT '预算状态(1-草稿,2-已审批,3-执行中,4-已完成,5-已取消)',
    budget_year YEAR NOT NULL COMMENT '预算年度',
    budget_period TINYINT DEFAULT 1 COMMENT '预算期间(1-年度,2-半年,3-季度,4-月度)',
    start_date DATE NOT NULL COMMENT '开始日期',
    end_date DATE NOT NULL COMMENT '结束日期',
    department_id BIGINT COMMENT '部门ID',
    project_id BIGINT COMMENT '项目ID',
    total_budget DECIMAL(15,2) NOT NULL COMMENT '预算总额',
    used_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '已使用金额',
    remaining_amount DECIMAL(15,2) DEFAULT 0.00 COMMENT '剩余金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    manager_id BIGINT COMMENT '预算负责人ID',
    description TEXT COMMENT '预算描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_budget_number (tenant_id, budget_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_budget_type (budget_type),
    INDEX idx_budget_status (budget_status),
    INDEX idx_budget_year (budget_year),
    INDEX idx_department_id (department_id),
    INDEX idx_project_id (project_id),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date),
    INDEX idx_manager_id (manager_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预算计划表';

-- ======================= 收支管理表 =======================

-- finance_income表（收入记录表）
DROP TABLE IF EXISTS finance_income;
CREATE TABLE finance_income (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '收入ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    income_number VARCHAR(50) NOT NULL COMMENT '收入编号',
    income_type TINYINT DEFAULT 1 COMMENT '收入类型(1-案件收入,2-顾问收入,3-代理收入,4-利息收入,5-其他收入)',
    income_status TINYINT DEFAULT 1 COMMENT '收入状态(1-已确认,2-已入账,3-已对账)',
    amount DECIMAL(15,2) NOT NULL COMMENT '收入金额',
    currency VARCHAR(10) DEFAULT 'CNY' COMMENT '币种',
    income_date DATETIME NOT NULL COMMENT '收入日期',
    case_id BIGINT COMMENT '关联案件ID',
    contract_id BIGINT COMMENT '关联合同ID',
    client_id BIGINT COMMENT '客户ID',
    account_id BIGINT COMMENT '收款账户ID',
    transaction_id BIGINT COMMENT '关联交易ID',
    invoice_id BIGINT COMMENT '关联发票ID',
    department_id BIGINT COMMENT '部门ID',
    lawyer_id BIGINT COMMENT '负责律师ID',
    income_source VARCHAR(100) COMMENT '收入来源',
    description TEXT COMMENT '收入描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_income_number (tenant_id, income_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_income_type (income_type),
    INDEX idx_income_status (income_status),
    INDEX idx_income_date (income_date),
    INDEX idx_case_id (case_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_client_id (client_id),
    INDEX idx_account_id (account_id),
    INDEX idx_department_id (department_id),
    INDEX idx_lawyer_id (lawyer_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_finance_income_account FOREIGN KEY (account_id) REFERENCES finance_account(id),
    CONSTRAINT fk_finance_income_transaction FOREIGN KEY (transaction_id) REFERENCES finance_transaction(id),
    CONSTRAINT fk_finance_income_invoice FOREIGN KEY (invoice_id) REFERENCES finance_invoice(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收入记录表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 