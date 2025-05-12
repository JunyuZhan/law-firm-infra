-- 案例模块参与方表结构调整
-- 版本: V7004
-- 模块: case
-- 创建时间: 2025-05-13
-- 说明: 合并case_party和case_participant表，解决功能重叠问题
-- 依赖: V7001, V7003

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 检查case_party表是否存在
SELECT COUNT(*) INTO @case_party_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'case_party';

-- 检查module_documentation表是否存在
SELECT COUNT(*) INTO @module_documentation_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'module_documentation';

-- 如果module_documentation表不存在，先创建它
DROP PROCEDURE IF EXISTS create_module_documentation_if_not_exists;
DELIMITER //
CREATE PROCEDURE create_module_documentation_if_not_exists()
BEGIN
    IF @module_documentation_exists = 0 THEN
        CREATE TABLE IF NOT EXISTS module_documentation (
            id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
            module_name VARCHAR(50) NOT NULL COMMENT '模块名称',
            version_range VARCHAR(50) NOT NULL COMMENT '版本范围',
            responsibility_description TEXT COMMENT '职责描述',
            author VARCHAR(50) COMMENT '作者',
            last_updated DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
            version INTEGER DEFAULT 0 COMMENT '版本号',
            sort INTEGER DEFAULT 0 COMMENT '排序号',
            status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
            deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            create_by VARCHAR(50) COMMENT '创建人',
            update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
            update_by VARCHAR(50) COMMENT '更新人',
            remark VARCHAR(255) COMMENT '备注',
            PRIMARY KEY (id),
            UNIQUE KEY uk_module_version (module_name, version_range)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模块文档表';
        
        -- 记录创建表日志
        INSERT INTO sys_operation_log(operate_type, module, description, create_time, operator_name)
        VALUES ('CREATE', 'system', 'Created module_documentation table in V7004', NOW(), 'system');
    END IF;
END //
DELIMITER ;

-- 执行检查并创建module_documentation表的存储过程
CALL create_module_documentation_if_not_exists();
DROP PROCEDURE IF EXISTS create_module_documentation_if_not_exists;

-- 如果case_party表不存在，先创建它
DROP PROCEDURE IF EXISTS create_case_party_if_not_exists;
DELIMITER //
CREATE PROCEDURE create_case_party_if_not_exists()
BEGIN
    IF @case_party_exists = 0 THEN
        CREATE TABLE IF NOT EXISTS case_party (
            id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
            tenant_id BIGINT COMMENT '租户ID',
            tenant_code VARCHAR(50) COMMENT '租户编码',
            case_id BIGINT NOT NULL COMMENT '案件ID',
            relation_type VARCHAR(50) NOT NULL COMMENT '关系类型',
            source_client_id BIGINT NOT NULL COMMENT '源客户ID',
            target_client_id BIGINT NOT NULL COMMENT '目标客户ID',
            relation_desc VARCHAR(255) COMMENT '关系描述',
            party_type INTEGER NOT NULL COMMENT '当事人类型（1-原告 2-被告 3-第三人）',
            party_role VARCHAR(50) COMMENT '当事人角色',
            agent_type INTEGER COMMENT '代理类型（1-特别授权 2-一般授权）',
            entrust_time DATETIME COMMENT '委托时间',
            agent_authorities TEXT COMMENT '代理权限（JSON格式）',
            case_status INTEGER DEFAULT 0 COMMENT '案件状态（0-未开始 1-进行中 2-已结束）',
            start_time DATETIME COMMENT '开始时间',
            end_time DATETIME COMMENT '结束时间',
            status INTEGER DEFAULT 1 COMMENT '状态（0-停用 1-正常）',
            effective_time DATETIME COMMENT '生效时间',
            expiry_time DATETIME COMMENT '失效时间',
            priority INTEGER DEFAULT 0 COMMENT '优先级',
            attributes TEXT COMMENT '关系属性（JSON格式）',
            version INTEGER DEFAULT 0 COMMENT '版本号',
            sort INTEGER DEFAULT 0 COMMENT '排序号',
            deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            create_by VARCHAR(50) COMMENT '创建人',
            update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
            update_by VARCHAR(50) COMMENT '更新人',
            remark VARCHAR(255) COMMENT '备注',
            PRIMARY KEY (id),
            KEY idx_case_id (case_id),
            KEY idx_source_client_id (source_client_id),
            KEY idx_party_type (party_type)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件当事人表';
        
        -- 记录创建表日志
        INSERT INTO sys_operation_log(operate_type, module, description, create_time, operator_name)
        VALUES ('CREATE', 'case', 'Created case_party table in V7004 due to missing dependency', NOW(), 'system');
    END IF;
END //
DELIMITER ;

-- 执行检查并创建表的存储过程
CALL create_case_party_if_not_exists();
DROP PROCEDURE IF EXISTS create_case_party_if_not_exists;

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
INSERT INTO sys_operation_log(operate_type, module, description, create_time, operator_name)
VALUES ('MIGRATE', 'case', 'Merged case_party data into case_participant table to resolve duplicate functionality', NOW(), 'system');

-- 删除临时表
DROP TABLE IF EXISTS temp_case_party_data;

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 