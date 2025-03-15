-- 客户模块数据库脚本

-- 客户表
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_no` varchar(32) NOT NULL COMMENT '客户编号',
  `client_name` varchar(100) NOT NULL COMMENT '客户名称',
  `client_type` tinyint NOT NULL COMMENT '客户类型（1:个人、2:企业）',
  `certificate_type` tinyint DEFAULT NULL COMMENT '证件类型（1:身份证、2:护照、3:营业执照）',
  `certificate_no` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '客户状态（1:正常、2:临时、3:归档、4:流失）',
  `lawyer_id` bigint DEFAULT NULL COMMENT '所属律师ID',
  `industry` varchar(50) DEFAULT NULL COMMENT '行业',
  `scale` varchar(50) DEFAULT NULL COMMENT '规模',
  `source_channel` tinyint DEFAULT NULL COMMENT '来源渠道（1:自主开发、2:同行推荐、3:客户介绍、4:网络推广、5:其他）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_client_no` (`client_no`),
  UNIQUE KEY `uk_certificate_no` (`certificate_no`),
  KEY `idx_lawyer_id` (`lawyer_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';

-- 客户联系人表
CREATE TABLE IF NOT EXISTS `client_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否默认联系人（0否 1是）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`),
  KEY `idx_is_default` (`is_default`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户联系人表';

-- 客户分类表
CREATE TABLE IF NOT EXISTS `client_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint DEFAULT '0' COMMENT '父分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `code` varchar(50) DEFAULT NULL COMMENT '分类编码',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户分类表';

-- 客户分类关系表
CREATE TABLE IF NOT EXISTS `client_category_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_client_category` (`client_id`,`category_id`),
  KEY `idx_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户分类关系表';

-- 客户标签表
CREATE TABLE IF NOT EXISTS `client_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(50) NOT NULL COMMENT '标签名称',
  `color` varchar(20) DEFAULT NULL COMMENT '标签颜色',
  `tag_type` tinyint DEFAULT '0' COMMENT '标签类型（0:通用、1:合同相关、2:案件相关、3:重要程度）',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1正常 0停用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_tag_type` (`tag_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户标签表';

-- 客户标签关系表
CREATE TABLE IF NOT EXISTS `client_tag_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_client_tag` (`client_id`,`tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户标签关系表';

-- 客户跟进记录表
CREATE TABLE IF NOT EXISTS `client_follow_up` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `client_id` bigint NOT NULL COMMENT '客户ID',
  `title` varchar(100) NOT NULL COMMENT '跟进标题',
  `content` text COMMENT '跟进内容',
  `follow_up_type` tinyint NOT NULL DEFAULT '1' COMMENT '跟进类型（1:电话拜访、2:上门拜访、3:邮件联系、4:微信联系、5:会议交流、6:其他）',
  `follow_up_time` datetime NOT NULL COMMENT '跟进时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1:待处理、2:已完成、3:已取消）',
  `result` text COMMENT '跟进结果',
  `next_plan` varchar(500) DEFAULT NULL COMMENT '下一步计划',
  `next_time` datetime DEFAULT NULL COMMENT '下一次跟进时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_client_id` (`client_id`),
  KEY `idx_follow_up_time` (`follow_up_time`),
  KEY `idx_status` (`status`),
  KEY `idx_create_by` (`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户跟进记录表';

-- 初始化客户分类数据
INSERT INTO `client_category` (`parent_id`, `name`, `code`, `sort`, `status`, `remark`) VALUES
(0, '个人客户', 'PERSONAL', 1, 1, '个人客户分类'),
(0, '企业客户', 'ENTERPRISE', 2, 1, '企业客户分类'),
(1, '民事法律事务', 'PERSONAL_CIVIL', 1, 1, '个人民事法律事务'),
(1, '刑事法律事务', 'PERSONAL_CRIMINAL', 2, 1, '个人刑事法律事务'),
(1, '行政法律事务', 'PERSONAL_ADMIN', 3, 1, '个人行政法律事务'),
(2, '公司法务', 'ENTERPRISE_COMPANY', 1, 1, '企业公司法务'),
(2, '知识产权', 'ENTERPRISE_IP', 2, 1, '企业知识产权'),
(2, '劳动法务', 'ENTERPRISE_LABOR', 3, 1, '企业劳动法务'),
(2, '合同法务', 'ENTERPRISE_CONTRACT', 4, 1, '企业合同法务');

-- 初始化客户标签数据
INSERT INTO `client_tag` (`tag_name`, `color`, `tag_type`, `sort`, `status`) VALUES
('重要客户', '#FF0000', 3, 1, 1),
('潜在客户', '#FFA500', 0, 2, 1),
('长期合作', '#0000FF', 0, 3, 1),
('合同纠纷', '#008000', 1, 4, 1),
('债务纠纷', '#800080', 1, 5, 1),
('刑事案件', '#A52A2A', 2, 6, 1),
('民事案件', '#2E8B57', 2, 7, 1),
('行政案件', '#DAA520', 2, 8, 1); 