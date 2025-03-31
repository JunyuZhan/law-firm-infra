-- 客户基本信息表
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_name` varchar(100) NOT NULL COMMENT '客户名称',
  `client_type` varchar(20) NOT NULL COMMENT '客户类型(individual-个人,enterprise-企业,government-政府机构)',
  `importance` tinyint(4) DEFAULT 3 COMMENT '重要程度(1-5)',
  `source` varchar(50) DEFAULT NULL COMMENT '客户来源',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-无效,1-有效)',
  `credit_code` varchar(50) DEFAULT NULL COMMENT '统一社会信用代码(企业客户)',
  `industry` varchar(50) DEFAULT NULL COMMENT '所属行业',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `website` varchar(255) DEFAULT NULL COMMENT '网站',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `principal_id` bigint(20) DEFAULT NULL COMMENT '负责人ID',
  `principal_name` varchar(50) DEFAULT NULL COMMENT '负责人姓名',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `team_id` bigint(20) DEFAULT NULL COMMENT '所属团队ID',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  INDEX `idx_client_name` (`client_name`),
  INDEX `idx_client_type` (`client_type`),
  INDEX `idx_principal_id` (`principal_id`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息表';

-- 个人客户表
CREATE TABLE IF NOT EXISTS `client_individual` (
  `id` bigint(20) NOT NULL COMMENT '关联客户ID',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` char(1) DEFAULT NULL COMMENT '性别(M-男,F-女)',
  `id_type` varchar(20) DEFAULT NULL COMMENT '证件类型',
  `id_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `profession` varchar(50) DEFAULT NULL COMMENT '职业',
  `education` varchar(50) DEFAULT NULL COMMENT '学历',
  `marital_status` varchar(20) DEFAULT NULL COMMENT '婚姻状况',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_individual_client` FOREIGN KEY (`id`) REFERENCES `client` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='个人客户信息表';

-- 企业客户表
CREATE TABLE IF NOT EXISTS `client_enterprise` (
  `id` bigint(20) NOT NULL COMMENT '关联客户ID',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `legal_representative` varchar(50) DEFAULT NULL COMMENT '法定代表人',
  `registered_capital` decimal(20,2) DEFAULT NULL COMMENT '注册资本',
  `established_date` date DEFAULT NULL COMMENT '成立日期',
  `business_scope` text DEFAULT NULL COMMENT '经营范围',
  `company_type` varchar(50) DEFAULT NULL COMMENT '公司类型',
  `organization_type` varchar(50) DEFAULT NULL COMMENT '组织形式',
  `staff_size` int(11) DEFAULT NULL COMMENT '员工规模',
  `annual_revenue` decimal(20,2) DEFAULT NULL COMMENT '年营业额',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_enterprise_client` FOREIGN KEY (`id`) REFERENCES `client` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业客户信息表';

-- 客户联系人表
CREATE TABLE IF NOT EXISTS `client_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint(20) NOT NULL COMMENT '客户ID',
  `name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `is_primary` tinyint(1) DEFAULT 0 COMMENT '是否主要联系人(0-否,1-是)',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  INDEX `idx_client_id` (`client_id`),
  CONSTRAINT `fk_contact_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户联系人表';

-- 客户跟进记录表
CREATE TABLE IF NOT EXISTS `client_follow_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint(20) NOT NULL COMMENT '客户ID',
  `follow_type` varchar(20) NOT NULL COMMENT '跟进类型(visit-拜访,call-电话,email-邮件,meeting-会议,other-其他)',
  `follow_time` datetime NOT NULL COMMENT '跟进时间',
  `content` text NOT NULL COMMENT '跟进内容',
  `next_follow_time` datetime DEFAULT NULL COMMENT '下次跟进时间',
  `next_plan` varchar(500) DEFAULT NULL COMMENT '下次计划',
  `status` varchar(20) DEFAULT NULL COMMENT '跟进状态',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `creator_name` varchar(50) NOT NULL COMMENT '创建人姓名',
  `contact_id` bigint(20) DEFAULT NULL COMMENT '联系人ID',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_client_follow_client_id` (`client_id`),
  INDEX `idx_client_follow_time` (`follow_time`),
  INDEX `idx_client_creator_id` (`creator_id`),
  CONSTRAINT `fk_follow_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户跟进记录表';

-- 客户标签表
CREATE TABLE IF NOT EXISTS `client_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `tag_type` varchar(20) DEFAULT NULL COMMENT '标签类型',
  `tag_color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户标签表';

-- 客户标签关联表
CREATE TABLE IF NOT EXISTS `client_tag_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint(20) NOT NULL COMMENT '客户ID',
  `tag_id` bigint(20) NOT NULL COMMENT '标签ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_client_tag` (`client_id`,`tag_id`),
  CONSTRAINT `fk_tag_relation_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_tag_relation_tag` FOREIGN KEY (`tag_id`) REFERENCES `client_tag` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户标签关联表'; 