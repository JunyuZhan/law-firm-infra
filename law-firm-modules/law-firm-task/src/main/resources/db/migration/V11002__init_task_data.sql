-- 任务管理模块数据初始化
-- 版本: V11002
-- 模块: task
-- 创建时间: 2023-10-01
-- 说明: 初始化任务管理模块基础数据

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 初始化任务分类
INSERT INTO work_task_category (category_name, category_code, parent_id, path, level, icon, is_system, status, deleted, create_time)
VALUES 
('法律事务', 'LEGAL_AFFAIRS', 0, '/LEGAL_AFFAIRS', 1, 'legal', true, 0, 0, NOW()),
('行政事务', 'ADMIN_AFFAIRS', 0, '/ADMIN_AFFAIRS', 1, 'admin', true, 0, 0, NOW()),
('客户服务', 'CLIENT_SERVICE', 0, '/CLIENT_SERVICE', 1, 'client', true, 0, 0, NOW()),
('财务管理', 'FINANCE_MANAGEMENT', 0, '/FINANCE_MANAGEMENT', 1, 'finance', true, 0, 0, NOW()),
('日常事务', 'DAILY_AFFAIRS', 0, '/DAILY_AFFAIRS', 1, 'daily', true, 0, 0, NOW());

-- 初始化任务分类二级分类 - 法律事务
INSERT INTO work_task_category (category_name, category_code, parent_id, path, level, icon, is_system, status, deleted, create_time)
VALUES 
('案件处理', 'CASE_HANDLING', 1, '/LEGAL_AFFAIRS/CASE_HANDLING', 2, 'case', true, 0, 0, NOW()),
('合同审核', 'CONTRACT_REVIEW', 1, '/LEGAL_AFFAIRS/CONTRACT_REVIEW', 2, 'contract', true, 0, 0, NOW()),
('法律研究', 'LEGAL_RESEARCH', 1, '/LEGAL_AFFAIRS/LEGAL_RESEARCH', 2, 'research', true, 0, 0, NOW()),
('法律文书', 'LEGAL_DOCUMENT', 1, '/LEGAL_AFFAIRS/LEGAL_DOCUMENT', 2, 'document', true, 0, 0, NOW()),
('出庭准备', 'COURT_PREPARATION', 1, '/LEGAL_AFFAIRS/COURT_PREPARATION', 2, 'court', true, 0, 0, NOW());

-- 初始化任务分类二级分类 - 行政事务
INSERT INTO work_task_category (category_name, category_code, parent_id, path, level, icon, is_system, status, deleted, create_time)
VALUES 
('会议筹备', 'MEETING_PREPARATION', 2, '/ADMIN_AFFAIRS/MEETING_PREPARATION', 2, 'meeting', true, 0, 0, NOW()),
('员工管理', 'EMPLOYEE_MANAGEMENT', 2, '/ADMIN_AFFAIRS/EMPLOYEE_MANAGEMENT', 2, 'employee', true, 0, 0, NOW()),
('办公用品', 'OFFICE_SUPPLIES', 2, '/ADMIN_AFFAIRS/OFFICE_SUPPLIES', 2, 'supplies', true, 0, 0, NOW()),
('设备维护', 'EQUIPMENT_MAINTENANCE', 2, '/ADMIN_AFFAIRS/EQUIPMENT_MAINTENANCE', 2, 'equipment', true, 0, 0, NOW());

-- 初始化任务标签
INSERT INTO work_task_tag (tag_name, tag_color, tag_type, status, deleted, create_time)
VALUES 
('紧急', '#FF0000', 0, 0, 0, NOW()),
('重要', '#FFA500', 0, 0, 0, NOW()),
('长期', '#008000', 0, 0, 0, NOW()),
('合作', '#0000FF', 0, 0, 0, NOW()),
('研究', '#800080', 0, 0, 0, NOW()),
('审核', '#A52A2A', 1, 0, 0, NOW()),
('起草', '#2E8B57', 1, 0, 0, NOW()),
('咨询', '#4682B4', 1, 0, 0, NOW()),
('会议', '#D2691E', 2, 0, 0, NOW()),
('培训', '#556B2F', 2, 0, 0, NOW());

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
