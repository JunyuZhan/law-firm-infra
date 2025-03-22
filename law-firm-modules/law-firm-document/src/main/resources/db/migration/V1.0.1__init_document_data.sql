-- 初始化文档分类
INSERT INTO `doc_category` (`parent_id`, `name`, `code`, `sort`, `description`, `create_by`, `create_time`)
VALUES 
(NULL, '合同文档', 'CONTRACT', 1, '各类合同及协议文档', 1, NOW()),
(NULL, '法律意见书', 'LEGAL_OPINION', 2, '法律意见书及分析报告', 1, NOW()),
(NULL, '案件材料', 'CASE_MATERIAL', 3, '诉讼案件相关材料', 1, NOW()),
(NULL, '内部文档', 'INTERNAL', 4, '内部使用的文档资料', 1, NOW()),
(NULL, '模板文档', 'TEMPLATE', 5, '各类文档模板', 1, NOW());

-- 使用变量存储分类ID
SET @contract_id = (SELECT id FROM `doc_category` WHERE `code` = 'CONTRACT' LIMIT 1);
SET @legal_opinion_id = (SELECT id FROM `doc_category` WHERE `code` = 'LEGAL_OPINION' LIMIT 1);
SET @case_material_id = (SELECT id FROM `doc_category` WHERE `code` = 'CASE_MATERIAL' LIMIT 1);
SET @template_id = (SELECT id FROM `doc_category` WHERE `code` = 'TEMPLATE' LIMIT 1);

-- 初始化子分类
INSERT INTO `doc_category` (`parent_id`, `name`, `code`, `sort`, `description`, `create_by`, `create_time`)
VALUES 
-- 合同文档子分类
(@contract_id, '劳动合同', 'LABOR_CONTRACT', 1, '劳动雇佣相关合同', 1, NOW()),
(@contract_id, '买卖合同', 'SALES_CONTRACT', 2, '买卖交易相关合同', 1, NOW()),
(@contract_id, '服务合同', 'SERVICE_CONTRACT', 3, '服务提供相关合同', 1, NOW()),

-- 法律意见书子分类
(@legal_opinion_id, '公司法', 'CORPORATE_LAW', 1, '公司法相关法律意见', 1, NOW()),
(@legal_opinion_id, '知识产权', 'IP_LAW', 2, '知识产权相关法律意见', 1, NOW()),

-- 案件材料子分类
(@case_material_id, '起诉材料', 'LITIGATION', 1, '诉讼起诉相关材料', 1, NOW()),
(@case_material_id, '答辩材料', 'DEFENSE', 2, '答辩相关材料', 1, NOW()),
(@case_material_id, '证据材料', 'EVIDENCE', 3, '证据相关材料', 1, NOW());

-- 初始化文档标签
INSERT INTO `doc_tag` (`name`, `color`, `create_by`, `create_time`)
VALUES 
('重要', '#ff4d4f', 1, NOW()),
('紧急', '#f5222d', 1, NOW()),
('草稿', '#faad14', 1, NOW()),
('已审核', '#52c41a', 1, NOW()),
('已存档', '#1890ff', 1, NOW()),
('保密', '#722ed1', 1, NOW()),
('参考', '#fa8c16', 1, NOW()),
('公开', '#13c2c2', 1, NOW()),
('内部', '#eb2f96', 1, NOW());

-- 初始化模板文档
INSERT INTO `doc_document` 
(`category_id`, `file_name`, `file_type`, `file_size`, `storage_path`, `storage_type`, 
`description`, `keywords`, `status`, `version`, `is_template`, `template_code`, 
`create_by`, `create_time`, `is_deleted`)
VALUES 
-- 合同模板
(@template_id, '标准服务合同模板', 'ftl', 8192, 'templates/document/contract_template.ftl', 'LOCAL', 
'通用的服务合同模板，适用于各类服务提供场景', '合同,模板,服务', 1, 1, 1, 'TPL_CONTRACT_SERVICE', 
1, NOW(), 0),

-- 法律意见书模板
(@template_id, '法律意见书模板', 'ftl', 7168, 'templates/document/legal_opinion_template.ftl', 'LOCAL', 
'标准法律意见书模板，适用于各类法律意见出具场景', '法律意见书,模板', 1, 1, 1, 'TPL_LEGAL_OPINION', 
1, NOW(), 0); 