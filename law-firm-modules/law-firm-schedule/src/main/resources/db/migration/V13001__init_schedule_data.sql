-- 日程模块初始化数据脚本

-- 插入日程类型字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('日程类型', 'schedule_type', '日程的类型分类', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入日程类型字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '会议', '1', '各类会议日程', 1, NOW(), NOW(), 0),
(@dict_id, '任务', '2', '需要完成的任务', 2, NOW(), NOW(), 0),
(@dict_id, '事件', '3', '各类事件和活动', 3, NOW(), NOW(), 0),
(@dict_id, '预约', '4', '客户或其他预约', 4, NOW(), NOW(), 0),
(@dict_id, '休假', '5', '休假和请假', 5, NOW(), NOW(), 0),
(@dict_id, '出差', '6', '外出公干和出差', 6, NOW(), NOW(), 0),
(@dict_id, '提醒', '7', '一般性提醒事项', 7, NOW(), NOW(), 0),
(@dict_id, '其他', '99', '其他类型日程', 99, NOW(), NOW(), 0);

-- 插入日程优先级字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('日程优先级', 'schedule_priority', '日程的优先级分类', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入日程优先级字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '紧急', '1', '紧急且重要的日程', 1, NOW(), NOW(), 0),
(@dict_id, '高', '2', '高优先级日程', 2, NOW(), NOW(), 0),
(@dict_id, '中', '3', '一般优先级日程', 3, NOW(), NOW(), 0),
(@dict_id, '低', '4', '低优先级日程', 4, NOW(), NOW(), 0),
(@dict_id, '不重要', '5', '可选或不重要的日程', 5, NOW(), NOW(), 0);

-- 插入日程提醒类型字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('提醒类型', 'reminder_type', '日程提醒的方式', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入日程提醒类型字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '系统提醒', '1', '系统内部通知提醒', 1, NOW(), NOW(), 0),
(@dict_id, '邮件提醒', '2', '通过邮件发送提醒', 2, NOW(), NOW(), 0),
(@dict_id, '短信提醒', '3', '通过短信发送提醒', 3, NOW(), NOW(), 0);

-- 插入提醒提前时间字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('提醒提前时间', 'remind_before', '日程提醒的提前时间(分钟)', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入提醒提前时间字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '5分钟前', '5', '提前5分钟提醒', 1, NOW(), NOW(), 0),
(@dict_id, '15分钟前', '15', '提前15分钟提醒', 2, NOW(), NOW(), 0),
(@dict_id, '30分钟前', '30', '提前30分钟提醒', 3, NOW(), NOW(), 0),
(@dict_id, '1小时前', '60', '提前1小时提醒', 4, NOW(), NOW(), 0),
(@dict_id, '2小时前', '120', '提前2小时提醒', 5, NOW(), NOW(), 0),
(@dict_id, '3小时前', '180', '提前3小时提醒', 6, NOW(), NOW(), 0),
(@dict_id, '6小时前', '360', '提前6小时提醒', 7, NOW(), NOW(), 0),
(@dict_id, '12小时前', '720', '提前12小时提醒', 8, NOW(), NOW(), 0),
(@dict_id, '1天前', '1440', '提前1天提醒', 9, NOW(), NOW(), 0),
(@dict_id, '2天前', '2880', '提前2天提醒', 10, NOW(), NOW(), 0),
(@dict_id, '1周前', '10080', '提前1周提醒', 11, NOW(), NOW(), 0);

-- 插入参与者角色字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('参与者角色', 'participant_role', '日程参与者的角色', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入参与者角色字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '参与者', '1', '普通参与者', 1, NOW(), NOW(), 0),
(@dict_id, '组织者', '2', '日程的组织者', 2, NOW(), NOW(), 0),
(@dict_id, '可选参与者', '3', '可选的参与者', 3, NOW(), NOW(), 0),
(@dict_id, '资源', '4', '会议室等资源', 4, NOW(), NOW(), 0);

-- 插入响应状态字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('响应状态', 'response_status', '参与者对日程邀请的响应状态', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入响应状态字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '未响应', '0', '尚未响应邀请', 1, NOW(), NOW(), 0),
(@dict_id, '接受', '1', '接受邀请', 2, NOW(), NOW(), 0),
(@dict_id, '拒绝', '2', '拒绝邀请', 3, NOW(), NOW(), 0),
(@dict_id, '待定', '3', '暂时待定', 4, NOW(), NOW(), 0);

-- 插入可见性字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('日程可见性', 'schedule_visibility', '日程的可见范围', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入可见性字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '公开', '1', '所有人可见', 1, NOW(), NOW(), 0),
(@dict_id, '私有', '2', '仅创建者可见', 2, NOW(), NOW(), 0),
(@dict_id, '仅团队可见', '3', '仅所属团队可见', 3, NOW(), NOW(), 0);

-- 插入外部日历类型字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('外部日历类型', 'external_calendar_type', '可同步的外部日历类型', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入外部日历类型字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, 'Google日历', 'google', 'Google Calendar', 1, NOW(), NOW(), 0),
(@dict_id, 'Outlook日历', 'outlook', 'Microsoft Outlook Calendar', 2, NOW(), NOW(), 0),
(@dict_id, 'Exchange', 'exchange', 'Microsoft Exchange', 3, NOW(), NOW(), 0),
(@dict_id, 'iCloud', 'icloud', 'Apple iCloud Calendar', 4, NOW(), NOW(), 0);

-- 插入同步方向字典
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('同步方向', 'sync_direction', '日历同步的方向', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入同步方向字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '导入', '1', '从外部日历导入到系统', 1, NOW(), NOW(), 0),
(@dict_id, '导出', '2', '从系统导出到外部日历', 2, NOW(), NOW(), 0),
(@dict_id, '双向同步', '3', '双向同步日程数据', 3, NOW(), NOW(), 0);

-- 插入系统权限数据

-- 日程管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, module, parent_id, sort, status, deleted, create_time)
VALUES 
('日程管理', 'schedule:manage', 1, 'SCHEDULE', 0, 50, 1, 0, NOW());

-- 获取父权限ID
SET @parent_id = LAST_INSERT_ID();

-- 日程权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, module, parent_id, sort, status, deleted, create_time)
VALUES 
('日程查看', 'schedule:view', 2, 'SCHEDULE', @parent_id, 1, 1, 0, NOW()),
('日程创建', 'schedule:create', 2, 'SCHEDULE', @parent_id, 2, 1, 0, NOW()),
('日程编辑', 'schedule:update', 2, 'SCHEDULE', @parent_id, 3, 1, 0, NOW()),
('日程删除', 'schedule:delete', 2, 'SCHEDULE', @parent_id, 4, 1, 0, NOW()),
('日程导出', 'schedule:export', 2, 'SCHEDULE', @parent_id, 5, 1, 0, NOW()),
('日程导入', 'schedule:import', 2, 'SCHEDULE', @parent_id, 6, 1, 0, NOW()),
('日程同步', 'schedule:sync', 2, 'SCHEDULE', @parent_id, 7, 1, 0, NOW()),
('会议室管理', 'schedule:room:manage', 2, 'SCHEDULE', @parent_id, 8, 1, 0, NOW());

-- 初始化超级管理员会议室
INSERT INTO meeting_room (room_name, location, capacity, facilities, description, status, create_by, create_time)
VALUES 
('主会议室', '总部一楼东侧', 20, '投影仪,电视,白板,音响,视频会议系统', '律所最大的会议室，配备完善的会议设施', 1, 1, NOW()),
('小会议室A', '总部二楼西侧', 8, '电视,白板', '中小型会议使用', 1, 1, NOW()),
('小会议室B', '总部二楼东侧', 6, '电视,白板', '小型会议或面谈使用', 1, 1, NOW()),
('VIP接待室', '总部一楼大厅旁', 4, '茶具,沙发', '接待重要客户使用', 1, 1, NOW()); 