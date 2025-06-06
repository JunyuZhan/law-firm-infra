-- 档案管理模块基础数据
-- 版本: V8002
-- 模块: 档案管理模块 (V8000-V8999)
-- 创建时间: 2023-06-01
-- 说明: 档案管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 档案相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('档案类型', 'archive_type', 1, 'system', NOW(), '档案类型字典'),
('档案状态', 'archive_status', 1, 'system', NOW(), '档案状态字典'),
('档案密级', 'archive_level', 1, 'system', NOW(), '档案密级字典'),
('保管期限', 'retention_period', 1, 'system', NOW(), '档案保管期限字典'),
('存储位置类型', 'location_type', 1, 'system', NOW(), '存储位置类型字典'),
('文件类型', 'file_type', 1, 'system', NOW(), '档案文件类型字典'),
('文件状态', 'file_condition', 1, 'system', NOW(), '档案文件状态字典'),
('标签类型', 'label_type', 1, 'system', NOW(), '档案标签类型字典'),
('借阅目的', 'borrow_purpose', 1, 'system', NOW(), '档案借阅目的字典'),
('借阅类型', 'borrow_type', 1, 'system', NOW(), '档案借阅类型字典'),
('借阅状态', 'borrow_status', 1, 'system', NOW(), '档案借阅状态字典'),
('审批结果', 'approval_result', 1, 'system', NOW(), '审批结果字典'),
('归还状态', 'archive_condition', 1, 'system', NOW(), '档案归还状态字典'),
('操作类型', 'archive_operation_type', 1, 'system', NOW(), '档案操作类型字典'),
('盘点类型', 'inventory_type', 1, 'system', NOW(), '档案盘点类型字典'),
('盘点状态', 'inventory_status', 1, 'system', NOW(), '档案盘点状态字典'),
('销毁原因', 'destroy_reason', 1, 'system', NOW(), '档案销毁原因字典'),
('销毁方式', 'destroy_method', 1, 'system', NOW(), '档案销毁方式字典'),
('销毁状态', 'destroy_status', 1, 'system', NOW(), '档案销毁状态字典');

-- ======================= 系统字典数据 =======================

-- 档案类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '案件档案', '1', 'archive_type', '', 'primary', 1, 1, 'system', NOW(), '案件相关档案'),
(2, '合同档案', '2', 'archive_type', '', 'success', 0, 1, 'system', NOW(), '合同相关档案'),
(3, '文档档案', '3', 'archive_type', '', 'info', 0, 1, 'system', NOW(), '普通文档档案'),
(4, '行政档案', '4', 'archive_type', '', 'warning', 0, 1, 'system', NOW(), '行政相关档案'),
(5, '财务档案', '5', 'archive_type', '', 'danger', 0, 1, 'system', NOW(), '财务相关档案');

-- 档案状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '在库', '1', 'archive_status', '', 'success', 1, 1, 'system', NOW(), '档案在库状态'),
(2, '借出', '2', 'archive_status', '', 'warning', 0, 1, 'system', NOW(), '档案借出状态'),
(3, '遗失', '3', 'archive_status', '', 'danger', 0, 1, 'system', NOW(), '档案遗失状态'),
(4, '损坏', '4', 'archive_status', '', 'danger', 0, 1, 'system', NOW(), '档案损坏状态'),
(5, '已销毁', '5', 'archive_status', '', 'info', 0, 1, 'system', NOW(), '档案已销毁状态');

-- 档案密级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '普通', '1', 'archive_level', '', 'info', 1, 1, 'system', NOW(), '普通密级'),
(2, '内部', '2', 'archive_level', '', 'primary', 0, 1, 'system', NOW(), '内部密级'),
(3, '机密', '3', 'archive_level', '', 'warning', 0, 1, 'system', NOW(), '机密密级'),
(4, '绝密', '4', 'archive_level', '', 'danger', 0, 1, 'system', NOW(), '绝密密级');

-- 保管期限
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '3年', '1', 'retention_period', '', 'info', 0, 1, 'system', NOW(), '保管3年'),
(2, '5年', '2', 'retention_period', '', 'primary', 1, 1, 'system', NOW(), '保管5年'),
(3, '10年', '3', 'retention_period', '', 'success', 0, 1, 'system', NOW(), '保管10年'),
(4, '30年', '4', 'retention_period', '', 'warning', 0, 1, 'system', NOW(), '保管30年'),
(5, '永久', '5', 'retention_period', '', 'danger', 0, 1, 'system', NOW(), '永久保管');

-- 存储位置类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '楼层', '1', 'location_type', '', 'primary', 0, 1, 'system', NOW(), '楼层位置'),
(2, '区域', '2', 'location_type', '', 'success', 0, 1, 'system', NOW(), '区域位置'),
(3, '档案柜', '3', 'location_type', '', 'info', 1, 1, 'system', NOW(), '档案柜位置'),
(4, '层板', '4', 'location_type', '', 'warning', 0, 1, 'system', NOW(), '层板位置'),
(5, '位置', '5', 'location_type', '', 'dark', 0, 1, 'system', NOW(), '具体位置');

-- 文件类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '原件', '1', 'file_type', '', 'danger', 1, 1, 'system', NOW(), '原始文件'),
(2, '复印件', '2', 'file_type', '', 'warning', 0, 1, 'system', NOW(), '复印文件'),
(3, '扫描件', '3', 'file_type', '', 'info', 0, 1, 'system', NOW(), '扫描文件'),
(4, '电子件', '4', 'file_type', '', 'success', 0, 1, 'system', NOW(), '电子文件');

-- 文件状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '完好', '1', 'file_condition', '', 'success', 1, 1, 'system', NOW(), '文件完好'),
(2, '轻微损坏', '2', 'file_condition', '', 'warning', 0, 1, 'system', NOW(), '文件轻微损坏'),
(3, '严重损坏', '3', 'file_condition', '', 'danger', 0, 1, 'system', NOW(), '文件严重损坏'),
(4, '无法使用', '4', 'file_condition', '', 'dark', 0, 1, 'system', NOW(), '文件无法使用');

-- 标签类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '业务标签', '1', 'label_type', '', 'primary', 1, 1, 'system', NOW(), '业务相关标签'),
(2, '状态标签', '2', 'label_type', '', 'info', 0, 1, 'system', NOW(), '状态相关标签'),
(3, '优先级标签', '3', 'label_type', '', 'warning', 0, 1, 'system', NOW(), '优先级标签'),
(4, '自定义标签', '4', 'label_type', '', 'success', 0, 1, 'system', NOW(), '自定义标签');

-- 借阅目的
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '业务办理', '1', 'borrow_purpose', '', 'primary', 1, 1, 'system', NOW(), '业务办理目的'),
(2, '研究学习', '2', 'borrow_purpose', '', 'success', 0, 1, 'system', NOW(), '研究学习目的'),
(3, '审计检查', '3', 'borrow_purpose', '', 'warning', 0, 1, 'system', NOW(), '审计检查目的'),
(4, '其他', '4', 'borrow_purpose', '', 'info', 0, 1, 'system', NOW(), '其他目的');

-- 借阅类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '普通借阅', '1', 'borrow_type', '', 'primary', 1, 1, 'system', NOW(), '普通借阅'),
(2, '紧急借阅', '2', 'borrow_type', '', 'danger', 0, 1, 'system', NOW(), '紧急借阅'),
(3, '外借', '3', 'borrow_type', '', 'warning', 0, 1, 'system', NOW(), '外借档案');

-- 借阅状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '申请中', '1', 'borrow_status', '', 'info', 1, 1, 'system', NOW(), '借阅申请中'),
(2, '已批准', '2', 'borrow_status', '', 'success', 0, 1, 'system', NOW(), '借阅已批准'),
(3, '已借出', '3', 'borrow_status', '', 'primary', 0, 1, 'system', NOW(), '档案已借出'),
(4, '已归还', '4', 'borrow_status', '', 'default', 0, 1, 'system', NOW(), '档案已归还'),
(5, '已拒绝', '5', 'borrow_status', '', 'danger', 0, 1, 'system', NOW(), '借阅已拒绝'),
(6, '已超期', '6', 'borrow_status', '', 'warning', 0, 1, 'system', NOW(), '借阅已超期');

-- 审批结果
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待审批', '1', 'approval_result', '', 'warning', 1, 1, 'system', NOW(), '待审批'),
(2, '同意', '2', 'approval_result', '', 'success', 0, 1, 'system', NOW(), '同意审批'),
(3, '拒绝', '3', 'approval_result', '', 'danger', 0, 1, 'system', NOW(), '拒绝审批');

-- 归还状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '完好', '1', 'archive_condition', '', 'success', 1, 1, 'system', NOW(), '档案完好'),
(2, '轻微损坏', '2', 'archive_condition', '', 'warning', 0, 1, 'system', NOW(), '档案轻微损坏'),
(3, '严重损坏', '3', 'archive_condition', '', 'danger', 0, 1, 'system', NOW(), '档案严重损坏'),
(4, '遗失', '4', 'archive_condition', '', 'dark', 0, 1, 'system', NOW(), '档案遗失');

-- 操作类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '创建', '1', 'archive_operation_type', '', 'success', 1, 1, 'system', NOW(), '创建档案'),
(2, '查看', '2', 'archive_operation_type', '', 'info', 0, 1, 'system', NOW(), '查看档案'),
(3, '编辑', '3', 'archive_operation_type', '', 'warning', 0, 1, 'system', NOW(), '编辑档案'),
(4, '删除', '4', 'archive_operation_type', '', 'danger', 0, 1, 'system', NOW(), '删除档案'),
(5, '借阅', '5', 'archive_operation_type', '', 'primary', 0, 1, 'system', NOW(), '借阅档案'),
(6, '归还', '6', 'archive_operation_type', '', 'default', 0, 1, 'system', NOW(), '归还档案'),
(7, '盘点', '7', 'archive_operation_type', '', 'dark', 0, 1, 'system', NOW(), '盘点档案'),
(8, '销毁', '8', 'archive_operation_type', '', 'secondary', 0, 1, 'system', NOW(), '销毁档案');

-- 盘点类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '全面盘点', '1', 'inventory_type', '', 'primary', 1, 1, 'system', NOW(), '全面盘点'),
(2, '抽样盘点', '2', 'inventory_type', '', 'info', 0, 1, 'system', NOW(), '抽样盘点'),
(3, '专项盘点', '3', 'inventory_type', '', 'warning', 0, 1, 'system', NOW(), '专项盘点');

-- 盘点状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '进行中', '1', 'inventory_status', '', 'warning', 1, 1, 'system', NOW(), '盘点进行中'),
(2, '已完成', '2', 'inventory_status', '', 'success', 0, 1, 'system', NOW(), '盘点已完成'),
(3, '已暂停', '3', 'inventory_status', '', 'info', 0, 1, 'system', NOW(), '盘点已暂停');

-- 销毁原因
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '超期', '1', 'destroy_reason', '', 'warning', 1, 1, 'system', NOW(), '保管期超期'),
(2, '损坏', '2', 'destroy_reason', '', 'danger', 0, 1, 'system', NOW(), '档案损坏'),
(3, '法定要求', '3', 'destroy_reason', '', 'primary', 0, 1, 'system', NOW(), '法定要求销毁'),
(4, '其他', '4', 'destroy_reason', '', 'info', 0, 1, 'system', NOW(), '其他原因');

-- 销毁方式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '粉碎', '1', 'destroy_method', '', 'primary', 1, 1, 'system', NOW(), '粉碎销毁'),
(2, '焚烧', '2', 'destroy_method', '', 'danger', 0, 1, 'system', NOW(), '焚烧销毁'),
(3, '其他', '3', 'destroy_method', '', 'info', 0, 1, 'system', NOW(), '其他方式');

-- 销毁状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '申请中', '1', 'destroy_status', '', 'warning', 1, 1, 'system', NOW(), '销毁申请中'),
(2, '已批准', '2', 'destroy_status', '', 'success', 0, 1, 'system', NOW(), '销毁已批准'),
(3, '已销毁', '3', 'destroy_status', '', 'info', 0, 1, 'system', NOW(), '已销毁'),
(4, '已拒绝', '4', 'destroy_status', '', 'danger', 0, 1, 'system', NOW(), '销毁已拒绝');

-- ======================= 档案分类初始化 =======================

-- 档案分类数据
INSERT INTO archive_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, retention_period, access_level, sort_order, is_system, create_by, create_time) VALUES
-- 一级分类
(0, '案件档案', 'CASE_ARCHIVE', 0, 1, '/案件档案', '案件相关档案分类', 2, 2, 1, 1, 'system', NOW()),
(0, '合同档案', 'CONTRACT_ARCHIVE', 0, 1, '/合同档案', '合同相关档案分类', 3, 2, 2, 1, 'system', NOW()),
(0, '文档档案', 'DOCUMENT_ARCHIVE', 0, 1, '/文档档案', '普通文档档案分类', 2, 1, 3, 1, 'system', NOW()),
(0, '行政档案', 'ADMIN_ARCHIVE', 0, 1, '/行政档案', '行政相关档案分类', 2, 2, 4, 1, 'system', NOW()),
(0, '财务档案', 'FINANCE_ARCHIVE', 0, 1, '/财务档案', '财务相关档案分类', 3, 3, 5, 1, 'system', NOW()),

-- 案件档案二级分类
(0, '民事案件', 'CIVIL_CASE_ARCHIVE', 1, 2, '/案件档案/民事案件', '民事案件档案', 2, 2, 1, 1, 'system', NOW()),
(0, '刑事案件', 'CRIMINAL_CASE_ARCHIVE', 1, 2, '/案件档案/刑事案件', '刑事案件档案', 3, 3, 2, 1, 'system', NOW()),
(0, '行政案件', 'ADMIN_CASE_ARCHIVE', 1, 2, '/案件档案/行政案件', '行政案件档案', 2, 2, 3, 1, 'system', NOW()),

-- 合同档案二级分类
(0, '服务合同', 'SERVICE_CONTRACT_ARCHIVE', 2, 2, '/合同档案/服务合同', '法律服务合同档案', 4, 2, 1, 1, 'system', NOW()),
(0, '劳动合同', 'LABOR_CONTRACT_ARCHIVE', 2, 2, '/合同档案/劳动合同', '劳动合同档案', 3, 2, 2, 1, 'system', NOW()),
(0, '重要合同', 'IMPORTANT_CONTRACT_ARCHIVE', 2, 2, '/合同档案/重要合同', '重要合同档案', 5, 3, 3, 1, 'system', NOW()),

-- 财务档案二级分类
(0, '账册档案', 'ACCOUNT_ARCHIVE', 5, 2, '/财务档案/账册档案', '财务账册档案', 3, 3, 1, 1, 'system', NOW()),
(0, '审计档案', 'AUDIT_ARCHIVE', 5, 2, '/财务档案/审计档案', '审计报告档案', 3, 3, 2, 1, 'system', NOW()),
(0, '税务档案', 'TAX_ARCHIVE', 5, 2, '/财务档案/税务档案', '税务相关档案', 3, 3, 3, 1, 'system', NOW());

-- ======================= 档案标签初始化 =======================

-- 业务标签
INSERT INTO archive_label (tenant_id, label_name, label_code, label_type, label_category, color, description, sort_order, is_system, create_by, create_time) VALUES
(0, '重要', 'IMPORTANT', 1, '优先级', '#ff4d4f', '重要档案', 1, 1, 'system', NOW()),
(0, '紧急', 'URGENT', 1, '优先级', '#ff7a45', '紧急档案', 2, 1, 'system', NOW()),
(0, '机密', 'CONFIDENTIAL', 1, '安全', '#722ed1', '机密档案', 3, 1, 'system', NOW()),
(0, '在库', 'IN_STOCK', 2, '状态', '#52c41a', '档案在库', 4, 1, 'system', NOW()),
(0, '借出', 'BORROWED', 2, '状态', '#faad14', '档案借出', 5, 1, 'system', NOW()),
(0, '已审核', 'REVIEWED', 2, '状态', '#1890ff', '已审核档案', 6, 1, 'system', NOW()),
(0, '待整理', 'PENDING_ORGANIZE', 2, '状态', '#d9d9d9', '待整理档案', 7, 1, 'system', NOW()),
(0, '原件', 'ORIGINAL', 1, '类型', '#13c2c2', '原件档案', 8, 1, 'system', NOW()),
(0, '电子档案', 'ELECTRONIC', 1, '类型', '#52c41a', '电子档案', 9, 1, 'system', NOW()),
(0, '可外借', 'BORROWABLE', 1, '权限', '#1890ff', '可外借档案', 10, 1, 'system', NOW());

-- ======================= 存储位置初始化 =======================

-- 基础存储位置
INSERT INTO archive_storage (tenant_id, location_code, location_name, parent_id, level, location_path, location_type, floor_number, description, capacity, manager_name, create_by, create_time) VALUES
-- 楼层
(0, 'F1', '1楼', 0, 1, '/1楼', 1, '1', '档案室1楼', 1000, '档案管理员', 'system', NOW()),
(0, 'F2', '2楼', 0, 1, '/2楼', 1, '2', '档案室2楼', 1000, '档案管理员', 'system', NOW()),

-- 区域
(0, 'F1-A', '1楼A区', 1, 2, '/1楼/A区', 2, '1', 'A区存放合同档案', 200, '档案管理员', 'system', NOW()),
(0, 'F1-B', '1楼B区', 1, 2, '/1楼/B区', 2, '1', 'B区存放案件档案', 200, '档案管理员', 'system', NOW()),
(0, 'F2-A', '2楼A区', 2, 2, '/2楼/A区', 2, '2', 'A区存放财务档案', 200, '档案管理员', 'system', NOW()),

-- 档案柜
(0, 'F1-A-001', '1楼A区001号柜', 3, 3, '/1楼/A区/001号柜', 3, '1', '合同档案柜001', 50, '档案管理员', 'system', NOW()),
(0, 'F1-A-002', '1楼A区002号柜', 3, 3, '/1楼/A区/002号柜', 3, '1', '合同档案柜002', 50, '档案管理员', 'system', NOW()),
(0, 'F1-B-001', '1楼B区001号柜', 4, 3, '/1楼/B区/001号柜', 3, '1', '案件档案柜001', 50, '档案管理员', 'system', NOW()),
(0, 'F2-A-001', '2楼A区001号柜', 5, 3, '/2楼/A区/001号柜', 3, '2', '财务档案柜001', 50, '档案管理员', 'system', NOW());

-- 初始化完成提示
SELECT '档案管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建档案分类：', COUNT(*), '个') AS category_count FROM archive_category WHERE is_system = 1;
SELECT CONCAT('已创建档案标签：', COUNT(*), '个') AS label_count FROM archive_label WHERE is_system = 1;
SELECT CONCAT('已创建存储位置：', COUNT(*), '个') AS storage_count FROM archive_storage; 