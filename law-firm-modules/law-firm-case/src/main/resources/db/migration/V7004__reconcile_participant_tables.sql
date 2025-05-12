-- 案例模块参与方表结构调整
-- 版本: V7004
-- 模块: case
-- 创建时间: 2025-05-13
-- 说明: 合并case_party和case_participant表，解决功能重叠问题
-- 依赖: V7001, V7003

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建临时表存储case_party中的数据
CREATE TABLE IF NOT EXISTS temp_case_party_data AS
SELECT 
    cp.id,
    cp.tenant_id,
    cp.tenant_code,
    cp.case_id,
    CAST(cp.case_id AS CHAR(50)) AS case_number,
    CASE 
        WHEN cp.party_type = 1 THEN 1  -- 原告
        WHEN cp.party_type = 2 THEN 2  -- 被告
        WHEN cp.party_type = 3 THEN 3  -- 第三人
        ELSE 99                        -- 其他
    END AS participant_type,
    COALESCE((SELECT client_name FROM client_info WHERE id = cp.source_client_id), 'Unknown') AS participant_name,
    cp.relation_desc AS role_description,
    NULL AS contact_person,
    NULL AS contact_phone,
    NULL AS contact_email,
    NULL AS contact_address,
    NULL AS id_type,
    NULL AS id_number,
    0 AS is_organization,
    NULL AS organization_type,
    NULL AS legal_representative,
    NULL AS social_credit_code,
    CASE WHEN cp.priority > 0 THEN 1 ELSE 0 END AS is_primary,
    0 AS need_notification,
    cp.remark AS remarks,
    cp.version,
    cp.sort,
    cp.status,
    cp.deleted,
    cp.create_time,
    cp.create_by,
    cp.update_time,
    cp.update_by,
    cp.remark
FROM case_party cp
WHERE NOT EXISTS (
    SELECT 1 FROM case_participant cpt 
    WHERE cpt.case_id = cp.case_id 
    AND cpt.participant_name = (SELECT client_name FROM client_info WHERE id = cp.source_client_id)
);

-- 将case_party中的数据合并到case_participant表
INSERT INTO case_participant (
    tenant_id, tenant_code, case_id, case_number, participant_type, participant_name,
    role_description, contact_person, contact_phone, contact_email, contact_address,
    id_type, id_number, is_organization, organization_type, legal_representative,
    social_credit_code, is_primary, need_notification, remarks, version, sort,
    status, deleted, create_time, create_by, update_time, update_by, remark
)
SELECT 
    tenant_id, tenant_code, case_id, case_number, participant_type, participant_name,
    role_description, contact_person, contact_phone, contact_email, contact_address,
    id_type, id_number, is_organization, organization_type, legal_representative,
    social_credit_code, is_primary, need_notification, remarks, version, sort,
    status, deleted, create_time, create_by, update_time, update_by, remark
FROM temp_case_party_data;

-- 更新案件表中的参与方引用
UPDATE case_info ci
SET client_name = (
    SELECT GROUP_CONCAT(cp.participant_name SEPARATOR ', ')
    FROM case_participant cp
    WHERE cp.case_id = ci.id
    AND cp.participant_type = 1  -- 原告/委托人
    AND cp.is_primary = 1        -- 主要参与方
    LIMIT 1
)
WHERE client_name IS NULL OR client_name = '';

-- 更新模块文档
INSERT INTO module_documentation (module_name, version_range, responsibility_description, last_updated)
VALUES ('case', 'V7000-V7999', '案件模块使用case_participant表作为唯一的案件参与方表。case_party表将被废弃，所有数据已合并至case_participant表。', NOW())
ON DUPLICATE KEY UPDATE
    responsibility_description = CONCAT(responsibility_description, ' 案件模块使用case_participant表作为唯一的案件参与方表。case_party表将被废弃，所有数据已合并至case_participant表。'),
    last_updated = NOW();

-- 添加迁移说明到系统日志
INSERT INTO sys_operation_log(operation_type, operation_module, operation_description, operation_time, operator)
VALUES ('DATA_MIGRATION', 'case', 'Merged case_party data into case_participant table to resolve duplicate functionality', NOW(), 'system');

-- 删除临时表
DROP TABLE IF EXISTS temp_case_party_data;

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 