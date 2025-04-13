-- 初始化角色数据

-- 清空角色表
TRUNCATE TABLE `auth_role`;

-- 添加系统管理员角色
INSERT INTO `auth_role` (`id`, `code`, `name`, `description`, `is_system`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(1, 'ROLE_ADMIN', '系统管理员', '系统管理员角色，拥有所有权限', 1, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加律师角色
INSERT INTO `auth_role` (`id`, `code`, `name`, `description`, `is_system`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(2, 'ROLE_LAWYER', '律师', '律师角色，拥有律师相关业务权限', 1, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加合伙人角色
INSERT INTO `auth_role` (`id`, `code`, `name`, `description`, `is_system`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(3, 'ROLE_PARTNER', '合伙人', '合伙人角色，拥有合伙人相关业务权限', 1, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加行政人员角色
INSERT INTO `auth_role` (`id`, `code`, `name`, `description`, `is_system`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(4, 'ROLE_STAFF', '行政人员', '行政人员角色，拥有行政相关业务权限', 1, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加财务人员角色
INSERT INTO `auth_role` (`id`, `code`, `name`, `description`, `is_system`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(5, 'ROLE_FINANCE', '财务人员', '财务人员角色，拥有财务相关业务权限', 1, 1, 0, 0, NOW(), 1, NOW(), 1); 