-- 档案模块表名修正
-- 版本: V12004
-- 模块: archive
-- 创建时间: 2023-11-15
-- 说明: 将档案模块中的case_archive表重命名为archive_case，使其符合模块前缀命名规范
-- 依赖: V12001, V12002, V12003

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 检查表是否存在
SELECT COUNT(*) INTO @table_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'case_archive';

-- 当旧表存在时，执行重命名操作
DROP PROCEDURE IF EXISTS rename_archive_table;
DELIMITER //
CREATE PROCEDURE rename_archive_table()
BEGIN
    -- 如果两个表都存在，将数据从旧表复制到新表
    IF (@table_exists > 0) THEN
        -- 创建新表（如果不存在）
        CREATE TABLE IF NOT EXISTS archive_case LIKE case_archive;
        
        -- 将旧表数据复制到新表
        INSERT IGNORE INTO archive_case
        SELECT * FROM case_archive;
        
        -- 更新相关的数据（如有需要）
        -- 更新引用case_archive表的外键关系
        UPDATE archive_file SET business_type = 'archive_case' WHERE business_type = 'case_archive';
        
        -- 添加迁移说明到系统日志
        INSERT INTO sys_operation_log(operation_type, operation_module, operation_description, operation_time, operator)
        VALUES ('TABLE_RENAMED', 'archive', 'Renamed case_archive table to archive_case to follow module prefix naming convention', NOW(), 'system');
        
        -- 删除旧表
        DROP TABLE case_archive;
    END IF;
END //
DELIMITER ;

-- 执行存储过程
CALL rename_archive_table();

-- 删除存储过程
DROP PROCEDURE IF EXISTS rename_archive_table;

-- 更新模块文档
INSERT INTO module_documentation (module_name, version_range, responsibility_description, last_updated)
VALUES ('archive', 'V12000-V12999', '档案模块所有表名应使用archive_前缀，遵循模块命名规范。原case_archive表已重命名为archive_case。', NOW())
ON DUPLICATE KEY UPDATE
    responsibility_description = CONCAT(responsibility_description, ' 档案模块所有表名应使用archive_前缀，遵循模块命名规范。原case_archive表已重命名为archive_case。'),
    last_updated = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;