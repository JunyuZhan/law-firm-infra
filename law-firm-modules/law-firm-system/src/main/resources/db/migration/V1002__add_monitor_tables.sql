-- 添加系统监控表

-- 创建系统性能监控表
CREATE TABLE IF NOT EXISTS sys_performance_monitor (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
  cpu_usage DECIMAL(5,2) NOT NULL COMMENT 'CPU使用率(%)',
  memory_usage DECIMAL(5,2) NOT NULL COMMENT '内存使用率(%)',
  memory_total BIGINT(20) NOT NULL COMMENT '总内存(MB)',
  memory_used BIGINT(20) NOT NULL COMMENT '已用内存(MB)',
  disk_usage DECIMAL(5,2) NOT NULL COMMENT '磁盘使用率(%)',
  disk_total BIGINT(20) NOT NULL COMMENT '总磁盘空间(MB)',
  disk_used BIGINT(20) NOT NULL COMMENT '已用磁盘空间(MB)',
  system_load DECIMAL(10,2) NOT NULL COMMENT '系统负载',
  monitor_time DATETIME NOT NULL COMMENT '监控时间',
  remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by BIGINT(20) DEFAULT NULL COMMENT '创建人ID',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by BIGINT(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (id),
  KEY idx_monitor_server_ip (server_ip),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统性能监控表';

-- 创建在线用户表
CREATE TABLE IF NOT EXISTS sys_online_user (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id BIGINT(20) NOT NULL COMMENT '用户ID',
  username VARCHAR(50) NOT NULL COMMENT '用户名',
  real_name VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  ip_address VARCHAR(50) NOT NULL COMMENT 'IP地址',
  location VARCHAR(100) DEFAULT NULL COMMENT '登录地点',
  browser VARCHAR(100) DEFAULT NULL COMMENT '浏览器',
  os VARCHAR(100) DEFAULT NULL COMMENT '操作系统',
  token VARCHAR(255) NOT NULL COMMENT '会话令牌',
  login_time DATETIME NOT NULL COMMENT '登录时间',
  last_activity_time DATETIME NOT NULL COMMENT '最后活动时间',
  expire_time DATETIME NOT NULL COMMENT '过期时间',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_online_user_token (token),
  KEY idx_online_user_id (user_id),
  KEY idx_online_username (username),
  KEY idx_online_ip (ip_address),
  KEY idx_online_login_time (login_time),
  KEY idx_online_expire_time (expire_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线用户表'; 