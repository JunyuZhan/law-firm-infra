-- 权限数据初始化

-- 插入系统级权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('系统管理', 'system:manage', 0, 0, 0, 'SYSTEM', 0, 1, 1),
('用户管理', 'system:user:manage', 0, 0, 0, 'SYSTEM', 1, 1, 1),
('用户查询', 'system:user:query', 2, 1, 0, 'SYSTEM', 2, 1, 1),
('用户创建', 'system:user:create', 2, 3, 0, 'SYSTEM', 2, 2, 1),
('用户编辑', 'system:user:edit', 2, 4, 0, 'SYSTEM', 2, 3, 1),
('用户删除', 'system:user:delete', 2, 5, 0, 'SYSTEM', 2, 4, 1),
('角色管理', 'system:role:manage', 0, 0, 0, 'SYSTEM', 1, 2, 1),
('角色查询', 'system:role:query', 2, 1, 0, 'SYSTEM', 7, 1, 1),
('角色创建', 'system:role:create', 2, 3, 0, 'SYSTEM', 7, 2, 1),
('角色编辑', 'system:role:edit', 2, 4, 0, 'SYSTEM', 7, 3, 1),
('角色删除', 'system:role:delete', 2, 5, 0, 'SYSTEM', 7, 4, 1),
('权限管理', 'system:permission:manage', 0, 0, 0, 'SYSTEM', 1, 3, 1);

-- 客户管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('客户管理', 'client:manage', 0, 0, 0, 'CLIENT', 0, 2, 1),
('客户查询', 'client:query', 2, 1, 0, 'CLIENT', 13, 1, 1),
('客户创建', 'client:create', 2, 3, 0, 'CLIENT', 13, 2, 1),
('客户编辑', 'client:edit', 2, 4, 0, 'CLIENT', 13, 3, 1),
('客户删除', 'client:delete', 2, 5, 0, 'CLIENT', 13, 4, 1),
('客户导入', 'client:import', 2, 0, 0, 'CLIENT', 13, 5, 1),
('客户导出', 'client:export', 2, 0, 0, 'CLIENT', 13, 6, 1);

-- 案件管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('案件管理', 'case:manage', 0, 0, 0, 'CASE', 0, 3, 1),
('案件查询', 'case:query', 2, 1, 0, 'CASE', 20, 1, 1),
('案件创建', 'case:create', 2, 3, 0, 'CASE', 20, 2, 1),
('案件编辑', 'case:edit', 2, 4, 0, 'CASE', 20, 3, 1),
('案件删除', 'case:delete', 2, 5, 0, 'CASE', 20, 4, 1),
('案件归档', 'case:archive', 2, 0, 0, 'CASE', 20, 5, 1),
('案件团队管理', 'case:team:manage', 0, 0, 0, 'CASE', 20, 6, 1);

-- 合同管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('合同管理', 'contract:manage', 0, 0, 0, 'CONTRACT', 0, 4, 1),
('合同查询', 'contract:query', 2, 1, 0, 'CONTRACT', 27, 1, 1),
('合同创建', 'contract:create', 2, 3, 0, 'CONTRACT', 27, 2, 1),
('合同编辑', 'contract:edit', 2, 4, 0, 'CONTRACT', 27, 3, 1),
('合同删除', 'contract:delete', 2, 5, 0, 'CONTRACT', 27, 4, 1),
('合同审批', 'contract:approve', 2, 6, 0, 'CONTRACT', 27, 5, 1);

-- 文档管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('文档管理', 'document:manage', 0, 0, 0, 'DOCUMENT', 0, 5, 1),
('文档查询', 'document:query', 2, 1, 0, 'DOCUMENT', 33, 1, 1),
('文档上传', 'document:upload', 2, 3, 0, 'DOCUMENT', 33, 2, 1),
('文档编辑', 'document:edit', 2, 4, 0, 'DOCUMENT', 33, 3, 1),
('文档删除', 'document:delete', 2, 5, 0, 'DOCUMENT', 33, 4, 1),
('文档下载', 'document:download', 2, 0, 0, 'DOCUMENT', 33, 5, 1);

-- 财务管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('财务管理', 'finance:manage', 0, 0, 0, 'FINANCE', 0, 6, 1),
('财务查询', 'finance:query', 2, 1, 0, 'FINANCE', 39, 1, 1),
('财务录入', 'finance:entry', 2, 3, 0, 'FINANCE', 39, 2, 1),
('财务编辑', 'finance:edit', 2, 4, 0, 'FINANCE', 39, 3, 1),
('财务删除', 'finance:delete', 2, 5, 0, 'FINANCE', 39, 4, 1),
('财务报表', 'finance:report', 2, 0, 0, 'FINANCE', 39, 5, 1);

-- 知识库管理权限
INSERT INTO sys_permission (permission_name, permission_code, permission_type, operation_type, data_scope, module, parent_id, sort, status)
VALUES 
('知识库管理', 'knowledge:manage', 0, 0, 0, 'KNOWLEDGE', 0, 7, 1),
('知识查询', 'knowledge:query', 2, 1, 0, 'KNOWLEDGE', 45, 1, 1),
('知识创建', 'knowledge:create', 2, 3, 0, 'KNOWLEDGE', 45, 2, 1),
('知识编辑', 'knowledge:edit', 2, 4, 0, 'KNOWLEDGE', 45, 3, 1),
('知识删除', 'knowledge:delete', 2, 5, 0, 'KNOWLEDGE', 45, 4, 1),
('知识分享', 'knowledge:share', 2, 0, 0, 'KNOWLEDGE', 45, 5, 1);

-- 给超级管理员角色分配所有权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;

-- 给律所主任角色分配部分权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 2, id FROM sys_permission WHERE permission_code LIKE '%query%' OR permission_code LIKE '%manage%' OR permission_code IN ('system:user:create', 'system:user:edit', 'finance:report');

-- 给律师角色分配部分权限
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 4, id FROM sys_permission WHERE permission_code LIKE 'case:%' OR permission_code LIKE 'client:%' OR permission_code LIKE 'document:%' OR permission_code LIKE 'knowledge:query'; 