-- Core-Workflow模块表结构初始化
-- 版本: V0075
-- 模块: 工作流模块 (V0070-V0079)
-- 创建时间: 2023-07-01
-- 说明: 工作流功能模块的完整表结构定义，基于workflow-model模块中的实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 工作流模块核心表 =======================

-- workflow_process_template表（流程模板表）
DROP TABLE IF EXISTS workflow_process_template;
CREATE TABLE workflow_process_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程模板ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    template_key VARCHAR(100) NOT NULL COMMENT '流程模板标识',
    template_name VARCHAR(100) NOT NULL COMMENT '流程模板名称',
    template_category VARCHAR(50) COMMENT '流程模板分类',
    business_type VARCHAR(50) COMMENT '业务类型',
    template_version VARCHAR(20) COMMENT '模板版本号',
    process_definition_id VARCHAR(100) COMMENT '流程定义ID',
    deployment_id VARCHAR(100) COMMENT '流程部署ID',
    description VARCHAR(255) COMMENT '流程模板描述',
    suspended TINYINT DEFAULT 0 COMMENT '是否挂起(0-否,1-是)',
    creator_name VARCHAR(100) COMMENT '创建人名称',
    updater_name VARCHAR(100) COMMENT '更新人名称',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_template_key_version (template_key, template_version),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_business_type (business_type),
    INDEX idx_template_category (template_category),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程模板表';

-- workflow_process表（流程实例表）
DROP TABLE IF EXISTS workflow_process;
CREATE TABLE workflow_process (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程实例ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    process_no VARCHAR(50) NOT NULL COMMENT '流程编号',
    process_name VARCHAR(100) NOT NULL COMMENT '流程名称',
    process_type TINYINT NOT NULL COMMENT '流程类型',
    status TINYINT DEFAULT 0 COMMENT '流程状态',
    description VARCHAR(255) COMMENT '流程描述',
    business_id BIGINT COMMENT '业务ID',
    business_type VARCHAR(50) COMMENT '业务类型',
    initiator_id BIGINT COMMENT '发起人ID',
    initiator_name VARCHAR(100) COMMENT '发起人名称',
    start_time DATETIME COMMENT '发起时间',
    end_time DATETIME COMMENT '结束时间',
    current_handler_id BIGINT COMMENT '当前处理人ID',
    current_handler_name VARCHAR(100) COMMENT '当前处理人名称',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    allow_revoke TINYINT DEFAULT 1 COMMENT '是否允许撤回(0-不允许,1-允许)',
    allow_transfer TINYINT DEFAULT 1 COMMENT '是否允许转办(0-不允许,1-允许)',
    process_config JSON COMMENT '流程配置(JSON格式)',
    process_instance_id VARCHAR(100) COMMENT 'Flowable流程实例ID',
    process_definition_id VARCHAR(100) COMMENT '流程定义ID',
    process_definition_key VARCHAR(100) COMMENT '流程定义Key',
    process_definition_name VARCHAR(100) COMMENT '流程定义名称',
    process_definition_version INT COMMENT '流程定义版本',
    deployment_id VARCHAR(100) COMMENT '部署ID',
    business_key VARCHAR(100) COMMENT '业务键',
    category VARCHAR(50) COMMENT '流程分类',
    tags VARCHAR(255) COMMENT '流程标签',
    template_id BIGINT COMMENT '流程模板ID',
    template_version VARCHAR(20) COMMENT '流程模板版本',
    variables JSON COMMENT '流程变量(JSON格式)',
    form_data JSON COMMENT '流程表单(JSON格式)',
    attachment_ids VARCHAR(255) COMMENT '流程附件IDs',
    notes TEXT COMMENT '流程备注',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_process_no (process_no),
    UNIQUE KEY uk_process_instance_id (process_instance_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_business_id_type (business_id, business_type),
    INDEX idx_initiator_id (initiator_id),
    INDEX idx_current_handler_id (current_handler_id),
    INDEX idx_status (status),
    INDEX idx_start_time (start_time),
    INDEX idx_template_id (template_id),
    
    CONSTRAINT fk_workflow_process_template FOREIGN KEY (template_id) REFERENCES workflow_process_template(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程实例表';

-- workflow_task表（流程任务表）
DROP TABLE IF EXISTS workflow_task;
CREATE TABLE workflow_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    task_no VARCHAR(50) NOT NULL COMMENT '任务编号',
    task_name VARCHAR(100) NOT NULL COMMENT '任务名称',
    task_type TINYINT NOT NULL COMMENT '任务类型',
    process_id BIGINT NOT NULL COMMENT '流程ID',
    process_no VARCHAR(50) NOT NULL COMMENT '流程实例编号',
    description VARCHAR(255) COMMENT '任务描述',
    handler_id BIGINT COMMENT '处理人ID',
    handler_name VARCHAR(100) COMMENT '处理人名称',
    handler_type TINYINT DEFAULT 1 COMMENT '处理人类型(1-用户,2-角色,3-部门)',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    due_time DATETIME COMMENT '截止时间',
    status TINYINT DEFAULT 0 COMMENT '任务状态(0-未开始,1-进行中,2-已完成,3-已取消)',
    result VARCHAR(100) COMMENT '任务结果',
    comment TEXT COMMENT '任务评论',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高)',
    prev_task_ids VARCHAR(255) COMMENT '前置任务IDs',
    next_task_ids VARCHAR(255) COMMENT '后续任务IDs',
    task_config JSON COMMENT '任务配置(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_task_no (task_no),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_process_id (process_id),
    INDEX idx_process_no (process_no),
    INDEX idx_handler_id (handler_id),
    INDEX idx_status (status),
    INDEX idx_start_time (start_time),
    INDEX idx_due_time (due_time),
    
    CONSTRAINT fk_workflow_task_process FOREIGN KEY (process_id) REFERENCES workflow_process(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程任务表';

-- workflow_permission表（流程权限表）
DROP TABLE IF EXISTS workflow_permission;
CREATE TABLE workflow_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程权限ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    process_definition_key VARCHAR(100) NOT NULL COMMENT '流程定义ID或流程定义键',
    permission_type TINYINT NOT NULL COMMENT '权限类型(1-流程定义权限,2-流程实例权限)',
    operation_type TINYINT NOT NULL COMMENT '操作类型(1-启动,2-查看,3-处理,4-取消,5-挂起,6-激活)',
    target_type TINYINT NOT NULL COMMENT '权限目标类型(1-角色,2-用户,3-部门)',
    target_id BIGINT NOT NULL COMMENT '权限目标ID(角色ID、用户ID或部门ID)',
    permission_policy TINYINT DEFAULT 1 COMMENT '权限策略(1-允许,2-拒绝)',
    sort_order INT DEFAULT 0 COMMENT '排序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_process_target (process_definition_key, permission_type, operation_type, target_type, target_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_target_id_type (target_id, target_type),
    INDEX idx_process_definition_key (process_definition_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程权限表';

-- workflow_history表（流程历史表）
DROP TABLE IF EXISTS workflow_history;
CREATE TABLE workflow_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程历史ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    process_id BIGINT NOT NULL COMMENT '流程ID',
    process_no VARCHAR(50) NOT NULL COMMENT '流程编号',
    task_id BIGINT COMMENT '任务ID',
    task_name VARCHAR(100) COMMENT '任务名称',
    handler_id BIGINT COMMENT '处理人ID',
    handler_name VARCHAR(100) COMMENT '处理人名称',
    action_type TINYINT NOT NULL COMMENT '操作类型(1-启动,2-处理,3-转办,4-撤回,5-取消)',
    action_time DATETIME NOT NULL COMMENT '操作时间',
    action_result VARCHAR(100) COMMENT '操作结果',
    comment TEXT COMMENT '操作意见',
    duration BIGINT COMMENT '处理时长(秒)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_process_id (process_id),
    INDEX idx_process_no (process_no),
    INDEX idx_task_id (task_id),
    INDEX idx_handler_id (handler_id),
    INDEX idx_action_time (action_time),
    
    CONSTRAINT fk_workflow_history_process FOREIGN KEY (process_id) REFERENCES workflow_process(id) ON DELETE CASCADE,
    CONSTRAINT fk_workflow_history_task FOREIGN KEY (task_id) REFERENCES workflow_task(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程历史表';

-- workflow_form表（流程表单表）
DROP TABLE IF EXISTS workflow_form;
CREATE TABLE workflow_form (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '流程表单ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    form_key VARCHAR(100) NOT NULL COMMENT '表单标识',
    form_name VARCHAR(100) NOT NULL COMMENT '表单名称',
    form_type TINYINT DEFAULT 1 COMMENT '表单类型(1-启动表单,2-任务表单)',
    process_definition_key VARCHAR(100) COMMENT '流程定义Key',
    task_definition_key VARCHAR(100) COMMENT '任务定义Key',
    form_config JSON COMMENT '表单配置(JSON格式)',
    form_fields JSON COMMENT '表单字段定义(JSON格式)',
    validation_rules JSON COMMENT '验证规则(JSON格式)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_form_key (form_key),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_process_definition_key (process_definition_key),
    INDEX idx_task_definition_key (task_definition_key),
    INDEX idx_form_type (form_type),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='流程表单表';

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1; 