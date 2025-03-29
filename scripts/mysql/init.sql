-- 律师事务所管理系统初始化脚本

-- 确保使用UTF8mb4字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `law_firm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE `law_firm`;

-- 系统用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `status` tinyint DEFAULT '1' COMMENT '状态(0停用,1正常)',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除(0否,1是)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 插入初始管理员账号 (密码: admin123)
INSERT INTO `sys_user` (`username`, `password`, `name`, `status`)
VALUES ('admin', '$2a$10$X7hbVQMMz.uHLOtiUXqJSOXZAjEYb3v0XiB..J5EjS3ZUxVqe55wG', '系统管理员', 1)
ON DUPLICATE KEY UPDATE `update_time` = CURRENT_TIMESTAMP;

-- 其他表根据需要添加...

SET FOREIGN_KEY_CHECKS = 1; 