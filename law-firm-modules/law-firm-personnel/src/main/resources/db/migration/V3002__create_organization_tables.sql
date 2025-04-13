-- 创建组织机构相关表

-- 创建部门表
CREATE TABLE IF NOT EXISTS `organization_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '部门编码',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门ID',
  `level` int(11) NOT NULL DEFAULT '1' COMMENT '部门层级',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '部门负责人ID（关联employee表）',
  `description` varchar(200) DEFAULT NULL COMMENT '部门描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_department_code` (`code`),
  KEY `idx_department_parent_id` (`parent_id`),
  KEY `idx_department_leader_id` (`leader_id`),
  KEY `idx_department_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 创建职位表
CREATE TABLE IF NOT EXISTS `organization_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '职位编码',
  `name` varchar(50) NOT NULL COMMENT '职位名称',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '职位类型：1-律师职位，2-行政职位，3-管理职位，0-其他',
  `level` int(11) NOT NULL DEFAULT '1' COMMENT '职位级别',
  `rank` int(11) NOT NULL DEFAULT '0' COMMENT '职级排序',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `description` varchar(200) DEFAULT NULL COMMENT '职位描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_position_code` (`code`),
  KEY `idx_position_department_id` (`department_id`),
  KEY `idx_position_type` (`type`),
  KEY `idx_position_level` (`level`),
  KEY `idx_position_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位表';

-- 创建团队表
CREATE TABLE IF NOT EXISTS `organization_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '团队编码',
  `name` varchar(50) NOT NULL COMMENT '团队名称',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '团队类型：1-案件团队，2-项目团队，3-专项团队，0-其他',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '团队负责人ID（关联employee表）',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `description` varchar(200) DEFAULT NULL COMMENT '团队描述',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_team_code` (`code`),
  KEY `idx_team_leader_id` (`leader_id`),
  KEY `idx_team_department_id` (`department_id`),
  KEY `idx_team_type` (`type`),
  KEY `idx_team_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

-- 创建团队成员表
CREATE TABLE IF NOT EXISTS `organization_team_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `team_id` bigint(20) NOT NULL COMMENT '团队ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `role` tinyint(4) NOT NULL DEFAULT '1' COMMENT '团队角色：1-负责人，2-主办人，3-协办人，4-其他成员',
  `join_date` date DEFAULT NULL COMMENT '加入日期',
  `leave_date` date DEFAULT NULL COMMENT '离开日期',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_team_employee` (`team_id`,`employee_id`),
  KEY `idx_team_member_team_id` (`team_id`),
  KEY `idx_team_member_employee_id` (`employee_id`),
  KEY `idx_team_member_role` (`role`),
  KEY `idx_team_member_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队成员表'; 