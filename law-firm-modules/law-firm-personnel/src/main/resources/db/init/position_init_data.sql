-- 注意：这是一个示例的初始化脚本，实际中应该根据具体需求进行调整
-- 初始化律师职位数据

-- 清空职位表中的律师职位数据（如果存在）
DELETE FROM `organization_position` WHERE `type` = 1;

-- 插入律师职位数据
INSERT INTO `organization_position` (`id`, `code`, `name`, `type`, `level`, `rank`, `department_id`, `description`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(101, 'LW0001', '实习律师', 1, 1, 1, NULL, '律师事务所实习律师职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(102, 'LW0002', '助理律师', 1, 2, 2, NULL, '律师事务所助理律师职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(103, 'LW0003', '专职律师', 1, 3, 3, NULL, '律师事务所专职律师职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(104, 'LW0004', '资深律师', 1, 4, 4, NULL, '律师事务所资深律师职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(105, 'LW0005', '合伙人律师', 1, 5, 5, NULL, '律师事务所合伙人律师职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(106, 'LW0006', '高级合伙人', 1, 6, 6, NULL, '律师事务所高级合伙人职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(107, 'LW0007', '执行合伙人', 1, 7, 7, NULL, '律师事务所执行合伙人职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(108, 'LW0008', '管理合伙人', 1, 8, 8, NULL, '律师事务所管理合伙人职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(109, 'LW0009', '律所主任', 1, 9, 9, NULL, '律师事务所主任职位', 1, 0, 1, NOW(), 1, NOW(), 1);

-- 插入行政职位数据
INSERT INTO `organization_position` (`id`, `code`, `name`, `type`, `level`, `rank`, `department_id`, `description`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(201, 'XZ0001', '行政专员', 2, 1, 1, NULL, '律师事务所行政专员职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(202, 'XZ0002', '人事专员', 2, 1, 1, NULL, '律师事务所人事专员职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(203, 'XZ0003', '财务专员', 2, 1, 1, NULL, '律师事务所财务专员职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(204, 'XZ0004', '行政主管', 2, 2, 2, NULL, '律师事务所行政主管职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(205, 'XZ0005', '人事主管', 2, 2, 2, NULL, '律师事务所人事主管职位', 1, 0, 1, NOW(), 1, NOW(), 1),
(206, 'XZ0006', '财务主管', 2, 2, 2, NULL, '律师事务所财务主管职位', 1, 0, 1, NOW(), 1, NOW(), 1); 