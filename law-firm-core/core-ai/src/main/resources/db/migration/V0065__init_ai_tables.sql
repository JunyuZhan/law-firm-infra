-- Core-AI模块表结构初始化
-- 版本: V0065
-- 模块: core-ai
-- 创建时间: 2023-07-01
-- 说明: 创建AI相关表，基于ai-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ai_model_config表（AI模型配置表）
CREATE TABLE IF NOT EXISTS ai_model_config (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  model_id VARCHAR(50) NOT NULL COMMENT '模型ID',
  model_name VARCHAR(100) NOT NULL COMMENT '模型名称',
  provider VARCHAR(50) NOT NULL COMMENT '服务提供商（如OpenAI、百度等）',
  model_type VARCHAR(30) NOT NULL COMMENT '模型类型',
  api_version VARCHAR(20) COMMENT 'API版本',
  model_version VARCHAR(20) COMMENT '模型版本',
  endpoint VARCHAR(255) COMMENT '服务端点URL',
  api_key VARCHAR(255) COMMENT 'API密钥（加密存储）',
  max_tokens INTEGER DEFAULT 2048 COMMENT '最大Token数',
  temperature DOUBLE DEFAULT 0.7 COMMENT '温度参数（控制创造性，0-2）',
  top_p DOUBLE DEFAULT 1.0 COMMENT 'Top P参数',
  prompt_template TEXT COMMENT '提示词模板（系统提示词）',
  last_used_time DATETIME COMMENT '最后使用时间',
  config_json TEXT COMMENT '额外配置（JSON格式）',
  description VARCHAR(255) COMMENT '描述',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_model_id (model_id),
  KEY idx_provider (provider),
  KEY idx_model_type (model_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI模型配置表';

-- ai_conversation表（AI对话会话表）
CREATE TABLE IF NOT EXISTS ai_conversation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID（UUID）',
  user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
  title VARCHAR(100) COMMENT '会话标题',
  last_active_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后活动时间',
  conversation_status INTEGER DEFAULT 0 COMMENT '会话状态：0进行中/1已结束/2已归档',
  model_id VARCHAR(50) COMMENT '使用的AI模型ID',
  message_count INTEGER DEFAULT 0 COMMENT '消息数量',
  summary TEXT COMMENT '会话摘要',
  category VARCHAR(50) COMMENT '对话类别（如法律咨询、文档分析等）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_conversation_id (conversation_id),
  KEY idx_user_id (user_id),
  KEY idx_last_active_time (last_active_time),
  KEY idx_conversation_status (conversation_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话会话表';

-- ai_message表（消息表）
CREATE TABLE IF NOT EXISTS ai_message (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  message_id VARCHAR(36) NOT NULL COMMENT '消息ID（UUID）',
  conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID',
  role VARCHAR(20) NOT NULL COMMENT '角色（user-用户/assistant-助手/system-系统）',
  content TEXT NOT NULL COMMENT '消息内容',
  tokens INTEGER DEFAULT 0 COMMENT 'Token数量',
  message_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间',
  message_type VARCHAR(20) DEFAULT 'text' COMMENT '消息类型（text/image/file等）',
  metadata TEXT COMMENT '元数据（JSON格式）',
  parent_id VARCHAR(36) COMMENT '父消息ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_message_id (message_id),
  KEY idx_conversation_id (conversation_id),
  KEY idx_message_time (message_time),
  KEY idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI消息表';

-- ai_user_feedback表（用户反馈表）
CREATE TABLE IF NOT EXISTS ai_user_feedback (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  feedback_id VARCHAR(36) NOT NULL COMMENT '反馈ID（UUID）',
  message_id VARCHAR(36) NOT NULL COMMENT '消息ID',
  conversation_id VARCHAR(36) NOT NULL COMMENT '会话ID',
  user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
  rating INTEGER COMMENT '评分（1-5）',
  feedback_content TEXT COMMENT '反馈内容',
  feedback_type VARCHAR(20) DEFAULT 'rating' COMMENT '反馈类型（rating/thumbs/comment）',
  feedback_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_feedback_id (feedback_id),
  UNIQUE KEY uk_message_user (message_id, user_id),
  KEY idx_message_id (message_id),
  KEY idx_conversation_id (conversation_id),
  KEY idx_user_id (user_id),
  KEY idx_feedback_time (feedback_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI用户反馈表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 