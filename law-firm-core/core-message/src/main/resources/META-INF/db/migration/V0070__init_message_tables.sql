-- Core-Message模块表结构初始化
-- 版本: V0070
-- 模块: 消息模块 (V0070-V0074)
-- 创建时间: 2023-07-01
-- 说明: 消息功能模块的完整表结构定义，基于message-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 消息模块核心表 =======================

-- message_base表（基础消息表）
DROP TABLE IF EXISTS message_base;
CREATE TABLE message_base (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '基础消息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_no VARCHAR(50) NOT NULL COMMENT '消息编号',
    title VARCHAR(200) COMMENT '消息标题',
    content TEXT COMMENT '消息内容',
    message_type VARCHAR(20) NOT NULL COMMENT '消息类型',
    status VARCHAR(20) NOT NULL COMMENT '消息状态',
    sender_id BIGINT COMMENT '发送者ID',
    sender_name VARCHAR(100) COMMENT '发送者名称',
    sender_type TINYINT DEFAULT 1 COMMENT '发送者类型(1-系统,2-用户)',
    receiver_id BIGINT COMMENT '接收者ID',
    receiver_name VARCHAR(100) COMMENT '接收者名称',
    receiver_type TINYINT DEFAULT 1 COMMENT '接收者类型(1-用户,2-角色,3-部门,4-全部)',
    send_time DATETIME COMMENT '发送时间',
    read_time DATETIME COMMENT '阅读时间',
    process_time DATETIME COMMENT '处理时间',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    need_confirm TINYINT DEFAULT 0 COMMENT '是否需要确认(0-否,1-是)',
    confirm_time DATETIME COMMENT '确认时间',
    message_config JSON COMMENT '消息配置(JSON格式)',
    business_id BIGINT COMMENT '关联业务ID',
    business_type VARCHAR(50) COMMENT '关联业务类型',
    contains_sensitive_data TINYINT DEFAULT 0 COMMENT '是否包含敏感数据(0-否,1-是)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_message_no (message_no),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_send_time (send_time),
    INDEX idx_status (status),
    INDEX idx_message_type (message_type),
    INDEX idx_business_id (business_id),
    INDEX idx_business_type (business_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基础消息表';

-- message_system表（系统消息表）
DROP TABLE IF EXISTS message_system;
CREATE TABLE message_system (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '系统消息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_id BIGINT NOT NULL COMMENT '关联消息ID',
    level TINYINT DEFAULT 1 COMMENT '消息级别(1-提示,2-警告,3-错误)',
    source VARCHAR(100) COMMENT '消息来源',
    module VARCHAR(50) COMMENT '消息模块',
    is_broadcast TINYINT DEFAULT 0 COMMENT '是否广播(0-否,1-是)',
    is_persistent TINYINT DEFAULT 1 COMMENT '是否持久化(0-否,1-是)',
    category VARCHAR(50) COMMENT '消息分类',
    tags VARCHAR(255) COMMENT '消息标签',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_message_id (message_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_level (level),
    INDEX idx_module (module),
    INDEX idx_category (category),
    
    CONSTRAINT fk_message_system_base FOREIGN KEY (message_id) REFERENCES message_base(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统消息表';

-- message_receiver表（消息接收者表）
DROP TABLE IF EXISTS message_receiver;
CREATE TABLE message_receiver (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '消息接收者ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_id BIGINT NOT NULL COMMENT '关联消息ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者ID',
    receiver_type TINYINT DEFAULT 1 COMMENT '接收者类型(1-用户,2-角色,3-部门,4-全部)',
    read_status TINYINT DEFAULT 0 COMMENT '阅读状态(0-未读,1-已读)',
    read_time DATETIME COMMENT '阅读时间',
    confirm_status TINYINT DEFAULT 0 COMMENT '确认状态(0-未确认,1-已确认)',
    confirm_time DATETIME COMMENT '确认时间',
    process_status TINYINT DEFAULT 0 COMMENT '处理状态(0-未处理,1-已处理)',
    process_time DATETIME COMMENT '处理时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    UNIQUE KEY uk_message_receiver (message_id, receiver_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_receiver_id (receiver_id),
    INDEX idx_read_status (read_status),
    INDEX idx_process_status (process_status),
    
    CONSTRAINT fk_message_receiver_base FOREIGN KEY (message_id) REFERENCES message_base(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息接收者表';

-- message_case表（案件消息表）
DROP TABLE IF EXISTS message_case;
CREATE TABLE message_case (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '案件消息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_id BIGINT NOT NULL COMMENT '关联消息ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    case_code VARCHAR(50) COMMENT '案件编号',
    case_title VARCHAR(255) COMMENT '案件标题',
    milestone VARCHAR(50) COMMENT '案件里程碑',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    due_date DATETIME COMMENT '截止日期',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_message_id (message_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_case_id (case_id),
    INDEX idx_case_code (case_code),
    INDEX idx_due_date (due_date),
    
    CONSTRAINT fk_message_case_base FOREIGN KEY (message_id) REFERENCES message_base(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='案件消息表';

-- message_contract表（合同消息表）
DROP TABLE IF EXISTS message_contract;
CREATE TABLE message_contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '合同消息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_id BIGINT NOT NULL COMMENT '关联消息ID',
    contract_id BIGINT NOT NULL COMMENT '合同ID',
    contract_code VARCHAR(50) COMMENT '合同编号',
    contract_title VARCHAR(255) COMMENT '合同标题',
    contract_stage VARCHAR(50) COMMENT '合同阶段',
    party_name VARCHAR(100) COMMENT '相对方名称',
    amount DECIMAL(20,2) COMMENT '合同金额',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    due_date DATETIME COMMENT '截止日期',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_message_id (message_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_contract_id (contract_id),
    INDEX idx_contract_code (contract_code),
    INDEX idx_due_date (due_date),
    
    CONSTRAINT fk_message_contract_base FOREIGN KEY (message_id) REFERENCES message_base(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同消息表';

-- message_notification表（通知表）
DROP TABLE IF EXISTS message_notification;
CREATE TABLE message_notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '通知ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    notify_id VARCHAR(36) NOT NULL COMMENT '通知ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    title VARCHAR(200) COMMENT '通知标题',
    content TEXT COMMENT '通知内容',
    notify_type VARCHAR(20) NOT NULL COMMENT '通知类型',
    business_type VARCHAR(50) COMMENT '业务类型',
    business_id BIGINT COMMENT '业务ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读(0-未读,1-已读)',
    read_time DATETIME COMMENT '阅读时间',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    icon VARCHAR(100) COMMENT '图标',
    link VARCHAR(255) COMMENT '链接',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    UNIQUE KEY uk_notify_id (notify_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read),
    INDEX idx_business_id (business_id),
    INDEX idx_business_type (business_type),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 