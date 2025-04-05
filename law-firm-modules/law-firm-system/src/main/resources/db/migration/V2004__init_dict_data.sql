-- 字典类型数据
INSERT INTO sys_dict (dict_name, dict_type, status, remark, create_by, update_by) VALUES 
('用户性别', 'sys_user_sex', 0, '用户性别列表', 'admin', 'admin'),
('机构类型', 'sys_org_type', 0, '机构类型列表', 'admin', 'admin'),
('系统开关', 'sys_normal_disable', 0, '系统开关列表', 'admin', 'admin'),
('任务状态', 'sys_job_status', 0, '任务状态列表', 'admin', 'admin'),
('任务分组', 'sys_job_group', 0, '任务分组列表', 'admin', 'admin'),
('系统是否', 'sys_yes_no', 0, '系统是否列表', 'admin', 'admin'),
('通知类型', 'sys_notice_type', 0, '通知类型列表', 'admin', 'admin'),
('通知状态', 'sys_notice_status', 0, '通知状态列表', 'admin', 'admin'),
('操作类型', 'sys_oper_type', 0, '操作类型列表', 'admin', 'admin'),
('操作状态', 'sys_common_status', 0, '操作状态列表', 'admin', 'admin'),
('文件存储类型', 'sys_file_storage', 0, '文件存储类型', 'admin', 'admin'),
('系统日志类型', 'sys_log_type', 0, '系统日志类型', 'admin', 'admin'),
('案件类型', 'case_type', 0, '案件类型', 'admin', 'admin'),
('案件状态', 'case_status', 0, '案件状态', 'admin', 'admin'),
('客户类型', 'client_type', 0, '客户类型', 'admin', 'admin'),
('学历水平', 'education_level', 0, '学历水平', 'admin', 'admin'),
('合同类型', 'contract_type', 0, '合同类型', 'admin', 'admin'),
('合同状态', 'contract_status', 0, '合同状态', 'admin', 'admin'),
('职位类型', 'position_type', 0, '职位类型', 'admin', 'admin'),
('专业领域', 'professional_field', 0, '专业领域', 'admin', 'admin'),
('证件类型', 'id_card_type', 0, '证件类型', 'admin', 'admin'),
('收费类型', 'fee_type', 0, '收费类型', 'admin', 'admin'),
('支付方式', 'payment_method', 0, '支付方式', 'admin', 'admin'),
('发票类型', 'invoice_type', 0, '发票类型', 'admin', 'admin'),
('发票状态', 'invoice_status', 0, '发票状态', 'admin', 'admin'),
('文档类型', 'document_type', 0, '文档类型', 'admin', 'admin'),
('文档密级', 'document_security', 0, '文档密级', 'admin', 'admin'),
('服务评价', 'service_rating', 0, '服务评价级别', 'admin', 'admin'),
('地区', 'region', 0, '地区列表', 'admin', 'admin'),
('行业', 'industry', 0, '行业分类', 'admin', 'admin'),
('会议类型', 'meeting_type', 0, '会议类型', 'admin', 'admin'),
('会议状态', 'meeting_status', 0, '会议状态', 'admin', 'admin');

-- 字典数据
-- 用户性别
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '男', '0', 1, 'icon-male', 'primary', 1, 0, '性别男', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_user_sex';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '女', '1', 2, 'icon-female', 'success', 0, 0, '性别女', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_user_sex';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '未知', '2', 3, 'icon-question', 'warning', 0, 0, '性别未知', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_user_sex';

-- 机构类型
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '总部', '1', 1, NULL, 'primary', 1, 0, '总部', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_org_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '分公司', '2', 2, NULL, 'success', 0, 0, '分公司', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_org_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '部门', '3', 3, NULL, 'info', 0, 0, '部门', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_org_type';

-- 系统开关
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '正常', '0', 1, NULL, 'primary', 1, 0, '正常状态', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_normal_disable';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '停用', '1', 2, NULL, 'danger', 0, 0, '停用状态', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_normal_disable';

-- 是否
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '是', 'Y', 1, NULL, 'primary', 1, 0, '肯定选项', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_yes_no';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '否', 'N', 2, NULL, 'danger', 0, 0, '否定选项', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_yes_no';

-- 通知类型
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '通知', '1', 1, 'icon-bell', 'warning', 1, 0, '通知', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_notice_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '公告', '2', 2, 'icon-bullhorn', 'success', 0, 0, '公告', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'sys_notice_type';

-- 案件类型
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '民事诉讼', '1', 1, NULL, 'primary', 1, 0, '民事诉讼', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '刑事辩护', '2', 2, NULL, 'danger', 0, 0, '刑事辩护', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '行政诉讼', '3', 3, NULL, 'info', 0, 0, '行政诉讼', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '非诉讼法律服务', '4', 4, NULL, 'success', 0, 0, '非诉讼法律服务', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '仲裁', '5', 5, NULL, 'warning', 0, 0, '仲裁', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '法律咨询', '6', 6, NULL, 'default', 0, 0, '法律咨询', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_type';

-- 案件状态
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '待受理', '1', 1, NULL, 'info', 1, 0, '待受理', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_status';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '进行中', '2', 2, NULL, 'primary', 0, 0, '进行中', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_status';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '暂停', '3', 3, NULL, 'warning', 0, 0, '暂停', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_status';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '结案', '4', 4, NULL, 'success', 0, 0, '结案', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_status';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '驳回', '5', 5, NULL, 'danger', 0, 0, '驳回', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'case_status';

-- 客户类型
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '个人', '1', 1, NULL, 'primary', 1, 0, '个人客户', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'client_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '企业', '2', 2, NULL, 'success', 0, 0, '企业客户', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'client_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '政府机构', '3', 3, NULL, 'info', 0, 0, '政府机构客户', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'client_type';
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, css_class, list_class, is_default, status, remark, create_by, update_by) 
SELECT id, '社会组织', '4', 4, NULL, 'warning', 0, 0, '社会组织客户', 'admin', 'admin' FROM sys_dict WHERE dict_type = 'client_type'; 