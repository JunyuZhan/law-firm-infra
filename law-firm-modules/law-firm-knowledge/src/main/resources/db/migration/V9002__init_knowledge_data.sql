-- 知识库模块数据初始化
-- 版本: V9002
-- 模块: knowledge
-- 创建时间: 2023-10-01
-- 说明: 初始化知识库管理模块基础数据

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 初始化知识分类（一级分类）
INSERT INTO knowledge_category (name, code, parent_id, path, level, sort, status, deleted, create_time)
VALUES 
('法律法规', 'LAW_REGULATION', 0, '/LAW_REGULATION', 1, 1, 0, 0, NOW()),
('案例分析', 'CASE_ANALYSIS', 0, '/CASE_ANALYSIS', 1, 2, 0, 0, NOW()),
('合同范本', 'CONTRACT_TEMPLATE', 0, '/CONTRACT_TEMPLATE', 1, 3, 0, 0, NOW()),
('法律文书', 'LEGAL_DOCUMENT', 0, '/LEGAL_DOCUMENT', 1, 4, 0, 0, NOW()),
('法律研究', 'LEGAL_RESEARCH', 0, '/LEGAL_RESEARCH', 1, 5, 0, 0, NOW()),
('业务知识', 'BUSINESS_KNOWLEDGE', 0, '/BUSINESS_KNOWLEDGE', 1, 6, 0, 0, NOW()),
('管理制度', 'MANAGEMENT_SYSTEM', 0, '/MANAGEMENT_SYSTEM', 1, 7, 0, 0, NOW());

-- 初始化知识分类（二级分类 - 法律法规）
INSERT INTO knowledge_category (name, code, parent_id, path, level, sort, status, deleted, create_time)
VALUES 
('宪法', 'CONSTITUTIONAL_LAW', 1, '/LAW_REGULATION/CONSTITUTIONAL_LAW', 2, 1, 0, 0, NOW()),
('民商法', 'CIVIL_COMMERCIAL_LAW', 1, '/LAW_REGULATION/CIVIL_COMMERCIAL_LAW', 2, 2, 0, 0, NOW()),
('刑法', 'CRIMINAL_LAW', 1, '/LAW_REGULATION/CRIMINAL_LAW', 2, 3, 0, 0, NOW()),
('行政法', 'ADMINISTRATIVE_LAW', 1, '/LAW_REGULATION/ADMINISTRATIVE_LAW', 2, 4, 0, 0, NOW()),
('经济法', 'ECONOMIC_LAW', 1, '/LAW_REGULATION/ECONOMIC_LAW', 2, 5, 0, 0, NOW()),
('社会法', 'SOCIAL_LAW', 1, '/LAW_REGULATION/SOCIAL_LAW', 2, 6, 0, 0, NOW()),
('诉讼与非诉讼程序法', 'PROCEDURAL_LAW', 1, '/LAW_REGULATION/PROCEDURAL_LAW', 2, 7, 0, 0, NOW());

-- 初始化知识分类（二级分类 - 案例分析）
INSERT INTO knowledge_category (name, code, parent_id, path, level, sort, status, deleted, create_time)
VALUES 
('民事案例', 'CIVIL_CASE', 2, '/CASE_ANALYSIS/CIVIL_CASE', 2, 1, 0, 0, NOW()),
('刑事案例', 'CRIMINAL_CASE', 2, '/CASE_ANALYSIS/CRIMINAL_CASE', 2, 2, 0, 0, NOW()),
('行政案例', 'ADMINISTRATIVE_CASE', 2, '/CASE_ANALYSIS/ADMINISTRATIVE_CASE', 2, 3, 0, 0, NOW()),
('典型案例', 'TYPICAL_CASE', 2, '/CASE_ANALYSIS/TYPICAL_CASE', 2, 4, 0, 0, NOW()),
('疑难案例', 'DIFFICULT_CASE', 2, '/CASE_ANALYSIS/DIFFICULT_CASE', 2, 5, 0, 0, NOW());

-- 初始化知识分类（二级分类 - 合同范本）
INSERT INTO knowledge_category (name, code, parent_id, path, level, sort, status, deleted, create_time)
VALUES 
('买卖合同', 'SALES_CONTRACT', 3, '/CONTRACT_TEMPLATE/SALES_CONTRACT', 2, 1, 0, 0, NOW()),
('租赁合同', 'LEASE_CONTRACT', 3, '/CONTRACT_TEMPLATE/LEASE_CONTRACT', 2, 2, 0, 0, NOW()),
('劳动合同', 'LABOR_CONTRACT', 3, '/CONTRACT_TEMPLATE/LABOR_CONTRACT', 2, 3, 0, 0, NOW()),
('建设工程合同', 'CONSTRUCTION_CONTRACT', 3, '/CONTRACT_TEMPLATE/CONSTRUCTION_CONTRACT', 2, 4, 0, 0, NOW()),
('技术合同', 'TECHNOLOGY_CONTRACT', 3, '/CONTRACT_TEMPLATE/TECHNOLOGY_CONTRACT', 2, 5, 0, 0, NOW()),
('金融合同', 'FINANCIAL_CONTRACT', 3, '/CONTRACT_TEMPLATE/FINANCIAL_CONTRACT', 2, 6, 0, 0, NOW()),
('其他合同', 'OTHER_CONTRACT', 3, '/CONTRACT_TEMPLATE/OTHER_CONTRACT', 2, 7, 0, 0, NOW());

-- 初始化知识分类（二级分类 - 法律文书）
INSERT INTO knowledge_category (name, code, parent_id, path, level, sort, status, deleted, create_time)
VALUES 
('诉讼文书', 'LITIGATION_DOCUMENT', 4, '/LEGAL_DOCUMENT/LITIGATION_DOCUMENT', 2, 1, 0, 0, NOW()),
('非诉文书', 'NON_LITIGATION_DOCUMENT', 4, '/LEGAL_DOCUMENT/NON_LITIGATION_DOCUMENT', 2, 2, 0, 0, NOW()),
('仲裁文书', 'ARBITRATION_DOCUMENT', 4, '/LEGAL_DOCUMENT/ARBITRATION_DOCUMENT', 2, 3, 0, 0, NOW()),
('公证文书', 'NOTARY_DOCUMENT', 4, '/LEGAL_DOCUMENT/NOTARY_DOCUMENT', 2, 4, 0, 0, NOW());

-- 初始化知识标签
INSERT INTO knowledge_tag (name, code, sort, status, deleted, create_time)
VALUES 
('民法典', 'CIVIL_CODE', 1, 0, 0, NOW()),
('公司法', 'COMPANY_LAW', 2, 0, 0, NOW()),
('合同法', 'CONTRACT_LAW', 3, 0, 0, NOW()),
('证券法', 'SECURITIES_LAW', 4, 0, 0, NOW()),
('劳动法', 'LABOR_LAW', 5, 0, 0, NOW()),
('知识产权', 'IP_LAW', 6, 0, 0, NOW()),
('房地产', 'REAL_ESTATE', 7, 0, 0, NOW()),
('金融保险', 'FINANCE_INSURANCE', 8, 0, 0, NOW()),
('环境保护', 'ENVIRONMENTAL', 9, 0, 0, NOW()),
('税收法规', 'TAX_LAW', 10, 0, 0, NOW()),
('医疗卫生', 'HEALTHCARE', 11, 0, 0, NOW()),
('教育法规', 'EDUCATION_LAW', 12, 0, 0, NOW()),
('外商投资', 'FOREIGN_INVESTMENT', 13, 0, 0, NOW()),
('刑事辩护', 'CRIMINAL_DEFENSE', 14, 0, 0, NOW()),
('行政诉讼', 'ADMINISTRATIVE_LITIGATION', 15, 0, 0, NOW()),
('婚姻家庭', 'MARRIAGE_FAMILY', 16, 0, 0, NOW()),
('继承', 'INHERITANCE', 17, 0, 0, NOW()),
('破产清算', 'BANKRUPTCY', 18, 0, 0, NOW()),
('重组并购', 'MERGER_ACQUISITION', 19, 0, 0, NOW()),
('国际贸易', 'INTERNATIONAL_TRADE', 20, 0, 0, NOW());

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 