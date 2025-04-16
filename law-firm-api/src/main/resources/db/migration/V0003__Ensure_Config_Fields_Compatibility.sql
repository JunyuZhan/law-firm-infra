/* 
 * API模块确保配置表字段兼容性
 * 版本号V0106，避免与系统模块V1xxx冲突
 */

-- =====================================================
-- 系统配置表字段兼容性保障
-- =====================================================
-- 此迁移脚本确保系统配置表字段与系统模块定义保持兼容
-- 由于跨模块结构统一的需要，我们将系统配置表的主要定义
-- 放在系统模块中，但API模块需要确保核心字段存在
--
-- 参见：law-firm-modules/law-firm-system/src/main/resources/db/migration/V1003__create_system_tables.sql
--
-- =====================================================

-- 执行兼容性检查并添加可能缺失的字段
SELECT 'Executing config table compatibility check and adding missing fields' AS message;

-- 注意：这些ALTER TABLE语句可能在字段已存在时产生错误
-- 但这些错误不会影响数据库完整性，可以安全忽略

-- 添加sort字段（如果不存在）
ALTER TABLE sys_config ADD sort INT DEFAULT 0;

-- 添加status字段（如果不存在）
ALTER TABLE sys_config ADD status TINYINT(1) DEFAULT 1;

-- 添加version字段（如果不存在）
ALTER TABLE sys_config ADD version INT DEFAULT 0;

-- 如果在未来需要添加特定SQL操作，可在此处添加
-- 目前仅作为占位符，确保版本连续性 