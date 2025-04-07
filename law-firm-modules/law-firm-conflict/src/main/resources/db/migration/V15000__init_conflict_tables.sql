-- 冲突检查模块表结构初始化

-- 冲突检查规则表
CREATE TABLE IF NOT EXISTS `conflict_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_name` varchar(100) NOT NULL COMMENT '规则名称',
  `rule_code` varchar(50) NOT NULL COMMENT '规则编码',
  `description` varchar(500) DEFAULT NULL COMMENT '规则描述',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `priority` int(11) NOT NULL DEFAULT 0 COMMENT '优先级(数字越大优先级越高)',
  `rule_type` varchar(50) NOT NULL COMMENT '规则类型(client-客户,case-案件,other-其他)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0-否,1-是)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rule_code` (`rule_code`),
  INDEX `idx_rule_type` (`rule_type`),
  INDEX `idx_rule_status` (`status`),
  INDEX `idx_rule_priority` (`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突检查规则表';

-- 冲突检查记录表
CREATE TABLE IF NOT EXISTS `conflict_check` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_code` varchar(32) NOT NULL COMMENT '检查编码',
  `check_type` varchar(50) NOT NULL COMMENT '检查类型(client-客户,case-案件,other-其他)',
  `target_id` bigint(20) DEFAULT NULL COMMENT '目标ID(客户ID或案件ID)',
  `target_name` varchar(255) DEFAULT NULL COMMENT '目标名称',
  `initiator_id` bigint(20) NOT NULL COMMENT '发起人ID',
  `initiator_name` varchar(50) NOT NULL COMMENT '发起人姓名',
  `status` varchar(20) NOT NULL COMMENT '检查状态(pending-待检查,checking-检查中,conflict-存在冲突,no_conflict-无冲突)',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  `result_summary` text DEFAULT NULL COMMENT '结果摘要',
  `has_conflict` tinyint(1) DEFAULT NULL COMMENT '是否存在冲突(0-否,1-是)',
  `approver_id` bigint(20) DEFAULT NULL COMMENT '审批人ID',
  `approver_name` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_result` varchar(20) DEFAULT NULL COMMENT '审批结果(approved-通过,rejected-拒绝)',
  `approve_comment` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_check_code` (`check_code`),
  INDEX `idx_check_target` (`target_id`),
  INDEX `idx_check_type` (`check_type`),
  INDEX `idx_check_status` (`status`),
  INDEX `idx_check_time` (`check_time`),
  INDEX `idx_check_result` (`has_conflict`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突检查记录表';

-- 冲突检查详情表
CREATE TABLE IF NOT EXISTS `conflict_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_id` bigint(20) NOT NULL COMMENT '检查记录ID',
  `rule_id` bigint(20) NOT NULL COMMENT '检查规则ID',
  `rule_name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `conflict_type` varchar(50) DEFAULT NULL COMMENT '冲突类型',
  `conflict_level` varchar(20) DEFAULT NULL COMMENT '冲突级别(low-低,medium-中,high-高)',
  `conflict_target_id` bigint(20) DEFAULT NULL COMMENT '冲突对象ID',
  `conflict_target_name` varchar(255) DEFAULT NULL COMMENT '冲突对象名称',
  `conflict_description` text DEFAULT NULL COMMENT '冲突描述',
  `suggestion` varchar(500) DEFAULT NULL COMMENT '处理建议',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_detail_check_id` (`check_id`),
  INDEX `idx_detail_rule_id` (`rule_id`),
  INDEX `idx_detail_level` (`conflict_level`),
  INDEX `idx_detail_target` (`conflict_target_id`),
  CONSTRAINT `fk_detail_check` FOREIGN KEY (`check_id`) REFERENCES `conflict_check` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突检查详情表';

-- 冲突豁免表
CREATE TABLE IF NOT EXISTS `conflict_waiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_id` bigint(20) NOT NULL COMMENT '检查记录ID',
  `detail_id` bigint(20) DEFAULT NULL COMMENT '检查详情ID',
  `waiver_reason` varchar(500) NOT NULL COMMENT '豁免原因',
  `waiver_by` bigint(20) NOT NULL COMMENT '豁免人ID',
  `waiver_name` varchar(50) NOT NULL COMMENT '豁免人姓名',
  `waiver_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '豁免时间',
  `approver_id` bigint(20) DEFAULT NULL COMMENT '审批人ID',
  `approver_name` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_result` varchar(20) DEFAULT NULL COMMENT '审批结果(approved-通过,rejected-拒绝)',
  `approve_comment` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `waiver_document` varchar(255) DEFAULT NULL COMMENT '豁免文档路径',
  `status` varchar(20) NOT NULL DEFAULT 'pending' COMMENT '状态(pending-待审批,approved-已批准,rejected-已拒绝)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_waiver_check_id` (`check_id`),
  INDEX `idx_waiver_detail_id` (`detail_id`),
  INDEX `idx_waiver_status` (`status`),
  CONSTRAINT `fk_waiver_check` FOREIGN KEY (`check_id`) REFERENCES `conflict_check` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突豁免表';

-- 冲突规则参数表
CREATE TABLE IF NOT EXISTS `conflict_rule_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_id` bigint(20) NOT NULL COMMENT '规则ID',
  `param_name` varchar(50) NOT NULL COMMENT '参数名称',
  `param_key` varchar(50) NOT NULL COMMENT '参数键',
  `param_value` varchar(255) DEFAULT NULL COMMENT '参数值',
  `param_type` varchar(20) NOT NULL COMMENT '参数类型(string-字符串,number-数字,boolean-布尔值,date-日期)',
  `is_required` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否必填(0-否,1-是)',
  `description` varchar(255) DEFAULT NULL COMMENT '参数描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_param_rule_id` (`rule_id`),
  CONSTRAINT `fk_param_rule` FOREIGN KEY (`rule_id`) REFERENCES `conflict_rule` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突规则参数表';

-- 冲突审批记录表
CREATE TABLE IF NOT EXISTS `conflict_approval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `check_record_id` bigint(20) NOT NULL COMMENT '检查记录ID',
  `applicant_id` bigint(20) NOT NULL COMMENT '申请人ID',
  `applicant_name` varchar(50) NOT NULL COMMENT '申请人姓名',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `apply_reason` varchar(500) NOT NULL COMMENT '申请理由',
  `status` varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态(PENDING-待审批,APPROVED-已批准,REJECTED-已拒绝)',
  `approver_id` bigint(20) DEFAULT NULL COMMENT '审批人ID',
  `approver_name` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_comment` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_approval_record` (`check_record_id`),
  INDEX `idx_applicant` (`applicant_id`),
  INDEX `idx_status` (`status`),
  CONSTRAINT `fk_approval_record` FOREIGN KEY (`check_record_id`) REFERENCES `conflict_check` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突审批记录表';

-- 冲突白名单表
CREATE TABLE IF NOT EXISTS `conflict_whitelist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `entity_type` varchar(20) NOT NULL COMMENT '实体类型(PARTY-当事人,CLIENT-客户,CASE-案件)',
  `entity_id` bigint(20) NOT NULL COMMENT '实体ID',
  `entity_name` varchar(200) NOT NULL COMMENT '实体名称',
  `rule_id` bigint(20) DEFAULT NULL COMMENT '规则ID(NULL表示全部规则)',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间(NULL表示永不过期)',
  `reason` varchar(500) NOT NULL COMMENT '加入白名单原因',
  `created_by` bigint(20) NOT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_entity_rule` (`entity_type`, `entity_id`, `rule_id`),
  INDEX `idx_entity` (`entity_type`, `entity_id`),
  INDEX `idx_rule` (`rule_id`),
  INDEX `idx_expired_time` (`expired_time`),
  INDEX `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突白名单表'; 