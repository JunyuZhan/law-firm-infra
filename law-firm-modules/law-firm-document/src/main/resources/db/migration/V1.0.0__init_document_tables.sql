-- 文档表
CREATE TABLE IF NOT EXISTS `doc_document` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `category_id` BIGINT COMMENT '分类ID',
    `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
    `file_type` VARCHAR(50) NOT NULL COMMENT '文件类型',
    `file_size` BIGINT NOT NULL COMMENT '文件大小(bytes)',
    `storage_path` VARCHAR(500) NOT NULL COMMENT '存储路径',
    `storage_type` VARCHAR(20) NOT NULL COMMENT '存储类型(LOCAL/OSS/S3)',
    `hash` VARCHAR(128) COMMENT '文件哈希值',
    `description` VARCHAR(1000) COMMENT '文档描述',
    `keywords` VARCHAR(500) COMMENT '关键词',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
    `version` INT NOT NULL DEFAULT 1 COMMENT '版本号',
    `is_template` TINYINT NOT NULL DEFAULT 0 COMMENT '是否为模板(0-否,1-是)',
    `template_code` VARCHAR(100) COMMENT '模板编码',
    `create_by` BIGINT NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_by` BIGINT COMMENT '更新人',
    `update_time` DATETIME COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
    PRIMARY KEY (`id`),
    INDEX `idx_category_id` (`category_id`),
    INDEX `idx_file_type` (`file_type`),
    INDEX `idx_status` (`status`),
    INDEX `idx_create_time` (`create_time`),
    INDEX `idx_template` (`is_template`, `template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档表';

-- 文档分类表
CREATE TABLE IF NOT EXISTS `doc_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id` BIGINT DEFAULT NULL COMMENT '父分类ID',
    `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `code` VARCHAR(50) COMMENT '分类编码',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `description` VARCHAR(500) COMMENT '分类描述',
    `create_by` BIGINT NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_by` BIGINT COMMENT '更新人',
    `update_time` DATETIME COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
    PRIMARY KEY (`id`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档分类表';

-- 文档标签表
CREATE TABLE IF NOT EXISTS `doc_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
    `color` VARCHAR(20) COMMENT '标签颜色',
    `create_by` BIGINT NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_by` BIGINT COMMENT '更新人',
    `update_time` DATETIME COMMENT '更新时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签表';

-- 文档标签关联表
CREATE TABLE IF NOT EXISTS `doc_document_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `tag_id` BIGINT NOT NULL COMMENT '标签ID',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_tag` (`document_id`, `tag_id`),
    INDEX `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签关联表';

-- 文档版本表
CREATE TABLE IF NOT EXISTS `doc_version` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `version` INT NOT NULL COMMENT '版本号',
    `file_size` BIGINT NOT NULL COMMENT '文件大小(bytes)',
    `storage_path` VARCHAR(500) NOT NULL COMMENT '存储路径',
    `hash` VARCHAR(128) COMMENT '文件哈希值',
    `remark` VARCHAR(500) COMMENT '版本备注',
    `create_by` BIGINT NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_document_version` (`document_id`, `version`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档版本表';

-- 文档访问记录表
CREATE TABLE IF NOT EXISTS `doc_access_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `access_type` VARCHAR(20) NOT NULL COMMENT '访问类型(VIEW/DOWNLOAD/EDIT)',
    `ip_address` VARCHAR(50) COMMENT 'IP地址',
    `user_agent` VARCHAR(500) COMMENT '用户代理',
    `access_time` DATETIME NOT NULL COMMENT '访问时间',
    PRIMARY KEY (`id`),
    INDEX `idx_document_id` (`document_id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_access_time` (`access_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档访问记录表';

-- 文档分享表
CREATE TABLE IF NOT EXISTS `doc_share` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `document_id` BIGINT NOT NULL COMMENT '文档ID',
    `share_code` VARCHAR(32) NOT NULL COMMENT '分享码',
    `expire_time` DATETIME COMMENT '过期时间',
    `access_count` INT DEFAULT 0 COMMENT '访问次数',
    `max_access_count` INT COMMENT '最大访问次数',
    `need_password` TINYINT DEFAULT 0 COMMENT '是否需要密码(0-否,1-是)',
    `password` VARCHAR(32) COMMENT '访问密码',
    `create_by` BIGINT NOT NULL COMMENT '创建人',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_share_code` (`share_code`),
    INDEX `idx_document_id` (`document_id`),
    INDEX `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档分享表'; 