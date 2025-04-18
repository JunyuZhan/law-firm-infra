-- System模块升级管理表结构初始化
-- 版本: V1003
-- 模块: system/upgrade
-- 创建时间: 2023-06-11
-- 说明: 创建系统升级和补丁管理相关表，基于实体类定义

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- sys_upgrade表（系统升级表）
CREATE TABLE IF NOT EXISTS sys_upgrade (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  upgrade_version VARCHAR(50) NOT NULL COMMENT '升级版本号',
  title VARCHAR(100) NOT NULL COMMENT '升级标题',
  description TEXT COMMENT '升级描述',
  upgrade_type VARCHAR(20) NOT NULL COMMENT '升级类型：PATCH-补丁升级，MINOR-小版本升级，MAJOR-大版本升级',
  upgrade_status VARCHAR(20) NOT NULL COMMENT '升级状态：PENDING-待升级，UPGRADING-升级中，SUCCESS-升级成功，FAILED-升级失败，ROLLBACK-已回滚',
  upgrade_time BIGINT COMMENT '升级时间',
  rollback_time BIGINT COMMENT '回滚时间',
  remark VARCHAR(255) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  UNIQUE KEY uk_upgrade_version (upgrade_version),
  KEY idx_upgrade_status (upgrade_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统升级表';

-- sys_patch表（系统补丁表）
CREATE TABLE IF NOT EXISTS sys_patch (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  tenant_id BIGINT COMMENT '租户ID',
  tenant_code VARCHAR(50) COMMENT '租户编码',
  upgrade_id BIGINT NOT NULL COMMENT '所属升级ID',
  file_path VARCHAR(255) NOT NULL COMMENT '补丁文件路径',
  description TEXT COMMENT '补丁描述',
  patch_type VARCHAR(20) NOT NULL COMMENT '补丁类型：SQL-SQL补丁，SCRIPT-脚本补丁，FILE-文件补丁',
  execute_order INTEGER DEFAULT 0 COMMENT '执行顺序',
  patch_status VARCHAR(20) NOT NULL COMMENT '补丁状态：PENDING-待执行，EXECUTING-执行中，SUCCESS-执行成功，FAILED-执行失败，ROLLBACK-已回滚',
  execute_time BIGINT COMMENT '执行时间',
  rollback_time BIGINT COMMENT '回滚时间',
  remark VARCHAR(255) COMMENT '备注',
  version INTEGER DEFAULT 0 COMMENT '版本号',
  status INTEGER DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
  sort INTEGER DEFAULT 0 COMMENT '排序号',
  deleted INTEGER DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_by VARCHAR(50) COMMENT '创建人',
  update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  update_by VARCHAR(50) COMMENT '更新人',
  PRIMARY KEY (id),
  KEY idx_upgrade_id (upgrade_id),
  KEY idx_patch_status (patch_status),
  KEY idx_execute_order (execute_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统补丁表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 