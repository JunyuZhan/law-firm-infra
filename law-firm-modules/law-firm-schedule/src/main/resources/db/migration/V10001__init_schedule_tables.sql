-- 日程安排模块表结构初始化
-- 版本: V10001
-- 模块: schedule
-- 创建时间: 2023-10-01
-- 说明: 创建日程安排模块相关表结构，基于实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- schedule_schedule表（日程表）
CREATE TABLE IF NOT EXISTS schedule_schedule (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  title VARCHAR(200) NOT NULL COMMENT '日程标题',
  content TEXT COMMENT '日程内容/描述',
  start_time DATETIME NOT NULL COMMENT '开始时间',
  end_time DATETIME NOT NULL COMMENT '结束时间',
  all_day BOOLEAN DEFAULT FALSE COMMENT '是否全天事项',
  location VARCHAR(255) COMMENT '地点',
  type INTEGER DEFAULT 1 COMMENT '日程类型（1-会议、2-任务、3-约见、4-法庭出庭等）',
  priority INTEGER DEFAULT 2 COMMENT '优先级（1-低、2-中、3-高）',
  schedule_status INTEGER DEFAULT 0 COMMENT '状态（0-计划中、1-进行中、2-已完成、3-已取消）',
  owner_id BIGINT NOT NULL COMMENT '所有者用户ID',
  is_private BOOLEAN DEFAULT FALSE COMMENT '是否私密日程',
  meeting_room_id BIGINT COMMENT '会议室ID（如果是会议类型）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_owner_id (owner_id),
  KEY idx_start_time (start_time),
  KEY idx_end_time (end_time),
  KEY idx_type (type),
  KEY idx_status (schedule_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程表';

-- schedule_event表（日程事件表）
CREATE TABLE IF NOT EXISTS schedule_event (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  schedule_id BIGINT NOT NULL COMMENT '日程ID',
  event_type INTEGER NOT NULL COMMENT '事件类型（1-创建日程、2-更新日程、3-取消日程等）',
  operator_id BIGINT COMMENT '操作用户ID',
  operator_name VARCHAR(100) COMMENT '操作用户名称',
  content TEXT COMMENT '事件内容（JSON格式）',
  remarks VARCHAR(500) COMMENT '事件备注',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  notify_flag INTEGER DEFAULT 0 COMMENT '是否通知（0-不通知、1-通知）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_schedule_id (schedule_id),
  KEY idx_event_type (event_type),
  KEY idx_operator_id (operator_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程事件表';

-- schedule_calendar表（日程日历表）
CREATE TABLE IF NOT EXISTS schedule_calendar (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  calendar_name VARCHAR(100) NOT NULL COMMENT '日历名称',
  calendar_code VARCHAR(50) NOT NULL COMMENT '日历编码',
  owner_id BIGINT NOT NULL COMMENT '所有者ID',
  color VARCHAR(20) COMMENT '日历颜色',
  is_default BOOLEAN DEFAULT FALSE COMMENT '是否默认日历',
  is_public BOOLEAN DEFAULT FALSE COMMENT '是否公开',
  description VARCHAR(500) COMMENT '日历描述',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_calendar_code (calendar_code),
  KEY idx_owner_id (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程日历表';

-- schedule_external_calendar表（外部日历表）
CREATE TABLE IF NOT EXISTS schedule_external_calendar (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  user_id BIGINT NOT NULL COMMENT '用户ID',
  calendar_name VARCHAR(100) NOT NULL COMMENT '日历名称',
  calendar_type VARCHAR(20) NOT NULL COMMENT '日历类型（Google、Outlook、Apple等）',
  external_id VARCHAR(200) COMMENT '外部日历ID',
  access_token TEXT COMMENT '访问令牌',
  refresh_token TEXT COMMENT '刷新令牌',
  token_expires_at DATETIME COMMENT '令牌过期时间',
  last_sync_time DATETIME COMMENT '最后同步时间',
  is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
  sync_enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用同步',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_user_id (user_id),
  KEY idx_calendar_type (calendar_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外部日历表';

-- schedule_participant表（日程参与者表）
CREATE TABLE IF NOT EXISTS schedule_participant (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  schedule_id BIGINT NOT NULL COMMENT '日程ID',
  user_id BIGINT NOT NULL COMMENT '参与者ID',
  user_name VARCHAR(100) COMMENT '参与者名称',
  response_status INTEGER DEFAULT 0 COMMENT '响应状态（0-待定、1-接受、2-拒绝、3-暂定）',
  is_organizer BOOLEAN DEFAULT FALSE COMMENT '是否组织者',
  notes VARCHAR(500) COMMENT '备注',
  notify_flag INTEGER DEFAULT 1 COMMENT '是否接收通知（0-否、1-是）',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_schedule_user (schedule_id, user_id),
  KEY idx_user_id (user_id),
  KEY idx_response_status (response_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程参与者表';

-- schedule_task_relation表（日程任务关联表）
CREATE TABLE IF NOT EXISTS schedule_task_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  schedule_id BIGINT NOT NULL COMMENT '日程ID',
  task_id BIGINT NOT NULL COMMENT '任务ID',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_schedule_task (schedule_id, task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程任务关联表';

-- schedule_case_relation表（日程案件关联表）
CREATE TABLE IF NOT EXISTS schedule_case_relation (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  schedule_id BIGINT NOT NULL COMMENT '日程ID',
  case_id BIGINT NOT NULL COMMENT '案件ID',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_schedule_case (schedule_id, case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程案件关联表';

-- schedule_reminder表（日程提醒表）
CREATE TABLE IF NOT EXISTS schedule_reminder (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  schedule_id BIGINT NOT NULL COMMENT '日程ID',
  remind_time DATETIME NOT NULL COMMENT '提醒时间',
  remind_type INTEGER DEFAULT 1 COMMENT '提醒类型（1-系统提醒、2-邮件提醒、3-短信提醒等）',
  remind_before INTEGER NOT NULL COMMENT '提前提醒时间（分钟）',
  is_repeat BOOLEAN DEFAULT FALSE COMMENT '是否重复提醒',
  repeat_interval INTEGER COMMENT '重复间隔（分钟）',
  repeat_times INTEGER DEFAULT 1 COMMENT '重复次数',
  remind_status INTEGER DEFAULT 0 COMMENT '提醒状态（0-待提醒、1-已提醒、2-已取消）',
  user_id BIGINT NOT NULL COMMENT '接收提醒用户ID',
  notify_content TEXT COMMENT '提醒内容',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_schedule_id (schedule_id),
  KEY idx_remind_time (remind_time),
  KEY idx_user_id (user_id),
  KEY idx_remind_status (remind_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日程提醒表';

-- schedule_meeting_room表（会议室表）
CREATE TABLE IF NOT EXISTS schedule_meeting_room (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  room_name VARCHAR(100) NOT NULL COMMENT '会议室名称',
  room_code VARCHAR(50) NOT NULL COMMENT '会议室编号',
  room_location VARCHAR(255) COMMENT '会议室位置',
  capacity INTEGER DEFAULT 0 COMMENT '容纳人数',
  floor VARCHAR(20) COMMENT '所在楼层',
  building VARCHAR(50) COMMENT '所在楼栋',
  has_projector BOOLEAN DEFAULT FALSE COMMENT '是否有投影仪',
  has_video_conf BOOLEAN DEFAULT FALSE COMMENT '是否有视频会议设备',
  has_whiteboard BOOLEAN DEFAULT FALSE COMMENT '是否有白板',
  facilities TEXT COMMENT '设施设备（JSON格式）',
  approval_required BOOLEAN DEFAULT FALSE COMMENT '是否需要审批',
  manager_id BIGINT COMMENT '管理员ID',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_room_code (room_code),
  KEY idx_capacity (capacity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室表';

-- schedule_meeting_room_booking表（会议室预订表）
CREATE TABLE IF NOT EXISTS schedule_meeting_room_booking (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  meeting_room_id BIGINT NOT NULL COMMENT '会议室ID',
  schedule_id BIGINT COMMENT '关联日程ID',
  title VARCHAR(200) NOT NULL COMMENT '预订标题',
  booker_id BIGINT NOT NULL COMMENT '预订人ID',
  booker_name VARCHAR(100) COMMENT '预订人姓名',
  start_time DATETIME NOT NULL COMMENT '开始时间',
  end_time DATETIME NOT NULL COMMENT '结束时间',
  attendees INTEGER DEFAULT 0 COMMENT '参会人数',
  booking_status INTEGER DEFAULT 0 COMMENT '预订状态（0-待审批、1-已审批、2-已拒绝、3-已取消）',
  approval_time DATETIME COMMENT '审批时间',
  approver_id BIGINT COMMENT '审批人ID',
  approver_name VARCHAR(100) COMMENT '审批人姓名',
  reject_reason VARCHAR(500) COMMENT '拒绝原因',
  booking_purpose VARCHAR(500) COMMENT '预订用途',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  remark VARCHAR(255) COMMENT '备注',
  PRIMARY KEY (id),
  KEY idx_meeting_room_id (meeting_room_id),
  KEY idx_schedule_id (schedule_id),
  KEY idx_booker_id (booker_id),
  KEY idx_booking_status (booking_status),
  KEY idx_start_time (start_time),
  KEY idx_end_time (end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室预订表';

-- schedule_external_calendar_sync表（外部日历同步表）
CREATE TABLE IF NOT EXISTS schedule_external_calendar_sync (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  external_calendar_id BIGINT NOT NULL COMMENT '外部日历ID',
  schedule_id BIGINT COMMENT '本地日程ID',
  external_event_id VARCHAR(255) NOT NULL COMMENT '外部事件ID',
  sync_status INTEGER DEFAULT 0 COMMENT '同步状态（0-未同步、1-已同步、2-同步失败）',
  sync_time DATETIME COMMENT '同步时间',
  error_message VARCHAR(500) COMMENT '错误信息',
  sync_direction VARCHAR(10) DEFAULT 'BOTH' COMMENT '同步方向（IN、OUT、BOTH）',
  last_modified_external DATETIME COMMENT '外部最后修改时间',
  last_modified_local DATETIME COMMENT '本地最后修改时间',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_external_event_id (external_calendar_id, external_event_id),
  KEY idx_schedule_id (schedule_id),
  KEY idx_sync_status (sync_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外部日历同步表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
