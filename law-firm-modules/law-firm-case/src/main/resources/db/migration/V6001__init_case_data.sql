-- 插入案件状态数据
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('案件状态', 'case_status', '案件处理的状态', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '新建', '1', '案件刚创建，未开始处理', 1, NOW(), NOW(), 0),
(@dict_id, '进行中', '2', '案件正在积极处理中', 2, NOW(), NOW(), 0),
(@dict_id, '暂停', '3', '因特殊原因暂时停止处理', 3, NOW(), NOW(), 0),
(@dict_id, '终止', '4', '案件非正常结束', 4, NOW(), NOW(), 0),
(@dict_id, '完成', '5', '案件已正常办结', 5, NOW(), NOW(), 0),
(@dict_id, '归档', '6', '案件已完成并归档', 6, NOW(), NOW(), 0);

-- 插入案件类型数据
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('案件类型', 'case_type', '律师事务所处理的案件类型', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '民事诉讼', '1', '处理民事纠纷的诉讼案件', 1, NOW(), NOW(), 0),
(@dict_id, '刑事诉讼', '2', '处理刑事犯罪的诉讼案件', 2, NOW(), NOW(), 0),
(@dict_id, '行政诉讼', '3', '处理行政纠纷的诉讼案件', 3, NOW(), NOW(), 0),
(@dict_id, '非诉讼', '4', '通过非诉讼方式解决的案件', 4, NOW(), NOW(), 0),
(@dict_id, '仲裁', '5', '通过仲裁方式解决的案件', 5, NOW(), NOW(), 0),
(@dict_id, '调解', '6', '通过调解方式解决的案件', 6, NOW(), NOW(), 0),
(@dict_id, '执行', '7', '判决或仲裁裁决的执行案件', 7, NOW(), NOW(), 0),
(@dict_id, '破产', '8', '破产清算、重组等案件', 8, NOW(), NOW(), 0),
(@dict_id, '法律顾问', '9', '担任法律顾问提供的服务', 9, NOW(), NOW(), 0),
(@dict_id, '其他', '99', '其他类型的案件', 99, NOW(), NOW(), 0);

-- 插入案件来源数据
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('案件来源', 'case_source', '案件的来源渠道', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '客户直接委托', '1', '客户直接联系律所委托', 1, NOW(), NOW(), 0),
(@dict_id, '律师介绍', '2', '通过其他律师介绍', 2, NOW(), NOW(), 0),
(@dict_id, '朋友介绍', '3', '通过朋友介绍', 3, NOW(), NOW(), 0),
(@dict_id, '老客户介绍', '4', '通过老客户介绍', 4, NOW(), NOW(), 0),
(@dict_id, '网络营销', '5', '通过网络营销获取', 5, NOW(), NOW(), 0),
(@dict_id, '法院指派', '6', '通过法院指派', 6, NOW(), NOW(), 0),
(@dict_id, '法律援助', '7', '法律援助案件', 7, NOW(), NOW(), 0),
(@dict_id, '其他', '99', '其他来源', 99, NOW(), NOW(), 0);

-- 插入办理方式数据
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('办理方式', 'handle_type', '案件的办理方式', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '全程代理', '1', '全程代理案件', 1, NOW(), NOW(), 0),
(@dict_id, '阶段代理', '2', '仅代理部分阶段', 2, NOW(), NOW(), 0),
(@dict_id, '法律意见', '3', '仅提供法律意见', 3, NOW(), NOW(), 0),
(@dict_id, '文书代拟', '4', '代为起草法律文书', 4, NOW(), NOW(), 0),
(@dict_id, '常年顾问', '5', '作为常年法律顾问处理', 5, NOW(), NOW(), 0),
(@dict_id, '联合办案', '6', '与其他律所联合办案', 6, NOW(), NOW(), 0),
(@dict_id, '指导代理', '7', '指导其他律师代理', 7, NOW(), NOW(), 0);

-- 插入收费类型数据
INSERT INTO sys_dict (dict_name, dict_code, description, created_time, updated_time, deleted) 
VALUES ('收费类型', 'fee_type', '案件的收费类型', NOW(), NOW(), 0);

-- 获取字典ID
SET @dict_id = LAST_INSERT_ID();

-- 插入字典项
INSERT INTO sys_dict_item (dict_id, item_name, item_value, description, sort, created_time, updated_time, deleted)
VALUES 
(@dict_id, '固定收费', '1', '案件固定收费', 1, NOW(), NOW(), 0),
(@dict_id, '计时收费', '2', '按工作时间计费', 2, NOW(), NOW(), 0),
(@dict_id, '风险收费', '3', '按结果收取风险代理费', 3, NOW(), NOW(), 0),
(@dict_id, '混合收费', '4', '固定+风险的混合收费', 4, NOW(), NOW(), 0),
(@dict_id, '阶段收费', '5', '分阶段收费', 5, NOW(), NOW(), 0),
(@dict_id, '减免收费', '6', '减免部分费用', 6, NOW(), NOW(), 0),
(@dict_id, '公益案件', '7', '公益案件不收费', 7, NOW(), NOW(), 0);

-- 插入示例案件数据
INSERT INTO case_info (
  case_number, case_name, case_description, case_type, case_status, 
  case_source, handle_type, fee_type, estimated_amount, client_name, 
  court_name, filing_time, tenant_id, create_by, update_by
) VALUES (
  'CAS20230001', '张三与李四合同纠纷案', '关于工程承包合同履行过程中产生的争议', 1, 2, 
  1, 1, 1, 50000.00, '张三', 
  '北京市朝阳区人民法院', '2023-01-15', 1, 'admin', 'admin'
); 