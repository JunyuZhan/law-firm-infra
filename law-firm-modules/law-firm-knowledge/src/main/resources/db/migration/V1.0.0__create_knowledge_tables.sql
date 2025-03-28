-- 知识管理模块数据库迁移脚本 V1.0.0

-- 知识文档表
CREATE TABLE IF NOT EXISTS `knowledge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` longtext COMMENT '内容',
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `keywords` varchar(200) DEFAULT NULL COMMENT '关键词',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `knowledge_type` varchar(20) NOT NULL COMMENT '知识类型',
  `view_count` int(11) DEFAULT '0' COMMENT '查看次数',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞次数',
  `author_id` bigint(20) DEFAULT NULL COMMENT '作者ID',
  `author_name` varchar(50) DEFAULT NULL COMMENT '作者姓名',
  `status` varchar(20) DEFAULT 'DRAFT' COMMENT '状态（DRAFT草稿/PUBLISHED已发布/ARCHIVED已归档）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_knowledge_category` (`category_id`),
  KEY `idx_knowledge_type` (`knowledge_type`),
  KEY `idx_knowledge_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识文档表';

-- 知识分类表
CREATE TABLE IF NOT EXISTS `knowledge_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `code` varchar(50) DEFAULT NULL COMMENT '分类编码',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID，0表示顶级分类',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `description` varchar(200) DEFAULT NULL COMMENT '分类描述',
  `status` varchar(20) DEFAULT 'NORMAL' COMMENT '状态（NORMAL正常/DISABLED禁用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_category_parent` (`parent_id`),
  KEY `idx_category_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识分类表';

-- 知识标签表
CREATE TABLE IF NOT EXISTS `knowledge_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `code` varchar(50) DEFAULT NULL COMMENT '标签编码',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `status` varchar(20) DEFAULT 'NORMAL' COMMENT '状态（NORMAL正常/DISABLED禁用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_tag_name` (`name`),
  KEY `idx_tag_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识标签表';

-- 知识标签关联表
CREATE TABLE IF NOT EXISTS `knowledge_tag_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `knowledge_id` bigint(20) NOT NULL COMMENT '知识ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_knowledge_tag` (`knowledge_id`,`tag_id`),
  KEY `idx_relation_knowledge` (`knowledge_id`),
  KEY `idx_relation_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识标签关联表';

-- 知识附件表
CREATE TABLE IF NOT EXISTS `knowledge_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `knowledge_id` bigint(20) NOT NULL COMMENT '知识ID',
  `file_name` varchar(100) NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小（字节）',
  `file_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `sort` int(11) DEFAULT '0' COMMENT '排序号',
  `storage_id` bigint(20) DEFAULT NULL COMMENT '存储ID（关联存储系统）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_attachment_knowledge` (`knowledge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识附件表'; 