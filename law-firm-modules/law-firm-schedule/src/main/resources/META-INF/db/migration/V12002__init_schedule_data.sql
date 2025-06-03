-- 日程管理模块基础数据
-- 版本: V12002
-- 模块: 日程管理模块 (V12000-V12999)
-- 创建时间: 2023-06-01
-- 说明: 日程管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 日程管理相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('日程类型', 'schedule_type', 1, 'system', NOW(), '日程类型字典'),
('日程优先级', 'schedule_priority', 1, 'system', NOW(), '日程优先级字典'),
('日程状态', 'schedule_status', 1, 'system', NOW(), '日程状态字典'),
('日程可见性', 'schedule_visibility', 1, 'system', NOW(), '日程可见性字典'),
('参与者状态', 'participant_status', 1, 'system', NOW(), '参与者响应状态字典'),
('提醒类型', 'reminder_type', 1, 'system', NOW(), '提醒类型字典'),
('提醒状态', 'reminder_status', 1, 'system', NOW(), '提醒状态字典'),
('重复类型', 'recurrence_type', 1, 'system', NOW(), '重复类型字典'),
('重复结束类型', 'recurrence_end_type', 1, 'system', NOW(), '重复结束类型字典'),
('会议室状态', 'meeting_room_status', 1, 'system', NOW(), '会议室状态字典'),
('预订状态', 'booking_status', 1, 'system', NOW(), '会议室预订状态字典'),
('设备类型', 'equipment_type', 1, 'system', NOW(), '会议室设备类型字典'),
('日历类型', 'calendar_type', 1, 'system', NOW(), '日历类型字典'),
('时区', 'timezone', 1, 'system', NOW(), '时区字典'),
('外部日历类型', 'external_calendar_type', 1, 'system', NOW(), '外部日历类型字典'),
('同步状态', 'sync_status', 1, 'system', NOW(), '同步状态字典');

-- ======================= 系统字典数据 =======================

-- 日程类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '会议', '1', 'schedule_type', '', 'primary', 'Y', 1, 'system', NOW(), '会议类型日程'),
(2, '任务', '2', 'schedule_type', '', 'success', 'N', 1, 'system', NOW(), '任务类型日程'),
(3, '约见', '3', 'schedule_type', '', 'info', 'N', 1, 'system', NOW(), '客户约见日程'),
(4, '法庭', '4', 'schedule_type', '', 'warning', 'N', 1, 'system', NOW(), '法庭出庭日程'),
(5, '事件', '5', 'schedule_type', '', 'dark', 'N', 1, 'system', NOW(), '一般事件日程'),
(6, '培训', '6', 'schedule_type', '', 'secondary', 'N', 1, 'system', NOW(), '培训学习日程'),
(7, '休假', '7', 'schedule_type', '', 'light', 'N', 1, 'system', NOW(), '休假类型日程');

-- 日程优先级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'schedule_priority', '', 'success', 'N', 1, 'system', NOW(), '低优先级'),
(2, '中', '2', 'schedule_priority', '', 'primary', 'Y', 1, 'system', NOW(), '中等优先级'),
(3, '高', '3', 'schedule_priority', '', 'warning', 'N', 1, 'system', NOW(), '高优先级'),
(4, '紧急', '4', 'schedule_priority', '', 'danger', 'N', 1, 'system', NOW(), '紧急优先级');

-- 日程状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'schedule_status', '', 'secondary', 'N', 1, 'system', NOW(), '草稿状态'),
(2, '计划中', '2', 'schedule_status', '', 'primary', 'Y', 1, 'system', NOW(), '计划中状态'),
(3, '进行中', '3', 'schedule_status', '', 'info', 'N', 1, 'system', NOW(), '进行中状态'),
(4, '已完成', '4', 'schedule_status', '', 'success', 'N', 1, 'system', NOW(), '已完成状态'),
(5, '已取消', '5', 'schedule_status', '', 'danger', 'N', 1, 'system', NOW(), '已取消状态');

-- 日程可见性
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '公开', '1', 'schedule_visibility', '', 'success', 'Y', 1, 'system', NOW(), '所有人可见'),
(2, '私密', '2', 'schedule_visibility', '', 'warning', 'N', 1, 'system', NOW(), '仅创建者可见'),
(3, '仅参与者', '3', 'schedule_visibility', '', 'info', 'N', 1, 'system', NOW(), '仅参与者可见');

-- 参与者状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待定', '1', 'participant_status', '', 'secondary', 'Y', 1, 'system', NOW(), '参与状态待定'),
(2, '接受', '2', 'participant_status', '', 'success', 'N', 1, 'system', NOW(), '接受参与'),
(3, '拒绝', '3', 'participant_status', '', 'danger', 'N', 1, 'system', NOW(), '拒绝参与'),
(4, '暂定', '4', 'participant_status', '', 'warning', 'N', 1, 'system', NOW(), '暂定参与');

-- 提醒类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '系统通知', '1', 'reminder_type', '', 'primary', 'Y', 1, 'system', NOW(), '系统内通知'),
(2, '邮件提醒', '2', 'reminder_type', '', 'success', 'N', 1, 'system', NOW(), '邮件提醒'),
(3, '短信提醒', '3', 'reminder_type', '', 'info', 'N', 1, 'system', NOW(), '短信提醒'),
(4, '微信推送', '4', 'reminder_type', '', 'warning', 'N', 1, 'system', NOW(), '微信推送提醒'),
(5, '桌面弹窗', '5', 'reminder_type', '', 'dark', 'N', 1, 'system', NOW(), '桌面弹窗提醒');

-- 提醒状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待提醒', '1', 'reminder_status', '', 'primary', 'Y', 1, 'system', NOW(), '等待提醒'),
(2, '已提醒', '2', 'reminder_status', '', 'success', 'N', 1, 'system', NOW(), '已经提醒'),
(3, '提醒失败', '3', 'reminder_status', '', 'danger', 'N', 1, 'system', NOW(), '提醒失败'),
(4, '已取消', '4', 'reminder_status', '', 'secondary', 'N', 1, 'system', NOW(), '已取消提醒');

-- 重复类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '每日', 'DAILY', 'recurrence_type', '', 'primary', 'N', 1, 'system', NOW(), '每日重复'),
(2, '每周', 'WEEKLY', 'recurrence_type', '', 'success', 'Y', 1, 'system', NOW(), '每周重复'),
(3, '每月', 'MONTHLY', 'recurrence_type', '', 'info', 'N', 1, 'system', NOW(), '每月重复'),
(4, '每年', 'YEARLY', 'recurrence_type', '', 'warning', 'N', 1, 'system', NOW(), '每年重复'),
(5, '自定义', 'CUSTOM', 'recurrence_type', '', 'dark', 'N', 1, 'system', NOW(), '自定义重复规则');

-- 重复结束类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '永不结束', 'NEVER', 'recurrence_end_type', '', 'primary', 'Y', 1, 'system', NOW(), '永远重复'),
(2, '指定次数', 'COUNT', 'recurrence_end_type', '', 'success', 'N', 1, 'system', NOW(), '重复指定次数'),
(3, '指定日期', 'DATE', 'recurrence_end_type', '', 'info', 'N', 1, 'system', NOW(), '重复到指定日期');

-- 会议室状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '可用', '1', 'meeting_room_status', '', 'success', 'Y', 1, 'system', NOW(), '会议室可用'),
(2, '维护中', '2', 'meeting_room_status', '', 'warning', 'N', 1, 'system', NOW(), '会议室维护中'),
(3, '停用', '3', 'meeting_room_status', '', 'danger', 'N', 1, 'system', NOW(), '会议室停用');

-- 预订状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待审批', '1', 'booking_status', '', 'primary', 'Y', 1, 'system', NOW(), '预订待审批'),
(2, '已批准', '2', 'booking_status', '', 'success', 'N', 1, 'system', NOW(), '预订已批准'),
(3, '已拒绝', '3', 'booking_status', '', 'danger', 'N', 1, 'system', NOW(), '预订已拒绝'),
(4, '已取消', '4', 'booking_status', '', 'secondary', 'N', 1, 'system', NOW(), '预订已取消'),
(5, '使用中', '5', 'booking_status', '', 'info', 'N', 1, 'system', NOW(), '正在使用'),
(6, '已完成', '6', 'booking_status', '', 'dark', 'N', 1, 'system', NOW(), '使用完成');

-- 设备类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '投影仪', '1', 'equipment_type', '', 'primary', 'N', 1, 'system', NOW(), '投影仪设备'),
(2, '音响系统', '2', 'equipment_type', '', 'success', 'N', 1, 'system', NOW(), '音响系统'),
(3, '视频会议', '3', 'equipment_type', '', 'info', 'N', 1, 'system', NOW(), '视频会议设备'),
(4, '白板', '4', 'equipment_type', '', 'warning', 'N', 1, 'system', NOW(), '白板'),
(5, '电子屏', '5', 'equipment_type', '', 'dark', 'N', 1, 'system', NOW(), '电子显示屏'),
(6, '网络设备', '6', 'equipment_type', '', 'secondary', 'N', 1, 'system', NOW(), '网络设备');

-- 日历类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '个人日历', '1', 'calendar_type', '', 'primary', 'Y', 1, 'system', NOW(), '个人日历'),
(2, '团队日历', '2', 'calendar_type', '', 'success', 'N', 1, 'system', NOW(), '团队共享日历'),
(3, '公共日历', '3', 'calendar_type', '', 'info', 'N', 1, 'system', NOW(), '公共日历'),
(4, '项目日历', '4', 'calendar_type', '', 'warning', 'N', 1, 'system', NOW(), '项目专用日历'),
(5, '假期日历', '5', 'calendar_type', '', 'dark', 'N', 1, 'system', NOW(), '假期日历');

-- 外部日历类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, 'Google日历', 'GOOGLE', 'external_calendar_type', '', 'primary', 'N', 1, 'system', NOW(), 'Google Calendar'),
(2, 'Outlook日历', 'OUTLOOK', 'external_calendar_type', '', 'success', 'N', 1, 'system', NOW(), 'Microsoft Outlook'),
(3, 'Apple日历', 'APPLE', 'external_calendar_type', '', 'info', 'N', 1, 'system', NOW(), 'Apple iCalendar'),
(4, '钉钉日历', 'DINGTALK', 'external_calendar_type', '', 'warning', 'N', 1, 'system', NOW(), '钉钉日历'),
(5, '企业微信', 'WEWORK', 'external_calendar_type', '', 'dark', 'N', 1, 'system', NOW(), '企业微信日历');

-- 同步状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '未同步', '1', 'sync_status', '', 'secondary', 'Y', 1, 'system', NOW(), '未同步状态'),
(2, '同步中', '2', 'sync_status', '', 'primary', 'N', 1, 'system', NOW(), '正在同步'),
(3, '同步成功', '3', 'sync_status', '', 'success', 'N', 1, 'system', NOW(), '同步成功'),
(4, '同步失败', '4', 'sync_status', '', 'danger', 'N', 1, 'system', NOW(), '同步失败');

-- ======================= 日历数据 =======================

-- 初始化系统默认日历
INSERT INTO schedule_calendar (tenant_id, calendar_name, calendar_code, calendar_type, owner_id, color, is_default, is_public, description, create_by, create_time, status) VALUES
(0, '系统日历', 'SYS_CALENDAR', 3, 1, '#4285F4', 1, 1, '系统默认日历，用于展示公共日程', 'system', NOW(), 1),
(0, '法庭日历', 'COURT_CALENDAR', 3, 1, '#DB4437', 0, 1, '法庭出庭相关日程', 'system', NOW(), 1),
(0, '会议日历', 'MEETING_CALENDAR', 3, 1, '#0F9D58', 0, 1, '公司内部会议安排', 'system', NOW(), 1),
(0, '客户日历', 'CLIENT_CALENDAR', 3, 1, '#F4B400', 0, 1, '客户会面预约', 'system', NOW(), 1),
(0, '假期日历', 'HOLIDAY_CALENDAR', 5, 1, '#FF6900', 0, 1, '法定节假日和公司假期', 'system', NOW(), 1);

-- ======================= 会议室数据 =======================

-- 初始化会议室数据
INSERT INTO schedule_meeting_room (tenant_id, room_name, room_code, room_location, capacity, floor, building, has_projector, has_video_conf, has_whiteboard, room_status, create_by, create_time, status) VALUES
(0, '大会议室', 'MEETING_ROOM_001', '总部办公区A栋3楼', 20, '3', 'A栋', 1, 1, 1, 1, 'system', NOW(), 1),
(0, '小会议室A', 'MEETING_ROOM_002', '总部办公区A栋3楼', 8, '3', 'A栋', 1, 0, 1, 1, 'system', NOW(), 1),
(0, '小会议室B', 'MEETING_ROOM_003', '总部办公区A栋3楼', 8, '3', 'A栋', 1, 0, 1, 1, 'system', NOW(), 1),
(0, '接待室', 'MEETING_ROOM_004', '总部办公区A栋1楼', 6, '1', 'A栋', 0, 0, 0, 1, 'system', NOW(), 1),
(0, '视频会议室', 'MEETING_ROOM_005', '总部办公区B栋5楼', 12, '5', 'B栋', 1, 1, 1, 1, 'system', NOW(), 1),
(0, '培训室', 'MEETING_ROOM_006', '总部办公区B栋4楼', 30, '4', 'B栋', 1, 1, 1, 1, 'system', NOW(), 1),
(0, '讨论室1', 'MEETING_ROOM_007', '总部办公区A栋2楼', 4, '2', 'A栋', 0, 0, 1, 1, 'system', NOW(), 1),
(0, '讨论室2', 'MEETING_ROOM_008', '总部办公区A栋2楼', 4, '2', 'A栋', 0, 0, 1, 1, 'system', NOW(), 1);

-- ======================= 会议室设备数据 =======================

-- 初始化会议室设备数据
INSERT INTO schedule_room_equipment (tenant_id, room_id, equipment_name, equipment_type, equipment_model, purchase_date, equipment_status, create_by, create_time, status) VALUES
(0, 1, '激光投影仪', 1, 'EPSON EB-2247U', '2023-01-15', 1, 'system', NOW(), 1),
(0, 1, '音响系统', 2, 'BOSE SoundTouch 30', '2023-01-15', 1, 'system', NOW(), 1),
(0, 1, '视频会议设备', 3, 'Cisco Webex Room Kit', '2023-01-15', 1, 'system', NOW(), 1),
(0, 1, '电子白板', 4, 'SMART Board 6065', '2023-01-15', 1, 'system', NOW(), 1),
(0, 2, '便携投影仪', 1, 'BENQ MS527', '2023-02-01', 1, 'system', NOW(), 1),
(0, 2, '白板', 4, '标准白板 120*90cm', '2023-02-01', 1, 'system', NOW(), 1),
(0, 5, '4K投影仪', 1, 'Sony VPL-VW295ES', '2023-01-20', 1, 'system', NOW(), 1),
(0, 5, '专业音响', 2, 'JBL Professional', '2023-01-20', 1, 'system', NOW(), 1),
(0, 5, '高清摄像头', 3, 'Logitech Rally', '2023-01-20', 1, 'system', NOW(), 1),
(0, 6, '培训投影仪', 1, 'EPSON EB-W49', '2023-03-01', 1, 'system', NOW(), 1),
(0, 6, '扩音设备', 2, '无线扩音器', '2023-03-01', 1, 'system', NOW(), 1);

-- 初始化完成提示
SELECT '日程管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'schedule_%' OR dict_type LIKE '%calendar%' OR dict_type LIKE '%reminder%' OR dict_type LIKE '%booking%' OR dict_type LIKE '%sync%';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'schedule_%' OR dict_type LIKE '%calendar%' OR dict_type LIKE '%reminder%' OR dict_type LIKE '%booking%' OR dict_type LIKE '%sync%';
SELECT CONCAT('已创建日历：', COUNT(*), '个') AS calendar_count FROM schedule_calendar WHERE tenant_id = 0;
SELECT CONCAT('已创建会议室：', COUNT(*), '个') AS room_count FROM schedule_meeting_room WHERE tenant_id = 0;
SELECT CONCAT('已创建设备：', COUNT(*), '个') AS equipment_count FROM schedule_room_equipment WHERE tenant_id = 0; 