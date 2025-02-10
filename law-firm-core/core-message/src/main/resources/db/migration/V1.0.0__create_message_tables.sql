-- 消息表
CREATE TABLE message (
    id VARCHAR(32) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type VARCHAR(50),
    sender_id BIGINT,
    sender_name VARCHAR(100),
    receiver_id BIGINT NOT NULL,
    receiver_name VARCHAR(100),
    is_read BOOLEAN DEFAULT FALSE,
    read_time TIMESTAMP,
    template_id VARCHAR(32),
    params TEXT,
    business_type VARCHAR(50),
    business_id VARCHAR(50),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    INDEX idx_receiver (receiver_id, is_read),
    INDEX idx_sender (sender_id),
    INDEX idx_business (business_type, business_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 消息模板表
CREATE TABLE message_template (
    id VARCHAR(32) PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(50),
    enabled BOOLEAN DEFAULT TRUE,
    param_desc TEXT,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    UNIQUE INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 用户消息设置表
CREATE TABLE user_message_setting (
    id VARCHAR(32) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    type VARCHAR(50),
    receive_site_message BOOLEAN DEFAULT TRUE,
    receive_email BOOLEAN DEFAULT FALSE,
    receive_sms BOOLEAN DEFAULT FALSE,
    receive_wechat BOOLEAN DEFAULT FALSE,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    UNIQUE INDEX idx_user_type (user_id, type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; 