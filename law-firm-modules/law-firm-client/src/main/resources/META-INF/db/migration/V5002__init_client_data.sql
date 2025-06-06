-- 客户管理模块基础数据
-- 版本: V5002
-- 模块: 客户管理模块 (V5000-V5999)
-- 创建时间: 2023-06-01
-- 说明: 客户管理功能的基础数据初始化
-- 包括：客户相关字典数据、基础分类、标签配置

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 字典类型初始化 =======================

INSERT INTO sys_dict_type (tenant_id, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(0, '客户类型', 'client_type', 1, NOW(), 'system', '客户类型字典'),
(0, '客户等级', 'client_level', 1, NOW(), 'system', '客户等级字典'),
(0, '客户来源', 'client_source', 1, NOW(), 'system', '客户来源字典'),
(0, '客户状态', 'client_status', 1, NOW(), 'system', '客户状态字典'),
(0, '证件类型', 'client_id_type', 1, NOW(), 'system', '客户证件类型字典'),
(0, '信用等级', 'client_credit_level', 1, NOW(), 'system', '客户信用等级字典'),
(0, '联系人类型', 'client_contact_type', 1, NOW(), 'system', '客户联系人类型字典'),
(0, '地址类型', 'client_address_type', 1, NOW(), 'system', '客户地址类型字典'),
(0, '关系类型', 'client_relation_type', 1, NOW(), 'system', '客户关系类型字典'),
(0, '跟进类型', 'client_follow_type', 1, NOW(), 'system', '客户跟进类型字典'),
(0, '跟进结果', 'client_follow_result', 1, NOW(), 'system', '客户跟进结果字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 字典数据初始化 =======================

INSERT INTO sys_dict_data (tenant_id, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 客户类型
(0, 'client_type', '个人客户', '1', 1, 1, NOW(), 'system', '客户类型 - 个人客户'),
(0, 'client_type', '企业客户', '2', 2, 1, NOW(), 'system', '客户类型 - 企业客户'),
(0, 'client_type', '政府机构', '3', 3, 1, NOW(), 'system', '客户类型 - 政府机构'),
(0, 'client_type', '社会组织', '4', 4, 1, NOW(), 'system', '客户类型 - 社会组织'),

-- 客户等级
(0, 'client_level', '普通客户', '1', 1, 1, NOW(), 'system', '客户等级 - 普通客户'),
(0, 'client_level', '重要客户', '2', 2, 1, NOW(), 'system', '客户等级 - 重要客户'),
(0, 'client_level', 'VIP客户', '3', 3, 1, NOW(), 'system', '客户等级 - VIP客户'),

-- 客户来源
(0, 'client_source', '主动咨询', '1', 1, 1, NOW(), 'system', '客户来源 - 主动咨询'),
(0, 'client_source', '同行推荐', '2', 2, 1, NOW(), 'system', '客户来源 - 同行推荐'),
(0, 'client_source', '客户推荐', '3', 3, 1, NOW(), 'system', '客户来源 - 客户推荐'),
(0, 'client_source', '广告宣传', '4', 4, 1, NOW(), 'system', '客户来源 - 广告宣传'),
(0, 'client_source', '老客户', '5', 5, 1, NOW(), 'system', '客户来源 - 老客户'),

-- 客户状态
(0, 'client_status', '正常', '1', 1, 1, NOW(), 'system', '客户状态 - 正常'),
(0, 'client_status', '禁用', '0', 2, 1, NOW(), 'system', '客户状态 - 禁用'),

-- 证件类型
(0, 'client_id_type', '身份证', '1', 1, 1, NOW(), 'system', '证件类型 - 身份证'),
(0, 'client_id_type', '护照', '2', 2, 1, NOW(), 'system', '证件类型 - 护照'),
(0, 'client_id_type', '营业执照', '3', 3, 1, NOW(), 'system', '证件类型 - 营业执照'),
(0, 'client_id_type', '其他', '4', 4, 1, NOW(), 'system', '证件类型 - 其他'),

-- 信用等级
(0, 'client_credit_level', 'A级', 'A', 1, 1, NOW(), 'system', '信用等级 - A级'),
(0, 'client_credit_level', 'B级', 'B', 2, 1, NOW(), 'system', '信用等级 - B级'),
(0, 'client_credit_level', 'C级', 'C', 3, 1, NOW(), 'system', '信用等级 - C级'),
(0, 'client_credit_level', 'D级', 'D', 4, 1, NOW(), 'system', '信用等级 - D级'),

-- 联系人类型
(0, 'client_contact_type', '法定代表人', '1', 1, 1, NOW(), 'system', '联系人类型 - 法定代表人'),
(0, 'client_contact_type', '业务联系人', '2', 2, 1, NOW(), 'system', '联系人类型 - 业务联系人'),
(0, 'client_contact_type', '财务联系人', '3', 3, 1, NOW(), 'system', '联系人类型 - 财务联系人'),
(0, 'client_contact_type', '其他', '4', 4, 1, NOW(), 'system', '联系人类型 - 其他'),

-- 地址类型
(0, 'client_address_type', '注册地址', '1', 1, 1, NOW(), 'system', '地址类型 - 注册地址'),
(0, 'client_address_type', '办公地址', '2', 2, 1, NOW(), 'system', '地址类型 - 办公地址'),
(0, 'client_address_type', '收件地址', '3', 3, 1, NOW(), 'system', '地址类型 - 收件地址'),
(0, 'client_address_type', '开票地址', '4', 4, 1, NOW(), 'system', '地址类型 - 开票地址'),
(0, 'client_address_type', '其他', '5', 5, 1, NOW(), 'system', '地址类型 - 其他'),

-- 关系类型
(0, 'client_relation_type', '推荐关系', '1', 1, 1, NOW(), 'system', '关系类型 - 推荐关系'),
(0, 'client_relation_type', '合作关系', '2', 2, 1, NOW(), 'system', '关系类型 - 合作关系'),
(0, 'client_relation_type', '竞争关系', '3', 3, 1, NOW(), 'system', '关系类型 - 竞争关系'),
(0, 'client_relation_type', '上下游关系', '4', 4, 1, NOW(), 'system', '关系类型 - 上下游关系'),
(0, 'client_relation_type', '其他', '5', 5, 1, NOW(), 'system', '关系类型 - 其他'),

-- 跟进类型
(0, 'client_follow_type', '电话跟进', '1', 1, 1, NOW(), 'system', '跟进类型 - 电话跟进'),
(0, 'client_follow_type', '邮件跟进', '2', 2, 1, NOW(), 'system', '跟进类型 - 邮件跟进'),
(0, 'client_follow_type', '实地拜访', '3', 3, 1, NOW(), 'system', '跟进类型 - 实地拜访'),
(0, 'client_follow_type', '会议沟通', '4', 4, 1, NOW(), 'system', '跟进类型 - 会议沟通'),
(0, 'client_follow_type', '其他', '5', 5, 1, NOW(), 'system', '跟进类型 - 其他'),

-- 跟进结果
(0, 'client_follow_result', '成功', '1', 1, 1, NOW(), 'system', '跟进结果 - 成功'),
(0, 'client_follow_result', '失败', '2', 2, 1, NOW(), 'system', '跟进结果 - 失败'),
(0, 'client_follow_result', '待定', '3', 3, 1, NOW(), 'system', '跟进结果 - 待定')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 客户分类初始化 =======================

INSERT INTO client_category (tenant_id, category_name, category_code, level, parent_id, category_path, description, sort, status, allow_select, is_system, create_time, create_by)
VALUES
(0, '客户分类', 'ROOT', 1, 0, '/ROOT', '客户分类根节点', 1, 1, 0, 1, NOW(), 'system'),
(0, '个人客户', 'INDIVIDUAL', 2, 1, '/ROOT/INDIVIDUAL', '个人客户分类', 1, 1, 1, 1, NOW(), 'system'),
(0, '企业客户', 'ENTERPRISE', 2, 1, '/ROOT/ENTERPRISE', '企业客户分类', 2, 1, 1, 1, NOW(), 'system'),
(0, '政府机构', 'GOVERNMENT', 2, 1, '/ROOT/GOVERNMENT', '政府机构分类', 3, 1, 1, 1, NOW(), 'system'),
(0, '社会组织', 'ORGANIZATION', 2, 1, '/ROOT/ORGANIZATION', '社会组织分类', 4, 1, 1, 1, NOW(), 'system'),
-- 企业客户子分类
(0, '大型企业', 'LARGE_ENTERPRISE', 3, 3, '/ROOT/ENTERPRISE/LARGE', '大型企业客户', 1, 1, 1, 1, NOW(), 'system'),
(0, '中小企业', 'SME', 3, 3, '/ROOT/ENTERPRISE/SME', '中小企业客户', 2, 1, 1, 1, NOW(), 'system'),
(0, '初创企业', 'STARTUP', 3, 3, '/ROOT/ENTERPRISE/STARTUP', '初创企业客户', 3, 1, 1, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE category_name = VALUES(category_name);

-- ======================= 客户标签初始化 =======================

INSERT INTO client_tag (tenant_id, tag_name, tag_code, color, tag_type, description, sort, is_system, status, create_time, create_by)
VALUES
(0, '重点客户', 'VIP_CLIENT', '#FF0000', 'LEVEL', '重点客户标签', 1, 1, 1, NOW(), 'system'),
(0, '长期合作', 'LONG_TERM', '#00FF00', 'COOPERATION', '长期合作客户', 2, 1, 1, NOW(), 'system'),
(0, '潜在客户', 'POTENTIAL', '#0000FF', 'POTENTIAL', '潜在客户标签', 3, 1, 1, NOW(), 'system'),
(0, '待开发', 'TO_DEVELOP', '#FFA500', 'POTENTIAL', '待开发客户', 4, 1, 1, NOW(), 'system'),
(0, '高价值', 'HIGH_VALUE', '#800080', 'VALUE', '高价值客户', 5, 1, 1, NOW(), 'system'),
(0, '优质客户', 'QUALITY', '#008000', 'LEVEL', '优质客户标签', 6, 1, 1, NOW(), 'system'),
(0, '战略客户', 'STRATEGIC', '#FF1493', 'LEVEL', '战略客户标签', 7, 1, 1, NOW(), 'system'),
(0, '信用良好', 'GOOD_CREDIT', '#32CD32', 'CREDIT', '信用良好客户', 8, 1, 1, NOW(), 'system'),
(0, '频繁联系', 'FREQUENT', '#FF69B4', 'FREQUENCY', '频繁联系客户', 9, 1, 1, NOW(), 'system'),
(0, '新客户', 'NEW_CLIENT', '#FFD700', 'STATUS', '新客户标签', 10, 1, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE tag_name = VALUES(tag_name); 