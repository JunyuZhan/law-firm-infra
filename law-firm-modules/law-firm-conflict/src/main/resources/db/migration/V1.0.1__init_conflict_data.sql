-- 冲突检查模块初始数据

-- 初始化冲突检查规则
INSERT INTO `conflict_rule` 
(`rule_name`, `rule_code`, `description`, `status`, `priority`, `rule_type`, `create_time`, `create_by`, `is_deleted`)
VALUES
-- 客户冲突规则
('相同当事人对立利益', 'CLIENT_OPPOSITE_INTEREST', '检查是否代理过与当前客户有对立利益关系的当事人', 1, 10, 'client', NOW(), 1, 0),
('法院回避关系', 'CLIENT_COURT_AVOIDANCE', '检查是否与法院审判人员存在亲属关系', 1, 9, 'client', NOW(), 1, 0),
('利益冲突方当事人', 'CLIENT_CONFLICT_PARTY', '检查是否曾经代理过与当前客户有利益冲突的当事人', 1, 8, 'client', NOW(), 1, 0),
('政府机构冲突', 'CLIENT_GOV_CONFLICT', '政府机构客户特殊冲突检查', 1, 7, 'client', NOW(), 1, 0),

-- 案件冲突规则
('案件标的冲突', 'CASE_SUBJECT_CONFLICT', '检查案件标的是否与已有案件存在冲突', 1, 10, 'case', NOW(), 1, 0),
('诉讼对方当事人', 'CASE_OPPOSITE_PARTY', '检查是否曾经代理过本案件对方当事人', 1, 9, 'case', NOW(), 1, 0),
('关联案件冲突', 'CASE_RELATED_CONFLICT', '检查是否存在与本案件相关联的案件冲突', 1, 8, 'case', NOW(), 1, 0),
('案件承办律师冲突', 'CASE_LAWYER_CONFLICT', '检查案件承办律师是否存在利益冲突', 1, 7, 'case', NOW(), 1, 0);

-- 初始化规则参数
INSERT INTO `conflict_rule_param`
(`rule_id`, `param_name`, `param_key`, `param_value`, `param_type`, `is_required`, `description`, `create_time`)
VALUES
-- 客户对立利益规则参数
((SELECT id FROM conflict_rule WHERE rule_code = 'CLIENT_OPPOSITE_INTEREST'), '检查年限', 'check_years', '3', 'number', 1, '向前追溯检查的年限', NOW()),
((SELECT id FROM conflict_rule WHERE rule_code = 'CLIENT_OPPOSITE_INTEREST'), '是否包含关联公司', 'include_related', 'true', 'boolean', 0, '是否检查关联公司', NOW()),

-- 法院回避关系参数
((SELECT id FROM conflict_rule WHERE rule_code = 'CLIENT_COURT_AVOIDANCE'), '亲属关系等级', 'relative_level', '3', 'number', 1, '检查的亲属关系等级(直系亲属和三代以内旁系亲属)', NOW()),

-- 案件标的冲突参数
((SELECT id FROM conflict_rule WHERE rule_code = 'CASE_SUBJECT_CONFLICT'), '标的匹配度', 'subject_match_rate', '0.8', 'number', 1, '标的匹配度阈值(0-1)', NOW()),
((SELECT id FROM conflict_rule WHERE rule_code = 'CASE_SUBJECT_CONFLICT'), '检查年限', 'check_years', '5', 'number', 1, '向前追溯检查的年限', NOW());

-- 初始化冲突检查相关字典数据
INSERT INTO `sys_dict` (`dict_name`, `dict_code`, `description`, `created_time`, `updated_time`, `deleted`)
VALUES ('冲突检查级别', 'conflict_level', '冲突检查的严重程度级别', NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `updated_time` = NOW();

-- 获取字典ID
SET @dict_id = (SELECT id FROM `sys_dict` WHERE `dict_code` = 'conflict_level' LIMIT 1);

-- 初始化冲突级别字典项
INSERT INTO `sys_dict_item` (`dict_id`, `item_value`, `item_label`, `item_order`, `status`, `created_time`, `updated_time`, `deleted`)
VALUES 
(@dict_id, 'high', '高风险', 1, 1, NOW(), NOW(), 0),
(@dict_id, 'medium', '中等风险', 2, 1, NOW(), NOW(), 0),
(@dict_id, 'low', '低风险', 3, 1, NOW(), NOW(), 0),
(@dict_id, 'warning', '提醒', 4, 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE `updated_time` = NOW(); 