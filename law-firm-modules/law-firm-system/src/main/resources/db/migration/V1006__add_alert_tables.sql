-- 添加系统告警相关表

-- 创建系统告警配置表
CREATE TABLE IF NOT EXISTS sys_alert_config (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  name VARCHAR(100) NOT NULL COMMENT '告警配置名称',
  type VARCHAR(50) NOT NULL COMMENT '告警类型：cpu_usage、memory_usage、disk_usage、system_error等',
  threshold DECIMAL(10,2) NOT NULL COMMENT '告警阈值',
  comparison VARCHAR(10) NOT NULL COMMENT '比较符：>、<、>=、<=、==、!=',
  level VARCHAR(20) NOT NULL COMMENT '告警级别：info、warning、error、critical',
  status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  notify_type VARCHAR(20) NOT NULL COMMENT '通知方式：email、sms、webhook等，多个用逗号分隔',
  notify_target VARCHAR(500) NOT NULL COMMENT '通知目标：邮箱、手机号、URL等，多个用逗号分隔',
  recovery_threshold DECIMAL(10,2) DEFAULT NULL COMMENT '恢复阈值',
  recovery_comparison VARCHAR(10) DEFAULT NULL COMMENT '恢复比较符',
  check_interval INT(11) NOT NULL DEFAULT 60 COMMENT '检查间隔(秒)',
  continuous_trigger INT(11) NOT NULL DEFAULT 1 COMMENT '连续触发次数',
  description VARCHAR(500) DEFAULT NULL COMMENT '告警描述',
  deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-否，1-是',
  tenant_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (id),
  KEY idx_alert_config_type (type),
  KEY idx_alert_config_level (level),
  KEY idx_alert_config_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统告警配置表';

-- 创建系统告警记录表
CREATE TABLE IF NOT EXISTS sys_alert_record (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  config_id BIGINT(20) NOT NULL COMMENT '告警配置ID',
  server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
  alert_type VARCHAR(50) NOT NULL COMMENT '告警类型',
  alert_level VARCHAR(20) NOT NULL COMMENT '告警级别',
  alert_value DECIMAL(10,2) NOT NULL COMMENT '告警值',
  threshold DECIMAL(10,2) NOT NULL COMMENT '告警阈值',
  comparison VARCHAR(10) NOT NULL COMMENT '比较符',
  alert_time DATETIME NOT NULL COMMENT '告警时间',
  recover_time DATETIME DEFAULT NULL COMMENT '恢复时间',
  recovery_value DECIMAL(10,2) DEFAULT NULL COMMENT '恢复值',
  duration INT(11) DEFAULT NULL COMMENT '持续时间(秒)',
  status VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态：active-活动，resolved-已解决',
  notify_status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '通知状态：pending-待通知，success-通知成功，failed-通知失败',
  notify_time DATETIME DEFAULT NULL COMMENT '通知时间',
  message TEXT NOT NULL COMMENT '告警消息',
  detail TEXT DEFAULT NULL COMMENT '告警详情',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_alert_record_config_id (config_id),
  KEY idx_alert_record_type (alert_type),
  KEY idx_alert_record_level (alert_level),
  KEY idx_alert_record_time (alert_time),
  KEY idx_alert_record_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统告警记录表'; 