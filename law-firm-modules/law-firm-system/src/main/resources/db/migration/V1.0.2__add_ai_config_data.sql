-- 添加AI配置记录
INSERT INTO sys_config (config_name, config_key, config_value, config_type, is_system, remark, create_by) VALUES 
('OpenAI API密钥', 'ai.openai.api-key', '', 'BUSINESS', 1, 'OpenAI服务API密钥，用于智能问答和文档处理', 'admin'),
('OpenAI模型', 'ai.openai.model', 'gpt-4', 'BUSINESS', 1, 'OpenAI服务使用的模型', 'admin'),
('百度AI API密钥', 'ai.baidu.api-key', '', 'BUSINESS', 1, '百度AI服务API密钥', 'admin'),
('百度AI Secret密钥', 'ai.baidu.secret-key', '', 'BUSINESS', 1, '百度AI服务Secret密钥', 'admin'),
('百度AI模型', 'ai.baidu.model', 'ERNIE-Bot-4', 'BUSINESS', 1, '百度AI服务使用的模型', 'admin'),
('AI默认提供商', 'ai.default-provider', 'openai', 'BUSINESS', 1, 'AI默认服务提供商(openai/baidu)', 'admin'),
('AI超时时间', 'ai.timeout', '30', 'BUSINESS', 1, 'AI服务请求超时时间(秒)', 'admin'),
('AI最大并发请求', 'ai.max-concurrent-requests', '10', 'BUSINESS', 1, 'AI服务最大并发请求数', 'admin'),
('AI启用缓存', 'ai.enable-cache', 'true', 'BUSINESS', 1, '是否启用AI响应缓存', 'admin'),
('AI敏感信息过滤', 'ai.enable-sensitive-filter', 'true', 'BUSINESS', 1, '是否启用敏感信息过滤', 'admin');

-- 添加AI配置类型
INSERT INTO sys_dict (dict_name, dict_type, status, remark, create_by) VALUES 
('AI提供商类型', 'ai_provider_type', 0, 'AI服务提供商类型', 'admin');

-- 添加AI提供商类型字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'ai_provider_type'), 'OpenAI', 'openai', 1, 0, 'OpenAI服务', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'ai_provider_type'), '百度文心', 'baidu', 2, 0, '百度文心大模型', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'ai_provider_type'), '本地模型', 'local', 3, 0, '本地部署的AI模型', 'admin'); 