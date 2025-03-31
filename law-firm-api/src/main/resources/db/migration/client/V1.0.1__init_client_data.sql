-- 初始化客户标签数据
INSERT INTO `client_tag` (`id`, `tag_name`, `tag_type`, `tag_color`, `deleted`, `create_time`, `create_by`)
VALUES
(1, '重要客户', 'importance', '#FF0000', 0, NOW(), 'system'),
(2, '潜在客户', 'status', '#FFA500', 0, NOW(), 'system'),
(3, '长期合作', 'relation', '#008000', 0, NOW(), 'system'),
(4, '新客户', 'status', '#0000FF', 0, NOW(), 'system'),
(5, '政府机构', 'type', '#800080', 0, NOW(), 'system'),
(6, '上市公司', 'type', '#4682B4', 0, NOW(), 'system'),
(7, '民营企业', 'type', '#228B22', 0, NOW(), 'system'),
(8, '外资企业', 'type', '#DAA520', 0, NOW(), 'system'),
(9, '个人客户', 'type', '#20B2AA', 0, NOW(), 'system'),
(10, '高净值', 'feature', '#B22222', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 初始化数据字典（客户来源）
INSERT INTO `sys_dict_item` (`dict_id`, `item_value`, `item_label`, `item_order`, `status`, `created_time`, `updated_time`, `deleted`)
VALUES 
-- 客户来源（假设dict_id为3表示客户类型字典）
(3, 'referral', '转介绍', 1, 1, NOW(), NOW(), 0),
(3, 'marketing', '市场营销', 2, 1, NOW(), NOW(), 0),
(3, 'website', '网站', 3, 1, NOW(), NOW(), 0),
(3, 'social_media', '社交媒体', 4, 1, NOW(), NOW(), 0),
(3, 'event', '活动', 5, 1, NOW(), NOW(), 0),
(3, 'cold_call', '陌生拜访', 6, 1, NOW(), NOW(), 0),
(3, 'partner', '合作伙伴', 7, 1, NOW(), NOW(), 0),
(3, 'other', '其他', 8, 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `updated_time` = NOW(); 