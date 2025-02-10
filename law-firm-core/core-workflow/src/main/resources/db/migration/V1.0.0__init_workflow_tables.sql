-- 流程权限表
CREATE TABLE IF NOT EXISTS wf_process_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_key VARCHAR(64) NOT NULL COMMENT '流程定义Key',
    process_name VARCHAR(128) COMMENT '流程名称',
    category VARCHAR(64) COMMENT '流程分类',
    start_roles TEXT COMMENT '可发起的角色列表（JSON）',
    start_departments TEXT COMMENT '可发起的部门列表（JSON）',
    task_node_permissions TEXT COMMENT '任务节点权限列表（JSON）',
    admin_roles TEXT COMMENT '流程管理员角色列表（JSON）',
    enabled TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    tenant_id VARCHAR(64) COMMENT '租户ID',
    remark VARCHAR(512) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    UNIQUE KEY uk_process_key (process_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程权限表';

-- 业务流程关联表
CREATE TABLE IF NOT EXISTS wf_business_process (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    business_type VARCHAR(64) NOT NULL COMMENT '业务类型',
    business_id VARCHAR(64) NOT NULL COMMENT '业务ID',
    business_title VARCHAR(256) COMMENT '业务标题',
    process_instance_id VARCHAR(64) NOT NULL COMMENT '流程实例ID',
    process_definition_id VARCHAR(64) NOT NULL COMMENT '流程定义ID',
    process_definition_key VARCHAR(64) NOT NULL COMMENT '流程定义Key',
    start_user_id VARCHAR(64) COMMENT '流程发起人ID',
    start_time DATETIME COMMENT '流程发起时间',
    end_time DATETIME COMMENT '流程结束时间',
    process_status VARCHAR(32) NOT NULL COMMENT '流程状态',
    current_task_id VARCHAR(64) COMMENT '当前任务ID',
    current_task_name VARCHAR(128) COMMENT '当前任务名称',
    current_assignee VARCHAR(64) COMMENT '当前处理人',
    form_data TEXT COMMENT '业务表单数据（JSON）',
    process_variables TEXT COMMENT '流程变量（JSON）',
    tenant_id VARCHAR(64) COMMENT '租户ID',
    remark VARCHAR(512) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    UNIQUE KEY uk_business (business_type, business_id),
    UNIQUE KEY uk_process_instance (process_instance_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务流程关联表';

-- 流程表单定义表
CREATE TABLE IF NOT EXISTS wf_form_definition (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    form_key VARCHAR(64) NOT NULL COMMENT '表单标识',
    form_name VARCHAR(128) NOT NULL COMMENT '表单名称',
    form_type VARCHAR(32) NOT NULL COMMENT '表单类型',
    category VARCHAR(64) COMMENT '表单分类',
    content TEXT NOT NULL COMMENT '表单内容(JSON)',
    version INT NOT NULL DEFAULT 1 COMMENT '版本号',
    status VARCHAR(32) NOT NULL COMMENT '状态',
    tenant_id VARCHAR(64) COMMENT '租户ID',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    UNIQUE KEY uk_form_key_version (form_key, version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程表单定义表';

-- 流程模板表
CREATE TABLE IF NOT EXISTS wf_process_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_key VARCHAR(64) NOT NULL COMMENT '流程定义Key',
    process_name VARCHAR(128) NOT NULL COMMENT '流程名称',
    category VARCHAR(64) COMMENT '流程分类',
    deployment_id VARCHAR(64) COMMENT '部署ID',
    process_definition_id VARCHAR(64) COMMENT '流程定义ID',
    file_name VARCHAR(128) COMMENT '流程定义文件名',
    xml_content TEXT COMMENT '流程定义XML',
    form_definition TEXT COMMENT '表单定义（JSON）',
    node_definition TEXT COMMENT '节点定义（JSON）',
    version INT NOT NULL DEFAULT 1 COMMENT '版本号',
    latest TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否最新版本',
    tenant_id VARCHAR(64) COMMENT '租户ID',
    remark VARCHAR(512) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    UNIQUE KEY uk_process_version (process_key, version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程模板表';

-- 流程操作日志表
CREATE TABLE IF NOT EXISTS wf_process_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    process_instance_id VARCHAR(64) NOT NULL COMMENT '流程实例ID',
    task_id VARCHAR(64) COMMENT '任务ID',
    task_name VARCHAR(128) COMMENT '任务名称',
    operation_type VARCHAR(64) NOT NULL COMMENT '操作类型',
    operator_id VARCHAR(64) NOT NULL COMMENT '操作人ID',
    operation_time DATETIME NOT NULL COMMENT '操作时间',
    operation_result VARCHAR(32) NOT NULL COMMENT '操作结果',
    detail TEXT COMMENT '操作详情',
    tenant_id VARCHAR(64) COMMENT '租户ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_process_instance (process_instance_id),
    KEY idx_task (task_id),
    KEY idx_operator (operator_id),
    KEY idx_operation_time (operation_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程操作日志表'; 