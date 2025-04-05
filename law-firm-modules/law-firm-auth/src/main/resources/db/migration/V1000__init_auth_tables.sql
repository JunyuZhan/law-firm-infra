-- 认证授权模块表结构初始化

-- 创建OAuth客户端表
CREATE TABLE IF NOT EXISTS oauth_client (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  client_id VARCHAR(100) NOT NULL COMMENT '客户端ID',
  client_secret VARCHAR(256) NOT NULL COMMENT '客户端密钥',
  client_name VARCHAR(100) NOT NULL COMMENT '客户端名称',
  redirect_uri VARCHAR(2000) COMMENT '重定向URI',
  grant_types VARCHAR(100) NOT NULL COMMENT '授权类型',
  scope VARCHAR(100) COMMENT '权限范围',
  auto_approve TINYINT(1) DEFAULT 0 COMMENT '是否自动授权',
  access_token_validity INT DEFAULT 7200 COMMENT '访问令牌有效期(秒)',
  refresh_token_validity INT DEFAULT 86400 COMMENT '刷新令牌有效期(秒)',
  additional_information VARCHAR(4096) COMMENT '附加信息',
  status INT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_by VARCHAR(64) COMMENT '创建人',
  update_by VARCHAR(64) COMMENT '更新人',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (id),
  UNIQUE KEY uk_client_id (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OAuth客户端表';

-- 创建用户第三方账号关联表
CREATE TABLE IF NOT EXISTS user_social (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  provider_type VARCHAR(30) NOT NULL COMMENT '提供商类型(WECHAT,ALIPAY,DINGTALK)',
  provider_user_id VARCHAR(100) NOT NULL COMMENT '提供商用户ID',
  provider_user_name VARCHAR(100) COMMENT '提供商用户名',
  union_id VARCHAR(100) COMMENT 'UnionID',
  credentials VARCHAR(1000) COMMENT '凭证',
  verified TINYINT(1) DEFAULT 0 COMMENT '是否验证',
  status INT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  create_by VARCHAR(64) COMMENT '创建人',
  update_by VARCHAR(64) COMMENT '更新人',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志',
  PRIMARY KEY (id),
  UNIQUE KEY uk_provider_user_id (provider_type, provider_user_id),
  KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户第三方账号关联表';

-- 创建用户登录历史表
CREATE TABLE IF NOT EXISTS user_login_history (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  login_type VARCHAR(20) NOT NULL COMMENT '登录类型(PASSWORD,SSO,SOCIAL)',
  ip_address VARCHAR(50) COMMENT 'IP地址',
  user_agent VARCHAR(500) COMMENT '用户代理',
  login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  login_status TINYINT(1) DEFAULT 1 COMMENT '登录状态: 0-失败, 1-成功',
  failure_reason VARCHAR(100) COMMENT '失败原因',
  session_id VARCHAR(100) COMMENT '会话ID',
  device_type VARCHAR(20) COMMENT '设备类型',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_login_time (login_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录历史表';

-- 创建验证码表
CREATE TABLE IF NOT EXISTS auth_verification_code (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  target VARCHAR(100) NOT NULL COMMENT '目标(手机号/邮箱)',
  code VARCHAR(20) NOT NULL COMMENT '验证码',
  type VARCHAR(20) NOT NULL COMMENT '类型(REGISTER,LOGIN,RESET_PASSWORD)',
  expire_time DATETIME NOT NULL COMMENT '过期时间',
  used TINYINT(1) DEFAULT 0 COMMENT '是否已使用',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_target_type (target, type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';

-- 创建会话表
CREATE TABLE IF NOT EXISTS auth_session (
  id VARCHAR(100) NOT NULL COMMENT '会话ID',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  username VARCHAR(100) NOT NULL COMMENT '用户名',
  ip_address VARCHAR(50) COMMENT 'IP地址',
  user_agent VARCHAR(500) COMMENT '用户代理',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  expire_time DATETIME NOT NULL COMMENT '过期时间',
  last_access_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
  status INT DEFAULT 1 COMMENT '状态: 0-过期, 1-活跃',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_expire_time (expire_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表'; 