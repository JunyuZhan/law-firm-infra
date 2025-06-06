-- Task模块数据初始化
-- 版本: V13002  
-- 模块: task
-- 创建时间: 2023-06-10
-- 说明: 任务管理模块基础数据，包括字典数据、分类数据、标签数据等

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 字典类型数据 =======================

-- 任务类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('任务类型', 'task_type', 1, 'system', NOW(), '任务业务类型分类'),
('任务优先级', 'task_priority', 1, 'system', NOW(), '任务优先级等级'),
('任务状态', 'task_status', 1, 'system', NOW(), '任务当前状态'),
('难度等级', 'difficulty_level', 1, 'system', NOW(), '任务难度分级'),
('重要程度', 'importance_level', 1, 'system', NOW(), '任务重要性程度'),
('紧急程度', 'urgency_level', 1, 'system', NOW(), '任务紧急程度'),
('参与角色', 'participant_role', 1, 'system', NOW(), '任务参与者角色'),
('权限级别', 'permission_level', 1, 'system', NOW(), '任务权限等级'),
('工作类型', 'work_type', 1, 'system', NOW(), '工作内容类型'),
('标签类型', 'tag_type', 1, 'system', NOW(), '任务标签分类'),
('附件类型', 'attachment_type', 1, 'system', NOW(), '任务附件类型'),
('内容类型', 'content_type', 1, 'system', NOW(), '任务内容分类'),
('统计类型', 'stat_type', 1, 'system', NOW(), '统计维度类型'),
('加入方式', 'join_type', 1, 'system', NOW(), '参与任务方式');

-- ======================= 字典数据 =======================

-- 任务类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '法律事务', '1', 'task_type', '', 'primary', 1, 1, 'system', NOW(), '法律相关任务'),
(2, '行政事务', '2', 'task_type', '', 'success', 0, 1, 'system', NOW(), '行政管理任务'),
(3, '客户服务', '3', 'task_type', '', 'info', 0, 1, 'system', NOW(), '客户服务任务'),
(4, '财务管理', '4', 'task_type', '', 'warning', 0, 1, 'system', NOW(), '财务相关任务'),
(5, '日常事务', '5', 'task_type', '', 'secondary', 0, 1, 'system', NOW(), '日常工作任务');

-- 任务优先级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'task_priority', '', 'success', 0, 1, 'system', NOW(), '低优先级'),
(2, '中', '2', 'task_priority', '', 'primary', 1, 1, 'system', NOW(), '中等优先级'),
(3, '高', '3', 'task_priority', '', 'warning', 0, 1, 'system', NOW(), '高优先级'),
(4, '紧急', '4', 'task_priority', '', 'danger', 0, 1, 'system', NOW(), '紧急优先级');

-- 任务状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'task_status', '', 'secondary', 1, 1, 'system', NOW(), '草稿状态'),
(2, '待分配', '2', 'task_status', '', 'info', 0, 1, 'system', NOW(), '待分配状态'),
(3, '进行中', '3', 'task_status', '', 'primary', 0, 1, 'system', NOW(), '进行中状态'),
(4, '已完成', '4', 'task_status', '', 'success', 0, 1, 'system', NOW(), '已完成状态'),
(5, '已暂停', '5', 'task_status', '', 'warning', 0, 1, 'system', NOW(), '已暂停状态'),
(6, '已取消', '6', 'task_status', '', 'danger', 0, 1, 'system', NOW(), '已取消状态');

-- ======================= 任务分类数据 =======================

-- 一级分类
INSERT INTO work_task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort, create_by, create_time, status) VALUES
(0, '法律事务', 'LEGAL_AFFAIRS', 0, 1, '/LEGAL_AFFAIRS', 'legal', '#007bff', '法律相关事务处理', 1, 0, 1, 'system', NOW(), 1),
(0, '行政事务', 'ADMIN_AFFAIRS', 0, 1, '/ADMIN_AFFAIRS', 'admin', '#28a745', '行政管理相关事务', 1, 0, 2, 'system', NOW(), 1),
(0, '客户服务', 'CLIENT_SERVICE', 0, 1, '/CLIENT_SERVICE', 'client', '#17a2b8', '客户服务相关任务', 1, 0, 3, 'system', NOW(), 1),
(0, '财务管理', 'FINANCE_MANAGEMENT', 0, 1, '/FINANCE_MANAGEMENT', 'finance', '#ffc107', '财务管理相关任务', 1, 0, 4, 'system', NOW(), 1),
(0, '日常事务', 'DAILY_AFFAIRS', 0, 1, '/DAILY_AFFAIRS', 'daily', '#6c757d', '日常工作事务', 1, 0, 5, 'system', NOW(), 1);

-- ======================= 任务标签数据 =======================

-- 优先级标签
INSERT INTO work_task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort, create_by, create_time, status) VALUES
(0, '紧急', 'URGENT', '#dc3545', 1, '优先级', '紧急处理标签', 1, 1, 'system', NOW(), 1),
(0, '重要', 'IMPORTANT', '#fd7e14', 1, '优先级', '重要任务标签', 1, 2, 'system', NOW(), 1),
(0, '一般', 'NORMAL', '#6c757d', 1, '优先级', '一般任务标签', 1, 3, 'system', NOW(), 1),
(0, '可延期', 'DEFERRABLE', '#28a745', 1, '优先级', '可延期处理标签', 1, 4, 'system', NOW(), 1);

-- 业务标签
INSERT INTO work_task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort, create_by, create_time, status) VALUES
(0, '法律', 'LEGAL', '#007bff', 2, '业务类型', '法律业务标签', 1, 1, 'system', NOW(), 1),
(0, '行政', 'ADMIN', '#28a745', 2, '业务类型', '行政管理标签', 1, 2, 'system', NOW(), 1),
(0, '客户', 'CLIENT', '#17a2b8', 2, '业务类型', '客户服务标签', 1, 3, 'system', NOW(), 1),
(0, '财务', 'FINANCE', '#ffc107', 2, '业务类型', '财务管理标签', 1, 4, 'system', NOW(), 1),
(0, '技术', 'TECHNICAL', '#6f42c1', 2, '业务类型', '技术相关标签', 1, 5, 'system', NOW(), 1);

-- 初始化完成提示
SELECT '任务管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'task_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_level' OR dict_type LIKE '%_role';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'task_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_level' OR dict_type LIKE '%_role';
SELECT CONCAT('已创建任务分类：', COUNT(*), '个') AS category_count FROM work_task_category WHERE tenant_id = 0;
SELECT CONCAT('已创建任务标签：', COUNT(*), '个') AS tag_count FROM work_task_tag WHERE tenant_id = 0; 