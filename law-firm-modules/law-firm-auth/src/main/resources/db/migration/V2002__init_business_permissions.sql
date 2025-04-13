-- 初始化业务权限数据

-- 清空权限表
TRUNCATE TABLE `auth_permission`;

-- 添加系统管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(1, 'system', '系统管理', 1, NULL, 'system', '/system', 'Layout', '/system/user', 'system', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(2, 'system:user', '用户管理', 1, 1, 'system', 'user', 'system/user/index', NULL, 'user', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(3, 'system:role', '角色管理', 1, 1, 'system', 'role', 'system/role/index', NULL, 'role', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(4, 'system:permission', '权限管理', 1, 1, 'system', 'permission', 'system/permission/index', NULL, 'permission', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(5, 'system:dict', '字典管理', 1, 1, 'system', 'dict', 'system/dict/index', NULL, 'dict', 4, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(6, 'system:config', '系统配置', 1, 1, 'system', 'config', 'system/config/index', NULL, 'config', 5, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(7, 'system:log', '系统日志', 1, 1, 'system', 'log', 'system/log/index', NULL, 'log', 6, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加组织机构模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(100, 'organization', '组织机构', 1, NULL, 'organization', '/organization', 'Layout', '/organization/department', 'tree', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(101, 'organization:department', '部门管理', 1, 100, 'organization', 'department', 'organization/department/index', NULL, 'component', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(102, 'organization:position', '职位管理', 1, 100, 'organization', 'position', 'organization/position/index', NULL, 'post', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(103, 'organization:team', '团队管理', 1, 100, 'organization', 'team', 'organization/team/index', NULL, 'peoples', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加人事管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(200, 'personnel', '人事管理', 1, NULL, 'personnel', '/personnel', 'Layout', '/personnel/employee', 'user', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(201, 'personnel:employee', '员工管理', 1, 200, 'personnel', 'employee', 'personnel/employee/index', NULL, 'peoples', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(202, 'personnel:lawyer', '律师管理', 1, 200, 'personnel', 'lawyer', 'personnel/lawyer/index', NULL, 'people', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(203, 'personnel:staff', '行政管理', 1, 200, 'personnel', 'staff', 'personnel/staff/index', NULL, 'user', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加客户管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(300, 'client', '客户管理', 1, NULL, 'client', '/client', 'Layout', '/client/index', 'client', 4, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(301, 'client:info', '客户信息', 1, 300, 'client', 'info', 'client/info/index', NULL, 'user', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(302, 'client:contact', '联系人管理', 1, 300, 'client', 'contact', 'client/contact/index', NULL, 'phone', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(303, 'client:follow', '客户跟进', 1, 300, 'client', 'follow', 'client/follow/index', NULL, 'guide', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加案件管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(400, 'case', '案件管理', 1, NULL, 'case', '/case', 'Layout', '/case/index', 'education', 5, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(401, 'case:info', '案件信息', 1, 400, 'case', 'info', 'case/info/index', NULL, 'documentation', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(402, 'case:type', '案件类型', 1, 400, 'case', 'type', 'case/type/index', NULL, 'tree-table', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(403, 'case:archive', '案件归档', 1, 400, 'case', 'archive', 'case/archive/index', NULL, 'zip', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加合同管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(500, 'contract', '合同管理', 1, NULL, 'contract', '/contract', 'Layout', '/contract/index', 'form', 6, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(501, 'contract:info', '合同信息', 1, 500, 'contract', 'info', 'contract/info/index', NULL, 'documentation', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(502, 'contract:template', '合同模板', 1, 500, 'contract', 'template', 'contract/template/index', NULL, 'education', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(503, 'contract:approval', '合同审批', 1, 500, 'contract', 'approval', 'contract/approval/index', NULL, 'skill', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1);

-- 添加财务管理模块权限
INSERT INTO `auth_permission` (`id`, `code`, `name`, `type`, `parent_id`, `module`, `path`, `component`, `redirect`, `icon`, `sort`, `hidden`, `status`, `deleted`, `tenant_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
(600, 'finance', '财务管理', 1, NULL, 'finance', '/finance', 'Layout', '/finance/invoice', 'money', 7, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(601, 'finance:invoice', '发票管理', 1, 600, 'finance', 'invoice', 'finance/invoice/index', NULL, 'documentation', 1, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(602, 'finance:expense', '费用报销', 1, 600, 'finance', 'expense', 'finance/expense/index', NULL, 'money', 2, 0, 1, 0, 0, NOW(), 1, NOW(), 1),
(603, 'finance:salary', '薪酬管理', 1, 600, 'finance', 'salary', 'finance/salary/index', NULL, 'education', 3, 0, 1, 0, 0, NOW(), 1, NOW(), 1); 