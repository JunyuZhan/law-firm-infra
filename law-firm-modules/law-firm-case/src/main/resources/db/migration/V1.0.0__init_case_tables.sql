-- 案件信息表
CREATE TABLE IF NOT EXISTS `case_info` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `case_number` varchar(50) NOT NULL COMMENT '案件编号',
    `case_name` varchar(200) NOT NULL COMMENT '案件名称',
    `case_type` varchar(50) NOT NULL COMMENT '案件类型',
    `case_status` varchar(20) NOT NULL COMMENT '案件状态',
    `client_id` bigint NOT NULL COMMENT '委托人ID',
    `client_name` varchar(100) NOT NULL COMMENT '委托人名称',
    `lawyer_id` bigint NOT NULL COMMENT '主办律师ID',
    `lawyer_name` varchar(50) NOT NULL COMMENT '主办律师名称',
    `assist_lawyer_ids` varchar(500) DEFAULT NULL COMMENT '协办律师IDs',
    `assist_lawyer_names` varchar(500) DEFAULT NULL COMMENT '协办律师名称',
    `accept_time` datetime NOT NULL COMMENT '案件受理时间',
    `expected_end_time` datetime DEFAULT NULL COMMENT '预计结案时间',
    `actual_end_time` datetime DEFAULT NULL COMMENT '实际结案时间',
    `case_amount` decimal(20,2) DEFAULT NULL COMMENT '案件标的额',
    `case_fee` decimal(20,2) DEFAULT NULL COMMENT '案件收费',
    `case_description` text COMMENT '案件描述',
    `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_case_number` (`case_number`),
    KEY `idx_case_status` (`case_status`),
    KEY `idx_client_id` (`client_id`),
    KEY `idx_lawyer_id` (`lawyer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='案件信息表';

-- 案件状态变更记录表
CREATE TABLE IF NOT EXISTS `case_status_log` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `case_id` bigint NOT NULL COMMENT '案件ID',
    `from_status` varchar(20) NOT NULL COMMENT '原状态',
    `to_status` varchar(20) NOT NULL COMMENT '新状态',
    `operator_id` bigint NOT NULL COMMENT '操作人ID',
    `operator_name` varchar(50) NOT NULL COMMENT '操作人姓名',
    `operation_time` datetime NOT NULL COMMENT '操作时间',
    `reason` varchar(500) DEFAULT NULL COMMENT '变更原因',
    `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_case_id` (`case_id`),
    KEY `idx_operation_time` (`operation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='案件状态变更记录表';

-- 案件立案审批表
CREATE TABLE IF NOT EXISTS `case_approval` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `case_id` bigint NOT NULL COMMENT '案件ID',
    `approver_id` bigint NOT NULL COMMENT '审批人ID',
    `approver_name` varchar(50) NOT NULL COMMENT '审批人姓名',
    `approval_opinion` varchar(500) NOT NULL COMMENT '审批意见',
    `approved` tinyint(1) NOT NULL COMMENT '是否通过',
    `approval_time` datetime NOT NULL COMMENT '审批时间',
    `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_case_id` (`case_id`),
    KEY `idx_approval_time` (`approval_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='案件立案审批表';

-- 案件结案记录表
CREATE TABLE IF NOT EXISTS `case_closure` (
    `id` bigint NOT NULL COMMENT '主键ID',
    `case_id` bigint NOT NULL COMMENT '案件ID',
    `closer_id` bigint NOT NULL COMMENT '结案人ID',
    `closer_name` varchar(50) NOT NULL COMMENT '结案人姓名',
    `closure_time` datetime NOT NULL COMMENT '结案时间',
    `closure_method` varchar(50) NOT NULL COMMENT '结案方式',
    `closure_reason` varchar(500) NOT NULL COMMENT '结案原因',
    `actual_fee` decimal(20,2) NOT NULL COMMENT '实际收费金额',
    `summary` text NOT NULL COMMENT '结案总结',
    `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_case_id` (`case_id`),
    KEY `idx_closure_time` (`closure_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='案件结案记录表'; 