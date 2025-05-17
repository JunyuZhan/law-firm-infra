-- Personnel模块初始数据
-- 版本: V3002
-- 模块: personnel
-- 创建时间: 2023-07-01
-- 说明: 初始化人事模块相关基础数据

-- 人事模块相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '人员类型', 'per_person_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '员工类型', 'per_employee_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '员工状态', 'per_employee_status', 0, 1, NOW(), 'system'),
(NULL, NULL, '律师职级', 'per_lawyer_level', 0, 1, NOW(), 'system'),
(NULL, NULL, '合同类型', 'per_contract_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '合同状态', 'per_contract_status', 0, 1, NOW(), 'system'),
(NULL, NULL, '职能类型', 'per_function_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '学历', 'per_education', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 人员类型字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 人员类型
(NULL, NULL, 'per_person_type', '律师', 'LAWYER', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_person_type', '行政人员', 'STAFF', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_person_type', '客户', 'CLIENT', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_person_type', '合作伙伴', 'PARTNER', 4, 0, 0, NOW(), 'system'),

-- 员工类型
(NULL, NULL, 'per_employee_type', '律师', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_employee_type', '行政人员', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_type', '实习生', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_type', '顾问', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_type', '其他', '99', 5, 0, 0, NOW(), 'system'),

-- 员工状态
(NULL, NULL, 'per_employee_status', '在职', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_employee_status', '离职', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_status', '试用期', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_status', '休假', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_employee_status', '停职', '5', 5, 0, 0, NOW(), 'system'),

-- 律师职级
(NULL, NULL, 'per_lawyer_level', '实习律师', '1', 1, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_lawyer_level', '初级律师', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_lawyer_level', '中级律师', '3', 3, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_lawyer_level', '高级律师', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_lawyer_level', '资深律师', '5', 5, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_lawyer_level', '合伙人', '6', 6, 0, 0, NOW(), 'system'),

-- 合同类型
(NULL, NULL, 'per_contract_type', '劳动合同', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_contract_type', '服务合同', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_contract_type', '保密协议', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_contract_type', '竞业限制协议', '4', 4, 0, 0, NOW(), 'system'),

-- 合同状态
(NULL, NULL, 'per_contract_status', '未签', '0', 1, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_contract_status', '已签', '1', 2, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_contract_status', '过期', '2', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_contract_status', '终止', '3', 4, 0, 0, NOW(), 'system'),

-- 职能类型
(NULL, NULL, 'per_function_type', '行政', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_function_type', '财务', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_function_type', '人事', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_function_type', '市场', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_function_type', 'IT', '5', 5, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_function_type', '前台', '6', 6, 0, 0, NOW(), 'system'),

-- 学历
(NULL, NULL, 'per_education', '高中', '1', 1, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_education', '专科', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_education', '本科', '3', 3, 0, 1, NOW(), 'system'),
(NULL, NULL, 'per_education', '硕士', '4', 4, 0, 0, NOW(), 'system'),
(NULL, NULL, 'per_education', '博士', '5', 5, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加人事管理相关菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '人事管理', 'personnel', 0, 0, '/personnel', 'team', NULL, 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel');

-- 员工管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '员工管理', 'personnel:employee', 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'employee', 'personnel/employee/index', 'user', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:employee');

-- 律师管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '律师管理', 'personnel:lawyer', 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'lawyer', 'personnel/lawyer/index', 'user-solid', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:lawyer');

-- 合同管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '合同管理', 'personnel:contract', 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'contract', 'personnel/contract/index', 'document', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:contract');

-- 为管理员角色分配权限
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'personnel%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );

-- organization模块相关字典类型
INSERT INTO sys_dict_type (tenant_id, tenant_code, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(NULL, NULL, '部门类型', 'org_department_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '团队类型', 'org_team_type', 0, 1, NOW(), 'system'),
(NULL, NULL, '职位类型', 'org_position_type', 0, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 部门类型字典数据
INSERT INTO sys_dict_data (tenant_id, tenant_code, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 部门类型
(NULL, NULL, 'org_department_type', '业务部门', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'org_department_type', '职能部门', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'org_department_type', '管理部门', '3', 3, 0, 0, NOW(), 'system'),

-- 团队类型
(NULL, NULL, 'org_team_type', '业务团队', '1', 1, 0, 1, NOW(), 'system'),
(NULL, NULL, 'org_team_type', '项目团队', '2', 2, 0, 0, NOW(), 'system'),
(NULL, NULL, 'org_team_type', '特殊团队', '3', 3, 0, 0, NOW(), 'system'),

-- 职位类型
(NULL, NULL, 'org_position_type', '管理岗', '1', 1, 0, 0, NOW(), 'system'),
(NULL, NULL, 'org_position_type', '业务岗', '2', 2, 0, 1, NOW(), 'system'),
(NULL, NULL, 'org_position_type', '职能岗', '3', 3, 0, 0, NOW(), 'system'),
(NULL, NULL, 'org_position_type', '实习岗', '4', 4, 0, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化组织架构菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, icon, component, sort, status, create_time, create_by)
SELECT NULL, NULL, '组织架构', 'organization', 0, 0, '/organization', 'office-building', NULL, 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization');

-- 部门管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '部门管理', 'organization:department', 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'department', 'personnel/organization/department/index', 'department', 1, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:department');

-- 团队管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '团队管理', 'organization:team', 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'team', 'personnel/organization/team/index', 'user-group', 2, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:team');

-- 职位管理菜单
INSERT INTO auth_permission (tenant_id, tenant_code, name, code, type, parent_id, path, component, icon, sort, status, create_time, create_by)
SELECT NULL, NULL, '职位管理', 'organization:position', 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'position', 'personnel/organization/position/index', 'position', 3, 0, NOW(), 'system'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:position');

-- 为管理员角色分配组织架构权限
INSERT INTO auth_role_permission (tenant_id, tenant_code, role_id, permission_id, status, create_time, create_by)
SELECT NULL, NULL, 1, permission.id, 0, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'organization%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );
