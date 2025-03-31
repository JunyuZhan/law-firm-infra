-- 添加is_system字段到sys_config表
ALTER TABLE sys_config 
ADD COLUMN is_system TINYINT(1) NOT NULL DEFAULT 0 
COMMENT '是否系统内置（0否/1是）'; 