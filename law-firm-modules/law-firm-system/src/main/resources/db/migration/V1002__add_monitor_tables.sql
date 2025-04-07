-- 创建系统监控表

-- 服务器监控表
CREATE TABLE IF NOT EXISTS sys_server_monitor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  server_name VARCHAR(100) NOT NULL COMMENT '服务器名称',
  server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
  cpu_usage DECIMAL(5,2) DEFAULT 0 COMMENT 'CPU使用率(%)',
  memory_usage DECIMAL(5,2) DEFAULT 0 COMMENT '内存使用率(%)',
  disk_usage DECIMAL(5,2) DEFAULT 0 COMMENT '磁盘使用率(%)',
  network_rx BIGINT DEFAULT 0 COMMENT '网络接收量(bytes)',
  network_tx BIGINT DEFAULT 0 COMMENT '网络发送量(bytes)',
  server_load VARCHAR(50) DEFAULT NULL COMMENT '服务器负载',
  monitor_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '监控时间',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY idx_server_ip (server_ip),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器监控表';

-- 应用监控表
CREATE TABLE IF NOT EXISTS sys_app_monitor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  app_name VARCHAR(100) NOT NULL COMMENT '应用名称',
  instance_id VARCHAR(100) NOT NULL COMMENT '实例ID',
  jvm_memory_used BIGINT DEFAULT 0 COMMENT 'JVM内存使用(bytes)',
  jvm_memory_max BIGINT DEFAULT 0 COMMENT 'JVM最大内存(bytes)',
  jvm_threads INT DEFAULT 0 COMMENT 'JVM线程数',
  heap_used BIGINT DEFAULT 0 COMMENT '堆内存使用(bytes)',
  non_heap_used BIGINT DEFAULT 0 COMMENT '非堆内存使用(bytes)',
  thread_active_count INT DEFAULT 0 COMMENT '活动线程数',
  thread_peak_count INT DEFAULT 0 COMMENT '峰值线程数',
  gc_count INT DEFAULT 0 COMMENT 'GC次数',
  gc_time BIGINT DEFAULT 0 COMMENT 'GC耗时(ms)',
  http_sessions INT DEFAULT 0 COMMENT 'HTTP会话数',
  monitor_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '监控时间',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY idx_app_name (app_name),
  KEY idx_instance_id (instance_id),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用监控表';

-- 数据库监控表
CREATE TABLE IF NOT EXISTS sys_db_monitor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  db_name VARCHAR(100) NOT NULL COMMENT '数据库名称',
  db_url VARCHAR(255) NOT NULL COMMENT '数据库连接',
  active_connections INT DEFAULT 0 COMMENT '活动连接数',
  max_connections INT DEFAULT 0 COMMENT '最大连接数',
  qps DECIMAL(10,2) DEFAULT 0 COMMENT '每秒查询数',
  tps DECIMAL(10,2) DEFAULT 0 COMMENT '每秒事务数',
  slow_queries INT DEFAULT 0 COMMENT '慢查询数',
  table_size BIGINT DEFAULT 0 COMMENT '数据表大小(bytes)',
  index_size BIGINT DEFAULT 0 COMMENT '索引大小(bytes)',
  status VARCHAR(20) DEFAULT 'NORMAL' COMMENT '状态(NORMAL,WARNING,ERROR)',
  monitor_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '监控时间',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY idx_db_name (db_name),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库监控表'; 