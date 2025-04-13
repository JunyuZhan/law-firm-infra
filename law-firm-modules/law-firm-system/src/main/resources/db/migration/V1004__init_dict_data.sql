-- 初始化系统字典数据

-- 清空数据字典表
TRUNCATE TABLE sys_dict;
TRUNCATE TABLE sys_dict_item;

-- 添加系统状态字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(1, 'system_status', '系统状态', '系统通用状态字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加系统状态字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(101, 1, '启用', '1', '系统状态-启用', 1, 1, 0, NOW(), 1, NOW(), 1),
(102, 1, '禁用', '0', '系统状态-禁用', 2, 1, 0, NOW(), 1, NOW(), 1);

-- 添加性别字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(2, 'gender', '性别', '性别字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加性别字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(201, 2, '男', '1', '性别-男', 1, 1, 0, NOW(), 1, NOW(), 1),
(202, 2, '女', '2', '性别-女', 2, 1, 0, NOW(), 1, NOW(), 1),
(203, 2, '未知', '0', '性别-未知', 3, 1, 0, NOW(), 1, NOW(), 1);

-- 添加婚姻状况字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(3, 'marital_status', '婚姻状况', '婚姻状况字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加婚姻状况字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(301, 3, '未婚', '1', '婚姻状况-未婚', 1, 1, 0, NOW(), 1, NOW(), 1),
(302, 3, '已婚', '2', '婚姻状况-已婚', 2, 1, 0, NOW(), 1, NOW(), 1),
(303, 3, '离婚', '3', '婚姻状况-离婚', 3, 1, 0, NOW(), 1, NOW(), 1),
(304, 3, '丧偶', '4', '婚姻状况-丧偶', 4, 1, 0, NOW(), 1, NOW(), 1),
(305, 3, '未知', '0', '婚姻状况-未知', 5, 1, 0, NOW(), 1, NOW(), 1);

-- 添加学历字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(4, 'education', '学历', '学历字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加学历字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(401, 4, '小学', '1', '学历-小学', 1, 1, 0, NOW(), 1, NOW(), 1),
(402, 4, '初中', '2', '学历-初中', 2, 1, 0, NOW(), 1, NOW(), 1),
(403, 4, '高中', '3', '学历-高中', 3, 1, 0, NOW(), 1, NOW(), 1),
(404, 4, '中专', '4', '学历-中专', 4, 1, 0, NOW(), 1, NOW(), 1),
(405, 4, '大专', '5', '学历-大专', 5, 1, 0, NOW(), 1, NOW(), 1),
(406, 4, '本科', '6', '学历-本科', 6, 1, 0, NOW(), 1, NOW(), 1),
(407, 4, '硕士', '7', '学历-硕士', 7, 1, 0, NOW(), 1, NOW(), 1),
(408, 4, '博士', '8', '学历-博士', 8, 1, 0, NOW(), 1, NOW(), 1),
(409, 4, '其他', '0', '学历-其他', 9, 1, 0, NOW(), 1, NOW(), 1);

-- 添加政治面貌字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(5, 'political_status', '政治面貌', '政治面貌字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加政治面貌字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(501, 5, '中共党员', '1', '政治面貌-中共党员', 1, 1, 0, NOW(), 1, NOW(), 1),
(502, 5, '中共预备党员', '2', '政治面貌-中共预备党员', 2, 1, 0, NOW(), 1, NOW(), 1),
(503, 5, '共青团员', '3', '政治面貌-共青团员', 3, 1, 0, NOW(), 1, NOW(), 1),
(504, 5, '民主党派', '4', '政治面貌-民主党派', 4, 1, 0, NOW(), 1, NOW(), 1),
(505, 5, '群众', '5', '政治面貌-群众', 5, 1, 0, NOW(), 1, NOW(), 1),
(506, 5, '其他', '0', '政治面貌-其他', 6, 1, 0, NOW(), 1, NOW(), 1);

-- 添加证件类型字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(6, 'id_type', '证件类型', '证件类型字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加证件类型字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(601, 6, '居民身份证', '1', '证件类型-居民身份证', 1, 1, 0, NOW(), 1, NOW(), 1),
(602, 6, '护照', '2', '证件类型-护照', 2, 1, 0, NOW(), 1, NOW(), 1),
(603, 6, '军官证', '3', '证件类型-军官证', 3, 1, 0, NOW(), 1, NOW(), 1),
(604, 6, '港澳通行证', '4', '证件类型-港澳通行证', 4, 1, 0, NOW(), 1, NOW(), 1),
(605, 6, '台湾通行证', '5', '证件类型-台湾通行证', 5, 1, 0, NOW(), 1, NOW(), 1),
(606, 6, '外国人居留许可', '6', '证件类型-外国人居留许可', 6, 1, 0, NOW(), 1, NOW(), 1),
(607, 6, '其他', '0', '证件类型-其他', 7, 1, 0, NOW(), 1, NOW(), 1);

-- 添加员工状态字典
INSERT INTO sys_dict (id, dict_code, dict_name, description, status, deleted, create_time, create_by, update_time, update_by) VALUES
(7, 'employee_status', '员工状态', '员工状态字典', 1, 0, NOW(), 1, NOW(), 1);

-- 添加员工状态字典项
INSERT INTO sys_dict_item (id, dict_id, item_name, item_value, description, sort, status, deleted, create_time, create_by, update_time, update_by) VALUES
(701, 7, '试用期', '1', '员工状态-试用期', 1, 1, 0, NOW(), 1, NOW(), 1),
(702, 7, '正式', '2', '员工状态-正式', 2, 1, 0, NOW(), 1, NOW(), 1),
(703, 7, '离职', '3', '员工状态-离职', 3, 1, 0, NOW(), 1, NOW(), 1),
(704, 7, '退休', '4', '员工状态-退休', 4, 1, 0, NOW(), 1, NOW(), 1),
(705, 7, '实习', '5', '员工状态-实习', 5, 1, 0, NOW(), 1, NOW(), 1),
(706, 7, '停职', '6', '员工状态-停职', 6, 1, 0, NOW(), 1, NOW(), 1),
(707, 7, '其他', '0', '员工状态-其他', 7, 1, 0, NOW(), 1, NOW(), 1); 