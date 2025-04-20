-- Client模块初始数据
-- 版本: V4002
-- 模块: client
-- 创建时间: 2023-08-01
-- 说明: 初始化客户管理模块相关基础数据

-- 客户管理相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '客户类型', 'client_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '客户等级', 'client_level', 0, 1, NOW(), 'system'),
(NULL, NULL, '客户来源', 'client_source', 0, 1, NOW(), 'system'),
(NULL, NULL, '客户状态', 'client_status', 0, 1, NOW(), 'system'),
(NULL, NULL, '联系人类型', 'contact_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '地址类型', 'address_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '跟进类型', 'follow_up_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '当事人类型', 'party_type', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 客户管理相关字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 客户类型
(NULL, NULL, 'client_type', '个人客户', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'client_type', '企业客户', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_type', '政府机构', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_type', '社会组织', '4', 4, 0, 0, NOW(), 'system'),

-- 客户等级
(NULL, NULL, 'client_level', '普通客户', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'client_level', '重要客户', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_level', 'VIP客户', '3', 3, 0, 0, NOW(), 'system'),

-- 客户来源
(NULL, NULL, 'client_source', '主动咨询', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'client_source', '同行推荐', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_source', '客户推荐', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_source', '广告宣传', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'client_source', '老客户', '5', 5, 0, 0, NOW(), 'system'),

-- 客户状态
(NULL, NULL, 'client_status', '正常', '0', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'client_status', '停用', '1', 2, 0, 0, NOW(), 'system'),

-- 联系人类型
(NULL, NULL, 'contact_type', '法定代表人', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'contact_type', '业务联系人', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'contact_type', '财务联系人', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'contact_type', '其他', '4', 4, 0, 0, NOW(), 'system'),

-- 地址类型
(NULL, NULL, 'address_type', '注册地址', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'address_type', '办公地址', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'address_type', '收件地址', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'address_type', '开票地址', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'address_type', '其他', '99', 5, 0, 0, NOW(), 'system'),

-- 跟进类型
(NULL, NULL, 'follow_up_type', '电话沟通', 'PHONE', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'follow_up_type', '邮件往来', 'EMAIL', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'follow_up_type', '现场拜访', 'VISIT', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'follow_up_type', '线上会议', 'ONLINE', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'follow_up_type', '文件递送', 'DOCUMENT', 5, 0, 0, NOW(), 'system'),
(NULL, NULL, 'follow_up_type', '其他', 'OTHER', 6, 0, 0, NOW(), 'system'),

-- 当事人类型（案件）
(NULL, NULL, 'party_type', '原告', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'party_type', '被告', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'party_type', '第三人', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'party_type', '上诉人', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'party_type', '被上诉人', '5', 5, 0, 0, NOW(), 'system'),
(NULL, NULL, 'party_type', '申请人', '6', 6, 0, 0, NOW(), 'system'),
(NULL, NULL, 'party_type', '被申请人', '7', 7, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加客户基础分类
INSERT INTO client_category (tenant_id, tenant_code, category_name, category_code, level, parent_id, category_path, sort_weight, status, allow_select, is_system, create_time, create_by)
VALUES
(NULL, NULL, '客户分类', 'ROOT', 1, 0, '/ROOT', 1, 1, 0, 1, NOW(), 'system'),
(NULL, NULL, '个人客户', 'INDIVIDUAL', 2, 1, '/ROOT/INDIVIDUAL', 1, 1, 1, 1, NOW(), 'system'),
(NULL, NULL, '企业客户', 'ENTERPRISE', 2, 1, '/ROOT/ENTERPRISE', 2, 1, 1, 1, NOW(), 'system'),
(NULL, NULL, '政府机构', 'GOVERNMENT', 2, 1, '/ROOT/GOVERNMENT', 3, 1, 1, 1, NOW(), 'system'),
(NULL, NULL, '社会组织', 'ORGANIZATION', 2, 1, '/ROOT/ORGANIZATION', 4, 1, 1, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化标签
INSERT INTO client_tag (tenant_id, tenant_code, tag_name, color, tag_type, sort, status, create_time, create_by)
VALUES
(NULL, NULL, '重点客户', '#FF0000', 'LEVEL', 1, 1, NOW(), 'system'),
(NULL, NULL, '长期合作', '#00FF00', 'COOPERATION', 2, 1, NOW(), 'system'),
(NULL, NULL, '潜在客户', '#0000FF', 'POTENTIAL', 3, 1, NOW(), 'system'),
(NULL, NULL, '待开发', '#FFA500', 'POTENTIAL', 4, 1, NOW(), 'system'),
(NULL, NULL, '高价值', '#800080', 'VALUE', 5, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加客户管理相关菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)    
SELECT NULL, NULL, '客户管理', 'client', 0, 0, '/client', 'peoples', 
NULL, 4, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'client');