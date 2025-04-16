-- 系统模块迁移脚本 V1008
-- 更新系统配置数据，确保所有记录都包含所需字段值

-- 添加分类字段
UPDATE sys_config 
SET category = CASE
  -- 系统基础配置
  WHEN config_key = 'system.name' THEN '系统设置'
  WHEN config_key = 'system.version' THEN '系统设置'
  WHEN config_key = 'system.logo' THEN '系统设置'
  WHEN config_key = 'system.description' THEN '系统设置'
  WHEN config_key = 'system.support.contact' THEN '系统设置'
  WHEN config_key = 'system.allow.register' THEN '系统设置'
  WHEN config_key = 'system.announcement' THEN '系统设置'
  WHEN config_key = 'system.announcement.enabled' THEN '系统设置'
  
  -- 安全配置
  WHEN config_key = 'security.password.expire-days' THEN '安全设置'
  WHEN config_key = 'security.password.min-length' THEN '安全设置'
  WHEN config_key = 'security.password.complexity' THEN '安全设置'
  WHEN config_key = 'security.login.lock-count' THEN '安全设置'
  WHEN config_key = 'security.login.lock-time' THEN '安全设置'
  WHEN config_key = 'security.login.single-user' THEN '安全设置'
  WHEN config_key = 'security.session.timeout' THEN '安全设置'
  
  -- 文件上传配置
  WHEN config_key = 'upload.allowed-types' THEN '文件上传'
  WHEN config_key = 'upload.max-size' THEN '文件上传'
  WHEN config_key = 'upload.path' THEN '文件上传'
  WHEN config_key = 'upload.auto-rename' THEN '文件上传'
  
  -- 邮件配置
  WHEN config_key = 'mail.host' THEN '邮件设置'
  WHEN config_key = 'mail.port' THEN '邮件设置'
  WHEN config_key = 'mail.username' THEN '邮件设置'
  WHEN config_key = 'mail.password' THEN '邮件设置'
  WHEN config_key = 'mail.from' THEN '邮件设置'
  WHEN config_key = 'mail.ssl-enabled' THEN '邮件设置'
  
  -- 短信配置
  WHEN config_key = 'sms.provider' THEN '短信设置'
  WHEN config_key = 'sms.access-key' THEN '短信设置'
  WHEN config_key = 'sms.access-secret' THEN '短信设置'
  WHEN config_key = 'sms.sign-name' THEN '短信设置'
  
  -- 日志配置
  WHEN config_key = 'log.operation.retention-days' THEN '日志设置'
  WHEN config_key = 'log.login.retention-days' THEN '日志设置'
  WHEN config_key = 'log.operation.enabled' THEN '日志设置'
  
  -- AI配置
  WHEN config_key = 'ai.provider' THEN 'AI设置'
  WHEN config_key = 'ai.api-key' THEN 'AI设置'
  WHEN config_key = 'ai.model' THEN 'AI设置'
  WHEN config_key = 'ai.max-tokens' THEN 'AI设置'
  WHEN config_key = 'ai.timeout' THEN 'AI设置'
  WHEN config_key = 'ai.enabled' THEN 'AI设置'
  WHEN config_key = 'ai.temperature' THEN 'AI设置'
  WHEN config_key = 'ai.endpoint' THEN 'AI设置'
  
  -- 默认分类
  ELSE '其他配置'
END
WHERE category IS NULL;

-- 设置其他必要字段
UPDATE sys_config
SET 
  is_system = 1,     -- 所有系统配置
  status = 1,        -- 所有启用状态
  sort = 0,          -- 默认排序值
  version = 1,       -- 初始版本号
  tenant_id = 0      -- 全局租户ID
WHERE is_system IS NULL OR status IS NULL; 