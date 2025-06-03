-- 日程管理模块表结构
-- 版本: V12001
-- 模块: 日程管理模块 (V12000-V12999)
-- 创建时间: 2023-06-01
-- 说明: 日程管理功能的完整表结构定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 日程基础管理表 =======================

-- schedule_schedule表（日程主表）
DROP TABLE IF EXISTS schedule_schedule;
CREATE TABLE schedule_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日程ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    schedule_number VARCHAR(50) NOT NULL COMMENT '日程编号',
    title VARCHAR(200) NOT NULL COMMENT '日程标题',
    description TEXT COMMENT '日程描述',
    schedule_type TINYINT DEFAULT 1 COMMENT '日程类型(1-会议,2-任务,3-约见,4-法庭,5-事件,6-培训,7-休假)',
    priority TINYINT DEFAULT 2 COMMENT '优先级(1-低,2-中,3-高,4-紧急)',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    timezone VARCHAR(50) DEFAULT 'Asia/Shanghai' COMMENT '时区',
    is_all_day TINYINT DEFAULT 0 COMMENT '是否全天(0-否,1-是)',
    location VARCHAR(500) COMMENT '地点',
    online_meeting_url VARCHAR(500) COMMENT '在线会议链接',
    calendar_id BIGINT COMMENT '所属日历ID',
    organizer_id BIGINT NOT NULL COMMENT '组织者ID',
    organizer_name VARCHAR(50) COMMENT '组织者姓名',
    schedule_status TINYINT DEFAULT 1 COMMENT '日程状态(1-草稿,2-计划中,3-进行中,4-已完成,5-已取消)',
    visibility TINYINT DEFAULT 1 COMMENT '可见性(1-公开,2-私密,3-仅参与者)',
    is_recurring TINYINT DEFAULT 0 COMMENT '是否重复(0-否,1-是)',
    recurrence_id BIGINT COMMENT '重复规则ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父日程ID(重复日程的单个实例)',
    is_exception TINYINT DEFAULT 0 COMMENT '是否例外实例(0-否,1-是)',
    meeting_room_id BIGINT COMMENT '会议室ID',
    cost DECIMAL(10,2) DEFAULT 0.00 COMMENT '成本费用',
    participant_count INT DEFAULT 0 COMMENT '参与人数',
    attachment_count INT DEFAULT 0 COMMENT '附件数量',
    reminder_count INT DEFAULT 0 COMMENT '提醒数量',
    case_id BIGINT COMMENT '关联案件ID',
    task_id BIGINT COMMENT '关联任务ID',
    client_id BIGINT COMMENT '关联客户ID',
    tags JSON COMMENT '标签信息(JSON格式)',
    custom_fields JSON COMMENT '自定义字段(JSON格式)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_schedule_number (tenant_id, schedule_number),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_title (title),
    INDEX idx_schedule_type (schedule_type),
    INDEX idx_priority (priority),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_organizer_id (organizer_id),
    INDEX idx_schedule_status (schedule_status),
    INDEX idx_calendar_id (calendar_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_case_id (case_id),
    INDEX idx_task_id (task_id),
    INDEX idx_client_id (client_id),
    INDEX idx_meeting_room_id (meeting_room_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程主表';

-- schedule_content表（日程内容详情表）
DROP TABLE IF EXISTS schedule_content;
CREATE TABLE schedule_content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内容ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型(1-主要内容,2-会议议程,3-准备事项,4-会议纪要)',
    content TEXT COMMENT '内容详情',
    content_html TEXT COMMENT 'HTML格式内容',
    agenda JSON COMMENT '议程安排(JSON格式)',
    preparation JSON COMMENT '准备事项(JSON格式)',
    meeting_minutes JSON COMMENT '会议纪要(JSON格式)',
    action_items JSON COMMENT '行动项(JSON格式)',
    decisions JSON COMMENT '决议事项(JSON格式)',
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
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_content_type (content_type),
    INDEX idx_sort_order (sort_order),
    INDEX idx_status (status),
    
    CONSTRAINT fk_schedule_content_schedule FOREIGN KEY (schedule_id) REFERENCES schedule_schedule(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程内容详情表'; 

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 