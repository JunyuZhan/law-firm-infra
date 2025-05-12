-- System模块监控表结构初始化
-- 版本: V1001
-- 模块: system/monitor
-- 创建时间: 2023-06-10
-- 说明: 创建系统监控相关表，基于实体类定义
-- 依赖: V0001基础表结构，包括sys_dict_type和sys_dict_data表

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 检查依赖表是否存在
SELECT 1 FROM information_schema.tables 
WHERE table_schema = DATABASE() 
  AND table_name IN ('sys_dict_type', 'sys_dict_data');

-- sys_app_monitor表（应用监控表）
CREATE TABLE IF NOT EXISTS sys_app_monitor (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  app_name VARCHAR(100) NOT NULL COMMENT '应用名称',
  instance_id VARCHAR(100) NOT NULL COMMENT '实例ID',
  jvm_memory_used BIGINT COMMENT 'JVM内存使用(bytes)',
  jvm_memory_max BIGINT COMMENT 'JVM最大内存(bytes)',
  thread_count INTEGER COMMENT '线程数量',
  jvm_threads INTEGER COMMENT 'JVM线程数',
  heap_used BIGINT COMMENT '堆内存使用(bytes)',
  non_heap_used BIGINT COMMENT '非堆内存使用(bytes)',
  thread_active_count INTEGER COMMENT '活动线程数',
  thread_peak_count INTEGER COMMENT '峰值线程数',
  gc_count INTEGER COMMENT 'GC次数',
  gc_time BIGINT COMMENT 'GC耗时(ms)',
  http_sessions INTEGER COMMENT 'HTTP会话数',
  monitor_time DATETIME COMMENT '监控时间',
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
  KEY idx_app_name (app_name),
  KEY idx_instance_id (instance_id),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用监控表';

-- sys_db_monitor表（数据库监控表）
CREATE TABLE IF NOT EXISTS sys_db_monitor (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  db_name VARCHAR(100) NOT NULL COMMENT '数据库名称',
  db_url VARCHAR(200) COMMENT '数据库连接',
  active_connections INTEGER COMMENT '活动连接数',
  max_connections INTEGER COMMENT '最大连接数',
  qps DECIMAL(10,2) COMMENT '每秒查询数',
  tps DECIMAL(10,2) COMMENT '每秒事务数',
  slow_queries INTEGER COMMENT '慢查询数',
  table_size BIGINT COMMENT '数据表大小(bytes)',
  index_size BIGINT COMMENT '索引大小(bytes)',
  db_status VARCHAR(20) COMMENT '状态(NORMAL,WARNING,ERROR)',
  monitor_time DATETIME COMMENT '监控时间',
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
  KEY idx_db_name (db_name),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据库监控表';

-- sys_server_monitor表（服务器监控表）
CREATE TABLE IF NOT EXISTS sys_server_monitor (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  server_name VARCHAR(100) COMMENT '服务器名称',
  server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
  cpu_usage DECIMAL(10,2) COMMENT 'CPU使用率(%)',
  memory_usage DECIMAL(10,2) COMMENT '内存使用率(%)',
  disk_usage DECIMAL(10,2) COMMENT '磁盘使用率(%)',
  network_rx BIGINT COMMENT '网络接收量(bytes)',
  network_tx BIGINT COMMENT '网络发送量(bytes)',
  server_load VARCHAR(50) COMMENT '服务器负载',
  monitor_time DATETIME COMMENT '监控时间',
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
  KEY idx_server_ip (server_ip),
  KEY idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务器监控表';

-- sys_monitor_alert表（监控告警表）
CREATE TABLE IF NOT EXISTS sys_monitor_alert (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  alert_id VARCHAR(50) NOT NULL COMMENT '告警ID',
  type VARCHAR(50) NOT NULL COMMENT '告警类型（CPU/Memory/Disk等）',
  level VARCHAR(20) NOT NULL COMMENT '告警级别（INFO/WARNING/ERROR）',
  message TEXT COMMENT '告警信息',
  alert_status VARCHAR(20) COMMENT '告警状态（PENDING待处理/HANDLING处理中/CLOSED已关闭）',
  handler VARCHAR(50) COMMENT '处理人',
  handle_time DATETIME COMMENT '处理时间',
  handle_result VARCHAR(500) COMMENT '处理结果',
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
  UNIQUE KEY uk_alert_id (alert_id),
  KEY idx_type (type),
  KEY idx_level (level),
  KEY idx_alert_status (alert_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监控告警表';

-- sys_monitor_config表（监控配置表）
CREATE TABLE IF NOT EXISTS sys_monitor_config (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  type VARCHAR(50) NOT NULL COMMENT '监控类型（CPU/Memory/Disk等）',
  name VARCHAR(100) NOT NULL COMMENT '配置名称',
  threshold DOUBLE NOT NULL COMMENT '阈值',
  interval_seconds INTEGER DEFAULT 60 COMMENT '采集间隔（秒）',
  enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用（0否/1是）',
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
  KEY idx_type (type),
  KEY idx_enabled (enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='监控配置表';

-- sys_monitor_data表（监控数据表）
CREATE TABLE IF NOT EXISTS sys_monitor_data (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  type VARCHAR(50) NOT NULL COMMENT '监控类型（CPU/Memory/Disk等）',
  name VARCHAR(100) NOT NULL COMMENT '监控项名称',
  title VARCHAR(100) COMMENT '监控项标题',
  value DOUBLE COMMENT '监控值',
  unit VARCHAR(20) COMMENT '单位',
  threshold DOUBLE COMMENT '阈值',
  description VARCHAR(255) COMMENT '描述',
  collect_time DATETIME COMMENT '采集时间',
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
  KEY idx_type (type),
  KEY idx_collect_time (collect_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统监控数据表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 