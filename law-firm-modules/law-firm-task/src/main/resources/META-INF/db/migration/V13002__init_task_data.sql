-- 任务管理模块基础数据
-- 版本: V13002
-- 模块: 任务管理模块 (V13000-V13999)
-- 创建时间: 2023-06-01
-- 说明: 任务管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 任务管理相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('任务类型', 'task_type', 1, 'system', NOW(), '任务类型字典'),
('任务优先级', 'task_priority', 1, 'system', NOW(), '任务优先级字典'),
('任务状态', 'task_status', 1, 'system', NOW(), '任务状态字典'),
('难度等级', 'difficulty_level', 1, 'system', NOW(), '任务难度等级字典'),
('重要程度', 'importance_level', 1, 'system', NOW(), '任务重要程度字典'),
('紧急程度', 'urgency_level', 1, 'system', NOW(), '任务紧急程度字典'),
('参与角色', 'participant_role', 1, 'system', NOW(), '任务参与角色字典'),
('权限级别', 'permission_level', 1, 'system', NOW(), '任务权限级别字典'),
('工作类型', 'work_type', 1, 'system', NOW(), '工作类型字典'),
('模板类型', 'template_type', 1, 'system', NOW(), '任务模板类型字典'),
('标签类型', 'tag_type', 1, 'system', NOW(), '任务标签类型字典'),
('附件类型', 'attachment_type', 1, 'system', NOW(), '任务附件类型字典'),
('内容类型', 'content_type', 1, 'system', NOW(), '任务内容类型字典'),
('存储类型', 'storage_type', 1, 'system', NOW(), '存储类型字典'),
('统计类型', 'stat_type', 1, 'system', NOW(), '统计类型字典'),
('加入方式', 'join_type', 1, 'system', NOW(), '参与者加入方式字典');

-- ======================= 系统字典数据 =======================

-- 任务类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '法律事务', '1', 'task_type', '', 'primary', 'Y', 1, 'system', NOW(), '法律相关任务'),
(2, '行政事务', '2', 'task_type', '', 'success', 'N', 1, 'system', NOW(), '行政管理任务'),
(3, '客户服务', '3', 'task_type', '', 'info', 'N', 1, 'system', NOW(), '客户服务任务'),
(4, '财务管理', '4', 'task_type', '', 'warning', 'N', 1, 'system', NOW(), '财务相关任务'),
(5, '日常事务', '5', 'task_type', '', 'secondary', 'N', 1, 'system', NOW(), '日常工作任务');

-- 任务优先级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'task_priority', '', 'success', 'N', 1, 'system', NOW(), '低优先级'),
(2, '中', '2', 'task_priority', '', 'primary', 'Y', 1, 'system', NOW(), '中等优先级'),
(3, '高', '3', 'task_priority', '', 'warning', 'N', 1, 'system', NOW(), '高优先级'),
(4, '紧急', '4', 'task_priority', '', 'danger', 'N', 1, 'system', NOW(), '紧急优先级');

-- 任务状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '草稿', '1', 'task_status', '', 'secondary', 'Y', 1, 'system', NOW(), '草稿状态'),
(2, '待分配', '2', 'task_status', '', 'info', 'N', 1, 'system', NOW(), '待分配状态'),
(3, '进行中', '3', 'task_status', '', 'primary', 'N', 1, 'system', NOW(), '进行中状态'),
(4, '已完成', '4', 'task_status', '', 'success', 'N', 1, 'system', NOW(), '已完成状态'),
(5, '已暂停', '5', 'task_status', '', 'warning', 'N', 1, 'system', NOW(), '已暂停状态'),
(6, '已取消', '6', 'task_status', '', 'danger', 'N', 1, 'system', NOW(), '已取消状态');

-- 难度等级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '简单', '1', 'difficulty_level', '', 'success', 'N', 1, 'system', NOW(), '简单任务'),
(2, '中等', '2', 'difficulty_level', '', 'primary', 'Y', 1, 'system', NOW(), '中等难度'),
(3, '困难', '3', 'difficulty_level', '', 'warning', 'N', 1, 'system', NOW(), '困难任务'),
(4, '专家', '4', 'difficulty_level', '', 'danger', 'N', 1, 'system', NOW(), '专家级任务');

-- 重要程度
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'importance_level', '', 'success', 'N', 1, 'system', NOW(), '低重要性'),
(2, '中', '2', 'importance_level', '', 'primary', 'Y', 1, 'system', NOW(), '中等重要'),
(3, '高', '3', 'importance_level', '', 'warning', 'N', 1, 'system', NOW(), '高重要性'),
(4, '关键', '4', 'importance_level', '', 'danger', 'N', 1, 'system', NOW(), '关键重要');

-- 紧急程度
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'urgency_level', '', 'success', 'N', 1, 'system', NOW(), '低紧急度'),
(2, '中', '2', 'urgency_level', '', 'primary', 'Y', 1, 'system', NOW(), '中等紧急'),
(3, '高', '3', 'urgency_level', '', 'warning', 'N', 1, 'system', NOW(), '高紧急度'),
(4, '紧急', '4', 'urgency_level', '', 'danger', 'N', 1, 'system', NOW(), '紧急处理');

-- 参与角色
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '创建者', '1', 'participant_role', '', 'primary', 'N', 1, 'system', NOW(), '任务创建者'),
(2, '负责人', '2', 'participant_role', '', 'success', 'Y', 1, 'system', NOW(), '任务负责人'),
(3, '参与者', '3', 'participant_role', '', 'info', 'N', 1, 'system', NOW(), '任务参与者'),
(4, '观察者', '4', 'participant_role', '', 'secondary', 'N', 1, 'system', NOW(), '任务观察者');

-- 权限级别
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '只读', '1', 'permission_level', '', 'info', 'Y', 1, 'system', NOW(), '只读权限'),
(2, '编辑', '2', 'permission_level', '', 'primary', 'N', 1, 'system', NOW(), '编辑权限'),
(3, '管理', '3', 'permission_level', '', 'warning', 'N', 1, 'system', NOW(), '管理权限');

-- 工作类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '开发', '1', 'work_type', '', 'primary', 'N', 1, 'system', NOW(), '开发工作'),
(2, '测试', '2', 'work_type', '', 'success', 'N', 1, 'system', NOW(), '测试工作'),
(3, '文档', '3', 'work_type', '', 'info', 'N', 1, 'system', NOW(), '文档编写'),
(4, '会议', '4', 'work_type', '', 'warning', 'Y', 1, 'system', NOW(), '会议讨论'),
(5, '其他', '5', 'work_type', '', 'secondary', 'N', 1, 'system', NOW(), '其他工作');

-- 模板类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '任务模板', '1', 'template_type', '', 'primary', 'Y', 1, 'system', NOW(), '通用任务模板'),
(2, '流程模板', '2', 'template_type', '', 'success', 'N', 1, 'system', NOW(), '工作流程模板'),
(3, '检查清单', '3', 'template_type', '', 'info', 'N', 1, 'system', NOW(), '检查清单模板');

-- 标签类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '优先级', '1', 'tag_type', '', 'danger', 'N', 1, 'system', NOW(), '优先级相关标签'),
(2, '业务', '2', 'tag_type', '', 'primary', 'Y', 1, 'system', NOW(), '业务类型标签'),
(3, '状态', '3', 'tag_type', '', 'success', 'N', 1, 'system', NOW(), '状态相关标签'),
(4, '属性', '4', 'tag_type', '', 'info', 'N', 1, 'system', NOW(), '属性特征标签');

-- 附件类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '需求文档', '1', 'attachment_type', '', 'primary', 'Y', 1, 'system', NOW(), '需求说明文档'),
(2, '设计图', '2', 'attachment_type', '', 'success', 'N', 1, 'system', NOW(), '设计图纸'),
(3, '参考资料', '3', 'attachment_type', '', 'info', 'N', 1, 'system', NOW(), '参考资料'),
(4, '其他', '4', 'attachment_type', '', 'secondary', 'N', 1, 'system', NOW(), '其他附件');

-- 内容类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '主要内容', '1', 'content_type', '', 'primary', 'Y', 1, 'system', NOW(), '任务主要内容'),
(2, '需求说明', '2', 'content_type', '', 'success', 'N', 1, 'system', NOW(), '需求详细说明'),
(3, '验收标准', '3', 'content_type', '', 'warning', 'N', 1, 'system', NOW(), '验收标准'),
(4, '操作指南', '4', 'content_type', '', 'info', 'N', 1, 'system', NOW(), '操作指导');

-- 存储类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '本地存储', '1', 'storage_type', '', 'primary', 'Y', 1, 'system', NOW(), '本地文件系统'),
(2, '云存储', '2', 'storage_type', '', 'success', 'N', 1, 'system', NOW(), '云端存储'),
(3, '第三方存储', '3', 'storage_type', '', 'info', 'N', 1, 'system', NOW(), '第三方存储服务');

-- 统计类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '日统计', '1', 'stat_type', '', 'primary', 'Y', 1, 'system', NOW(), '按日统计'),
(2, '周统计', '2', 'stat_type', '', 'success', 'N', 1, 'system', NOW(), '按周统计'),
(3, '月统计', '3', 'stat_type', '', 'info', 'N', 1, 'system', NOW(), '按月统计'),
(4, '年统计', '4', 'stat_type', '', 'warning', 'N', 1, 'system', NOW(), '按年统计');

-- 加入方式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '邀请', '1', 'join_type', '', 'success', 'Y', 1, 'system', NOW(), '受邀加入'),
(2, '申请', '2', 'join_type', '', 'primary', 'N', 1, 'system', NOW(), '主动申请'),
(3, '指派', '3', 'join_type', '', 'warning', 'N', 1, 'system', NOW(), '管理员指派');

-- ======================= 任务分类数据 =======================

-- 一级分类
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '法律事务', 'LEGAL_AFFAIRS', 0, 1, '/LEGAL_AFFAIRS', 'legal', '#007bff', '法律相关事务处理', 1, 0, 1, 'system', NOW(), 1),
(0, '行政事务', 'ADMIN_AFFAIRS', 0, 1, '/ADMIN_AFFAIRS', 'admin', '#28a745', '行政管理相关事务', 1, 0, 2, 'system', NOW(), 1),
(0, '客户服务', 'CLIENT_SERVICE', 0, 1, '/CLIENT_SERVICE', 'client', '#17a2b8', '客户服务相关任务', 1, 0, 3, 'system', NOW(), 1),
(0, '财务管理', 'FINANCE_MANAGEMENT', 0, 1, '/FINANCE_MANAGEMENT', 'finance', '#ffc107', '财务管理相关任务', 1, 0, 4, 'system', NOW(), 1),
(0, '日常事务', 'DAILY_AFFAIRS', 0, 1, '/DAILY_AFFAIRS', 'daily', '#6c757d', '日常工作事务', 1, 0, 5, 'system', NOW(), 1);

-- 二级分类 - 法律事务
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '案件处理', 'CASE_HANDLING', 1, 2, '/LEGAL_AFFAIRS/CASE_HANDLING', 'case', '#007bff', '案件相关任务处理', 1, 1, 1, 'system', NOW(), 1),
(0, '合同审核', 'CONTRACT_REVIEW', 1, 2, '/LEGAL_AFFAIRS/CONTRACT_REVIEW', 'contract', '#007bff', '合同审核相关任务', 1, 1, 2, 'system', NOW(), 1),
(0, '法律研究', 'LEGAL_RESEARCH', 1, 2, '/LEGAL_AFFAIRS/LEGAL_RESEARCH', 'research', '#007bff', '法律条文研究任务', 1, 1, 3, 'system', NOW(), 1),
(0, '文书起草', 'DOCUMENT_DRAFTING', 1, 2, '/LEGAL_AFFAIRS/DOCUMENT_DRAFTING', 'document', '#007bff', '法律文书起草任务', 1, 1, 4, 'system', NOW(), 1),
(0, '出庭准备', 'COURT_PREPARATION', 1, 2, '/LEGAL_AFFAIRS/COURT_PREPARATION', 'court', '#007bff', '法庭出庭准备工作', 1, 1, 5, 'system', NOW(), 1),
(0, '证据收集', 'EVIDENCE_COLLECTION', 1, 2, '/LEGAL_AFFAIRS/EVIDENCE_COLLECTION', 'evidence', '#007bff', '案件证据收集任务', 1, 1, 6, 'system', NOW(), 1);

-- 二级分类 - 行政事务
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '会议组织', 'MEETING_ORGANIZATION', 2, 2, '/ADMIN_AFFAIRS/MEETING_ORGANIZATION', 'meeting', '#28a745', '会议组织筹备任务', 1, 1, 1, 'system', NOW(), 1),
(0, '员工管理', 'EMPLOYEE_MANAGEMENT', 2, 2, '/ADMIN_AFFAIRS/EMPLOYEE_MANAGEMENT', 'employee', '#28a745', '员工管理相关任务', 1, 1, 2, 'system', NOW(), 1),
(0, '办公管理', 'OFFICE_MANAGEMENT', 2, 2, '/ADMIN_AFFAIRS/OFFICE_MANAGEMENT', 'office', '#28a745', '办公室管理任务', 1, 1, 3, 'system', NOW(), 1),
(0, '设备维护', 'EQUIPMENT_MAINTENANCE', 2, 2, '/ADMIN_AFFAIRS/EQUIPMENT_MAINTENANCE', 'equipment', '#28a745', '设备维护保养任务', 1, 1, 4, 'system', NOW(), 1),
(0, '采购管理', 'PROCUREMENT_MANAGEMENT', 2, 2, '/ADMIN_AFFAIRS/PROCUREMENT_MANAGEMENT', 'procurement', '#28a745', '采购相关管理任务', 1, 1, 5, 'system', NOW(), 1);

-- 二级分类 - 客户服务
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '客户咨询', 'CLIENT_CONSULTATION', 3, 2, '/CLIENT_SERVICE/CLIENT_CONSULTATION', 'consultation', '#17a2b8', '客户咨询服务任务', 1, 1, 1, 'system', NOW(), 1),
(0, '关系维护', 'RELATIONSHIP_MAINTENANCE', 3, 2, '/CLIENT_SERVICE/RELATIONSHIP_MAINTENANCE', 'relationship', '#17a2b8', '客户关系维护任务', 1, 1, 2, 'system', NOW(), 1),
(0, '满意度调查', 'SATISFACTION_SURVEY', 3, 2, '/CLIENT_SERVICE/SATISFACTION_SURVEY', 'survey', '#17a2b8', '客户满意度调查', 1, 1, 3, 'system', NOW(), 1),
(0, '投诉处理', 'COMPLAINT_HANDLING', 3, 2, '/CLIENT_SERVICE/COMPLAINT_HANDLING', 'complaint', '#17a2b8', '客户投诉处理任务', 1, 1, 4, 'system', NOW(), 1);

-- 二级分类 - 财务管理
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '费用结算', 'FEE_SETTLEMENT', 4, 2, '/FINANCE_MANAGEMENT/FEE_SETTLEMENT', 'settlement', '#ffc107', '费用结算相关任务', 1, 1, 1, 'system', NOW(), 1),
(0, '发票管理', 'INVOICE_MANAGEMENT', 4, 2, '/FINANCE_MANAGEMENT/INVOICE_MANAGEMENT', 'invoice', '#ffc107', '发票开具管理任务', 1, 1, 2, 'system', NOW(), 1),
(0, '财务分析', 'FINANCIAL_ANALYSIS', 4, 2, '/FINANCE_MANAGEMENT/FINANCIAL_ANALYSIS', 'analysis', '#ffc107', '财务数据分析任务', 1, 1, 3, 'system', NOW(), 1),
(0, '预算管理', 'BUDGET_MANAGEMENT', 4, 2, '/FINANCE_MANAGEMENT/BUDGET_MANAGEMENT', 'budget', '#ffc107', '预算制定管理任务', 1, 1, 4, 'system', NOW(), 1);

-- 二级分类 - 日常事务
INSERT INTO task_category (tenant_id, category_name, category_code, parent_id, level, path, icon, color, description, is_system, is_leaf, sort_order, create_by, create_time, status) VALUES
(0, '培训学习', 'TRAINING_LEARNING', 5, 2, '/DAILY_AFFAIRS/TRAINING_LEARNING', 'training', '#6c757d', '培训学习相关任务', 1, 1, 1, 'system', NOW(), 1),
(0, '团建活动', 'TEAM_BUILDING', 5, 2, '/DAILY_AFFAIRS/TEAM_BUILDING', 'teambuilding', '#6c757d', '团队建设活动任务', 1, 1, 2, 'system', NOW(), 1),
(0, '其他事务', 'OTHER_AFFAIRS', 5, 2, '/DAILY_AFFAIRS/OTHER_AFFAIRS', 'other', '#6c757d', '其他日常事务', 1, 1, 3, 'system', NOW(), 1);

-- ======================= 任务标签数据 =======================

-- 优先级标签
INSERT INTO task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort_order, create_by, create_time, status) VALUES
(0, '紧急', 'URGENT', '#dc3545', 1, '优先级', '紧急处理标签', 1, 1, 'system', NOW(), 1),
(0, '重要', 'IMPORTANT', '#fd7e14', 1, '优先级', '重要任务标签', 1, 2, 'system', NOW(), 1),
(0, '一般', 'NORMAL', '#6c757d', 1, '优先级', '一般任务标签', 1, 3, 'system', NOW(), 1),
(0, '可延期', 'DEFERRABLE', '#28a745', 1, '优先级', '可延期处理标签', 1, 4, 'system', NOW(), 1);

-- 业务标签
INSERT INTO task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort_order, create_by, create_time, status) VALUES
(0, '法律', 'LEGAL', '#007bff', 2, '业务类型', '法律业务标签', 1, 1, 'system', NOW(), 1),
(0, '行政', 'ADMIN', '#28a745', 2, '业务类型', '行政管理标签', 1, 2, 'system', NOW(), 1),
(0, '客户', 'CLIENT', '#17a2b8', 2, '业务类型', '客户服务标签', 1, 3, 'system', NOW(), 1),
(0, '财务', 'FINANCE', '#ffc107', 2, '业务类型', '财务管理标签', 1, 4, 'system', NOW(), 1),
(0, '技术', 'TECHNICAL', '#6f42c1', 2, '业务类型', '技术相关标签', 1, 5, 'system', NOW(), 1);

-- 状态标签
INSERT INTO task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort_order, create_by, create_time, status) VALUES
(0, '新任务', 'NEW_TASK', '#20c997', 3, '任务状态', '新建任务标签', 1, 1, 'system', NOW(), 1),
(0, '进行中', 'IN_PROGRESS', '#007bff', 3, '任务状态', '正在进行标签', 1, 2, 'system', NOW(), 1),
(0, '待审核', 'PENDING_REVIEW', '#fd7e14', 3, '任务状态', '待审核标签', 1, 3, 'system', NOW(), 1),
(0, '已完成', 'COMPLETED', '#28a745', 3, '任务状态', '已完成标签', 1, 4, 'system', NOW(), 1);

-- 属性标签
INSERT INTO task_tag (tenant_id, tag_name, tag_code, tag_color, tag_type, tag_group, description, is_system, sort_order, create_by, create_time, status) VALUES
(0, '可委托', 'DELEGATABLE', '#6c757d', 4, '任务属性', '可委托他人标签', 1, 1, 'system', NOW(), 1),
(0, '需协作', 'COLLABORATIVE', '#e83e8c', 4, '任务属性', '需要协作标签', 1, 2, 'system', NOW(), 1),
(0, '独立完成', 'INDEPENDENT', '#6f42c1', 4, '任务属性', '独立完成标签', 1, 3, 'system', NOW(), 1),
(0, '需培训', 'TRAINING_REQUIRED', '#fd7e14', 4, '任务属性', '需要培训标签', 1, 4, 'system', NOW(), 1);

-- ======================= 任务模板数据 =======================

-- 法律事务模板
INSERT INTO task_template (tenant_id, template_name, template_code, template_type, category_id, title_template, description_template, estimated_hours, priority, difficulty_level, creator_id, creator_name, is_public, is_recommended, sort_order, create_by, create_time, status) VALUES
(0, '案件分析模板', 'CASE_ANALYSIS_TEMPLATE', 1, 7, '案件分析：{案件名称}', '对案件进行详细分析，包括事实梳理、法律适用、证据分析等', 8.00, 3, 3, 1, 'system', 1, 1, 1, 'system', NOW(), 1),
(0, '合同审核模板', 'CONTRACT_REVIEW_TEMPLATE', 1, 8, '合同审核：{合同名称}', '对合同条款进行法律风险审核，提出修改建议', 4.00, 2, 2, 1, 'system', 1, 1, 2, 'system', NOW(), 1),
(0, '法律文书起草模板', 'LEGAL_DOCUMENT_TEMPLATE', 1, 10, '起草{文书类型}', '根据案件情况起草相应的法律文书', 6.00, 2, 3, 1, 'system', 1, 1, 3, 'system', NOW(), 1);

-- 行政事务模板
INSERT INTO task_template (tenant_id, template_name, template_code, template_type, category_id, title_template, description_template, estimated_hours, priority, difficulty_level, creator_id, creator_name, is_public, is_recommended, sort_order, create_by, create_time, status) VALUES
(0, '会议组织模板', 'MEETING_ORGANIZATION_TEMPLATE', 1, 13, '组织{会议名称}', '安排会议场地、通知参会人员、准备会议材料', 3.00, 2, 1, 1, 'system', 1, 1, 4, 'system', NOW(), 1),
(0, '员工入职模板', 'EMPLOYEE_ONBOARDING_TEMPLATE', 2, 14, '新员工入职：{员工姓名}', '协助新员工完成入职手续和培训', 16.00, 2, 2, 1, 'system', 1, 1, 5, 'system', NOW(), 1),
(0, '设备维护模板', 'EQUIPMENT_MAINTENANCE_TEMPLATE', 3, 16, '{设备名称}维护保养', '定期维护保养设备，确保正常运行', 2.00, 1, 1, 1, 'system', 1, 0, 6, 'system', NOW(), 1);

-- 初始化完成提示
SELECT '任务管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'task_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_level' OR dict_type LIKE '%_role';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'task_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_level' OR dict_type LIKE '%_role';
SELECT CONCAT('已创建任务分类：', COUNT(*), '个') AS category_count FROM task_category WHERE tenant_id = 0;
SELECT CONCAT('已创建任务标签：', COUNT(*), '个') AS tag_count FROM task_tag WHERE tenant_id = 0;
SELECT CONCAT('已创建任务模板：', COUNT(*), '个') AS template_count FROM task_template WHERE tenant_id = 0; 