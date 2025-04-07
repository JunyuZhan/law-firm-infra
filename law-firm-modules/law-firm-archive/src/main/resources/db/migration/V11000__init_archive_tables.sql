-- 初始化归档模块表结构

-- 档案资料表
CREATE TABLE IF NOT EXISTS `archive` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) NOT NULL COMMENT '档案标题',
  `description` text COMMENT '档案描述',
  `category_id` bigint(20) NOT NULL COMMENT '档案分类ID',
  `client_id` bigint(20) COMMENT '关联客户ID',
  `case_id` bigint(20) COMMENT '关联案件ID',
  `file_id` bigint(20) COMMENT '关联文件ID',
  `storage_location` varchar(255) COMMENT '实体档案存储位置',
  `retention_period` varchar(50) COMMENT '保存期限',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 1-正常, 0-已销毁',
  `is_confidential` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否机密: 1-是, 0-否',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) COMMENT '更新人',
  `updated_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除: 1-是, 0-否',
  PRIMARY KEY (`id`),
  INDEX `idx_archive_category` (`category_id`),
  INDEX `idx_archive_client` (`client_id`),
  INDEX `idx_archive_case` (`case_id`),
  INDEX `idx_archive_status` (`status`),
  INDEX `idx_archive_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案资料表';

-- 档案分类表
CREATE TABLE IF NOT EXISTS `archive_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级ID, 0表示顶级分类',
  `level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '层级',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序值',
  `description` varchar(255) COMMENT '分类描述',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) COMMENT '更新人',
  `updated_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除: 1-是, 0-否',
  PRIMARY KEY (`id`),
  INDEX `idx_archive_category_parent` (`parent_id`),
  INDEX `idx_archive_category_level` (`level`),
  INDEX `idx_archive_category_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案分类表';

-- 档案标签表
CREATE TABLE IF NOT EXISTS `archive_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_color` varchar(20) COMMENT '标签颜色',
  `description` varchar(255) COMMENT '标签描述',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) COMMENT '更新人',
  `updated_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除: 1-是, 0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_name` (`tag_name`, `deleted`),
  INDEX `idx_archive_tag_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案标签表';

-- 档案标签关联表
CREATE TABLE IF NOT EXISTS `archive_tag_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `archive_id` bigint(20) NOT NULL COMMENT '档案ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_archive_tag` (`archive_id`, `tag_id`),
  INDEX `idx_archive_tag_relation_archive` (`archive_id`),
  INDEX `idx_archive_tag_relation_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案标签关联表';

-- 档案借阅记录表
CREATE TABLE IF NOT EXISTS `archive_borrow_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `archive_id` bigint(20) NOT NULL COMMENT '档案ID',
  `borrower_id` bigint(20) NOT NULL COMMENT '借阅人ID',
  `borrow_time` datetime NOT NULL COMMENT '借阅时间',
  `planned_return_time` datetime NOT NULL COMMENT '计划归还时间',
  `actual_return_time` datetime COMMENT '实际归还时间',
  `borrow_reason` varchar(255) COMMENT '借阅原因',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 1-借出中, 2-已归还, 3-逾期未还',
  `approver_id` bigint(20) COMMENT '审批人ID',
  `approve_time` datetime COMMENT '审批时间',
  `remarks` varchar(500) COMMENT '备注',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) COMMENT '更新人',
  `updated_time` datetime ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_archive_borrow_archive` (`archive_id`),
  INDEX `idx_archive_borrow_borrower` (`borrower_id`),
  INDEX `idx_archive_borrow_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案借阅记录表';

-- 档案操作历史表
CREATE TABLE IF NOT EXISTS `archive_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `archive_id` bigint(20) NOT NULL COMMENT '档案ID',
  `operation_type` varchar(50) NOT NULL COMMENT '操作类型: create, update, borrow, return, destroy, etc.',
  `operation_user_id` bigint(20) NOT NULL COMMENT '操作人ID',
  `operation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operation_details` text COMMENT '操作详情',
  `old_values` text COMMENT '旧值(JSON格式)',
  `new_values` text COMMENT '新值(JSON格式)',
  `ip_address` varchar(50) COMMENT 'IP地址',
  PRIMARY KEY (`id`),
  INDEX `idx_archive_history_archive` (`archive_id`),
  INDEX `idx_archive_history_operation_time` (`operation_time`),
  INDEX `idx_archive_history_operation_type` (`operation_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='档案操作历史表'; 