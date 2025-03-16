-- 创建合同模板表（如果不存在）
CREATE TABLE IF NOT EXISTS `contract_template` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_name` varchar(100) NOT NULL COMMENT '模板名称',
    `template_code` varchar(50) NOT NULL COMMENT '模板编码',
    `category` tinyint(4) NOT NULL COMMENT '模板类别（1-标准合同 2-定制合同 3-框架协议）',
    `content` text NOT NULL COMMENT '模板内容',
    `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（0-禁用 1-启用）',
    `description` varchar(500) DEFAULT NULL COMMENT '模板描述',
    `version` varchar(20) NOT NULL DEFAULT '1.0' COMMENT '模板版本',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同模板表';

-- 初始化合同模板数据
INSERT INTO `contract_template` (`template_name`, `template_code`, `category`, `content`, `status`, `description`, `version`, `create_by`) VALUES
('标准法律顾问合同', 'CT_LEGAL_ADVISOR', 1, '甲方：__________\n乙方：__________\n\n一、服务内容\n1.1 甲方委托乙方担任法律顾问，提供法律服务。\n1.2 乙方接受委托，担任甲方法律顾问，提供下列法律服务：\n   (1) 解答甲方提出的法律咨询；\n   (2) 审查甲方的规章制度、经济合同等法律文书；\n   (3) 参与甲方内部重大决策的法律论证；\n   (4) 协助甲方处理经济纠纷和其他法律事务。\n\n二、服务方式\n2.1 乙方律师可以通过电话、传真、电子邮件、微信等方式解答甲方的咨询。\n2.2 根据甲方要求，乙方律师每月到甲方办公场所提供现场服务不少于___次，每次不少于___小时。\n\n三、服务期限\n3.1 本合同服务期为___年，自____年__月__日起至____年__月__日止。\n\n四、顾问费及支付方式\n4.1 甲方向乙方支付法律顾问费人民币___元/年。\n4.2 支付方式：____________。\n\n五、双方的权利和义务\n5.1 甲方的权利和义务：\n   (1) 甲方有权要求乙方提供本合同约定的法律服务；\n   (2) 甲方应当向乙方如实提供与法律服务有关的资料和情况；\n   (3) 甲方应当按时支付顾问费。\n\n5.2 乙方的权利和义务：\n   (1) 乙方有权了解与法律服务有关的资料和情况；\n   (2) 乙方应当勤勉尽责，及时提供法律服务；\n   (3) 乙方对甲方提供的资料和情况负有保密义务。\n\n六、违约责任\n6.1 甲方未按时支付顾问费的，应当支付违约金。违约金按照延期支付金额的____%计算。\n6.2 乙方未按约定提供法律服务的，应当减免相应的顾问费。\n\n七、合同的变更和解除\n7.1 经双方协商一致，可以变更或解除本合同。\n7.2 有下列情形之一的，任何一方可以解除本合同：\n   (1) 因不可抗力致使合同目的无法实现的；\n   (2) 另一方严重违约，致使合同目的无法实现的。\n\n八、争议解决\n8.1 双方因履行本合同发生争议的，应当协商解决；协商不成的，可以向有管辖权的人民法院提起诉讼。\n\n九、其他\n9.1 本合同一式两份，甲乙双方各执一份，具有同等法律效力。\n9.2 本合同自双方签字盖章之日起生效。\n\n甲方（盖章）：          乙方（盖章）：\n代表人（签字）：        代表人（签字）：\n日期：                  日期：', 1, '标准法律顾问服务合同模板', '1.0', 1),

('律师委托代理合同', 'CT_LEGAL_AGENT', 1, '委托人（甲方）：__________\n受托人（乙方）：__________\n\n甲方因____________一案，委托乙方担任其诉讼代理人，双方经协商一致，订立如下合同：\n\n一、甲方委托乙方办理的法律事务范围\n1.1 代理甲方参加本案诉讼活动，包括但不限于：提起诉讼，参加庭审，调查取证，收集并提交证据材料，参加调解，申请强制执行等。\n1.2 案件名称：____________________________\n1.3 案件编号：____________________________\n1.4 审理法院：____________________________\n\n二、乙方的权限\n2.1 乙方的代理权限为【 】特别授权代理【 】普通授权代理\n2.2 特别授权包括：代为承认、放弃、变更诉讼请求，进行和解，提起反诉或者上诉。\n\n三、委托代理费及支付方式\n3.1 委托代理费总额为人民币________元。\n3.2 支付方式：\n   (1) 首期费用：签订本合同时，甲方向乙方支付人民币________元；\n   (2) 尾期费用：案件一审判决后，甲方向乙方支付人民币________元。\n\n四、双方的权利和义务\n4.1 甲方的权利和义务：\n   (1) 向乙方如实陈述案件事实，提供与案件有关的真实证据材料；\n   (2) 按期支付委托代理费；\n   (3) 有权了解乙方办案进展情况；\n   (4) 有权对乙方的代理行为提出要求，但不得要求乙方违反法律规定或律师职业道德。\n\n4.2 乙方的权利和义务：\n   (1) 认真研究案情，制定合理的代理方案；\n   (2) 及时向甲方通报案件进展情况；\n   (3) 勤勉尽责，维护甲方的合法权益；\n   (4) 对甲方提供的材料和案件情况保密。\n\n五、风险提示\n5.1 乙方不对诉讼结果作出任何承诺或保证。\n5.2 诉讼结果取决于法院的审理和判决，存在不确定性。\n\n六、合同变更与解除\n6.1 经双方协商一致，可以变更或解除本合同。\n6.2 有下列情形之一的，乙方有权解除合同：\n   (1) 甲方故意隐瞒与案件有关的重要事实或者提供虚假证据材料；\n   (2) 甲方要求乙方违反法律规定或律师职业道德；\n   (3) 甲方未按约定支付委托代理费。\n\n七、违约责任\n7.1 甲方未按时支付代理费的，每逾期一日，按应付未付金额的__%支付违约金。\n7.2 乙方无正当理由中途退出代理的，应当退还甲方已支付的部分代理费，并承担相应的违约责任。\n\n八、争议解决\n8.1 双方因履行本合同发生争议的，应当协商解决；协商不成的，可以向合同履行地人民法院提起诉讼。\n\n九、其他事项\n9.1 本合同一式两份，甲乙双方各执一份，具有同等法律效力。\n9.2 本合同自双方签字盖章之日起生效。\n9.3 本合同未尽事宜，双方可另行协商，签订补充协议。补充协议与本合同具有同等法律效力。\n\n委托人（甲方）：         受托人（乙方）：\n签字：                   签字：\n日期：                   日期：', 1, '标准律师委托代理合同模板', '1.0', 1),

('知识产权许可合同', 'CT_IP_LICENSE', 1, '许可方（甲方）：__________\n被许可方（乙方）：__________\n\n鉴于甲方是________的合法权利人，现甲方同意授权乙方在本合同约定的范围内使用该知识产权，双方经友好协商，达成如下协议：\n\n一、知识产权基本情况\n1.1 知识产权名称：__________\n1.2 知识产权类型：【 】专利权【 】商标权【 】著作权【 】其他：__________\n1.3 注册/登记号：__________\n1.4 有效期限：自____年__月__日至____年__月__日\n\n二、许可范围\n2.1 许可方式：【 】独占许可【 】排他许可【 】普通许可\n2.2 许可内容：__________\n2.3 地域范围：__________\n2.4 许可期限：自____年__月__日至____年__月__日\n\n三、许可使用费及支付方式\n3.1 许可使用费总额为人民币________元。\n3.2 支付方式：\n   【 】一次性支付：乙方应在本合同生效后____日内一次性支付全部许可使用费。\n   【 】分期支付：__________\n   【 】提成支付：__________\n\n四、双方的权利和义务\n4.1 甲方的权利和义务：\n   (1) 保证其对所许可的知识产权享有合法权利；\n   (2) 在合同期限内维护知识产权的有效性；\n   (3) 按约定向乙方提供必要的技术资料和支持；\n   (4) 对乙方的合理使用不得干涉。\n\n4.2 乙方的权利和义务：\n   (1) 按约定支付许可使用费；\n   (2) 在约定的许可范围内使用知识产权，不得超出；\n   (3) 未经甲方书面同意，不得向第三方转让或分许可；\n   (4) 发现知识产权被侵权时，应当及时通知甲方。\n\n五、保密义务\n5.1 双方对在合作过程中获知的对方商业秘密和技术信息负有保密义务。\n5.2 保密期限：自本合同签订之日起至知识产权保护期限届满后____年。\n\n六、违约责任\n6.1 乙方未按时支付许可使用费的，每逾期一日，按应付未付金额的__%支付违约金。\n6.2 乙方超出许可范围使用知识产权的，应当停止侵权行为，赔偿甲方损失，并支付合同总金额____%的违约金。\n6.3 甲方违反保证义务，导致乙方无法正常使用知识产权的，应当退还相应的许可使用费，并赔偿乙方的直接损失。\n\n七、合同的变更与解除\n7.1 经双方协商一致，可以变更或解除本合同。\n7.2 有下列情形之一的，一方可以解除合同：\n   (1) 另一方严重违约，致使合同目的无法实现；\n   (2) 知识产权被宣告无效或提前终止；\n   (3) 不可抗力导致合同无法履行。\n\n八、争议解决\n8.1 双方因履行本合同发生争议的，应当协商解决；协商不成的，可以向________仲裁委员会申请仲裁。\n\n九、其他事项\n9.1 本合同一式两份，甲乙双方各执一份，具有同等法律效力。\n9.2 本合同自双方签字盖章之日起生效。\n\n许可方（甲方）：         被许可方（乙方）：\n代表人签字：             代表人签字：\n日期：                   日期：', 1, '知识产权许可合同模板', '1.0', 1);

-- 创建合同模板变量表（如果不存在）
CREATE TABLE IF NOT EXISTS `contract_template_variable` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_id` bigint(20) NOT NULL COMMENT '模板ID',
    `variable_name` varchar(50) NOT NULL COMMENT '变量名称',
    `variable_key` varchar(50) NOT NULL COMMENT '变量键',
    `variable_type` varchar(20) NOT NULL COMMENT '变量类型（text文本/number数字/date日期/select选择）',
    `default_value` varchar(200) DEFAULT NULL COMMENT '默认值',
    `is_required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否必填（0否1是）',
    `options` varchar(500) DEFAULT NULL COMMENT '选项（JSON格式，针对select类型）',
    `description` varchar(200) DEFAULT NULL COMMENT '变量描述',
    `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_template_id` (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同模板变量表';

-- 初始化基础模板变量
INSERT INTO `contract_template_variable` (`template_id`, `variable_name`, `variable_key`, `variable_type`, `default_value`, `is_required`, `description`, `order_num`, `create_by`) 
SELECT 
    id AS template_id,
    '甲方名称', 'party_a_name', 'text', NULL, 1, '合同甲方名称', 1, 1
FROM 
    `contract_template`
UNION ALL
SELECT 
    id AS template_id,
    '乙方名称', 'party_b_name', 'text', NULL, 1, '合同乙方名称', 2, 1
FROM 
    `contract_template`
UNION ALL
SELECT 
    id AS template_id,
    '合同金额', 'contract_amount', 'number', NULL, 1, '合同总金额', 3, 1
FROM 
    `contract_template`
UNION ALL
SELECT 
    id AS template_id,
    '开始日期', 'start_date', 'date', NULL, 1, '合同开始日期', 4, 1
FROM 
    `contract_template`
UNION ALL
SELECT 
    id AS template_id,
    '结束日期', 'end_date', 'date', NULL, 1, '合同结束日期', 5, 1
FROM 
    `contract_template`;
