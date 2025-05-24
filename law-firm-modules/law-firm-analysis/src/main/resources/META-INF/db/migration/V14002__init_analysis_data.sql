-- 分析模块常用字典数据初始化
-- 版本: V14002
-- 仅供演示，实际可根据业务调整

-- 初始化分析类型及任务状态演示数据
INSERT INTO analysis_task (analysis_type, task_status, description, create_time, status, deleted)
VALUES 
  ('案件统计', '待处理', '系统初始化-案件统计', NOW(), 0, 0),
  ('客户分析', '待处理', '系统初始化-客户分析', NOW(), 0, 0),
  ('财务分析', '待处理', '系统初始化-财务分析', NOW(), 0, 0),
  ('案件统计', '进行中', '演示任务-进行中', NOW(), 0, 0),
  ('客户分析', '已完成', '演示任务-已完成', NOW(), 0, 0),
  ('财务分析', '失败', '演示任务-失败', NOW(), 0, 0);

-- 初始化历史任务（演示数据）
INSERT INTO analysis_task_history (task_id, analysis_type, exec_status, result_summary, start_time, end_time, create_time, status, deleted)
VALUES
  (1, '案件统计', '成功', '案件统计分析成功', NOW(), NOW(), NOW(), 0, 0),
  (2, '客户分析', '失败', '客户分析执行失败', NOW(), NOW(), NOW(), 0, 0); 