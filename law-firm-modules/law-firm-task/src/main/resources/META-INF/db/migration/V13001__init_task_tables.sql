-- Task模块表结构初始化
-- 版本: V13001
-- 模块: task
-- 创建时间: 2023-06-10
-- 说明: 任务管理模块表结构，统一规范和约束

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 任务管理核心表 =======================

-- work_task表（工作任务表）
DROP TABLE IF EXISTS work_task;
CREATE TABLE work_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
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
    parent_id BIGINT DEFAULT 0 COMMENT '父任务ID',
    case_id BIGINT COMMENT '关联案件ID',
    client_id BIGINT COMMENT '关联客户ID',
    schedule_id BIGINT COMMENT '关联日程ID',
    is_legal_task TINYINT DEFAULT 0 COMMENT '是否法律专业任务(0-否,1-是)',
    document_ids TEXT COMMENT '关联文档ID列表(JSON数组)',
    department_id BIGINT COMMENT '所属部门ID',
    estimated_hours DECIMAL(8,2) DEFAULT 0 COMMENT '预估工时(小时)',
    actual_hours DECIMAL(8,2) DEFAULT 0 COMMENT '实际工时(小时)',
    progress_percentage DECIMAL(5,2) DEFAULT 0 COMMENT '完成进度(0-100)',
    cancel_reason VARCHAR(500) COMMENT '取消原因',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
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
    INDEX idx_parent_id (parent_id),
    INDEX idx_case_id (case_id),
    INDEX idx_client_id (client_id),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_due_date (due_date),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务表';

-- work_task_attachment表（任务附件表）
DROP TABLE IF EXISTS work_task_attachment;
CREATE TABLE work_task_attachment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '附件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
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
    sort INT DEFAULT 0 COMMENT '排序号',
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
    
    CONSTRAINT fk_work_task_attachment_task FOREIGN KEY (task_id) REFERENCES work_task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务附件表';

-- work_task_category表（任务分类表）
DROP TABLE IF EXISTS work_task_category;
CREATE TABLE work_task_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
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
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
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
    INDEX idx_status (status),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务分类表';

-- work_task_tag表（任务标签表）
DROP TABLE IF EXISTS work_task_tag;
CREATE TABLE work_task_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_code VARCHAR(50) COMMENT '标签编码',
    tag_color VARCHAR(20) DEFAULT '#007bff' COMMENT '标签颜色',
    tag_type TINYINT DEFAULT 1 COMMENT '标签类型(1-优先级,2-业务,3-状态,4-属性)',
    tag_group VARCHAR(50) COMMENT '标签分组',
    description VARCHAR(200) COMMENT '标签描述',
    usage_count INT DEFAULT 0 COMMENT '使用次数',
    is_hot TINYINT DEFAULT 0 COMMENT '是否热门(0-否,1-是)',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统内置(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
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
    INDEX idx_status (status),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务标签表';

-- work_task_tag_relation表（任务标签关联表）
DROP TABLE IF EXISTS work_task_tag_relation;
CREATE TABLE work_task_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    tag_weight DECIMAL(3,2) DEFAULT 1.00 COMMENT '标签权重',
    relation_type TINYINT DEFAULT 1 COMMENT '关联类型(1-手动添加,2-自动识别,3-批量导入)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_task_tag (tenant_id, task_id, tag_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_task_id (task_id),
    INDEX idx_tag_id (tag_id),
    INDEX idx_relation_type (relation_type),
    INDEX idx_status (status),
    
    CONSTRAINT fk_work_task_tag_relation_task FOREIGN KEY (task_id) REFERENCES work_task(id) ON DELETE CASCADE,
    CONSTRAINT fk_work_task_tag_relation_tag FOREIGN KEY (tag_id) REFERENCES work_task_tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务标签关联表';

-- work_task_comment表（任务评论表）
DROP TABLE IF EXISTS work_task_comment;
CREATE TABLE work_task_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    comment_content TEXT NOT NULL COMMENT '评论内容',
    comment_type TINYINT DEFAULT 1 COMMENT '评论类型(1-普通评论,2-进度更新,3-问题反馈,4-建议意见)',
    commenter_id BIGINT NOT NULL COMMENT '评论者ID',
    commenter_name VARCHAR(50) COMMENT '评论者姓名',
    commenter_avatar VARCHAR(200) COMMENT '评论者头像',
    is_anonymous TINYINT DEFAULT 0 COMMENT '是否匿名(0-否,1-是)',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    attachment_urls JSON COMMENT '附件链接(JSON格式)',
    mentioned_users JSON COMMENT '提及的用户(JSON格式)',
    is_private TINYINT DEFAULT 0 COMMENT '是否私密评论(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
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
    INDEX idx_comment_type (comment_type),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time),
    
    CONSTRAINT fk_work_task_comment_task FOREIGN KEY (task_id) REFERENCES work_task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作任务评论表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;