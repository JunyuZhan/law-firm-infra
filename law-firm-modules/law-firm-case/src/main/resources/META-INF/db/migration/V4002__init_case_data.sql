-- 案件管理模块基础数据
-- 版本: V4002
-- 模块: 案件管理模块 (V4000-V4999)
-- 创建时间: 2023-06-01
-- 说明: 案件管理功能的基础数据初始化
-- 包括：案件相关字典数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 字典类型初始化 =======================

INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, create_time, create_by, remark)
VALUES
(NULL, NULL, '案件类型', 'case_type', 1, NOW(), 'system', '案件类型字典'),
(NULL, NULL, '案件状态', 'case_status', 1, NOW(), 'system', '案件状态字典'),
(NULL, NULL, '案件阶段', 'case_stage', 1, NOW(), 'system', '案件阶段字典'),
(NULL, NULL, '案件优先级', 'case_priority', 1, NOW(), 'system', '案件优先级字典'),
(NULL, NULL, '案件难度', 'case_difficulty', 1, NOW(), 'system', '案件难度字典'),
(NULL, NULL, '案件来源', 'case_source', 1, NOW(), 'system', '案件来源字典'),
(NULL, NULL, '案件结果', 'case_result', 1, NOW(), 'system', '案件结果字典'),
(NULL, NULL, '费用类型', 'case_fee_type', 1, NOW(), 'system', '案件费用类型字典'),
(NULL, NULL, '收费方式', 'case_fee_category', 1, NOW(), 'system', '案件收费方式字典'),
(NULL, NULL, '费用状态', 'case_fee_status', 1, NOW(), 'system', '案件费用状态字典'),
(NULL, NULL, '团队角色', 'case_team_role', 1, NOW(), 'system', '案件团队角色字典'),
(NULL, NULL, '参与方类型', 'case_participant_type', 1, NOW(), 'system', '案件参与方类型字典'),
(NULL, NULL, '任务类型', 'case_task_type', 1, NOW(), 'system', '案件任务类型字典'),
(NULL, NULL, '任务状态', 'case_task_status', 1, NOW(), 'system', '案件任务状态字典'),
(NULL, NULL, '任务优先级', 'case_task_priority', 1, NOW(), 'system', '案件任务优先级字典'),
(NULL, NULL, '文档类型', 'case_document_type', 1, NOW(), 'system', '案件文档类型字典'),
(NULL, NULL, '文档状态', 'case_document_status', 1, NOW(), 'system', '案件文档状态字典'),
(NULL, NULL, '事件类型', 'case_event_type', 1, NOW(), 'system', '案件事件类型字典'),
(NULL, NULL, '事件状态', 'case_event_status', 1, NOW(), 'system', '案件事件状态字典')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- ======================= 字典数据初始化 =======================

INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, create_time, create_by, remark)
VALUES 
-- 案件类型
(NULL, NULL, 'case_type', '民事案件', '1', 1, 1, NOW(), 'system', '案件类型 - 民事案件'),
(NULL, NULL, 'case_type', '刑事案件', '2', 2, 1, NOW(), 'system', '案件类型 - 刑事案件'),
(NULL, NULL, 'case_type', '行政案件', '3', 3, 1, NOW(), 'system', '案件类型 - 行政案件'),
(NULL, NULL, 'case_type', '仲裁案件', '4', 4, 1, NOW(), 'system', '案件类型 - 仲裁案件'),
(NULL, NULL, 'case_type', '非诉业务', '5', 5, 1, NOW(), 'system', '案件类型 - 非诉业务'),

-- 案件状态
(NULL, NULL, 'case_status', '待受理', '1', 1, 1, NOW(), 'system', '案件状态 - 待受理'),
(NULL, NULL, 'case_status', '进行中', '2', 2, 1, NOW(), 'system', '案件状态 - 进行中'),
(NULL, NULL, 'case_status', '已结案', '3', 3, 1, NOW(), 'system', '案件状态 - 已结案'),
(NULL, NULL, 'case_status', '已关闭', '4', 4, 1, NOW(), 'system', '案件状态 - 已关闭'),

-- 案件阶段
(NULL, NULL, 'case_stage', '立案', '1', 1, 1, NOW(), 'system', '案件阶段 - 立案'),
(NULL, NULL, 'case_stage', '调查', '2', 2, 1, NOW(), 'system', '案件阶段 - 调查'),
(NULL, NULL, 'case_stage', '开庭', '3', 3, 1, NOW(), 'system', '案件阶段 - 开庭'),
(NULL, NULL, 'case_stage', '执行', '4', 4, 1, NOW(), 'system', '案件阶段 - 执行'),
(NULL, NULL, 'case_stage', '结案', '5', 5, 1, NOW(), 'system', '案件阶段 - 结案'),

-- 案件优先级
(NULL, NULL, 'case_priority', '低', '1', 1, 1, NOW(), 'system', '案件优先级 - 低'),
(NULL, NULL, 'case_priority', '中', '2', 2, 1, NOW(), 'system', '案件优先级 - 中'),
(NULL, NULL, 'case_priority', '高', '3', 3, 1, NOW(), 'system', '案件优先级 - 高'),
(NULL, NULL, 'case_priority', '紧急', '4', 4, 1, NOW(), 'system', '案件优先级 - 紧急'),

-- 案件难度
(NULL, NULL, 'case_difficulty', '简单', '1', 1, 1, NOW(), 'system', '案件难度 - 简单'),
(NULL, NULL, 'case_difficulty', '一般', '2', 2, 1, NOW(), 'system', '案件难度 - 一般'),
(NULL, NULL, 'case_difficulty', '复杂', '3', 3, 1, NOW(), 'system', '案件难度 - 复杂'),
(NULL, NULL, 'case_difficulty', '疑难', '4', 4, 1, NOW(), 'system', '案件难度 - 疑难'),

-- 案件来源
(NULL, NULL, 'case_source', '新客户', '1', 1, 1, NOW(), 'system', '案件来源 - 新客户'),
(NULL, NULL, 'case_source', '老客户', '2', 2, 1, NOW(), 'system', '案件来源 - 老客户'),
(NULL, NULL, 'case_source', '转介绍', '3', 3, 1, NOW(), 'system', '案件来源 - 转介绍'),
(NULL, NULL, 'case_source', '法律援助', '4', 4, 1, NOW(), 'system', '案件来源 - 法律援助'),

-- 案件结果
(NULL, NULL, 'case_result', '胜诉', '1', 1, 1, NOW(), 'system', '案件结果 - 胜诉'),
(NULL, NULL, 'case_result', '败诉', '2', 2, 1, NOW(), 'system', '案件结果 - 败诉'),
(NULL, NULL, 'case_result', '和解', '3', 3, 1, NOW(), 'system', '案件结果 - 和解'),
(NULL, NULL, 'case_result', '调解', '4', 4, 1, NOW(), 'system', '案件结果 - 调解'),
(NULL, NULL, 'case_result', '撤诉', '5', 5, 1, NOW(), 'system', '案件结果 - 撤诉'),

-- 费用类型
(NULL, NULL, 'case_fee_type', '律师费', '1', 1, 1, NOW(), 'system', '费用类型 - 律师费'),
(NULL, NULL, 'case_fee_type', '诉讼费', '2', 2, 1, NOW(), 'system', '费用类型 - 诉讼费'),
(NULL, NULL, 'case_fee_type', '差旅费', '3', 3, 1, NOW(), 'system', '费用类型 - 差旅费'),
(NULL, NULL, 'case_fee_type', '其他费用', '4', 4, 1, NOW(), 'system', '费用类型 - 其他费用'),

-- 收费方式
(NULL, NULL, 'case_fee_category', '固定费用', '1', 1, 1, NOW(), 'system', '收费方式 - 固定费用'),
(NULL, NULL, 'case_fee_category', '按小时', '2', 2, 1, NOW(), 'system', '收费方式 - 按小时'),
(NULL, NULL, 'case_fee_category', '按比例', '3', 3, 1, NOW(), 'system', '收费方式 - 按比例'),
(NULL, NULL, 'case_fee_category', '风险代理', '4', 4, 1, NOW(), 'system', '收费方式 - 风险代理'),

-- 费用状态
(NULL, NULL, 'case_fee_status', '待收', '1', 1, 1, NOW(), 'system', '费用状态 - 待收'),
(NULL, NULL, 'case_fee_status', '部分收取', '2', 2, 1, NOW(), 'system', '费用状态 - 部分收取'),
(NULL, NULL, 'case_fee_status', '已收取', '3', 3, 1, NOW(), 'system', '费用状态 - 已收取'),
(NULL, NULL, 'case_fee_status', '减免', '4', 4, 1, NOW(), 'system', '费用状态 - 减免'),

-- 团队角色
(NULL, NULL, 'case_team_role', '主办律师', '1', 1, 1, NOW(), 'system', '团队角色 - 主办律师'),
(NULL, NULL, 'case_team_role', '协办律师', '2', 2, 1, NOW(), 'system', '团队角色 - 协办律师'),
(NULL, NULL, 'case_team_role', '律师助理', '3', 3, 1, NOW(), 'system', '团队角色 - 律师助理'),
(NULL, NULL, 'case_team_role', '实习生', '4', 4, 1, NOW(), 'system', '团队角色 - 实习生'),

-- 参与方类型
(NULL, NULL, 'case_participant_type', '原告', '1', 1, 1, NOW(), 'system', '参与方类型 - 原告'),
(NULL, NULL, 'case_participant_type', '被告', '2', 2, 1, NOW(), 'system', '参与方类型 - 被告'),
(NULL, NULL, 'case_participant_type', '第三人', '3', 3, 1, NOW(), 'system', '参与方类型 - 第三人'),
(NULL, NULL, 'case_participant_type', '证人', '4', 4, 1, NOW(), 'system', '参与方类型 - 证人'),
(NULL, NULL, 'case_participant_type', '鉴定人', '5', 5, 1, NOW(), 'system', '参与方类型 - 鉴定人'),

-- 任务类型
(NULL, NULL, 'case_task_type', '文件准备', '1', 1, 1, NOW(), 'system', '任务类型 - 文件准备'),
(NULL, NULL, 'case_task_type', '法律研究', '2', 2, 1, NOW(), 'system', '任务类型 - 法律研究'),
(NULL, NULL, 'case_task_type', '客户沟通', '3', 3, 1, NOW(), 'system', '任务类型 - 客户沟通'),
(NULL, NULL, 'case_task_type', '证据收集', '4', 4, 1, NOW(), 'system', '任务类型 - 证据收集'),
(NULL, NULL, 'case_task_type', '出庭准备', '5', 5, 1, NOW(), 'system', '任务类型 - 出庭准备'),

-- 任务状态
(NULL, NULL, 'case_task_status', '待处理', '1', 1, 1, NOW(), 'system', '任务状态 - 待处理'),
(NULL, NULL, 'case_task_status', '进行中', '2', 2, 1, NOW(), 'system', '任务状态 - 进行中'),
(NULL, NULL, 'case_task_status', '已完成', '3', 3, 1, NOW(), 'system', '任务状态 - 已完成'),
(NULL, NULL, 'case_task_status', '已取消', '4', 4, 1, NOW(), 'system', '任务状态 - 已取消'),

-- 任务优先级
(NULL, NULL, 'case_task_priority', '低', '1', 1, 1, NOW(), 'system', '任务优先级 - 低'),
(NULL, NULL, 'case_task_priority', '中', '2', 2, 1, NOW(), 'system', '任务优先级 - 中'),
(NULL, NULL, 'case_task_priority', '高', '3', 3, 1, NOW(), 'system', '任务优先级 - 高'),
(NULL, NULL, 'case_task_priority', '紧急', '4', 4, 1, NOW(), 'system', '任务优先级 - 紧急'),

-- 文档类型
(NULL, NULL, 'case_document_type', '起诉书', '1', 1, 1, NOW(), 'system', '文档类型 - 起诉书'),
(NULL, NULL, 'case_document_type', '答辩状', '2', 2, 1, NOW(), 'system', '文档类型 - 答辩状'),
(NULL, NULL, 'case_document_type', '证据材料', '3', 3, 1, NOW(), 'system', '文档类型 - 证据材料'),
(NULL, NULL, 'case_document_type', '判决书', '4', 4, 1, NOW(), 'system', '文档类型 - 判决书'),
(NULL, NULL, 'case_document_type', '法律意见书', '5', 5, 1, NOW(), 'system', '文档类型 - 法律意见书'),

-- 文档状态
(NULL, NULL, 'case_document_status', '草稿', '1', 1, 1, NOW(), 'system', '文档状态 - 草稿'),
(NULL, NULL, 'case_document_status', '待审核', '2', 2, 1, NOW(), 'system', '文档状态 - 待审核'),
(NULL, NULL, 'case_document_status', '已发布', '3', 3, 1, NOW(), 'system', '文档状态 - 已发布'),
(NULL, NULL, 'case_document_status', '已归档', '4', 4, 1, NOW(), 'system', '文档状态 - 已归档'),

-- 事件类型
(NULL, NULL, 'case_event_type', '开庭', '1', 1, 1, NOW(), 'system', '事件类型 - 开庭'),
(NULL, NULL, 'case_event_type', '客户会议', '2', 2, 1, NOW(), 'system', '事件类型 - 客户会议'),
(NULL, NULL, 'case_event_type', '团队讨论', '3', 3, 1, NOW(), 'system', '事件类型 - 团队讨论'),
(NULL, NULL, 'case_event_type', '调解会议', '4', 4, 1, NOW(), 'system', '事件类型 - 调解会议'),
(NULL, NULL, 'case_event_type', '证人约见', '5', 5, 1, NOW(), 'system', '事件类型 - 证人约见'),

-- 事件状态
(NULL, NULL, 'case_event_status', '计划中', '1', 1, 1, NOW(), 'system', '事件状态 - 计划中'),
(NULL, NULL, 'case_event_status', '进行中', '2', 2, 1, NOW(), 'system', '事件状态 - 进行中'),
(NULL, NULL, 'case_event_status', '已完成', '3', 3, 1, NOW(), 'system', '事件状态 - 已完成'),
(NULL, NULL, 'case_event_status', '已取消', '4', 4, 1, NOW(), 'system', '事件状态 - 已取消')
ON DUPLICATE KEY UPDATE update_time = NOW(); 