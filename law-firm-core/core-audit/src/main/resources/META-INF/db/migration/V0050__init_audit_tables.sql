-- Core-Audit模块表结构初始化
-- 版本: V0050
-- 模块: core-audit
-- 创建时间: 2023-07-01
-- 说明: 创建审计相关表，基于log-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- t_audit_log表（审计日志表）
CREATE TABLE IF NOT EXISTS t_audit_log (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  operator_id BIGINT COMMENT '操作人ID',
  operator_name VARCHAR(100) COMMENT '操作人名称',
  operator_ip VARCHAR(50) COMMENT '操作人IP',
  operate_type VARCHAR(20) COMMENT '操作类型',
  business_type VARCHAR(20) COMMENT '业务类型',
  module VARCHAR(50) COMMENT '操作模块',
  description VARCHAR(255) COMMENT '操作描述',
  status INTEGER DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  error_msg TEXT COMMENT '错误消息',
  operation_time DATETIME COMMENT '操作时间',
  before_data TEXT COMMENT '操作前数据',
  after_data TEXT COMMENT '操作后数据',
  changed_fields TEXT COMMENT '变更项',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_operation_time (operation_time),
  KEY idx_operator_id (operator_id),
  KEY idx_module (module)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审计日志表';

-- t_audit_record表（审计记录表）
CREATE TABLE IF NOT EXISTS t_audit_record (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  audit_log_id BIGINT NOT NULL COMMENT '审计日志ID',
  auditor_id BIGINT NOT NULL COMMENT '审计人ID',
  auditor_name VARCHAR(100) COMMENT '审计人名称',
  audit_action VARCHAR(50) NOT NULL COMMENT '审计动作（如：提交、审核、驳回等）',
  audit_result INTEGER DEFAULT 0 COMMENT '审计结果（0通过 1不通过）',
  audit_opinion TEXT COMMENT '审计意见',
  audit_time DATETIME NOT NULL COMMENT '审计时间',
  audit_node VARCHAR(50) COMMENT '审计节点',
  next_node VARCHAR(50) COMMENT '下一个审计节点',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_audit_log_id (audit_log_id),
  KEY idx_auditor_id (auditor_id),
  KEY idx_audit_time (audit_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审计记录表';

-- sys_login_log表（登录日志表）
CREATE TABLE IF NOT EXISTS sys_login_log (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  operator_id BIGINT COMMENT '操作人ID',
  operator_name VARCHAR(100) COMMENT '操作人名称',
  operate_type VARCHAR(20) COMMENT '操作类型',
  business_type VARCHAR(20) COMMENT '业务类型',
  module VARCHAR(50) COMMENT '操作模块',
  description VARCHAR(255) COMMENT '操作描述',
  status INTEGER DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  error_msg TEXT COMMENT '错误消息',
  username VARCHAR(100) COMMENT '登录账号',
  login_status INTEGER DEFAULT 0 COMMENT '登录状态（0成功 1失败）',
  login_msg VARCHAR(255) COMMENT '登录信息',
  login_time VARCHAR(50) COMMENT '登录时间',
  logout_time VARCHAR(50) COMMENT '退出时间',
  online_time BIGINT COMMENT '在线时长（分钟）',
  session_id VARCHAR(100) COMMENT '会话ID',
  login_type VARCHAR(20) COMMENT '登录类型（如：账号密码、手机验证码、微信等）',
  login_ip VARCHAR(50) COMMENT '登录IP',
  login_location VARCHAR(100) COMMENT '登录地点',
  browser VARCHAR(50) COMMENT '浏览器类型',
  os VARCHAR(50) COMMENT '操作系统',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_username (username),
  KEY idx_login_time (login_time),
  KEY idx_login_status (login_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- sys_system_log表（系统日志表）
CREATE TABLE IF NOT EXISTS sys_system_log (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  operator_id BIGINT COMMENT '操作人ID',
  operator_name VARCHAR(100) COMMENT '操作人名称',
  operate_type VARCHAR(20) COMMENT '操作类型',
  business_type VARCHAR(20) COMMENT '业务类型',
  module VARCHAR(50) COMMENT '操作模块',
  description VARCHAR(255) COMMENT '操作描述',
  status INTEGER DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  error_msg TEXT COMMENT '错误消息',
  system_module VARCHAR(50) COMMENT '系统模块',
  function_name VARCHAR(100) COMMENT '功能名称',
  method_name VARCHAR(100) COMMENT '方法名称',
  request_method VARCHAR(10) COMMENT '请求方式',
  request_url VARCHAR(255) COMMENT '请求URL',
  request_params TEXT COMMENT '请求参数',
  response_params TEXT COMMENT '返回参数',
  execution_time BIGINT COMMENT '执行时间(毫秒)',
  exception_info TEXT COMMENT '异常信息',
  operator_ip VARCHAR(50) COMMENT '操作人IP',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_system_module (system_module),
  KEY idx_function_name (function_name),
  KEY idx_status (status),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- sys_operation_log表（操作日志表）
CREATE TABLE IF NOT EXISTS sys_operation_log (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  operator_id BIGINT COMMENT '操作人ID',
  operator_name VARCHAR(100) COMMENT '操作人名称',
  operate_type VARCHAR(20) COMMENT '操作类型',
  business_type VARCHAR(20) COMMENT '业务类型',
  module VARCHAR(50) COMMENT '操作模块',
  description VARCHAR(255) COMMENT '操作描述',
  status INTEGER DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  error_msg TEXT COMMENT '错误消息',
  target_id BIGINT COMMENT '操作对象ID',
  target_type VARCHAR(50) COMMENT '操作对象类型',
  target_name VARCHAR(100) COMMENT '操作对象名称',
  method VARCHAR(100) COMMENT '操作方法',
  params TEXT COMMENT '操作参数',
  result TEXT COMMENT '操作结果',
  cost_time BIGINT COMMENT '操作耗时(毫秒)',
  source VARCHAR(50) COMMENT '操作来源（如：PC端、移动端等）',
  operator_ip VARCHAR(50) COMMENT '操作人IP',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_target_id (target_id),
  KEY idx_target_type (target_type),
  KEY idx_operate_type (operate_type),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- sys_business_log表（业务日志表）
CREATE TABLE IF NOT EXISTS sys_business_log (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  operator_id BIGINT COMMENT '操作人ID',
  operator_name VARCHAR(100) COMMENT '操作人名称',
  operate_type VARCHAR(20) COMMENT '操作类型',
  business_type VARCHAR(20) COMMENT '业务类型',
  module VARCHAR(50) COMMENT '操作模块',
  description VARCHAR(255) COMMENT '操作描述',
  status INTEGER DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  error_msg TEXT COMMENT '错误消息',
  business_id VARCHAR(50) COMMENT '业务ID',
  business_module VARCHAR(50) COMMENT '业务模块',
  business_operation VARCHAR(50) COMMENT '业务操作',
  business_data TEXT COMMENT '业务数据',
  operator_ip VARCHAR(50) COMMENT '操作人IP',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_business_id (business_id),
  KEY idx_business_module (business_module),
  KEY idx_business_operation (business_operation),
  KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务日志表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 