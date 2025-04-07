-- 初始化客户分类数据
INSERT INTO client_category (id, category_name, category_code, parent_id, level, sort_order, description, create_by, create_time, deleted)
VALUES
(1, '企业客户', 'COMPANY', 0, 1, 1, '企业类客户', 'admin', NOW(), 0),
(2, '个人客户', 'INDIVIDUAL', 0, 1, 2, '个人类客户', 'admin', NOW(), 0),
(3, '政府机构', 'GOVERNMENT', 0, 1, 3, '政府机构类客户', 'admin', NOW(), 0),
(11, '国有企业', 'STATE_OWNED', 1, 2, 1, '国有企业客户', 'admin', NOW(), 0),
(12, '民营企业', 'PRIVATE', 1, 2, 2, '民营企业客户', 'admin', NOW(), 0),
(13, '外资企业', 'FOREIGN', 1, 2, 3, '外资企业客户', 'admin', NOW(), 0),
(14, '上市公司', 'LISTED', 1, 2, 4, '上市公司客户', 'admin', NOW(), 0),
(21, 'VIP客户', 'VIP', 2, 2, 1, '重要个人客户', 'admin', NOW(), 0),
(22, '普通客户', 'NORMAL', 2, 2, 2, '普通个人客户', 'admin', NOW(), 0),
(31, '中央机构', 'CENTRAL', 3, 2, 1, '中央政府机构', 'admin', NOW(), 0),
(32, '地方机构', 'LOCAL', 3, 2, 2, '地方政府机构', 'admin', NOW(), 0),
(33, '事业单位', 'INSTITUTION', 3, 2, 3, '事业单位', 'admin', NOW(), 0);

-- 初始化客户来源数据
INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '转介绍', 'REFERRAL', 1, 0, '客户转介绍', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '市场营销', 'MARKETING', 2, 0, '市场营销活动', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '网站', 'WEBSITE', 3, 0, '网站访问', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '社交媒体', 'SOCIAL_MEDIA', 4, 0, '社交媒体渠道', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '活动', 'EVENT', 5, 0, '线下活动', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

INSERT INTO sys_dict_item (dict_id, dict_label, dict_value, dict_sort, status, remark, create_by, create_time, deleted) 
SELECT id, '陌生拜访', 'COLD_CALL', 6, 0, '陌生拜访', 'admin', NOW(), 0 FROM sys_dict WHERE dict_type = 'client_source';

-- 初始化示例客户数据
INSERT INTO client_info (client_name, client_code, client_type, unified_social_credit_code, industry, 
                        address, contact_person, contact_phone, principal_id, principal_name, 
                        category_id, source, status, importance, create_by, create_time)
VALUES 
('北京XXX科技有限公司', 'CL20230001', 'COMPANY', '91110000123456789X', '信息技术',
 '北京市海淀区中关村大街1号', '张三', '13800138001', 1, '王律师',
 11, 'REFERRAL', 'NORMAL', 'HIGH', 'admin', NOW()),
 
('上海ABC贸易有限公司', 'CL20230002', 'COMPANY', '91310000987654321Y', '贸易',
 '上海市浦东新区陆家嘴1号', '李四', '13900139002', 2, '李律师',
 12, 'WEBSITE', 'NORMAL', 'NORMAL', 'admin', NOW()),
 
('广州市政府法律事务办公室', 'CL20230003', 'GOVERNMENT', NULL, '政府部门',
 '广州市越秀区府前路1号', '陈五', '13700137003', 1, '王律师',
 31, 'EVENT', 'NORMAL', 'VIP', 'admin', NOW()),
 
('赵明', 'CL20230004', 'INDIVIDUAL', NULL, NULL,
 '深圳市南山区科技园', '赵明', '13600136004', 3, '张律师',
 21, 'MARKETING', 'NORMAL', 'NORMAL', 'admin', NOW()); 