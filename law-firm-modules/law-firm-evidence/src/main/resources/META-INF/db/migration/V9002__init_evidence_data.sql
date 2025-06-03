-- 证据管理模块基础数据
-- 版本: V9002
-- 模块: 证据管理模块 (V9000-V9999)
-- 创建时间: 2023-06-01
-- 说明: 证据管理功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 证据相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('证据类型', 'evidence_type', 1, 'system', NOW(), '证据类型字典'),
('证据性质', 'evidence_nature', 1, 'system', NOW(), '证据性质字典'),
('证据来源', 'evidence_source', 1, 'system', NOW(), '证据来源字典'),
('证据状态', 'evidence_status', 1, 'system', NOW(), '证据状态字典'),
('证明力等级', 'proof_level', 1, 'system', NOW(), '证明力等级字典'),
('质证人类型', 'challenger_type', 1, 'system', NOW(), '质证人类型字典'),
('质证结果', 'challenge_result', 1, 'system', NOW(), '质证结果字典'),
('认证结果', 'authentication_result', 1, 'system', NOW(), '证据认证结果字典'),
('保全类型', 'preservation_type', 1, 'system', NOW(), '证据保全类型字典'),
('保全方式', 'preservation_method', 1, 'system', NOW(), '证据保全方式字典'),
('保全状态', 'preservation_status', 1, 'system', NOW(), '证据保全状态字典');

-- ======================= 系统字典数据 =======================

-- 证据类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '书证', '1', 'evidence_type', '', 'primary', 'Y', 1, 'system', NOW(), '书面证据'),
(2, '物证', '2', 'evidence_type', '', 'success', 'N', 1, 'system', NOW(), '实物证据'),
(3, '证人证言', '3', 'evidence_type', '', 'info', 'N', 1, 'system', NOW(), '证人提供的证言'),
(4, '视听资料', '4', 'evidence_type', '', 'warning', 'N', 1, 'system', NOW(), '音视频等资料'),
(5, '电子数据', '5', 'evidence_type', '', 'danger', 'N', 1, 'system', NOW(), '电子形式的数据');

-- 证据性质
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '直接证据', '1', 'evidence_nature', '', 'primary', 'Y', 1, 'system', NOW(), '能够直接证明案件主要事实的证据'),
(2, '间接证据', '2', 'evidence_nature', '', 'info', 'N', 1, 'system', NOW(), '不能单独直接证明案件主要事实的证据'),
(3, '原始证据', '3', 'evidence_nature', '', 'success', 'N', 1, 'system', NOW(), '直接来源于案件事实的第一手资料'),
(4, '传来证据', '4', 'evidence_nature', '', 'warning', 'N', 1, 'system', NOW(), '经过转述或复制的证据');

-- 证据来源
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '当事人提供', '1', 'evidence_source', '', 'primary', 'Y', 1, 'system', NOW(), '由当事人主动提供'),
(2, '法院调取', '2', 'evidence_source', '', 'success', 'N', 1, 'system', NOW(), '由法院依职权调取'),
(3, '律师调查', '3', 'evidence_source', '', 'info', 'N', 1, 'system', NOW(), '律师调查收集'),
(4, '公安移送', '4', 'evidence_source', '', 'warning', 'N', 1, 'system', NOW(), '公安机关移送'),
(5, '其他途径', '5', 'evidence_source', '', 'dark', 'N', 1, 'system', NOW(), '其他合法途径获得');

-- 证据状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '收集中', '1', 'evidence_status', '', 'warning', 'Y', 1, 'system', NOW(), '证据收集中'),
(2, '已收集', '2', 'evidence_status', '', 'info', 'N', 1, 'system', NOW(), '证据已收集'),
(3, '已质证', '3', 'evidence_status', '', 'primary', 'N', 1, 'system', NOW(), '证据已质证'),
(4, '已认定', '4', 'evidence_status', '', 'success', 'N', 1, 'system', NOW(), '证据已被法院认定'),
(5, '已归档', '5', 'evidence_status', '', 'default', 'N', 1, 'system', NOW(), '证据已归档');

-- 证明力等级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '强', '1', 'proof_level', '', 'danger', 'N', 1, 'system', NOW(), '证明力强'),
(2, '较强', '2', 'proof_level', '', 'warning', 'N', 1, 'system', NOW(), '证明力较强'),
(3, '一般', '3', 'proof_level', '', 'primary', 'Y', 1, 'system', NOW(), '证明力一般'),
(4, '较弱', '4', 'proof_level', '', 'info', 'N', 1, 'system', NOW(), '证明力较弱'),
(5, '弱', '5', 'proof_level', '', 'secondary', 'N', 1, 'system', NOW(), '证明力弱');

-- 质证人类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '对方当事人', '1', 'challenger_type', '', 'primary', 'Y', 1, 'system', NOW(), '对方当事人'),
(2, '对方律师', '2', 'challenger_type', '', 'info', 'N', 1, 'system', NOW(), '对方律师'),
(3, '第三人', '3', 'challenger_type', '', 'warning', 'N', 1, 'system', NOW(), '第三人'),
(4, '其他', '4', 'challenger_type', '', 'dark', 'N', 1, 'system', NOW(), '其他质证人');

-- 质证结果
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '有效', '1', 'challenge_result', '', 'success', 'N', 1, 'system', NOW(), '证据有效'),
(2, '无效', '2', 'challenge_result', '', 'danger', 'N', 1, 'system', NOW(), '证据无效'),
(3, '部分有效', '3', 'challenge_result', '', 'warning', 'N', 1, 'system', NOW(), '证据部分有效'),
(4, '待定', '4', 'challenge_result', '', 'info', 'Y', 1, 'system', NOW(), '质证结果待定');

-- 认证结果
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '有效', '1', 'authentication_result', '', 'success', 'N', 1, 'system', NOW(), '认证有效'),
(2, '无效', '2', 'authentication_result', '', 'danger', 'N', 1, 'system', NOW(), '认证无效'),
(3, '部分有效', '3', 'authentication_result', '', 'warning', 'N', 1, 'system', NOW(), '部分有效');

-- 保全类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '诉前保全', '1', 'preservation_type', '', 'primary', 'Y', 1, 'system', NOW(), '诉讼前保全'),
(2, '诉中保全', '2', 'preservation_type', '', 'success', 'N', 1, 'system', NOW(), '诉讼中保全'),
(3, '执行保全', '3', 'preservation_type', '', 'warning', 'N', 1, 'system', NOW(), '执行程序中保全');

-- 保全方式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '查封', '1', 'preservation_method', '', 'primary', 'Y', 1, 'system', NOW(), '查封保全'),
(2, '扣押', '2', 'preservation_method', '', 'warning', 'N', 1, 'system', NOW(), '扣押保全'),
(3, '冻结', '3', 'preservation_method', '', 'info', 'N', 1, 'system', NOW(), '冻结保全'),
(4, '其他', '4', 'preservation_method', '', 'dark', 'N', 1, 'system', NOW(), '其他保全方式');

-- 保全状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '申请中', '1', 'preservation_status', '', 'warning', 'Y', 1, 'system', NOW(), '保全申请中'),
(2, '已保全', '2', 'preservation_status', '', 'success', 'N', 1, 'system', NOW(), '已执行保全'),
(3, '已解除', '3', 'preservation_status', '', 'info', 'N', 1, 'system', NOW(), '保全已解除'),
(4, '已失效', '4', 'preservation_status', '', 'danger', 'N', 1, 'system', NOW(), '保全已失效');

-- ======================= 证据分类初始化 =======================

-- 证据分类数据
INSERT INTO evidence_category (tenant_id, category_name, category_code, parent_id, level, category_path, description, sort_order, is_system, create_by, create_time) VALUES
-- 一级分类
(0, '书证类', 'DOCUMENT_EVIDENCE', 0, 1, '/书证类', '书面形式的证据材料', 1, 1, 'system', NOW()),
(0, '物证类', 'PHYSICAL_EVIDENCE', 0, 1, '/物证类', '实物形式的证据材料', 2, 1, 'system', NOW()),
(0, '证言类', 'TESTIMONY_EVIDENCE', 0, 1, '/证言类', '证人证言和当事人陈述', 3, 1, 'system', NOW()),
(0, '视听类', 'AUDIOVISUAL_EVIDENCE', 0, 1, '/视听类', '音频视频等视听资料', 4, 1, 'system', NOW()),
(0, '电子类', 'ELECTRONIC_EVIDENCE', 0, 1, '/电子类', '电子数据形式的证据', 5, 1, 'system', NOW()),

-- 书证类二级分类
(0, '合同协议', 'CONTRACT_DOCUMENT', 1, 2, '/书证类/合同协议', '合同、协议等书证', 1, 1, 'system', NOW()),
(0, '财务凭证', 'FINANCIAL_DOCUMENT', 1, 2, '/书证类/财务凭证', '发票、收据等财务凭证', 2, 1, 'system', NOW()),
(0, '身份证明', 'IDENTITY_DOCUMENT', 1, 2, '/书证类/身份证明', '身份证、营业执照等身份证明', 3, 1, 'system', NOW()),

-- 物证类二级分类
(0, '产品样品', 'PRODUCT_SAMPLE', 2, 2, '/物证类/产品样品', '产品实物样品', 1, 1, 'system', NOW()),
(0, '现场物品', 'SCENE_OBJECT', 2, 2, '/物证类/现场物品', '现场提取的物品', 2, 1, 'system', NOW()),

-- 电子类二级分类
(0, '电子邮件', 'EMAIL_EVIDENCE', 5, 2, '/电子类/电子邮件', '电子邮件往来记录', 1, 1, 'system', NOW()),
(0, '网页截图', 'WEBPAGE_SCREENSHOT', 5, 2, '/电子类/网页截图', '网页内容截图', 2, 1, 'system', NOW()),
(0, '聊天记录', 'CHAT_RECORD', 5, 2, '/电子类/聊天记录', '微信、QQ等聊天记录', 3, 1, 'system', NOW());

-- ======================= 证据标签初始化 =======================

-- 证据标签数据
INSERT INTO evidence_label (tenant_id, label_name, label_code, label_type, label_category, color, description, sort_order, is_system, create_by, create_time) VALUES
(0, '关键证据', 'KEY_EVIDENCE', 1, '重要性', '#ff4d4f', '关键性证据', 1, 1, 'system', NOW()),
(0, '辅助证据', 'AUXILIARY_EVIDENCE', 1, '重要性', '#1890ff', '辅助性证据', 2, 1, 'system', NOW()),
(0, '原件', 'ORIGINAL', 1, '形式', '#52c41a', '原始证据', 3, 1, 'system', NOW()),
(0, '复印件', 'COPY', 1, '形式', '#faad14', '复印件证据', 4, 1, 'system', NOW()),
(0, '已质证', 'CHALLENGED', 2, '状态', '#722ed1', '已完成质证', 5, 1, 'system', NOW()),
(0, '未质证', 'NOT_CHALLENGED', 2, '状态', '#d9d9d9', '尚未质证', 6, 1, 'system', NOW()),
(0, '有争议', 'DISPUTED', 2, '状态', '#fa8c16', '存在争议', 7, 1, 'system', NOW()),
(0, '需保全', 'NEED_PRESERVATION', 3, '紧急程度', '#ff7a45', '需要保全', 8, 1, 'system', NOW()),
(0, '已保全', 'PRESERVED', 2, '状态', '#13c2c2', '已执行保全', 9, 1, 'system', NOW()),
(0, '机密', 'CONFIDENTIAL', 1, '安全级别', '#722ed1', '机密证据', 10, 1, 'system', NOW());

-- 初始化完成提示
SELECT '证据管理模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建证据分类：', COUNT(*), '个') AS category_count FROM evidence_category WHERE is_system = 1;
SELECT CONCAT('已创建证据标签：', COUNT(*), '个') AS label_count FROM evidence_label WHERE is_system = 1; 