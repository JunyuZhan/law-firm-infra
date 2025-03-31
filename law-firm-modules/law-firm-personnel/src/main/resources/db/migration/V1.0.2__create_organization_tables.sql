-- 组织结构相关表创建脚本

-- 创建部门表
CREATE TABLE IF NOT EXISTS `organization_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '部门名称',
  `code` varchar(32) NOT NULL COMMENT '部门编码',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父部门ID',
  `level` int(11) DEFAULT 0 COMMENT '层级',
  `path` varchar(255) DEFAULT NULL COMMENT '层级路径',
  `sort` int(11) DEFAULT 0 COMMENT '排序号',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '部门负责人ID',
  `description` varchar(500) DEFAULT NULL COMMENT '部门描述',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `tenant_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 创建职位表
CREATE TABLE IF NOT EXISTS `organization_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '职位名称',
  `code` varchar(32) NOT NULL COMMENT '职位编码',
  `type` tinyint(2) NOT NULL COMMENT '职位类型(1-律师职位,2-行政职位,3-管理职位)',
  `level` int(11) DEFAULT NULL COMMENT '职级',
  `rank` int(11) DEFAULT NULL COMMENT '职等',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `description` varchar(500) DEFAULT NULL COMMENT '职位描述',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `tenant_id`),
  KEY `idx_department_id` (`department_id`),
  CONSTRAINT `fk_position_department` FOREIGN KEY (`department_id`) REFERENCES `organization_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='职位表';

-- 创建团队表
CREATE TABLE IF NOT EXISTS `organization_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) NOT NULL COMMENT '团队名称',
  `code` varchar(32) NOT NULL COMMENT '团队编码',
  `type` tinyint(2) NOT NULL COMMENT '团队类型(1-业务团队,2-项目团队,3-专项团队)',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '团队负责人ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `description` varchar(500) DEFAULT NULL COMMENT '团队描述',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`, `tenant_id`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_leader_id` (`leader_id`),
  CONSTRAINT `fk_team_department` FOREIGN KEY (`department_id`) REFERENCES `organization_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

-- 创建团队成员表
CREATE TABLE IF NOT EXISTS `organization_team_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `team_id` bigint(20) NOT NULL COMMENT '团队ID',
  `employee_id` bigint(20) NOT NULL COMMENT '员工ID',
  `role` varchar(32) DEFAULT NULL COMMENT '团队角色',
  `join_time` datetime NOT NULL COMMENT '加入时间',
  `leave_time` datetime DEFAULT NULL COMMENT '离开时间',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_team_employee` (`team_id`, `employee_id`),
  KEY `idx_team_id` (`team_id`),
  KEY `idx_employee_id` (`employee_id`),
  CONSTRAINT `fk_team_member_team` FOREIGN KEY (`team_id`) REFERENCES `organization_team` (`id`),
  CONSTRAINT `fk_team_member_employee` FOREIGN KEY (`employee_id`) REFERENCES `personnel_employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队成员表';

-- 添加employee表与department、position的外键约束
ALTER TABLE `personnel_employee` 
ADD CONSTRAINT `fk_employee_department` FOREIGN KEY (`department_id`) 
REFERENCES `organization_department` (`id`);

ALTER TABLE `personnel_employee` 
ADD CONSTRAINT `fk_employee_position` FOREIGN KEY (`position_id`) 
REFERENCES `organization_position` (`id`);

-- 添加employee表与auth_user表的外键约束
ALTER TABLE `personnel_employee` 
ADD CONSTRAINT `fk_employee_user` FOREIGN KEY (`user_id`) 
REFERENCES `auth_user` (`id`) ON DELETE SET NULL;

-- 添加auth_user表中的employee_id外键约束
-- 注意：这个约束需要在auth模块中执行，这里仅作参考
-- ALTER TABLE `auth_user` 
-- ADD CONSTRAINT `fk_user_employee` FOREIGN KEY (`employee_id`) 
-- REFERENCES `personnel_employee` (`id`) ON DELETE SET NULL; 