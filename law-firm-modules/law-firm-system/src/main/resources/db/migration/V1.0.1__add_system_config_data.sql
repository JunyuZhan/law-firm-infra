-- 初始化系统配置数据
INSERT INTO sys_config (config_name, config_key, config_value, config_type, is_system, remark, create_by) VALUES 
('系统名称', 'sys.name', '律师事务所管理系统', 'SYSTEM', 1, '系统名称配置', 'admin'),
('系统版本', 'sys.version', '1.0.0', 'SYSTEM', 1, '系统版本配置', 'admin'),
('系统Logo', 'sys.logo', '/static/logo.png', 'SYSTEM', 1, '系统Logo配置', 'admin'),
('系统首页', 'sys.index', '/dashboard', 'SYSTEM', 1, '系统首页配置', 'admin'),
('系统底部版权', 'sys.copyright', 'Copyright © 2023 Law Firm Management System', 'SYSTEM', 1, '系统底部版权配置', 'admin'),
('系统默认主题', 'sys.default.theme', 'light', 'SYSTEM', 1, '系统默认主题配置', 'admin'),
('系统默认语言', 'sys.default.language', 'zh_CN', 'SYSTEM', 1, '系统默认语言配置', 'admin'),
('系统默认分页大小', 'sys.default.page.size', '10', 'SYSTEM', 1, '系统默认分页大小配置', 'admin'),
('系统会话超时时间', 'sys.session.timeout', '1800', 'SYSTEM', 1, '系统会话超时时间配置（秒）', 'admin'),
('系统最大上传文件大小', 'sys.upload.max.size', '10', 'SYSTEM', 1, '系统最大上传文件大小配置（MB）', 'admin');

-- 初始化字典数据
INSERT INTO sys_dict (dict_name, dict_type, status, remark, create_by) VALUES 
('系统状态', 'sys_status', 0, '系统状态字典', 'admin'),
('配置类型', 'config_type', 0, '配置类型字典', 'admin'),
('是否标识', 'yes_no', 0, '是否标识字典', 'admin'),
('补丁类型', 'patch_type', 0, '补丁类型字典', 'admin'),
('升级状态', 'upgrade_status', 0, '升级状态字典', 'admin');

-- 初始化字典项数据
-- 系统状态字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'sys_status'), '正常', '0', 1, 0, '正常状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'sys_status'), '停用', '1', 2, 0, '停用状态', 'admin');

-- 配置类型字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'config_type'), '系统配置', 'SYSTEM', 1, 0, '系统配置', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'config_type'), '业务配置', 'BUSINESS', 2, 0, '业务配置', 'admin');

-- 是否标识字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'yes_no'), '是', '1', 1, 0, '是', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'yes_no'), '否', '0', 2, 0, '否', 'admin');

-- 补丁类型字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), 'SQL脚本', 'SQL', 1, 0, 'SQL脚本补丁', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), 'JAR包', 'JAR', 2, 0, 'JAR包补丁', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), '配置文件', 'CONFIG', 3, 0, '配置文件补丁', 'admin');

-- 升级状态字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '待升级', 'PENDING', 1, 0, '待升级状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '升级中', 'UPGRADING', 2, 0, '升级中状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '成功', 'SUCCESS', 3, 0, '升级成功状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '失败', 'FAILED', 4, 0, '升级失败状态', 'admin'); 