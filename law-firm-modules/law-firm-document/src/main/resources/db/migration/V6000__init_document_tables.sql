-- 文档表
CREATE TABLE IF NOT EXISTS `doc_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` bigint(20) DEFAULT NULL COMMENT '文档分类ID',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小(字节)',
  `storage_path` varchar(500) NOT NULL COMMENT '存储路径',
  `storage_type` varchar(50) DEFAULT 'LOCAL' COMMENT '存储类型(LOCAL-本地存储,OSS-对象存储,S3-亚马逊S3)',
  `description` text COMMENT '文档描述',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键词(逗号分隔)',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
  `version` int(11) DEFAULT 1 COMMENT '版本号',
  `is_template` tinyint(1) DEFAULT 0 COMMENT '是否模板(0-否,1-是)',
  `template_code` varchar(100) DEFAULT NULL COMMENT '模板编码',
  `case_id` bigint(20) DEFAULT NULL COMMENT '关联案件ID',
  `client_id` bigint(20) DEFAULT NULL COMMENT '关联客户ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  PRIMARY KEY (`id`),
  INDEX `idx_doc_category_id` (`category_id`),
  INDEX `idx_doc_case_id` (`case_id`),
  INDEX `idx_doc_client_id` (`client_id`),
  INDEX `idx_doc_create_time` (`create_time`),
  INDEX `idx_doc_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档表';

-- 文档分类表
CREATE TABLE IF NOT EXISTS `doc_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级分类ID',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `code` varchar(50) NOT NULL COMMENT '分类编码',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `description` varchar(500) DEFAULT NULL COMMENT '分类描述',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`code`),
  INDEX `idx_category_parent_id` (`parent_id`),
  INDEX `idx_category_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档分类表';

-- 文档标签表
CREATE TABLE IF NOT EXISTS `doc_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  PRIMARY KEY (`id`),
  INDEX `idx_tag_name` (`name`),
  INDEX `idx_tag_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签表';

-- 文档标签关联表
CREATE TABLE IF NOT EXISTS `doc_document_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `document_id` bigint(20) NOT NULL COMMENT '文档ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_doc_tag` (`document_id`,`tag_id`),
  INDEX `idx_doc_tag_document_id` (`document_id`),
  INDEX `idx_doc_tag_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签关联表';

-- 文档版本表
CREATE TABLE IF NOT EXISTS `doc_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `document_id` bigint(20) NOT NULL COMMENT '文档ID',
  `version` int(11) NOT NULL COMMENT '版本号',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小(字节)',
  `storage_path` varchar(500) NOT NULL COMMENT '存储路径',
  `change_log` varchar(500) DEFAULT NULL COMMENT '变更说明',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_document_version` (`document_id`,`version`),
  INDEX `idx_version_document_id` (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档版本表';

-- 文档访问日志表
CREATE TABLE IF NOT EXISTS `doc_access_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `document_id` bigint(20) NOT NULL COMMENT '文档ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `operation` varchar(50) NOT NULL COMMENT '操作类型(view-查看,download-下载,edit-编辑,delete-删除)',
  `access_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  PRIMARY KEY (`id`),
  INDEX `idx_access_document_id` (`document_id`),
  INDEX `idx_access_user_id` (`user_id`),
  INDEX `idx_access_time` (`access_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档访问日志表';

-- 文档分享表
CREATE TABLE IF NOT EXISTS `doc_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `document_id` bigint(20) NOT NULL COMMENT '文档ID',
  `share_code` varchar(32) NOT NULL COMMENT '分享码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `access_count` int(11) DEFAULT 0 COMMENT '访问次数',
  `max_access_count` int(11) DEFAULT NULL COMMENT '最大访问次数(NULL表示不限制)',
  `password` varchar(32) DEFAULT NULL COMMENT '访问密码',
  `is_download` tinyint(1) DEFAULT 0 COMMENT '是否允许下载(0-否,1-是)',
  `create_by` bigint(20) NOT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_share_code` (`share_code`),
  INDEX `idx_share_document_id` (`document_id`),
  INDEX `idx_share_expire_time` (`expire_time`),
  INDEX `idx_share_is_deleted` (`is_deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档分享表'; 