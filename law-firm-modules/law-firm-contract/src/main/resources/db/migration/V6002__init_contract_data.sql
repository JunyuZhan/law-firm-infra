-- 合同模块初始化数据
-- 版本: V6002
-- 模块: contract
-- 创建时间: 2023-10-01
-- 说明: 创建合同管理模块初始化数据

-- 合同相关字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 合同类型
(NULL, NULL, 'contract_type', '法律顾问合同', '1', 1, 0, NOW(), 'system', '合同类型 - 法律顾问合同'),
(NULL, NULL, 'contract_type', '诉讼代理合同', '2', 2, 0, NOW(), 'system', '合同类型 - 诉讼代理合同'),
(NULL, NULL, 'contract_type', '非诉法律服务合同', '3', 3, 0, NOW(), 'system', '合同类型 - 非诉法律服务合同'),
(NULL, NULL, 'contract_type', '知识产权代理合同', '4', 4, 0, NOW(), 'system', '合同类型 - 知识产权代理合同'),
(NULL, NULL, 'contract_type', '尽职调查合同', '5', 5, 0, NOW(), 'system', '合同类型 - 尽职调查合同'),
(NULL, NULL, 'contract_type', '法律咨询合同', '6', 6, 0, NOW(), 'system', '合同类型 - 法律咨询合同'),

-- 合同状态
(NULL, NULL, 'contract_status', '草稿', '0', 1, 0, NOW(), 'system', '合同状态 - 草稿'),
(NULL, NULL, 'contract_status', '审批中', '1', 2, 0, NOW(), 'system', '合同状态 - 审批中'),
(NULL, NULL, 'contract_status', '已生效', '2', 3, 0, NOW(), 'system', '合同状态 - 已生效'),
(NULL, NULL, 'contract_status', '已到期', '3', 4, 0, NOW(), 'system', '合同状态 - 已到期'),
(NULL, NULL, 'contract_status', '已终止', '4', 5, 0, NOW(), 'system', '合同状态 - 已终止'),
(NULL, NULL, 'contract_status', '已作废', '5', 6, 0, NOW(), 'system', '合同状态 - 已作废'),

-- 收费类型
(NULL, NULL, 'fee_type', '固定收费', '1', 1, 0, NOW(), 'system', '收费类型 - 固定收费'),
(NULL, NULL, 'fee_type', '计时收费', '2', 2, 0, NOW(), 'system', '收费类型 - 计时收费'),
(NULL, NULL, 'fee_type', '风险收费', '3', 3, 0, NOW(), 'system', '收费类型 - 风险收费'),
(NULL, NULL, 'fee_type', '混合收费', '4', 4, 0, NOW(), 'system', '收费类型 - 混合收费'),
(NULL, NULL, 'fee_type', '阶段收费', '5', 5, 0, NOW(), 'system', '收费类型 - 阶段收费'),
(NULL, NULL, 'fee_type', '年费收费', '6', 6, 0, NOW(), 'system', '收费类型 - 年费收费'),

-- 付款方式
(NULL, NULL, 'payment_method', '银行转账', '1', 1, 0, NOW(), 'system', '付款方式 - 银行转账'),
(NULL, NULL, 'payment_method', '现金支付', '2', 2, 0, NOW(), 'system', '付款方式 - 现金支付'),
(NULL, NULL, 'payment_method', '支票', '3', 3, 0, NOW(), 'system', '付款方式 - 支票'),
(NULL, NULL, 'payment_method', '第三方支付', '4', 4, 0, NOW(), 'system', '付款方式 - 第三方支付'),
(NULL, NULL, 'payment_method', '其他方式', '5', 5, 0, NOW(), 'system', '付款方式 - 其他方式'),

-- 团队成员角色
(NULL, NULL, 'team_role_type', '主办律师', '1', 1, 0, NOW(), 'system', '团队角色 - 主办律师'),
(NULL, NULL, 'team_role_type', '协办律师', '2', 2, 0, NOW(), 'system', '团队角色 - 协办律师'),
(NULL, NULL, 'team_role_type', '指导律师', '3', 3, 0, NOW(), 'system', '团队角色 - 指导律师'),
(NULL, NULL, 'team_role_type', '实习律师', '4', 4, 0, NOW(), 'system', '团队角色 - 实习律师'),
(NULL, NULL, 'team_role_type', '专家顾问', '5', 5, 0, NOW(), 'system', '团队角色 - 专家顾问'),
(NULL, NULL, 'team_role_type', '辅助人员', '6', 6, 0, NOW(), 'system', '团队角色 - 辅助人员'),

-- 合同条款类型
(NULL, NULL, 'clause_type', '服务内容', '1', 1, 0, NOW(), 'system', '条款类型 - 服务内容'),
(NULL, NULL, 'clause_type', '权利与义务', '2', 2, 0, NOW(), 'system', '条款类型 - 权利与义务'),
(NULL, NULL, 'clause_type', '收费标准', '3', 3, 0, NOW(), 'system', '条款类型 - 收费标准'),
(NULL, NULL, 'clause_type', '付款方式', '4', 4, 0, NOW(), 'system', '条款类型 - 付款方式'),
(NULL, NULL, 'clause_type', '保密条款', '5', 5, 0, NOW(), 'system', '条款类型 - 保密条款'),
(NULL, NULL, 'clause_type', '违约责任', '6', 6, 0, NOW(), 'system', '条款类型 - 违约责任'),
(NULL, NULL, 'clause_type', '争议解决', '7', 7, 0, NOW(), 'system', '条款类型 - 争议解决'),
(NULL, NULL, 'clause_type', '合同期限', '8', 8, 0, NOW(), 'system', '条款类型 - 合同期限'),
(NULL, NULL, 'clause_type', '合同终止', '9', 9, 0, NOW(), 'system', '条款类型 - 合同终止'),
(NULL, NULL, 'clause_type', '其他条款', '10', 10, 0, NOW(), 'system', '条款类型 - 其他条款'),

-- 审核类型
(NULL, NULL, 'review_type', '法务审核', '1', 1, 0, NOW(), 'system', '审核类型 - 法务审核'),
(NULL, NULL, 'review_type', '业务审核', '2', 2, 0, NOW(), 'system', '审核类型 - 业务审核'),
(NULL, NULL, 'review_type', '财务审核', '3', 3, 0, NOW(), 'system', '审核类型 - 财务审核'),
(NULL, NULL, 'review_type', '风控审核', '4', 4, 0, NOW(), 'system', '审核类型 - 风控审核'),
(NULL, NULL, 'review_type', '管理层审核', '5', 5, 0, NOW(), 'system', '审核类型 - 管理层审核'),

-- 变更类型
(NULL, NULL, 'change_type', '合同金额变更', '1', 1, 0, NOW(), 'system', '变更类型 - 合同金额变更'),
(NULL, NULL, 'change_type', '合同期限变更', '2', 2, 0, NOW(), 'system', '变更类型 - 合同期限变更'),
(NULL, NULL, 'change_type', '服务内容变更', '3', 3, 0, NOW(), 'system', '变更类型 - 服务内容变更'),
(NULL, NULL, 'change_type', '客户信息变更', '4', 4, 0, NOW(), 'system', '变更类型 - 客户信息变更'),
(NULL, NULL, 'change_type', '律师团队变更', '5', 5, 0, NOW(), 'system', '变更类型 - 律师团队变更'),
(NULL, NULL, 'change_type', '其他变更', '6', 6, 0, NOW(), 'system', '变更类型 - 其他变更'),

-- 冲突类型
(NULL, NULL, 'conflict_type', '利益冲突', '1', 1, 0, NOW(), 'system', '冲突类型 - 利益冲突'),
(NULL, NULL, 'conflict_type', '客户冲突', '2', 2, 0, NOW(), 'system', '冲突类型 - 客户冲突'),
(NULL, NULL, 'conflict_type', '业务冲突', '3', 3, 0, NOW(), 'system', '冲突类型 - 业务冲突'),
(NULL, NULL, 'conflict_type', '人员冲突', '4', 4, 0, NOW(), 'system', '冲突类型 - 人员冲突'),
(NULL, NULL, 'conflict_type', '时间冲突', '5', 5, 0, NOW(), 'system', '冲突类型 - 时间冲突'),

-- 冲突级别
(NULL, NULL, 'conflict_level', '轻微', '1', 1, 0, NOW(), 'system', '冲突级别 - 轻微'),
(NULL, NULL, 'conflict_level', '一般', '2', 2, 0, NOW(), 'system', '冲突级别 - 一般'),
(NULL, NULL, 'conflict_level', '严重', '3', 3, 0, NOW(), 'system', '冲突级别 - 严重'),
(NULL, NULL, 'conflict_level', '致命', '4', 4, 0, NOW(), 'system', '冲突级别 - 致命')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化合同相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(NULL, NULL, '合同类型', 'contract_type', 0, NOW(), 'system', '合同类型字典'),
(NULL, NULL, '合同状态', 'contract_status', 0, NOW(), 'system', '合同状态字典'),
(NULL, NULL, '收费类型', 'fee_type', 0, NOW(), 'system', '收费类型字典'),
(NULL, NULL, '付款方式', 'payment_method', 0, NOW(), 'system', '付款方式字典'),
(NULL, NULL, '团队成员角色', 'team_role_type', 0, NOW(), 'system', '团队成员角色字典'),
(NULL, NULL, '合同条款类型', 'clause_type', 0, NOW(), 'system', '合同条款类型字典'),
(NULL, NULL, '审核类型', 'review_type', 0, NOW(), 'system', '审核类型字典'),
(NULL, NULL, '变更类型', 'change_type', 0, NOW(), 'system', '变更类型字典'),
(NULL, NULL, '冲突类型', 'conflict_type', 0, NOW(), 'system', '冲突类型字典'),
(NULL, NULL, '冲突级别', 'conflict_level', 0, NOW(), 'system', '冲突级别字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加默认合同模板（可根据实际需求修改或补充）
INSERT INTO contract_template (tenant_id, tenant_code, template_name, template_code, template_type, content, status, 
                             template_version, is_default, category, description, usage_count, 
                             create_time, create_by, remark)
VALUES
(NULL, NULL, '法律顾问服务合同', 'TEMP_CONSULT_001', '1', 
'甲方：____ \n乙方：____ \n\n根据《中华人民共和国民法典》及相关法律法规，甲乙双方本着平等自愿、诚实信用的原则，经协商一致，就乙方为甲方提供法律顾问服务事宜达成如下协议： \n\n一、服务内容\n1. 解答甲方提出的法律咨询； \n2. 审查甲方的规章制度、合同文本等法律文件； \n3. 代理甲方进行法律谈判； \n4. 代理甲方处理纠纷及诉讼事务； \n5. 其他双方约定的法律服务事项。 \n\n二、服务期限\n本合同服务期限为__年，自____年__月__日起至____年__月__日止。 \n\n三、服务费用\n1. 顾问服务费：人民币__元整（¥____）/年； \n2. 支付方式：____；\n\n四、双方权利义务\n（甲方权利义务及乙方权利义务条款）\n\n五、保密条款\n（保密义务相关条款）\n\n六、违约责任\n（违约责任相关条款）\n\n七、争议解决\n（争议解决方式相关条款）\n\n八、其他\n（其他约定条款）\n\n甲方（盖章）：           乙方（盖章）：\n代表人（签字）：         代表人（签字）：\n日期：                  日期：', 
1, '1.0', TRUE, '常规合同', '标准法律顾问服务合同模板', 0, NOW(), 'system', '初始默认法律顾问合同模板'),

(NULL, NULL, '诉讼代理合同', 'TEMP_LITIGATION_001', '2', 
'委托人：____ \n受托人：____ \n\n根据《中华人民共和国民法典》《中华人民共和国律师法》及相关法律法规的规定，委托人与受托人本着自愿、平等、诚实信用的原则，就委托受托人办理诉讼事务达成如下协议： \n\n一、委托事项\n委托人委托受托人担任____案件的代理人，案由：____。 \n\n二、委托权限\n（委托权限相关条款，如普通代理或特别授权代理）\n\n三、委托期限\n自本合同签订之日起至____案件生效法律文书确定的权利义务履行完毕之日止。 \n\n四、代理费用及支付方式\n1. 代理费：人民币__元整（¥____）； \n2. 支付方式：____；\n\n五、双方权利义务\n（委托人权利义务及受托人权利义务条款）\n\n六、违约责任\n（违约责任相关条款）\n\n七、争议解决\n（争议解决方式相关条款）\n\n八、其他\n（其他约定条款）\n\n委托人（盖章）：         受托人（盖章）：\n代表人（签字）：         代表人（签字）：\n日期：                  日期：', 
1, '1.0', TRUE, '诉讼代理', '标准诉讼代理合同模板', 0, NOW(), 'system', '初始默认诉讼代理合同模板')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;