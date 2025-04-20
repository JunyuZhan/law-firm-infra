-- 日程安排模块数据初始化
-- 版本: V10002
-- 模块: schedule
-- 创建时间: 2023-10-01
-- 说明: 初始化日程安排模块基础数据

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 初始化会议室数据
INSERT INTO schedule_meeting_room (room_name, room_code, room_location, capacity, floor, building, has_projector, has_video_conf, has_whiteboard, status, deleted, create_time)
VALUES 
('大会议室', 'MEETING_ROOM_001', '总部办公区A栋', 20, '3', 'A栋', true, true, true, 0, 0, NOW()),
('小会议室A', 'MEETING_ROOM_002', '总部办公区A栋', 8, '3', 'A栋', true, false, true, 0, 0, NOW()),
('小会议室B', 'MEETING_ROOM_003', '总部办公区A栋', 8, '3', 'A栋', true, false, true, 0, 0, NOW()),
('接待室', 'MEETING_ROOM_004', '总部办公区A栋', 6, '1', 'A栋', false, false, false, 0, 0, NOW()),
('视频会议室', 'MEETING_ROOM_005', '总部办公区B栋', 12, '5', 'B栋', true, true, true, 0, 0, NOW());

-- 初始化系统默认日历
INSERT INTO schedule_calendar (calendar_name, calendar_code, owner_id, color, is_default, is_public, description, status, deleted, create_time)
VALUES 
('系统日历', 'SYS_CALENDAR', 1, '#4285F4', true, true, '系统默认日历，用于展示公共日程', 0, 0, NOW()),
('法庭日历', 'COURT_CALENDAR', 1, '#DB4437', true, true, '法庭出庭相关日程', 0, 0, NOW()),
('会议日历', 'MEETING_CALENDAR', 1, '#0F9D58', true, true, '公司内部会议安排', 0, 0, NOW()),
('接待日历', 'RECEPTION_CALENDAR', 1, '#F4B400', true, true, '客户接待安排', 0, 0, NOW());

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
