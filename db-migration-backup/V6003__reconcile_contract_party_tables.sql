-- 合同模块当事人表数据迁移
-- 版本: V6003
-- 模块: contract
-- 创建时间: 2023-11-15
-- 说明: 将客户模块中的合同当事人表数据迁移到合同模块，解决模块职责划分问题
-- 依赖: V4001(client), V6001(contract)

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 说明：合同当事人表(contract_party)在客户模块(V4001)和合同模块(V6001)中都有定义
-- 由于执行顺序是先客户模块再合同模块，所以在此脚本中需要同步数据

-- 检查客户模块中的contract_party表是否存在
SELECT COUNT(*) INTO @contract_party_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'contract_party';

-- 先删除可能已存在的存储过程
DROP PROCEDURE IF EXISTS migrate_contract_party_data;

-- 创建一个存储过程，用于合并contract_party表中的数据
DELIMITER //
CREATE PROCEDURE migrate_contract_party_data()
BEGIN
    -- 只有当表存在时才执行数据迁移
    IF @contract_party_exists > 0 THEN
        -- 从客户模块中将记录合并到合同模块
        INSERT IGNORE INTO contract_party (
            id, tenant_id, tenant_code, contract_id, relation_type, source_client_id, target_client_id, 
            relation_desc, party_type, sign_role, sign_qualification, sign_time, performance_status, 
            contract_rights, contract_obligations, start_time, end_time, status, effective_time, expiry_time, 
            priority, attributes, version, sort, deleted, create_time, create_by, update_time, update_by, remark
        )
        SELECT 
            id, tenant_id, tenant_code, contract_id, relation_type, source_client_id, target_client_id, 
            relation_desc, party_type, sign_role, sign_qualification, sign_time, performance_status, 
            contract_rights, contract_obligations, start_time, end_time, status, effective_time, expiry_time, 
            priority, attributes, version, sort, deleted, create_time, create_by, update_time, update_by, remark
        FROM contract_party;
    END IF;
END //
DELIMITER ;

-- 执行存储过程
CALL migrate_contract_party_data();

-- 删除存储过程
DROP PROCEDURE IF EXISTS migrate_contract_party_data;
  
-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 