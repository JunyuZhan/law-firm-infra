-- 系统模块初始化数据
-- 版本: V0002
-- 模块: API
-- 创建时间: 2023-06-02
-- 说明: 初始化系统基础数据，包括系统配置、字典等基础数据
-- 不包含依赖auth模块的数据，auth模块相关数据已移至V2002脚本

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 初始化存储桶
INSERT INTO storage_bucket (tenant_id, tenant_code, bucket_name, storage_type, endpoint, status, create_time, create_by, remark)
SELECT NULL, NULL, 'default', 'LOCAL', 'http://localhost:8080/api/files', 0, NOW(), 'system', '默认本地存储桶'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM storage_bucket WHERE bucket_name = 'default');

-- 初始化系统配置
INSERT INTO sys_config (tenant_id, tenant_code, config_key, config_value, group_name, status, create_time, create_by)
VALUES 
(NULL, NULL, 'sys.name', '律师事务所管理系统', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.version', '1.0.0', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.copyright', 'Copyright © 2023 Law Firm Management System', '系统设置', 0, NOW(), 'system'),
(NULL, NULL, 'sys.upload.allowed.ext', 'jpg,jpeg,png,gif,doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar', '上传设置', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化字典
INSERT INTO sys_dict (tenant_id, tenant_code, dict_name, dict_code, dict_type, status, create_time, create_by)
VALUES
(NULL, NULL, '用户性别', 'sys_user_gender', 'sys_user_gender', 0, NOW(), 'system'),
(NULL, NULL, '权限状态', 'sys_permission_status', 'sys_permission_status', 0, NOW(), 'system'),
(NULL, NULL, '系统开关', 'sys_normal_disable', 'sys_normal_disable', 0, NOW(), 'system'),
(NULL, NULL, '任务状态', 'sys_job_status', 'sys_job_status', 0, NOW(), 'system'),
(NULL, NULL, '系统是否', 'sys_yes_no', 'sys_yes_no', 0, NOW(), 'system'),
(NULL, NULL, '案件状态', 'case_status', 'case_status', 0, NOW(), 'system'),
(NULL, NULL, '案件类型', 'case_type', 'case_type', 0, NOW(), 'system'),
(NULL, NULL, '文档类型', 'doc_type', 'doc_type', 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化字典项
INSERT INTO sys_dict_item (tenant_id, tenant_code, dict_id, label, value, sort, status, create_time, create_by)
VALUES
-- 用户性别
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '男', '1', 1, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '女', '2', 2, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_user_gender'), '未知', '0', 3, 0, NOW(), 'system'),

-- 权限状态
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_permission_status'), '正常', '0', 1, 0, NOW(), 'system'),
(NULL, NULL, (SELECT id FROM sys_dict WHERE dict_code = 'sys_permission_status'), '禁用', '1', 2, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 