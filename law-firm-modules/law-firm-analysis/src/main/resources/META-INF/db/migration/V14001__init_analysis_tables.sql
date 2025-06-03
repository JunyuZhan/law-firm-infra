-- 数据分析模块表结构
-- 版本: V14001
-- 模块: 数据分析模块 (V14000-V14999)
-- 创建时间: 2023-06-01
-- 说明: 数据分析功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 分析任务管理表 =======================

-- analysis_task表（分析任务主表）
DROP TABLE IF EXISTS analysis_task;
CREATE TABLE analysis_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_number VARCHAR(50) NOT NULL COMMENT '任务编号',
    task_name VARCHAR(200) NOT NULL COMMENT '任务名称',
    description TEXT COMMENT '任务描述',
    task_type TINYINT DEFAULT 1 COMMENT '任务类型(1-业务分析,2-财务分析,3-客户分析,4-案件分析,5-绩效分析)',
    analysis_category TINYINT DEFAULT 1 COMMENT '分析类别(1-统计分析,2-趋势分析,3-对比分析,4-预测分析)',
    task_status TINYINT DEFAULT 1 COMMENT '任务状态(1-待执行,2-执行中,3-已完成,4-失败,5-已暂停,6-已取消)',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    creator_id BIGINT NOT NULL COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    executor_id BIGINT COMMENT '执行者ID',
    executor_name VARCHAR(50) COMMENT '执行者姓名',
    template_id BIGINT COMMENT '使用模板ID',
    datasource_id BIGINT COMMENT '数据源ID',
    schedule_id BIGINT COMMENT '调度配置ID',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    planned_start_time DATETIME COMMENT '计划开始时间',
    planned_end_time DATETIME COMMENT '计划结束时间',
    execution_duration BIGINT DEFAULT 0 COMMENT '执行时长(秒)',
    data_range_start DATETIME COMMENT '数据范围开始时间',
    data_range_end DATETIME COMMENT '数据范围结束时间',
    parameters JSON COMMENT '任务参数(JSON格式)',
    config JSON COMMENT '配置信息(JSON格式)',
    progress_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '完成进度(0-100)',
    result_count BIGINT DEFAULT 0 COMMENT '结果记录数',
    error_message TEXT COMMENT '错误信息',
    retry_count INT DEFAULT 0 COMMENT '重试次数',
    max_retry_count INT DEFAULT 3 COMMENT '最大重试次数',
    is_scheduled TINYINT DEFAULT 0 COMMENT '是否定时任务(0-否,1-是)',
    is_enabled TINYINT DEFAULT 1 COMMENT '是否启用(0-禁用,1-启用)',
    notification_config JSON COMMENT '通知配置(JSON格式)',
    tags JSON COMMENT '标签信息(JSON格式)',
    cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '执行成本',
    quality_score DECIMAL(3,1) DEFAULT 0 COMMENT '质量评分(0-10)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_task_number (tenant_id, task_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_type (task_type),
    INDEX idx_task_status (task_status),
    INDEX idx_priority (priority),
    INDEX idx_creator_id (creator_id),
    INDEX idx_executor_id (executor_id),
    INDEX idx_template_id (template_id),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_is_scheduled (is_scheduled),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析任务主表';

-- analysis_schedule表（分析调度配置表）
DROP TABLE IF EXISTS analysis_schedule;
CREATE TABLE analysis_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '调度ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    schedule_name VARCHAR(100) NOT NULL COMMENT '调度名称',
    schedule_type TINYINT DEFAULT 1 COMMENT '调度类型(1-定时,2-周期,3-条件触发)',
    cron_expression VARCHAR(100) COMMENT 'Cron表达式',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    trigger_condition TEXT COMMENT '触发条件',
    repeat_interval INT DEFAULT 0 COMMENT '重复间隔(分钟)',
    max_execution_count INT DEFAULT 0 COMMENT '最大执行次数(0-不限制)',
    current_execution_count INT DEFAULT 0 COMMENT '当前执行次数',
    last_execution_time DATETIME COMMENT '最后执行时间',
    next_execution_time DATETIME COMMENT '下次执行时间',
    is_active TINYINT DEFAULT 1 COMMENT '是否激活(0-否,1-是)',
    config JSON COMMENT '调度配置(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_schedule_type (schedule_type),
    INDEX idx_next_execution_time (next_execution_time),
    INDEX idx_is_active (is_active),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析调度配置表';

-- analysis_result表（分析结果存储表）
DROP TABLE IF EXISTS analysis_result;
CREATE TABLE analysis_result (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '结果ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    result_type TINYINT DEFAULT 1 COMMENT '结果类型(1-统计数据,2-图表数据,3-报表数据,4-原始数据)',
    result_format TINYINT DEFAULT 1 COMMENT '结果格式(1-JSON,2-XML,3-CSV,4-Excel)',
    result_title VARCHAR(200) COMMENT '结果标题',
    result_summary TEXT COMMENT '结果摘要',
    result_data LONGTEXT COMMENT '结果数据',
    metadata JSON COMMENT '元数据(JSON格式)',
    file_path VARCHAR(500) COMMENT '文件路径',
    file_size BIGINT DEFAULT 0 COMMENT '文件大小(字节)',
    row_count BIGINT DEFAULT 0 COMMENT '行数',
    column_count INT DEFAULT 0 COMMENT '列数',
    chart_config JSON COMMENT '图表配置(JSON格式)',
    export_formats JSON COMMENT '支持导出格式(JSON格式)',
    cache_key VARCHAR(200) COMMENT '缓存键',
    cache_expire_time DATETIME COMMENT '缓存过期时间',
    is_cached TINYINT DEFAULT 0 COMMENT '是否已缓存(0-否,1-是)',
    access_count INT DEFAULT 0 COMMENT '访问次数',
    last_access_time DATETIME COMMENT '最后访问时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_result_type (result_type),
    INDEX idx_cache_key (cache_key),
    INDEX idx_cache_expire_time (cache_expire_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_analysis_result_task FOREIGN KEY (task_id) REFERENCES analysis_task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析结果存储表';

-- ======================= 指标体系管理表 =======================

-- analysis_indicator表（分析指标定义表）
DROP TABLE IF EXISTS analysis_indicator;
CREATE TABLE analysis_indicator (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '指标ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    indicator_code VARCHAR(50) NOT NULL COMMENT '指标编码',
    indicator_name VARCHAR(200) NOT NULL COMMENT '指标名称',
    indicator_type TINYINT DEFAULT 1 COMMENT '指标类型(1-数量,2-质量,3-效率,4-财务,5-风险)',
    data_type TINYINT DEFAULT 1 COMMENT '数据类型(1-整数,2-小数,3-百分比,4-金额,5-文本)',
    unit VARCHAR(20) COMMENT '单位',
    description TEXT COMMENT '指标描述',
    formula_id BIGINT COMMENT '计算公式ID',
    dimension_ids JSON COMMENT '维度ID列表(JSON格式)',
    datasource_id BIGINT COMMENT '数据源ID',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统内置(0-否,1-是)',
    is_key_indicator TINYINT DEFAULT 0 COMMENT '是否关键指标(0-否,1-是)',
    category VARCHAR(50) COMMENT '指标分类',
    group_name VARCHAR(100) COMMENT '指标分组',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    threshold_config JSON COMMENT '阈值配置(JSON格式)',
    format_config JSON COMMENT '格式配置(JSON格式)',
    aggregation_type TINYINT DEFAULT 1 COMMENT '聚合类型(1-求和,2-平均,3-最大,4-最小,5-计数)',
    time_granularity TINYINT DEFAULT 1 COMMENT '时间粒度(1-天,2-周,3-月,4-季度,5-年)',
    refresh_frequency INT DEFAULT 3600 COMMENT '刷新频率(秒)',
    last_calculated_time DATETIME COMMENT '最后计算时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_indicator_code (tenant_id, indicator_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_indicator_type (indicator_type),
    INDEX idx_category (category),
    INDEX idx_group_name (group_name),
    INDEX idx_is_key_indicator (is_key_indicator),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析指标定义表';

-- analysis_dimension表（分析维度配置表）
DROP TABLE IF EXISTS analysis_dimension;
CREATE TABLE analysis_dimension (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '维度ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    dimension_code VARCHAR(50) NOT NULL COMMENT '维度编码',
    dimension_name VARCHAR(100) NOT NULL COMMENT '维度名称',
    dimension_type TINYINT DEFAULT 1 COMMENT '维度类型(1-时间,2-组织,3-业务,4-地域,5-客户)',
    parent_id BIGINT DEFAULT 0 COMMENT '父维度ID',
    level TINYINT DEFAULT 1 COMMENT '层级',
    path VARCHAR(500) COMMENT '层级路径',
    table_name VARCHAR(100) COMMENT '关联表名',
    column_name VARCHAR(50) COMMENT '关联字段名',
    value_type TINYINT DEFAULT 1 COMMENT '值类型(1-字符串,2-数字,3-日期)',
    default_value VARCHAR(200) COMMENT '默认值',
    value_range JSON COMMENT '值范围(JSON格式)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    is_filterable TINYINT DEFAULT 1 COMMENT '是否可筛选(0-否,1-是)',
    is_groupable TINYINT DEFAULT 1 COMMENT '是否可分组(0-否,1-是)',
    config JSON COMMENT '配置信息(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_dimension_code (tenant_id, dimension_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_dimension_type (dimension_type),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析维度配置表';

-- analysis_formula表（计算公式管理表）
DROP TABLE IF EXISTS analysis_formula;
CREATE TABLE analysis_formula (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '公式ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    formula_name VARCHAR(100) NOT NULL COMMENT '公式名称',
    formula_code VARCHAR(50) COMMENT '公式编码',
    formula_type TINYINT DEFAULT 1 COMMENT '公式类型(1-数学公式,2-SQL查询,3-脚本函数)',
    formula_expression TEXT NOT NULL COMMENT '公式表达式',
    variables JSON COMMENT '变量定义(JSON格式)',
    parameters JSON COMMENT '参数配置(JSON格式)',
    description TEXT COMMENT '公式描述',
    example_usage TEXT COMMENT '使用示例',
    validation_rules JSON COMMENT '验证规则(JSON格式)',
    is_builtin TINYINT DEFAULT 0 COMMENT '是否内置(0-否,1-是)',
    category VARCHAR(50) COMMENT '公式分类',
    complexity_level TINYINT DEFAULT 1 COMMENT '复杂度级别(1-简单,2-中等,3-复杂)',
    execution_time_avg DECIMAL(10,3) DEFAULT 0 COMMENT '平均执行时间(毫秒)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    last_used_time DATETIME COMMENT '最后使用时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_formula_code (tenant_id, formula_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_formula_type (formula_type),
    INDEX idx_category (category),
    INDEX idx_complexity_level (complexity_level),
    INDEX idx_usage_count (usage_count),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='计算公式管理表';

-- ======================= 报表管理表 =======================

-- analysis_report表（报表配置表）
DROP TABLE IF EXISTS analysis_report;
CREATE TABLE analysis_report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '报表ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    report_code VARCHAR(50) NOT NULL COMMENT '报表编码',
    report_name VARCHAR(200) NOT NULL COMMENT '报表名称',
    report_type TINYINT DEFAULT 1 COMMENT '报表类型(1-定期报表,2-专题报表,3-实时报表,4-自定义报表)',
    category VARCHAR(50) COMMENT '报表分类',
    description TEXT COMMENT '报表描述',
    datasource_id BIGINT COMMENT '数据源ID',
    template_id BIGINT COMMENT '模板ID',
    layout_config JSON COMMENT '布局配置(JSON格式)',
    style_config JSON COMMENT '样式配置(JSON格式)',
    parameter_config JSON COMMENT '参数配置(JSON格式)',
    filter_config JSON COMMENT '筛选配置(JSON格式)',
    chart_ids JSON COMMENT '图表ID列表(JSON格式)',
    indicator_ids JSON COMMENT '指标ID列表(JSON格式)',
    dimension_ids JSON COMMENT '维度ID列表(JSON格式)',
    refresh_frequency INT DEFAULT 3600 COMMENT '刷新频率(秒)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    is_scheduled TINYINT DEFAULT 0 COMMENT '是否定时生成(0-否,1-是)',
    schedule_config JSON COMMENT '调度配置(JSON格式)',
    export_formats JSON COMMENT '导出格式(JSON格式)',
    owner_id BIGINT NOT NULL COMMENT '拥有者ID',
    owner_name VARCHAR(50) COMMENT '拥有者姓名',
    view_count INT DEFAULT 0 COMMENT '查看次数',
    last_generated_time DATETIME COMMENT '最后生成时间',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_report_code (tenant_id, report_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_report_type (report_type),
    INDEX idx_category (category),
    INDEX idx_owner_id (owner_id),
    INDEX idx_is_public (is_public),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报表配置表';

-- analysis_chart表（图表配置表）
DROP TABLE IF EXISTS analysis_chart;
CREATE TABLE analysis_chart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '图表ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    chart_name VARCHAR(100) NOT NULL COMMENT '图表名称',
    chart_type TINYINT DEFAULT 1 COMMENT '图表类型(1-柱状图,2-折线图,3-饼图,4-散点图,5-热力图,6-雷达图)',
    chart_library TINYINT DEFAULT 1 COMMENT '图表库(1-ECharts,2-D3,3-Highcharts,4-Chart.js)',
    data_config JSON COMMENT '数据配置(JSON格式)',
    style_config JSON COMMENT '样式配置(JSON格式)',
    interaction_config JSON COMMENT '交互配置(JSON格式)',
    animation_config JSON COMMENT '动画配置(JSON格式)',
    responsive_config JSON COMMENT '响应式配置(JSON格式)',
    indicator_ids JSON COMMENT '关联指标ID(JSON格式)',
    dimension_ids JSON COMMENT '关联维度ID(JSON格式)',
    datasource_id BIGINT COMMENT '数据源ID',
    query_config JSON COMMENT '查询配置(JSON格式)',
    cache_config JSON COMMENT '缓存配置(JSON格式)',
    width INT DEFAULT 400 COMMENT '宽度(像素)',
    height INT DEFAULT 300 COMMENT '高度(像素)',
    is_drillable TINYINT DEFAULT 0 COMMENT '是否支持钻取(0-否,1-是)',
    drill_config JSON COMMENT '钻取配置(JSON格式)',
    is_exportable TINYINT DEFAULT 1 COMMENT '是否可导出(0-否,1-是)',
    export_formats JSON COMMENT '导出格式(JSON格式)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_chart_type (chart_type),
    INDEX idx_chart_library (chart_library),
    INDEX idx_datasource_id (datasource_id),
    INDEX idx_usage_count (usage_count),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图表配置表';

-- analysis_dashboard表（仪表盘配置表）
DROP TABLE IF EXISTS analysis_dashboard;
CREATE TABLE analysis_dashboard (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '仪表盘ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    dashboard_name VARCHAR(100) NOT NULL COMMENT '仪表盘名称',
    dashboard_code VARCHAR(50) COMMENT '仪表盘编码',
    description TEXT COMMENT '描述',
    layout_type TINYINT DEFAULT 1 COMMENT '布局类型(1-网格,2-自由,3-模板)',
    layout_config JSON COMMENT '布局配置(JSON格式)',
    theme_config JSON COMMENT '主题配置(JSON格式)',
    chart_configs JSON COMMENT '图表配置列表(JSON格式)',
    filter_config JSON COMMENT '全局筛选配置(JSON格式)',
    refresh_config JSON COMMENT '刷新配置(JSON格式)',
    permission_config JSON COMMENT '权限配置(JSON格式)',
    auto_refresh_interval INT DEFAULT 300 COMMENT '自动刷新间隔(秒)',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认(0-否,1-是)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    owner_id BIGINT NOT NULL COMMENT '拥有者ID',
    owner_name VARCHAR(50) COMMENT '拥有者姓名',
    view_count INT DEFAULT 0 COMMENT '查看次数',
    last_viewed_time DATETIME COMMENT '最后查看时间',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_dashboard_code (tenant_id, dashboard_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_layout_type (layout_type),
    INDEX idx_owner_id (owner_id),
    INDEX idx_is_default (is_default),
    INDEX idx_is_public (is_public),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='仪表盘配置表';

-- ======================= 数据源管理表 =======================

-- analysis_datasource表（数据源配置表）
DROP TABLE IF EXISTS analysis_datasource;
CREATE TABLE analysis_datasource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '数据源ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    datasource_name VARCHAR(100) NOT NULL COMMENT '数据源名称',
    datasource_code VARCHAR(50) COMMENT '数据源编码',
    datasource_type TINYINT DEFAULT 1 COMMENT '数据源类型(1-MySQL,2-PostgreSQL,3-Redis,4-Elasticsearch,5-API,6-文件)',
    connection_config JSON COMMENT '连接配置(JSON格式)',
    auth_config JSON COMMENT '认证配置(JSON格式)',
    pool_config JSON COMMENT '连接池配置(JSON格式)',
    description TEXT COMMENT '描述',
    is_encrypted TINYINT DEFAULT 1 COMMENT '是否加密存储(0-否,1-是)',
    connection_status TINYINT DEFAULT 0 COMMENT '连接状态(0-未测试,1-正常,2-异常)',
    last_test_time DATETIME COMMENT '最后测试时间',
    last_test_result TEXT COMMENT '最后测试结果',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    health_check_interval INT DEFAULT 300 COMMENT '健康检查间隔(秒)',
    timeout_seconds INT DEFAULT 30 COMMENT '超时时间(秒)',
    retry_count INT DEFAULT 3 COMMENT '重试次数',
    is_readonly TINYINT DEFAULT 1 COMMENT '是否只读(0-否,1-是)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_datasource_code (tenant_id, datasource_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_datasource_type (datasource_type),
    INDEX idx_connection_status (connection_status),
    INDEX idx_usage_count (usage_count),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源配置表';

-- analysis_dataset表（数据集定义表）
DROP TABLE IF EXISTS analysis_dataset;
CREATE TABLE analysis_dataset (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '数据集ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    dataset_name VARCHAR(100) NOT NULL COMMENT '数据集名称',
    dataset_code VARCHAR(50) COMMENT '数据集编码',
    dataset_type TINYINT DEFAULT 1 COMMENT '数据集类型(1-表,2-视图,3-查询,4-API,5-文件)',
    datasource_id BIGINT NOT NULL COMMENT '数据源ID',
    source_config JSON COMMENT '数据源配置(JSON格式)',
    query_config JSON COMMENT '查询配置(JSON格式)',
    field_config JSON COMMENT '字段配置(JSON格式)',
    filter_config JSON COMMENT '过滤配置(JSON格式)',
    cache_config JSON COMMENT '缓存配置(JSON格式)',
    description TEXT COMMENT '描述',
    refresh_mode TINYINT DEFAULT 1 COMMENT '刷新模式(1-实时,2-定时,3-手动)',
    refresh_interval INT DEFAULT 3600 COMMENT '刷新间隔(秒)',
    last_refresh_time DATETIME COMMENT '最后刷新时间',
    row_count BIGINT DEFAULT 0 COMMENT '行数',
    column_count INT DEFAULT 0 COMMENT '列数',
    data_size BIGINT DEFAULT 0 COMMENT '数据大小(字节)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    owner_id BIGINT NOT NULL COMMENT '拥有者ID',
    owner_name VARCHAR(50) COMMENT '拥有者姓名',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_dataset_code (tenant_id, dataset_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_dataset_type (dataset_type),
    INDEX idx_datasource_id (datasource_id),
    INDEX idx_refresh_mode (refresh_mode),
    INDEX idx_owner_id (owner_id),
    INDEX idx_usage_count (usage_count),
    INDEX idx_status (status),
    
    CONSTRAINT fk_analysis_dataset_datasource FOREIGN KEY (datasource_id) REFERENCES analysis_datasource(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据集定义表';

-- analysis_cache表（分析缓存表）
DROP TABLE IF EXISTS analysis_cache;
CREATE TABLE analysis_cache (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '缓存ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    cache_key VARCHAR(200) NOT NULL COMMENT '缓存键',
    cache_type TINYINT DEFAULT 1 COMMENT '缓存类型(1-查询结果,2-图表数据,3-报表数据,4-计算结果)',
    cache_data LONGTEXT COMMENT '缓存数据',
    metadata JSON COMMENT '元数据(JSON格式)',
    data_size BIGINT DEFAULT 0 COMMENT '数据大小(字节)',
    hit_count INT DEFAULT 0 COMMENT '命中次数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    expire_time DATETIME NOT NULL COMMENT '过期时间',
    last_access_time DATETIME COMMENT '最后访问时间',
    access_count INT DEFAULT 0 COMMENT '访问次数',
    source_info JSON COMMENT '来源信息(JSON格式)',
    compression_type TINYINT DEFAULT 0 COMMENT '压缩类型(0-无,1-GZIP,2-LZ4)',
    is_compressed TINYINT DEFAULT 0 COMMENT '是否压缩(0-否,1-是)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    
    UNIQUE KEY uk_tenant_cache_key (tenant_id, cache_key),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_cache_type (cache_type),
    INDEX idx_expire_time (expire_time),
    INDEX idx_hit_count (hit_count),
    INDEX idx_last_access_time (last_access_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析缓存表';

-- ======================= 模板管理表 =======================

-- analysis_template表（分析模板表）
DROP TABLE IF EXISTS analysis_template;
CREATE TABLE analysis_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    template_name VARCHAR(100) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) COMMENT '模板编码',
    template_type TINYINT DEFAULT 1 COMMENT '模板类型(1-任务模板,2-报表模板,3-图表模板,4-仪表盘模板)',
    category VARCHAR(50) COMMENT '模板分类',
    description TEXT COMMENT '模板描述',
    preview_image VARCHAR(500) COMMENT '预览图片',
    config_template JSON COMMENT '配置模板(JSON格式)',
    default_config JSON COMMENT '默认配置(JSON格式)',
    parameter_schema JSON COMMENT '参数模式(JSON格式)',
    is_builtin TINYINT DEFAULT 0 COMMENT '是否内置(0-否,1-是)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    rating DECIMAL(3,1) DEFAULT 0 COMMENT '评分(0-10)',
    rating_count INT DEFAULT 0 COMMENT '评分人数',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    author_name VARCHAR(50) COMMENT '作者姓名',
    tags JSON COMMENT '标签(JSON格式)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_template_code (tenant_id, template_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_template_type (template_type),
    INDEX idx_category (category),
    INDEX idx_author_id (author_id),
    INDEX idx_usage_count (usage_count),
    INDEX idx_rating (rating),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析模板表';

-- ======================= 历史记录表 =======================

-- analysis_history表（分析历史表）
DROP TABLE IF EXISTS analysis_history;
CREATE TABLE analysis_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '历史ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    execution_id VARCHAR(50) COMMENT '执行ID',
    execution_status TINYINT DEFAULT 1 COMMENT '执行状态(1-成功,2-失败,3-超时,4-取消)',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    execution_duration BIGINT DEFAULT 0 COMMENT '执行时长(毫秒)',
    result_summary TEXT COMMENT '结果摘要',
    error_message TEXT COMMENT '错误信息',
    result_count BIGINT DEFAULT 0 COMMENT '结果记录数',
    data_processed BIGINT DEFAULT 0 COMMENT '处理数据量',
    memory_used BIGINT DEFAULT 0 COMMENT '内存使用量(字节)',
    cpu_time BIGINT DEFAULT 0 COMMENT 'CPU时间(毫秒)',
    executor_info JSON COMMENT '执行器信息(JSON格式)',
    parameters JSON COMMENT '执行参数(JSON格式)',
    performance_metrics JSON COMMENT '性能指标(JSON格式)',
    retry_count INT DEFAULT 0 COMMENT '重试次数',
    parent_execution_id VARCHAR(50) COMMENT '父执行ID',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_execution_id (execution_id),
    INDEX idx_execution_status (execution_status),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_parent_execution_id (parent_execution_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_analysis_history_task FOREIGN KEY (task_id) REFERENCES analysis_task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分析历史表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 