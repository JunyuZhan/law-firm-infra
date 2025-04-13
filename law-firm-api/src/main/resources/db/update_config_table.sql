USE law_firm;

-- 添加is_system字段到sys_config表
ALTER TABLE sys_config 
ADD COLUMN is_system TINYINT(1) NOT NULL DEFAULT 0 
COMMENT '是否系统内置（0否/1是）';

-- 添加version字段到sys_config表
ALTER TABLE sys_config 
ADD COLUMN version BIGINT DEFAULT 0 
COMMENT '版本号' AFTER update_time;


-- 添加status字段到sys_config表
ALTER TABLE sys_config 
ADD COLUMN status INT DEFAULT 0 
COMMENT '状态（0-启用，1-禁用）';

-- 添加sort字段到sys_config表
ALTER TABLE sys_config 
ADD COLUMN sort INT DEFAULT 0 
COMMENT '排序号';