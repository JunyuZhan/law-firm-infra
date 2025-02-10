-- 创建档案分类表
CREATE TABLE IF NOT EXISTS `archive_category` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `category_code` varchar(50) NOT NULL COMMENT '分类编码',
    `category_name` varchar(100) NOT NULL COMMENT '分类名称',
    `parent_id` bigint DEFAULT NULL COMMENT '父分类ID',
    `level` int NOT NULL DEFAULT '1' COMMENT '分类层级',
    `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序号',
    `description` varchar(500) DEFAULT NULL COMMENT '分类描述',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_category_code` (`category_code`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='档案分类表';

-- 创建档案表
CREATE TABLE IF NOT EXISTS `archive` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `archive_number` varchar(50) NOT NULL COMMENT '档案编号',
    `archive_name` varchar(200) NOT NULL COMMENT '档案名称',
    `category_id` bigint NOT NULL COMMENT '档案分类ID',
    `description` varchar(500) DEFAULT NULL COMMENT '档案描述',
    `location` varchar(200) DEFAULT NULL COMMENT '存储位置',
    `status` varchar(20) NOT NULL DEFAULT 'IN_STORAGE' COMMENT '档案状态（IN_STORAGE-在库、BORROWED-借出、DESTROYED-销毁）',
    `retention_period` varchar(20) NOT NULL COMMENT '保管期限',
    `security_level` varchar(20) NOT NULL DEFAULT 'PUBLIC' COMMENT '密级（PUBLIC-公开、INTERNAL-内部、CONFIDENTIAL-保密、SECRET-机密）',
    `filing_time` datetime NOT NULL COMMENT '归档时间',
    `filing_person` varchar(50) NOT NULL COMMENT '归档人',
    `current_borrower` varchar(50) DEFAULT NULL COMMENT '当前借阅人',
    `expected_return_time` datetime DEFAULT NULL COMMENT '预计归还时间',
    `case_id` bigint DEFAULT NULL COMMENT '关联案件ID',
    `client_id` bigint DEFAULT NULL COMMENT '关联客户ID',
    `document_path` varchar(500) DEFAULT NULL COMMENT '电子文档路径',
    `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_archive_number` (`archive_number`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_case_id` (`case_id`),
    KEY `idx_client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='档案表';

-- 创建档案借阅表
CREATE TABLE IF NOT EXISTS `archive_borrow` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `archive_id` bigint NOT NULL COMMENT '档案ID',
    `borrower` varchar(50) NOT NULL COMMENT '借阅人',
    `borrow_time` datetime NOT NULL COMMENT '借阅时间',
    `expected_return_time` datetime NOT NULL COMMENT '预计归还时间',
    `actual_return_time` datetime DEFAULT NULL COMMENT '实际归还时间',
    `borrow_reason` varchar(500) NOT NULL COMMENT '借阅原因',
    `status` varchar(20) NOT NULL DEFAULT 'BORROWED' COMMENT '借阅状态（BORROWED-借出、RETURNED-已归还、OVERDUE-超期）',
    `approver` varchar(50) NOT NULL COMMENT '审批人',
    `approval_time` datetime NOT NULL COMMENT '审批时间',
    `approval_opinion` varchar(500) DEFAULT NULL COMMENT '审批意见',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_archive_id` (`archive_id`),
    KEY `idx_borrower` (`borrower`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='档案借阅表'; 