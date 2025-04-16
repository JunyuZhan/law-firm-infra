/* 
 * 知识管理模块数据库优化脚本
 * 版本号V9004 - 知识库模块使用V9000-V9899范围
 * (注意V9900已用于跨模块约束)
 */

-- 知识管理模块数据库优化脚本 V9004

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

-- 添加审批状态
ALTER TABLE knowledge_document 
ADD COLUMN approval_status TINYINT(1) DEFAULT 1 COMMENT '审批状态: 0-待审批, 1-已通过, 2-已拒绝',
ADD COLUMN approver_id BIGINT(20) DEFAULT NULL COMMENT '审批人ID',
ADD COLUMN approval_time DATETIME DEFAULT NULL COMMENT '审批时间',
ADD COLUMN approval_remarks VARCHAR(500) DEFAULT NULL COMMENT '审批备注';

-- 添加阅读权限控制
ALTER TABLE knowledge_document 
ADD COLUMN permission_type TINYINT(1) DEFAULT 0 COMMENT '权限类型: 0-公开, 1-部门内, 2-指定人员';

-- 添加知识文档访问记录表
CREATE TABLE IF NOT EXISTS `knowledge_access_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `knowledge_id` bigint(20) NOT NULL COMMENT '知识ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `access_type` varchar(20) NOT NULL COMMENT '访问类型: view, download, edit',
  `access_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  PRIMARY KEY (`id`),
  INDEX `idx_knowledge_access_knowledge` (`knowledge_id`),
  INDEX `idx_knowledge_access_user` (`user_id`),
  INDEX `idx_knowledge_access_time` (`access_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识访问记录表';

-- 添加权限配置表
CREATE TABLE IF NOT EXISTS `knowledge_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `knowledge_id` bigint(20) NOT NULL COMMENT '知识ID',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID(用户ID、部门ID等)',
  `target_type` varchar(20) NOT NULL COMMENT '目标类型: user, department, role, team',
  `permission_type` varchar(20) NOT NULL COMMENT '权限类型: read, edit, delete',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_knowledge_permission` (`knowledge_id`, `target_id`, `target_type`, `permission_type`),
  INDEX `idx_knowledge_permission_knowledge` (`knowledge_id`),
  INDEX `idx_knowledge_permission_target` (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识权限表';