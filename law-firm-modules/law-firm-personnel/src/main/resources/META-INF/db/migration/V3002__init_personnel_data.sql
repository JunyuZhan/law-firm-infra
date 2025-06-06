-- 版本: V3002
-- 模块: 人员管理模块 (V3000-V3999)
-- 创建时间: 2023-06-01
-- 说明: 人员管理模块初始数据

-- Personnel模块初始数据
-- 版本: V3002
-- 模块: personnel
-- 创建时间: 2023-07-01
-- 说明: 初始化人事模块相关基础数据

-- 人事模块相关字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, description, is_system, sort, create_by)
VALUES
('人员类型', 'per_person_type', '人员类型字典', 1, 1, 'system'),
('员工类型', 'per_employee_type', '员工类型字典', 1, 2, 'system'),
('员工状态', 'per_employee_status', '员工状态字典', 1, 3, 'system'),
('律师职级', 'per_lawyer_level', '律师职级字典', 1, 4, 'system'),
('合同类型', 'per_contract_type', '合同类型字典', 1, 5, 'system'),
('合同状态', 'per_contract_status', '合同状态字典', 1, 6, 'system'),
('职能类型', 'per_function_type', '职能类型字典', 1, 7, 'system'),
('学历', 'per_education', '学历字典', 1, 8, 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 人员类型字典数据
INSERT INTO sys_dict_data (tenant_id, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 人员类型
(0, 'per_person_type', '律师', 'LAWYER', 1, 1, 1, NOW(), 'system'),
(0, 'per_person_type', '行政人员', 'STAFF', 2, 1, 0, NOW(), 'system'),
(0, 'per_person_type', '客户', 'CLIENT', 3, 1, 0, NOW(), 'system'),
(0, 'per_person_type', '合作伙伴', 'PARTNER', 4, 1, 0, NOW(), 'system'),

-- 员工类型
(0, 'per_employee_type', '律师', '1', 1, 1, 1, NOW(), 'system'),
(0, 'per_employee_type', '行政人员', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_employee_type', '实习生', '3', 3, 1, 0, NOW(), 'system'),
(0, 'per_employee_type', '顾问', '4', 4, 1, 0, NOW(), 'system'),
(0, 'per_employee_type', '其他', '99', 5, 1, 0, NOW(), 'system'),

-- 员工状态
(0, 'per_employee_status', '在职', '1', 1, 1, 1, NOW(), 'system'),
(0, 'per_employee_status', '离职', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_employee_status', '试用期', '3', 3, 1, 0, NOW(), 'system'),
(0, 'per_employee_status', '休假', '4', 4, 1, 0, NOW(), 'system'),
(0, 'per_employee_status', '停职', '5', 5, 1, 0, NOW(), 'system'),

-- 律师职级
(0, 'per_lawyer_level', '实习律师', '1', 1, 1, 0, NOW(), 'system'),
(0, 'per_lawyer_level', '初级律师', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_lawyer_level', '中级律师', '3', 3, 1, 1, NOW(), 'system'),
(0, 'per_lawyer_level', '高级律师', '4', 4, 1, 0, NOW(), 'system'),
(0, 'per_lawyer_level', '资深律师', '5', 5, 1, 0, NOW(), 'system'),
(0, 'per_lawyer_level', '合伙人', '6', 6, 1, 0, NOW(), 'system'),

-- 合同类型
(0, 'per_contract_type', '劳动合同', '1', 1, 1, 1, NOW(), 'system'),
(0, 'per_contract_type', '服务合同', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_contract_type', '保密协议', '3', 3, 1, 0, NOW(), 'system'),
(0, 'per_contract_type', '竞业限制协议', '4', 4, 1, 0, NOW(), 'system'),

-- 合同状态
(0, 'per_contract_status', '未签', '0', 1, 1, 0, NOW(), 'system'),
(0, 'per_contract_status', '已签', '1', 2, 1, 1, NOW(), 'system'),
(0, 'per_contract_status', '过期', '2', 3, 1, 0, NOW(), 'system'),
(0, 'per_contract_status', '终止', '3', 4, 1, 0, NOW(), 'system'),

-- 职能类型
(0, 'per_function_type', '行政', '1', 1, 1, 1, NOW(), 'system'),
(0, 'per_function_type', '财务', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_function_type', '人事', '3', 3, 1, 0, NOW(), 'system'),
(0, 'per_function_type', '市场', '4', 4, 1, 0, NOW(), 'system'),
(0, 'per_function_type', 'IT', '5', 5, 1, 0, NOW(), 'system'),
(0, 'per_function_type', '前台', '6', 6, 1, 0, NOW(), 'system'),

-- 学历
(0, 'per_education', '高中', '1', 1, 1, 0, NOW(), 'system'),
(0, 'per_education', '专科', '2', 2, 1, 0, NOW(), 'system'),
(0, 'per_education', '本科', '3', 3, 1, 1, NOW(), 'system'),
(0, 'per_education', '硕士', '4', 4, 1, 0, NOW(), 'system'),
(0, 'per_education', '博士', '5', 5, 1, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 添加人事管理相关菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, 0, 'personnel', '人事管理', 1, '/personnel', NULL, 'team', 3, 1, NOW(), 'system', '人事管理模块'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel');

-- 员工管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'personnel:employee', '员工管理', 1, 'employee', 'personnel/employee/index', 'user', 1, 1, NOW(), 'system', '员工管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:employee');

-- 律师管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'personnel:lawyer', '律师管理', 1, 'lawyer', 'personnel/lawyer/index', 'user-solid', 2, 1, NOW(), 'system', '律师管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:lawyer');

-- 合同管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'personnel'), 'personnel:contract', '合同管理', 1, 'contract', 'personnel/contract/index', 'document', 3, 1, NOW(), 'system', '合同管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'personnel:contract');

-- 为管理员角色分配权限
INSERT INTO auth_role_permission (tenant_id, role_id, permission_id, status, create_time, create_by)
SELECT 0, 1, permission.id, 1, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'personnel%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );

-- organization模块相关字典类型
INSERT INTO sys_dict_type (tenant_id, dict_name, dict_type, status, is_system, create_time, create_by)
VALUES
(0, '部门类型', 'org_department_type', 1, 1, NOW(), 'system'),
(0, '团队类型', 'org_team_type', 1, 1, NOW(), 'system'),
(0, '职位类型', 'org_position_type', 1, 1, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 部门类型字典数据
INSERT INTO sys_dict_data (tenant_id, dict_type, dict_label, dict_value, dict_sort, status, is_default, create_time, create_by)
VALUES
-- 部门类型
(0, 'org_department_type', '业务部门', '1', 1, 1, 1, NOW(), 'system'),
(0, 'org_department_type', '职能部门', '2', 2, 1, 0, NOW(), 'system'),
(0, 'org_department_type', '管理部门', '3', 3, 1, 0, NOW(), 'system'),

-- 团队类型
(0, 'org_team_type', '业务团队', '1', 1, 1, 1, NOW(), 'system'),
(0, 'org_team_type', '项目团队', '2', 2, 1, 0, NOW(), 'system'),
(0, 'org_team_type', '特殊团队', '3', 3, 1, 0, NOW(), 'system'),

-- 职位类型
(0, 'org_position_type', '管理岗', '1', 1, 1, 0, NOW(), 'system'),
(0, 'org_position_type', '业务岗', '2', 2, 1, 1, NOW(), 'system'),
(0, 'org_position_type', '职能岗', '3', 3, 1, 0, NOW(), 'system'),
(0, 'org_position_type', '实习岗', '4', 4, 1, 0, NOW(), 'system')
ON DUPLICATE KEY UPDATE update_time = NOW();

-- 初始化组织架构菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, 0, 'organization', '组织架构', 1, '/organization', NULL, 'office-building', 2, 1, NOW(), 'system', '组织架构模块'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization');

-- 部门管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'organization:department', '部门管理', 1, 'department', 'personnel/organization/department/index', 'department', 1, 1, NOW(), 'system', '部门管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:department');

-- 团队管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'organization:team', '团队管理', 1, 'team', 'personnel/organization/team/index', 'user-group', 2, 1, NOW(), 'system', '团队管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:team');

-- 职位管理菜单
INSERT INTO auth_permission (tenant_id, parent_id, code, name, type, path, component, icon, sort, status, create_time, create_by, remark)
SELECT 0, (SELECT id FROM auth_permission WHERE code = 'organization'), 'organization:position', '职位管理', 1, 'position', 'personnel/organization/position/index', 'position', 3, 1, NOW(), 'system', '职位管理'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM auth_permission WHERE code = 'organization:position');

-- 为管理员角色分配组织架构权限
INSERT INTO auth_role_permission (tenant_id, role_id, permission_id, status, create_time, create_by)
SELECT 0, 1, permission.id, 1, NOW(), 'system'
FROM auth_permission permission
WHERE permission.code LIKE 'organization%'
  AND NOT EXISTS (
    SELECT 1 FROM auth_role_permission 
    WHERE role_id = 1 AND permission_id = permission.id
  );
