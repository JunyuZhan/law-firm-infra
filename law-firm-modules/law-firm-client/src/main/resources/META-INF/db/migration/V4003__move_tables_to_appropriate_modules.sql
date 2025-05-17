-- 客户模块表结构调整
-- 版本: V4003
-- 模块: client
-- 创建时间: 2025-05-13
-- 说明: 从客户模块中移除不属于该模块的表(case_party和contract_party)，这些表已迁移到各自对应的业务模块
-- 依赖: V4001
-- 注意: 此脚本应在V6003和V7003执行后执行，确保数据已被迁移。但由于Flyway按版本号顺序执行，会先执行此脚本，因此脚本内已添加表存在性检查

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 确认案件模块和合同模块中已有这些表的数据
-- 这一步是确认性检查，确保数据已经被迁移到相应模块
SELECT COUNT(*) INTO @case_party_exists_in_case 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'case_party';

SELECT COUNT(*) INTO @contract_party_exists_in_contract 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'contract_party';

-- 执行删除操作的存储过程
DROP PROCEDURE IF EXISTS cleanup_client_module_tables;
DELIMITER //
CREATE PROCEDURE cleanup_client_module_tables()
BEGIN
    -- 如果案件模块中有case_party表，则从客户模块中删除该表
    IF @case_party_exists_in_case > 0 THEN
        -- 删除表
        DROP TABLE IF EXISTS case_party;
    END IF;
    
    -- 如果合同模块中有contract_party表，则从客户模块中删除该表
    IF @contract_party_exists_in_contract > 0 THEN
        -- 删除表
        DROP TABLE IF EXISTS contract_party;
    END IF;
END //
DELIMITER ;

-- 执行存储过程
CALL cleanup_client_module_tables();

-- 删除存储过程
DROP PROCEDURE IF EXISTS cleanup_client_module_tables;

-- 更新模块说明
-- 检查module_documentation表是否存在
SELECT COUNT(*) INTO @table_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'module_documentation';

-- 创建更新模块文档的存储过程
DROP PROCEDURE IF EXISTS update_module_doc;
DELIMITER //
CREATE PROCEDURE update_module_doc()
BEGIN
    IF @table_exists > 0 THEN
        INSERT INTO module_documentation (module_name, version_range, responsibility_description, last_updated)
        VALUES ('client', 'V4000-V4999', '客户模块负责管理客户基本信息、联系方式、地址、分类、标签等数据。不再包含案件当事人和合同当事人信息，这些已迁移至对应业务模块。', NOW())
        ON DUPLICATE KEY UPDATE
            responsibility_description = '客户模块负责管理客户基本信息、联系方式、地址、分类、标签等数据。不再包含案件当事人和合同当事人信息，这些已迁移至对应业务模块。',
            last_updated = NOW();
    END IF;
END //
DELIMITER ;

-- 执行存储过程
CALL update_module_doc();

-- 删除存储过程
DROP PROCEDURE IF EXISTS update_module_doc;

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1;