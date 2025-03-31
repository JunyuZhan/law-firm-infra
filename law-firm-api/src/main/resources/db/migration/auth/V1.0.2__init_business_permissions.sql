-- 业务模块权限初始化

-- 1. 仪表盘模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(100, '仪表盘', 'dashboard', 'menu', 0, '/dashboard', 'dashboard/index', 'dashboard', 1, 1, 0, NOW(), NOW(), 0),
(101, '工作台', 'dashboard:workbench', 'menu', 100, '/dashboard/workbench', 'dashboard/workbench/index', 'dashboard:workbench', 1, 1, 1, NOW(), NOW(), 0),
(102, '数据统计', 'dashboard:analysis', 'menu', 100, '/dashboard/analysis', 'dashboard/analysis/index', 'dashboard:analysis', 1, 1, 2, NOW(), NOW(), 0);

-- 2. 案件管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(200, '案件管理', 'case', 'menu', 0, '/case', 'Layout', 'case', 1, 1, 2, NOW(), NOW(), 0),
(201, '案件列表', 'case:list', 'menu', 200, '/case/list', 'case/list/index', 'case:list', 1, 1, 1, NOW(), NOW(), 0),
(202, '案件详情', 'case:detail', 'menu', 200, '/case/detail/:id', 'case/detail/index', 'case:detail', 1, 0, 2, NOW(), NOW(), 0),
(203, '案件创建', 'case:create', 'button', 201, '', '', 'case:create', 1, 1, 1, NOW(), NOW(), 0),
(204, '案件编辑', 'case:update', 'button', 201, '', '', 'case:update', 1, 1, 2, NOW(), NOW(), 0),
(205, '案件删除', 'case:delete', 'button', 201, '', '', 'case:delete', 1, 1, 3, NOW(), NOW(), 0),
(206, '案件审批', 'case:approve', 'button', 201, '', '', 'case:approve', 1, 1, 4, NOW(), NOW(), 0);

-- 3. 客户管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(300, '客户管理', 'client', 'menu', 0, '/client', 'Layout', 'client', 1, 1, 3, NOW(), NOW(), 0),
(301, '客户列表', 'client:list', 'menu', 300, '/client/list', 'client/list/index', 'client:list', 1, 1, 1, NOW(), NOW(), 0),
(302, '客户详情', 'client:detail', 'menu', 300, '/client/detail/:id', 'client/detail/index', 'client:detail', 1, 0, 2, NOW(), NOW(), 0),
(303, '客户创建', 'client:create', 'button', 301, '', '', 'client:create', 1, 1, 1, NOW(), NOW(), 0),
(304, '客户编辑', 'client:update', 'button', 301, '', '', 'client:update', 1, 1, 2, NOW(), NOW(), 0),
(305, '客户删除', 'client:delete', 'button', 301, '', '', 'client:delete', 1, 1, 3, NOW(), NOW(), 0);

-- 4. 合同管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(400, '合同管理', 'contract', 'menu', 0, '/contract', 'Layout', 'contract', 1, 1, 4, NOW(), NOW(), 0),
(401, '合同列表', 'contract:list', 'menu', 400, '/contract/list', 'contract/list/index', 'contract:list', 1, 1, 1, NOW(), NOW(), 0),
(402, '合同详情', 'contract:detail', 'menu', 400, '/contract/detail/:id', 'contract/detail/index', 'contract:detail', 1, 0, 2, NOW(), NOW(), 0),
(403, '合同创建', 'contract:create', 'button', 401, '', '', 'contract:create', 1, 1, 1, NOW(), NOW(), 0),
(404, '合同编辑', 'contract:update', 'button', 401, '', '', 'contract:update', 1, 1, 2, NOW(), NOW(), 0),
(405, '合同删除', 'contract:delete', 'button', 401, '', '', 'contract:delete', 1, 1, 3, NOW(), NOW(), 0),
(406, '合同审批', 'contract:approve', 'button', 401, '', '', 'contract:approve', 1, 1, 4, NOW(), NOW(), 0);

-- 5. 文档管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(500, '文档管理', 'document', 'menu', 0, '/document', 'Layout', 'document', 1, 1, 5, NOW(), NOW(), 0),
(501, '文档列表', 'document:list', 'menu', 500, '/document/list', 'document/list/index', 'document:list', 1, 1, 1, NOW(), NOW(), 0),
(502, '文档详情', 'document:detail', 'menu', 500, '/document/detail/:id', 'document/detail/index', 'document:detail', 1, 0, 2, NOW(), NOW(), 0),
(503, '文档上传', 'document:upload', 'button', 501, '', '', 'document:upload', 1, 1, 1, NOW(), NOW(), 0),
(504, '文档编辑', 'document:update', 'button', 501, '', '', 'document:update', 1, 1, 2, NOW(), NOW(), 0),
(505, '文档删除', 'document:delete', 'button', 501, '', '', 'document:delete', 1, 1, 3, NOW(), NOW(), 0),
(506, '文档下载', 'document:download', 'button', 501, '', '', 'document:download', 1, 1, 4, NOW(), NOW(), 0);

-- 6. 财务管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(600, '财务管理', 'finance', 'menu', 0, '/finance', 'Layout', 'finance', 1, 1, 6, NOW(), NOW(), 0),
(601, '收入管理', 'finance:income', 'menu', 600, '/finance/income', 'finance/income/index', 'finance:income', 1, 1, 1, NOW(), NOW(), 0),
(602, '支出管理', 'finance:expense', 'menu', 600, '/finance/expense', 'finance/expense/index', 'finance:expense', 1, 1, 2, NOW(), NOW(), 0),
(603, '账单管理', 'finance:bill', 'menu', 600, '/finance/bill', 'finance/bill/index', 'finance:bill', 1, 1, 3, NOW(), NOW(), 0),
(604, '发票管理', 'finance:invoice', 'menu', 600, '/finance/invoice', 'finance/invoice/index', 'finance:invoice', 1, 1, 4, NOW(), NOW(), 0),
(605, '报销管理', 'finance:reimburse', 'menu', 600, '/finance/reimburse', 'finance/reimburse/index', 'finance:reimburse', 1, 1, 5, NOW(), NOW(), 0);

-- 7. 行政管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(700, '行政管理', 'admin', 'menu', 0, '/admin', 'Layout', 'admin', 1, 1, 7, NOW(), NOW(), 0),
(701, '办公用品', 'admin:supplies', 'menu', 700, '/admin/supplies', 'admin/supplies/index', 'admin:supplies', 1, 1, 1, NOW(), NOW(), 0),
(702, '会议管理', 'admin:meeting', 'menu', 700, '/admin/meeting', 'admin/meeting/index', 'admin:meeting', 1, 1, 2, NOW(), NOW(), 0),
(703, '资产管理', 'admin:asset', 'menu', 700, '/admin/asset', 'admin/asset/index', 'admin:asset', 1, 1, 3, NOW(), NOW(), 0),
(704, '印章管理', 'admin:seal', 'menu', 700, '/admin/seal', 'admin/seal/index', 'admin:seal', 1, 1, 4, NOW(), NOW(), 0);

-- 8. 知识库管理模块
INSERT INTO `auth_permission` (`id`, `name`, `code`, `type`, `parent_id`, `path`, `component`, `permission`, `status`, `visible`, `sort`, `create_time`, `update_time`, `deleted`)
VALUES 
(800, '知识库管理', 'knowledge', 'menu', 0, '/knowledge', 'Layout', 'knowledge', 1, 1, 8, NOW(), NOW(), 0),
(801, '知识列表', 'knowledge:list', 'menu', 800, '/knowledge/list', 'knowledge/list/index', 'knowledge:list', 1, 1, 1, NOW(), NOW(), 0),
(802, '知识详情', 'knowledge:detail', 'menu', 800, '/knowledge/detail/:id', 'knowledge/detail/index', 'knowledge:detail', 1, 0, 2, NOW(), NOW(), 0),
(803, '知识创建', 'knowledge:create', 'button', 801, '', '', 'knowledge:create', 1, 1, 1, NOW(), NOW(), 0),
(804, '知识编辑', 'knowledge:update', 'button', 801, '', '', 'knowledge:update', 1, 1, 2, NOW(), NOW(), 0),
(805, '知识删除', 'knowledge:delete', 'button', 801, '', '', 'knowledge:delete', 1, 1, 3, NOW(), NOW(), 0);

-- 根据前端权限设计文档，为不同角色分配权限
-- 系统管理员权限（拥有所有权限）
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
SELECT 1, id, NOW() FROM `auth_permission` WHERE id >= 100
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 律所主任权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
SELECT 2, id, NOW() FROM `auth_permission` 
WHERE id BETWEEN 100 AND 899 AND id NOT IN (205) -- 排除案件删除等高风险操作
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 合伙人权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
-- 仪表盘权限（团队级别）
(3, 100, NOW()), (3, 101, NOW()), (3, 102, NOW()),
-- 案件管理权限（团队级别）
(3, 200, NOW()), (3, 201, NOW()), (3, 202, NOW()), 
(3, 203, NOW()), (3, 204, NOW()),
-- 客户管理权限（团队级别）
(3, 300, NOW()), (3, 301, NOW()), (3, 302, NOW()),
(3, 303, NOW()), (3, 304, NOW()),
-- 合同管理权限（团队级别）
(3, 400, NOW()), (3, 401, NOW()), (3, 402, NOW()),
(3, 403, NOW()), (3, 404, NOW()),
-- 文档管理权限（团队级别）
(3, 500, NOW()), (3, 501, NOW()), (3, 502, NOW()),
(3, 503, NOW()), (3, 504, NOW()), (3, 505, NOW()), (3, 506, NOW()),
-- 知识库管理权限（团队级别）
(3, 800, NOW()), (3, 801, NOW()), (3, 802, NOW()),
(3, 803, NOW()), (3, 804, NOW()), (3, 805, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 执业律师权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
-- 仪表盘权限（个人级别）
(4, 100, NOW()), (4, 101, NOW()),
-- 案件管理权限（个人+团队创建）
(4, 200, NOW()), (4, 201, NOW()), (4, 202, NOW()), (4, 203, NOW()),
-- 客户管理权限（个人+团队创建）
(4, 300, NOW()), (4, 301, NOW()), (4, 302, NOW()), (4, 303, NOW()),
-- 合同管理权限（个人级别）
(4, 400, NOW()), (4, 401, NOW()), (4, 402, NOW()),
-- 文档管理权限（个人+团队创建）
(4, 500, NOW()), (4, 501, NOW()), (4, 502, NOW()), (4, 503, NOW()), (4, 506, NOW()),
-- 知识库管理权限（个人+团队创建）
(4, 800, NOW()), (4, 801, NOW()), (4, 802, NOW()), (4, 803, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 实习律师权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
-- 仪表盘权限（个人级别）
(5, 100, NOW()), (5, 101, NOW()),
-- 案件管理权限（个人/只读）
(5, 200, NOW()), (5, 201, NOW()), (5, 202, NOW()),
-- 客户管理权限（个人/只读）
(5, 300, NOW()), (5, 301, NOW()), (5, 302, NOW()),
-- 合同管理权限（个人/只读）
(5, 400, NOW()), (5, 401, NOW()), (5, 402, NOW()),
-- 文档管理权限（个人/只读）
(5, 500, NOW()), (5, 501, NOW()), (5, 502, NOW()), (5, 506, NOW()),
-- 知识库管理权限（个人/只读）
(5, 800, NOW()), (5, 801, NOW()), (5, 802, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW();

-- 行政/财务人员权限
INSERT INTO `auth_role_permission` (`role_id`, `permission_id`, `create_time`)
VALUES
-- 仪表盘权限（个人级别）
(6, 100, NOW()), (6, 101, NOW()),
-- 合同管理权限（部门相关/只读）
(6, 400, NOW()), (6, 401, NOW()), (6, 402, NOW()),
-- 文档管理权限（部门相关）
(6, 500, NOW()), (6, 501, NOW()), (6, 502, NOW()), (6, 503, NOW()), (6, 506, NOW()),
-- 财务管理权限（部门全权）
(6, 600, NOW()), (6, 601, NOW()), (6, 602, NOW()), (6, 603, NOW()), (6, 604, NOW()), (6, 605, NOW()),
-- 行政管理权限（部门全权）
(6, 700, NOW()), (6, 701, NOW()), (6, 702, NOW()), (6, 703, NOW()), (6, 704, NOW())
ON DUPLICATE KEY UPDATE `create_time` = NOW(); 