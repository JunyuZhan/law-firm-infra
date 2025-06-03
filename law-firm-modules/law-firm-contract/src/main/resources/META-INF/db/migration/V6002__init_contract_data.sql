-- 合同管理模块基础数据
-- 版本: V6002
-- 模块: 合同管理模块 (V6000-V6999)
-- 创建时间: 2023-06-01
-- 说明: 合同管理功能的基础数据初始化
-- 包括：合同相关字典数据、基础模板配置

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 字典类型初始化 =======================

INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(NULL, NULL, '合同类型', 'contract_type', 1, NOW(), 'system', '合同类型字典'),
(NULL, NULL, '合同状态', 'contract_status', 1, NOW(), 'system', '合同状态字典'),
(NULL, NULL, '收费类型', 'contract_fee_type', 1, NOW(), 'system', '合同收费类型字典'),
(NULL, NULL, '收费方式', 'contract_fee_category', 1, NOW(), 'system', '合同收费方式字典'),
(NULL, NULL, '付款状态', 'contract_payment_status', 1, NOW(), 'system', '合同付款状态字典'),
(NULL, NULL, '团队角色', 'contract_team_role', 1, NOW(), 'system', '合同团队角色字典'),
(NULL, NULL, '条款类型', 'contract_clause_type', 1, NOW(), 'system', '合同条款类型字典'),
(NULL, NULL, '审核类型', 'contract_review_type', 1, NOW(), 'system', '合同审核类型字典'),
(NULL, NULL, '审核状态', 'contract_review_status', 1, NOW(), 'system', '合同审核状态字典'),
(NULL, NULL, '审批状态', 'contract_approval_status', 1, NOW(), 'system', '合同审批状态字典'),
(NULL, NULL, '里程碑类型', 'contract_milestone_type', 1, NOW(), 'system', '合同里程碑类型字典'),
(NULL, NULL, '里程碑状态', 'contract_milestone_status', 1, NOW(), 'system', '合同里程碑状态字典'),
(NULL, NULL, '变更类型', 'contract_change_type', 1, NOW(), 'system', '合同变更类型字典'),
(NULL, NULL, '变更状态', 'contract_change_status', 1, NOW(), 'system', '合同变更状态字典'),
(NULL, NULL, '冲突类型', 'contract_conflict_type', 1, NOW(), 'system', '合同冲突类型字典'),
(NULL, NULL, '冲突级别', 'contract_conflict_level', 1, NOW(), 'system', '合同冲突级别字典'),
(NULL, NULL, '冲突状态', 'contract_conflict_status', 1, NOW(), 'system', '合同冲突状态字典'),
(NULL, NULL, '保密级别', 'contract_confidentiality_level', 1, NOW(), 'system', '合同保密级别字典'),
(NULL, NULL, '附件类型', 'contract_attachment_type', 1, NOW(), 'system', '合同附件类型字典'),
(NULL, NULL, '计费周期', 'contract_billing_cycle', 1, NOW(), 'system', '合同计费周期字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 字典数据初始化 =======================

INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 合同类型
(NULL, NULL, 'contract_type', '委托代理', '1', 1, 1, NOW(), 'system', '合同类型 - 委托代理'),
(NULL, NULL, 'contract_type', '法律顾问', '2', 2, 1, NOW(), 'system', '合同类型 - 法律顾问'),
(NULL, NULL, 'contract_type', '专项服务', '3', 3, 1, NOW(), 'system', '合同类型 - 专项服务'),
(NULL, NULL, 'contract_type', '其他', '4', 4, 1, NOW(), 'system', '合同类型 - 其他'),

-- 合同状态
(NULL, NULL, 'contract_status', '草稿', '1', 1, 1, NOW(), 'system', '合同状态 - 草稿'),
(NULL, NULL, 'contract_status', '待审核', '2', 2, 1, NOW(), 'system', '合同状态 - 待审核'),
(NULL, NULL, 'contract_status', '已审核', '3', 3, 1, NOW(), 'system', '合同状态 - 已审核'),
(NULL, NULL, 'contract_status', '已签署', '4', 4, 1, NOW(), 'system', '合同状态 - 已签署'),
(NULL, NULL, 'contract_status', '执行中', '5', 5, 1, NOW(), 'system', '合同状态 - 执行中'),
(NULL, NULL, 'contract_status', '已完成', '6', 6, 1, NOW(), 'system', '合同状态 - 已完成'),
(NULL, NULL, 'contract_status', '已终止', '7', 7, 1, NOW(), 'system', '合同状态 - 已终止'),

-- 收费类型
(NULL, NULL, 'contract_fee_type', '律师费', '1', 1, 1, NOW(), 'system', '收费类型 - 律师费'),
(NULL, NULL, 'contract_fee_type', '诉讼费', '2', 2, 1, NOW(), 'system', '收费类型 - 诉讼费'),
(NULL, NULL, 'contract_fee_type', '差旅费', '3', 3, 1, NOW(), 'system', '收费类型 - 差旅费'),
(NULL, NULL, 'contract_fee_type', '其他费用', '4', 4, 1, NOW(), 'system', '收费类型 - 其他费用'),

-- 收费方式
(NULL, NULL, 'contract_fee_category', '固定费用', '1', 1, 1, NOW(), 'system', '收费方式 - 固定费用'),
(NULL, NULL, 'contract_fee_category', '按小时', '2', 2, 1, NOW(), 'system', '收费方式 - 按小时'),
(NULL, NULL, 'contract_fee_category', '按比例', '3', 3, 1, NOW(), 'system', '收费方式 - 按比例'),
(NULL, NULL, 'contract_fee_category', '风险代理', '4', 4, 1, NOW(), 'system', '收费方式 - 风险代理'),

-- 付款状态
(NULL, NULL, 'contract_payment_status', '未支付', '1', 1, 1, NOW(), 'system', '付款状态 - 未支付'),
(NULL, NULL, 'contract_payment_status', '部分支付', '2', 2, 1, NOW(), 'system', '付款状态 - 部分支付'),
(NULL, NULL, 'contract_payment_status', '已支付', '3', 3, 1, NOW(), 'system', '付款状态 - 已支付'),
(NULL, NULL, 'contract_payment_status', '已退款', '4', 4, 1, NOW(), 'system', '付款状态 - 已退款'),

-- 团队角色
(NULL, NULL, 'contract_team_role', '主办律师', '1', 1, 1, NOW(), 'system', '团队角色 - 主办律师'),
(NULL, NULL, 'contract_team_role', '协办律师', '2', 2, 1, NOW(), 'system', '团队角色 - 协办律师'),
(NULL, NULL, 'contract_team_role', '律师助理', '3', 3, 1, NOW(), 'system', '团队角色 - 律师助理'),
(NULL, NULL, 'contract_team_role', '实习生', '4', 4, 1, NOW(), 'system', '团队角色 - 实习生'),

-- 条款类型
(NULL, NULL, 'contract_clause_type', '标准条款', '1', 1, 1, NOW(), 'system', '条款类型 - 标准条款'),
(NULL, NULL, 'contract_clause_type', '特殊条款', '2', 2, 1, NOW(), 'system', '条款类型 - 特殊条款'),
(NULL, NULL, 'contract_clause_type', '补充条款', '3', 3, 1, NOW(), 'system', '条款类型 - 补充条款'),

-- 审核类型
(NULL, NULL, 'contract_review_type', '内容审核', '1', 1, 1, NOW(), 'system', '审核类型 - 内容审核'),
(NULL, NULL, 'contract_review_type', '法务审核', '2', 2, 1, NOW(), 'system', '审核类型 - 法务审核'),
(NULL, NULL, 'contract_review_type', '财务审核', '3', 3, 1, NOW(), 'system', '审核类型 - 财务审核'),
(NULL, NULL, 'contract_review_type', '管理审核', '4', 4, 1, NOW(), 'system', '审核类型 - 管理审核'),

-- 审核状态
(NULL, NULL, 'contract_review_status', '待审核', '1', 1, 1, NOW(), 'system', '审核状态 - 待审核'),
(NULL, NULL, 'contract_review_status', '审核通过', '2', 2, 1, NOW(), 'system', '审核状态 - 审核通过'),
(NULL, NULL, 'contract_review_status', '审核拒绝', '3', 3, 1, NOW(), 'system', '审核状态 - 审核拒绝'),
(NULL, NULL, 'contract_review_status', '需要修改', '4', 4, 1, NOW(), 'system', '审核状态 - 需要修改'),

-- 审批状态
(NULL, NULL, 'contract_approval_status', '待审批', '1', 1, 1, NOW(), 'system', '审批状态 - 待审批'),
(NULL, NULL, 'contract_approval_status', '审批通过', '2', 2, 1, NOW(), 'system', '审批状态 - 审批通过'),
(NULL, NULL, 'contract_approval_status', '审批拒绝', '3', 3, 1, NOW(), 'system', '审批状态 - 审批拒绝'),
(NULL, NULL, 'contract_approval_status', '已撤回', '4', 4, 1, NOW(), 'system', '审批状态 - 已撤回'),

-- 里程碑类型
(NULL, NULL, 'contract_milestone_type', '开始节点', '1', 1, 1, NOW(), 'system', '里程碑类型 - 开始节点'),
(NULL, NULL, 'contract_milestone_type', '过程节点', '2', 2, 1, NOW(), 'system', '里程碑类型 - 过程节点'),
(NULL, NULL, 'contract_milestone_type', '结束节点', '3', 3, 1, NOW(), 'system', '里程碑类型 - 结束节点'),

-- 里程碑状态
(NULL, NULL, 'contract_milestone_status', '未开始', '1', 1, 1, NOW(), 'system', '里程碑状态 - 未开始'),
(NULL, NULL, 'contract_milestone_status', '进行中', '2', 2, 1, NOW(), 'system', '里程碑状态 - 进行中'),
(NULL, NULL, 'contract_milestone_status', '已完成', '3', 3, 1, NOW(), 'system', '里程碑状态 - 已完成'),
(NULL, NULL, 'contract_milestone_status', '已延期', '4', 4, 1, NOW(), 'system', '里程碑状态 - 已延期'),
(NULL, NULL, 'contract_milestone_status', '已跳过', '5', 5, 1, NOW(), 'system', '里程碑状态 - 已跳过'),

-- 变更类型
(NULL, NULL, 'contract_change_type', '金额变更', '1', 1, 1, NOW(), 'system', '变更类型 - 金额变更'),
(NULL, NULL, 'contract_change_type', '期限变更', '2', 2, 1, NOW(), 'system', '变更类型 - 期限变更'),
(NULL, NULL, 'contract_change_type', '条款变更', '3', 3, 1, NOW(), 'system', '变更类型 - 条款变更'),
(NULL, NULL, 'contract_change_type', '团队变更', '4', 4, 1, NOW(), 'system', '变更类型 - 团队变更'),
(NULL, NULL, 'contract_change_type', '其他', '5', 5, 1, NOW(), 'system', '变更类型 - 其他'),

-- 变更状态
(NULL, NULL, 'contract_change_status', '草稿', '1', 1, 1, NOW(), 'system', '变更状态 - 草稿'),
(NULL, NULL, 'contract_change_status', '待审核', '2', 2, 1, NOW(), 'system', '变更状态 - 待审核'),
(NULL, NULL, 'contract_change_status', '已审核', '3', 3, 1, NOW(), 'system', '变更状态 - 已审核'),
(NULL, NULL, 'contract_change_status', '已生效', '4', 4, 1, NOW(), 'system', '变更状态 - 已生效'),
(NULL, NULL, 'contract_change_status', '已拒绝', '5', 5, 1, NOW(), 'system', '变更状态 - 已拒绝'),

-- 冲突类型
(NULL, NULL, 'contract_conflict_type', '利益冲突', '1', 1, 1, NOW(), 'system', '冲突类型 - 利益冲突'),
(NULL, NULL, 'contract_conflict_type', '时间冲突', '2', 2, 1, NOW(), 'system', '冲突类型 - 时间冲突'),
(NULL, NULL, 'contract_conflict_type', '资源冲突', '3', 3, 1, NOW(), 'system', '冲突类型 - 资源冲突'),
(NULL, NULL, 'contract_conflict_type', '条款冲突', '4', 4, 1, NOW(), 'system', '冲突类型 - 条款冲突'),
(NULL, NULL, 'contract_conflict_type', '其他', '5', 5, 1, NOW(), 'system', '冲突类型 - 其他'),

-- 冲突级别
(NULL, NULL, 'contract_conflict_level', '低', '1', 1, 1, NOW(), 'system', '冲突级别 - 低'),
(NULL, NULL, 'contract_conflict_level', '中', '2', 2, 1, NOW(), 'system', '冲突级别 - 中'),
(NULL, NULL, 'contract_conflict_level', '高', '3', 3, 1, NOW(), 'system', '冲突级别 - 高'),
(NULL, NULL, 'contract_conflict_level', '严重', '4', 4, 1, NOW(), 'system', '冲突级别 - 严重'),

-- 冲突状态
(NULL, NULL, 'contract_conflict_status', '待处理', '1', 1, 1, NOW(), 'system', '冲突状态 - 待处理'),
(NULL, NULL, 'contract_conflict_status', '处理中', '2', 2, 1, NOW(), 'system', '冲突状态 - 处理中'),
(NULL, NULL, 'contract_conflict_status', '已解决', '3', 3, 1, NOW(), 'system', '冲突状态 - 已解决'),
(NULL, NULL, 'contract_conflict_status', '已忽略', '4', 4, 1, NOW(), 'system', '冲突状态 - 已忽略'),

-- 保密级别
(NULL, NULL, 'contract_confidentiality_level', '公开', '1', 1, 1, NOW(), 'system', '保密级别 - 公开'),
(NULL, NULL, 'contract_confidentiality_level', '内部', '2', 2, 1, NOW(), 'system', '保密级别 - 内部'),
(NULL, NULL, 'contract_confidentiality_level', '机密', '3', 3, 1, NOW(), 'system', '保密级别 - 机密'),
(NULL, NULL, 'contract_confidentiality_level', '绝密', '4', 4, 1, NOW(), 'system', '保密级别 - 绝密'),

-- 附件类型
(NULL, NULL, 'contract_attachment_type', '合同文本', '1', 1, 1, NOW(), 'system', '附件类型 - 合同文本'),
(NULL, NULL, 'contract_attachment_type', '签署页', '2', 2, 1, NOW(), 'system', '附件类型 - 签署页'),
(NULL, NULL, 'contract_attachment_type', '补充协议', '3', 3, 1, NOW(), 'system', '附件类型 - 补充协议'),
(NULL, NULL, 'contract_attachment_type', '其他', '4', 4, 1, NOW(), 'system', '附件类型 - 其他'),

-- 计费周期
(NULL, NULL, 'contract_billing_cycle', '一次性', '1', 1, 1, NOW(), 'system', '计费周期 - 一次性'),
(NULL, NULL, 'contract_billing_cycle', '按月', '2', 2, 1, NOW(), 'system', '计费周期 - 按月'),
(NULL, NULL, 'contract_billing_cycle', '按季', '3', 3, 1, NOW(), 'system', '计费周期 - 按季'),
(NULL, NULL, 'contract_billing_cycle', '按年', '4', 4, 1, NOW(), 'system', '计费周期 - 按年')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 合同模板初始化 =======================

INSERT INTO contract_template (tenant_id, template_name, template_code, template_type, template_category, 
                              template_content, template_version, is_active, is_default, usage_count,
                              creator_name, approval_status, applicable_scope, usage_instructions,
                              status, create_time, create_by)
VALUES
(0, '法律顾问服务合同模板', 'LEGAL_ADVISOR_TEMPLATE', 2, '顾问服务',
'甲方（委托方）：{CLIENT_NAME}\n地址：{CLIENT_ADDRESS}\n联系电话：{CLIENT_PHONE}\n\n乙方（受托方）：{LAW_FIRM_NAME}\n地址：{LAW_FIRM_ADDRESS}\n联系电话：{LAW_FIRM_PHONE}\n\n根据《中华人民共和国民法典》及相关法律法规，甲乙双方本着平等自愿、诚实信用的原则，经协商一致，就乙方为甲方提供法律顾问服务事宜达成如下协议：\n\n第一条 服务内容\n1. 解答甲方提出的法律咨询\n2. 审查甲方的规章制度、合同文本等法律文件\n3. 代理甲方进行法律谈判\n4. 代理甲方处理纠纷及诉讼事务\n5. 其他双方约定的法律服务事项\n\n第二条 服务期限\n本合同服务期限为{CONTRACT_PERIOD}，自{START_DATE}起至{END_DATE}止。\n\n第三条 服务费用\n1. 顾问服务费：人民币{TOTAL_AMOUNT}元整（¥{TOTAL_AMOUNT_CN}）\n2. 支付方式：{PAYMENT_TERMS}\n\n第四条 双方权利义务\n（甲方权利义务及乙方权利义务条款）\n\n第五条 保密条款\n（保密义务相关条款）\n\n第六条 违约责任\n（违约责任相关条款）\n\n第七条 争议解决\n（争议解决方式相关条款）\n\n第八条 其他\n（其他约定条款）\n\n甲方（盖章）：           乙方（盖章）：\n代表人（签字）：         代表人（签字）：\n日期：                  日期：',
'1.0', 1, 1, 0, 'system', 2, '适用于常规法律顾问服务',
'1. 填写客户基本信息\n2. 确定服务期限和费用\n3. 根据具体情况调整服务内容\n4. 审核后生成正式合同',
1, NOW(), 'system'),

(0, '委托代理合同模板', 'LITIGATION_AGENT_TEMPLATE', 1, '诉讼代理',
'委托人：{CLIENT_NAME}\n受托人：{LAW_FIRM_NAME}\n\n根据《中华人民共和国民法典》《中华人民共和国律师法》及相关法律法规的规定，委托人与受托人本着自愿、平等、诚实信用的原则，就委托受托人办理诉讼事务达成如下协议：\n\n第一条 委托事项\n委托人委托受托人担任{CASE_NAME}案件的代理人，案由：{CASE_REASON}。\n\n第二条 委托权限\n{DELEGATION_POWER}\n\n第三条 委托期限\n自本合同签订之日起至{CASE_NAME}案件生效法律文书确定的权利义务履行完毕之日止。\n\n第四条 代理费用及支付方式\n1. 代理费：人民币{TOTAL_AMOUNT}元整（¥{TOTAL_AMOUNT_CN}）\n2. 支付方式：{PAYMENT_TERMS}\n\n第五条 双方权利义务\n（委托人权利义务及受托人权利义务条款）\n\n第六条 违约责任\n（违约责任相关条款）\n\n第七条 争议解决\n（争议解决方式相关条款）\n\n第八条 其他\n（其他约定条款）\n\n委托人（盖章）：         受托人（盖章）：\n代表人（签字）：         代表人（签字）：\n日期：                  日期：',
'1.0', 1, 1, 0, 'system', 2, '适用于诉讼案件代理',
'1. 填写案件基本信息\n2. 明确委托权限范围\n3. 确定代理费用\n4. 审核后生成正式合同',
1, NOW(), 'system'),

(0, '专项服务合同模板', 'SPECIAL_SERVICE_TEMPLATE', 3, '专项服务',
'甲方（委托方）：{CLIENT_NAME}\n乙方（服务方）：{LAW_FIRM_NAME}\n\n根据相关法律法规，甲乙双方就{SERVICE_NAME}专项法律服务事宜达成如下协议：\n\n第一条 服务内容\n{SERVICE_SCOPE}\n\n第二条 服务期限\n{SERVICE_PERIOD}\n\n第三条 服务费用\n总费用：人民币{TOTAL_AMOUNT}元整\n支付方式：{PAYMENT_TERMS}\n\n第四条 交付标准\n{DELIVERY_STANDARDS}\n\n第五条 其他条款\n（根据具体服务内容补充）\n\n甲方（盖章）：           乙方（盖章）：\n代表人（签字）：         代表人（签字）：\n日期：                  日期：',
'1.0', 1, 0, 0, 'system', 2, '适用于各类专项法律服务',
'1. 明确专项服务内容\n2. 设定交付标准\n3. 确定服务期限和费用\n4. 根据具体服务类型调整条款',
1, NOW(), 'system')
ON DUPLICATE KEY UPDATE template_name = VALUES(template_name); 