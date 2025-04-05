-- 知识管理模块初始数据 V8001

-- 知识分类初始数据
INSERT INTO `knowledge_category` (
  `id`, `name`, `code`, `parent_id`, `sort`, `description`, 
  `status`, `tenant_id`, `create_time`, `update_time`
)
VALUES
(1, '文档模板', 'DOCUMENT_TEMPLATE', 0, 1, '各类法律文档模板', 1, 1, NOW(), NOW()),
(2, '工作指南', 'WORK_GUIDE', 0, 2, '案件办理流程指南', 1, 1, NOW(), NOW()),
(3, '业务规范', 'BUSINESS_STANDARD', 0, 3, '业务流程规范', 1, 1, NOW(), NOW()),
(4, '培训资料', 'TRAINING_MATERIAL', 0, 4, '培训资料文档', 1, 1, NOW(), NOW()),
(5, '经验总结', 'EXPERIENCE_SUMMARY', 0, 5, '案件经验总结', 1, 1, NOW(), NOW()),
(6, '诉讼文书', 'LITIGATION_DOCUMENT', 1, 1, '诉讼文书模板', 1, 1, NOW(), NOW()),
(7, '合同文本', 'CONTRACT_DOCUMENT', 1, 2, '合同标准文本', 1, 1, NOW(), NOW()),
(8, '法律意见书', 'LEGAL_OPINION', 1, 3, '法律意见书模板', 1, 1, NOW(), NOW());

-- 知识标签初始数据
INSERT INTO `knowledge_tag` (
  `id`, `name`, `code`, `color`, `sort`, 
  `status`, `tenant_id`, `create_time`, `update_time`
)
VALUES
(1, '民事诉讼', 'CIVIL_LITIGATION', '#1890FF', 1, 1, 1, NOW(), NOW()),
(2, '刑事诉讼', 'CRIMINAL_LITIGATION', '#F5222D', 2, 1, 1, NOW(), NOW()),
(3, '行政诉讼', 'ADMINISTRATIVE_LITIGATION', '#FAAD14', 3, 1, 1, NOW(), NOW()),
(4, '合同纠纷', 'CONTRACT_DISPUTE', '#13C2C2', 4, 1, 1, NOW(), NOW()),
(5, '劳动纠纷', 'LABOR_DISPUTE', '#722ED1', 5, 1, 1, NOW(), NOW()),
(6, '房产纠纷', 'REAL_ESTATE_DISPUTE', '#EB2F96', 6, 1, 1, NOW(), NOW()),
(7, '婚姻家庭', 'MARRIAGE_FAMILY', '#52C41A', 7, 1, 1, NOW(), NOW()),
(8, '知识产权', 'INTELLECTUAL_PROPERTY', '#FA8C16', 8, 1, 1, NOW(), NOW()); 