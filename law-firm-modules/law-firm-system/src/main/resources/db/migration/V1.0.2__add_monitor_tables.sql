-- 监控数据表
CREATE TABLE IF NOT EXISTS sys_monitor_data (
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    type varchar(50) NOT NULL COMMENT '监控类型（CPU/Memory/Disk等）',
    name varchar(100) NOT NULL COMMENT '监控项名称',
    title varchar(100) NOT NULL COMMENT '监控项标题',
    value double NOT NULL COMMENT '监控值',
    unit varchar(20) NOT NULL COMMENT '单位',
    threshold double DEFAULT NULL COMMENT '阈值',
    description varchar(500) DEFAULT NULL COMMENT '描述',
    collect_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '采集时间',
    create_by varchar(64) NOT NULL COMMENT '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by varchar(64) DEFAULT NULL COMMENT '更新人',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
    PRIMARY KEY (id),
    KEY idx_type (type),
    KEY idx_collect_time (collect_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统监控数据表';

-- 监控告警表
CREATE TABLE IF NOT EXISTS sys_monitor_alert (
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    alert_id varchar(64) NOT NULL COMMENT '告警ID',
    type varchar(50) NOT NULL COMMENT '告警类型（CPU/Memory/Disk等）',
    level varchar(20) NOT NULL COMMENT '告警级别（INFO/WARNING/ERROR）',
    message varchar(500) NOT NULL COMMENT '告警信息',
    status varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING待处理/HANDLING处理中/CLOSED已关闭）',
    handler varchar(64) DEFAULT NULL COMMENT '处理人',
    handle_time datetime DEFAULT NULL COMMENT '处理时间',
    handle_result varchar(500) DEFAULT NULL COMMENT '处理结果',
    create_by varchar(64) NOT NULL COMMENT '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by varchar(64) DEFAULT NULL COMMENT '更新人',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_alert_id (alert_id),
    KEY idx_type (type),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统监控告警表';

-- 监控配置表
CREATE TABLE IF NOT EXISTS sys_monitor_config (
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    type varchar(50) NOT NULL COMMENT '监控类型（CPU/Memory/Disk等）',
    name varchar(100) NOT NULL COMMENT '配置名称',
    threshold double NOT NULL COMMENT '阈值',
    interval_seconds int NOT NULL DEFAULT 60 COMMENT '采集间隔（秒）',
    enabled tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（0否/1是）',
    description varchar(500) DEFAULT NULL COMMENT '描述',
    create_by varchar(64) NOT NULL COMMENT '创建人',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by varchar(64) DEFAULT NULL COMMENT '更新人',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_type_name (type, name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统监控配置表';

-- 初始化监控配置数据
INSERT INTO sys_monitor_config (type, name, threshold, interval_seconds, enabled, description, create_by) VALUES 
('CPU', 'cpu_usage', 80, 60, 1, 'CPU使用率监控', 'admin'),
('Memory', 'memory_usage', 80, 60, 1, '内存使用率监控', 'admin'),
('Disk', 'disk_usage', 90, 300, 1, '磁盘使用率监控', 'admin'),
('SystemLoad', 'load_average', 10, 60, 1, '系统负载监控', 'admin'); 