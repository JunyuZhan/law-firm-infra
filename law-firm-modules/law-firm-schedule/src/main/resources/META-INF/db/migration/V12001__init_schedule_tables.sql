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

-- schedule_calendar表（日历表）
DROP TABLE IF EXISTS schedule_calendar;
CREATE TABLE schedule_calendar (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日历ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    calendar_name VARCHAR(100) NOT NULL COMMENT '日历名称',
    calendar_code VARCHAR(50) NOT NULL COMMENT '日历代码',
    calendar_type TINYINT DEFAULT 1 COMMENT '日历类型(1-个人,2-团队,3-公共,4-外部,5-假期)',
    owner_id BIGINT COMMENT '所有者ID',
    color VARCHAR(7) DEFAULT '#4285F4' COMMENT '日历颜色',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认日历(0-否,1-是)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    description VARCHAR(500) COMMENT '日历描述',
    timezone VARCHAR(50) DEFAULT 'Asia/Shanghai' COMMENT '时区',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_calendar_code (tenant_id, calendar_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_calendar_name (calendar_name),
    INDEX idx_calendar_type (calendar_type),
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日历表';

-- schedule_meeting_room表（会议室表）
DROP TABLE IF EXISTS schedule_meeting_room;
CREATE TABLE schedule_meeting_room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '会议室ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    room_name VARCHAR(100) NOT NULL COMMENT '会议室名称',
    room_code VARCHAR(50) NOT NULL COMMENT '会议室编码',
    room_location VARCHAR(200) COMMENT '会议室位置',
    capacity INT DEFAULT 0 COMMENT '容纳人数',
    floor VARCHAR(20) COMMENT '楼层',
    building VARCHAR(50) COMMENT '建筑物',
    has_projector TINYINT DEFAULT 0 COMMENT '是否有投影仪(0-否,1-是)',
    has_video_conf TINYINT DEFAULT 0 COMMENT '是否有视频会议(0-否,1-是)',
    has_whiteboard TINYINT DEFAULT 0 COMMENT '是否有白板(0-否,1-是)',
    room_status TINYINT DEFAULT 1 COMMENT '会议室状态(0-维护中,1-可用,2-占用)',
    contact_person VARCHAR(50) COMMENT '联系人',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    booking_advance_hours INT DEFAULT 24 COMMENT '预订提前小时数',
    max_booking_hours INT DEFAULT 8 COMMENT '最大预订小时数',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_room_code (tenant_id, room_code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_room_name (room_name),
    INDEX idx_capacity (capacity),
    INDEX idx_room_status (room_status),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议室表';

-- schedule_room_equipment表（会议室设备表）
DROP TABLE IF EXISTS schedule_room_equipment;
CREATE TABLE schedule_room_equipment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '设备ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    room_id BIGINT NOT NULL COMMENT '会议室ID',
    equipment_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    equipment_type TINYINT DEFAULT 1 COMMENT '设备类型(1-投影仪,2-音响,3-话筒,4-白板,5-电脑,6-电视,7-其他)',
    equipment_model VARCHAR(100) COMMENT '设备型号',
    purchase_date DATE COMMENT '购买日期',
    equipment_status TINYINT DEFAULT 1 COMMENT '设备状态(0-故障,1-正常,2-维护中)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_room_id (room_id),
    INDEX idx_equipment_name (equipment_name),
    INDEX idx_equipment_type (equipment_type),
    INDEX idx_equipment_status (equipment_status),
    INDEX idx_status (status),
    
    CONSTRAINT fk_room_equipment_room FOREIGN KEY (room_id) REFERENCES schedule_meeting_room(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议室设备表'; 

-- schedule_meeting_room_booking表（会议室预订表）
DROP TABLE IF EXISTS schedule_meeting_room_booking;
CREATE TABLE schedule_meeting_room_booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预订ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    meeting_room_id BIGINT NOT NULL COMMENT '会议室ID',
    schedule_id BIGINT COMMENT '关联的日程ID',
    user_id BIGINT NOT NULL COMMENT '预订人ID',
    title VARCHAR(200) NOT NULL COMMENT '会议标题',
    description TEXT COMMENT '会议描述',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    booking_type TINYINT DEFAULT 1 COMMENT '预订类型(1-常规会议,2-培训,3-活动,4-其他)',
    booking_status TINYINT DEFAULT 1 COMMENT '预订状态(1-待审核,2-已确认,3-已取消,4-已完成)',
    attendees_count INT DEFAULT 0 COMMENT '参与人数',
    remarks VARCHAR(500) COMMENT '预订备注',
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
    INDEX idx_meeting_room_id (meeting_room_id),
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_user_id (user_id),
    INDEX idx_start_time (start_time),
    INDEX idx_end_time (end_time),
    INDEX idx_booking_status (booking_status),
    INDEX idx_status (status),
    
    CONSTRAINT fk_room_booking_room FOREIGN KEY (meeting_room_id) REFERENCES schedule_meeting_room(id) ON DELETE CASCADE,
    CONSTRAINT fk_room_booking_schedule FOREIGN KEY (schedule_id) REFERENCES schedule_schedule(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会议室预订表';

-- schedule_participant表（日程参与者表）
DROP TABLE IF EXISTS schedule_participant;
CREATE TABLE schedule_participant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '参与者ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户姓名',
    user_email VARCHAR(100) COMMENT '用户邮箱',
    participant_type TINYINT DEFAULT 1 COMMENT '参与者类型(1-必须参与,2-可选参与,3-仅通知)',
    response_status TINYINT DEFAULT 1 COMMENT '响应状态(1-待回复,2-已接受,3-已拒绝,4-待定)',
    response_time DATETIME COMMENT '响应时间',
    response_note VARCHAR(500) COMMENT '响应备注',
    is_organizer TINYINT DEFAULT 0 COMMENT '是否组织者(0-否,1-是)',
    notification_sent TINYINT DEFAULT 0 COMMENT '是否已发送通知(0-否,1-是)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_schedule_user (tenant_id, schedule_id, user_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_user_id (user_id),
    INDEX idx_participant_type (participant_type),
    INDEX idx_response_status (response_status),
    INDEX idx_status (status),
    
    CONSTRAINT fk_schedule_participant_schedule FOREIGN KEY (schedule_id) REFERENCES schedule_schedule(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程参与者表';

-- schedule_reminder表（日程提醒表）
DROP TABLE IF EXISTS schedule_reminder;
CREATE TABLE schedule_reminder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '提醒ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    reminder_type TINYINT DEFAULT 1 COMMENT '提醒类型(1-邮件,2-短信,3-系统通知,4-弹窗)',
    reminder_time DATETIME NOT NULL COMMENT '提醒时间',
    advance_minutes INT DEFAULT 15 COMMENT '提前分钟数',
    is_sent TINYINT DEFAULT 0 COMMENT '是否已发送(0-否,1-是)',
    send_time DATETIME COMMENT '发送时间',
    send_result VARCHAR(500) COMMENT '发送结果',
    retry_count INT DEFAULT 0 COMMENT '重试次数',
    max_retry INT DEFAULT 3 COMMENT '最大重试次数',
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
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_user_id (user_id),
    INDEX idx_reminder_time (reminder_time),
    INDEX idx_reminder_type (reminder_type),
    INDEX idx_is_sent (is_sent),
    INDEX idx_status (status),
    
    CONSTRAINT fk_schedule_reminder_schedule FOREIGN KEY (schedule_id) REFERENCES schedule_schedule(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程提醒表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 