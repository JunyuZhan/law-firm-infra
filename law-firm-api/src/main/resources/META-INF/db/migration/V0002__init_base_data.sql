-- API层基础数据初始化
-- 版本: V0002
-- 模块: API (系统基础层)
-- 创建时间: 2023-06-01
-- 说明: 初始化系统运行必需的基础数据
-- 包括：系统配置、数据字典、存储桶等基础数据

-- 设置字符集
SET NAMES utf8mb4;

-- ======================= 系统配置数据 =======================

-- 系统基础配置
INSERT INTO sys_config (config_key, config_value, config_type, group_name, description, is_system, sort, create_by) VALUES
('system.name', '律师事务所管理系统', 1, 'system', '系统名称', 1, 1, 'system'),
('system.version', '1.0.0', 1, 'system', '系统版本', 1, 2, 'system'),
('system.copyright', '© 2023 律师事务所管理系统', 1, 'system', '版权信息', 1, 3, 'system'),
('system.logo', '/static/images/logo.png', 1, 'system', '系统Logo', 1, 4, 'system'),
('system.favicon', '/static/images/favicon.ico', 1, 'system', '网站图标', 1, 5, 'system'),

-- 安全配置
('security.password.min.length', '8', 2, 'security', '密码最小长度', 1, 10, 'system'),
('security.password.max.length', '20', 2, 'security', '密码最大长度', 1, 11, 'system'),
('security.password.require.special', 'true', 3, 'security', '密码是否需要特殊字符', 1, 12, 'system'),
('security.login.max.attempts', '5', 2, 'security', '登录最大尝试次数', 1, 13, 'system'),
('security.session.timeout', '1800', 2, 'security', '会话超时时间(秒)', 1, 14, 'system'),

-- 文件上传配置
('upload.max.file.size', '104857600', 2, 'upload', '最大文件大小(字节)', 1, 20, 'system'),
('upload.allowed.extensions', '["jpg","jpeg","png","gif","pdf","doc","docx","xls","xlsx","ppt","pptx","txt","zip","rar"]', 4, 'upload', '允许的文件扩展名', 1, 21, 'system'),
('upload.image.max.width', '1920', 2, 'upload', '图片最大宽度', 1, 22, 'system'),
('upload.image.max.height', '1080', 2, 'upload', '图片最大高度', 1, 23, 'system'),

-- 邮件配置
('mail.smtp.host', 'smtp.qq.com', 1, 'mail', 'SMTP服务器', 1, 30, 'system'),
('mail.smtp.port', '587', 2, 'mail', 'SMTP端口', 1, 31, 'system'),
('mail.smtp.username', '', 1, 'mail', 'SMTP用户名', 1, 32, 'system'),
('mail.smtp.password', '', 1, 'mail', 'SMTP密码', 1, 33, 'system'),
('mail.smtp.ssl.enable', 'true', 3, 'mail', '是否启用SSL', 1, 34, 'system');

-- ======================= 数据字典类型 =======================

INSERT INTO sys_dict_type (dict_name, dict_type, description, is_system, sort, create_by) VALUES
('用户状态', 'user_status', '用户账号状态', 1, 1, 'system'),
('性别', 'gender', '性别类型', 1, 2, 'system'),
('学历', 'education', '教育程度', 1, 3, 'system'),
('职业类型', 'profession_type', '职业分类', 1, 4, 'system'),
('案件状态', 'case_status', '案件处理状态', 1, 5, 'system'),
('案件类型', 'case_type', '案件分类', 1, 6, 'system'),
('案件优先级', 'case_priority', '案件优先级别', 1, 7, 'system'),
('文档类型', 'document_type', '文档分类', 1, 8, 'system'),
('通知类型', 'notice_type', '通知分类', 1, 9, 'system'),
('操作类型', 'operation_type', '系统操作类型', 1, 10, 'system'),
('存储类型', 'storage_type', '文件存储类型', 1, 11, 'system'),
('登录类型', 'login_type', '登录方式', 1, 12, 'system'),
('设备类型', 'device_type', '设备分类', 1, 13, 'system'),
('数据状态', 'data_status', '数据记录状态', 1, 14, 'system'),
('审核状态', 'audit_status', '审核流程状态', 1, 15, 'system');

-- ======================= 数据字典数据 =======================

-- 用户状态
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, is_default, css_class, list_class, tag_type, create_by) VALUES
('user_status', '正常', '1', 1, 1, '', 'success', 'success', 'system'),
('user_status', '禁用', '0', 2, 0, '', 'danger', 'danger', 'system'),
('user_status', '锁定', '2', 3, 0, '', 'warning', 'warning', 'system'),

-- 性别
('gender', '男', '1', 1, 0, '', 'primary', 'primary', 'system'),
('gender', '女', '2', 2, 0, '', 'danger', 'danger', 'system'),
('gender', '未知', '0', 3, 1, '', 'info', 'info', 'system'),

-- 学历
('education', '小学', '1', 1, 0, '', '', '', 'system'),
('education', '初中', '2', 2, 0, '', '', '', 'system'),
('education', '高中', '3', 3, 0, '', '', '', 'system'),
('education', '中专', '4', 4, 0, '', '', '', 'system'),
('education', '大专', '5', 5, 0, '', '', '', 'system'),
('education', '本科', '6', 6, 1, '', '', '', 'system'),
('education', '硕士', '7', 7, 0, '', '', '', 'system'),
('education', '博士', '8', 8, 0, '', '', '', 'system'),

-- 职业类型
('profession_type', '律师', '1', 1, 1, '', 'primary', 'primary', 'system'),
('profession_type', '律师助理', '2', 2, 0, '', 'success', 'success', 'system'),
('profession_type', '实习律师', '3', 3, 0, '', 'warning', 'warning', 'system'),
('profession_type', '法务专员', '4', 4, 0, '', 'info', 'info', 'system'),
('profession_type', '行政人员', '5', 5, 0, '', 'secondary', 'secondary', 'system'),

-- 案件状态
('case_status', '待接收', '1', 1, 1, '', 'warning', 'warning', 'system'),
('case_status', '进行中', '2', 2, 0, '', 'primary', 'primary', 'system'),
('case_status', '已完成', '3', 3, 0, '', 'success', 'success', 'system'),
('case_status', '已关闭', '4', 4, 0, '', 'secondary', 'secondary', 'system'),
('case_status', '已暂停', '5', 5, 0, '', 'danger', 'danger', 'system'),

-- 案件类型
('case_type', '民事诉讼', '1', 1, 0, '', '', '', 'system'),
('case_type', '刑事辩护', '2', 2, 0, '', '', '', 'system'),
('case_type', '行政诉讼', '3', 3, 0, '', '', '', 'system'),
('case_type', '商事仲裁', '4', 4, 0, '', '', '', 'system'),
('case_type', '法律顾问', '5', 5, 1, '', '', '', 'system'),
('case_type', '合同纠纷', '6', 6, 0, '', '', '', 'system'),
('case_type', '劳动争议', '7', 7, 0, '', '', '', 'system'),
('case_type', '知识产权', '8', 8, 0, '', '', '', 'system'),
('case_type', '房产纠纷', '9', 9, 0, '', '', '', 'system'),
('case_type', '婚姻家庭', '10', 10, 0, '', '', '', 'system'),

-- 案件优先级
('case_priority', '低', '1', 1, 0, '', 'info', 'info', 'system'),
('case_priority', '中', '2', 2, 1, '', 'warning', 'warning', 'system'),
('case_priority', '高', '3', 3, 0, '', 'danger', 'danger', 'system'),
('case_priority', '紧急', '4', 4, 0, '', 'danger', 'danger', 'system'),

-- 文档类型
('document_type', '合同文件', '1', 1, 0, '', '', '', 'system'),
('document_type', '法律文书', '2', 2, 0, '', '', '', 'system'),
('document_type', '证据材料', '3', 3, 0, '', '', '', 'system'),
('document_type', '委托书', '4', 4, 0, '', '', '', 'system'),
('document_type', '判决书', '5', 5, 0, '', '', '', 'system'),
('document_type', '调解书', '6', 6, 0, '', '', '', 'system'),
('document_type', '其他文档', '99', 99, 1, '', '', '', 'system'),

-- 通知类型
('notice_type', '系统通知', '1', 1, 1, '', 'primary', 'primary', 'system'),
('notice_type', '案件提醒', '2', 2, 0, '', 'warning', 'warning', 'system'),
('notice_type', '任务提醒', '3', 3, 0, '', 'info', 'info', 'system'),
('notice_type', '审批通知', '4', 4, 0, '', 'success', 'success', 'system'),

-- 操作类型
('operation_type', '创建', 'CREATE', 1, 0, '', 'success', 'success', 'system'),
('operation_type', '更新', 'UPDATE', 2, 0, '', 'primary', 'primary', 'system'),
('operation_type', '删除', 'DELETE', 3, 0, '', 'danger', 'danger', 'system'),
('operation_type', '查询', 'QUERY', 4, 1, '', 'info', 'info', 'system'),
('operation_type', '导入', 'IMPORT', 5, 0, '', 'warning', 'warning', 'system'),
('operation_type', '导出', 'EXPORT', 6, 0, '', 'secondary', 'secondary', 'system'),

-- 存储类型
('storage_type', '本地存储', '1', 1, 1, '', '', '', 'system'),
('storage_type', 'MinIO', '2', 2, 0, '', '', '', 'system'),
('storage_type', '阿里云OSS', '3', 3, 0, '', '', '', 'system'),
('storage_type', '腾讯云COS', '4', 4, 0, '', '', '', 'system'),
('storage_type', 'AWS S3', '5', 5, 0, '', '', '', 'system'),

-- 登录类型
('login_type', '账号密码', '1', 1, 1, '', '', '', 'system'),
('login_type', '手机验证码', '2', 2, 0, '', '', '', 'system'),
('login_type', '第三方登录', '3', 3, 0, '', '', '', 'system'),

-- 设备类型
('device_type', '桌面端', 'desktop', 1, 1, '', '', '', 'system'),
('device_type', '移动端', 'mobile', 2, 0, '', '', '', 'system'),
('device_type', '平板端', 'tablet', 3, 0, '', '', '', 'system'),

-- 数据状态
('data_status', '正常', '1', 1, 1, '', 'success', 'success', 'system'),
('data_status', '禁用', '0', 2, 0, '', 'danger', 'danger', 'system'),

-- 审核状态
('audit_status', '待审核', '1', 1, 1, '', 'warning', 'warning', 'system'),
('audit_status', '审核通过', '2', 2, 0, '', 'success', 'success', 'system'),
('audit_status', '审核拒绝', '3', 3, 0, '', 'danger', 'danger', 'system');

-- ======================= 存储桶配置 =======================

-- 默认本地存储桶
INSERT INTO storage_bucket (bucket_name, storage_type, endpoint, is_default, is_public, max_file_size, allowed_extensions, sort, create_by, remark) VALUES
('default-local', 1, '/upload', 1, 0, 104857600, '["jpg","jpeg","png","gif","pdf","doc","docx","xls","xlsx","ppt","pptx","txt","zip","rar"]', 1, 'system', '默认本地存储桶'),
('images', 1, '/upload/images', 0, 1, 10485760, '["jpg","jpeg","png","gif","webp","svg"]', 2, 'system', '图片存储桶'),
('documents', 1, '/upload/documents', 0, 0, 52428800, '["pdf","doc","docx","xls","xlsx","ppt","pptx","txt"]', 3, 'system', '文档存储桶'),
('temp', 1, '/upload/temp', 0, 0, 104857600, '["jpg","jpeg","png","gif","pdf","doc","docx","xls","xlsx","ppt","pptx","txt","zip","rar"]', 4, 'system', '临时文件存储桶'); 