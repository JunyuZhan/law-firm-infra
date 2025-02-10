-- 文档表
CREATE TABLE `document` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `doc_no` varchar(32) NOT NULL COMMENT '文档编号',
  `doc_name` varchar(100) NOT NULL COMMENT '文档名称',
  `doc_type` tinyint NOT NULL COMMENT '文档类型（1:合同、2:协议、3:报告、4:其他）',
  `file_type` varchar(20) NOT NULL COMMENT '文件类型（doc/docx/pdf等）',
  `file_size` bigint NOT NULL COMMENT '文件大小（字节）',
  `storage_path` varchar(255) NOT NULL COMMENT '存储路径',
  `version` int NOT NULL DEFAULT '1' COMMENT '当前版本号',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1:正常、2:已归档、3:已删除）',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键词',
  `description` varchar(500) DEFAULT NULL COMMENT '文档描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_doc_no` (`doc_no`),
  KEY `idx_doc_type` (`doc_type`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档表';

-- 文档版本表
CREATE TABLE `document_version` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `doc_id` bigint NOT NULL COMMENT '文档ID',
  `version_no` int NOT NULL COMMENT '版本号',
  `file_size` bigint NOT NULL COMMENT '文件大小（字节）',
  `storage_path` varchar(255) NOT NULL COMMENT '存储路径',
  `change_desc` varchar(500) DEFAULT NULL COMMENT '变更说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_doc_version` (`doc_id`, `version_no`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文档版本表'; 