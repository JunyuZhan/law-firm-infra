-- 创建系统告警相关表

-- 告警规则表
CREATE TABLE IF NOT EXISTS sys_alert_rule (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  rule_name VARCHAR(100) NOT NULL COMMENT '规则名称',
  rule_code VARCHAR(50) NOT NULL COMMENT '规则代码',
  monitor_type VARCHAR(50) NOT NULL COMMENT '监控类型(SERVER,APPLICATION,DATABASE)',
  metric_name VARCHAR(100) NOT NULL COMMENT '监控指标名称',
  threshold_type VARCHAR(20) NOT NULL COMMENT '阈值类型(GREATER,LESS,EQUAL)',
  threshold_value DECIMAL(10,2) NOT NULL COMMENT '阈值',
  duration INT DEFAULT 1 COMMENT '持续次数',
  alert_level VARCHAR(20) NOT NULL COMMENT '告警级别(LOW,MEDIUM,HIGH,CRITICAL)',
  alert_channels VARCHAR(255) DEFAULT 'EMAIL' COMMENT '告警渠道(EMAIL,SMS,WEBHOOK,SYSTEM)',
  is_enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用(0-禁用,1-启用)',
  notify_interval INT DEFAULT 30 COMMENT '通知间隔(分钟)',
  description VARCHAR(255) DEFAULT NULL COMMENT '规则描述',
  create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY uk_rule_code (rule_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警规则表';

-- 告警记录表
CREATE TABLE IF NOT EXISTS sys_alert_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  rule_id BIGINT NOT NULL COMMENT '规则ID',
  alert_target VARCHAR(100) NOT NULL COMMENT '告警目标(服务器IP/应用名/数据库名)',
  metric_name VARCHAR(100) NOT NULL COMMENT '监控指标名称',
  current_value VARCHAR(100) NOT NULL COMMENT '当前值',
  threshold_value VARCHAR(100) NOT NULL COMMENT '阈值',
  alert_level VARCHAR(20) NOT NULL COMMENT '告警级别(LOW,MEDIUM,HIGH,CRITICAL)',
  alert_content TEXT NOT NULL COMMENT '告警内容',
  alert_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '告警时间',
  process_status VARCHAR(20) DEFAULT 'UNPROCESSED' COMMENT '处理状态(UNPROCESSED,PROCESSING,PROCESSED,IGNORED)',
  process_by VARCHAR(50) DEFAULT NULL COMMENT '处理人',
  process_time DATETIME DEFAULT NULL COMMENT '处理时间',
  process_note VARCHAR(255) DEFAULT NULL COMMENT '处理说明',
  is_recovered TINYINT(1) DEFAULT 0 COMMENT '是否已恢复(0-未恢复,1-已恢复)',
  recover_time DATETIME DEFAULT NULL COMMENT '恢复时间',
  KEY idx_rule_id (rule_id),
  KEY idx_alert_time (alert_time),
  KEY idx_process_status (process_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警记录表';

-- 告警通知配置表
CREATE TABLE IF NOT EXISTS sys_alert_notify (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  channel_type VARCHAR(20) NOT NULL COMMENT '通知渠道类型(EMAIL,SMS,WEBHOOK,SYSTEM)',
  channel_name VARCHAR(100) NOT NULL COMMENT '通知渠道名称',
  notify_config TEXT NOT NULL COMMENT '通知配置(JSON格式)',
  alert_levels VARCHAR(50) DEFAULT 'LOW,MEDIUM,HIGH,CRITICAL' COMMENT '接收告警级别',
  is_enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用(0-禁用,1-启用)',
  create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY uk_channel_name (channel_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警通知配置表';

-- 告警通知记录表
CREATE TABLE IF NOT EXISTS sys_alert_notify_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  alert_record_id BIGINT NOT NULL COMMENT '告警记录ID',
  channel_id BIGINT NOT NULL COMMENT '通知渠道ID',
  channel_type VARCHAR(20) NOT NULL COMMENT '通知渠道类型',
  notify_target VARCHAR(100) NOT NULL COMMENT '通知目标(邮箱/手机号/URL等)',
  notify_content TEXT NOT NULL COMMENT '通知内容',
  notify_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通知时间',
  notify_status VARCHAR(20) NOT NULL COMMENT '通知状态(SUCCESS,FAILED)',
  error_message VARCHAR(255) DEFAULT NULL COMMENT '错误信息',
  KEY idx_alert_record_id (alert_record_id),
  KEY idx_notify_time (notify_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警通知记录表'; 