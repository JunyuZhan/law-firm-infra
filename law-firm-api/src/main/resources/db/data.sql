-- 请注意这是API模块的基础数据初始化，auth相关数据已迁移到auth模块

-- 系统配置
INSERT INTO sys_config (id, config_key, config_value, config_name, remark, status, create_time, update_time, deleted, config_type, is_system, create_by)
VALUES (1, 'system.name', '律师事务所管理系统', '系统名称', '系统名称配置', 1, NOW(), NOW(), 0, 'SYSTEM', 1, 'admin'),
       (2, 'system.version', '1.0.0', '系统版本', '系统版本配置', 1, NOW(), NOW(), 0, 'SYSTEM', 1, 'admin'),
       (3, 'system.notice', '欢迎使用律师事务所管理系统', '系统公告', '登录页面显示的公告', 1, NOW(), NOW(), 0, 'SYSTEM', 1, 'admin'),
       (4, 'system.logo', '/logo.png', '系统Logo', '系统Logo图片路径', 1, NOW(), NOW(), 0, 'SYSTEM', 1, 'admin'),
       (5, 'storage.strategy', 'LOCAL', '存储策略', '文件存储策略: LOCAL-本地存储, ALIYUN_OSS-阿里云OSS, AWS_S3-AWS S3', 1, NOW(), NOW(), 0, 'SYSTEM', 1, 'admin')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 字典类型
INSERT INTO sys_dict (id, dict_name, dict_code, description, created_time, updated_time, deleted)
VALUES (1, '案件类型', 'case_type', '案件分类', NOW(), NOW(), 0),
       (2, '案件状态', 'case_status', '案件处理状态', NOW(), NOW(), 0),
       (3, '客户类型', 'client_type', '客户分类', NOW(), NOW(), 0),
       (4, '文档类型', 'doc_type', '文档分类', NOW(), NOW(), 0),
       (5, '合同类型', 'contract_type', '合同分类', NOW(), NOW(), 0),
       (6, '收费类型', 'fee_type', '收费方式', NOW(), NOW(), 0),
       (7, '风险等级', 'risk_level', '风险评估等级', NOW(), NOW(), 0),
       (8, '紧急程度', 'urgency_level', '紧急程度', NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE updated_time = NOW();

-- 字典数据
INSERT INTO sys_dict_item (dict_id, item_value, item_label, item_order, status, created_time, updated_time, deleted)
VALUES 
-- 案件类型
(1, 'civil', '民事案件', 1, 1, NOW(), NOW(), 0),
(1, 'commercial', '商事案件', 2, 1, NOW(), NOW(), 0),
(1, 'criminal', '刑事案件', 3, 1, NOW(), NOW(), 0),
(1, 'administrative', '行政案件', 4, 1, NOW(), NOW(), 0),
(1, 'ip', '知识产权', 5, 1, NOW(), NOW(), 0),
(1, 'international', '国际业务', 6, 1, NOW(), NOW(), 0),
-- 案件状态
(2, 'pending', '待处理', 1, 1, NOW(), NOW(), 0),
(2, 'processing', '处理中', 2, 1, NOW(), NOW(), 0),
(2, 'closed', '已结案', 3, 1, NOW(), NOW(), 0),
(2, 'archived', '已归档', 4, 1, NOW(), NOW(), 0),
-- 客户类型
(3, 'individual', '个人客户', 1, 1, NOW(), NOW(), 0),
(3, 'enterprise', '企业客户', 2, 1, NOW(), NOW(), 0),
(3, 'government', '政府机构', 3, 1, NOW(), NOW(), 0),
-- 文档类型
(4, 'contract', '合同文件', 1, 1, NOW(), NOW(), 0),
(4, 'judgment', '裁判文书', 2, 1, NOW(), NOW(), 0),
(4, 'evidence', '证据材料', 3, 1, NOW(), NOW(), 0),
(4, 'brief', '法律意见书', 4, 1, NOW(), NOW(), 0),
(4, 'letter', '函件通知', 5, 1, NOW(), NOW(), 0),
-- 合同类型
(5, 'service', '法律服务合同', 1, 1, NOW(), NOW(), 0),
(5, 'retainer', '常年法律顾问合同', 2, 1, NOW(), NOW(), 0),
(5, 'project', '项目合同', 3, 1, NOW(), NOW(), 0),
-- 收费类型
(6, 'fixed', '固定费用', 1, 1, NOW(), NOW(), 0),
(6, 'hourly', '计时收费', 2, 1, NOW(), NOW(), 0),
(6, 'contingency', '风险代理', 3, 1, NOW(), NOW(), 0),
(6, 'mixed', '混合收费', 4, 1, NOW(), NOW(), 0),
-- 风险等级
(7, 'low', '低风险', 1, 1, NOW(), NOW(), 0),
(7, 'medium', '中风险', 2, 1, NOW(), NOW(), 0),
(7, 'high', '高风险', 3, 1, NOW(), NOW(), 0),
-- 紧急程度
(8, 'normal', '普通', 1, 1, NOW(), NOW(), 0),
(8, 'urgent', '紧急', 2, 1, NOW(), NOW(), 0),
(8, 'critical', '非常紧急', 3, 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE updated_time = NOW();

-- 存储桶初始化
INSERT INTO storage_bucket (id, bucket_name, storage_type, status, created_time, updated_time, deleted)
VALUES (1, 'default', 'LOCAL', 1, NOW(), NOW(), 0),
       (2, 'documents', 'LOCAL', 1, NOW(), NOW(), 0),
       (3, 'contracts', 'LOCAL', 1, NOW(), NOW(), 0),
       (4, 'evidence', 'LOCAL', 1, NOW(), NOW(), 0),
       (5, 'templates', 'LOCAL', 1, NOW(), NOW(), 0)
ON DUPLICATE KEY UPDATE updated_time = NOW(); 