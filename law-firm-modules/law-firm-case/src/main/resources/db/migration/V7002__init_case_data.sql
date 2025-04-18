-- 案件模块初始化数据
-- 版本: V7002
-- 模块: case
-- 创建时间: 2023-10-01
-- 说明: 创建案件管理模块初始化数据

-- 案件类型初始化数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 案件类型
(NULL, NULL, 'case_type', '民事案件', '1', 1, 0, NOW(), 'system', '案件类型 - 民事案件'),
(NULL, NULL, 'case_type', '刑事案件', '2', 2, 0, NOW(), 'system', '案件类型 - 刑事案件'),
(NULL, NULL, 'case_type', '行政案件', '3', 3, 0, NOW(), 'system', '案件类型 - 行政案件'),
(NULL, NULL, 'case_type', '非诉讼', '4', 4, 0, NOW(), 'system', '案件类型 - 非诉讼'),
(NULL, NULL, 'case_type', '知识产权', '5', 5, 0, NOW(), 'system', '案件类型 - 知识产权'),
(NULL, NULL, 'case_type', '合同纠纷', '6', 6, 0, NOW(), 'system', '案件类型 - 合同纠纷'),
-- 案件状态
(NULL, NULL, 'case_status', '待受理', '0', 1, 0, NOW(), 'system', '案件状态 - 待受理'),
(NULL, NULL, 'case_status', '进行中', '1', 2, 0, NOW(), 'system', '案件状态 - 进行中'),
(NULL, NULL, 'case_status', '已结案', '2', 3, 0, NOW(), 'system', '案件状态 - 已结案'),
(NULL, NULL, 'case_status', '已关闭', '3', 4, 0, NOW(), 'system', '案件状态 - 已关闭'),
-- 案件难度
(NULL, NULL, 'case_difficulty', '简单', '1', 1, 0, NOW(), 'system', '案件难度 - 简单'),
(NULL, NULL, 'case_difficulty', '普通', '2', 2, 0, NOW(), 'system', '案件难度 - 普通'),
(NULL, NULL, 'case_difficulty', '复杂', '3', 3, 0, NOW(), 'system', '案件难度 - 复杂'),
(NULL, NULL, 'case_difficulty', '高度复杂', '4', 4, 0, NOW(), 'system', '案件难度 - 高度复杂'),
-- 案件重要性
(NULL, NULL, 'case_importance', '一般', '1', 1, 0, NOW(), 'system', '案件重要性 - 一般'),
(NULL, NULL, 'case_importance', '重要', '2', 2, 0, NOW(), 'system', '案件重要性 - 重要'),
(NULL, NULL, 'case_importance', '非常重要', '3', 3, 0, NOW(), 'system', '案件重要性 - 非常重要'),
-- 案件优先级
(NULL, NULL, 'case_priority', '低', '1', 1, 0, NOW(), 'system', '案件优先级 - 低'),
(NULL, NULL, 'case_priority', '中', '2', 2, 0, NOW(), 'system', '案件优先级 - 中'),
(NULL, NULL, 'case_priority', '高', '3', 3, 0, NOW(), 'system', '案件优先级 - 高'),
(NULL, NULL, 'case_priority', '紧急', '4', 4, 0, NOW(), 'system', '案件优先级 - 紧急'),
-- 案件来源
(NULL, NULL, 'case_source', '直接委托', '1', 1, 0, NOW(), 'system', '案件来源 - 直接委托'),
(NULL, NULL, 'case_source', '法院指定', '2', 2, 0, NOW(), 'system', '案件来源 - 法院指定'),
(NULL, NULL, 'case_source', '转介绍', '3', 3, 0, NOW(), 'system', '案件来源 - 转介绍'),
(NULL, NULL, 'case_source', '老客户', '4', 4, 0, NOW(), 'system', '案件来源 - 老客户'),
(NULL, NULL, 'case_source', '法律援助', '5', 5, 0, NOW(), 'system', '案件来源 - 法律援助'),
-- 办理方式
(NULL, NULL, 'case_handle_type', '诉讼', '1', 1, 0, NOW(), 'system', '办理方式 - 诉讼'),
(NULL, NULL, 'case_handle_type', '仲裁', '2', 2, 0, NOW(), 'system', '办理方式 - 仲裁'),
(NULL, NULL, 'case_handle_type', '调解', '3', 3, 0, NOW(), 'system', '办理方式 - 调解'),
(NULL, NULL, 'case_handle_type', '谈判', '4', 4, 0, NOW(), 'system', '办理方式 - 谈判'),
(NULL, NULL, 'case_handle_type', '法律咨询', '5', 5, 0, NOW(), 'system', '办理方式 - 法律咨询'),
-- 收费类型
(NULL, NULL, 'case_fee_type', '固定收费', '1', 1, 0, NOW(), 'system', '收费类型 - 固定收费'),
(NULL, NULL, 'case_fee_type', '计时收费', '2', 2, 0, NOW(), 'system', '收费类型 - 计时收费'),
(NULL, NULL, 'case_fee_type', '风险收费', '3', 3, 0, NOW(), 'system', '收费类型 - 风险收费'),
(NULL, NULL, 'case_fee_type', '混合收费', '4', 4, 0, NOW(), 'system', '收费类型 - 混合收费'),
(NULL, NULL, 'case_fee_type', '免费', '5', 5, 0, NOW(), 'system', '收费类型 - 免费'),
-- 案件结果
(NULL, NULL, 'case_result', '胜诉', '1', 1, 0, NOW(), 'system', '案件结果 - 胜诉'),
(NULL, NULL, 'case_result', '败诉', '2', 2, 0, NOW(), 'system', '案件结果 - 败诉'),
(NULL, NULL, 'case_result', '部分胜诉', '3', 3, 0, NOW(), 'system', '案件结果 - 部分胜诉'),
(NULL, NULL, 'case_result', '调解结案', '4', 4, 0, NOW(), 'system', '案件结果 - 调解结案'),
(NULL, NULL, 'case_result', '撤诉', '5', 5, 0, NOW(), 'system', '案件结果 - 撤诉'),
(NULL, NULL, 'case_result', '其他', '6', 6, 0, NOW(), 'system', '案件结果 - 其他'),
-- 团队成员角色
(NULL, NULL, 'team_member_role', '主办律师', '1', 1, 0, NOW(), 'system', '团队成员角色 - 主办律师'),
(NULL, NULL, 'team_member_role', '协办律师', '2', 2, 0, NOW(), 'system', '团队成员角色 - 协办律师'),
(NULL, NULL, 'team_member_role', '指导律师', '3', 3, 0, NOW(), 'system', '团队成员角色 - 指导律师'),
(NULL, NULL, 'team_member_role', '实习律师', '4', 4, 0, NOW(), 'system', '团队成员角色 - 实习律师'),
(NULL, NULL, 'team_member_role', '专家顾问', '5', 5, 0, NOW(), 'system', '团队成员角色 - 专家顾问'),
(NULL, NULL, 'team_member_role', '律所合伙人', '6', 6, 0, NOW(), 'system', '团队成员角色 - 律所合伙人'),
-- 案件参与方类型
(NULL, NULL, 'participant_type', '委托人', '1', 1, 0, NOW(), 'system', '参与方类型 - 委托人'),
(NULL, NULL, 'participant_type', '对方当事人', '2', 2, 0, NOW(), 'system', '参与方类型 - 对方当事人'),
(NULL, NULL, 'participant_type', '法官', '3', 3, 0, NOW(), 'system', '参与方类型 - 法官'),
(NULL, NULL, 'participant_type', '仲裁员', '4', 4, 0, NOW(), 'system', '参与方类型 - 仲裁员'),
(NULL, NULL, 'participant_type', '证人', '5', 5, 0, NOW(), 'system', '参与方类型 - 证人'),
(NULL, NULL, 'participant_type', '鉴定人', '6', 6, 0, NOW(), 'system', '参与方类型 - 鉴定人'),
(NULL, NULL, 'participant_type', '第三人', '7', 7, 0, NOW(), 'system', '参与方类型 - 第三人'),
-- 任务类型
(NULL, NULL, 'task_type', '文件准备', '1', 1, 0, NOW(), 'system', '任务类型 - 文件准备'),
(NULL, NULL, 'task_type', '法律研究', '2', 2, 0, NOW(), 'system', '任务类型 - 法律研究'),
(NULL, NULL, 'task_type', '客户沟通', '3', 3, 0, NOW(), 'system', '任务类型 - 客户沟通'),
(NULL, NULL, 'task_type', '证据收集', '4', 4, 0, NOW(), 'system', '任务类型 - 证据收集'),
(NULL, NULL, 'task_type', '出庭准备', '5', 5, 0, NOW(), 'system', '任务类型 - 出庭准备'),
(NULL, NULL, 'task_type', '文书起草', '6', 6, 0, NOW(), 'system', '任务类型 - 文书起草'),
(NULL, NULL, 'task_type', '会议', '7', 7, 0, NOW(), 'system', '任务类型 - 会议'),
-- 任务状态
(NULL, NULL, 'task_status', '待处理', '0', 1, 0, NOW(), 'system', '任务状态 - 待处理'),
(NULL, NULL, 'task_status', '进行中', '1', 2, 0, NOW(), 'system', '任务状态 - 进行中'),
(NULL, NULL, 'task_status', '已完成', '2', 3, 0, NOW(), 'system', '任务状态 - 已完成'),
(NULL, NULL, 'task_status', '已取消', '3', 4, 0, NOW(), 'system', '任务状态 - 已取消'),
(NULL, NULL, 'task_status', '已暂停', '4', 5, 0, NOW(), 'system', '任务状态 - 已暂停'),
(NULL, NULL, 'task_status', '待审核', '5', 6, 0, NOW(), 'system', '任务状态 - 待审核'),
-- 文档类型
(NULL, NULL, 'document_type', '起诉书', '1', 1, 0, NOW(), 'system', '文档类型 - 起诉书'),
(NULL, NULL, 'document_type', '答辩状', '2', 2, 0, NOW(), 'system', '文档类型 - 答辩状'),
(NULL, NULL, 'document_type', '证据材料', '3', 3, 0, NOW(), 'system', '文档类型 - 证据材料'),
(NULL, NULL, 'document_type', '判决书', '4', 4, 0, NOW(), 'system', '文档类型 - 判决书'),
(NULL, NULL, 'document_type', '法律意见书', '5', 5, 0, NOW(), 'system', '文档类型 - 法律意见书'),
(NULL, NULL, 'document_type', '委托合同', '6', 6, 0, NOW(), 'system', '文档类型 - 委托合同'),
(NULL, NULL, 'document_type', '会议记录', '7', 7, 0, NOW(), 'system', '文档类型 - 会议记录'),
-- 文档状态
(NULL, NULL, 'document_status', '草稿', '0', 1, 0, NOW(), 'system', '文档状态 - 草稿'),
(NULL, NULL, 'document_status', '待审核', '1', 2, 0, NOW(), 'system', '文档状态 - 待审核'),
(NULL, NULL, 'document_status', '已发布', '2', 3, 0, NOW(), 'system', '文档状态 - 已发布'),
(NULL, NULL, 'document_status', '已归档', '3', 4, 0, NOW(), 'system', '文档状态 - 已归档'),
(NULL, NULL, 'document_status', '已废弃', '4', 5, 0, NOW(), 'system', '文档状态 - 已废弃'),
-- 事件类型
(NULL, NULL, 'event_type', '开庭', '1', 1, 0, NOW(), 'system', '事件类型 - 开庭'),
(NULL, NULL, 'event_type', '客户会议', '2', 2, 0, NOW(), 'system', '事件类型 - 客户会议'),
(NULL, NULL, 'event_type', '团队讨论', '3', 3, 0, NOW(), 'system', '事件类型 - 团队讨论'),
(NULL, NULL, 'event_type', '调解会议', '4', 4, 0, NOW(), 'system', '事件类型 - 调解会议'),
(NULL, NULL, 'event_type', '证人约见', '5', 5, 0, NOW(), 'system', '事件类型 - 证人约见'),
(NULL, NULL, 'event_type', '文件递交', '6', 6, 0, NOW(), 'system', '事件类型 - 文件递交'),
(NULL, NULL, 'event_type', '截止日期', '7', 7, 0, NOW(), 'system', '事件类型 - 截止日期'),
-- 费用类型
(NULL, NULL, 'fee_type', '律师费', '1', 1, 0, NOW(), 'system', '费用类型 - 律师费'),
(NULL, NULL, 'fee_type', '诉讼费', '2', 2, 0, NOW(), 'system', '费用类型 - 诉讼费'),
(NULL, NULL, 'fee_type', '差旅费', '3', 3, 0, NOW(), 'system', '费用类型 - 差旅费'),
(NULL, NULL, 'fee_type', '公证费', '4', 4, 0, NOW(), 'system', '费用类型 - 公证费'),
(NULL, NULL, 'fee_type', '调查费', '5', 5, 0, NOW(), 'system', '费用类型 - 调查费'),
(NULL, NULL, 'fee_type', '鉴定费', '6', 6, 0, NOW(), 'system', '费用类型 - 鉴定费'),
(NULL, NULL, 'fee_type', '其他费用', '7', 7, 0, NOW(), 'system', '费用类型 - 其他费用')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化案件团队成员角色
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(NULL, NULL, '案件类型', 'case_type', 0, NOW(), 'system', '案件类型字典'),
(NULL, NULL, '案件状态', 'case_status', 0, NOW(), 'system', '案件状态字典'),
(NULL, NULL, '案件难度', 'case_difficulty', 0, NOW(), 'system', '案件难度字典'),
(NULL, NULL, '案件重要性', 'case_importance', 0, NOW(), 'system', '案件重要性字典'),
(NULL, NULL, '案件优先级', 'case_priority', 0, NOW(), 'system', '案件优先级字典'),
(NULL, NULL, '案件来源', 'case_source', 0, NOW(), 'system', '案件来源字典'),
(NULL, NULL, '办理方式', 'case_handle_type', 0, NOW(), 'system', '案件办理方式字典'),
(NULL, NULL, '收费类型', 'case_fee_type', 0, NOW(), 'system', '案件收费类型字典'),
(NULL, NULL, '案件结果', 'case_result', 0, NOW(), 'system', '案件结果字典'),
(NULL, NULL, '团队成员角色', 'team_member_role', 0, NOW(), 'system', '团队成员角色字典'),
(NULL, NULL, '参与方类型', 'participant_type', 0, NOW(), 'system', '案件参与方类型字典'),
(NULL, NULL, '任务类型', 'task_type', 0, NOW(), 'system', '任务类型字典'),
(NULL, NULL, '任务状态', 'task_status', 0, NOW(), 'system', '任务状态字典'),
(NULL, NULL, '文档类型', 'document_type', 0, NOW(), 'system', '文档类型字典'),
(NULL, NULL, '文档状态', 'document_status', 0, NOW(), 'system', '文档状态字典'),
(NULL, NULL, '事件类型', 'event_type', 0, NOW(), 'system', '事件类型字典'),
(NULL, NULL, '费用类型', 'fee_type', 0, NOW(), 'system', '费用类型字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;
