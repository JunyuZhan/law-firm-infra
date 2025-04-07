-- 插入系统配置数据
INSERT INTO sys_config (config_name, config_key, config_value, config_type, remark, create_by, update_by)
VALUES 
-- 系统基础配置
('系统名称', 'system.name', '律师事务所管理系统', 'SYSTEM', '系统名称配置', 'admin', 'admin'),
('系统版本号', 'system.version', '1.0.0', 'SYSTEM', '系统版本号配置', 'admin', 'admin'),
('系统Logo', 'system.logo', '/static/img/logo.png', 'SYSTEM', '系统Logo路径配置', 'admin', 'admin'),
('系统描述', 'system.description', '专业律师事务所管理系统，提供全方位的律所事务管理解决方案', 'SYSTEM', '系统描述配置', 'admin', 'admin'),
('技术支持联系方式', 'system.support.contact', 'support@lawfirm.com', 'SYSTEM', '技术支持联系方式', 'admin', 'admin'),
('允许用户注册', 'system.allow.register', 'false', 'SYSTEM', '是否允许用户注册', 'admin', 'admin'),
('系统公告', 'system.announcement', '欢迎使用律师事务所管理系统', 'SYSTEM', '系统公告内容', 'admin', 'admin'),
('是否显示公告', 'system.announcement.enabled', 'true', 'SYSTEM', '是否显示系统公告', 'admin', 'admin'),

-- 安全配置
('密码过期天数', 'security.password.expire-days', '90', 'SYSTEM', '密码过期天数，0表示永不过期', 'admin', 'admin'),
('密码最小长度', 'security.password.min-length', '8', 'SYSTEM', '密码最小长度', 'admin', 'admin'),
('密码复杂度要求', 'security.password.complexity', 'MEDIUM', 'SYSTEM', '密码复杂度要求：LOW-低，MEDIUM-中，HIGH-高', 'admin', 'admin'),
('登录失败锁定次数', 'security.login.lock-count', '5', 'SYSTEM', '登录失败多少次后锁定账号', 'admin', 'admin'),
('账号锁定时间(分钟)', 'security.login.lock-time', '30', 'SYSTEM', '账号锁定后，多少分钟后自动解锁', 'admin', 'admin'),
('单用户登录', 'security.login.single-user', 'false', 'SYSTEM', '是否限制用户只能在一处登录', 'admin', 'admin'),
('会话超时时间(分钟)', 'security.session.timeout', '120', 'SYSTEM', '会话超时时间，单位分钟', 'admin', 'admin'),

-- 文件上传配置
('允许上传的文件类型', 'upload.allowed-types', 'jpg,jpeg,png,gif,doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar', 'SYSTEM', '允许上传的文件类型', 'admin', 'admin'),
('最大文件上传大小(MB)', 'upload.max-size', '50', 'SYSTEM', '最大文件上传大小，单位MB', 'admin', 'admin'),
('文件存储路径', 'upload.path', '/data/upload', 'SYSTEM', '文件上传存储路径', 'admin', 'admin'),
('是否自动重命名文件', 'upload.auto-rename', 'true', 'SYSTEM', '是否自动重命名上传的文件', 'admin', 'admin'),

-- 邮件配置
('邮件服务器地址', 'mail.host', 'smtp.example.com', 'SYSTEM', '邮件服务器地址', 'admin', 'admin'),
('邮件服务器端口', 'mail.port', '465', 'SYSTEM', '邮件服务器端口', 'admin', 'admin'),
('邮件发送者用户名', 'mail.username', 'noreply@example.com', 'SYSTEM', '邮件发送者用户名', 'admin', 'admin'),
('邮件发送者密码', 'mail.password', 'password', 'SYSTEM', '邮件发送者密码', 'admin', 'admin'),
('邮件发送者显示名', 'mail.from', '律师事务所管理系统', 'SYSTEM', '邮件发送者显示名', 'admin', 'admin'),
('启用SSL', 'mail.ssl-enabled', 'true', 'SYSTEM', '是否启用SSL发送邮件', 'admin', 'admin'),

-- 短信配置
('短信服务提供商', 'sms.provider', 'ALIYUN', 'SYSTEM', '短信服务提供商：ALIYUN-阿里云，TENCENT-腾讯云', 'admin', 'admin'),
('短信AccessKey', 'sms.access-key', 'your-access-key', 'SYSTEM', '短信服务AccessKey', 'admin', 'admin'),
('短信AccessSecret', 'sms.access-secret', 'your-access-secret', 'SYSTEM', '短信服务AccessSecret', 'admin', 'admin'),
('短信签名', 'sms.sign-name', '律师事务所', 'SYSTEM', '短信签名', 'admin', 'admin'),

-- 日志配置
('操作日志保留天数', 'log.operation.retention-days', '180', 'SYSTEM', '操作日志保留天数', 'admin', 'admin'),
('登录日志保留天数', 'log.login.retention-days', '180', 'SYSTEM', '登录日志保留天数', 'admin', 'admin'),
('是否记录操作日志', 'log.operation.enabled', 'true', 'SYSTEM', '是否记录操作日志', 'admin', 'admin');

-- 插入AI相关配置
INSERT INTO sys_config (config_name, config_key, config_value, config_type, remark, create_by, update_by)
VALUES 
('AI服务提供商', 'ai.provider', 'OPENAI', 'SYSTEM', 'AI服务提供商：OPENAI-OpenAI，BAIDU-百度，ALIYUN-阿里云', 'admin', 'admin'),
('AI API密钥', 'ai.api-key', 'your-api-key', 'SYSTEM', 'AI服务API密钥', 'admin', 'admin'),
('AI模型', 'ai.model', 'gpt-3.5-turbo', 'SYSTEM', 'AI使用的模型', 'admin', 'admin'),
('AI最大Token数', 'ai.max-tokens', '2000', 'SYSTEM', 'AI请求最大Token数', 'admin', 'admin'),
('AI请求超时(秒)', 'ai.timeout', '30', 'SYSTEM', 'AI请求超时时间，单位秒', 'admin', 'admin'),
('启用AI功能', 'ai.enabled', 'true', 'SYSTEM', '是否启用AI功能', 'admin', 'admin'),
('AI温度参数', 'ai.temperature', '0.7', 'SYSTEM', 'AI温度参数，控制输出的随机性', 'admin', 'admin'),
('AI服务端点', 'ai.endpoint', 'https://api.openai.com/v1/chat/completions', 'SYSTEM', 'AI服务端点URL', 'admin', 'admin'); 