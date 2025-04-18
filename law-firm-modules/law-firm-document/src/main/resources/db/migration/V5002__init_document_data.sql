-- 文档模块初始化数据
-- 版本: V5002
-- 模块: document
-- 创建时间: 2023-09-01
-- 说明: 创建文档管理模块初始化数据

-- 文档分类初始化
INSERT INTO doc_category (tenant_id, tenant_code, category_name, category_code, parent_id, level, sort_no, description, is_enabled, status, create_time, create_by, remark)
VALUES 
(NULL, NULL, '案件文档', 'CASE_DOC', 0, 1, 1, '与案件相关的所有文档', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '起诉文书', 'CASE_COMPLAINT', 1, 2, 1, '案件起诉相关文书', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '答辩文书', 'CASE_DEFENSE', 1, 2, 2, '案件答辩相关文书', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '证据材料', 'CASE_EVIDENCE', 1, 2, 3, '案件证据相关材料', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '判决文书', 'CASE_JUDGMENT', 1, 2, 4, '案件判决相关文书', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '合同文档', 'CONTRACT_DOC', 0, 1, 2, '与合同相关的所有文档', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '服务合同', 'CONTRACT_SERVICE', 6, 2, 1, '法律服务合同', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '聘用合同', 'CONTRACT_EMPLOYMENT', 6, 2, 2, '律师聘用合同', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '保密协议', 'CONTRACT_NDA', 6, 2, 3, '保密协议', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '知识文章', 'KNOWLEDGE_ARTICLE', 0, 1, 3, '知识库相关文章', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '法律法规', 'KNOWLEDGE_LAW', 10, 2, 1, '法律法规文章', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '案例分析', 'KNOWLEDGE_CASE_ANALYSIS', 10, 2, 2, '案例分析文章', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '业务指南', 'KNOWLEDGE_GUIDE', 10, 2, 3, '业务指南文章', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '模板文档', 'TEMPLATE_DOC', 0, 1, 4, '各类文档模板', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '合同模板', 'TEMPLATE_CONTRACT', 14, 2, 1, '各类合同模板', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '诉讼文书模板', 'TEMPLATE_LITIGATION', 14, 2, 2, '诉讼文书模板', TRUE, 0, NOW(), 'system', '系统预置分类'),
(NULL, NULL, '函件模板', 'TEMPLATE_LETTER', 14, 2, 3, '函件模板', TRUE, 0, NOW(), 'system', '系统预置分类')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 文档标签初始化
INSERT INTO doc_tag (tenant_id, tenant_code, tag_name, tag_code, tag_type, color, description, is_system, is_enabled, status, create_time, create_by, remark)
VALUES 
(NULL, NULL, '重要', 'IMPORTANT', 'COMMON', '#FF0000', '重要文档标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '紧急', 'URGENT', 'COMMON', '#FF6600', '紧急文档标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '机密', 'CONFIDENTIAL', 'SECURITY', '#000000', '机密文档标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '草稿', 'DRAFT', 'STATUS', '#AAAAAA', '草稿状态标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '已归档', 'ARCHIVED', 'STATUS', '#006600', '已归档标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '民事', 'CIVIL', 'CASE_TYPE', '#3399FF', '民事案件标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '刑事', 'CRIMINAL', 'CASE_TYPE', '#990000', '刑事案件标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '行政', 'ADMINISTRATIVE', 'CASE_TYPE', '#666699', '行政案件标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '公司法', 'COMPANY_LAW', 'LEGAL_FIELD', '#009966', '公司法标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '合同法', 'CONTRACT_LAW', 'LEGAL_FIELD', '#999900', '合同法标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '知识产权', 'INTELLECTUAL_PROPERTY', 'LEGAL_FIELD', '#FF9900', '知识产权标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签'),
(NULL, NULL, '劳动法', 'LABOR_LAW', 'LEGAL_FIELD', '#CC6600', '劳动法标签', TRUE, TRUE, 0, NOW(), 'system', '系统预置标签')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 文档模板初始化
INSERT INTO doc_template (tenant_id, tenant_code, title, template_code, doc_type, category_id, template_type, description, tags, apply_scope, author, is_public, status, create_time, create_by, remark)
VALUES 
(NULL, NULL, '法律顾问服务合同', 'TPL_CONTRACT_LEGAL_ADVISOR', 'CONTRACT', 15, 'WORD', '律师事务所与客户签订的法律顾问服务合同模板', '合同,服务', '律师事务所与客户', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '民事起诉状', 'TPL_LITIGATION_CIVIL_COMPLAINT', 'CASE', 16, 'WORD', '民事案件起诉状模板', '诉讼,民事', '民事诉讼', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '民事答辩状', 'TPL_LITIGATION_CIVIL_DEFENSE', 'CASE', 16, 'WORD', '民事案件答辩状模板', '诉讼,民事', '民事诉讼', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '授权委托书', 'TPL_LETTER_AUTHORIZATION', 'COMMON', 17, 'WORD', '授权委托书模板', '委托,授权', '通用', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '保密协议', 'TPL_CONTRACT_NDA', 'CONTRACT', 15, 'WORD', '保密协议模板', '合同,保密', '通用', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '法律意见书', 'TPL_LEGAL_OPINION', 'KNOWLEDGE', 16, 'WORD', '法律意见书模板', '意见,分析', '通用', 'system', TRUE, 0, NOW(), 'system', '系统预置模板'),
(NULL, NULL, '合同审查报告', 'TPL_CONTRACT_REVIEW', 'KNOWLEDGE', 16, 'WORD', '合同审查报告模板', '审查,报告,合同', '合同审查', 'system', TRUE, 0, NOW(), 'system', '系统预置模板')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 知识文章初始化
INSERT INTO doc_article (tenant_id, tenant_code, title, doc_type, doc_status, document_format, description, access_level, category_id, article_type, author, source, summary, is_top, is_recommend, is_original, status, create_time, create_by, remark)
VALUES 
(NULL, NULL, '公司设立法律实务指南', 'ARTICLE', 'PUBLISHED', 'HTML', '关于公司设立的法律实务指南', 'PUBLIC', 13, 'GUIDE', 'system', '律师事务所', '本文介绍了公司设立的法律流程和注意事项，包括公司类型选择、注册资本、股权结构设计等实务指南。', TRUE, TRUE, TRUE, 0, NOW(), 'system', '系统预置文章'),
(NULL, NULL, '劳动合同签订的常见法律问题', 'ARTICLE', 'PUBLISHED', 'HTML', '劳动合同签订的法律问题分析', 'PUBLIC', 13, 'GUIDE', 'system', '律师事务所', '本文分析了劳动合同签订过程中的常见法律问题，包括试用期约定、竞业限制、保密条款等内容。', FALSE, TRUE, TRUE, 0, NOW(), 'system', '系统预置文章')
