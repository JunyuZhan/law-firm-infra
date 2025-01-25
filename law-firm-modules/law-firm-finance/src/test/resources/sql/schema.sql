-- 收费记录表
CREATE TABLE IF NOT EXISTS fee_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL COMMENT '收费金额',
    paid_amount DECIMAL(19,2) DEFAULT 0.00 COMMENT '已支付金额',
    fee_status VARCHAR(20) COMMENT '收费状态：UNPAID/PAID/PARTIAL/REFUNDED',
    fee_type VARCHAR(50) COMMENT '收费类型：案件收费/咨询收费/其他收费',
    case_id BIGINT COMMENT '关联案件ID',
    client_id BIGINT COMMENT '关联客户ID',
    law_firm_id BIGINT COMMENT '关联律所ID',
    payment_time DATETIME COMMENT '支付时间',
    payment_method VARCHAR(50) COMMENT '支付方式',
    description VARCHAR(500) COMMENT '收费说明',
    remark VARCHAR(500) COMMENT '备注',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    INDEX idx_case_id (case_id),
    INDEX idx_client_id (client_id),
    INDEX idx_law_firm_id (law_firm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费记录表';

-- 支出记录表
CREATE TABLE IF NOT EXISTS expense_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(19,2) NOT NULL COMMENT '支出金额',
    expense_status VARCHAR(20) COMMENT '支出状态：PENDING/APPROVED/REJECTED/PAID',
    expense_type VARCHAR(50) COMMENT '支出类型：日常运营/人员工资/办公设备/其他支出',
    law_firm_id BIGINT COMMENT '关联律所ID',
    department_id BIGINT COMMENT '关联部门ID',
    applicant_id BIGINT COMMENT '申请人ID',
    approver_id BIGINT COMMENT '审批人ID',
    expense_time DATETIME COMMENT '支出时间',
    payment_method VARCHAR(50) COMMENT '支付方式',
    description VARCHAR(500) COMMENT '支出说明',
    remark VARCHAR(500) COMMENT '备注',
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    INDEX idx_law_firm_id (law_firm_id),
    INDEX idx_department_id (department_id),
    INDEX idx_applicant_id (applicant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支出记录表'; 