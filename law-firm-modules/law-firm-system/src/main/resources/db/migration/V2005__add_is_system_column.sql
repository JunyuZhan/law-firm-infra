-- 为配置表添加is_system字段
ALTER TABLE sys_config ADD is_system TINYINT(1) DEFAULT 0;

-- 将所有现有配置标记为系统内置
UPDATE sys_config SET is_system = 1; 