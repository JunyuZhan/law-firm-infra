-- 用户表
CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `account_expire_time` datetime DEFAULT NULL COMMENT '账号过期时间',
  `password_expire_time` datetime DEFAULT NULL COMMENT '密码过期时间',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '关联员工ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 权限表
CREATE TABLE IF NOT EXISTS `auth_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `code` varchar(50) NOT NULL COMMENT '权限编码',
  `type` varchar(20) NOT NULL COMMENT '权限类型(menu-菜单,button-按钮,api-接口)',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(0-禁用,1-启用)',
  `visible` tinyint(1) DEFAULT 1 COMMENT '是否可见(0-隐藏,1-显示)',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `version` int(11) DEFAULT 0 COMMENT '版本号',
  `sort` int(11) DEFAULT 0 COMMENT '排序',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 角色权限关联表
CREATE TABLE IF NOT EXISTS `auth_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `tenant_code` varchar(50) DEFAULT NULL COMMENT '租户编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 认证令牌表
CREATE TABLE IF NOT EXISTS auth_token (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  access_token VARCHAR(255) NOT NULL,
  refresh_token VARCHAR(255),
  token_type VARCHAR(20) DEFAULT 'Bearer',
  expires_in BIGINT NOT NULL,
  scope VARCHAR(100),
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='认证令牌表'; 