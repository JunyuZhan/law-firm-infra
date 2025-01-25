-- 添加文件类型限制配置表
CREATE TABLE IF NOT EXISTS sys_file_type_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_type VARCHAR(50) NOT NULL COMMENT '文件类型',
    max_size BIGINT NOT NULL COMMENT '最大大小(字节)',
    enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_file_type (file_type)
) COMMENT '文件类型配置表';

-- 插入默认配置
INSERT INTO sys_file_type_config (file_type, max_size, enabled) VALUES
('pdf', 10485760, 1),    -- 10MB
('doc', 5242880, 1),     -- 5MB
('docx', 5242880, 1),    -- 5MB
('jpg', 2097152, 1),     -- 2MB
('png', 2097152, 1),     -- 2MB
('txt', 1048576, 1);     -- 1MB

-- 修改案件文件表，添加文件类型验证标志
ALTER TABLE case_file 
ADD COLUMN file_validated TINYINT(1) DEFAULT 0 COMMENT '文件类型是否已验证' AFTER file_type,
ADD COLUMN validate_time DATETIME COMMENT '验证时间' AFTER file_validated;

-- 更新现有文件验证状态
UPDATE case_file SET file_validated = 1, validate_time = NOW() 
WHERE file_type IN (SELECT file_type FROM sys_file_type_config); 