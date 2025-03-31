-- 确保sys_config表中有is_system字段
-- 直接执行添加列的操作，如果列已存在将抛出异常，但Flyway会继续执行
ALTER TABLE `sys_config` 
ADD `is_system` tinyint(1) NOT NULL DEFAULT 0 
COMMENT '是否系统内置（0否/1是）'; 