-- 系统模块迁移脚本 V1007
-- 更新系统配置表结构，统一字段定义
-- 此脚本用于确保sys_config表的结构与API模块保持一致

-- 1. 添加缺失的字段
ALTER TABLE sys_config ADD COLUMN category VARCHAR(50) NULL;
ALTER TABLE sys_config ADD COLUMN is_system TINYINT NOT NULL DEFAULT 0;
ALTER TABLE sys_config ADD COLUMN status TINYINT NOT NULL DEFAULT 1;
ALTER TABLE sys_config ADD COLUMN sort INT DEFAULT 0;
ALTER TABLE sys_config ADD COLUMN version INT DEFAULT 0;
ALTER TABLE sys_config ADD COLUMN tenant_id BIGINT NOT NULL DEFAULT 0;

-- 2. 修改字段类型和长度
ALTER TABLE sys_config MODIFY config_key VARCHAR(50) NOT NULL COMMENT '配置键';
ALTER TABLE sys_config MODIFY config_value VARCHAR(500) NOT NULL COMMENT '配置值';
ALTER TABLE sys_config MODIFY config_name VARCHAR(100) NULL COMMENT '配置名称';
ALTER TABLE sys_config MODIFY create_by BIGINT NULL COMMENT '创建人';
ALTER TABLE sys_config MODIFY update_by BIGINT NULL COMMENT '更新人';

-- 3. 添加索引
CREATE INDEX idx_category ON sys_config(category);
CREATE INDEX idx_status ON sys_config(status);

-- 4. 修改唯一索引
DROP INDEX uk_config_key ON sys_config;
CREATE UNIQUE INDEX uk_config_key ON sys_config(config_key, tenant_id); 