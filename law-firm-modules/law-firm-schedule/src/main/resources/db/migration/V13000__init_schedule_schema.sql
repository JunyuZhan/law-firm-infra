-- 日程模块数据库表结构初始化脚本

-- 日程表
CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    title VARCHAR(200) NOT NULL COMMENT '日程标题',
    description TEXT COMMENT '日程描述',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    location VARCHAR(255) COMMENT '地点',
    schedule_type TINYINT DEFAULT 1 COMMENT '日程类型: 1-会议, 2-任务, 3-事件, 4-预约, 5-其他',
    priority TINYINT DEFAULT 3 COMMENT '优先级: 1-紧急, 2-高, 3-中, 4-低, 5-不重要',
    visibility TINYINT DEFAULT 1 COMMENT '可见性: 1-公开, 2-私有, 3-仅团队可见',
    is_all_day TINYINT DEFAULT 0 COMMENT '是否全天: 0-否, 1-是',
    recurrence_rule VARCHAR(500) COMMENT '重复规则',
    recurrence_id BIGINT COMMENT '重复源ID',
    creator_id BIGINT NOT NULL COMMENT '创建人ID',
    owner_id BIGINT NOT NULL COMMENT '所有人ID',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-取消, 1-正常, 2-完成',
    meeting_room_id BIGINT COMMENT '会议室ID',
    external_id VARCHAR(100) COMMENT '外部系统ID',
    external_calendar VARCHAR(50) COMMENT '外部日历名称',
    color VARCHAR(20) COMMENT '日程颜色',
    tenant_id BIGINT COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程表';

-- 日程参与者表
CREATE TABLE IF NOT EXISTS schedule_participant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    participant_id BIGINT NOT NULL COMMENT '参与者ID',
    participant_type TINYINT DEFAULT 1 COMMENT '参与者类型: 1-用户, 2-部门, 3-角色, 4-团队',
    participant_role TINYINT DEFAULT 1 COMMENT '参与者角色: 1-参与者, 2-组织者, 3-可选参与者, 4-资源',
    response_status TINYINT DEFAULT 0 COMMENT '响应状态: 0-未响应, 1-接受, 2-拒绝, 3-待定',
    comments VARCHAR(500) COMMENT '备注',
    send_notification TINYINT DEFAULT 1 COMMENT '是否发送通知: 0-否, 1-是',
    tenant_id BIGINT COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_participant (participant_id, participant_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程参与者表';

-- 日程提醒表
CREATE TABLE IF NOT EXISTS schedule_reminder (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    reminder_type TINYINT DEFAULT 1 COMMENT '提醒类型: 1-系统, 2-邮件, 3-短信',
    reminder_time DATETIME NOT NULL COMMENT '提醒时间',
    remind_before INT DEFAULT 15 COMMENT '提前提醒分钟数',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待提醒, 1-已提醒, 2-已忽略, 3-失败',
    retry_count TINYINT DEFAULT 0 COMMENT '重试次数',
    last_retry_time DATETIME COMMENT '最后重试时间',
    recipient_id BIGINT COMMENT '接收人ID',
    recipient_type TINYINT DEFAULT 1 COMMENT '接收人类型: 1-参与者, 2-创建者, 3-指定用户',
    message TEXT COMMENT '提醒内容',
    tenant_id BIGINT COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    INDEX idx_schedule_id (schedule_id),
    INDEX idx_reminder_time (reminder_time, status),
    INDEX idx_recipient (recipient_id, recipient_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程提醒表';

-- 日程与案件关联表
CREATE TABLE IF NOT EXISTS schedule_case_relation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    case_id BIGINT NOT NULL COMMENT '案件ID',
    description VARCHAR(255) COMMENT '关联描述',
    tenant_id BIGINT COMMENT '租户ID',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_schedule_case (schedule_id, case_id, deleted),
    INDEX idx_case_id (case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程与案件关联表';

-- 日程与任务关联表
CREATE TABLE IF NOT EXISTS schedule_task_relation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    schedule_id BIGINT NOT NULL COMMENT '日程ID',
    task_id BIGINT NOT NULL COMMENT '任务ID',
    description VARCHAR(255) COMMENT '关联描述',
    tenant_id BIGINT COMMENT '租户ID',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_schedule_task (schedule_id, task_id, deleted),
    INDEX idx_task_id (task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程与任务关联表';

-- 会议室表
CREATE TABLE IF NOT EXISTS meeting_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    room_name VARCHAR(100) NOT NULL COMMENT '会议室名称',
    location VARCHAR(255) COMMENT '位置',
    capacity INT DEFAULT 0 COMMENT '容量',
    facilities VARCHAR(500) COMMENT '设施',
    description TEXT COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-停用, 1-可用',
    department_id BIGINT COMMENT '所属部门ID',
    approval_required TINYINT DEFAULT 0 COMMENT '是否需要审批: 0-否, 1-是',
    approver_id BIGINT COMMENT '审批人ID',
    booking_notice INT DEFAULT 0 COMMENT '预订提前通知小时数',
    tenant_id BIGINT COMMENT '租户ID',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室表';

-- 会议室预订表
CREATE TABLE IF NOT EXISTS meeting_room_booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    meeting_room_id BIGINT NOT NULL COMMENT '会议室ID',
    schedule_id BIGINT COMMENT '关联日程ID',
    title VARCHAR(200) NOT NULL COMMENT '预订标题',
    description TEXT COMMENT '预订描述',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    booker_id BIGINT NOT NULL COMMENT '预订人ID',
    attendees_count INT DEFAULT 0 COMMENT '参与人数',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-取消, 1-待审批, 2-已审批, 3-已拒绝, 4-已完成',
    approver_id BIGINT COMMENT '审批人ID',
    approval_time DATETIME COMMENT '审批时间',
    approval_comments VARCHAR(500) COMMENT '审批备注',
    recurring_rule VARCHAR(200) COMMENT '重复规则',
    tenant_id BIGINT COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    INDEX idx_meeting_room (meeting_room_id),
    INDEX idx_schedule (schedule_id),
    INDEX idx_time (start_time, end_time, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室预订表';

-- 外部日历账号表
CREATE TABLE IF NOT EXISTS external_calendar_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    calendar_type VARCHAR(50) NOT NULL COMMENT '日历类型(Google, Outlook等)',
    access_token TEXT COMMENT '访问令牌',
    refresh_token TEXT COMMENT '刷新令牌',
    token_expires_at BIGINT COMMENT '令牌过期时间戳',
    calendar_id VARCHAR(255) COMMENT '外部日历ID',
    calendar_name VARCHAR(255) COMMENT '外部日历名称',
    sync_enabled TINYINT DEFAULT 1 COMMENT '是否启用同步: 0-禁用, 1-启用',
    last_sync_time DATETIME COMMENT '最后同步时间',
    error_message VARCHAR(500) COMMENT '最后错误信息',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_user_calendar (user_id, calendar_type, deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外部日历账号表';

-- 日历同步配置表
CREATE TABLE IF NOT EXISTS calendar_sync_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    calendar_type VARCHAR(50) NOT NULL COMMENT '日历类型',
    sync_direction TINYINT DEFAULT 3 COMMENT '同步方向: 1-导入, 2-导出, 3-双向',
    sync_interval INT DEFAULT 30 COMMENT '同步间隔(分钟)',
    auto_sync_enabled TINYINT DEFAULT 1 COMMENT '是否启用自动同步: 0-禁用, 1-启用',
    sync_all_events TINYINT DEFAULT 0 COMMENT '是否同步所有事件: 0-否, 1-是',
    schedule_types VARCHAR(100) COMMENT '同步的日程类型，逗号分隔',
    start_time_sync DATE COMMENT '同步开始日期',
    end_time_sync DATE COMMENT '同步结束日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_user_calendar_config (user_id, calendar_type, deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历同步配置表';

-- 日历同步历史记录表
CREATE TABLE IF NOT EXISTS calendar_sync_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    calendar_type VARCHAR(50) NOT NULL COMMENT '日历类型',
    sync_direction TINYINT COMMENT '同步方向: 1-导入, 2-导出, 3-双向',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    total_events INT DEFAULT 0 COMMENT '总事件数',
    added_events INT DEFAULT 0 COMMENT '新增事件数',
    updated_events INT DEFAULT 0 COMMENT '更新事件数',
    deleted_events INT DEFAULT 0 COMMENT '删除事件数',
    success TINYINT DEFAULT 1 COMMENT '是否成功: 0-失败, 1-成功',
    error_message VARCHAR(500) COMMENT '错误信息',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_calendar (user_id, calendar_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历同步历史记录表';