-- 添加is_system列到字典表
ALTER TABLE sys_dict ADD is_system TINYINT(1) NOT NULL DEFAULT 0; 