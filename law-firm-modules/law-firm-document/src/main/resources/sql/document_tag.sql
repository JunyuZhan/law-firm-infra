-- 文档标签表
CREATE TABLE `doc_tag` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
    `tag_code` varchar(50) NOT NULL COMMENT '标签编码',
    `tag_type` varchar(20) DEFAULT NULL COMMENT '标签类型',
    `color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
    `description` varchar(200) DEFAULT NULL COMMENT '标签描述',
    `is_system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否系统标签（0：否，1：是）',
    `is_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用（0：禁用，1：启用）',
    `use_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用次数',
    `last_use_time` datetime DEFAULT NULL COMMENT '最近使用时间',
    `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
    `created_by` bigint(20) NOT NULL COMMENT '创建人',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tag_code_tenant` (`tag_code`, `tenant_id`),
    KEY `idx_tag_name` (`tag_name`),
    KEY `idx_tag_type` (`tag_type`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签表';

-- 文档标签关联表
CREATE TABLE `doc_tag_rel` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `doc_id` bigint(20) NOT NULL COMMENT '文档ID',
    `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
    `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
    `created_by` bigint(20) NOT NULL COMMENT '创建人',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_doc_tag` (`doc_id`, `tag_id`),
    KEY `idx_tag_id` (`tag_id`),
    KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档标签关联表'; 