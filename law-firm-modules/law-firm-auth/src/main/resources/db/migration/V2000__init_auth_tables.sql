-- 创建认证相关表

-- 创建权限表
CREATE TABLE IF NOT EXISTS `auth_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '权限编码',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '权限类型：1-菜单 2-按钮 3-接口 4-数据',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `module` varchar(50) NOT NULL COMMENT '所属模块',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `component` varchar(100) DEFAULT NULL COMMENT '前端组件',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `hidden` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏：0-否 1-是',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用 1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否 1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`code`),
  KEY `idx_permission_parent_id` (`parent_id`),
  KEY `idx_permission_type` (`type`),
  KEY `idx_permission_module` (`module`),
  KEY `idx_permission_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 创建角色表
CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `is_system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否系统角色：0-否 1-是',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0-禁用 1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否 1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`code`),
  KEY `idx_role_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 创建角色-权限关系表
CREATE TABLE IF NOT EXISTS `auth_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否 1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`),
  KEY `idx_role_permission_role_id` (`role_id`),
  KEY `idx_role_permission_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关系表';

-- 创建用户-角色关系表
CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否 1-是',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
  KEY `idx_user_role_user_id` (`user_id`),
  KEY `idx_user_role_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关系表'; 