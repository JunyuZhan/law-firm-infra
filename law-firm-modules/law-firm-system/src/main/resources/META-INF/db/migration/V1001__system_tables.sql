-- 系统管理模块表结构
-- 版本: V1001
-- 模块: 系统管理模块 (V1000-V1999)
-- 创建时间: 2023-06-01
-- 说明: 系统管理功能的完整表结构定义
-- 包括：菜单管理、角色管理、组织管理、定时任务、系统监控、系统升级

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 菜单管理表 =======================

-- sys_menu表（系统菜单表）
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '菜单ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    menu_code VARCHAR(100) NOT NULL COMMENT '菜单编码',
    menu_type TINYINT NOT NULL COMMENT '菜单类型(1-目录,2-菜单,3-按钮)',
    path VARCHAR(200) COMMENT '路由地址',
    component VARCHAR(255) COMMENT '组件路径',
    perms VARCHAR(100) COMMENT '权限标识',
    icon VARCHAR(100) COMMENT '菜单图标',
    is_frame TINYINT DEFAULT 0 COMMENT '是否外链(0-否,1-是)',
    is_cache TINYINT DEFAULT 1 COMMENT '是否缓存(0-否,1-是)',
    visible TINYINT DEFAULT 1 COMMENT '是否显示(0-否,1-是)',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_menu_code (tenant_id, menu_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_menu_type (menu_type),
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

-- ======================= 角色管理表 =======================

-- sys_role表（系统角色表）
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(100) NOT NULL COMMENT '角色编码',
    role_type TINYINT DEFAULT 1 COMMENT '角色类型(1-系统角色,2-业务角色)',
    data_scope TINYINT DEFAULT 1 COMMENT '数据权限(1-全部,2-自定义,3-本部门,4-本部门及下级,5-仅本人)',
    description VARCHAR(255) COMMENT '角色描述',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_role_code (tenant_id, role_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_role_type (role_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- sys_role_menu表（角色菜单关联表）
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    
    UNIQUE KEY uk_role_menu (role_id, menu_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_role_id (role_id),
    INDEX idx_menu_id (menu_id),
    
    CONSTRAINT fk_role_menu_role FOREIGN KEY (role_id) REFERENCES sys_role(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_menu_menu FOREIGN KEY (menu_id) REFERENCES sys_menu(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- ======================= 组织管理表 =======================

-- sys_organization表（组织架构表）
DROP TABLE IF EXISTS sys_organization;
CREATE TABLE sys_organization (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '组织ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父组织ID',
    org_name VARCHAR(50) NOT NULL COMMENT '组织名称',
    org_code VARCHAR(100) NOT NULL COMMENT '组织编码',
    org_type TINYINT DEFAULT 1 COMMENT '组织类型(1-公司,2-部门,3-小组)',
    level_code VARCHAR(255) COMMENT '层级编码(用于快速查询)',
    leader_id BIGINT COMMENT '负责人ID',
    leader_name VARCHAR(50) COMMENT '负责人姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱地址',
    address VARCHAR(255) COMMENT '办公地址',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_org_code (tenant_id, org_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_org_type (org_type),
    INDEX idx_leader_id (leader_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组织架构表';

-- ======================= 定时任务表 =======================

-- sys_job表（定时任务表）
DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    job_name VARCHAR(50) NOT NULL COMMENT '任务名称',
    job_group VARCHAR(50) DEFAULT 'DEFAULT' COMMENT '任务分组',
    job_class VARCHAR(255) NOT NULL COMMENT '任务类名',
    cron_expression VARCHAR(100) NOT NULL COMMENT 'Cron表达式',
    description VARCHAR(255) COMMENT '任务描述',
    is_concurrent TINYINT DEFAULT 0 COMMENT '是否并发(0-否,1-是)',
    misfire_policy TINYINT DEFAULT 1 COMMENT '错过策略(1-立即执行,2-执行一次,3-放弃执行)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-暂停,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_job_name (job_name),
    INDEX idx_job_group (job_group),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务表';

-- sys_job_log表（任务执行日志表）
DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    job_id BIGINT NOT NULL COMMENT '任务ID',
    job_name VARCHAR(50) COMMENT '任务名称',
    job_group VARCHAR(50) COMMENT '任务分组',
    job_class VARCHAR(255) COMMENT '任务类名',
    cron_expression VARCHAR(100) COMMENT 'Cron表达式',
    execute_status TINYINT COMMENT '执行状态(0-失败,1-成功)',
    execute_time DATETIME COMMENT '执行时间',
    execute_duration INT COMMENT '执行时长(毫秒)',
    execute_result TEXT COMMENT '执行结果',
    error_message TEXT COMMENT '错误信息',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_job_id (job_id),
    INDEX idx_execute_status (execute_status),
    INDEX idx_execute_time (execute_time),
    
    CONSTRAINT fk_job_log_job FOREIGN KEY (job_id) REFERENCES sys_job(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务执行日志表';

-- ======================= 系统监控表 =======================

-- sys_app_monitor表（应用监控表）
DROP TABLE IF EXISTS sys_app_monitor;
CREATE TABLE sys_app_monitor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '监控ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    app_name VARCHAR(100) NOT NULL COMMENT '应用名称',
    instance_id VARCHAR(100) NOT NULL COMMENT '实例ID',
    jvm_memory_used BIGINT COMMENT 'JVM内存使用(bytes)',
    jvm_memory_max BIGINT COMMENT 'JVM最大内存(bytes)',
    thread_count INT COMMENT '线程数量',
    gc_count INT COMMENT 'GC次数',
    monitor_time DATETIME COMMENT '监控时间',
    version INT DEFAULT 0 COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_app_name (app_name),
    INDEX idx_instance_id (instance_id),
    INDEX idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用监控表';

-- sys_db_monitor表（数据库监控表）
DROP TABLE IF EXISTS sys_db_monitor;
CREATE TABLE sys_db_monitor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '监控ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    db_name VARCHAR(100) NOT NULL COMMENT '数据库名称',
    db_url VARCHAR(1024) COMMENT '数据库连接',
    active_connections INT COMMENT '活动连接数',
    max_connections INT COMMENT '最大连接数',
    qps DECIMAL(10,2) COMMENT '每秒查询数',
    tps DECIMAL(10,2) COMMENT '每秒事务数',
    slow_queries INT COMMENT '慢查询数',
    table_size BIGINT COMMENT '数据表大小(bytes)',
    index_size BIGINT COMMENT '索引大小(bytes)',
    db_status VARCHAR(20) COMMENT '状态(NORMAL,WARNING,ERROR)',
    monitor_time DATETIME COMMENT '监控时间',
    version INT DEFAULT 0 COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_db_name (db_name),
    INDEX idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库监控表';

-- sys_server_monitor表（服务器监控表）
DROP TABLE IF EXISTS sys_server_monitor;
CREATE TABLE sys_server_monitor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '监控ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    server_name VARCHAR(100) COMMENT '服务器名称',
    server_ip VARCHAR(50) NOT NULL COMMENT '服务器IP',
    cpu_usage DECIMAL(10,2) COMMENT 'CPU使用率(%)',
    memory_usage DECIMAL(10,2) COMMENT '内存使用率(%)',
    disk_usage DECIMAL(10,2) COMMENT '磁盘使用率(%)',
    network_rx BIGINT COMMENT '网络接收量(bytes)',
    network_tx BIGINT COMMENT '网络发送量(bytes)',
    server_load VARCHAR(1024) COMMENT '服务器负载',
    monitor_time DATETIME COMMENT '监控时间',
    version INT DEFAULT 0 COMMENT '版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_server_ip (server_ip),
    INDEX idx_monitor_time (monitor_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务器监控表';

-- sys_monitor_config表（监控配置表）
DROP TABLE IF EXISTS sys_monitor_config;
CREATE TABLE sys_monitor_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    monitor_type TINYINT NOT NULL COMMENT '监控类型(1-服务器,2-应用,3-数据库,4-接口)',
    monitor_name VARCHAR(100) NOT NULL COMMENT '监控名称',
    monitor_target VARCHAR(255) NOT NULL COMMENT '监控目标',
    threshold_warning DECIMAL(10,2) COMMENT '警告阈值',
    threshold_critical DECIMAL(10,2) COMMENT '严重阈值',
    check_interval INT DEFAULT 60 COMMENT '检查间隔(秒)',
    timeout_seconds INT DEFAULT 30 COMMENT '超时时间(秒)',
    retry_count INT DEFAULT 3 COMMENT '重试次数',
    notification_enabled TINYINT DEFAULT 1 COMMENT '是否通知(0-否,1-是)',
    notification_channels VARCHAR(255) COMMENT '通知渠道(邮件,短信,钉钉等)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_monitor_type (monitor_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='监控配置表';

-- sys_monitor_data表（监控数据表）
DROP TABLE IF EXISTS sys_monitor_data;
CREATE TABLE sys_monitor_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '数据ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    config_id BIGINT NOT NULL COMMENT '配置ID',
    metric_name VARCHAR(100) NOT NULL COMMENT '指标名称',
    metric_value DECIMAL(15,4) COMMENT '指标值',
    metric_unit VARCHAR(20) COMMENT '单位',
    status TINYINT COMMENT '状态(0-正常,1-警告,2-严重)',
    collect_time DATETIME NOT NULL COMMENT '采集时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_config_id (config_id),
    INDEX idx_metric_name (metric_name),
    INDEX idx_collect_time (collect_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_monitor_data_config FOREIGN KEY (config_id) REFERENCES sys_monitor_config(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='监控数据表';

-- sys_monitor_alert表（监控告警表）
DROP TABLE IF EXISTS sys_monitor_alert;
CREATE TABLE sys_monitor_alert (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '告警ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    config_id BIGINT NOT NULL COMMENT '配置ID',
    alert_level TINYINT NOT NULL COMMENT '告警级别(1-警告,2-严重,3-紧急)',
    alert_title VARCHAR(255) NOT NULL COMMENT '告警标题',
    alert_content TEXT COMMENT '告警内容',
    alert_time DATETIME NOT NULL COMMENT '告警时间',
    alert_status TINYINT DEFAULT 1 COMMENT '处理状态(1-待处理,2-处理中,3-已处理,4-已忽略)',
    handler_id BIGINT COMMENT '处理人ID',
    handler_name VARCHAR(50) COMMENT '处理人姓名',
    handle_time DATETIME COMMENT '处理时间',
    handle_result TEXT COMMENT '处理结果',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_config_id (config_id),
    INDEX idx_alert_level (alert_level),
    INDEX idx_alert_status (alert_status),
    INDEX idx_alert_time (alert_time),
    
    CONSTRAINT fk_monitor_alert_config FOREIGN KEY (config_id) REFERENCES sys_monitor_config(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='监控告警表';

-- ======================= 系统升级表 =======================

-- sys_upgrade表（系统升级表）
DROP TABLE IF EXISTS sys_upgrade;
CREATE TABLE sys_upgrade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '升级ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    version_from VARCHAR(20) NOT NULL COMMENT '源版本',
    version_to VARCHAR(20) NOT NULL COMMENT '目标版本',
    upgrade_type TINYINT DEFAULT 1 COMMENT '升级类型(1-补丁,2-小版本,3-大版本)',
    upgrade_status TINYINT DEFAULT 1 COMMENT '升级状态(1-待升级,2-升级中,3-升级成功,4-升级失败)',
    upgrade_description TEXT COMMENT '升级说明',
    backup_required TINYINT DEFAULT 1 COMMENT '是否需要备份(0-否,1-是)',
    backup_path VARCHAR(500) COMMENT '备份路径',
    upgrade_script TEXT COMMENT '升级脚本',
    rollback_script TEXT COMMENT '回滚脚本',
    upgrade_start_time DATETIME COMMENT '升级开始时间',
    upgrade_end_time DATETIME COMMENT '升级结束时间',
    upgrade_duration INT COMMENT '升级耗时(秒)',
    upgrade_result TEXT COMMENT '升级结果',
    error_message TEXT COMMENT '错误信息',
    operator_id BIGINT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_version_from (version_from),
    INDEX idx_version_to (version_to),
    INDEX idx_upgrade_status (upgrade_status),
    INDEX idx_upgrade_start_time (upgrade_start_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统升级表';

-- sys_upgrade_log表（升级日志表）
DROP TABLE IF EXISTS sys_upgrade_log;
CREATE TABLE sys_upgrade_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    upgrade_id BIGINT NOT NULL COMMENT '升级ID',
    step_name VARCHAR(100) NOT NULL COMMENT '步骤名称',
    step_order INT NOT NULL COMMENT '步骤顺序',
    step_status TINYINT COMMENT '步骤状态(1-执行中,2-成功,3-失败)',
    step_description TEXT COMMENT '步骤描述',
    step_result TEXT COMMENT '执行结果',
    error_message TEXT COMMENT '错误信息',
    execute_start_time DATETIME COMMENT '开始时间',
    execute_end_time DATETIME COMMENT '结束时间',
    execute_duration INT COMMENT '执行时长(毫秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_upgrade_id (upgrade_id),
    INDEX idx_step_order (step_order),
    INDEX idx_step_status (step_status),
    
    CONSTRAINT fk_upgrade_log_upgrade FOREIGN KEY (upgrade_id) REFERENCES sys_upgrade(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='升级日志表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;