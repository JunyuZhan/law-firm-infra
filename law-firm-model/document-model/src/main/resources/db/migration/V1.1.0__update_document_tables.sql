-- 更新文档主表
DROP TABLE IF EXISTS `doc_document`;
CREATE TABLE `doc_document` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_number` varchar(50) NOT NULL COMMENT '文档编号',
    `document_name` varchar(200) NOT NULL COMMENT '文档名称',
    `document_type` varchar(50) NOT NULL COMMENT '文档类型',
    `security_level` varchar(50) NOT NULL COMMENT '文档密级',
    `document_status` varchar(50) NOT NULL DEFAULT 'DRAFT' COMMENT '文档状态',
    `category_id` bigint NOT NULL COMMENT '分类ID',
    `folder` varchar(500) DEFAULT NULL COMMENT '所属文件夹',
    `tags` varchar(1000) DEFAULT NULL COMMENT '标签（JSON数组）',
    `law_firm_id` bigint NOT NULL COMMENT '律所ID',
    `case_id` bigint DEFAULT NULL COMMENT '案件ID',
    `client_id` bigint DEFAULT NULL COMMENT '客户ID',
    `contract_id` bigint DEFAULT NULL COMMENT '合同ID',
    `department_id` varchar(50) DEFAULT NULL COMMENT '所属部门',
    `keywords` varchar(500) DEFAULT NULL COMMENT '关键词',
    `summary` varchar(2000) DEFAULT NULL COMMENT '文档摘要',
    `current_version` varchar(20) DEFAULT NULL COMMENT '当前版本号',
    `retention_time` datetime DEFAULT NULL COMMENT '保留期限',
    `related_documents` varchar(500) DEFAULT NULL COMMENT '关联文档IDs（JSON数组）',
    `template_id` varchar(100) DEFAULT NULL COMMENT '模板ID',
    `language` varchar(100) DEFAULT NULL COMMENT '文档语言',
    `need_archive` tinyint(1) DEFAULT '0' COMMENT '是否需要归档',
    `archive_time` datetime DEFAULT NULL COMMENT '归档时间',
    `ocr_status` varchar(20) DEFAULT 'NOT_STARTED' COMMENT 'OCR状态',
    `ocr_time` datetime DEFAULT NULL COMMENT 'OCR完成时间',
    `ocr_error_message` varchar(500) DEFAULT NULL COMMENT 'OCR错误信息',
    `status` varchar(20) NOT NULL DEFAULT 'ENABLED' COMMENT '状态',
    `version` int DEFAULT '0' COMMENT '版本号',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_number` (`document_number`),
    KEY `idx_document_type` (`document_type`),
    KEY `idx_document_status` (`document_status`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_law_firm_id` (`law_firm_id`),
    KEY `idx_case_id` (`case_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档表';

-- 文档存储表
DROP TABLE IF EXISTS `doc_document_storage`;
CREATE TABLE `doc_document_storage` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` bigint NOT NULL COMMENT '文档ID',
    `file_name` varchar(200) NOT NULL COMMENT '文件名',
    `file_type` varchar(50) NOT NULL COMMENT '文件类型',
    `file_size` bigint NOT NULL COMMENT '文件大小（字节）',
    `storage_path` varchar(500) NOT NULL COMMENT '存储路径',
    `md5` varchar(32) DEFAULT NULL COMMENT '文件MD5',
    `status` varchar(20) NOT NULL DEFAULT 'ENABLED' COMMENT '状态',
    `version` int DEFAULT '0' COMMENT '版本号',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_id` (`document_id`),
    CONSTRAINT `fk_storage_document_id` FOREIGN KEY (`document_id`) REFERENCES `doc_document` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档存储表';

-- 文档版本表
DROP TABLE IF EXISTS `doc_document_version`;
CREATE TABLE `doc_document_version` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` bigint NOT NULL COMMENT '文档ID',
    `version_number` varchar(20) NOT NULL COMMENT '版本号',
    `file_name` varchar(200) NOT NULL COMMENT '文件名',
    `file_type` varchar(50) NOT NULL COMMENT '文件类型',
    `file_size` bigint NOT NULL COMMENT '文件大小（字节）',
    `storage_path` varchar(500) NOT NULL COMMENT '存储路径',
    `md5` varchar(32) DEFAULT NULL COMMENT '文件MD5',
    `change_description` varchar(2000) DEFAULT NULL COMMENT '变更说明',
    `status` varchar(20) NOT NULL DEFAULT 'ENABLED' COMMENT '状态',
    `version` int DEFAULT '0' COMMENT '版本号',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_version` (`document_id`, `version_number`),
    CONSTRAINT `fk_version_document_id` FOREIGN KEY (`document_id`) REFERENCES `doc_document` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档版本表';

-- 数据迁移（如果需要）
INSERT INTO doc_document (
    document_number,
    document_name,
    document_type,
    security_level,
    document_status,
    category_id,
    law_firm_id,
    create_time,
    update_time,
    create_by,
    update_by,
    deleted,
    status
)
SELECT 
    doc_no,
    doc_name,
    CASE doc_type 
        WHEN 1 THEN 'CONTRACT'
        WHEN 2 THEN 'AGREEMENT'
        WHEN 3 THEN 'REPORT'
        ELSE 'OTHER'
    END,
    'NORMAL',  -- 默认安全级别
    CASE status
        WHEN 1 THEN 'NORMAL'
        WHEN 2 THEN 'ARCHIVED'
        WHEN 3 THEN 'DELETED'
    END,
    0,  -- 默认分类ID
    0,  -- 默认律所ID
    create_time,
    update_time,
    create_by,
    update_by,
    deleted,
    CASE WHEN status = 1 THEN 'ENABLED' ELSE 'DISABLED' END
FROM document
WHERE EXISTS (SELECT 1 FROM document LIMIT 1);

-- 迁移文档存储信息
INSERT INTO doc_document_storage (
    document_id,
    file_name,
    file_type,
    file_size,
    storage_path,
    create_time,
    update_time,
    status
)
SELECT 
    d2.id,
    d1.doc_name,
    d1.file_type,
    d1.file_size,
    d1.storage_path,
    d1.create_time,
    d1.update_time,
    'ENABLED'
FROM document d1
JOIN doc_document d2 ON d1.doc_no = d2.document_number
WHERE EXISTS (SELECT 1 FROM document LIMIT 1);

-- 迁移版本信息
INSERT INTO doc_document_version (
    document_id,
    version_number,
    file_name,
    file_type,
    file_size,
    storage_path,
    change_description,
    create_time,
    create_by,
    status
)
SELECT 
    d2.id,
    CAST(dv.version_no AS CHAR),
    d2.document_name,
    'UNKNOWN',  -- 默认文件类型
    dv.file_size,
    dv.storage_path,
    dv.change_desc,
    dv.create_time,
    dv.create_by,
    'ENABLED'
FROM document_version dv
JOIN doc_document d2 ON dv.doc_id = d2.id
WHERE EXISTS (SELECT 1 FROM document_version LIMIT 1);

-- 删除旧表（可选，建议先保留一段时间）
-- DROP TABLE IF EXISTS document_version;
-- DROP TABLE IF EXISTS document; 