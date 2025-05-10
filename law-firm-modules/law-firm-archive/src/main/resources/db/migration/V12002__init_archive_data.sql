-- 档案模块初始数据
-- 版本: V12002
-- 模块: archive
-- 创建时间: 2025-04-26
-- 说明: 初始化档案管理模块的字典数据和基础数据
-- 依赖: V12001档案表结构，V0002基础字典表数据

-- 设置字符集
SET NAMES utf8mb4;

-- 档案类型字典
INSERT INTO sys_dict_type(dict_name, dict_type, status, remark, create_by, create_time)
VALUES ('档案类型', 'archive_type', 0, '档案管理的类型', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_name='档案类型', status=0, update_time=NOW();

-- 档案类型字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, remark, create_by, create_time)
VALUES 
(1, '案件档案', '1', 'archive_type', '', 'primary', 'Y', 0, '案件相关档案', 'admin', NOW()),
(2, '合同档案', '2', 'archive_type', '', 'success', 'N', 0, '合同相关档案', 'admin', NOW()),
(3, '文档档案', '3', 'archive_type', '', 'info', 'N', 0, '普通文档档案', 'admin', NOW()),
(4, '行政档案', '4', 'archive_type', '', 'warning', 'N', 0, '行政相关档案', 'admin', NOW()),
(5, '财务档案', '5', 'archive_type', '', 'danger', 'N', 0, '财务相关档案', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_label=VALUES(dict_label), dict_value=VALUES(dict_value), status=VALUES(status), update_time=NOW();

-- 档案状态字典
INSERT INTO sys_dict_type(dict_name, dict_type, status, remark, create_by, create_time)
VALUES ('档案状态', 'archive_status', 0, '档案的状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_name='档案状态', status=0, update_time=NOW();

-- 档案状态字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, remark, create_by, create_time)
VALUES 
(1, '正常', '1', 'archive_status', '', 'primary', 'Y', 0, '正常状态', 'admin', NOW()),
(2, '借出', '2', 'archive_status', '', 'warning', 'N', 0, '已借出状态', 'admin', NOW()),
(3, '遗失', '3', 'archive_status', '', 'danger', 'N', 0, '遗失状态', 'admin', NOW()),
(4, '损坏', '4', 'archive_status', '', 'danger', 'N', 0, '损坏状态', 'admin', NOW()),
(5, '已销毁', '5', 'archive_status', '', 'info', 'N', 0, '已销毁状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_label=VALUES(dict_label), dict_value=VALUES(dict_value), status=VALUES(status), update_time=NOW();

-- 同步状态字典
INSERT INTO sys_dict_type(dict_name, dict_type, status, remark, create_by, create_time)
VALUES ('同步状态', 'sync_status', 0, '档案同步状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_name='同步状态', status=0, update_time=NOW();

-- 同步状态字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, remark, create_by, create_time)
VALUES 
(1, '未同步', '0', 'sync_status', '', 'info', 'Y', 0, '未同步状态', 'admin', NOW()),
(2, '已同步', '1', 'sync_status', '', 'success', 'N', 0, '已同步状态', 'admin', NOW()),
(3, '同步失败', '2', 'sync_status', '', 'danger', 'N', 0, '同步失败状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_label=VALUES(dict_label), dict_value=VALUES(dict_value), status=VALUES(status), update_time=NOW();

-- 借阅状态字典
INSERT INTO sys_dict_type(dict_name, dict_type, status, remark, create_by, create_time)
VALUES ('借阅状态', 'borrow_status', 0, '档案借阅状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_name='借阅状态', status=0, update_time=NOW();

-- 借阅状态字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, remark, create_by, create_time)
VALUES 
(1, '未借出', '0', 'borrow_status', '', 'primary', 'Y', 0, '未借出状态', 'admin', NOW()),
(2, '已借出', '1', 'borrow_status', '', 'warning', 'N', 0, '已借出状态', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_label=VALUES(dict_label), dict_value=VALUES(dict_value), status=VALUES(status), update_time=NOW();

-- 业务类型字典
INSERT INTO sys_dict_type(dict_name, dict_type, status, remark, create_by, create_time)
VALUES ('档案业务类型', 'archive_business_type', 0, '档案关联的业务类型', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_name='档案业务类型', status=0, update_time=NOW();

-- 业务类型字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, remark, create_by, create_time)
VALUES 
(1, '案件', 'CASE', 'archive_business_type', '', 'primary', 'Y', 0, '案件业务', 'admin', NOW()),
(2, '合同', 'CONTRACT', 'archive_business_type', '', 'success', 'N', 0, '合同业务', 'admin', NOW()),
(3, '文档', 'DOCUMENT', 'archive_business_type', '', 'info', 'N', 0, '文档业务', 'admin', NOW()),
(4, '行政', 'ADMIN', 'archive_business_type', '', 'warning', 'N', 0, '行政业务', 'admin', NOW()),
(5, '财务', 'FINANCE', 'archive_business_type', '', 'danger', 'N', 0, '财务业务', 'admin', NOW())
ON DUPLICATE KEY UPDATE dict_label=VALUES(dict_label), dict_value=VALUES(dict_value), status=VALUES(status), update_time=NOW(); 