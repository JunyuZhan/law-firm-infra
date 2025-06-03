-- Core-AI模块表结构初始化
-- 版本: V0065
-- 模块: AI智能模块 (V0060-V0069)
-- 创建时间: 2023-07-01
-- 说明: AI智能功能模块的完整表结构定义，基于ai-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= AI智能模块核心表 =======================

-- ai_model_config表（AI模型配置表）
DROP TABLE IF EXISTS ai_model_config;
CREATE TABLE ai_model_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'AI模型配置ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    model_id VARCHAR(50) NOT NULL COMMENT '模型ID',
    model_name VARCHAR(100) NOT NULL COMMENT '模型名称',
    provider VARCHAR(50) NOT NULL COMMENT '服务提供商(如OpenAI、百度等)',
    model_type VARCHAR(30) NOT NULL COMMENT '模型类型',
    api_version VARCHAR(20) COMMENT 'API版本',
    model_version VARCHAR(20) COMMENT '模型版本',
    endpoint VARCHAR(255) COMMENT '服务端点URL',
    api_key VARCHAR(255) COMMENT 'API密钥(加密存储)',
    max_tokens INT DEFAULT 2048 COMMENT '最大Token数',
    temperature DOUBLE DEFAULT 0.7 COMMENT '温度参数(控制创造性，0-2)',
    top_p DOUBLE DEFAULT 1.0 COMMENT 'Top P参数',
    prompt_template TEXT COMMENT '提示词模板(系统提示词)',
    last_used_time DATETIME COMMENT '最后使用时间',
    config_json TEXT COMMENT '额外配置(JSON格式)',
    description VARCHAR(255) COMMENT '描述',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_model_id (model_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_provider (provider),
    INDEX idx_model_type (model_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI模型配置表';

-- ai_conversation表（AI对话会话表）
DROP TABLE IF EXISTS ai_conversation;
CREATE TABLE ai_conversation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'AI对话会话ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID(UUID)',
    user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    title VARCHAR(100) COMMENT '会话标题',
    last_active_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后活动时间',
    conversation_status TINYINT DEFAULT 0 COMMENT '会话状态(0-进行中,1-已结束,2-已归档)',
    model_id VARCHAR(50) COMMENT '使用的AI模型ID',
    message_count INT DEFAULT 0 COMMENT '消息数量',
    summary TEXT COMMENT '会话摘要',
    category VARCHAR(50) COMMENT '对话类别(如法律咨询、文档分析等)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_conversation_id (conversation_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_user_id (user_id),
    INDEX idx_last_active_time (last_active_time),
    INDEX idx_conversation_status (conversation_status),
    INDEX idx_model_id (model_id),
    
    CONSTRAINT fk_ai_conversation_model FOREIGN KEY (model_id) REFERENCES ai_model_config(model_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI对话会话表';

-- ai_message表（AI消息表）
DROP TABLE IF EXISTS ai_message;
CREATE TABLE ai_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'AI消息ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    message_id VARCHAR(36) NOT NULL COMMENT '消息ID(UUID)',
    conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID',
    role VARCHAR(20) NOT NULL COMMENT '角色(user-用户,assistant-助手,system-系统)',
    content TEXT NOT NULL COMMENT '消息内容',
    tokens INT DEFAULT 0 COMMENT 'Token数量',
    message_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间',
    message_type VARCHAR(20) DEFAULT 'text' COMMENT '消息类型(text,image,file等)',
    metadata TEXT COMMENT '元数据(JSON格式)',
    parent_id VARCHAR(36) COMMENT '父消息ID',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_message_id (message_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_message_time (message_time),
    INDEX idx_role (role),
    INDEX idx_parent_id (parent_id),
    
    CONSTRAINT fk_ai_message_conversation FOREIGN KEY (conversation_id) REFERENCES ai_conversation(conversation_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI消息表';

-- ai_user_feedback表（AI用户反馈表）
DROP TABLE IF EXISTS ai_user_feedback;
CREATE TABLE ai_user_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'AI用户反馈ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    feedback_id VARCHAR(36) NOT NULL COMMENT '反馈ID(UUID)',
    message_id VARCHAR(36) NOT NULL COMMENT '消息ID',
    conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID',
    user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    rating TINYINT COMMENT '评分(1-5)',
    feedback_content TEXT COMMENT '反馈内容',
    feedback_type VARCHAR(20) DEFAULT 'rating' COMMENT '反馈类型(rating,thumbs,comment)',
    feedback_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    UNIQUE KEY uk_feedback_id (feedback_id),
    UNIQUE KEY uk_message_user (message_id, user_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_message_id (message_id),
    INDEX idx_conversation_id (conversation_id),
    INDEX idx_user_id (user_id),
    INDEX idx_feedback_time (feedback_time),
    INDEX idx_rating (rating),
    
    CONSTRAINT fk_ai_feedback_message FOREIGN KEY (message_id) REFERENCES ai_message(message_id) ON DELETE CASCADE,
    CONSTRAINT fk_ai_feedback_conversation FOREIGN KEY (conversation_id) REFERENCES ai_conversation(conversation_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI用户反馈表';

-- 添加AI相关系统配置
-- 使用统一的系统配置分组名称"SYS_AI_CONFIG"
-- 配置按照功能分组批量插入

-- 基础AI配置
INSERT INTO sys_config (config_key, config_value, config_type, group_name, is_system, status, remark, create_by)
VALUES 
('AI_ENABLED', 'true', 1, 'SYS_AI_CONFIG', 1, 1, 'AI功能启用状态', 'system'),
('AI_DEFAULT_PROVIDER', 'OPENAI', 1, 'SYS_AI_CONFIG', 1, 1, '默认AI服务提供商(OPENAI/BAIDU/LOCAL等)', 'system'),
('AI_MAX_TOKENS', '2048', 1, 'SYS_AI_CONFIG', 1, 1, 'AI最大Token数', 'system'),
('AI_TEMPERATURE', '0.7', 1, 'SYS_AI_CONFIG', 1, 1, 'AI温度参数(0-2)', 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- OpenAI相关配置
INSERT INTO sys_config (config_key, config_value, config_type, group_name, is_system, status, remark, create_by)
VALUES 
('OPENAI_API_KEY', '', 1, 'SYS_AI_CONFIG', 1, 1, 'OpenAI API密钥', 'system'),
('OPENAI_API_BASE_URL', 'https://api.openai.com/v1', 1, 'SYS_AI_CONFIG', 1, 1, 'OpenAI API基础URL', 'system'),
('OPENAI_MODEL', 'gpt-3.5-turbo', 1, 'SYS_AI_CONFIG', 1, 1, 'OpenAI默认模型', 'system'),
('OPENAI_TIMEOUT', '60000', 1, 'SYS_AI_CONFIG', 1, 1, 'OpenAI请求超时时间(毫秒)', 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 百度AI相关配置
INSERT INTO sys_config (config_key, config_value, config_type, group_name, is_system, status, remark, create_by)
VALUES 
('BAIDU_AI_API_KEY', '', 1, 'SYS_AI_CONFIG', 1, 1, '百度AI API密钥', 'system'),
('BAIDU_AI_SECRET_KEY', '', 1, 'SYS_AI_CONFIG', 1, 1, '百度AI 密钥', 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 本地模型相关配置
INSERT INTO sys_config (config_key, config_value, config_type, group_name, is_system, status, remark, create_by)
VALUES 
('AI_LOCAL_EMBEDDING_MODEL', '', 1, 'SYS_AI_CONFIG', 1, 1, '本地嵌入模型路径', 'system'),
('AI_LOCAL_LLM_MODEL', '', 1, 'SYS_AI_CONFIG', 1, 1, '本地LLM模型路径', 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 