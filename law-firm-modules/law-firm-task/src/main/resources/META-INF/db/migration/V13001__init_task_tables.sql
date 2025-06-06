-- 任务管理模块表结构
-- 版本: V13001
-- 模块: 任务管理模块 (V13000-V13999)
-- 创建时间: 2023-06-01
-- 说明: 任务管理功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 任务管理表 =======================

-- task_info表（任务基础信息表）
DROP TABLE IF EXISTS task_info;
CREATE TABLE task_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_number VARCHAR(50) NOT NULL COMMENT '任务编号',
    title VARCHAR(200) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    task_type TINYINT DEFAULT 1 COMMENT '任务类型(1-法律事务,2-行政事务,3-客户服务,4-财务管理,5-日常事务)',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    task_status TINYINT DEFAULT 1 COMMENT '任务状态(1-草稿,2-待分配,3-进行中,4-已完成,5-已暂停,6-已取消)',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    due_date DATETIME COMMENT '截止日期',
    complete_time DATETIME COMMENT '完成时间',
    creator_id BIGINT NOT NULL COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    assignee_id BIGINT COMMENT '负责人ID',
    assignee_name VARCHAR(50) COMMENT '负责人姓名',
    category_id BIGINT COMMENT '分类ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父任务ID',
    level TINYINT DEFAULT 1 COMMENT '层级',
    path VARCHAR(500) COMMENT '层级路径',
    case_id BIGINT COMMENT '关联案件ID',
    client_id BIGINT COMMENT '关联客户ID',
    contract_id BIGINT COMMENT '关联合同ID',
    schedule_id BIGINT COMMENT '关联日程ID',
    project_id BIGINT COMMENT '关联项目ID',
    template_id BIGINT COMMENT '来源模板ID',
    is_template TINYINT DEFAULT 0 COMMENT '是否模板(0-否,1-是)',
    is_recurring TINYINT DEFAULT 0 COMMENT '是否重复任务(0-否,1-是)',
    recurrence_rule JSON COMMENT '重复规则(JSON格式)',
    estimated_hours DECIMAL(8,2) DEFAULT 0 COMMENT '预估工时(小时)',
    actual_hours DECIMAL(8,2) DEFAULT 0 COMMENT '实际工时(小时)',
    progress_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '完成进度(0-100)',
    difficulty_level TINYINT DEFAULT 2 COMMENT '难度等级(1-简单,2-中等,3-困难,4-专家)',
    importance_level TINYINT DEFAULT 2 COMMENT '重要程度(1-低,2-中,3-高,4-关键)',
    urgency_level TINYINT DEFAULT 2 COMMENT '紧急程度(1-低,2-中,3-高,4-紧急)',
    participant_count INT DEFAULT 0 COMMENT '参与人数',
    attachment_count INT DEFAULT 0 COMMENT '附件数量',
    comment_count INT DEFAULT 0 COMMENT '评论数量',
    like_count INT DEFAULT 0 COMMENT '点赞数量',
    follow_count INT DEFAULT 0 COMMENT '关注数量',
    view_count INT DEFAULT 0 COMMENT '查看次数',
    cost DECIMAL(12,2) DEFAULT 0.00 COMMENT '任务成本',
    tags JSON COMMENT '标签信息(JSON格式)',
    dependencies JSON COMMENT '依赖任务(JSON格式)',
    milestones JSON COMMENT '里程碑(JSON格式)',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    completion_note TEXT COMMENT '完成说明',
    cancel_reason VARCHAR(500) COMMENT '取消原因',
    pause_reason VARCHAR(500) COMMENT '暂停原因',
    quality_score DECIMAL(3,1) DEFAULT 0 COMMENT '质量评分(0-10)',
    satisfaction_score DECIMAL(3,1) DEFAULT 0 COMMENT '满意度评分(0-10)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_task_number (tenant_id, task_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_title (title),
    INDEX idx_task_type (task_type),
    INDEX idx_priority (priority),
    INDEX idx_task_status (task_status),
    INDEX idx_creator_id (creator_id),
    INDEX idx_assignee_id (assignee_id),
    INDEX idx_category_id (category_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_case_id (case_id),
    INDEX idx_client_id (client_id),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_due_date (due_date),
    INDEX idx_complete_time (complete_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务主表';

-- task_content表（任务内容详情表）
DROP TABLE IF EXISTS task_content;
CREATE TABLE task_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内容ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-主要内容,2-需求说明,3-验收标准,4-操作指南)',
    content TEXT COMMENT '内容详情',
    content_html TEXT COMMENT 'HTML格式内容',
    requirements JSON COMMENT '需求说明(JSON格式)',
    acceptance_criteria JSON COMMENT '验收标准(JSON格式)',
    operation_guide JSON COMMENT '操作指南(JSON格式)',
    resources JSON COMMENT '资源信息(JSON格式)',
    `references` JSON COMMENT '参考资料(JSON格式)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_content_type (content_type),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_content_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务内容详情表';

-- task_attachment表（任务附件表）
DROP TABLE IF EXISTS task_attachment;
CREATE TABLE task_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '附件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_size BIGINT DEFAULT 0 COMMENT '文件大小(字节)',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_ext VARCHAR(20) COMMENT '文件扩展名',
    storage_type TINYINT DEFAULT 1 COMMENT '存储类型(1-本地,2-云存储,3-第三方)',
    storage_path VARCHAR(500) COMMENT '存储路径',
    uploader_id BIGINT NOT NULL COMMENT '上传者ID',
    uploader_name VARCHAR(50) COMMENT '上传者姓名',
    upload_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    last_download_time DATETIME COMMENT '最后下载时间',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    attachment_type TINYINT DEFAULT 1 COMMENT '附件类型(1-需求文档,2-设计图,3-参考资料,4-其他)',
    description VARCHAR(500) COMMENT '附件描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_uploader_id (uploader_id),
    INDEX idx_file_type (file_type),
    INDEX idx_attachment_type (attachment_type),
    INDEX idx_upload_time (upload_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_attachment_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务附件表';

-- ======================= 分类标签管理表 =======================

-- task_category表（任务分类表）
DROP TABLE IF EXISTS task_category;
CREATE TABLE task_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    category_name VARCHAR(100) NOT NULL COMMENT '分类名称',
    category_code VARCHAR(50) NOT NULL COMMENT '分类编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level TINYINT DEFAULT 1 COMMENT '分类层级',
    path VARCHAR(500) COMMENT '分类路径',
    icon VARCHAR(100) COMMENT '图标',
    color VARCHAR(20) COMMENT '颜色',
    description VARCHAR(500) COMMENT '分类描述',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统内置(0-否,1-是)',
    is_leaf TINYINT DEFAULT 1 COMMENT '是否叶子节点(0-否,1-是)',
    task_count INT DEFAULT 0 COMMENT '任务数量',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_category_code (tenant_id, category_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务分类表';

-- task_tag表（任务标签表）
DROP TABLE IF EXISTS task_tag;
CREATE TABLE task_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_code VARCHAR(50) COMMENT '标签编码',
    tag_color VARCHAR(20) DEFAULT '#007bff' COMMENT '标签颜色',
    tag_type TINYINT DEFAULT 1 COMMENT '标签类型(1-优先级,2-业务,3-状态,4-属性)',
    tag_group VARCHAR(50) COMMENT '标签分组',
    description VARCHAR(200) COMMENT '标签描述',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门(0-否,1-是)',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统内置(0-否,1-是)',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_tag_name (tenant_id, tag_name),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_tag_type (tag_type),
    INDEX idx_tag_group (tag_group),
    INDEX idx_usage_count (usage_count),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务标签表';

-- task_tag_relation表（任务标签关联表）
DROP TABLE IF EXISTS task_tag_relation;
CREATE TABLE task_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    tag_weight DECIMAL(3,2) DEFAULT 1.00 COMMENT '标签权重',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-手动添加,2-自动识别,3-批量导入)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    
    UNIQUE KEY uk_task_tag (task_id, tag_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_tag_id (tag_id),
    INDEX idx_relation_type (relation_type),
    
    CONSTRAINT fk_task_tag_relation_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_tag_relation_tag FOREIGN KEY (tag_id) REFERENCES task_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务标签关联表';

-- ======================= 协作管理表 =======================

-- task_participant表（任务参与者表）
DROP TABLE IF EXISTS task_participant;
CREATE TABLE task_participant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '参与者ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    participant_role TINYINT DEFAULT 1 COMMENT '参与角色(1-创建者,2-负责人,3-参与者,4-观察者)',
    join_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    join_type TINYINT DEFAULT 1 COMMENT '加入方式(1-邀请,2-申请,3-指派)',
    permission_level TINYINT DEFAULT 1 COMMENT '权限级别(1-只读,2-编辑,3-管理)',
    contribution_score DECIMAL(5,2) DEFAULT 0 COMMENT '贡献评分',
    work_hours DECIMAL(8,2) DEFAULT 0 COMMENT '工作时长',
    is_active TINYINT DEFAULT 1 COMMENT '是否活跃(0-否,1-是)',
    last_active_time DATETIME COMMENT '最后活跃时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_task_user (task_id, user_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_user_id (user_id),
    INDEX idx_participant_role (participant_role),
    INDEX idx_join_time (join_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_participant_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务参与者表';

-- ======================= 进度管理表 =======================

-- task_progress表（任务进度记录表）
DROP TABLE IF EXISTS task_progress;
CREATE TABLE task_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '进度ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    progress_percentage DECIMAL(5,2) NOT NULL COMMENT '进度百分比(0-100)',
    progress_description TEXT COMMENT '进度描述',
    milestone_name VARCHAR(100) COMMENT '里程碑名称',
    is_milestone TINYINT DEFAULT 0 COMMENT '是否里程碑(0-否,1-是)',
    work_hours DECIMAL(8,2) DEFAULT 0 COMMENT '本次工时',
    total_hours DECIMAL(8,2) DEFAULT 0 COMMENT '累计工时',
    remaining_hours DECIMAL(8,2) DEFAULT 0 COMMENT '剩余工时',
    quality_score DECIMAL(3,1) DEFAULT 0 COMMENT '质量评分(0-10)',
    completion_evidence TEXT COMMENT '完成证据',
    next_steps TEXT COMMENT '下一步计划',
    obstacles TEXT COMMENT '遇到的障碍',
    reporter_id BIGINT NOT NULL COMMENT '汇报人ID',
    reporter_name VARCHAR(50) COMMENT '汇报人姓名',
    report_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '汇报时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_progress_percentage (progress_percentage),
    INDEX idx_reporter_id (reporter_id),
    INDEX idx_report_time (report_time),
    INDEX idx_is_milestone (is_milestone),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_progress_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务进度记录表';

-- ======================= 互动管理表 =======================

-- task_comment表（任务评论表）
DROP TABLE IF EXISTS task_comment;
CREATE TABLE task_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    content TEXT NOT NULL COMMENT '评论内容',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-文本,2-语音,3-图片)',
    commenter_id BIGINT NOT NULL COMMENT '评论者ID',
    commenter_name VARCHAR(50) COMMENT '评论者姓名',
    commenter_avatar VARCHAR(200) COMMENT '评论者头像',
    reply_to_id BIGINT COMMENT '回复目标ID',
    reply_to_name VARCHAR(50) COMMENT '回复目标姓名',
    mention_users JSON COMMENT '提及用户(JSON格式)',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    is_sticky TINYINT DEFAULT 0 COMMENT '是否置顶(0-否,1-是)',
    is_private TINYINT DEFAULT 0 COMMENT '是否私密(0-否,1-是)',
    ip_address VARCHAR(50) COMMENT 'IP地址',
    user_agent VARCHAR(500) COMMENT '用户代理',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_commenter_id (commenter_id),
    INDEX idx_reply_to_id (reply_to_id),
    INDEX idx_create_time (create_time),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_comment_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务评论表';

-- ======================= 时间管理表 =======================

-- task_time_log表（任务工时记录表）
DROP TABLE IF EXISTS task_time_log;
CREATE TABLE task_time_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '工时ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    duration DECIMAL(8,2) DEFAULT 0 COMMENT '工作时长(小时)',
    work_description TEXT COMMENT '工作描述',
    work_type TINYINT DEFAULT 1 COMMENT '工作类型(1-开发,2-测试,3-文档,4-会议,5-其他)',
    is_billable TINYINT DEFAULT 1 COMMENT '是否计费(0-否,1-是)',
    hourly_rate DECIMAL(10,2) DEFAULT 0 COMMENT '小时费率',
    total_cost DECIMAL(12,2) DEFAULT 0 COMMENT '总成本',
    productivity_score DECIMAL(3,1) DEFAULT 0 COMMENT '生产力评分(0-10)',
    log_type TINYINT DEFAULT 1 COMMENT '记录类型(1-手动,2-自动,3-导入)',
    device_info VARCHAR(200) COMMENT '设备信息',
    location_info VARCHAR(200) COMMENT '位置信息',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_user_id (user_id),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_work_type (work_type),
    INDEX idx_log_type (log_type),
    INDEX idx_status (status),
    
    CONSTRAINT fk_task_time_log_task FOREIGN KEY (task_id) REFERENCES task_info(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务工时记录表';

-- ======================= 模板管理表 =======================

-- task_template表（任务模板表）
DROP TABLE IF EXISTS task_template;
CREATE TABLE task_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '模板ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    template_name VARCHAR(100) NOT NULL COMMENT '模板名称',
    template_code VARCHAR(50) COMMENT '模板编码',
    template_type TINYINT DEFAULT 1 COMMENT '模板类型(1-任务模板,2-流程模板,3-检查清单)',
    category_id BIGINT COMMENT '分类ID',
    title_template VARCHAR(200) COMMENT '标题模板',
    description_template TEXT COMMENT '描述模板',
    estimated_hours DECIMAL(8,2) DEFAULT 0 COMMENT '预估工时',
    priority TINYINT DEFAULT 2 COMMENT '默认优先级',
    difficulty_level TINYINT DEFAULT 2 COMMENT '难度等级',
    checklist JSON COMMENT '检查清单(JSON格式)',
    resources JSON COMMENT '资源配置(JSON格式)',
    participants JSON COMMENT '参与者配置(JSON格式)',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    success_rate DECIMAL(5,2) DEFAULT 0 COMMENT '成功率',
    avg_completion_time DECIMAL(8,2) DEFAULT 0 COMMENT '平均完成时间(小时)',
    creator_id BIGINT NOT NULL COMMENT '创建者ID',
    creator_name VARCHAR(50) COMMENT '创建者姓名',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    is_recommended TINYINT DEFAULT 0 COMMENT '是否推荐(0-否,1-是)',
    rating DECIMAL(3,1) DEFAULT 0 COMMENT '评分(0-10)',
    rating_count INT DEFAULT 0 COMMENT '评分人数',
    sort_order INT DEFAULT 0 COMMENT '排序序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_template_code (tenant_id, template_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_template_type (template_type),
    INDEX idx_category_id (category_id),
    INDEX idx_creator_id (creator_id),
    INDEX idx_usage_count (usage_count),
    INDEX idx_rating (rating),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务模板表';

-- ======================= 统计分析表 =======================

-- task_statistics表（任务统计表）
DROP TABLE IF EXISTS task_statistics;
CREATE TABLE task_statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '统计ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    stat_date DATE NOT NULL COMMENT '统计日期',
    stat_type TINYINT DEFAULT 1 COMMENT '统计类型(1-日统计,2-周统计,3-月统计,4-年统计)',
    user_id BIGINT COMMENT '用户ID(null表示全局统计)',
    department_id BIGINT COMMENT '部门ID',
    category_id BIGINT COMMENT '分类ID',
    total_tasks INT DEFAULT 0 COMMENT '总任务数',
    created_tasks INT DEFAULT 0 COMMENT '新建任务数',
    completed_tasks INT DEFAULT 0 COMMENT '完成任务数',
    cancelled_tasks INT DEFAULT 0 COMMENT '取消任务数',
    overdue_tasks INT DEFAULT 0 COMMENT '逾期任务数',
    in_progress_tasks INT DEFAULT 0 COMMENT '进行中任务数',
    completion_rate DECIMAL(5,2) DEFAULT 0 COMMENT '完成率',
    overdue_rate DECIMAL(5,2) DEFAULT 0 COMMENT '逾期率',
    avg_completion_time DECIMAL(8,2) DEFAULT 0 COMMENT '平均完成时间(小时)',
    total_work_hours DECIMAL(12,2) DEFAULT 0 COMMENT '总工作时长',
    avg_quality_score DECIMAL(3,1) DEFAULT 0 COMMENT '平均质量评分',
    total_cost DECIMAL(15,2) DEFAULT 0 COMMENT '总成本',
    efficiency_score DECIMAL(5,2) DEFAULT 0 COMMENT '效率评分',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_stat_date_type_user (stat_date, stat_type, user_id, department_id, category_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_stat_date (stat_date),
    INDEX idx_stat_type (stat_type),
    INDEX idx_user_id (user_id),
    INDEX idx_department_id (department_id),
    INDEX idx_category_id (category_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务统计表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 