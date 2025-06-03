-- 案例模块当事人表数据迁移
-- 版本: V7003
-- 模块: case
-- 创建时间: 2023-11-15
-- 说明: 将客户模块中的案件当事人表数据迁移到案例模块，解决模块职责划分问题
-- 依赖: V4001(client), V7001(case)

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 说明：案件当事人表(case_party)在客户模块(V4001)和案件模块(V7001)中都有定义
-- 由于执行顺序是先客户模块再案件模块，所以在此脚本中需要同步数据

-- 检查客户模块中的case_party表是否存在
SELECT COUNT(*) INTO @case_party_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'case_party';

-- 先删除可能已存在的存储过程
DROP PROCEDURE IF EXISTS migrate_case_party_data;

-- 创建存储过程进行数据迁移
DELIMITER //
CREATE PROCEDURE migrate_case_party_data()
BEGIN
    -- 只有当表存在时才执行数据迁移
    IF @case_party_exists > 0 THEN
        -- 插入客户模块中不存在于案件模块的记录
        INSERT IGNORE INTO case_party (
          id, tenant_id, tenant_code, case_id, relation_type, source_client_id, target_client_id, 
          relation_desc, party_type, party_role, agent_type, entrust_time, agent_authorities, 
          case_status, start_time, end_time, status, effective_time, expiry_time, priority, attributes,
          version, sort, deleted, create_time, create_by, update_time, update_by, remark
        )
        SELECT 
          cp.id, cp.tenant_id, cp.tenant_code, cp.case_id, cp.relation_type, cp.source_client_id, cp.target_client_id, 
          cp.relation_desc, cp.party_type, cp.party_role, cp.agent_type, cp.entrust_time, cp.agent_authorities, 
          cp.case_status, cp.start_time, cp.end_time, cp.status, cp.effective_time, cp.expiry_time, cp.priority, cp.attributes,
          cp.version, cp.sort, cp.deleted, cp.create_time, cp.create_by, cp.update_time, cp.update_by, cp.remark
        FROM case_party cp;
    END IF;
END //
DELIMITER ;

-- 执行存储过程
CALL migrate_case_party_data();

-- 删除存储过程
DROP PROCEDURE IF EXISTS migrate_case_party_data;
  
-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 