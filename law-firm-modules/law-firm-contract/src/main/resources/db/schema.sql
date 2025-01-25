-- 创建合同表
CREATE TABLE IF NOT EXISTS `contract` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `contract_no` varchar(32) NOT NULL COMMENT '合同编号',
    `name` varchar(100) NOT NULL COMMENT '合同名称',
    `type` tinyint(4) NOT NULL COMMENT '合同类型（1-常规合同 2-委托合同 3-顾问合同）',
    `amount` decimal(12,2) DEFAULT NULL COMMENT '合同金额',
    `sign_date` datetime DEFAULT NULL COMMENT '签订日期',
    `effective_date` datetime DEFAULT NULL COMMENT '生效日期',
    `expiry_date` datetime DEFAULT NULL COMMENT '到期日期',
    `client_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
    `client_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
    `lawyer_id` bigint(20) DEFAULT NULL COMMENT '负责律师ID',
    `lawyer_name` varchar(50) DEFAULT NULL COMMENT '负责律师名称',
    `branch_id` bigint(20) DEFAULT NULL COMMENT '所属分所ID',
    `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
    `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '合同状态（1-草稿 2-审核中 3-已生效 4-已到期 5-已终止）',
    `file_id` bigint(20) DEFAULT NULL COMMENT '合同文件ID',
    `description` varchar(500) DEFAULT NULL COMMENT '合同描述',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_contract_no` (`contract_no`),
    KEY `idx_client_id` (`client_id`),
    KEY `idx_lawyer_id` (`lawyer_id`),
    KEY `idx_branch_id` (`branch_id`),
    KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- 创建合同条款表
CREATE TABLE IF NOT EXISTS `contract_clause` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `contract_id` bigint(20) NOT NULL COMMENT '合同ID',
    `title` varchar(100) NOT NULL COMMENT '条款标题',
    `content` text NOT NULL COMMENT '条款内容',
    `order_num` int(11) NOT NULL COMMENT '条款序号',
    `type` tinyint(4) NOT NULL COMMENT '条款类型（1-基本条款 2-特殊条款 3-其他条款）',
    `required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否必要条款',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_contract_id` (`contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同条款表';

-- 创建合同审批表
CREATE TABLE IF NOT EXISTS `contract_approval` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `contract_id` bigint(20) NOT NULL COMMENT '合同ID',
    `approver_id` bigint(20) NOT NULL COMMENT '审批人ID',
    `approver_name` varchar(50) NOT NULL COMMENT '审批人姓名',
    `node` tinyint(4) NOT NULL COMMENT '审批节点（1-部门负责人 2-分所负责人 3-法务审核 4-财务审核）',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审批状态（0-待审批 1-通过 2-驳回）',
    `comment` varchar(500) DEFAULT NULL COMMENT '审批意见',
    `approval_time` datetime DEFAULT NULL COMMENT '审批时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
    `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_contract_id` (`contract_id`),
    KEY `idx_approver_id` (`approver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同审批表'; 