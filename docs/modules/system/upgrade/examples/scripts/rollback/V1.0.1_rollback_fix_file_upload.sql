-- 删除文件验证相关字段
ALTER TABLE case_file 
DROP COLUMN file_validated,
DROP COLUMN validate_time;

-- 删除文件类型配置表
DROP TABLE IF EXISTS sys_file_type_config; 