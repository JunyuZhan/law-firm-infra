-- 初始化任务标签数据
INSERT INTO `task_tag` (`id`, `tag_name`, `tag_color`, `deleted`, `create_time`, `create_by`)
VALUES
(1, '紧急', '#FF0000', 0, NOW(), 'system'),
(2, '重要', '#FFA500', 0, NOW(), 'system'),
(3, '会议', '#008000', 0, NOW(), 'system'),
(4, '审核', '#0000FF', 0, NOW(), 'system'),
(5, '客户沟通', '#800080', 0, NOW(), 'system'),
(6, '法院事务', '#4682B4', 0, NOW(), 'system'),
(7, '文档准备', '#228B22', 0, NOW(), 'system'),
(8, '调研', '#DAA520', 0, NOW(), 'system'),
(9, '草拟', '#20B2AA', 0, NOW(), 'system'),
(10, '复核', '#B22222', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE `update_time` = NOW();

-- 初始化任务类型字典数据
INSERT INTO `sys_dict_item` (`dict_id`, `item_value`, `item_label`, `item_order`, `status`, `created_time`, `updated_time`, `deleted`)
VALUES 
-- 任务类型（假设dict_id为8表示紧急程度字典）
(8, 'normal', '普通', 1, 1, NOW(), NOW(), 0),
(8, 'urgent', '紧急', 2, 1, NOW(), NOW(), 0),
(8, 'critical', '非常紧急', 3, 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `updated_time` = NOW(); 