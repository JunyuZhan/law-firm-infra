-- 创建案件合同关联表
CREATE TABLE IF NOT EXISTS `case_contract_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `case_id` bigint(20) NOT NULL COMMENT '案件ID',
  `contract_id` bigint(20) NOT NULL COMMENT '合同ID',
  `relation_type` tinyint(4) DEFAULT NULL COMMENT '关联类型（1:委托合同, 2:服务合同, 3:其他）',
  `relation_desc` varchar(255) DEFAULT NULL COMMENT '关联描述',
  `tenant_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_case_contract` (`case_id`,`contract_id`,`deleted`),
  KEY `idx_contract_id` (`contract_id`),
  KEY `idx_case_id` (`case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='案件合同关联表'; 