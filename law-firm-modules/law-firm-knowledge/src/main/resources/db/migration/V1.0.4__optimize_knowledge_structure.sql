-- 知识管理模块数据库优化脚本 V1.0.4

-- 为知识文档表添加全文索引
ALTER TABLE `knowledge_document` 
ADD FULLTEXT INDEX `ftidx_knowledge_content` (`title`, `content`, `summary`, `keywords`) WITH PARSER ngram;

-- 为知识文档表添加复合索引
ALTER TABLE `knowledge_document` 
ADD INDEX `idx_knowledge_type_status` (`knowledge_type`, `status`, `tenant_id`);

-- 为知识文档表添加查询频率高的复合索引
ALTER TABLE `knowledge_document` 
ADD INDEX `idx_knowledge_tenant_status` (`tenant_id`, `status`, `create_time`);

-- 为知识分类表添加复合索引
ALTER TABLE `knowledge_category` 
ADD INDEX `idx_category_tenant_status` (`tenant_id`, `status`, `sort`);

-- 为知识标签表添加复合索引
ALTER TABLE `knowledge_tag` 
ADD INDEX `idx_tag_tenant_status` (`tenant_id`, `status`, `sort`);

-- 为知识附件表添加复合索引
ALTER TABLE `knowledge_attachment` 
ADD INDEX `idx_attachment_tenant` (`tenant_id`, `knowledge_id`);

-- 确保知识标签关联表包含必要的复合索引
ALTER TABLE `knowledge_tag_relation` 
ADD INDEX `idx_tag_relation_tenant` (`tenant_id`, `knowledge_id`, `tag_id`);

-- 为表添加统一的审计字段
ALTER TABLE `knowledge_tag_relation`
ADD COLUMN `version` int(11) DEFAULT 0 COMMENT '版本号' AFTER `tenant_id`,
ADD COLUMN `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除（0否 1是）' AFTER `version`,
ADD COLUMN `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `create_time`,
ADD COLUMN `update_by` varchar(50) DEFAULT NULL COMMENT '更新人' AFTER `create_by`;

-- 为知识标签关联表添加租户编码字段
ALTER TABLE `knowledge_tag_relation`
ADD COLUMN `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码' AFTER `tenant_id`; 