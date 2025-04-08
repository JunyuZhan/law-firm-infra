-- 知识管理模块初始数据 V9001

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

-- 插入示例知识文档
INSERT INTO `knowledge_document` (
  `id`, `title`, `content`, `summary`, `keywords`, `category_id`, 
  `knowledge_type`, `view_count`, `like_count`, `author_id`, `author_name`, 
  `status`, `tenant_id`, `create_time`, `update_time`
)
VALUES
(1, '民事起诉状标准模板', '<h1>民事起诉状</h1><p>原告：姓名，性别，出生年月日，民族，籍贯，职业，工作单位，住址。</p><p>被告：姓名，性别，出生年月日，民族，籍贯，职业，工作单位，住址。</p><h2>诉讼请求</h2><p>1. 请求判令被告赔偿原告经济损失人民币元；</p><p>2. 本案诉讼费用由被告承担。</p><h2>事实与理由</h2><p>（此处描述事实经过和提出诉讼的法律依据）</p><p>此致</p><p>XXX人民法院</p><p>具状人：XXX</p><p>年月日</p>', '民事诉讼起诉状标准格式及撰写要点说明', '民事诉讼,起诉状,格式,模板', 6, 'DOCUMENT_TEMPLATE', 156, 32, 1, '张律师', 1, 1, NOW(), NOW()),

(2, '劳动仲裁案件代理工作流程指南', '<h1>劳动仲裁案件办理流程</h1><h2>一、接案阶段</h2><p>1. 与当事人见面了解基本情况</p><p>2. 审查劳动合同、工资记录等相关证据</p><p>3. 评估胜诉可能性和案件风险</p><p>4. 签订委托代理协议</p><h2>二、准备阶段</h2><p>1. 制作仲裁申请书</p><p>2. 整理和补充证据材料</p><p>3. 准备代理词</p><h2>三、仲裁阶段</h2><p>1. 参加开庭</p><p>2. 质证和辩论</p><p>3. 跟进仲裁结果</p><h2>四、后续处理</h2><p>1. 仲裁裁决执行</p><p>2. 必要时准备提起诉讼</p>', '劳动仲裁案件代理全流程指导文档，帮助律师规范化处理劳动争议案件', '劳动仲裁,劳动争议,代理流程,工作指南', 2, 'WORK_GUIDE', 78, 15, 2, '李律师', 1, 1, NOW(), NOW()),

(3, '合同审查关键点分析指南', '<h1>合同审查要点</h1><h2>一、合同主体</h2><p>1. 确认合同主体资格</p><p>2. 审查主体权利能力和行为能力</p><p>3. 核实主体资质证明文件</p><h2>二、合同内容</h2><p>1. 标的物是否明确</p><p>2. 权利义务是否对等</p><p>3. 违约责任是否合理</p><p>4. 争议解决条款是否明确</p><h2>三、合同形式</h2><p>1. 签署程序是否合法</p><p>2. 特殊合同是否符合法定形式</p><h2>四、常见风险防范</h2><p>1. 合同陷阱识别</p><p>2. 不平等条款调整建议</p>', '提供全面的合同审查指南，包括主体资格、合同条款、风险防范等关键点分析', '合同审查,法律风险,合同条款,风险防范', 3, 'BUSINESS_STANDARD', 120, 45, 3, '王律师', 1, 1, NOW(), NOW());

-- 为示例知识文档添加标签关联
INSERT INTO `knowledge_tag_relation` (
  `knowledge_id`, `tag_id`, `tenant_id`, `create_time`
)
VALUES
(1, 1, 1, NOW()),  -- 民事起诉状 关联 民事诉讼标签
(2, 5, 1, NOW()),  -- 劳动仲裁工作流程 关联 劳动纠纷标签
(3, 4, 1, NOW()),  -- 合同审查指南 关联 合同纠纷标签
(3, 8, 1, NOW());  -- 合同审查指南 关联 知识产权标签

-- 为示例知识文档添加附件
INSERT INTO `knowledge_attachment` (
  `id`, `knowledge_id`, `file_name`, `file_path`, 
  `file_size`, `file_type`, `sort`, `storage_id`, 
  `tenant_id`, `create_time`, `update_time`
)
VALUES
(1, 1, '民事起诉状模板.docx', '/files/knowledge/templates/civil_complaint_template.docx', 25600, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', 1, 'STORAGE_ID_1', 1, NOW(), NOW()),
(2, 2, '劳动仲裁流程图.pdf', '/files/knowledge/guides/labor_arbitration_flowchart.pdf', 153600, 'application/pdf', 1, 'STORAGE_ID_2', 1, NOW(), NOW()),
(3, 3, '合同审查清单.xlsx', '/files/knowledge/standards/contract_review_checklist.xlsx', 18400, 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 1, 'STORAGE_ID_3', 1, NOW(), NOW()); 