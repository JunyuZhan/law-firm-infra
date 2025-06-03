-- 数据分析模块基础数据
-- 版本: V14002
-- 模块: 数据分析模块 (V14000-V14999)
-- 创建时间: 2023-06-01
-- 说明: 数据分析功能的基础字典数据和初始化数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统字典类型 =======================

-- 数据分析相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES
('分析任务类型', 'analysis_task_type', 1, 'system', NOW(), '分析任务类型字典'),
('分析类别', 'analysis_category', 1, 'system', NOW(), '分析类别字典'),
('任务状态', 'analysis_task_status', 1, 'system', NOW(), '分析任务状态字典'),
('任务优先级', 'analysis_priority', 1, 'system', NOW(), '分析任务优先级字典'),
('调度类型', 'schedule_type', 1, 'system', NOW(), '调度类型字典'),
('结果类型', 'result_type', 1, 'system', NOW(), '分析结果类型字典'),
('结果格式', 'result_format', 1, 'system', NOW(), '分析结果格式字典'),
('指标类型', 'indicator_type', 1, 'system', NOW(), '分析指标类型字典'),
('数据类型', 'data_type', 1, 'system', NOW(), '数据类型字典'),
('聚合类型', 'aggregation_type', 1, 'system', NOW(), '聚合类型字典'),
('时间粒度', 'time_granularity', 1, 'system', NOW(), '时间粒度字典'),
('维度类型', 'dimension_type', 1, 'system', NOW(), '分析维度类型字典'),
('公式类型', 'formula_type', 1, 'system', NOW(), '计算公式类型字典'),
('复杂度级别', 'complexity_level', 1, 'system', NOW(), '复杂度级别字典'),
('报表类型', 'report_type', 1, 'system', NOW(), '报表类型字典'),
('图表类型', 'chart_type', 1, 'system', NOW(), '图表类型字典'),
('图表库', 'chart_library', 1, 'system', NOW(), '图表库字典'),
('布局类型', 'layout_type', 1, 'system', NOW(), '布局类型字典'),
('数据源类型', 'datasource_type', 1, 'system', NOW(), '数据源类型字典'),
('连接状态', 'connection_status', 1, 'system', NOW(), '连接状态字典'),
('数据集类型', 'dataset_type', 1, 'system', NOW(), '数据集类型字典'),
('刷新模式', 'refresh_mode', 1, 'system', NOW(), '刷新模式字典'),
('缓存类型', 'cache_type', 1, 'system', NOW(), '缓存类型字典'),
('压缩类型', 'compression_type', 1, 'system', NOW(), '压缩类型字典'),
('模板类型', 'template_type', 1, 'system', NOW(), '模板类型字典'),
('执行状态', 'execution_status', 1, 'system', NOW(), '执行状态字典');

-- ======================= 系统字典数据 =======================

-- 分析任务类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '业务分析', '1', 'analysis_task_type', '', 'primary', 'Y', 1, 'system', NOW(), '业务数据分析'),
(2, '财务分析', '2', 'analysis_task_type', '', 'success', 'N', 1, 'system', NOW(), '财务数据分析'),
(3, '客户分析', '3', 'analysis_task_type', '', 'info', 'N', 1, 'system', NOW(), '客户行为分析'),
(4, '案件分析', '4', 'analysis_task_type', '', 'warning', 'N', 1, 'system', NOW(), '案件效率分析'),
(5, '绩效分析', '5', 'analysis_task_type', '', 'danger', 'N', 1, 'system', NOW(), '绩效评估分析');

-- 分析类别
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '统计分析', '1', 'analysis_category', '', 'primary', 'Y', 1, 'system', NOW(), '基础统计分析'),
(2, '趋势分析', '2', 'analysis_category', '', 'success', 'N', 1, 'system', NOW(), '趋势变化分析'),
(3, '对比分析', '3', 'analysis_category', '', 'info', 'N', 1, 'system', NOW(), '对比分析'),
(4, '预测分析', '4', 'analysis_category', '', 'warning', 'N', 1, 'system', NOW(), '预测性分析');

-- 任务状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待执行', '1', 'analysis_task_status', '', 'secondary', 'Y', 1, 'system', NOW(), '等待执行'),
(2, '执行中', '2', 'analysis_task_status', '', 'primary', 'N', 1, 'system', NOW(), '正在执行'),
(3, '已完成', '3', 'analysis_task_status', '', 'success', 'N', 1, 'system', NOW(), '执行完成'),
(4, '失败', '4', 'analysis_task_status', '', 'danger', 'N', 1, 'system', NOW(), '执行失败'),
(5, '已暂停', '5', 'analysis_task_status', '', 'warning', 'N', 1, 'system', NOW(), '暂停执行'),
(6, '已取消', '6', 'analysis_task_status', '', 'dark', 'N', 1, 'system', NOW(), '取消执行');

-- 任务优先级
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '低', '1', 'analysis_priority', '', 'success', 'N', 1, 'system', NOW(), '低优先级'),
(2, '中', '2', 'analysis_priority', '', 'primary', 'Y', 1, 'system', NOW(), '中等优先级'),
(3, '高', '3', 'analysis_priority', '', 'warning', 'N', 1, 'system', NOW(), '高优先级'),
(4, '紧急', '4', 'analysis_priority', '', 'danger', 'N', 1, 'system', NOW(), '紧急优先级');

-- 调度类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '定时调度', '1', 'schedule_type', '', 'primary', 'Y', 1, 'system', NOW(), '定时执行'),
(2, '周期调度', '2', 'schedule_type', '', 'success', 'N', 1, 'system', NOW(), '周期性执行'),
(3, '条件触发', '3', 'schedule_type', '', 'info', 'N', 1, 'system', NOW(), '条件触发执行');

-- 结果类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '统计数据', '1', 'result_type', '', 'primary', 'Y', 1, 'system', NOW(), '统计汇总数据'),
(2, '图表数据', '2', 'result_type', '', 'success', 'N', 1, 'system', NOW(), '图表展示数据'),
(3, '报表数据', '3', 'result_type', '', 'info', 'N', 1, 'system', NOW(), '报表格式数据'),
(4, '原始数据', '4', 'result_type', '', 'warning', 'N', 1, 'system', NOW(), '原始明细数据');

-- 结果格式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, 'JSON', '1', 'result_format', '', 'primary', 'Y', 1, 'system', NOW(), 'JSON格式'),
(2, 'XML', '2', 'result_format', '', 'success', 'N', 1, 'system', NOW(), 'XML格式'),
(3, 'CSV', '3', 'result_format', '', 'info', 'N', 1, 'system', NOW(), 'CSV格式'),
(4, 'Excel', '4', 'result_format', '', 'warning', 'N', 1, 'system', NOW(), 'Excel格式');

-- 指标类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '数量指标', '1', 'indicator_type', '', 'primary', 'Y', 1, 'system', NOW(), '数量统计指标'),
(2, '质量指标', '2', 'indicator_type', '', 'success', 'N', 1, 'system', NOW(), '质量评价指标'),
(3, '效率指标', '3', 'indicator_type', '', 'info', 'N', 1, 'system', NOW(), '效率评估指标'),
(4, '财务指标', '4', 'indicator_type', '', 'warning', 'N', 1, 'system', NOW(), '财务分析指标'),
(5, '风险指标', '5', 'indicator_type', '', 'danger', 'N', 1, 'system', NOW(), '风险评估指标');

-- 数据类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '整数', '1', 'data_type', '', 'primary', 'Y', 1, 'system', NOW(), '整数类型'),
(2, '小数', '2', 'data_type', '', 'success', 'N', 1, 'system', NOW(), '小数类型'),
(3, '百分比', '3', 'data_type', '', 'info', 'N', 1, 'system', NOW(), '百分比类型'),
(4, '金额', '4', 'data_type', '', 'warning', 'N', 1, 'system', NOW(), '金额类型'),
(5, '文本', '5', 'data_type', '', 'secondary', 'N', 1, 'system', NOW(), '文本类型');

-- 聚合类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '求和', '1', 'aggregation_type', '', 'primary', 'Y', 1, 'system', NOW(), '求和聚合'),
(2, '平均', '2', 'aggregation_type', '', 'success', 'N', 1, 'system', NOW(), '平均值聚合'),
(3, '最大值', '3', 'aggregation_type', '', 'info', 'N', 1, 'system', NOW(), '最大值聚合'),
(4, '最小值', '4', 'aggregation_type', '', 'warning', 'N', 1, 'system', NOW(), '最小值聚合'),
(5, '计数', '5', 'aggregation_type', '', 'secondary', 'N', 1, 'system', NOW(), '计数聚合');

-- 时间粒度
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '天', '1', 'time_granularity', '', 'primary', 'Y', 1, 'system', NOW(), '按天统计'),
(2, '周', '2', 'time_granularity', '', 'success', 'N', 1, 'system', NOW(), '按周统计'),
(3, '月', '3', 'time_granularity', '', 'info', 'N', 1, 'system', NOW(), '按月统计'),
(4, '季度', '4', 'time_granularity', '', 'warning', 'N', 1, 'system', NOW(), '按季度统计'),
(5, '年', '5', 'time_granularity', '', 'secondary', 'N', 1, 'system', NOW(), '按年统计');

-- 维度类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '时间维度', '1', 'dimension_type', '', 'primary', 'Y', 1, 'system', NOW(), '时间相关维度'),
(2, '组织维度', '2', 'dimension_type', '', 'success', 'N', 1, 'system', NOW(), '组织结构维度'),
(3, '业务维度', '3', 'dimension_type', '', 'info', 'N', 1, 'system', NOW(), '业务类型维度'),
(4, '地域维度', '4', 'dimension_type', '', 'warning', 'N', 1, 'system', NOW(), '地理位置维度'),
(5, '客户维度', '5', 'dimension_type', '', 'secondary', 'N', 1, 'system', NOW(), '客户分类维度');

-- 公式类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '数学公式', '1', 'formula_type', '', 'primary', 'Y', 1, 'system', NOW(), '数学计算公式'),
(2, 'SQL查询', '2', 'formula_type', '', 'success', 'N', 1, 'system', NOW(), 'SQL查询语句'),
(3, '脚本函数', '3', 'formula_type', '', 'info', 'N', 1, 'system', NOW(), '脚本函数');

-- 复杂度级别
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '简单', '1', 'complexity_level', '', 'success', 'Y', 1, 'system', NOW(), '简单级别'),
(2, '中等', '2', 'complexity_level', '', 'primary', 'N', 1, 'system', NOW(), '中等级别'),
(3, '复杂', '3', 'complexity_level', '', 'warning', 'N', 1, 'system', NOW(), '复杂级别');

-- 报表类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '定期报表', '1', 'report_type', '', 'primary', 'Y', 1, 'system', NOW(), '定期生成报表'),
(2, '专题报表', '2', 'report_type', '', 'success', 'N', 1, 'system', NOW(), '专题分析报表'),
(3, '实时报表', '3', 'report_type', '', 'info', 'N', 1, 'system', NOW(), '实时监控报表'),
(4, '自定义报表', '4', 'report_type', '', 'warning', 'N', 1, 'system', NOW(), '自定义报表');

-- 图表类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '柱状图', '1', 'chart_type', '', 'primary', 'Y', 1, 'system', NOW(), '柱状图表'),
(2, '折线图', '2', 'chart_type', '', 'success', 'N', 1, 'system', NOW(), '折线图表'),
(3, '饼图', '3', 'chart_type', '', 'info', 'N', 1, 'system', NOW(), '饼图表'),
(4, '散点图', '4', 'chart_type', '', 'warning', 'N', 1, 'system', NOW(), '散点图表'),
(5, '热力图', '5', 'chart_type', '', 'danger', 'N', 1, 'system', NOW(), '热力图表'),
(6, '雷达图', '6', 'chart_type', '', 'secondary', 'N', 1, 'system', NOW(), '雷达图表');

-- 图表库
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, 'ECharts', '1', 'chart_library', '', 'primary', 'Y', 1, 'system', NOW(), 'ECharts图表库'),
(2, 'D3', '2', 'chart_library', '', 'success', 'N', 1, 'system', NOW(), 'D3图表库'),
(3, 'Highcharts', '3', 'chart_library', '', 'info', 'N', 1, 'system', NOW(), 'Highcharts图表库'),
(4, 'Chart.js', '4', 'chart_library', '', 'warning', 'N', 1, 'system', NOW(), 'Chart.js图表库');

-- 布局类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '网格布局', '1', 'layout_type', '', 'primary', 'Y', 1, 'system', NOW(), '网格式布局'),
(2, '自由布局', '2', 'layout_type', '', 'success', 'N', 1, 'system', NOW(), '自由拖拽布局'),
(3, '模板布局', '3', 'layout_type', '', 'info', 'N', 1, 'system', NOW(), '预定义模板布局');

-- 数据源类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, 'MySQL', '1', 'datasource_type', '', 'primary', 'Y', 1, 'system', NOW(), 'MySQL数据库'),
(2, 'PostgreSQL', '2', 'datasource_type', '', 'success', 'N', 1, 'system', NOW(), 'PostgreSQL数据库'),
(3, 'Redis', '3', 'datasource_type', '', 'info', 'N', 1, 'system', NOW(), 'Redis缓存'),
(4, 'Elasticsearch', '4', 'datasource_type', '', 'warning', 'N', 1, 'system', NOW(), 'Elasticsearch搜索'),
(5, 'API', '5', 'datasource_type', '', 'danger', 'N', 1, 'system', NOW(), 'API接口'),
(6, '文件', '6', 'datasource_type', '', 'secondary', 'N', 1, 'system', NOW(), '文件数据源');

-- 连接状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(0, '未测试', '0', 'connection_status', '', 'secondary', 'Y', 1, 'system', NOW(), '未进行连接测试'),
(1, '正常', '1', 'connection_status', '', 'success', 'N', 1, 'system', NOW(), '连接正常'),
(2, '异常', '2', 'connection_status', '', 'danger', 'N', 1, 'system', NOW(), '连接异常');

-- 数据集类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '数据表', '1', 'dataset_type', '', 'primary', 'Y', 1, 'system', NOW(), '数据库表'),
(2, '视图', '2', 'dataset_type', '', 'success', 'N', 1, 'system', NOW(), '数据库视图'),
(3, '查询', '3', 'dataset_type', '', 'info', 'N', 1, 'system', NOW(), 'SQL查询'),
(4, 'API', '4', 'dataset_type', '', 'warning', 'N', 1, 'system', NOW(), 'API接口'),
(5, '文件', '5', 'dataset_type', '', 'secondary', 'N', 1, 'system', NOW(), '文件数据');

-- 刷新模式
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '实时', '1', 'refresh_mode', '', 'success', 'Y', 1, 'system', NOW(), '实时刷新'),
(2, '定时', '2', 'refresh_mode', '', 'primary', 'N', 1, 'system', NOW(), '定时刷新'),
(3, '手动', '3', 'refresh_mode', '', 'warning', 'N', 1, 'system', NOW(), '手动刷新');

-- 缓存类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '查询结果', '1', 'cache_type', '', 'primary', 'Y', 1, 'system', NOW(), '查询结果缓存'),
(2, '图表数据', '2', 'cache_type', '', 'success', 'N', 1, 'system', NOW(), '图表数据缓存'),
(3, '报表数据', '3', 'cache_type', '', 'info', 'N', 1, 'system', NOW(), '报表数据缓存'),
(4, '计算结果', '4', 'cache_type', '', 'warning', 'N', 1, 'system', NOW(), '计算结果缓存');

-- 压缩类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(0, '无压缩', '0', 'compression_type', '', 'secondary', 'Y', 1, 'system', NOW(), '不压缩'),
(1, 'GZIP', '1', 'compression_type', '', 'primary', 'N', 1, 'system', NOW(), 'GZIP压缩'),
(2, 'LZ4', '2', 'compression_type', '', 'success', 'N', 1, 'system', NOW(), 'LZ4压缩');

-- 模板类型
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '任务模板', '1', 'template_type', '', 'primary', 'Y', 1, 'system', NOW(), '分析任务模板'),
(2, '报表模板', '2', 'template_type', '', 'success', 'N', 1, 'system', NOW(), '报表配置模板'),
(3, '图表模板', '3', 'template_type', '', 'info', 'N', 1, 'system', NOW(), '图表配置模板'),
(4, '仪表盘模板', '4', 'template_type', '', 'warning', 'N', 1, 'system', NOW(), '仪表盘配置模板');

-- 执行状态
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '成功', '1', 'execution_status', '', 'success', 'Y', 1, 'system', NOW(), '执行成功'),
(2, '失败', '2', 'execution_status', '', 'danger', 'N', 1, 'system', NOW(), '执行失败'),
(3, '超时', '3', 'execution_status', '', 'warning', 'N', 1, 'system', NOW(), '执行超时'),
(4, '取消', '4', 'execution_status', '', 'secondary', 'N', 1, 'system', NOW(), '执行取消');

-- ======================= 分析维度数据 =======================

-- 时间维度
INSERT INTO analysis_dimension (tenant_id, dimension_code, dimension_name, dimension_type, parent_id, level, path, table_name, column_name, value_type, sort_order, create_by, create_time, status) VALUES
(0, 'TIME_YEAR', '年', 1, 0, 1, '/TIME_YEAR', '', 'YEAR', 2, 1, 'system', NOW(), 1),
(0, 'TIME_QUARTER', '季度', 1, 0, 1, '/TIME_QUARTER', '', 'QUARTER', 2, 2, 'system', NOW(), 1),
(0, 'TIME_MONTH', '月', 1, 0, 1, '/TIME_MONTH', '', 'MONTH', 2, 3, 'system', NOW(), 1),
(0, 'TIME_WEEK', '周', 1, 0, 1, '/TIME_WEEK', '', 'WEEK', 2, 4, 'system', NOW(), 1),
(0, 'TIME_DAY', '日', 1, 0, 1, '/TIME_DAY', '', 'DAY', 2, 5, 'system', NOW(), 1);

-- 组织维度
INSERT INTO analysis_dimension (tenant_id, dimension_code, dimension_name, dimension_type, parent_id, level, path, table_name, column_name, value_type, sort_order, create_by, create_time, status) VALUES
(0, 'ORG_DEPT', '部门', 2, 0, 1, '/ORG_DEPT', 'sys_dept', 'dept_name', 1, 1, 'system', NOW(), 1),
(0, 'ORG_USER', '用户', 2, 0, 1, '/ORG_USER', 'sys_user', 'user_name', 1, 2, 'system', NOW(), 1),
(0, 'ORG_ROLE', '角色', 2, 0, 1, '/ORG_ROLE', 'sys_role', 'role_name', 1, 3, 'system', NOW(), 1);

-- 业务维度
INSERT INTO analysis_dimension (tenant_id, dimension_code, dimension_name, dimension_type, parent_id, level, path, table_name, column_name, value_type, sort_order, create_by, create_time, status) VALUES
(0, 'BUS_CASE_TYPE', '案件类型', 3, 0, 1, '/BUS_CASE_TYPE', 'case_case', 'case_type', 1, 1, 'system', NOW(), 1),
(0, 'BUS_CASE_STATUS', '案件状态', 3, 0, 1, '/BUS_CASE_STATUS', 'case_case', 'case_status', 1, 2, 'system', NOW(), 1),
(0, 'BUS_CLIENT_TYPE', '客户类型', 3, 0, 1, '/BUS_CLIENT_TYPE', 'client_client', 'client_type', 1, 3, 'system', NOW(), 1),
(0, 'BUS_CONTRACT_TYPE', '合同类型', 3, 0, 1, '/BUS_CONTRACT_TYPE', 'contract_contract', 'contract_type', 1, 4, 'system', NOW(), 1);

-- ======================= 分析指标数据 =======================

-- 案件相关指标
INSERT INTO analysis_indicator (tenant_id, indicator_code, indicator_name, indicator_type, data_type, unit, description, category, group_name, aggregation_type, time_granularity, sort_order, create_by, create_time, status) VALUES
(0, 'CASE_COUNT', '案件总数', 1, 1, '件', '案件总数量统计', '案件管理', '基础指标', 5, 3, 1, 'system', NOW(), 1),
(0, 'CASE_NEW_COUNT', '新增案件数', 1, 1, '件', '新增案件数量统计', '案件管理', '基础指标', 5, 3, 2, 'system', NOW(), 1),
(0, 'CASE_CLOSE_COUNT', '结案数量', 1, 1, '件', '结案数量统计', '案件管理', '基础指标', 5, 3, 3, 'system', NOW(), 1),
(0, 'CASE_SUCCESS_RATE', '胜诉率', 2, 3, '%', '案件胜诉率统计', '案件管理', '质量指标', 2, 3, 4, 'system', NOW(), 1),
(0, 'CASE_CYCLE_AVG', '平均审理周期', 3, 2, '天', '案件平均审理周期', '案件管理', '效率指标', 2, 3, 5, 'system', NOW(), 1);

-- 客户相关指标
INSERT INTO analysis_indicator (tenant_id, indicator_code, indicator_name, indicator_type, data_type, unit, description, category, group_name, aggregation_type, time_granularity, sort_order, create_by, create_time, status) VALUES
(0, 'CLIENT_COUNT', '客户总数', 1, 1, '个', '客户总数量统计', '客户管理', '基础指标', 5, 3, 1, 'system', NOW(), 1),
(0, 'CLIENT_NEW_COUNT', '新增客户数', 1, 1, '个', '新增客户数量统计', '客户管理', '基础指标', 5, 3, 2, 'system', NOW(), 1),
(0, 'CLIENT_ACTIVE_COUNT', '活跃客户数', 1, 1, '个', '活跃客户数量统计', '客户管理', '基础指标', 5, 3, 3, 'system', NOW(), 1),
(0, 'CLIENT_SATISFACTION', '客户满意度', 2, 3, '%', '客户满意度评分', '客户管理', '质量指标', 2, 3, 4, 'system', NOW(), 1),
(0, 'CLIENT_VALUE_AVG', '客户平均价值', 4, 4, '元', '客户平均价值统计', '客户管理', '财务指标', 2, 3, 5, 'system', NOW(), 1);

-- 财务相关指标
INSERT INTO analysis_indicator (tenant_id, indicator_code, indicator_name, indicator_type, data_type, unit, description, category, group_name, aggregation_type, time_granularity, sort_order, create_by, create_time, status) VALUES
(0, 'REVENUE_TOTAL', '总收入', 4, 4, '元', '总收入统计', '财务管理', '财务指标', 1, 3, 1, 'system', NOW(), 1),
(0, 'REVENUE_GROWTH', '收入增长率', 4, 3, '%', '收入增长率统计', '财务管理', '财务指标', 2, 3, 2, 'system', NOW(), 1),
(0, 'COST_TOTAL', '总成本', 4, 4, '元', '总成本统计', '财务管理', '财务指标', 1, 3, 3, 'system', NOW(), 1),
(0, 'PROFIT_TOTAL', '总利润', 4, 4, '元', '总利润统计', '财务管理', '财务指标', 1, 3, 4, 'system', NOW(), 1),
(0, 'PROFIT_MARGIN', '利润率', 4, 3, '%', '利润率统计', '财务管理', '财务指标', 2, 3, 5, 'system', NOW(), 1);

-- ======================= 分析公式数据 =======================

-- 基础计算公式
INSERT INTO analysis_formula (tenant_id, formula_name, formula_code, formula_type, formula_expression, description, is_builtin, category, complexity_level, create_by, create_time, status) VALUES
(0, '胜诉率计算', 'WIN_RATE', 1, '(胜诉案件数 / 总案件数) * 100', '计算案件胜诉率', 1, '业务分析', 1, 'system', NOW(), 1),
(0, '收入增长率', 'REVENUE_GROWTH', 1, '((当期收入 - 上期收入) / 上期收入) * 100', '计算收入增长率', 1, '财务分析', 1, 'system', NOW(), 1),
(0, '利润率计算', 'PROFIT_MARGIN', 1, '(利润 / 收入) * 100', '计算利润率', 1, '财务分析', 1, 'system', NOW(), 1),
(0, '客户满意度', 'CLIENT_SATISFACTION', 1, 'SUM(满意度评分) / COUNT(客户数)', '计算客户满意度平均分', 1, '客户分析', 1, 'system', NOW(), 1),
(0, '案件平均周期', 'CASE_AVG_CYCLE', 1, 'AVG(DATEDIFF(结案日期, 立案日期))', '计算案件平均审理周期', 1, '业务分析', 2, 'system', NOW(), 1);

-- ======================= 分析模板数据 =======================

-- 业务分析模板
INSERT INTO analysis_template (tenant_id, template_name, template_code, template_type, category, description, is_builtin, is_public, author_id, author_name, sort_order, create_by, create_time, status) VALUES
(0, '案件统计分析模板', 'CASE_ANALYSIS_TEMPLATE', 1, '业务分析', '案件数量、类型、状态等统计分析模板', 1, 1, 1, 'system', 1, 'system', NOW(), 1),
(0, '客户行为分析模板', 'CLIENT_BEHAVIOR_TEMPLATE', 1, '客户分析', '客户行为模式、价值分析模板', 1, 1, 1, 'system', 2, 'system', NOW(), 1),
(0, '财务收入分析模板', 'FINANCE_REVENUE_TEMPLATE', 1, '财务分析', '收入构成、趋势分析模板', 1, 1, 1, 'system', 3, 'system', NOW(), 1),
(0, '绩效评估分析模板', 'PERFORMANCE_TEMPLATE', 1, '绩效分析', '员工及团队绩效评估模板', 1, 1, 1, 'system', 4, 'system', NOW(), 1);

-- 报表模板
INSERT INTO analysis_template (tenant_id, template_name, template_code, template_type, category, description, is_builtin, is_public, author_id, author_name, sort_order, create_by, create_time, status) VALUES
(0, '综合业务报表模板', 'BUSINESS_REPORT_TEMPLATE', 2, '业务报表', '综合业务数据报表模板', 1, 1, 1, 'system', 1, 'system', NOW(), 1),
(0, '财务月报模板', 'FINANCE_MONTHLY_TEMPLATE', 2, '财务报表', '财务月度报表模板', 1, 1, 1, 'system', 2, 'system', NOW(), 1),
(0, '客户分析报表模板', 'CLIENT_REPORT_TEMPLATE', 2, '客户报表', '客户分析专题报表模板', 1, 1, 1, 'system', 3, 'system', NOW(), 1);

-- 图表模板
INSERT INTO analysis_template (tenant_id, template_name, template_code, template_type, category, description, is_builtin, is_public, author_id, author_name, sort_order, create_by, create_time, status) VALUES
(0, '趋势分析图表模板', 'TREND_CHART_TEMPLATE', 3, '趋势图表', '时间序列趋势分析图表模板', 1, 1, 1, 'system', 1, 'system', NOW(), 1),
(0, '对比分析图表模板', 'COMPARE_CHART_TEMPLATE', 3, '对比图表', '多维度对比分析图表模板', 1, 1, 1, 'system', 2, 'system', NOW(), 1),
(0, '分布分析图表模板', 'DISTRIBUTION_CHART_TEMPLATE', 3, '分布图表', '数据分布情况图表模板', 1, 1, 1, 'system', 3, 'system', NOW(), 1);

-- 仪表盘模板
INSERT INTO analysis_template (tenant_id, template_name, template_code, template_type, category, description, is_builtin, is_public, author_id, author_name, sort_order, create_by, create_time, status) VALUES
(0, '执行监控仪表盘', 'EXECUTIVE_DASHBOARD_TEMPLATE', 4, '管理仪表盘', '管理层综合监控仪表盘模板', 1, 1, 1, 'system', 1, 'system', NOW(), 1),
(0, '业务运营仪表盘', 'OPERATION_DASHBOARD_TEMPLATE', 4, '运营仪表盘', '日常业务运营监控仪表盘模板', 1, 1, 1, 'system', 2, 'system', NOW(), 1),
(0, '财务监控仪表盘', 'FINANCE_DASHBOARD_TEMPLATE', 4, '财务仪表盘', '财务数据监控仪表盘模板', 1, 1, 1, 'system', 3, 'system', NOW(), 1);

-- 初始化完成提示
SELECT '数据分析模块基础数据初始化完成' AS result;
SELECT CONCAT('已创建字典类型：', COUNT(*), '个') AS dict_type_count FROM sys_dict_type WHERE dict_type LIKE 'analysis_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_mode' OR dict_type LIKE '%_status';
SELECT CONCAT('已创建字典数据：', COUNT(*), '个') AS dict_data_count FROM sys_dict_data WHERE dict_type LIKE 'analysis_%' OR dict_type LIKE '%_type' OR dict_type LIKE '%_mode' OR dict_type LIKE '%_status';
SELECT CONCAT('已创建分析维度：', COUNT(*), '个') AS dimension_count FROM analysis_dimension WHERE tenant_id = 0;
SELECT CONCAT('已创建分析指标：', COUNT(*), '个') AS indicator_count FROM analysis_indicator WHERE tenant_id = 0;
SELECT CONCAT('已创建分析公式：', COUNT(*), '个') AS formula_count FROM analysis_formula WHERE tenant_id = 0;
SELECT CONCAT('已创建分析模板：', COUNT(*), '个') AS template_count FROM analysis_template WHERE tenant_id = 0; 