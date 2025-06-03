-- System模块监控数据初始化
-- 版本: V1002
-- 模块: system/monitor
-- 创建时间: 2023-06-10
-- 说明: 初始化系统监控相关数据，包括监控配置和字典项
-- 注意: 系统监控权限相关操作已移至V2003脚本

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 添加监控相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
SELECT NULL, NULL, '监控类型', 'sys_monitor_type', 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_type')
UNION ALL
SELECT NULL, NULL, '告警级别', 'sys_alert_level', 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_type')
UNION ALL
SELECT NULL, NULL, '告警状态', 'sys_alert_status', 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_type')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加监控类型字典项
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
SELECT NULL, NULL, 'sys_monitor_type', 'CPU使用率', 'CPU_USAGE', 1, 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_monitor_type', '内存使用率', 'MEMORY_USAGE', 2, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_monitor_type', '磁盘使用率', 'DISK_USAGE', 3, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_monitor_type', '数据库连接数', 'DB_CONNECTIONS', 4, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_monitor_type', 'JVM内存使用', 'JVM_MEMORY', 5, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_monitor_type', '网络流量', 'NETWORK_TRAFFIC', 6, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
-- 告警级别
SELECT NULL, NULL, 'sys_alert_level', '信息', 'INFO', 1, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_alert_level', '警告', 'WARNING', 2, 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_alert_level', '错误', 'ERROR', 3, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_alert_level', '致命', 'FATAL', 4, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
-- 告警状态
SELECT NULL, NULL, 'sys_alert_status', '待处理', 'PENDING', 1, 0, 1, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_alert_status', '处理中', 'HANDLING', 2, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
UNION ALL
SELECT NULL, NULL, 'sys_alert_status', '已关闭', 'CLOSED', 3, 0, 0, NOW(), 'system'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_dict_data')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化监控配置（默认配置）
INSERT INTO sys_monitor_config (tenant_id, tenant_code, type, name, threshold, interval_seconds, enabled, description, status, create_time, create_by, remark)
SELECT NULL, NULL, 'CPU_USAGE', 'CPU使用率告警阈值', 80.0, 60, TRUE, 'CPU使用率超过80%触发告警', 0, NOW(), 'system', '默认CPU监控配置'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_monitor_config')
UNION ALL
SELECT NULL, NULL, 'MEMORY_USAGE', '内存使用率告警阈值', 85.0, 60, TRUE, '内存使用率超过85%触发告警', 0, NOW(), 'system', '默认内存监控配置'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_monitor_config')
UNION ALL
SELECT NULL, NULL, 'DISK_USAGE', '磁盘使用率告警阈值', 90.0, 300, TRUE, '磁盘使用率超过90%触发告警', 0, NOW(), 'system', '默认磁盘监控配置'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_monitor_config')
UNION ALL
SELECT NULL, NULL, 'DB_CONNECTIONS', '数据库连接使用率告警阈值', 80.0, 300, TRUE, '数据库连接使用率超过80%触发告警', 0, NOW(), 'system', '默认数据库连接监控配置'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_monitor_config')
UNION ALL
SELECT NULL, NULL, 'JVM_MEMORY', 'JVM内存使用率告警阈值', 85.0, 60, TRUE, 'JVM内存使用率超过85%触发告警', 0, NOW(), 'system', '默认JVM内存监控配置'
FROM DUAL
WHERE EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'sys_monitor_config')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 